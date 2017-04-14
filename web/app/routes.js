var thrift = require('thrift')

var agentTypes = require('../thrift-gen/Agent_types.js');
var apolloTypes = require('../thrift-gen/Apollo_types.js');
var schedulerTypes = require('../thrift-gen/Scheduler_types.js');
var adminService = require('../thrift-gen/SchedulerAdminService.js');

module.exports = function(app) {

	var transport = thrift.TFramedTransport;
	var protocol = thrift.TBinaryProtocol;

	var connection = null;
	var client = null;

	function setupConnection() {
		if (connection != null) return;
		try {
			connection = thrift.createConnection("127.0.0.1", 4355, {
			  transport : transport,
			  protocol : protocol,
			  connect_timeout:1000,
			  timeout : 1000,
			});
			connection.on('error', function(err) {
				connection = null;
				client = null;
			});
			client = thrift.createClient(adminService, connection);
		} catch (err) {
			connection = null;
			client = null;
		}
	}

	// packs a byte array into a double
	function pack(bytes) {
	    var out = 0;
		if (bytes == null) return out;
	    for(var i = 0; i < bytes.length; i++) {
			out *= 256.0;
	        out += bytes[i];
	    }
	    return out;
	}

	function parseColor(curStatus) {
		var color;
		if (curStatus == 0 || curStatus == 1 || curStatus == 3 || curStatus == 4 || curStatus == 5) {
			color = 'orange';
		} else if (curStatus == 2) {
			color = 'green';
		} else if (curStatus == 6 || curStatus == 7 || curStatus == 8 || curStatus == 9 || curStatus == 10) {
			color = 'red';
		}
		return color;
	}

	function parseTaskList(taskList) {
		var res = [];
		if (taskList == null) return res;
		var idx;
		for (idx = 0; idx < taskList.length; idx++) {
			var task = taskList[idx];
			//console.log(task);
			var taskName = task.descriptor.name;
			var taskFails = task.numFailures;
			var curStatus = task.status;
			var taskId = task.descriptor.id;
			var timestmp = -1;
			var time = -1;
			var updates = task.statusUpdates;
			var statusIdx;
			for (statusIdx = updates.length-1; statusIdx >= 0; statusIdx--) {
				var update = updates[statusIdx];
				var col = parseColor(update.status);
				if (col == 'red') {
					break;
				}
				if (col == 'orange' || col == 'green') {
					timestmp = pack(update.timestamp.buffer);
				}
			}

		  	var clock = Date.now();
			if (col == 'orange' || col == 'green') {
			 	time = clock-timestmp;
			}
			var color = parseColor(curStatus);
			res.push({
					id:taskId,
					text:taskName,
					status:curStatus,
					timestamp:timestmp,
					timesince:time,
					numFailures:taskFails,
					statusColor:color
			});
		}
		return res;
	}

	var lastTaskList = [];
	// get all task data....
	app.get('/api/tasks', function(req, res) {
		setupConnection();
		if (!connection.connected) {
			res.json(lastTaskList);
			return;
		}
		try {
			client.getAllTaskHealth(function(err, response) {
				if (err || response.result == null) {
					res.json(lastTaskList);
					return;
				}
				var taskList = parseTaskList(response.result.schedulerGetStateResult.state.taskList);
				lastTaskList = taskList;
				res.json(lastTaskList);
			});
		} catch (err) {
			connection = null;
			client = null;
		}
	});

	app.get('/api/status', function(req, res) {
		setupConnection();
		if (!connection.connected) {
			res.json(false);
			return;
		}
		try {
			client.getAllTaskHealth(function(err, response) {
				if (err || response.result == null) {
					res.json(false);
					return;
				}
				res.json(true);
			});
		} catch (err) {
			connection = null;
			client = null;
		}
	});

	app.get('/api/inspect/:task_id', function(req, res) {
		setupConnection();
		if (!connection.connected) {
			res.json(null);
			return;
		}
		try {
			client.getTaskPorts(new apolloTypes.TaskID({id:req.params.task_id}), function(err, response) {
				if (err || response.result == null) {
					return;
				}
				res.json(response);
			});
		} catch (err) {
			connection = null;
			client = null;
		}
	});

	app.post('/api/register', function(req, res) {
		setupConnection();
		if (!connection.connected) {
			return;
		}
		try {
			console.log("NAME: " + req.body.task_name);
			console.log("URL: " + req.body.task_url);
			var uri = new apolloTypes.FetcherURI({locater:req.body.task_url});
			client.registerTask(new apolloTypes.TaskDescriptor({name:req.body.task_name,urlLocater:uri}));
		} catch (err) {
			connection = null;
			client = null;
		}
	});

	app.post('/api/kill/:task_id', function(req, res) {
		setupConnection();
		if (!connection.connected) {
			return;
		}
		try {
			client.killTask(new apolloTypes.TaskID({id:req.params.task_id}));
		} catch (err) {
			connection = null;
			client = null;
		}
    });

	// application -------------------------------------------------------------
	app.get('*', function(req, res) {
		res.sendfile('./public/index.html'); // load the single view file (angular will handle the page changes on the front-end)
	});
};

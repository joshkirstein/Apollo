//
// Autogenerated by Thrift Compiler (0.9.3)
//
// DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
//
var thrift = require('thrift');
var Thrift = thrift.Thrift;
var Q = thrift.Q;

var Apollo_ttypes = require('./Apollo_types')


var ttypes = require('./Scheduler_types');
//HELPER FUNCTIONS AND STRUCTURES

SchedulerAgentService_verifyTasks_args = function(args) {
  this.id = null;
  this.tasks = null;
  if (args) {
    if (args.id !== undefined && args.id !== null) {
      this.id = new Apollo_ttypes.AgentID(args.id);
    }
    if (args.tasks !== undefined && args.tasks !== null) {
      this.tasks = Thrift.copyList(args.tasks, [Apollo_ttypes.TaskID]);
    }
  }
};
SchedulerAgentService_verifyTasks_args.prototype = {};
SchedulerAgentService_verifyTasks_args.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRUCT) {
        this.id = new Apollo_ttypes.AgentID();
        this.id.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.LIST) {
        var _size0 = 0;
        var _rtmp34;
        this.tasks = [];
        var _etype3 = 0;
        _rtmp34 = input.readListBegin();
        _etype3 = _rtmp34.etype;
        _size0 = _rtmp34.size;
        for (var _i5 = 0; _i5 < _size0; ++_i5)
        {
          var elem6 = null;
          elem6 = new Apollo_ttypes.TaskID();
          elem6.read(input);
          this.tasks.push(elem6);
        }
        input.readListEnd();
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

SchedulerAgentService_verifyTasks_args.prototype.write = function(output) {
  output.writeStructBegin('SchedulerAgentService_verifyTasks_args');
  if (this.id !== null && this.id !== undefined) {
    output.writeFieldBegin('id', Thrift.Type.STRUCT, 1);
    this.id.write(output);
    output.writeFieldEnd();
  }
  if (this.tasks !== null && this.tasks !== undefined) {
    output.writeFieldBegin('tasks', Thrift.Type.LIST, 2);
    output.writeListBegin(Thrift.Type.STRUCT, this.tasks.length);
    for (var iter7 in this.tasks)
    {
      if (this.tasks.hasOwnProperty(iter7))
      {
        iter7 = this.tasks[iter7];
        iter7.write(output);
      }
    }
    output.writeListEnd();
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

SchedulerAgentService_verifyTasks_result = function(args) {
  this.success = null;
  if (args) {
    if (args.success !== undefined && args.success !== null) {
      this.success = new Apollo_ttypes.Response(args.success);
    }
  }
};
SchedulerAgentService_verifyTasks_result.prototype = {};
SchedulerAgentService_verifyTasks_result.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 0:
      if (ftype == Thrift.Type.STRUCT) {
        this.success = new Apollo_ttypes.Response();
        this.success.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

SchedulerAgentService_verifyTasks_result.prototype.write = function(output) {
  output.writeStructBegin('SchedulerAgentService_verifyTasks_result');
  if (this.success !== null && this.success !== undefined) {
    output.writeFieldBegin('success', Thrift.Type.STRUCT, 0);
    this.success.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

SchedulerAgentService_registerAgent_args = function(args) {
  this.thiz = null;
  if (args) {
    if (args.thiz !== undefined && args.thiz !== null) {
      this.thiz = new Apollo_ttypes.MachineDescriptor(args.thiz);
    }
  }
};
SchedulerAgentService_registerAgent_args.prototype = {};
SchedulerAgentService_registerAgent_args.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRUCT) {
        this.thiz = new Apollo_ttypes.MachineDescriptor();
        this.thiz.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

SchedulerAgentService_registerAgent_args.prototype.write = function(output) {
  output.writeStructBegin('SchedulerAgentService_registerAgent_args');
  if (this.thiz !== null && this.thiz !== undefined) {
    output.writeFieldBegin('thiz', Thrift.Type.STRUCT, 1);
    this.thiz.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

SchedulerAgentService_registerAgent_result = function(args) {
  this.success = null;
  this.ex = null;
  if (args instanceof ttypes.AgentRegistrationException) {
    this.ex = args;
    return;
  }
  if (args) {
    if (args.success !== undefined && args.success !== null) {
      this.success = new Apollo_ttypes.Response(args.success);
    }
    if (args.ex !== undefined && args.ex !== null) {
      this.ex = args.ex;
    }
  }
};
SchedulerAgentService_registerAgent_result.prototype = {};
SchedulerAgentService_registerAgent_result.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 0:
      if (ftype == Thrift.Type.STRUCT) {
        this.success = new Apollo_ttypes.Response();
        this.success.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 1:
      if (ftype == Thrift.Type.STRUCT) {
        this.ex = new ttypes.AgentRegistrationException();
        this.ex.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

SchedulerAgentService_registerAgent_result.prototype.write = function(output) {
  output.writeStructBegin('SchedulerAgentService_registerAgent_result');
  if (this.success !== null && this.success !== undefined) {
    output.writeFieldBegin('success', Thrift.Type.STRUCT, 0);
    this.success.write(output);
    output.writeFieldEnd();
  }
  if (this.ex !== null && this.ex !== undefined) {
    output.writeFieldBegin('ex', Thrift.Type.STRUCT, 1);
    this.ex.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

SchedulerAgentService_updateTaskStatus_args = function(args) {
  this.id = null;
  this.task = null;
  this.update = null;
  if (args) {
    if (args.id !== undefined && args.id !== null) {
      this.id = new Apollo_ttypes.AgentID(args.id);
    }
    if (args.task !== undefined && args.task !== null) {
      this.task = new Apollo_ttypes.TaskID(args.task);
    }
    if (args.update !== undefined && args.update !== null) {
      this.update = new Apollo_ttypes.StatusUpdate(args.update);
    }
  }
};
SchedulerAgentService_updateTaskStatus_args.prototype = {};
SchedulerAgentService_updateTaskStatus_args.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRUCT) {
        this.id = new Apollo_ttypes.AgentID();
        this.id.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.STRUCT) {
        this.task = new Apollo_ttypes.TaskID();
        this.task.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 3:
      if (ftype == Thrift.Type.STRUCT) {
        this.update = new Apollo_ttypes.StatusUpdate();
        this.update.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

SchedulerAgentService_updateTaskStatus_args.prototype.write = function(output) {
  output.writeStructBegin('SchedulerAgentService_updateTaskStatus_args');
  if (this.id !== null && this.id !== undefined) {
    output.writeFieldBegin('id', Thrift.Type.STRUCT, 1);
    this.id.write(output);
    output.writeFieldEnd();
  }
  if (this.task !== null && this.task !== undefined) {
    output.writeFieldBegin('task', Thrift.Type.STRUCT, 2);
    this.task.write(output);
    output.writeFieldEnd();
  }
  if (this.update !== null && this.update !== undefined) {
    output.writeFieldBegin('update', Thrift.Type.STRUCT, 3);
    this.update.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

SchedulerAgentService_updateTaskStatus_result = function(args) {
  this.success = null;
  this.ex = null;
  if (args instanceof ttypes.SchedulerTaskStatusUpdateException) {
    this.ex = args;
    return;
  }
  if (args) {
    if (args.success !== undefined && args.success !== null) {
      this.success = new Apollo_ttypes.Response(args.success);
    }
    if (args.ex !== undefined && args.ex !== null) {
      this.ex = args.ex;
    }
  }
};
SchedulerAgentService_updateTaskStatus_result.prototype = {};
SchedulerAgentService_updateTaskStatus_result.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 0:
      if (ftype == Thrift.Type.STRUCT) {
        this.success = new Apollo_ttypes.Response();
        this.success.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 1:
      if (ftype == Thrift.Type.STRUCT) {
        this.ex = new ttypes.SchedulerTaskStatusUpdateException();
        this.ex.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

SchedulerAgentService_updateTaskStatus_result.prototype.write = function(output) {
  output.writeStructBegin('SchedulerAgentService_updateTaskStatus_result');
  if (this.success !== null && this.success !== undefined) {
    output.writeFieldBegin('success', Thrift.Type.STRUCT, 0);
    this.success.write(output);
    output.writeFieldEnd();
  }
  if (this.ex !== null && this.ex !== undefined) {
    output.writeFieldBegin('ex', Thrift.Type.STRUCT, 1);
    this.ex.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

SchedulerAgentServiceClient = exports.Client = function(output, pClass) {
    this.output = output;
    this.pClass = pClass;
    this._seqid = 0;
    this._reqs = {};
};
SchedulerAgentServiceClient.prototype = {};
SchedulerAgentServiceClient.prototype.seqid = function() { return this._seqid; }
SchedulerAgentServiceClient.prototype.new_seqid = function() { return this._seqid += 1; }
SchedulerAgentServiceClient.prototype.verifyTasks = function(id, tasks, callback) {
  this._seqid = this.new_seqid();
  if (callback === undefined) {
    var _defer = Q.defer();
    this._reqs[this.seqid()] = function(error, result) {
      if (error) {
        _defer.reject(error);
      } else {
        _defer.resolve(result);
      }
    };
    this.send_verifyTasks(id, tasks);
    return _defer.promise;
  } else {
    this._reqs[this.seqid()] = callback;
    this.send_verifyTasks(id, tasks);
  }
};

SchedulerAgentServiceClient.prototype.send_verifyTasks = function(id, tasks) {
  var output = new this.pClass(this.output);
  output.writeMessageBegin('verifyTasks', Thrift.MessageType.CALL, this.seqid());
  var args = new SchedulerAgentService_verifyTasks_args();
  args.id = id;
  args.tasks = tasks;
  args.write(output);
  output.writeMessageEnd();
  return this.output.flush();
};

SchedulerAgentServiceClient.prototype.recv_verifyTasks = function(input,mtype,rseqid) {
  var callback = this._reqs[rseqid] || function() {};
  delete this._reqs[rseqid];
  if (mtype == Thrift.MessageType.EXCEPTION) {
    var x = new Thrift.TApplicationException();
    x.read(input);
    input.readMessageEnd();
    return callback(x);
  }
  var result = new SchedulerAgentService_verifyTasks_result();
  result.read(input);
  input.readMessageEnd();

  if (null !== result.success) {
    return callback(null, result.success);
  }
  return callback('verifyTasks failed: unknown result');
};
SchedulerAgentServiceClient.prototype.registerAgent = function(thiz, callback) {
  this._seqid = this.new_seqid();
  if (callback === undefined) {
    var _defer = Q.defer();
    this._reqs[this.seqid()] = function(error, result) {
      if (error) {
        _defer.reject(error);
      } else {
        _defer.resolve(result);
      }
    };
    this.send_registerAgent(thiz);
    return _defer.promise;
  } else {
    this._reqs[this.seqid()] = callback;
    this.send_registerAgent(thiz);
  }
};

SchedulerAgentServiceClient.prototype.send_registerAgent = function(thiz) {
  var output = new this.pClass(this.output);
  output.writeMessageBegin('registerAgent', Thrift.MessageType.CALL, this.seqid());
  var args = new SchedulerAgentService_registerAgent_args();
  args.thiz = thiz;
  args.write(output);
  output.writeMessageEnd();
  return this.output.flush();
};

SchedulerAgentServiceClient.prototype.recv_registerAgent = function(input,mtype,rseqid) {
  var callback = this._reqs[rseqid] || function() {};
  delete this._reqs[rseqid];
  if (mtype == Thrift.MessageType.EXCEPTION) {
    var x = new Thrift.TApplicationException();
    x.read(input);
    input.readMessageEnd();
    return callback(x);
  }
  var result = new SchedulerAgentService_registerAgent_result();
  result.read(input);
  input.readMessageEnd();

  if (null !== result.ex) {
    return callback(result.ex);
  }
  if (null !== result.success) {
    return callback(null, result.success);
  }
  return callback('registerAgent failed: unknown result');
};
SchedulerAgentServiceClient.prototype.updateTaskStatus = function(id, task, update, callback) {
  this._seqid = this.new_seqid();
  if (callback === undefined) {
    var _defer = Q.defer();
    this._reqs[this.seqid()] = function(error, result) {
      if (error) {
        _defer.reject(error);
      } else {
        _defer.resolve(result);
      }
    };
    this.send_updateTaskStatus(id, task, update);
    return _defer.promise;
  } else {
    this._reqs[this.seqid()] = callback;
    this.send_updateTaskStatus(id, task, update);
  }
};

SchedulerAgentServiceClient.prototype.send_updateTaskStatus = function(id, task, update) {
  var output = new this.pClass(this.output);
  output.writeMessageBegin('updateTaskStatus', Thrift.MessageType.CALL, this.seqid());
  var args = new SchedulerAgentService_updateTaskStatus_args();
  args.id = id;
  args.task = task;
  args.update = update;
  args.write(output);
  output.writeMessageEnd();
  return this.output.flush();
};

SchedulerAgentServiceClient.prototype.recv_updateTaskStatus = function(input,mtype,rseqid) {
  var callback = this._reqs[rseqid] || function() {};
  delete this._reqs[rseqid];
  if (mtype == Thrift.MessageType.EXCEPTION) {
    var x = new Thrift.TApplicationException();
    x.read(input);
    input.readMessageEnd();
    return callback(x);
  }
  var result = new SchedulerAgentService_updateTaskStatus_result();
  result.read(input);
  input.readMessageEnd();

  if (null !== result.ex) {
    return callback(result.ex);
  }
  if (null !== result.success) {
    return callback(null, result.success);
  }
  return callback('updateTaskStatus failed: unknown result');
};
SchedulerAgentServiceProcessor = exports.Processor = function(handler) {
  this._handler = handler
}
SchedulerAgentServiceProcessor.prototype.process = function(input, output) {
  var r = input.readMessageBegin();
  if (this['process_' + r.fname]) {
    return this['process_' + r.fname].call(this, r.rseqid, input, output);
  } else {
    input.skip(Thrift.Type.STRUCT);
    input.readMessageEnd();
    var x = new Thrift.TApplicationException(Thrift.TApplicationExceptionType.UNKNOWN_METHOD, 'Unknown function ' + r.fname);
    output.writeMessageBegin(r.fname, Thrift.MessageType.EXCEPTION, r.rseqid);
    x.write(output);
    output.writeMessageEnd();
    output.flush();
  }
}

SchedulerAgentServiceProcessor.prototype.process_verifyTasks = function(seqid, input, output) {
  var args = new SchedulerAgentService_verifyTasks_args();
  args.read(input);
  input.readMessageEnd();
  if (this._handler.verifyTasks.length === 2) {
    Q.fcall(this._handler.verifyTasks, args.id, args.tasks)
      .then(function(result) {
        var result = new SchedulerAgentService_verifyTasks_result({success: result});
        output.writeMessageBegin("verifyTasks", Thrift.MessageType.REPLY, seqid);
        result.write(output);
        output.writeMessageEnd();
        output.flush();
      }, function (err) {
        var result = new Thrift.TApplicationException(Thrift.TApplicationExceptionType.UNKNOWN, err.message);
        output.writeMessageBegin("verifyTasks", Thrift.MessageType.EXCEPTION, seqid);
        result.write(output);
        output.writeMessageEnd();
        output.flush();
      });
  } else {
    this._handler.verifyTasks(args.id, args.tasks, function (err, result) {
      if (err == null) {
        var result = new SchedulerAgentService_verifyTasks_result((err != null ? err : {success: result}));
        output.writeMessageBegin("verifyTasks", Thrift.MessageType.REPLY, seqid);
      } else {
        var result = new Thrift.TApplicationException(Thrift.TApplicationExceptionType.UNKNOWN, err.message);
        output.writeMessageBegin("verifyTasks", Thrift.MessageType.EXCEPTION, seqid);
      }
      result.write(output);
      output.writeMessageEnd();
      output.flush();
    });
  }
}

SchedulerAgentServiceProcessor.prototype.process_registerAgent = function(seqid, input, output) {
  var args = new SchedulerAgentService_registerAgent_args();
  args.read(input);
  input.readMessageEnd();
  if (this._handler.registerAgent.length === 1) {
    Q.fcall(this._handler.registerAgent, args.thiz)
      .then(function(result) {
        var result = new SchedulerAgentService_registerAgent_result({success: result});
        output.writeMessageBegin("registerAgent", Thrift.MessageType.REPLY, seqid);
        result.write(output);
        output.writeMessageEnd();
        output.flush();
      }, function (err) {
        if (err instanceof ttypes.AgentRegistrationException) {
          var result = new SchedulerAgentService_registerAgent_result(err);
          output.writeMessageBegin("registerAgent", Thrift.MessageType.REPLY, seqid);
        } else {
          var result = new Thrift.TApplicationException(Thrift.TApplicationExceptionType.UNKNOWN, err.message);
          output.writeMessageBegin("registerAgent", Thrift.MessageType.EXCEPTION, seqid);
        }
        result.write(output);
        output.writeMessageEnd();
        output.flush();
      });
  } else {
    this._handler.registerAgent(args.thiz, function (err, result) {
      if (err == null || err instanceof ttypes.AgentRegistrationException) {
        var result = new SchedulerAgentService_registerAgent_result((err != null ? err : {success: result}));
        output.writeMessageBegin("registerAgent", Thrift.MessageType.REPLY, seqid);
      } else {
        var result = new Thrift.TApplicationException(Thrift.TApplicationExceptionType.UNKNOWN, err.message);
        output.writeMessageBegin("registerAgent", Thrift.MessageType.EXCEPTION, seqid);
      }
      result.write(output);
      output.writeMessageEnd();
      output.flush();
    });
  }
}

SchedulerAgentServiceProcessor.prototype.process_updateTaskStatus = function(seqid, input, output) {
  var args = new SchedulerAgentService_updateTaskStatus_args();
  args.read(input);
  input.readMessageEnd();
  if (this._handler.updateTaskStatus.length === 3) {
    Q.fcall(this._handler.updateTaskStatus, args.id, args.task, args.update)
      .then(function(result) {
        var result = new SchedulerAgentService_updateTaskStatus_result({success: result});
        output.writeMessageBegin("updateTaskStatus", Thrift.MessageType.REPLY, seqid);
        result.write(output);
        output.writeMessageEnd();
        output.flush();
      }, function (err) {
        if (err instanceof ttypes.SchedulerTaskStatusUpdateException) {
          var result = new SchedulerAgentService_updateTaskStatus_result(err);
          output.writeMessageBegin("updateTaskStatus", Thrift.MessageType.REPLY, seqid);
        } else {
          var result = new Thrift.TApplicationException(Thrift.TApplicationExceptionType.UNKNOWN, err.message);
          output.writeMessageBegin("updateTaskStatus", Thrift.MessageType.EXCEPTION, seqid);
        }
        result.write(output);
        output.writeMessageEnd();
        output.flush();
      });
  } else {
    this._handler.updateTaskStatus(args.id, args.task, args.update, function (err, result) {
      if (err == null || err instanceof ttypes.SchedulerTaskStatusUpdateException) {
        var result = new SchedulerAgentService_updateTaskStatus_result((err != null ? err : {success: result}));
        output.writeMessageBegin("updateTaskStatus", Thrift.MessageType.REPLY, seqid);
      } else {
        var result = new Thrift.TApplicationException(Thrift.TApplicationExceptionType.UNKNOWN, err.message);
        output.writeMessageBegin("updateTaskStatus", Thrift.MessageType.EXCEPTION, seqid);
      }
      result.write(output);
      output.writeMessageEnd();
      output.flush();
    });
  }
}


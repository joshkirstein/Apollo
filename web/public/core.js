var apolloDash = angular.module('apolloDash', ['ngMaterial', 'ngMessages']);

apolloDash.filter('hhmmss', function () {
  return function (time) {
	time /= 1000.0;
    var sec_num = parseInt(time, 10); // don't forget the second param
    var hours   = Math.floor(sec_num / 3600);
    var minutes = Math.floor((sec_num - (hours * 3600)) / 60);
    var seconds = sec_num - (hours * 3600) - (minutes * 60);

    if (hours   < 10) {hours   = "0"+hours;}
    if (minutes < 10) {minutes = "0"+minutes;}
    if (seconds < 10) {seconds = "0"+seconds;}
    var time    = hours+':'+minutes+':'+seconds;
    return time;
  }
});

apolloDash.controller("mainController", function($scope, $http, $timeout, $mdDialog) {
	$scope.formData = {};
	$scope.tasks = [];

    $scope.newTask = function() {
        var name = $mdDialog.prompt()
              .title('What is the name of your task?')
              .placeholder('Web App #1')
              .ok('Next')
              .cancel('Cancel');

        $mdDialog.show(name).then(function(nameresult) {

            var uri = $mdDialog.prompt()
                  .title('What is the URL of the docker image?')
                  .placeholder('file:///Users/joshuakirstein/Desktop/image.tz')
                  .ok('Start')
                  .cancel('Cancel');

            $mdDialog.show(uri).then(function(uriresult) {
                $http.post('/api/register', {
                    task_name:nameresult,
                    task_url:uriresult
                });
            }, function() {});

        }, function() {});
    };

    $scope.inspectTask = function(task) {
        $http.get('/api/inspect/' + task.id).then(
            function(data) {
                if (data == null || data.data == null || data.data.result == null) return;
                var res = data.data.result.schedulerGetTaskPortsResult.portStrings;
                $mdDialog.show(
                  $mdDialog.alert()
                    .parent(angular.element(document.querySelector('#popupContainer')))
                    .clickOutsideToClose(true)
                    .title('Inspect')
                    .textContent('Mapped ports: ' + res)
                    .ok('Okay')
                );
            }
        );
    };

    $scope.killTask = function(task) {
        $http.post('/api/kill/' + task.id);
    };

    $http.get('/api/tasks').then(
        function(data) {
            $scope.tasks = data.data;
        }
    );

	$scope.tickInterval = 1000;
    $scope.refreshInterval = 1000;

    var tick = function() {
        $scope.clock = Date.now();
		var idx;
		for (idx = 0; idx < $scope.tasks.length; idx++) {
			var task = $scope.tasks[idx];
			if (task.statusColor == 'orange' ||task.statusColor == 'green') {
				task.timesince = $scope.clock-task.timestamp;
			}
		}
        $timeout(tick, $scope.tickInterval); // reset the timer
    }

    var tick2 = function() {
        $http.get('/api/tasks').then(
            function(data) {
                $scope.tasks = data.data;
            }
        );
        $timeout(tick2, $scope.refreshInterval); // reset the timer
    }

    $timeout(tick, $scope.tickInterval);
    $timeout(tick2, $scope.refreshInterval);
});

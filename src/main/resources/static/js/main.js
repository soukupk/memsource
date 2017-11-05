angular.module('memsource', [])
  .controller('setupController', function($scope,apiService) {
    
    $scope.saveConfig = function() {
    	apiService.saveConfig($scope.configuration).then(function(value) {
    		$scope.saveMessage = "Saved";
    	}, function(reason) {
    		$scope.saveMessage = "Error:" + angular.toJson(reason);
    	});
    }
    
})
  .controller('projectController', function($scope,apiService) {
    $scope.projects = [
    ];
    
    $scope.reloadProjects = function() {
    	$scope.listMessage = "Loading projects...";
    	apiService.listProjects().then(function(value) {
    		$scope.listMessage = "Ok";
    		$scope.projects = value.data;
    	}, function(reason) {
    		if(reason.data !== undefined) {
    			$scope.listMessage = "Error: " + reason.data.message;
    		} else {
    			$scope.listMessage = "Error: " + angular.toJson(reason);
    		}
    	});
    }
    
})
  .service('apiService', function($http) {
	  return {
		  saveConfig: function(configuration) {
			  return $http.post("/configuration", configuration);
		  },
		  
		  listProjects: function() {
			  return $http.get("/projects");
		  }
	  		
	  }
  });


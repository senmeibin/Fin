    var app = angular.module("loginApp", []);
    app.controller('loginController', function($scope,$http){
    	// init
    	$scope.name="";
    	$scope.password="";
    	$scope.checkLoginUser = function(){
    		 $http({
    		    url: "checkLoginUser.json",
    		    method: "GET",
    		    params: {name: $scope.name,password:$scope.password}
    		 })
    	    .then(function(response) {
    	        //First function handles success
    	    	if(response.statusText=="OK"){
    	    		if(response.data){
    	    			location.href = "index.html";
    	    		}else{
    	    			alert("用户名或密码不对！")
    	    		}
    	    	}else{
    	    		alert("系统出错了！");
    	    	}
    	    }, function(response) {
    	        //Second function handles error
    	        alert("系统出错了！");
    	    });
    	}
    });
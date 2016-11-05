    var app = angular.module("fn04App", []);
    app.controller('fn04Controller', function($scope,$http){
    	// init
    	$scope.searchOK=false;
    	$scope.search_year = new Date().getFullYear();
    	$scope.invalidFlag=[];
    	$scope.itemList = [];
    	$scope.addItem = function(){$scope.itemList.push({seq:0,year:$scope.result_year+"",name:"项目",amount:0})};
    	$scope.search = function(){
    		 $http({
    		    url: "fn04.json",
    		    method: "GET",
    		    params: {search_year: $scope.search_year}
    		 })
    	    .then(function(response) {
    	        //First function handles success
    	    	if(response.statusText=="OK"){
    	    		$scope.searchOK=true;
        	        $scope.result_year = $scope.search_year;
        	        $scope.itemList = response.data;
    	    	}else{
    	    		$scope.searchOK=false;
    	    		alert("出错了！");
    	    	}
    	    }, function(response) {
    	        //Second function handles error
    	    	$scope.searchOK=false;
    	        alert("出错了！");
    	    });
    	}
    	$scope.checkInput = function(){
    		var ret = true;
    		for(var i=0;i<$scope.invalidFlag.length;i++){
    			if($scope.invalidFlag[i]==true){
    				ret=false;
    				break;
    			}
    		}
    		return ret;
    	}
    	$scope.saveItem = function(){
	   		 $http({
	 		    url: "fn04_updateAll.json",
	 		    method: "POST",
	 		    data: $scope.itemList
	 		 })
	 	    .then(function(response) {
	 	        //First function handles success
	 	    	if(response.statusText=="OK"){
	 	    		$scope.searchOK=true;
	     	        $scope.itemList = response.data;
	     	        alert("数据保存成功。");
	 	    	}else{
	 	    		$scope.searchOK=false;
	 	    		alert("出错了！");
	 	    	}
	 	    }, function(response) {
	 	        //Second function handles error
	 	    	$scope.searchOK=false;
	 	        alert("出错了！");
	 	    });
    	}

    	// load
    	$scope.search();
    });
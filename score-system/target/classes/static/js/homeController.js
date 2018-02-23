app.controller('homeController', function($scope,$http, $rootScope) {
	$scope.cancelAuthkey = function () {
		var r = confirm("認証を取消します。よろしいですか？");
		if (r == true) {
			localStorage.removeItem("authkey");
			$rootScope.authkeyObj=null;
			location.reload(false);
		} else {
			return;
		}
	}
	
	$scope.registAuthkey = function (){
		$http({
				url: "/authkey/getOne",
				method: "GET",
				params: {key: $scope.key}
			 })
			.then(function(response) {
				//First function handles success
				if(response.status=="200"){
					if(response.data){
						localStorage.setItem("authkey",$scope.key);
						$rootScope.authkeyObj=response.data;
						location.reload(false);
					}else{
						alert("認証キーが存在しません。");
					}
					
				}else{
					alert("出错了！");
				}
			}, function(response) {
				//Second function handles error
				alert("出错了！");
			});
	}
	
	$scope.init = function(){
		 $http({
		    url: "/schedule/getAll",
		    method: "GET"
		 })
	    .then(function(response) {
	        //First function handles success
	    	if(response.status=="200"){
	    		$scope.schedule_list = response.data;
	    	}else{
	    		alert("出错了！");
	    	}
	    }, function(response) {
	        //Second function handles error
	        alert("出错了！");
	    });
	}
});

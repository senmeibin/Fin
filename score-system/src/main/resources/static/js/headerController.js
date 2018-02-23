app.controller('headerController', function($scope,$http, $rootScope) {
	$rootScope.authkeyObj=null;
	$scope.init = function(){
		if (typeof(Storage) === "undefined") {
			alert("无法读取注册信息。如使用注册功能请使用最新版chrome或者safari浏览器。");
			return;
		}
		
		if(localStorage.getItem("authkey")){
			 $http({
					url: "/authkey/getOne",
					method: "GET",
					params: {key: localStorage.getItem("authkey")}
				 })
				.then(function(response) {
					//First function handles success
					if(response.status=="200"){
						$rootScope.authkeyObj=response.data;
					}else{
						alert("出错了！");
					}
				}, function(response) {
					//Second function handles error
					alert("出错了！");
				});
		}
	}
});


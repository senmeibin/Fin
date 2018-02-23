app.controller('pointsCaculateController', function($scope,$http, $rootScope) {
	$scope.editLastBatchDate = function (){
		if(!$scope.lastBatchDateInput || $scope.lastBatchDateInput.toString().length!=8){
			alert("日期格式不对。");
			return;
		}
		
		var r = confirm("确定要修改上次比赛积分日期么？");
		if (r == false) {
			return;
		}
		
		var temp = angular.copy($scope.lastBatchDateObj);
		temp.value=$scope.lastBatchDateInput;
		
		$http({
				url: "/syspara/editLastBatchDate",
				 method: "POST",
				 data: temp
			 })
			.then(function(response) {
				//First function handles success
				if(response.status=="200"){
					$scope.lastBatchDateObj=temp;
				}else{
					alert("出错了！");
				}
			}, function(response) {
				//Second function handles error
				if(response.data.message){
					alert(response.data.message);
				}else{
					alert("出错了！");
				}
			});
	}
	

	$scope.caculatePoint = function (){
		var r = confirm("确定要计算比赛积分？");
		if (r == false) {
			return;
		}
		$http({
				url: "/point/caculatePoint",
				method: "POST"
			 })
			.then(function(response) {
				//First function handles success
				if(response.status=="200"){
					alert("积分计算正常结束。");
					$scope.init();
				}else{
					alert("出错了！");
				}
			}, function(response) {
				//Second function handles error
				if(response.data.message){
					alert(response.data.message);
				}else{
					alert("出错了！");
				}
			});
	}
	
	$scope.init = function (){
		$http({
				url: "/syspara/getLastBatchDate",
				method: "GET"
			 })
			.then(function(response) {
				//First function handles success
				if(response.status=="200"){
					$scope.lastBatchDateObj=response.data;
				}else{
					alert("出错了！");
				}
			}, function(response) {
				//Second function handles error
				alert("出错了！");
			});
	}
});

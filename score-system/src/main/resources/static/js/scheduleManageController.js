app.controller('scheduleManageController', function($scope,$http, $rootScope) {
	$scope.getAll = function(){
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
	
	$scope.addSchedule = function(){
		if(!$scope.inputSchedule.training_date || !$scope.inputSchedule.training_time){
			alert("请输入新日程的日期和时间。");
			return;
		}
		
		 $http({
			url: "/schedule/add",
			 method: "POST",
			 data: $scope.inputSchedule
		 })
		.then(function(response) {
			//First function handles success
			if(response.status=="200"){
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
	
	$scope.copySchedule = function(){
		if(!$scope.schedule_list || $scope.schedule_list.length==0){
			alert("没有数据可以拷贝");
			return;
		}
		
		$scope.inputSchedule = angular.copy($scope.schedule_list[$scope.schedule_list.length-1]);
	}
	
	$scope.editSchedule = function(target){
		$scope.tempSchedule = angular.copy(target);
		$('#editScheduleModal').modal('show');
	}
	
	$scope.saveSchedule = function(target){
		if(!target.training_date || !target.training_time){
			alert("请输入该日程的日期和时间。");
			return;
		}
		
		 $http({
			url: "/schedule/edit",
			 method: "POST",
			 data: target
		 })
		.then(function(response) {
			//First function handles success
			if(response.status=="200"){
				$('#editScheduleModal').modal('hide');
				$scope.getAll();
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
	
	$scope.deleteSchedule = function(target){
		var r = confirm("确定要删除这个联系日程么？");
		if (r == true) {
		} else {
			return;
		}
		
		 $http({
			url: "/schedule/delete",
			 method: "POST",
			 data: target.id
		 })
		.then(function(response) {
			//First function handles success
			if(response.status=="200"){
				$scope.getAll();
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
	
	$scope.init = function(){
		$scope.inputSchedule={};
		$scope.getAll();
	}
});

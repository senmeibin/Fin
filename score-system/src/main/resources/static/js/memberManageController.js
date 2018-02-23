app.controller('memberManageController', function($scope,$http, $rootScope) {
	$scope.getAll = function(){
		 $http({
			url: "/member/getAll",
			method: "GET"
		 })
		.then(function(response) {
			//First function handles success
			if(response.status=="200"){
				$scope.member_list = response.data;
			}else{
				alert("出错了！");
			}
		}, function(response) {
			//Second function handles error
			alert("出错了！");
		});
	}
	
	$scope.editMember = function(target){
		$scope.tempMember = angular.copy(target);
		$scope.active_flg_choice = $scope.active_flg_choices[target.active_flg];
		$('#editMemberModal').modal('show');
	}
	
	$scope.addMember = function(){
		if(!$scope.inputMember.name || (!$scope.inputMember.init_point && $scope.inputMember.init_point!==0)){
			alert("请输入姓名和初始分数。");
			return;
		}
		
		 $http({
			url: "/member/add",
			 method: "POST",
			 data: $scope.inputMember
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
	
	$scope.saveMember = function(target){
		if(!target.name || (!target.init_point && target.init_point!==0)){
			alert("请输入姓名和初始分数。");
			return;
		}
		
		// set choice value
		target.active_flg=$scope.active_flg_choice.value;
		
		 $http({
			url: "/member/edit",
			 method: "POST",
			 data: target
		 })
		.then(function(response) {
			//First function handles success
			if(response.status=="200"){
				$('#editMemberModal').modal('hide');
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
		$scope.inputMember={};
		$scope.inputMember.init_point=2000;
		$scope.inputMember.active_flg=1;
		$scope.active_flg_choices = [{value: 0, label: "否"},{value: 1, label: "是"}];
		$scope.getAll();
	}
});

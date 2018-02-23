app.controller('matchEntryController', function($scope,$http) {
	$scope.getTodayMatch = function(){
		 $http({
			url: "/match/getTodayMatch",
			method: "GET"
		 })
		.then(function(response) {
			//First function handles success
			if(response.status=="200"){
				if(response.data.length>0){
					$scope.todayMatches = response.data;
				}else{
					$scope.todayMatches = [];
				}
			}else{
				alert("出错了！");
			}
		}, function(response) {
			//Second function handles error
			alert("出错了！");
		});
	}
	
	$scope.getRecentMember = function(){
		 $http({
			url: "/point/getRecentMember",
			method: "GET"
		 })
		.then(function(response) {
			//First function handles success
			if(response.status=="200"){
				$scope.memberArr=[];
				for(var i=0;i<response.data.length;i++){
					var row=[];
					row[0] = response.data[i++];
					if(i<response.data.length){
						row[1] = response.data[i++];
					}
					if(i<response.data.length){
						row[2] = response.data[i];
					}
					$scope.memberArr.push(row);
				}
			}else{
				alert("出错了！");
			}
		}, function(response) {
			//Second function handles error
			alert("出错了！");
		});
	}
	
	$scope.autoInput = function(member_id){
		if($scope.inputMatch.winner1==null || $scope.inputMatch.winner1===""){
			$scope.inputMatch.winner1=member_id;
		}else if($scope.inputMatch.winner2==null || $scope.inputMatch.winner2===""){
			$scope.inputMatch.winner2=member_id;
		}else if($scope.inputMatch.losser1==null || $scope.inputMatch.losser1===""){
			$scope.inputMatch.losser1=member_id;
		}else if($scope.inputMatch.losser2==null || $scope.inputMatch.losser2===""){
			$scope.inputMatch.losser2=member_id;
			$("#lossScoreModal").modal();
		}else{}
	}
	
	$scope.backClear = function(){
		if($scope.inputMatch.losser2!=null && $scope.inputMatch.losser2!==""){
			$scope.inputMatch.losser2="";
		}else if($scope.inputMatch.losser1!=null && $scope.inputMatch.losser1!==""){
			$scope.inputMatch.losser1="";
		}else if($scope.inputMatch.winner2!=null && $scope.inputMatch.winner2!==""){
			$scope.inputMatch.winner2="";
		}else if($scope.inputMatch.winner1!=null && $scope.inputMatch.winner1!==""){
			$scope.inputMatch.winner1="";
		}else{}
	}
	
	$scope.autoSetWinScore = function(){

		if($scope.inputMatch.loss_score>=20){
			if($scope.inputMatch.loss_score<=28){
				$scope.inputMatch.win_score= $scope.inputMatch.loss_score + 2;
			}else{
				$scope.inputMatch.win_score= $scope.inputMatch.loss_score + 1;
			}
		}else{
			$scope.inputMatch.win_score= 21;
		}
	}
	
	$scope.getAllMember = function(){
		 $http({
			url: "/member/getAllActivated",
			method: "GET"
		 })
		.then(function(response) {
			//First function handles success
			if(response.status=="200"){
				$scope.allMemberMap={};
				for(var i=0;i<response.data.length;i++){
					$scope.allMemberMap[response.data[i].id]=response.data[i].name;
				}
				
				$scope.allMemberArr=[];
				for(var i=0;i<response.data.length;i++){
					var row=[];
					row[0] = response.data[i++];
					if(i<response.data.length){
						row[1] = response.data[i++];
					}
					if(i<response.data.length){
						row[2] = response.data[i++];
					}
					if(i<response.data.length){
						row[3] = response.data[i];
					}
					$scope.allMemberArr.push(row);
				}
			}else{
				alert("出错了！");
			}
		}, function(response) {
			//Second function handles error
			alert("出错了！");
		});
	}

	$scope.addMember = function (match) {
		// validate
		if(!$scope.newMemberName){
			alert("请输入会员姓名。");
			return;
		}
		
		var newMember = {};
		newMember.name=$scope.newMemberName;
		newMember.init_point=2000;
		newMember.active_flg = 1;
		
		// submit
		$http({
			url: "/member/add",
			 method: "POST",
			 data: newMember
		 })
		.then(function(response) {
			//First function handles success
			if(response.status=="200"){
				alert("会员名："+response.data.name+"\nID："+response.data.id+"\n已经登陆到系统。");
				$scope.newMemberName="";
				document.getElementById('addMemberModalCloseBtn').click();
				$scope.getAllMember();
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
	};
	
	$scope.matchInput = function(){
		// validate
		if(!$scope.validateMatch($scope.inputMatch)){
			return;
		}
		
		// submit
		 $http({
			url: "/match/add",
 			method: "POST",
 			data: $scope.inputMatch
		 })
		.then(function(response) {
			//First function handles success
			if(response.status=="200"){
				$scope.matchClear();
				$scope.getTodayMatch();
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
	
	$scope.matchClear =function(){
		$scope.inputMatch.winner1="";
		$scope.inputMatch.winner2="";
		$scope.inputMatch.losser1="";
		$scope.inputMatch.losser2="";
		$scope.inputMatch.win_score=21;
		$scope.inputMatch.loss_score=17;
	}
	
	$scope.getTemplate = function (todayMatch) {
		if (todayMatch.id === $scope.selectedTodayMatch.id) return 'edit';
		else return 'display';
	};
	
	$scope.matchEdit = function (match) {
		$scope.selectedTodayMatch = angular.copy(match);
	};
	
	$scope.matchDelete = function (match) {
	    var r = confirm("确定要删除这场比赛么？");
	    if (r == true) {
	    } else {
	        return;
	    }
		// submit
		$http({
			url: "/match/delete",
			 method: "POST",
			 data: match
		 })
		.then(function(response) {
			//First function handles success
			if(response.status=="200"){
				$scope.getTodayMatch();
				$scope.getRecentMember();
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
	};
	
	$scope.matchSave = function (idx) {
		// validate
		if(!$scope.validateMatch($scope.selectedTodayMatch)){
			return;
		}
		// submit
		$http({
			url: "/match/edit",
			 method: "POST",
			 data: $scope.selectedTodayMatch
		 })
		.then(function(response) {
			//First function handles success
			if(response.status=="200"){
				$scope.getTodayMatch();
				$scope.reset();
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
	};
	
	$scope.reset = function () {
		$scope.selectedTodayMatch = {};
	};
	
	$scope.validateMatch = function(match){
		if(match.winner1==null || match.winner1==="" || !$scope.allMemberMap[match.winner1]){
			alert("胜者1没有被输入或者不存在。");
			return false;
		}else if(match.winner2==null || match.winner2==="" || !$scope.allMemberMap[match.winner2]){
			alert("胜者2没有被输入或者不存在。");
			return false;
		}else if(match.losser1==null || match.losser1==="" || !$scope.allMemberMap[match.losser1]){
			alert("败者1没有被输入或者不存在。");
			return false;
		}else if(match.losser2==null || match.losser2==="" || !$scope.allMemberMap[match.losser2]){
			alert("败者2没有被输入或者不存在。");
			return false;
		}else if(!match.win_score || match.win_score < 21){
			alert("胜者比分没有被输入或者小于21分。");
			return false;
		}else if(!match.loss_score && match.loss_score !== 0){
			alert("败者比分没有被输入。");
			return false;
		}else if(match.win_score <= match.loss_score){
			alert("胜者比分不可以小于等于败者比分。");
			return false;
		}else{
			return true;
		}
	}
	
	$scope.init = function(){
		$scope.scoreArr=[];
		for(var i=0;i<31;i++){
			$scope.scoreArr[i]=i;
		}
		$scope.getRecentMember();
		$scope.getAllMember();
		$scope.selectedTodayMatch={};
		$scope.getTodayMatch();
	}
});

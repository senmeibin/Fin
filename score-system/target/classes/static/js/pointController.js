app.controller('pointController', function($scope,$http) {
	$scope.searchPoint = function(match_date_arg){
		 $http({
		    url: "/point/get",
		    method: "GET",
		    params: {match_date: match_date_arg}
		 })
	    .then(function(response) {
	        //First function handles success
	    	if(response.status=="200"){
	    		if(response.data.length>0){
	    			$scope.points = response.data;
	    			$scope.match_date_table = response.data[0].match_date;
	    			$scope.match_date=Number(response.data[0].match_date);
	    		}else{
	    			alert("该天没有比赛。");
	    		}
	    	}else{
	    		alert("出错了！");
	    	}
	    }, function(response) {
	        //Second function handles error
	        alert("出错了！");
	    });
	}
	
	$scope.searchMatch = function(match_date_arg){
		 $http({
		    url: "/match/getMatchDtoList",
		    method: "GET",
		    params: {match_date: match_date_arg}
		 })
	    .then(function(response) {
	        //First function handles success
	    	if(response.status=="200"){
	    		if(response.data.length>0){
	    			$scope.matchList = response.data;
	    		}else{
	    			alert("该天没有比赛。");
	    		}
	    	}else{
	    		alert("出错了！");
	    	}
	    }, function(response) {
	        //Second function handles error
	        alert("出错了！");
	    });
	}
	
	$scope.searchWinPer = function(match_date_arg){
		 $http({
		    url: "/point/getWinPerRank",
		    method: "GET",
		    params: {match_date: match_date_arg}
		 })
	    .then(function(response) {
	        //First function handles success
	    	if(response.status=="200"){
	    		if(response.data.length>0){
	    			$scope.winPerList = response.data;
	    		}else{
	    			alert("该天没有比赛。");
	    		}
	    	}else{
	    		alert("出错了！");
	    	}
	    }, function(response) {
	        //Second function handles error
	        alert("出错了！");
	    });
	}
	
	$scope.search = function(match_date_arg){
		var index = $scope.match_date_list.indexOf(match_date_arg.toString());
		if(index == -1){
			alert("该天没有比赛。");
		}else{
			$scope.searchPoint(match_date_arg);
			$scope.searchMatch(match_date_arg);
			$scope.searchWinPer(match_date_arg);
		}
	}
	
	$scope.searchWithDate = function(){
		$scope.search($scope.match_date);
	}
	
	$scope.searchLast = function(){
		//$scope.match_date=Number($scope.match_date_list[0]);
		$scope.search($scope.match_date_list[0]);
	}
	
	$scope.searchPrevious = function(){
		var index = $scope.match_date_list.indexOf($scope.match_date_table);
		if (index != -1 && index < $scope.match_date_list.length-1){
			$scope.search($scope.match_date_list[index+1]);
		}
	}
	
	$scope.searchNext = function(){
		var index = $scope.match_date_list.indexOf($scope.match_date_table);
		if (index != -1 && index > 0){
			$scope.search($scope.match_date_list[index-1]);
		}
	}
	
	$scope.matchModify = function(){
		if(!$scope.modifyMatchNo){
			alert("请输入比赛No。");
			return;
		}
		
		if($scope.modifyMatchNo<1 || $scope.modifyMatchNo > $scope.matchList.length){
			alert("比赛No不存在。");
			return;
		}
		
		 $http({
		    url: "/match/getOne",
		    method: "GET",
		    params: {id: $scope.matchList[$scope.modifyMatchNo-1].id}
		 })
	    .then(function(response) {
	        //First function handles success
	    	if(response.status=="200"){
	    		if(response.data){
	    			$scope.modifyTarget = response.data;
	    			$('#modifyMatchModal').modal('show');
	    		}else{
	    			alert("没有找到该场比赛。");
	    		}
	    	}else{
	    		alert("出错了！");
	    	}
	    }, function(response) {
	        //Second function handles error
	        alert("出错了！");
	    });
	}

	$scope.matchSave = function () {
		// validate
		if(!$scope.validateMatch($scope.modifyTarget)){
			return;
		}
		
		var endPointUrl="";
		if($scope.modifyTarget.id){
			endPointUrl="/match/edit";
		}else{
			endPointUrl="/match/add";
		}
		// submit
		$http({
			url: endPointUrl,
			 method: "POST",
			 data: $scope.modifyTarget
		 })
		.then(function(response) {
			//First function handles success
			if(response.status=="200"){
				$scope.search($scope.match_date_table);
				$('#modifyMatchModal').modal('hide');
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
	
	$scope.matchDelete = function () {
		if(!$scope.modifyMatchNo){
			alert("请输入比赛No。");
			return;
		}
		
		if($scope.modifyMatchNo<1 || $scope.modifyMatchNo > $scope.matchList.length){
			alert("比赛No不存在。");
			return;
		}
		
		var r = confirm("确定要删除这场比赛么？");
	    if (r == true) {
	    } else {
	        return;
	    }
		// submit
		$http({
			url: "/match/delete",
			 method: "POST",
			 data: $scope.matchList[$scope.modifyMatchNo-1]
		 })
		.then(function(response) {
			//First function handles success
			if(response.status=="200"){
				$scope.modifyMatchNo="";
				$scope.search($scope.match_date_table);
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
	
	$scope.matchAdd = function(){
		$scope.modifyTarget={};
		$scope.modifyTarget.match_date=$scope.match_date_table;
		$('#modifyMatchModal').modal('show');
	}

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
		    url: "/point/getMatchDate",
		    method: "GET"
		 })
	    .then(function(response) {
	        //First function handles success
	    	if(response.status=="200"){
	    		$scope.match_date_list = response.data;
	    		$scope.searchLast();
	    	}else{
	    		alert("出错了！");
	    	}
	    }, function(response) {
	        //Second function handles error
	        alert("出错了！");
	    });
		$scope.getAllMember();
	}
});

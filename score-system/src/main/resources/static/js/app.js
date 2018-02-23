var app = angular.module('app', ['ngRoute','ngResource']);
app.config(function($routeProvider,$httpProvider){
    $routeProvider
        .when('/',{
            templateUrl: '/views/home.html',
            controller: 'homeController'
        })
        .when('/points',{
            templateUrl: '/views/points.html',
            controller: 'pointController'
        })
        .when('/matchEntry',{
            templateUrl: '/views/matchEntry.html',
            controller: 'matchEntryController'
        })
        .when('/pointsCalculate',{
            templateUrl: '/views/pointsCalculate.html',
            controller: 'pointsCaculateController'
        })
        .when('/scheduleManage',{
            templateUrl: '/views/scheduleManage.html',
            controller: 'scheduleManageController'
        })
        .when('/memberManage',{
            templateUrl: '/views/memberManage.html',
            controller: 'memberManageController'
        })
        .otherwise(
            { redirectTo: '/'}
        );
    $httpProvider.defaults.headers.common.Authorization=localStorage.getItem("authkey");
});


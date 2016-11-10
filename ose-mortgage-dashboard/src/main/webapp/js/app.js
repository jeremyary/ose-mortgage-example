'use strict';


var app = angular.module('app', [
    'ngRoute',
    'ngResource',
    'ngSanitize'
]);

app.constant('Constants', {
   PROCESS_STATE: {
       0: 'PENDING',
       1: 'ACTIVE',
       2: 'COMPLETED',
       3: 'ABORTED',
       4: 'SUSPENDED'
   }
});

app.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'views/home.html',
                controller: 'HomeCtrl'
            })
            .when('/broker', {
                templateUrl: 'views/broker.html',
                controller: 'BrokerCtrl'
            })
            .when('/manager', {
                templateUrl: 'views/manager.html',
                controller: 'ManagerCtrl'
            })
            .when('/applicant', {
                templateUrl: 'views/applicant.html',
                controller: 'ApplicantCtrl'
            })
            .when('/admin', {
                templateUrl: 'views/admin.html',
                controller: 'AdminCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });
    }]);
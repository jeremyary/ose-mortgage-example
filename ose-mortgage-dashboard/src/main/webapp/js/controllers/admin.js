'use strict';

angular.module('app')
    .controller("AdminCtrl", ['$scope', '$http', function ($scope, $http) {

        $scope.role = "admin";
    }]);
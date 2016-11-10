'use strict';

angular.module('app')
    .controller("BrokerCtrl", ['$scope', '$http', 'Constants', function ($scope, $http, Constants) {

        $scope.consts = Constants;

        $http.get("service/broker/tasks").success(function (data) {
            $scope.containers = data;
        });
    }]);
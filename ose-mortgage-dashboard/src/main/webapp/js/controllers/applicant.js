'use strict';

angular.module('app')
    .controller("ApplicantCtrl", ['$scope', '$http', function ($scope, $http) {

        $scope.applicantName = "Bob Dole";
        $scope.applicantSsn = "313781234";
        $scope.applicantIncome = 150000;
        $scope.propertyAddress = "100 Central Ave";
        $scope.propertyPrice = 350000;
        $scope.downPayment = 50000;
        $scope.amortization = 5;

        $scope.startApp = function() {
            $http.get("service/applicant/startApp").success(function (data) {
                alert('success!');
            });
        };

    }]);
'use strict';

angular.module('research').controller('LoginController', function ($scope, $location, authenticationService) {
    $scope.iHealthLogin = function() {
        authenticationService.iHealthLogin();
    };
});
'use strict';

angular.module('research').service('authenticationService', function ($http) {
    this.iHealthLogin = function () {
        return $http.get("http://localhost:8080/research/server/authorization/iHealthLabsAuthorizationUrl").then(
            function(response) {
                window.location.href = response.data;
            },
            function(reason) {
                //TODO error while building href
            }
        );
    };
});
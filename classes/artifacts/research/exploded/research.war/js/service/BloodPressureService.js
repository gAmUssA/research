'use strict';

angular.module('research').service('bloodPressureService', function ($http) {
    this.getData = function () {
        return $http.get("http://localhost:8080/research/server/bp").then(
            function(response) {
                return response.data;
            },
            function(reason) {
                //TODO error
            }
        );
    };
});
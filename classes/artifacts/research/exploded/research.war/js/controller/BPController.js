'use strict';

function BPController($scope, bpData) {
    $scope.data = bpData;
}

BPController.resolve = {
    bpData: function(bloodPressureService) {
        return bloodPressureService.getData();
    }
};

angular.module('research').controller('BPController', BPController);
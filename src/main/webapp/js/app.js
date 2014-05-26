/**
 * Created by Evgenia on 5/24/2014.
 */

var researchApplication = angular.module('research', ['ngRoute']);

researchApplication.config(function ($routeProvider, $locationProvider) {
    $routeProvider
        .when ('/', {
            templateUrl: 'views/login.html',
            controller: 'LoginController'
        })
        .when('/bp', {
            templateUrl: 'views/bp.html',
            controller: 'BPController',
            resolve: BPController.resolve
        })
        .otherwise({
            redirectTo: '/'
}       );
});
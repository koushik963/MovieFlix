(function () {
    'use strict';

    angular.module('movieflix', ['ngRoute','ngAnimate', 'ui.bootstrap'])
        .config(mainConfig);

    function mainConfig($routeProvider) {

        $routeProvider
            .when('/movieflix/titles/', {
                templateUrl: 'views/titles.tmpl.html',
                controller: 'titleController',
                controllerAs: 'titleVm'

            })
            .when('/titles', {
                templateUrl: 'app/views/admin.tmpl.html',
                controller: 'titleController',
                controllerAs: 'titleVm'

            })
            .when('/user', {
                templateUrl: 'app/views/user.tmpl.html',
                controller: 'homeController',
                controllerAs: 'homeVm'
            })
            .otherwise({
                redirectTo: '/titles'
            })
    }


})();
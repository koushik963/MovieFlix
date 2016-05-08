(function(){
    'use strict';
    
    angular.module('movieflix', ['ngRoute'])
        .config(mainConfig);

    function mainConfig($routeProvider) {

        $routeProvider
            .when('/movieflix/titles/', {
                templateUrl: 'views/titles.tmpl.html',
                controller: 'titleController',
                controllerAs: 'titleVm'

            })
            .when('/movieflix/admin/create/', {
                templateUrl: 'views/create-title.tmpl.html',
                controller: 'titleController',
                controllerAs: 'titleVm'

            })
            .when('/titles',{
                templateUrl: 'app/views/admin-view-titles.tmpl.html',
                controller: 'titleController',
                controllerAs: 'titleVm'

            })
            .otherwise({
                redirectTo: '/titles'
            })


    }
    
    
})();
(function () {
    'use strict';

    angular.module('movieflix', ['ngResource', 'ngRoute', 'http-auth-interceptor', 'ngAnimate', 'ui.bootstrap'])
        .config(mainConfig)
        .run(runApp)
        .constant('USER_ROLES', {
            all: '*',
            admin: 'admin',
            user: 'user'
        });

    function mainConfig($routeProvider) {

        $routeProvider

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
            .when('/user/:id', {
                templateUrl: 'app/views/title-detail.tmpl.html',
                controller: 'titleDetailController',
                controllerAs: 'singleTitleVm'
            })
            .when('/login', {
                templateUrl: 'app/views/sign-in.tmpl.html',
                controller: 'loginController',
                controllerAs: 'loginVm',
                access: {
                    loginRequired: false,
                    authorizedRoles: '*'
                }

            })
            .otherwise({
                redirectTo: '/titles'
            })
    }

    function runApp($rootScope, $location, $http, authorizationService, session, USER_ROLES, $q,$timeout) {
        $rootScope.$on('$routeChangeStart', function (event, next) {
            if (next.originalPath === "/login" && $rootScope.authenticated) {
                event.preventDefault();
            } else if (next.access && next.access.loginRequired && !$rootScope.authenticated) {
                event.preventDefault();
                $rootScope.$broadcast("event:auth-loginRequired", {});
            } else if (next.access && !authorizationService.isAuthorized(next.access.authorizedRoles)) {
                event.preventDefault();
                $rootScope.$broadcast("event:auth-forbidden", {});
            }
        });
      /*  $rootScope.$on('$routeChangeSuccess', function (scope, next, current) {
            $rootScope.$evalAsync(function () {
               $.material.init();
            });
        });*/
        // Call when the the client is confirmed
        $rootScope.$on('event:auth-loginConfirmed', function (event, data) {
            console.log('login confirmed start ' + data);
            $rootScope.loadingAccount = false;
            var nextLocation = ($rootScope.requestedUrl ? $rootScope.requestedUrl : "/user");
            console.log('*******'+nextLocation);
            var delay = ($location.path() === "/loading" ? 1500 : 0);

            $timeout(function () {
                session.create(data);
                console.dir(data);
                $rootScope.account = session;
                $rootScope.authenticated = true;
                console.log($rootScope.authenticated);
                $location.path(nextLocation).replace();
            }, delay);

          /*  session.create(data);
            console.dir(session);
            $rootScope.account = session;
            $rootScope.authenticated = true;
            $location.path(nextLocation).replace();*/

        });
        // Call when the 401 response is returned by the server
        $rootScope.$on('event:auth-loginRequired', function (event, data) {
            console.log('inside login required event');
            if ($rootScope.loadingAccount && data.status !== 401) {
                $rootScope.requestedUrl = $location.path()
                $location.path('/loading');
            } else {
                console.log('inside else');
                session.inValidate();
                $rootScope.authenticated = false;
                $rootScope.loadingAccount = false;
                $location.path('/login');
            }
        });

        // Call when the user logs out
        $rootScope.$on('event:auth-loginCancelled', function () {
            $location.path('/login').replace();
        });
        // Get already authenticated user account
     //   authorizationService.getAccount();
    }
})();
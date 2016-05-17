(function () {
    'use strict';

    angular.module('movieflix', ['ngRoute', 'http-auth-interceptor', 'ngAnimate', 'ui.bootstrap'])
        .config(mainConfig)
        .run(runApp)

    function mainConfig($routeProvider) {

        $routeProvider

            .when('/titles', {
                templateUrl: 'app/views/admin.tmpl.html',
                controller: 'titleController',
                controllerAs: 'titleVm',
                access: {
                    loginRequired: true,
                    authorizedRoles: 'admin'
                }

            })
            .when('/user', {
                templateUrl: 'app/views/user.tmpl.html',
                controller: 'homeController',
                controllerAs: 'homeVm',
                access: {
                    loginRequired: true,
                    authorizedRoles: '*'
                }
            })
            .when('/user/:id', {
                templateUrl: 'app/views/title-detail.tmpl.html',
                controller: 'titleDetailController',
                controllerAs: 'singleTitleVm',
                access: {
                    loginRequired: true,
                    authorizedRoles: '*'
                }
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
            .when('/signup',{
                templateUrl: 'app/views/sign-up.tmpl.html',
                controller: 'userController',
                controllerAs: 'userVm',
                access: {
                    loginRequired: true,
                    authorizedRoles: '*'
                }
            })
            .when('/logout', {
                template: "<div>Logout</div>",
                controller: 'logoutController',
                access: {
                    loginRequired: false,
                    authorizedRoles: '*'
                }
            })
            .otherwise({
                redirectTo: '/titles'
            });
    }

    function runApp($rootScope, $location, $http, authorizationService, session, $q, $timeout) {
        $rootScope.$on('$routeChangeStart', function (event, next) {
            if (next.originalPath === "/login" && $rootScope.authenticated) {
                event.preventDefault();
            } else if (next.access && next.access.loginRequired && !$rootScope.authenticated) {
                event.preventDefault();
                $rootScope.$broadcast("event:auth-loginRequired", {});
            } else if (next.access && !authorizationService.isAuthorized(next.access.authorizedRoles)) {
                console.log('unauthorised');
                event.preventDefault();
                $rootScope.$broadcast("event:auth-forbidden", {});
            }
        });
        
        // Call when the the client is confirmed
        $rootScope.$on('event:auth-loginConfirmed', function (event, data) {
            console.log('login confirmed start ' + data);
            $rootScope.loadingAccount = false;
            var nextLocation = ($rootScope.requestedUrl ? $rootScope.requestedUrl : "/user");
            console.log('*******' + nextLocation);
            var delay = ($location.path() === "/loading" ? 1500 : 0);

            $timeout(function () {
                session.create(data);
                console.dir(data);
                $rootScope.account = session;
                $rootScope.authenticated = true;
                console.log($rootScope.authenticated);
                $location.path(nextLocation).replace();
            }, delay);

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
    }
})();
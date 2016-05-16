(function () {
    'use strict';

    angular.module('movieflix')
        .service('authorizationService', authorizationService);


    authorizationService.$inject = ['$rootScope', '$http', 'authService', 'session', '$q'];

    function authorizationService($rootScope, $http, authService, session) {

        var self = this;
        self.login = login;
        self.getAccount = getAccount;
        self.isAuthorized = isAuthorized;
        self.logout = logout;
        function login(userName, password) {
            console.log('inside log in method of auth service');
            var config = {
                params: {
                    username: userName,
                    password: password
                },
                ignoreAuthModule: 'ignoreAuthModule'
            };
         /*   $http.post('http://localhost:8080/movieflix/authenticate', '', config)
                .then(function (data) {
                    console.log('success login');
                    authService.loginConfirmed(data);
                }, function () {
                    console.log('error login');
                    $rootScope.authenticationError = true;
                    session.inValidate();
                });*/

            $http.post('http://localhost:8080/movieflix/authenticate', '', config)
                .success(function (data, status, headers, config) {
                    authService.loginConfirmed(data);
                }).error(function (data, status, headers, config) {
                $rootScope.authenticationError = true;
                session.inValidate();
            });
        };

        function getAccount() {
            $rootScope.loadingAccount = true;
            $http.get('http://localhost:8080/movieflix/security/account')
                .then(function (response) {
                    authService.loginConfirmed(response.data);
                });
        };
        
        function isAuthorized(authorizedRoles) {
            if (!angular.isArray(authorizedRoles)) {
                if (authorizedRoles == '*') {
                    return true;
                }
                authorizedRoles = [authorizedRoles];
            }
            var isAuthorized = false;
            angular.forEach(authorizedRoles, function (authorizedRole) {
                var authorized = (!!session.login &&
                session.userRoles.indexOf(authorizedRole) !== -1);
                if (authorized || authorizedRole == '*') {
                    isAuthorized = true;
                }
            });
            return isAuthorized;
        };
        function logout() {
            $rootScope.authenticationError = false;
            $rootScope.authenticated = false;
            $rootScope.account = null;
            $http.get('logout');
            session.invalidate();
            authService.loginCancelled();
        };
    }

})();
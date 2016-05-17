(function () {
    'use strict';

    angular.module('movieflix')
        .service('authorizationService', authorizationService);


    authorizationService.$inject = ['$rootScope', '$http', 'authService', 'session', 'CONFIG'];

    function authorizationService($rootScope, $http, authService, session, CONFIG) {

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
            console.log(CONFIG.API_HOST);
            $http.post(CONFIG.API_HOST + '/authenticate', '', config)
                .success(function (data, status, headers, config) {
                    authService.loginConfirmed(data);
                }).error(function (data, status, headers, config) {
                $rootScope.authenticationError = true;
                session.inValidate();
            });
        };

        function getAccount() {
            $rootScope.loadingAccount = true;
            $http.get(CONFIG.API_HOST + '/security/account')
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
            console.log('in auth service log out method');
            $rootScope.authenticationError = false;
            $rootScope.authenticated = false;
            $rootScope.account = null;
            $http.get(CONFIG.API_HOST + '/logout')
                .success(function (data) {
                    console.log('logout success');
                }).error(function () {
                console.log('logout failure');
            });
            session.inValidate();
            authService.loginCancelled();
        };
    }

})();
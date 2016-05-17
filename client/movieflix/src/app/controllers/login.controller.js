(function () {
    'use strict';
    angular.module('movieflix')
        .controller('loginController', loginController);
    loginController.$inject = ['$rootScope', 'authorizationService'];
    function loginController($rootScope, authorizationService) {
        var self = this;
        self.username="";
        self.password="";
        console.log('inside login controller');
        self.login = function () {
            console.log('inside log in login in controller');
            $rootScope.authenticationError = false;
            console.log('attempting to login with details: '+self.username+self.password);
            authorizationService.login(self.username, self.password);
               /* .then(function (response) {
                    console.log('success' + response);
                }, function (errResponse) {
                    console.log('error login' + errResponse);
                });*/
        };
    }
})();
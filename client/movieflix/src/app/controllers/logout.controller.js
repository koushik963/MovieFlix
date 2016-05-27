(function () {
    'use strict';
    
    angular.module('movieflix')
        .controller('logoutController', logoutController);

    logoutController.$inject = ['authorizationService'];
    function logoutController(authorizationService) {
        console.log('inside log out controller before log out');
        var self = this;
        self.logout = function () {
            console.log('inside logout controller');
            authorizationService.logout();
//                .then(function (response) {
//                    console.log('success' + response);
//                }, function (errResponse) {
//                    console.log('error in logout' + errResponse);
//                });
        };
        self.logout();
    }
})();
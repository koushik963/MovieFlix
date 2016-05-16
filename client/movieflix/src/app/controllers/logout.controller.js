(function () {
    'use strict';
    angular.module('movieflix')
        .controller('logoutController', logoutController);
    logoutController.$inject = ['authorizationService'];
    function logoutController(authorizationService) {
        var self = this;
        self.logout = function () {
            authorizationService.logout()
                .then(function (reponse) {
                    console.log('success' + response);
                }, function (errResponse) {
                    console.log('error in logout' + errResponse)
                });
        }
    }
})();
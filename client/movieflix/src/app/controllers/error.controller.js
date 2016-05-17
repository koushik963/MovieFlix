(function () {
    'use strict';

    angular.module('movieflix')
        .controller('errorController',errorController);
    errorController.$inject=['$routeParams'];

    function errorController($routeParams) {

        var self=this;
        self.code = $routeParams.code;

        switch (self.code) {
            case "403" :
                self.message = "Oops! you have come to unauthorised page."
                break;
            case "404" :
                self.message = "Page not found."
                break;
            default:
                self.code = 500;
                $scope.message = "Oops! unexpected error"
        }
    }
})();
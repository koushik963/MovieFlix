(function () {
    'use strict';

    angular.module('movieflix')
        .controller('userController', userController);

    userController.$inject = ['userService','$scope','$location'];

    function userController(userService,$scope,$location) {
        var self = this;

        self.signUp = signUp;

        function signUp() {
            userService.signUp(self.user)
                .then(function (response) {
                    console.log('succes sign up');
                    $location.path('/login').replace();
                }, function (errResponse) {
                    console.log('error signing up');
                })
        };
       

        function reset() {
            self.user = {};
            $scope.myForm.$setPristine(); //reset Form
        };
    }
})();
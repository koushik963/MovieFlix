(function () {
    'use strict';

    angular.module('movieflix')
        .service('userService', userService);

    userService.$inject = ['$http', '$q'];
    function userService($http, $q) {
        var self = this;
        self.signUp = signUp;
        
        function signUp(user) {
           return $http.post('http://localhost:8080/movieflix/users',user)
                .then(successFn,errorFn);
        }
        function successFn(response) {
            return response.data;
        }

        function errorFn(errResponse) {
            console.log('error getting data');
            return $q.reject(errResponse.statusText);
        }
    }
})();
(function () {
    'use strict';

    angular.module('movieflix')
        .service('userService', userService);

    userService.$inject = ['$http', '$q','CONFIG'];
    function userService($http, $q,CONFIG) {
        var self = this;
        self.signUp = signUp;
        
        function signUp(user) {
           return $http.post(CONFIG.API_HOST+'/users',user)
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
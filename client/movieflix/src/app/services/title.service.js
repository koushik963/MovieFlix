(function () {
    'use strict';
    angular.module('movieflix')
        .service('titleService', titleService);

    titleService.$inject = ['$http', '$q'];

    function titleService($http, $q) {
        var self = this;
        self.createTitle = createTitle;
        self.fetchAllTitles = fetchAllTitles;
        self.updateTitle = updateTitle;
        self.deleteTitle = deleteTitle;

        function createTitle(title) {
            return $http.post('http://localhost:8080/movieflix/titles/admin/create/', title)
                .then(successFn, errorFn);
        }

        function fetchAllTitles() {
            console.log('inside title service');
            return $http.get('http://localhost:63342/movieflix/src/hello.json')
                .then(successFn, errorFn);
        }

        function updateTitle(title, id) {
            return $http.post('http://localhost:8080/movieflix/titles/admin/update/' + id, title)
                .then(successFn, errorFn);
        }

        function deleteTitle(id) {
            return $http.post('http://localhost:8080/movieflix/titles/admin/delete/' + id)
                .then(successFn, errorFn);
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

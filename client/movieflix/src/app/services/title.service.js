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

        function createTitle() {
            return $http.post('/movieflix/admin/create/', title)
                .then(successFn, errorFn);
        }

        function fetchAllTitles() {
            return $http.get('http://localhost:8080/movieflix/titles')
                .then(successFn, errorFn);
        }

        function updateTitle(title, id) {
            return $http.post('/movieflix/admin/title/update/' + id, title)
                .then(successFn, errorFn);
        }

        function deleteTitle(id) {
            return $http.post('/movieflix/admin/title/delete/' + id)
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

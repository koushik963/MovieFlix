(function () {
    'use strict';
    angular.module('movieflix')
        .service('titleService', titleService);

    titleService.$inject = ['$http', '$q'];

    function titleService($http, $q) {
        var self = this;
        self.createTitle = createTitle;
        self.fetchAllTitles = fetchAllTitles;
        self.fetchTitleById = fetchTitleById;
        self.updateTitle = updateTitle;
        self.deleteTitle = deleteTitle;
        self.postComment = postComment;
        self.rate = rate;

        function createTitle(title) {
            return $http.post('http://localhost:8080/movieflix/titles/admin/create/', title)
                .then(successFn, errorFn);
        }

        function fetchAllTitles() {
            console.log('inside title service');
            return $http.get('http://localhost:8080/movieflix/titles/')
                .then(successFn, errorFn);
        }

        function postComment(comment) {
            $http.post('http://localhost:8080/movieflix/rating/' + userId + '/' + titleId + '?comment=' + comment)
                .then(successFn, errorFn);
        }

        function fetchTitleById(id) {
            return $http.get('http://localhost:8080/movieflix/titles/' + id)
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

        function rate(userId, titleId, rating) {
            $http.post('http://localhost:8080/movieflix/rating/' + userId + '/' + titleId + '?rate=' + rating)
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

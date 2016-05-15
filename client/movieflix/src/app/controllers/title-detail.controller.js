(function () {
    'use strict';
    angular.module('movieflix')
        .controller('titleDetailController', titleDetailController);

    titleDetailController.$inject = ['$scope', 'titleService', '$routeParams'];
    function titleDetailController($scope, titleService, $routeParams) {
        var self = this;
        self.rate;
        self.userComment;

        self.fetchTitleById = function () {
            titleService.fetchTitleById($routeParams.id)
                .then(function (response) {
                    self.selectedTitle = response;
                }, function (errResponse) {
                    console.error('Error Retreiving details of Single Title' + $routeParams)
                });
        };
        self.fetchTitleById();

        self.postComment = function () {
            //need to update with user id
            titleService.postComment(1, selectedTitle.id, userComment)
                .then(function (response) {
                    console.log('posted comment succesfully');
                }, function (errResponse) {
                    console.error('error posting comment.');
                });
        };

        self.postRating = function () {
            titleService.rate(1, selectedTitle.id, rate)
                .then(function (response) {

                }, function (errResponse) {

                });
        }


    }

})();
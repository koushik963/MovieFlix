(function () {
    'use strict';
    angular.module('movieflix')
        .controller('titleDetailController', titleDetailController);

    titleDetailController.$inject = ['$rootScope', 'titleService', '$routeParams', 'Notification', '$scope'];
    function titleDetailController($rootScope, titleService, $routeParams, Notification, $scope) {
        var self = this;
        self.rate;
        self.userComment;
        self.selectedTitle = [];

        self.fetchTitleById = function () {
            titleService.fetchTitleById($routeParams.id)
                .then(function (response) {
                    self.selectedTitle = response;
                    console.log('original rate' + self.originalRate);
                    self.getAvgRating();
                    self.setYourRating();
                }, function (errResponse) {
                    console.error('Error Retreiving details of Single Title' + $routeParams)
                });
        };
        self.fetchTitleById();

        self.postComment = function () {
            //need to update with user id
            console.log($rootScope.account.id);
            titleService.postComment($rootScope.account.id, self.selectedTitle.id, self.userComment)
                .then(function (response) {
                    console.log('posted comment succesfully');
                    Notification.success('Posted Comment Succesfully!!!');
                    self.userComment='';
                    self.fetchTitleById();
                }, function (errResponse) {
                    console.error('error posting comment.');
                });
        };

        self.postRating = function () {
            titleService.rate(1, self.selectedTitle.id, rate)
                .then(function (response) {

                }, function (errResponse) {

                });
        }

        self.getAvgRating = function () {
            titleService.getAvgRating(self.selectedTitle.id)
                .then(function (response) {
                    self.avgRating = response;
                }, function (errResponse) {
                    console.log('error fetching avg rrating');
                });
        }

        self.setYourRating = function () {
            for (var i = 0; i < self.selectedTitle.userRatingCollection.length; i++) {
                if (self.selectedTitle.userRatingCollection[i].userRatingPK.userId == $rootScope.account.id) {
                    self.yourRating = self.selectedTitle.userRatingCollection[i].rating;
                    self.originalRate = self.yourRating;
                }
            }
        }

        $scope.$watch(
            function () {
                return self.yourRating;
            },
            function handleRateChange(newValue, oldValue) {
                if (newValue !== undefined && newValue !== self.originalRate) {
                    console.log("new rate value", newValue);
                    titleService.postRate($rootScope.account.id, self.selectedTitle.id, newValue)
                        .then(function (response) {
                            console.log('rate posted');
                            self.fetchTitleById();
                            Notification.success('Posted Rating Succesfully!!!');
                        }, function (errResponse) {
                            console.log('error posting rate');
                        })
                }

            }
        );

    }

})();
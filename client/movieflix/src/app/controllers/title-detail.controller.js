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

                    self.originalRate=response.imdbrating;
                    console.log('original rate'+self.originalRate);
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

        $scope.$watch(
            function () {
                return self.selectedTitle.imdbrating;
            },
            function handleRateChange(newValue, oldValue) {
                if(newValue!==undefined && newValue!==self.originalRate){
                    console.log("new rate value", newValue);
                    titleService.postRate($rootScope.account.id,self.selectedTitle.id,newValue)
                        .then(function(response){
                            console.log('rate posted');
                            Notification.success('Posted Rating Succesfully!!!');
                        },function (errResponse) {
                            console.log('error posting rate');
                        })
                }

            }
        );

    }

})();
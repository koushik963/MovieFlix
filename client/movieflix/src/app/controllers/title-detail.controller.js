(function () {
    'use strict';
    angular.module('movieflix')
        .controller('titleDetailController', titleDetailController);

    titleDetailController.$inject = ['$scope', 'titleService', '$routeParams'];
    function titleDetailController($scope, titleService, $routeParams) {
        var self = this;
self.rate=10;

        self.updatedTitle;
        self.fetchTitleById = function () {
            titleService.fetchTitleById($routeParams.id)
                .then(function (response) {
                    self.selectedTitle = response;
                }, function (errResponse) {
                    console.error('Error Retreiving details of Single Title' + $routeParams)
                });
        };
        self.fetchTitleById();
        self.display = function () {

            self.updatedTitle = self.selectedTitle;
            console.log(self.selectedTitle);
            console.log(self.selectedTitle.id);
        }

        $scope.$watch('selectedTitle', function (newvalue) {
            if (newvalue) {
                self.display();
            }
        });
    }

})();
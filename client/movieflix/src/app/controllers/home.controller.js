(function () {
    'use strict';

    angular.module('movieflix')
        .controller('homeController', homeController)
        .directive('titleDirective',titleDirective);

    homeController.$inject = ['$scope', '$filter', 'titleService'];

    function homeController($scope, $filter, titleService) {
        var self = this;
        self.title = {};
        self.titles = [];
        self.currentPage = 1;
        self.pageSize = 10;
        self.userComment = "";
        self.rateValue = "";
        self.user = {};
        self.searchString = "";
        self.titles = [];
        self.searchTitles = [];
//----------- rating variables---------------
        self.max=10;
        self.rate=5;
        self.isReadOnly=false;

        self.hoveringOver = function(value) {
            self.overStar = value;
        };
        //--------------rating variables end-------------------
        self.fetchAllTitles = function () {
            titleService.fetchAllTitles()
                .then(function (response) {
                    console.dir(response);
                    self.titles = response;
                }, function (errResponse) {
                    console.error('Error Fetching Titles')
                });
        };
        self.fetchAllTitles();

       

    }


    function titleDirective(){
        console.log('inside directive');
        var dir ={
            scope:{
                person: '='
            },
            templateUrl:'app/views/title-directive.html'
        }
        return dir;
    }
})();
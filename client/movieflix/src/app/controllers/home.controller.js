(function () {
    'use strict';

    angular.module('movieflix')
        .controller('homeController', homeController)
        .directive('titleDirective', titleDirective);

    homeController.$inject = ['$scope', '$filter', 'titleService', '$window'];

    function homeController($scope, $filter, titleService, $window) {
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
        self.rateProperties = {};
        self.changeSort = changeSort;
        self.sortReverse = false;
        self.totalItems = "";
        self.itemsPerPage = 12;
        self.maxSize = 5;

        self.getNoOfComments = getNoOfComments;
        self.onPageChange = onPageChange;


        function changeSort(order) {
            self.sortReverse = !self.sortReverse;
            if (order == '0') {
                self.titles = $filter('orderBy')(self.titles, 'Year', self.sortReverse);
            } else if (order == '1') {
                self.titles = $filter('orderBy')(self.titles, 'imdbVotes', self.sortReverse);
            } else if (order == '2') {
                self.titles = $filter('orderBy')(self.titles, 'imdbRating', self.sortReverse);
            } else if (order == '3') {
                var temp = [];
                for (var j = 0; j < self.titles.length; j++) {
                    if (self.titles[j].type == 'Romance') {
                        temp.push(self.titles[j]);
                    }
                }
                self.titles = $filter('orderBy')(temp, 'type', self.sortReverse);
            } else if (order == '4') {
                var temp = [];
                for (var j = 0; j < self.titles.length; j++) {
                    if (self.titles[j].type == 'Movie') {
                        temp.push(self.titles[j]);
                    }
                }
                self.titles = $filter('orderBy')(temp, 'type', self.sortReverse);
            }
        }

        function onPageChange() {

            console.log('page chaging');
            $window.scrollTo(0, 0);
        }

        self.hoveringOver = function (value) {
            self.overStar = value;
        };
        self.setrateprop = function () {
            self.rateProperties = {
                max: 10,
                rate: 6.4,
                isReadOnly: true
            };
        }
        //--------------rating variables end-------------------
        self.fetchAllTitles = function () {
            titleService.fetchAllTitles()
                .then(function (response) {
                    console.dir(response);
                    self.titles = response;
                    self.totalItems = response.length;
                }, function (errResponse) {
                    console.error('Error Fetching Titles')
                });
        };
        self.fetchAllTitles();

        function getNoOfComments(id) {
            console.log('inside get number of comments method');
          //  self.fetchAllTitles();
            var count = 0;
            for (var i = 0; i < self.titles.length; i++) {
                if (self.titles[i].id == id) {
                    for (var j = 0; j < self.titles[i].userRatingCollection.length; j++) {
                        if (self.titles[i].userRatingCollection[j].comment != null) {
                            count++;
                        }
                    }
                }
            }
            console.log('count--- ' + count);
            return count;
        }
    }



    function titleDirective() {
        console.log('inside directive');
        var dir = {
            scope: {
                title: '=',
                number:'='
            },
            templateUrl: 'app/views/title-directive.html'
        }
        return dir;
    }
})();
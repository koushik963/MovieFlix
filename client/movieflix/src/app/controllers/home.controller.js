(function () {
    'use strict';

    angular.module('movieflix')
        .controller('homeController', homeController)
        .directive('titleDirective', titleDirective);

    homeController.$inject = ['$scope', '$filter', 'titleService', '$window'];

    function homeController($scope, $filter, titleService, $window) {
        var self = this;
        /*  self.title = {};
         self.titles = [];*/
        self.currentPage = 1;
        self.pageSize = 10;
        /* self.userComment = "";
         self.rateValue = "";
         self.user = {};
         self.searchString = "";
         self.titles = [];
         self.searchTitles = [];
         self.rateProperties = {};*/
        self.changeSort = changeSort;
        self.sortReverse = false;
        //   self.totalItems = "";
        self.itemsPerPage = 4;
        self.maxSize = 5;

        self.getNoOfComments = getNoOfComments;
        self.onPageChange = onPageChange;
        self.refresh = refresh;

        function refresh() {
            self.titles = self.originalTitles;
            self.totalItems=self.originalTitles.length;
        }

        function changeSort(order) {
            self.sortReverse = !self.sortReverse;
            if (order == '0') {
                self.titles = $filter('orderBy')(self.titles, 'year', self.sortReverse);
            } else if (order == '1') {
                self.titles = $filter('orderBy')(self.titles, 'imdbvotes', self.sortReverse);
            } else if (order == '2') {
                self.titles = $filter('orderBy')(self.titles, 'imdbrating', self.sortReverse);
            } else if (order == '3') {
                //   self.titles=self.fetchAllTitles();
                var temp = [];
                for (var j = 0; j < self.originalTitles.length; j++) {
                    if (self.originalTitles[j].type == 'tv' || self.originalTitles[j].type == 'TV') {
                        temp.push(self.originalTitles[j]);
                    }
                }
                // self.titles=self.fetchAllTitles();
                self.totalItems = temp.length;
                self.titles = $filter('orderBy')(temp, 'imdbrating', true);
            } else if (order == '4') {
                //     self.titles=self.fetchAllTitles();
                var temp = [];
                for (var j = 0; j < self.originalTitles.length; j++) {
                    if (self.originalTitles[j].type == 'movie' || self.originalTitles[j].type == 'Movie') {
                        temp.push(self.originalTitles[j]);
                    }
                }
                //    self.titles=self.fetchAllTitles();
                self.totalItems = temp.length;
                self.titles = $filter('orderBy')(temp, 'imdbrating', true);
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
                    self.originalTitles = response;
                }, function (errResponse) {
                    console.error('Error Fetching Titles')
                });
        };
        self.fetchAllTitles();

        function getNoOfComments(id) {
            // console.log('inside get number of comments method');
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
                number: '='
            },
            templateUrl: 'app/views/title-directive.html'
        }
        return dir;
    }
})();
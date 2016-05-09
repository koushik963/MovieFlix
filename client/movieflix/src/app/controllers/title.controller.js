(function () {
    'use strict';
    angular.module('movieflix')
        .controller('titleController', titleController);

    titleController.$inject = ['$scope', 'titleService','$location','$anchorScroll'];
    function titleController($scope, titleService,$location,$anchorScroll) {
        var self = this;

        self.submit = submit;
        self.edit = edit;
        self.remove = remove;
        self.reset = reset;
        init();
        function init() {

        }
        self.fetchAllTitles = function () {
            titleService.fetchAllTitles()
                .then(function (response) {
                    console.dir(response);
                    self.titles = response;
                }, function (errResponse) {
                    console.error('Error Fetching Titles')
                });
        };
        self.createTitle = function (title) {
            titleService.createTitle(title)
                .then(
                    self.fetchAllTitles,
                    function (errResponse) {
                        console.error('Error while creating Titles.');
                    }
                );
        };

        self.deleteTitle = function(id) {
            titleService.deleteTitle(id)
                .then(
                    self.fetchAllTitles,
                    function(errResponse) {
                        console.error('Error while deleting Title.');
                    }
                );
        };

        self.updateTitle = function(title, id){
            titleService.updateTitle(title, id)
                .then(
                    self.fetchAllTitles,
                    function(errResponse){
                        console.error('Error while updating Title.');
                    }
                );
        };

        function submit() {
            if (self.title.id == null) {
                console.log('Saving New Title', self.title);
                self.createTitle(self.title);
            } else {
                self.updateTitle(self.title, self.title.id);
                console.log('title updated with id ', self.title.id);
            }
            self.reset();
        };

        function edit(id) {
            console.log('id to be edited', id);
            $location.hash('top');
            $anchorScroll();
            for (var i = 0; i < self.titles.length; i++) {
                if (self.titles[i].id == id) {
                    self.title = angular.copy(self.titles[i]);
                    break;
                }
            }
        };

        function remove(id) {
            console.log('id to be deleted', id);
            self.deleteTitle(id);

        };
        function reset() {
            self.title = {};
            $scope.myForm.$setPristine(); //reset Form
        };
        self.fetchAllTitles();
    }


})();
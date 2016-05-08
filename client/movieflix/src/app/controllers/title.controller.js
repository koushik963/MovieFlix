(function () {
    'use strict';
    angular.module('movieflix')
        .controller('titleController', titleController);


    titleController.$inject = ['titleService'];
    function titleController(titleService) {
        var self = this;

        init();
        function init() {
            titleService.createTitle(title)
                .then(
                    function (response) {
                        self.fetchAllTitles;
                    },
                    function (errResponse) {
                        console.error('Error while creating Titles.');
                    }
                );
            titleService.fetchAllTitles()
                .then(function (response) {
                    console.dir(response);
                    self.titles = response;
                }, function (errResponse) {
                    console.error('Error Fetching Titles')
                });
        }
    }

})();
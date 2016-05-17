(function () {
    'use strict';
    angular.module('movieflix')
        .service('session', session);
    session.$inject = [];
    function session() {
        var self = this;
        self.create = create;
        self.inValidate = inValidate;
        function create(data) {
            self.id = data.id;
            self.firstName = data.firstname;
            self.lastName = data.lastname;
            self.email = data.email;
            self.userRoles = [];
            for (var i = 0; i < data.authorities.length; i++) {
                self.userRoles.push(data.authorities[i].name);
            }
        };
        function inValidate() {
            self.id = null;
            self.login = null;
            self.firstName = null;
            self.lastName = null;
            self.email = null;
            self.userRoles = null;
        };
    }
})();
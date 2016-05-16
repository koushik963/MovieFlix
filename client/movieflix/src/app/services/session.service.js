(function () {
    'use strict';
    angular.module('movieflix')
        .service('session', session);
    session.$inject = ['$resource'];
    function session($resource) {
        var self = this;
        self.create = create;
        self.inValidate = inValidate;
        function create(data) {
            console.log('inside session service');
            console.dir(data);
            /*  data.query(function (value) {
             self.id = value.id;
             console.log('id value: ' + value.id);
             console.log(Object.keys(value));
             self.firstName = value.firstName;
             self.lastName = value.lastName;
             self.email = value.email;
             self.userRoles = [];
             });*/
            self.id = data.id;
            console.log('id value: ' + data.id);
            self.firstName = data.firstname;
            self.lastName = data.lastname;
            self.email = data.email;
            self.userRoles = [];
          /*  angular.forEach(data.authorities, function (value, key) {
                self.push(value.name);
            }, self.userRoles);*/
            /*for(var i=0;i<data.authorities.length;i++){
                self.userRoles.push(data.authorities[i]);
            }*/

            for(var i=0;i<data.authorities.length;i++){
                self.userRoles.push(data.authorities[i].name);
            }
            console.log('***********');
            console.dir(self);

            console.log('***********');
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
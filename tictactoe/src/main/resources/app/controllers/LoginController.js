(function (define) {

    'use strict';

    define([],
        function () {
            var LoginController = function ($scope, $http) {
                $scope.onNameEnter = function () {
                    $http.post('/authenticate', $scope.name)
                        .then(function (data) {
                            if (data.status == 200) {

                            }
                        })
                }
            };
            return ['$scope', '$http', LoginController];
        }
    );
})(define);
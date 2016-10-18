'use strict';

angular.module('sncard').controller('LinkedinConnectionController',
    function ($scope, $state) {
        $scope.message = 'Loading ...';
        var init = function () {
            console.log('LinkedinConnectionController initialized');
        };
        init();
    }).controller('LinkedinProfileController',
    function ($scope, $state, profileRs) {
        $scope.loadProfile = function () {
            if (profileRs != null) {
                profileRs.$promise.then(function success(response) {
                    $scope.profile = response;
                    $scope.message = '';
                }, function error(error) {
                    $scope.message = 'Couldn\'t able to fetch profile :(';
                    console.log(error);
                });
            }
        };

        var init = function () {
            console.log('LinkedinProfileController initialized');
            $scope.loadProfile();
        };
        init();
    });
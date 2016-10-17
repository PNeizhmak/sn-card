'use strict';

angular.module('sncard')
    .config(function ($stateProvider) {
        $stateProvider
            .state('home', {
                parent: 'site',
                url: '/',
                data: {},
                views: {
                    'content@': {
                        templateUrl: 'app/main/main.html',
                        controller: 'MainController'
                    }
                }
            });
    });
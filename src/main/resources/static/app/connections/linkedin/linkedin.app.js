'use strict';

angular.module('sncard')
    .config(function ($stateProvider) {
        $stateProvider
            .state('connections.linkedin.manage', {
                parent: 'connections.linkedin',
                url: '/manage',
                data: {},
                views: {
                    'content@': {
                        templateUrl: 'app/connections/linkedin/linkedinManager.html',
                        controller: 'LinkedinConnectionController as lcc'
                    }
                }
            })
            .state('connections.linkedin.manage.profile', {
                parent: 'connections.linkedin.manage',
                url: '/profile',
                templateUrl: 'app/connections/linkedin/profile.html',
                controller: 'LinkedinProfileController',
                resolve: {
                    profileRs: function (Linkedin) {
                        return Linkedin.fetchProfile();
                    }
                }
            });
    });
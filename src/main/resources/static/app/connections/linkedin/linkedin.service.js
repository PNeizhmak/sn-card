'use strict';

angular.module('sncard')
    .factory('Linkedin', function ($resource) {
        return $resource('/api/connect/linkedin', {}, {
            'fetchProfile': {url: 'api/connect/linkedin/profile', method: 'GET'}
        });
    });
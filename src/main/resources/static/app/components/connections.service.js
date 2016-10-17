'use strict';

angular.module('sncard')
    .factory('Connections', function ($resource) {
        return $resource('api/connect', {}, {});
    });
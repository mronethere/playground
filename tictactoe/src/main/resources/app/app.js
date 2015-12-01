(function (define, angular) {

    'use strict';

    define([
            'controllers/LoginController',
            'controllers/GameController'
        ], function (LoginController, GameController) {
            var appName = 'tictactoe';
            angular
                .module(appName, ['ui.router', 'ui.bootstrap'])
                .controller('LoginController', LoginController)
                .controller('GameController', GameController)
                .config(function ($stateProvider, $urlRouterProvider) {
                    $urlRouterProvider.otherwise('/login');

                    $stateProvider
                        .state('login', {
                            url: '/login',
                            templateUrl: '/app/views/login.view.html'
                        })
                        .state('game', {
                            url: '/game',
                            templateUrl: '/app/views/game.view.html'
                        });
                });
            return appName;
        }
    );
})(define, angular);
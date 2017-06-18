/* JavaScript to initialize RequireJS AMD modules.
    
    Created by: Andrew
    Created on: 6.17.17
*/

//configure access to external libraries and AMD modules
requirejs.config({
    baseUrl: 'assets/javascripts',
    paths: {
        'jquery': 'lib/jquery-3.2.1.slim.min',
        'plotly': 'lib/plotly-1.27.1.min',
        'histogramRenderer' : 'histogramRenderer',
        'chartInit' : 'chartInit',
        'fixChosenMenus' : 'fixChosenMenus',
        'chartAjax' : 'chartAjax'
    },
});

//load modules with onLoad behavior
require(['chartInit', 'fixChosenMenus', 'chartAjax'], function(chartInit, fixChosenMenus, chartAjax){
	console.log("Finished onload scripts");
});

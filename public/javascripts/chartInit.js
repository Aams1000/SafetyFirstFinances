/* JavaScript for rendering histogram on load. Histogram object is Histogram::toJson.
    
    Created by: Andrew
    Created on: 6.18.17
*/

define(['jquery', 'histogramRenderer', 'chartAjax'], function($, histogramRenderer){

  function initChartOnLoad() {
    const chartData = JSON.parse(document.getElementById('initialChartData').getAttribute('data-value').replace(/&quot;/g,'"'));
    const chartHolderDiv = document.getElementById('defaultHistogram');
    $(document).ready(function() {
      histogramRenderer.renderHistogram(chartData, chartHolderDiv);
      console.log("Loaded histogram");
    });
  }

  //initial load
  initChartOnLoad(histogramRenderer);
});

  


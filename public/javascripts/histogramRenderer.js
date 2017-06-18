/* JavaScript for histogram-specific operations (Plotly calls these "bar" charts). Histogram
    object is Histogram::toJson.
    
    Created by: Andrew
    Created on: 5.28.17
*/

define(['plotly', 'jquery'], function(Plotly, $){

  function renderHistogram(histogram, div) {
    const labels = [];
    const dataValues = [];
    for (var i = 0; i < histogram.labelsAndDataValues.length; i++){
      labels.push(histogram.labelsAndDataValues[i].label);
      dataValues.push(histogram.labelsAndDataValues[i].value);
    }

    const layout = {
      title: histogram.titlesAndAxesLabels.title,
      xaxis: {
        title: histogram.titlesAndAxesLabels.x
      },
      yaxis: {
        title: histogram.titlesAndAxesLabels.y
      }
    };
    const data = [{
      x: labels,
      y: dataValues,
      type: 'bar'
    }];
    Plotly.plot(div, data, layout);
  }

  return {renderHistogram: renderHistogram};
});

  


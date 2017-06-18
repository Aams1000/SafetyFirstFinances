/* JavaScript for sending and receiving chart AJAX requests.
    
    Created by: Andrew
    Created on: 6.17.17
*/

define(['jquery'], function($){

  // const ID_AJAX_BUTTON = "viewSpendingButton";
  // const ID_CHART_AREA = "chartArea";
  // const ID_YEAR_SELECT = "yearSelect";
  // const ID_MONTH_SELECT = "monthSelect";
  // const ID_CATEGORY_SELECT = "categorySelect";

  const HEADER_AREA_IDS = Object.freeze({
    "ID_AJAX_BUTTON" : "viewSpendingButton",
    "ID_CHART_AREA" : "chartArea",
    "ID_YEAR_SELECT" : "yearSelect",
    "ID_MONTH_SELECT" : "monthSelect",
    "ID_CATEGORY_SELECT" : "categorySelect"
  });

  const BUTTON_CLICK_EVENT = "click";


  function ajaxLoadChartFromDropdowns() {
    console.log("In ajax function");
  }

  function setAjaxButtonEventListener() {
    console.log("Setting ajax button event listener...");
    const ajaxButton = document.getElementById(HEADER_AREA_IDS.ID_AJAX_BUTTON);
    ajaxButton.addEventListener(BUTTON_CLICK_EVENT, ajaxLoadChartFromDropdowns);
    console.log("Finished setting ajax button event listener");
  }

  //init event listener on load
  $(document).ready(setAjaxButtonEventListener());

  return {
    HeaderAreaIds : HEADER_AREA_IDS,
    ajaxLoadChartFromDropdowns : ajaxLoadChartFromDropdowns
  };

});



/* JavaScript for fixing Chosen's multiselect dropdown menu stylings. These are modified by Chosen
  by putting style="" attributes on tags, so this goes in and modifies some of that.
    
    Created by: Andrew
    Created on: 5.28.17
*/

define(['jquery'], function($){

  function fixMultiSelectStyling() {
    const selectAreas = document.getElementsByClassName("chosen-container chosen-container-multi");
    if (selectAreas) {
      for (var i = 0; i < selectAreas.length; i++) {
        selectAreas[i].setAttribute("style", "width: 300;");
      }
    }
  }

  function fixMenusOnInit() {
      console.log("Fixing multiselect styling...");
      fixMultiSelectStyling();
      console.log("Finished fixing multiselect styling");
  }

  //fix Chosen stylings on pageload
  $(document).ready(fixMenusOnInit());

});

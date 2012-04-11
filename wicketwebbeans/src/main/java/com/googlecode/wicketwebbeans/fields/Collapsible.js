  function collapsibleBeanTableToggle(barElement)
  {
    var parentElements = barElement.parentNode.childNodes;
    var element;
    // Find self in parent and get next node with className of collapsibleBean
    var foundSelf = false;
    for (var i = 0; i < parentElements.length; i++) {
      if (parentElements[i] == barElement) {
        foundSelf = true;
      }
      else if (foundSelf && parentElements[i].className == "beanInCollapsibleField") {
        element = parentElements[i];
      }
    }
    
    if (!element) {
      return;
    }
    
    var expandButton = barElement.childNodes[0];
    var collapseButton = barElement.childNodes[1];
    var currDisplayStyle;
    // Get the computed display style, which is initially set by CSS.
    if (element.currentStyle) {
      currDisplayStyle = element.currentStyle.display; // IE
    }
    else {
      currDisplayStyle = document.defaultView.getComputedStyle(element, null).display;
    }
    
    if (currDisplayStyle == "none") {
      element.style.display="block";
      expandButton.style.display = "none";
      collapseButton.style.display = "block";
    }
    else {
      element.style.display="none";
      collapseButton.style.display = "none";
      expandButton.style.display = "block";
    }
  }

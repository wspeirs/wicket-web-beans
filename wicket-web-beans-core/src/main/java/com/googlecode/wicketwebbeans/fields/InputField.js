  function inputField_HandleEnter(field, event) {
      var blah;
    var keyCode = (event.keyCode ? event.keyCode : (event.which ? event.which : event.charCode));
    if (keyCode == 13) {
      var i;
      for (i = 0; i < field.form.elements.length && field != field.form.elements[i]; i++) {
      }

      i = (i + 1) % field.form.elements.length;
      field.form.elements[i].focus();
      return false;
    } 

    return true;
  }

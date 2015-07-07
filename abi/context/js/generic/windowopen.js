function window_open(theURL,winName,features) {
  winName = window.open(theURL,winName,features);
  winName.window.focus();
}
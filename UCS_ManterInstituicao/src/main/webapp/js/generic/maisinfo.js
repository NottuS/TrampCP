/*
Autor: Carlos H. Roland
*/

document.write('<div id="box_info"></div>');

ns = document.layers;
ie = document.all;
ns6 = (document.getElementById && !document.all);
box = document.getElementById('box_info');

box.style.position = 'absolute';
box.style.visibility = 'hidden';
box.style.fontFamily = 'Verdana, Arial, Helvetica, sans-serif';
box.style.fontSize = '9px';
box.style.color = '#000000';
box.style.backgroundColor = '#FFFFCC';
box.style.display = 'block';
box.style.padding = '2px';
box.style.border = '1px solid #000000';
box.style.zIndex = '10000000';

var pos_x;
var pos_y;
var exibe_box;

function maisinfo(msg) {

	if(typeof pos_x == "undefined") pos_x = 0;
	if(typeof pos_y == "undefined") pos_y = 0;
	
	box.innerHTML = msg;

	mostrandoBox();
	
}
function mostrandoBox () {
	box.style.visibility = 'visible';
	box.style.left = (10 + pos_x) + 'px';
	box.style.top = (17 + pos_y) + 'px';
	document.body.style.cursor = 'help';

	exibebox = setTimeout('mostrandoBox()',1);
}
function menosinfo() {
		
	clearTimeout(exibebox);
	box.style.visibility = 'hidden';
	document.body.style.cursor = 'default';

}
function moveMouse(e){
		if(ie){
			scrOfY = document.documentElement.scrollTop;
    		scrOfX = document.documentElement.scrollLeft;
			pos_x = event.clientX + scrOfX;
			pos_y = event.clientY + scrOfY;
		} else if (ns){
			scrOfY = document.body.scrollTop;
    		scrOfX = document.body.scrollLeft;
			pos_x = e.x + scrOfX;
			pos_y = e.y + scrOfY;
		} else if (ns6){
			scrOfY = window.pageYOffset;
    		scrOfX = window.pageXOffset;
			pos_x = e.clientX + scrOfX;
			pos_y = e.clientY + scrOfY;
		}    
}
document.onmousemove = moveMouse;


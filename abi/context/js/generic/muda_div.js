//script adaptado para DetranVeículos

function muda(id,obj) {
	if (document.getElementById(id).style.display == "none") {
		document.getElementById(id).style.display = "block";
		obj.style.backgroundImage = "url(../../images/icon_menos.png)";
		
		//abaixo corrige problema da borda que fica aparecendo mesmo escondendo o layer.
		if (document.all) document.getElementById("list_tabela").style.display = "block";
		 
	}
	else {
		document.getElementById(id).style.display = "none";
		obj.style.backgroundImage = "url(../../images/icon_mais.png)";
		
		//abaixo corrige problema da borda que fica aparecendo mesmo escondendo o layer.
		if (document.all) document.getElementById("list_tabela").style.display = "none";
	}
}



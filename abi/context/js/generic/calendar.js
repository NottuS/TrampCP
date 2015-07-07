var linkcount=0;
var linkdays = new Array();

function addLink(month, day, href) {
	linkdays[linkcount] = new Array(month,day,href);
	linkcount++;
}

function montaCalendario(thismonth,thisyear) {

	todayDate=new Date();

	if (thismonth == todayDate.getMonth()) {
		thisday=todayDate.getDay();
		thisdate=todayDate.getDate();
	}
	else {
		first_of_month = new Date(thisyear, thismonth, 1);
		thisday=first_of_month.getDay()-1;
		thisdate='';
	}	
	
	startspaces=thisdate;//inicio do mes

	monthnames = new Array();
	
		monthnames[0]="Janeiro";
		monthnames[1]="Fevereiro";
		monthnames[2]="Março";
		monthnames[3]="Abril";
		monthnames[4]="Maio";
		monthnames[5]="Junho";
		monthnames[6]="Julho";
		monthnames[7]="Agosto";
		monthnames[8]="Setembro";
		monthnames[9]="Outubro";
		monthnames[10]="Novembro";
		monthnames[11]="Dezembro";

	monthdays = new Array(12);
	
		monthdays[0]=31;
		monthdays[1]=28;
		monthdays[2]=31;
		monthdays[3]=30;
		monthdays[4]=31;
		monthdays[5]=30;
		monthdays[6]=31;
		monthdays[7]=31;
		monthdays[8]=30;
		monthdays[9]=31;
		monthdays[10]=30;
		monthdays[11]=31;
	
	//ano bissexto
	thisyear = thisyear % 100;
	thisyear = ((thisyear < 50) ? (2000 + thisyear) : (1900 + thisyear));
	if (((thisyear % 4 == 0) && !(thisyear % 100 == 0))||(thisyear % 400 == 0)) monthdays[1]++;
	//ano bissexto
	
	//data de início na semana
	while (startspaces > 7) startspaces-=7;
	startspaces = thisday - startspaces + 1;
	if (startspaces < 0) startspaces+=7;
	//data de início na semana
	
	//escreve o mês e o ano em cima
	calendario = "<table border=2 bgcolor=#FFFFFF bordercolor=black>";
	calendario += "<font color=black>";
	calendario += "<tr><td colspan=7><center><strong>" + monthnames[thismonth] + " " + thisyear + "</strong></center></font></td></tr>";
	calendario += "<tr>";
	calendario += "<td align=center>D</td>";
	calendario += "<td align=center>S</td>";
	calendario += "<td align=center>T</td>";
	calendario += "<td align=center>Q</td>";
	calendario += "<td align=center>Q</td>";
	calendario += "<td align=center>S</td>";
	calendario += "<td align=center>S</td>"; 
	calendario += "</tr>";
	calendario += "<tr>";
	//escreve o mês e o ano em cima
	
	for (s=0;s<startspaces;s++) {
		calendario += "<td> </td>";
	}
	
	count=1;
	while (count <= monthdays[thismonth]) {
		for (b = startspaces;b<7;b++) {
			linktrue=false;
			calendario += "<td>";
			for (c=0;c<linkdays.length;c++) {
				if (linkdays[c] != null) {
					if ((linkdays[c][0]==thismonth) && (linkdays[c][1]==count)) {
						calendario += "<a href=\"" + linkdays[c][2] + "\">";
						linktrue=true;
					}
				}
			}
						
			//coloca o dia de hoje em destaque
			if (count==thisdate) {
				calendario += "<font color='#FF0000'><strong>";
			}
			if (count <= monthdays[thismonth]) {
				calendario += count;
			}
			else {
				calendario += " ";
			}
			if (count==thisdate) {
				calendario += "</strong></font>";
			}
			//coloca o dia de hoje em destaque
			if (linktrue) {
				calendario += "</a>";
			}
			calendario += "</td>";
			count++;
		}
		calendario += "</tr>";
		calendario += "<tr>";
		startspaces=0;
	}
	calendario += "</table></p>";
	
	document.getElementById("areadocalendario").innerHTML = calendario;
	
}
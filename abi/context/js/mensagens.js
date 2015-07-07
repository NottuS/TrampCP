mensagem[500] = "Confirma a exclus�o de %1?"
mensagem[501] = "Confirmar cancelamento?"

mensagem[9000] = "O campo %1 � obrigat�rio!";
mensagem[9001] = "O campo %1 deve ser num�rico!";
mensagem[9002] = "%1 inv�lida!";
mensagem[9003] = "%1 inv�lido!";
mensagem[9004] = "O campo %1 n�o pode ser maior que o campo %2!";
mensagem[9005] = "%1 deve ter no m�nimo %2 caracteres.";
mensagem[9007] = "A quantidade glosada n�o pode ser superior a quantidade executada.";
mensagem[9018] = "Preencher uma das op��es de par�metro para pesquisa."
mensagem[9019] = "Preencher somente uma das op��es de par�metro para pesquisa."

/**
 * @author rodrigo.hjort
 * @param codigo: c�digo da mensagem
 * @param param1: primeiro par�metro (%1)
 * @param param2: segundo par�metro (%2)
 * @param param3: terceiro par�metro (%3) 
 * @return: mensagem catalogada do sistema, com os par�metro substitu�dos.<br>
 */
function mensagem(codigo, param1, param2, param3) {
	
	var texto = mensagem[codigo];
	if (texto != undefined) {
		if (param1 == undefined) param1 = "";
		if (param2 == undefined) param2 = "";
		if (param3 == undefined) param3 = "";
		texto = trocaTudo(texto, "%1", param1);
		texto = trocaTudo(texto, "%2", param2);
		texto = trocaTudo(texto, "%3", param3);
	} else {
		var cd = parseInt(codigo);
		if(isNaN(cd)){
			texto = codigo;
		}else{
			texto = "(MENSAGEM " + codigo + " N�O DEFINIDA)";
		} 
	}
	return(texto);
}

/**
 * @author rodrigo.hjort
 * @param texto: frase original a serem substitu�dos par�metros
 * @param velho: texto velho
 * @param novo: texto novo
 * @return: texto com as palavras substitu�das
 */
function trocaTudo(texto, velho, novo) {
	if (texto != undefined) {
		var ind = texto.indexOf(velho);
		while (ind > -1) {
			texto = texto.replace(velho, novo);
			ind = texto.indexOf(velho);
	    }
	}
	return(texto);
}

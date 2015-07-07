mensagem[500] = "Confirma a exclusão de %1?"
mensagem[501] = "Confirmar cancelamento?"

mensagem[9000] = "O campo %1 é obrigatório!";
mensagem[9001] = "O campo %1 deve ser numérico!";
mensagem[9002] = "%1 inválida!";
mensagem[9003] = "%1 inválido!";
mensagem[9004] = "O campo %1 não pode ser maior que o campo %2!";
mensagem[9005] = "%1 deve ter no mínimo %2 caracteres.";
mensagem[9007] = "A quantidade glosada não pode ser superior a quantidade executada.";
mensagem[9018] = "Preencher uma das opções de parâmetro para pesquisa."
mensagem[9019] = "Preencher somente uma das opções de parâmetro para pesquisa."

/**
 * @author rodrigo.hjort
 * @param codigo: código da mensagem
 * @param param1: primeiro parâmetro (%1)
 * @param param2: segundo parâmetro (%2)
 * @param param3: terceiro parâmetro (%3) 
 * @return: mensagem catalogada do sistema, com os parâmetro substituídos.<br>
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
			texto = "(MENSAGEM " + codigo + " NÃO DEFINIDA)";
		} 
	}
	return(texto);
}

/**
 * @author rodrigo.hjort
 * @param texto: frase original a serem substituídos parâmetros
 * @param velho: texto velho
 * @param novo: texto novo
 * @return: texto com as palavras substituídas
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

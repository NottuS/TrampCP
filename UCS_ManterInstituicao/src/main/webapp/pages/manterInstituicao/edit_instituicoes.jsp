<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>


<c:url var="js_maisinfo" value="/js/generic/maisinfo.js" />
<c:url var="js_calendario" value="/js/generic/calendarpopup.js" />
<c:url var="js_funcoes" value="/js/generic/funcoes.js" />
<c:url var="js_data" value="/js/generic/data.js" />
<c:url var="js_cnpj" value="/js/generic/cnpj.js" />
<c:url var="js_fone" value="/js/generic/fone.js" />
<c:url var="js_util" value="/js/generic/util.js" />
<c:url var='icon_excluir' value='/images/icon_excluir.png' />
<c:url var='icon_info' value='/images/icon_info.png' />
<c:url var='icon_calendar' value='/images/icon_calendario.png' />
<c:url var='link_pesquisar' value='/manterInstituicao.do?action=pesquisarInstituicoes' />
<c:url var='link_validar_cnpj' value='/manterInstituicao.do?action=validateCNPJ' />

<script language="JavaScript" type="text/JavaScript" src="${js_maisinfo}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_calendario}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_funcoes}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_data}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_cnpj}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_fone}"></script>
<script language="JavaScript" type="text/JavaScript" src="${js_util}"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

<script language="javascript">

	function voltar(){	
		document.manterInstituicaoForm.action="${link_pesquisar}";
		document.manterInstituicaoForm.submit();
	}


	/* 
	 * Seta o atributo "selecionado" para todos os items da lista 
	 */
	function disponiveis(arg) {
		for (i = 0; i < document.manterInstituicaoForm.areasInteresseDisponiveis.length; i++){
			document.manterInstituicaoForm.areasInteresseDisponiveis[i].selected = arg;
		}
	}

	/* 
	 * Seta o atributo "selecionado" para todos os items da lista 
	 */
	function selecionadas(arg) {
		for (i = 0; i < document.manterInstituicaoForm.areasInteresseSelecionadas.length; i++){
			document.manterInstituicaoForm.areasInteresseSelecionadas[i].selected = arg;
		}

	}

	/*  
	 * opcao == 1 == lado esquerdo 
	 * opcao == 2 == lado direito
	 */
	function selecionarTodos(opcao) {
		var campo;
		if (opcao == 2) {
			// campo recebe a lista dos itens do lado direito
			campo = document.manterInstituicaoForm.areasInteresseSelecionadas;
		} else {
			// campo recebe a lista dos itens do lado esquerdo
			campo = document.manterInstituicaoForm.areasInteresseDisponiveis;
		}

		/* atribui a propriedade de "selecionado" para todos os itens da lista */
		for (i = 0; i < campo.options.length; i++) {
			campo.options[i].selected = true;
		}
	}

	/*  
	 * opcao == 1 == lado esquerdo 
	 * opcao == 2 == lado direito
	 */
	function desmarcarTodos(opcao) {
		var campo;
		if (opcao == 2) {
			// campo recebe a lista dos itens do lado direito
			campo = document.manterInstituicaoForm.areasInteresseSelecionadas;
		} else {
			// campo recebe a lista dos itens do lado esquerdo
			campo = document.manterInstituicaoForm.areasInteresseDisponiveis;
		}
		/* desativa a propriedade de "selecionado" para todos os itens da lista */
		for (i = 0; i < campo.options.length; i++) {
			campo.options[i].selected = false;
		}
	}

	function initTelefones(){
		if(typeof document.manterInstituicaoForm.telefones == 'undefined'){
			document.manterInstituicaoForm.telefones = [];
			$('#telefonesTable').find("tr").each(function(index, element){
				if(element.id.length !== 0){
					document.manterInstituicaoForm.telefones.push(element.id.trim());
				}
			});
		}
		console.log(document.manterInstituicaoForm.telefones);
	}
	
	function adcionaTelefone(telefone){
		if(telefone.value.length !== 0) {
			initTelefones();
			var ehUnico = true;
			document.manterInstituicaoForm.telefones.forEach(function(telefoneAux){
				if(telefone.value === telefoneAux){
					ehUnico = false;
					return false;
				}
			});
			if(!ehUnico){
				alert("Telefone existente");
				return false;
			}
			
			var row = $('<tr></tr>');
			row.attr("id",telefone.value);
			row.append($('<td></td>').html(telefone.value));
			row.append('<td align="left"><img src="${icon_excluir}" onclick="JavaScript: excluirTelefone(this.parentNode.parentNode);"/></td>');				
			$('#telefonesTable').append(row);

			document.manterInstituicaoForm.telefones.push(telefone.value);
		} else {
			alert("Favor informar o telefone");
			return false;
		}
		telefone.value = "";
	}

	function excluirTelefone(telefone){
		initTelefones();
		$.each(document.manterInstituicaoForm.telefones, function(i){
		    if(document.manterInstituicaoForm.telefones[i] === telefone.id) {
		    	document.manterInstituicaoForm.telefones.splice(i,1);
		        return false;
		    }
		});
		telefone.remove();
	}

	function cnpjUnico(form){
		//var CNPJs = eval('${cnpjs}');
		//var ehUnico = true;
		var cnpjSemMascara = form.cnpj.value;
		cnpjSemMascara = cnpjSemMascara.replace(/[^0-9]+/g, "");
		var ehUnico = false;
		
		$.ajax({
            type: "GET",
            url: "/UCS_ManterInstituicao/manterInstituicao.do?action=validateCNPJ",
            data: "cnpj=" + cnpjSemMascara,
            cache: false,
            async: false,
            timeout: 3000,
            success: function(response){
            	console.log(response);
                // we have the response
               //alert(response);
               if(eval(response) == true){
					form.razaoSocial.disabled=false;
					form.naturezaJuridica.disabled=false;
					form.btnSalvar.disabled=false;
					$('#telefoneAdd').attr("disabled", false);
					$('[id=porte]').attr("disabled", false);
					$('#btnAddTelefone').attr("disabled", false);
					ehUnico = true;
               } else {
		   			form.razaoSocial.disabled=true;
					form.naturezaJuridica.disabled=true;
					form.btnSalvar.disabled=true;
					$('#telefoneAdd').attr("disabled", true);
					$('#btnAddTelefone').attr("disabled", true);
					$('[id=porte]').attr("disabled", true);
					$('#cnpj').attr("onkeyup", "JavaScript: MascaraCNPJ(this, event); cnpjUnico(document.manterInstituicaoForm);");
					ehUnico = false;
               }
            },
            error: function(e){
                alert('Error: ' + e);
                e.preventDefault();
                ehUnico = false;
            }
        });
        return ehUnico;
	}
	
	function validarCampos(){
		
		var form = document.manterInstituicaoForm;
		
		Trim(form.cnpj);
		if(form.actionType.value !== "alterar") {
			if(form.cnpj.value.length === 0){
				alert("Favor infomar o CNPJ da instituição.");
				form.cnpj.focus();
				return false;
			} else if ( !ValidaCNPJ(form.cnpj)){
				alert("CNPJ inválido!");
				form.cnpj.focus();
				return false;
			} else if (!cnpjUnico(form) ) {
				alert("CNPJ ja cadastrado");
				form.cnpj.focus();
				return false;
			} 
		}
		
		Trim(form.razaoSocial);
		if (form.razaoSocial.value.length !== 0 && form.razaoSocial.value.length < 4){
			alert("Favor informar no mínimo (4) letras para a Razão Social.");
			form.razaoSocial.focus();
			return false;			
		} else if (form.razaoSocial.value.length <= 0){
			alert("Favor informar a Razão Social.");
			form.razaoSocial.focus();
			return false;
		}

		if(form.porte.value.length <= 0){
			alert("Favor selecionar o Porte da instituição.");
			return false;
		}

		if(form.naturezaJuridica.value === "0"){
			alert("Favor selecionar o Natureza Jurídica da instituição.");
			form.naturezaJuridica.focus();
			return false;
		}

		DesmascaraCNPJ(form.cnpj);
		
		initTelefones();
		var telefoneList = $('<input/>');
		telefoneList.attr("type","hidden");
		telefoneList.attr("value", [form.telefones]);
		telefoneList.attr("id", "telefones");
		telefoneList.attr("name", "telefones");
		$(form).append(telefoneList);
		
		disponiveis(true);    
		selecionadas(true);
		
		form.submit();
	}
</script>
	<c:choose> 
		<c:when test='${manterInstituicaoForm.actionType == "incluir"}'>
			<c:set var="titulo" value="Incluir"></c:set>
		</c:when>
		<c:otherwise>
			<c:set var="titulo" value="Alterar"></c:set>
		</c:otherwise>
	</c:choose>

<div id="conteudo">
	
	<div align="center">
	<h1>${titulo} Instituição</h1>

	<html:form action="/manterInstituicao.do?action=salvarInstituicao" onsubmit="return validarCampos()">
	<!--html:form action="${acao}" -->	
	<html:hidden property="codInstituicao" value="${manterInstituicaoForm.codInstituicao}"/>
	<html:hidden property="actionType" value="${manterInstituicaoForm.actionType}" />
	
    <div id="conteudo_corpo">	
	 <table class="form_tabela" cellspacing="0">
			<tr>
				<td class="form_label">*CNPJ:</td>
				<!-- TODO Crtl C here -->
				<td>
					<html:text disabled="${manterInstituicaoForm.actionType eq 'alterar' ? 'true' : 'false'}" size="18" maxlength="18" property="cnpj" styleId="cnpj" onblur="javascript:return(MascaraCNPJ(this, event));" onkeyup="javascript:return(MascaraCNPJ(this, event));" />
				</td>
			</tr>
			<tr>
				<td class="form_label">*Razão social:</td>
				<td>
					<html:text size="100" maxlength="100" property="razaoSocial" styleId="razaoSocial" onkeypress="javascript:DigitaLetra(document.getElementById('razaoSocial'));" />
				</td>
			</tr>
			<tr>
				<!-- TODO essa joça n vem selecionada na tela de resultado -->
				<td class="form_label">*Natureza Jurídica:</td>
				<td>
					<html:select property="naturezaJuridica" styleId="naturezaJuridica">
					 <option value="0" selected>Selecione</option>
					<c:forEach var="nj" items="${naturezaJuridicas}">
				        <option value="${nj.codNaturezaJuridica}" ${manterInstituicaoForm.naturezaJuridica eq nj.codNaturezaJuridica ? 'selected' : ''}>
				            <!-- ${nj.codNaturezaJuridica}-${nj.descricao} -->
				            ${nj.mascara} 
				        </option>
				    </c:forEach>
					</html:select> 
				</td>
			</tr>
			<tr>
				<td class="form_label">*Porte:</td>
				<td>
					<c:forEach var="porte" items="${sessionScope.portes}" >
				        <html:radio property="porte" styleId="porte" value="${porte.codigo}" >
				            ${porte.descricao} 
				        </html:radio>
				    </c:forEach>
				</td>
			</tr>
		</table>
	</div>
  <div id="conteudo_corpo">
	  <b>Telefone:</b><input type="text" name="telefoneAdd" id="telefoneAdd"  onblur="javascript:return(MascaraFone(this, event));" onkeypress="javascript:return(MascaraFone(this, event));"/>
	  <html:button property="btnAddTelefone"  styleId="btnAddTelefone" styleClass="form_botao" value="adicionar" onclick="JavaScript: adcionaTelefone(document.getElementById('telefoneAdd'));" />
	  <hr>
	  <table id="telefonesTable" width="100%" >
		  <tr>
		  	<td><b>Telefone</b></td>
		  	<td align="left"><b>Excluir</b></td>
		  </tr>
		  <c:forEach var="telefone" items="${telefones}">
		  	<tr id="${telefone.telefone}">
				<td>${telefone.telefone}</td>
		  		<td align="left"> <img  src="${icon_excluir}" onclick="JavaScript: excluirTelefone(this.parentNode.parentNode);"/></td>
		  	</tr>
		  </c:forEach>
	  </table>
  </div>
  
    <div id="conteudo_corpo">
	   <b>Área de Interesse</b><br>
	   <b>Dispoiníveis</b>
		<div id="areasInteresse" align="center">
			<table class="form_tabela" cellspacing="0">
				<tr>
					<td colspan="5">
						<ch:select  classe="form_tabela"	ordenada="true" moverTodas="true" >
							<ch:esquerda combo="areasInteresseDisponiveis" label="Itens Disponíveis" onError="">
								<ch:opcoes  colecao="areasInteresseDisponiveis" nome="descricao"	valor="codAreaInteresse" />
							</ch:esquerda>
	
							<ch:direita combo="areasInteresseSelecionadas" label="* Selecionadas" onError="">
								<ch:opcoes  colecao="areasInteresseSelecionadas" nome="descricao"	valor="codAreaInteresse" />
							</ch:direita>
						</ch:select></td>
				</tr>
				<tr>
					<td align="left"><a href="JavaScript: selecionarTodos(1);">Selecionar sodos</a></td>
					<td align="right"><a href="JavaScript: desmarcarTodos(1);">Desmarcar Todos</a></td>
					<td width="80"></td>
					<td align="left"><a href="JavaScript: selecionarTodos(2);">Selecionar Todos</a></td>
					<td align="right"><a href="JavaScript: desmarcarTodos(2);">Desmarcar Todos</a></td>
				</tr>
			</table>
		</div>
   	</div>
 
   	<hr>
  	<p class="obs_obrigatorio">(*) Campo de preenchimento obrigatório.</p>
 	<div align="center">
		 <!--<html:button property="btnSalvar" styleClass="form_botao" value="${titulo}" onclick="validarCampos();" />-->
		<html:submit property="btnSalvar" styleId="btnSalvar" styleClass="form_botao" value="${titulo}" />
		<html:button property="btnCancelar" styleClass="form_botao" value="Cancelar" onclick="voltar();" />
	</div>	
	
  </html:form>

  </div>
</div>
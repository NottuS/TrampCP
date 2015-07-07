<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/html-2.0" prefix="ch" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<c:url var='icon_adicionar' value='/images/icon_adicionar1.png'/>
<c:url var='icon_excluir' value='/images/icon_excluir.png'/>
<c:url var='icon_localizar' value='/images/icon_localizar.png' />
<c:url var='icon_limpar' value='/images/icon_apagar.png' />

<script type="text/javascript">
function adicionarGrupo() {
	if (validarCamposGrupo()){
		submitAjax('/abi/manterUsuario.do?action=adicionarGrupoManterUsuario&grupo='+document.manterUsuarioForm.grupo.value, document.manterUsuarioForm, 'divListaGrupo',false);
		
	}
}
</script>
<html:hidden property="desabilitaCampo" name="manterUsuarioForm" />
<html:hidden property="desabilitaOrgao" name="manterUsuarioForm"/>

<html:hidden property="desabilitaInstituicao" name="manterUsuarioForm"/>
<html:hidden property="isAdmGeral" name="manterUsuarioForm" styleId="isAdmGeral"/>

<h2>Grupo</h2>
<table width="100%">
	<tr>
		<td class="form_label" width="200">Grupo:</td>
		<td>
			<table>
				<tr>
					<td>
						<html:select property="grupo" name="manterUsuarioForm" styleId="grupo">
							<html:option value="">- Selecione -</html:option>
							<html:options collection="grupos" property="codigo" labelProperty="descricao" />
						</html:select>
					</td>
					<td>
						<div id="divBtGrupo">
							<a href="javascript:adicionarGrupo();" 	id="linkAddGrupo"><img src="${icon_adicionar}" width="16"id="iconAddGrupo" height="16" border="0"></a>&nbsp;
							<a href="javascript:adicionarGrupo();">Adicionar</a> <a	href="javascript:limparCamposGrupo();"><img	src="${icon_limpar}" width="16" height="16" border="0"></a>&nbsp;
							<a href="javascript:limparCamposGrupo();">Limpar</a>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td colspan="2"><c:if
			test="${listGrupos != null && listGrupos.totalRegistros > 0}">
			<c:url var="icon_excluir" value="/images/icon_excluir.png" />
			<c:url var="link_excluirGrupo" value="JavaScript:excluirGrupo(%1);" />
			<ch:table classTable="list_tabela" classLinha1="list_cor_sim"	classLinha2="list_cor_nao">
				<ch:lista bean="${listGrupos}"	atributoId="grupoSentinela.codGrupoSentinela" />
				<ch:campo atributo="grupoSentinela.descricaoGrupo" 	label="<center>Grupo Vinculado</center>" align="center" />
				<ch:action imagem="${icon_excluir}" link="${link_excluirGrupo}" label="Excluir" width="3%" align="center" />
			</ch:table>
		</c:if></td>
	</tr>
</table>

<table cellspacing="0" class="form_tabela" width="100%">
<tr>
					<td colspan="2" width="100%">
					<div id="divInstituicao">
						<h2>Instituição</h2>
						<table width="100%">
							<tr>
								<td class="form_label" width="200">Instituição:</td>
								<td>
									<html:select styleId="instituicao"  property="instituicao" disabled="${manterUsuarioForm.desabilitaInstituicao}" onchange="javascript:carregarComboOrgao();" name="manterUsuarioForm">
										<html:option value="">- Selecione -</html:option>
										<html:options collection="listaInstituicao"	property="codInstituicao" labelProperty="nome" />
									</html:select>
								</td>
							</tr>
						</table>
					</div>
				</tr>
		<tr>
					<td colspan="2" width="100%">
					    <div id="divOrgao"> 
							<h2>Órgão</h2>
							<table width="100%">
							 	<tr>
							 		<td class="form_label" width="200">* Administração: </td>
							 		<td>
										<html:radio value="1" property="administracao" disabled="${manterUsuarioForm.desabilitaOrgao}" name="manterUsuarioForm" styleId ="administracao1" onchange="javascript:carregarComboOrgao();">Direta</html:radio>
										<html:radio value="2" property="administracao" disabled="${manterUsuarioForm.desabilitaOrgao}" name="manterUsuarioForm" styleId ="administracao2" onchange="javascript:carregarComboOrgao();">Indireta</html:radio>
										<html:radio value="3" property="administracao" disabled="${manterUsuarioForm.desabilitaOrgao}" name="manterUsuarioForm" styleId ="administracao3" onchange="javascript:carregarComboOrgao();">Terceiros</html:radio>
									</td>
								</tr>
								<tr>
								 	<td class="form_label">* Órgão: </td>
								 	<td>
										<table>
											<tr> 
												<td>
													<div id="divComboOrgao">
														<tiles:insert page="/pages/usuario/_edit_comboOrgao.jsp"/>
													</div>
												</td>
												<td>
													<div id="divBtOrgao"> 
														<a href="javascript:adicionarOrgao();" id="linkAddOrgao"><img src="${icon_adicionar}" width="16" height="16" id="iconAddOrgao" border="0"></a>&nbsp;<a href="javascript:adicionarOrgao()">Adicionar</a>						
														<a href="javascript:limparCamposOrgao();"><img src="${icon_limpar}" width="16" height="16" border="0"></a>&nbsp;<a href="javascript:limparCamposOrgao();">Limpar</a>
													</div>
												</td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td colspan="2"> 
										<div id="divListOrgao">
											<tiles:insert page="/pages/usuario/_edit_listOrgaoUsuario.jsp"/>
										</div>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				</table>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="pt_BR" scope="application"/>

<c:if test="${bemImovelSimplificado != null}">
	<div id="conteudo_informativo" align="center">
		<table width="99%">
			<tr>
		    	<td class="form_label" width="15%">CPE:</td>
		    	<td width="34%">
					<c:if test="${bemImovelSimplificado.nrBemImovel != null && bemImovelSimplificado.nrBemImovel != ''}">
				        <c:out value="${bemImovelSimplificado.nrBemImovel}"></c:out> 
					</c:if>
				</td>
		    	<td class="form_label" width="15%">Administração:</td>
		        <td width="34%">
					<c:if test="${bemImovelSimplificado.descricaoAdministracao != null && bemImovelSimplificado.descricaoAdministracao != ''}">
			        	<c:out value="${bemImovelSimplificado.descricaoAdministracao}"></c:out> 
					</c:if>
					<c:if test="${bemImovelSimplificado.descricaoAdministracao == null}">
						<font color="red"><c:out value="--Sem Informação--"></c:out></font>
					</c:if>
		        </td>
			</tr>
			<tr>
		    	<td class="form_label">Órgão Proprietário:</td>
		        <td colspan="3">
					<c:if test="${bemImovelSimplificado.orgao != null && bemImovelSimplificado.orgao.codOrgao != ''}">
			        	<c:out value="${bemImovelSimplificado.orgao.siglaDescricao}"></c:out> 
					</c:if>
					<c:if test="${bemImovelSimplificado.orgao == null}">
						<font color="red"><c:out value="--Sem Informação--"></c:out></font>
					</c:if>
		        </td>
			</tr>
			<tr>
		    	<td class="form_label">Classificação do Imóvel:</td>
		        <td>
					<c:if test="${bemImovelSimplificado.classificacaoBemImovel != null && bemImovelSimplificado.classificacaoBemImovel.codClassificacaoBemImovel != ''}">
			        	<c:out value="${bemImovelSimplificado.classificacaoBemImovel.descricao}"></c:out> 
					</c:if>
					<c:if test="${bemImovelSimplificado.classificacaoBemImovel == null}">
						<font color="red"><c:out value="--Sem Informação--"></c:out></font>
					</c:if>
		        </td>
		    	<td class="form_label">Situação de Localização:</td>
		        <td>
					<c:if test="${bemImovelSimplificado.descricaoSituacaoLocal != null && bemImovelSimplificado.descricaoSituacaoLocal != ''}">
		        		<c:out value="${bemImovelSimplificado.descricaoSituacaoLocal}"></c:out> 
					</c:if>
					<c:if test="${bemImovelSimplificado.descricaoSituacaoLocal == null}">
						<font color="red"><c:out value="--Sem Informação--"></c:out></font>
					</c:if>
		        </td>
			</tr>

			<tr>
		    	<td class="form_label">Situação Legal - Cartorial:</td>
		        <td>
					<c:if test="${bemImovelSimplificado.situacaoLegalCartorial != null && bemImovelSimplificado.situacaoLegalCartorial.codSituacaoLegalCartorial != ''}">
		        		<c:out value="${bemImovelSimplificado.situacaoLegalCartorial.descricao}"></c:out> 
					</c:if>
					<c:if test="${bemImovelSimplificado.situacaoLegalCartorial == null}">
						<font color="red"><c:out value="--Sem Informação--"></c:out></font>
					</c:if>
		        </td>
		    	<td class="form_label">Município/Estado:</td>
		        <td>
					<c:if test="${bemImovelSimplificado.municipioEstado != null && bemImovelSimplificado.municipioEstado != ''}">
		        		<c:out value="${bemImovelSimplificado.municipioEstado}"></c:out> 
					</c:if>
					<c:if test="${bemImovelSimplificado.municipioEstado == null}">
						<font color="red"><c:out value="--Sem Informação--"></c:out></font>
					</c:if>
		        </td>
			</tr>

			<tr>
		    	<td class="form_label">Logradouro/Nº:</td>
		        <td colspan="3">
					<c:if test="${bemImovelSimplificado.logradouro != null && bemImovelSimplificado.logradouro != ''}">
			        	<c:out value="${bemImovelSimplificado.logradouro}"></c:out> 
						<c:if test="${bemImovelSimplificado.numero != null && bemImovelSimplificado.numero != ''}">
				        	/ <c:out value="${bemImovelSimplificado.numero}"></c:out>
						</c:if>
					</c:if>
					<c:if test="${bemImovelSimplificado.logradouro == null}">
						<font color="red"><c:out value="--Sem Informação--"></c:out></font>
					</c:if>
		        </td>
			</tr>
			<tr>
		    	<td class="form_label">Bairro/Distrito:</td>
		        <td colspan="3">
					<c:if test="${bemImovelSimplificado.bairroDistrito != null && bemImovelSimplificado.bairroDistrito != ''}">
				        <c:out value="${bemImovelSimplificado.bairroDistrito}"></c:out> 
					</c:if>
					<c:if test="${bemImovelSimplificado.bairroDistrito == null}">
						<font color="red"><c:out value="--Sem Informação--"></c:out></font>
					</c:if>
			    </td>
			</tr>

			<tr>
		    	<td class="form_label">Área do Terreno (m²):</td>
		        <td>
					<c:if test="${bemImovelSimplificado.areaTerreno != null && bemImovelSimplificado.areaTerreno != ''}">
			        	<c:out value="${bemImovelSimplificado.areaTerrenoFormatado}"></c:out> 
					</c:if>
					<c:if test="${bemImovelSimplificado.areaTerreno == null}">
						<font color="red"><c:out value="--Sem Informação--"></c:out></font>
					</c:if>
		        </td>
		    	<td class="form_label">Área Construída (m²):</td>
		        <td>
					<c:if test="${bemImovelSimplificado.areaConstruida != null && bemImovelSimplificado.areaConstruida != ''}">
			        	<c:out value="${bemImovelSimplificado.areaConstruidaFormatado}"></c:out> 
					</c:if>
					<c:if test="${bemImovelSimplificado.areaConstruida == null}">
						<font color="red"><c:out value="--Sem Informação--"></c:out></font>
					</c:if>
		        </td>
			</tr>

			<tr>
		    	<td class="form_label">Somente Terreno:</td>
		        <td>
					<c:if test="${bemImovelSimplificado.somenteTerrenoDescricao != null && bemImovelSimplificado.somenteTerrenoDescricao != ''}">
			        	<c:out value="${bemImovelSimplificado.somenteTerrenoDescricao}"></c:out> 
					</c:if>
					<c:if test="${bemImovelSimplificado.somenteTerrenoDescricao == null}">
						<font color="red"><c:out value="--Sem Informação--"></c:out></font>
					</c:if>
		        </td>
		    	<td class="form_label">Situação do Imóvel:</td>
		        <td>
					<c:if test="${bemImovelSimplificado.situacaoImovel != null && bemImovelSimplificado.situacaoImovel.codSituacaoImovel != ''}">
			        	<c:out value="${bemImovelSimplificado.situacaoImovel.descricao}"></c:out> 
					</c:if>
					<c:if test="${bemImovelSimplificado.situacaoImovel == null}">
						<font color="red"><c:out value="--Sem Informação--"></c:out></font>
					</c:if>
		        </td>
				
			</tr>
		</table>
	</div>		
</c:if>
<c:if test="${bemImovelSimplificado == null && execBuscaBemImovel > 0 && cedidoPara == null && msgValidacao == null}">
	<div id="conteudo_informativo" align="center">
		<script type="text/javascript">
			desabilitaCampos();
		</script>
		<table width="99%">
			<tr>
		        <td align="center" colspan="2"><b>Identificação do Imóvel inexistente.</b></td>
			</tr>
		</table>
	</div>		
</c:if>

<c:if test="${bemImovelSimplificado == null && execBuscaBemImovel > 0 && cedidoPara != null}">
	<div id="conteudo_informativo" align="center">
		<script type="text/javascript">
			desabilitaCampos();
		</script>
		<table width="99%">
			<tr>
		        <td align="center" colspan="2"><b>Imóvel cedido para o órgão: <c:out value="${cedidoPara}"></c:out>
		        </b></td>
			</tr>
		</table>
	</div>		
</c:if>

<c:if test="${bemImovelSimplificado == null && execBuscaBemImovel > 0 && msgValidacao != null}">
	<div id="conteudo_informativo" align="center">
		<script type="text/javascript">
			desabilitaCampos();
		</script>
		<table width="99%">
			<tr>
		        <td align="center" colspan="2"><b><c:out value="${msgValidacao}"></c:out></b></td>
			</tr>
		</table>
	</div>		
</c:if>

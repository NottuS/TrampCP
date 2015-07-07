
package gov.pr.celepar.abi.form;

import org.apache.struts.validator.ValidatorForm;


/**
 * @author Luciana R. Bélico
 * @version 1.0
 * @since 02/02/2010
 * 
 * Responsável por encapsular os dados das telas de orgãos.
 */
public class OrgaoForm extends ValidatorForm {
	
	private static final long serialVersionUID = 2448401180184675084L;
	private String actionType;
	private String codOrgao;
	private String descricao;
	private String sigla;
	private String indTipoAdministracao;
	private String indGrupoSentinela;
	private String instituicao;
	private String adm;
	private String conInstituicao;
	
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getCodOrgao() {
		return codOrgao;
	}
	public void setCodOrgao(String codOrgao) {
		this.codOrgao = codOrgao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getIndTipoAdministracao() {
		return indTipoAdministracao;
	}
	public void setIndTipoAdministracao(String indTipoAdministracao) {
		this.indTipoAdministracao = indTipoAdministracao;
	}
	public String getIndGrupoSentinela() {
		return indGrupoSentinela;
	}
	public void setIndGrupoSentinela(String indGrupoSentinela) {
		this.indGrupoSentinela = indGrupoSentinela;
	}
	public String getAdm() {
		return adm;
	}
	public void setAdm(String adm) {
		this.adm = adm;
	}
	public String getConInstituicao() {
		return conInstituicao;
	}
	public void setConInstituicao(String conInstituicao) {
		this.conInstituicao = conInstituicao;
	}
	public String getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}
	
	
	
}

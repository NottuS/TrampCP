package gov.pr.celepar.abi.form;

import gov.pr.celepar.abi.util.Dominios.administracaoImovel;
import gov.pr.celepar.abi.util.Util;


/**
 * @author pialarissi
 * @version 1.0
 * @since 19/01/2010
 * 
 * Responsável por encapsular os dados das telas de Lei/Decreto.
 */
public class LeiBemImovelForm extends BemImovelForm {

	private static final long serialVersionUID = -3716987785974966109L;
	private String actionType;
	private String codLeiBemImovel;
	private String codBemImovel;	
	private String codInstituicao;
	private String tipoLeiBemImovel;    
    private String numero;
    private String dataAssinatura;
    private String dataPublicacao;
	private String administracao;
    private String somenteTerreno;
    private String nrDioe;
    
    private String camposPesquisaUCOrigem;
    private String actionUCOrigem;
    
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getCodLeiBemImovel() {
		return codLeiBemImovel;
	}
	public void setCodLeiBemImovel(String codLeiBemImovel) {
		this.codLeiBemImovel = codLeiBemImovel;
	}
	public String getCodBemImovel() {
		return codBemImovel;
	}
	public void setCodBemImovel(String codBemImovel) {
		this.codBemImovel = codBemImovel;
	}
	public String getTipoLeiBemImovel() {
		return tipoLeiBemImovel;
	}
	public void setTipoLeiBemImovel(String tipoLeiBemImovel) {
		this.tipoLeiBemImovel = tipoLeiBemImovel;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getDataAssinatura() {
		return dataAssinatura;
	}
	public void setDataAssinatura(String dataAssinatura) {
		this.dataAssinatura = dataAssinatura;
	}
	public String getDataPublicacao() {
		return dataPublicacao;
	}
	public void setDataPublicacao(String dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}
	public String getDescricaoAdministracao(){
		if (!Util.strEmBranco( this.administracao)){
			return administracaoImovel.getAdministracaoImovelByIndex(Integer.valueOf(this.administracao)).getLabel();
		}
		else{
			return "";
		}
	}
	public String getAdministracao() {
		return administracao;
	}
	public void setAdministracao(String administracao) {
		this.administracao = administracao;
	}
	
    public String getSomenteTerreno() {
		return somenteTerreno;
	}
	public void setSomenteTerreno(String somenteTerreno) {
		this.somenteTerreno = somenteTerreno;
	}
	
	public String getCamposPesquisaUCOrigem() {
		return camposPesquisaUCOrigem;
	}
	public void setCamposPesquisaUCOrigem(String camposPesquisaUCOrigem) {
		this.camposPesquisaUCOrigem = camposPesquisaUCOrigem;
	}
	public String getActionUCOrigem() {
		return actionUCOrigem;
	}
	public void setActionUCOrigem(String actionUCOrigem) {
		this.actionUCOrigem = actionUCOrigem;
	}
	public void setNrDioe(String nrDioe) {
		this.nrDioe = nrDioe;
	}
	public String getNrDioe() {
		return nrDioe;
	}
	public void setCodInstituicao(String codInstituicao) {
		this.codInstituicao = codInstituicao;
	}
	public String getCodInstituicao() {
		return codInstituicao;
	}
	
}

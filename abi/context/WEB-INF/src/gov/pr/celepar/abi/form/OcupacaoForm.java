package gov.pr.celepar.abi.form;

import gov.pr.celepar.abi.util.Dominios.administracaoImovel;
import gov.pr.celepar.abi.util.Util;


/**
 * @author 
 * @version 1.0
 * @since 19/01/2010
 * 
 * Responsável por encapsular os dados das telas de ocupação da edificação.
 */
public class OcupacaoForm extends BemImovelForm {
	
	private static final long serialVersionUID = -743256314553799158L;
	
	private String codOcupacao;
	private String codEdificacao;
	private String codOrgao;
	private String administracao;
	private String actionType;
	private String codBemImovel;
	private String descricao;
	private String ocupacaoMetroQuadrado;
	private String ocupacaoPercentual;
	private String dataOcupacao;
	private String termoTransferencia;
	private String dataLei;
	private String vigenciaLei;
	private String numeroLei;
	private String numeroNotificacao;
	private String prazoNotificacao;
    private String protocoloNotificacaoSpi;
    private String especificacao;
    private String areaConstruida;
    private String situacaoOcupacao;

	public String getCodOcupacao() {
		return codOcupacao;
	}
	public void setCodOcupacao(String codOcupacao) {
		this.codOcupacao = codOcupacao;
	}
	public void setCodEdificacao(String codEdificacao) {
		this.codEdificacao = codEdificacao;
	}
	public String getCodEdificacao() {
		return codEdificacao;
	}
	public String getCodOrgao() {
		return codOrgao;
	}
	public void setCodOrgao(String codOrgao) {
		this.codOrgao = codOrgao;
	}
	public String getEspecificacao() {
		return especificacao;
	}
	public void setEspecificacao(String especificacao) {
		this.especificacao = especificacao;
	}
	public String getAreaConstruida() {
		return areaConstruida;
	}
	public void setAreaConstruida(String areaConstruida) {
		this.areaConstruida = areaConstruida;
	}
	public String getSituacaoOcupacao() {
		return situacaoOcupacao;
	}
	public void setSituacaoOcupacao(String situacaoOcupacao) {
		this.situacaoOcupacao = situacaoOcupacao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getOcupacaoMetroQuadrado() {
		return ocupacaoMetroQuadrado;
	}
	public void setOcupacaoMetroQuadrado(String ocupacaoMetroQuadrado) {
		this.ocupacaoMetroQuadrado = ocupacaoMetroQuadrado;
	}
	public String getOcupacaoPercentual() {
		return ocupacaoPercentual;
	}
	public void setOcupacaoPercentual(String ocupacaoPercentual) {
		this.ocupacaoPercentual = ocupacaoPercentual;
	}
	public String getDataOcupacao() {
		return dataOcupacao;
	}
	public void setDataOcupacao(String dataOcupacao) {
		this.dataOcupacao = dataOcupacao;
	}
	public String getTermoTransferencia() {
		return termoTransferencia;
	}
	public void setTermoTransferencia(String termoTransferencia) {
		this.termoTransferencia = termoTransferencia;
	}
	public String getDataLei() {
		return dataLei;
	}
	public void setDataLei(String dataLei) {
		this.dataLei = dataLei;
	}
	public String getVigenciaLei() {
		return vigenciaLei;
	}
	public void setVigenciaLei(String vigenciaLei) {
		this.vigenciaLei = vigenciaLei;
	}
	public String getNumeroLei() {
		return numeroLei;
	}
	public void setNumeroLei(String numeroLei) {
		this.numeroLei = numeroLei;
	}
	public String getNumeroNotificacao() {
		return numeroNotificacao;
	}
	public void setNumeroNotificacao(String numeroNotificacao) {
		this.numeroNotificacao = numeroNotificacao;
	}
	public String getPrazoNotificacao() {
		return prazoNotificacao;
	}
	public void setPrazoNotificacao(String prazoNotificacao) {
		this.prazoNotificacao = prazoNotificacao;
	}
	public String getProtocoloNotificacaoSpi() {
		return protocoloNotificacaoSpi;
	}
	public void setProtocoloNotificacaoSpi(String protocoloNotificacaoSpi) {
		this.protocoloNotificacaoSpi = protocoloNotificacaoSpi;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getActionType() {
		return actionType;
	}
	public void setCodBemImovel(String codBemImovel) {
		this.codBemImovel = codBemImovel;
	}
	public String getCodBemImovel() {
		return codBemImovel;
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
}

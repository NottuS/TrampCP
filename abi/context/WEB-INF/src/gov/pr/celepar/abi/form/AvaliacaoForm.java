package gov.pr.celepar.abi.form;

import gov.pr.celepar.abi.pojo.Edificacao;
import gov.pr.celepar.abi.util.Dominios.administracaoImovel;
import gov.pr.celepar.abi.util.Util;

import java.util.Collection;


/**
 * @author pialarissi
 * @version 1.0
 * @since 19/01/2010
 * 
 * Responsável por encapsular os dados das telas de Avaliação.
 */
public class AvaliacaoForm extends BemImovelForm {

	private static final long serialVersionUID = 6655878550179821026L;
	private String actionType;
	private String codAvaliacao;
	private String codBemImovel;	
	private String edificacao;    
    private String valor;
    private String dataAvaliacao;
    private String administracao;
    private String somenteTerreno;
    private String indTipoAvaliacao;
    private String selBemImovel;
    
    public String getSelBemImovel() {
		return selBemImovel;
	}
	public void setSelBemImovel(String selBemImovel) {
		this.selBemImovel = selBemImovel;
	}
	private Collection<Edificacao> edificacoes;

    
    public Collection<Edificacao> getEdificacoes() {
		return edificacoes;
	}
	public void setEdificacoes(Collection<Edificacao> edificacoes) {
		this.edificacoes = edificacoes;
	}
	
	public String getCodBemImovel() {
		return codBemImovel;
	}
	public void setCodBemImovel(String codBemImovel) {
		this.codBemImovel = codBemImovel;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getCodAvaliacao() {
		return codAvaliacao;
	}
	public void setCodAvaliacao(String codAvaliacao) {
		this.codAvaliacao = codAvaliacao;
	}
	
	public String getEdificacao() {
		return edificacao;
	}
	public void setEdificacao(String edificacao) {
		this.edificacao = edificacao;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getDataAvaliacao() {
		return dataAvaliacao;
	}
	public void setDataAvaliacao(String dataAvaliacao) {
		this.dataAvaliacao = dataAvaliacao;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
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
	public String getIndTipoAvaliacao() {
		return indTipoAvaliacao;
	}
	public void setIndTipoAvaliacao(String indTipoAvaliacao) {
		this.indTipoAvaliacao = indTipoAvaliacao;
	}

}

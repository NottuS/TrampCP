
package gov.pr.celepar.abi.form;

import gov.pr.celepar.abi.generico.form.BaseForm;


/**
 * Classe responsavel permitir a impressao de vistorias de bens imoveis (finalizadas e abertas).<BR>
 * @author ginaalmeida
 * @version 1.0
 * @since 13/07/2011
 */
public class GerarRelatorioVistoriaBemImovelForm extends BaseForm {

	
	private static final long serialVersionUID = -730043308589882360L;
	
	private String bemImovel;
	private String imprimir;
	private String codVistoria;
	private String conInstituicao;
	
	
	public String getImprimir() {
		return imprimir;
	}
	public void setImprimir(String imprimir) {
		this.imprimir = imprimir;
	}
	public String getBemImovel() {
		return bemImovel;
	}
	public void setBemImovel(String bemImovel) {
		this.bemImovel = bemImovel;
	}
	public String getCodVistoria() {
		return codVistoria;
	}
	public void setCodVistoria(String codVistoria) {
		this.codVistoria = codVistoria;
	}
	public String getConInstituicao() {
		return conInstituicao;
	}
	public void setConsIntituicao(String conInstituicao) {
		this.conInstituicao = conInstituicao;
	}
	
}
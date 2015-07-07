package gov.pr.celepar.abi.generico.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

public class BaseForm extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2697159620468445421L;
	private String tipoAcao;
	private String ucsChamador;
	private String ucsRetorno;
	private String ucsDestino;
	
	/*
	 * Quando um UCS chamador precisa saber o id dos objetos incluidos/alterados/excluidos 
	 * pelo UCS Destino, o UCS destino deve guardar o id dos objetos neste array.
	 */
	private List<String> listaIdObjetos = new ArrayList<String>(0);
	
	public String getTipoAcao() {
		return tipoAcao;
	}
	public void setTipoAcao(String tipoAcao) {
		this.tipoAcao = tipoAcao;
	}
	public String getUcsChamador() {
		return ucsChamador;
	}
	public void setUcsChamador(String ucsChamador) {
		this.ucsChamador = ucsChamador;
	}
	public String getUcsRetorno() {
		return ucsRetorno;
	}
	public void setUcsRetorno(String ucsRetorno) {
		this.ucsRetorno = ucsRetorno;
	}
	public String getUcsDestino() {
		return ucsDestino;
	}
	public void setUcsDestino(String ucsDestino) {
		this.ucsDestino = ucsDestino;
	}
	public List<String> getListaIdObjetos() {
		return listaIdObjetos;
	}
	public void setListaIdObjetos(List<String> listaIdObjetos) {
		this.listaIdObjetos = listaIdObjetos;
	} 
	
	
}

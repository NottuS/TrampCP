package gov.pr.celepar.abi.dto;

import gov.pr.celepar.abi.pojo.ParametroAgenda;

import java.util.List;


public class AgendaDTO {

	private List<NotificacaoDTO> listaNotificacaoDTOAVencerStr;
	private List<NotificacaoDTO> listaNotificacaoDTOVencidaStr;
	private List<VistoriaDTO> listaVistoriaDTOVencidaStr;
	private List<VistoriaDTO> listaVistoriaDTONaoFinalizada;
	private List<DoacaoDTO> listaDoacaoDTOAVencerStr;
	private List<DoacaoDTO> listaDoacaoDTONaoFinalizada;
	private List<DoacaoDTO> listaDoacaoDTOVencida;
	private List<CessaoDeUsoDTO> listaCessaoDeUsoVencerStr;
	private List<CessaoDeUsoDTO> listaCessaoDeUsoEmPreenchimentoStr;
	private List<CessaoDeUsoDTO> listaCessaoDeUsoEmRenovacaoStr;
	private List<CessaoDeUsoDTO> listaCessaoDeUsoDTOVencida;
	private String usuario;
	private ParametroAgenda parametroAgenda;
	private String instituicao;
	
    public AgendaDTO() {
    }

	public List<NotificacaoDTO> getListaNotificacaoDTOAVencerStr() {
		return listaNotificacaoDTOAVencerStr;
	}

	public void setListaNotificacaoDTOAVencerStr(
			List<NotificacaoDTO> listaNotificacaoDTOAVencerStr) {
		this.listaNotificacaoDTOAVencerStr = listaNotificacaoDTOAVencerStr;
	}

	public List<NotificacaoDTO> getListaNotificacaoDTOVencidaStr() {
		return listaNotificacaoDTOVencidaStr;
	}

	public void setListaNotificacaoDTOVencidaStr(
			List<NotificacaoDTO> listaNotificacaoDTOVencidaStr) {
		this.listaNotificacaoDTOVencidaStr = listaNotificacaoDTOVencidaStr;
	}

	public List<VistoriaDTO> getListaVistoriaDTOVencidaStr() {
		return listaVistoriaDTOVencidaStr;
	}

	public void setListaVistoriaDTOVencidaStr(
			List<VistoriaDTO> listaVistoriaDTOVencidaStr) {
		this.listaVistoriaDTOVencidaStr = listaVistoriaDTOVencidaStr;
	}

	public List<VistoriaDTO> getListaVistoriaDTONaoFinalizada() {
		return listaVistoriaDTONaoFinalizada;
	}

	public void setListaVistoriaDTONaoFinalizada(
			List<VistoriaDTO> listaVistoriaDTONaoFinalizada) {
		this.listaVistoriaDTONaoFinalizada = listaVistoriaDTONaoFinalizada;
	}

	public List<DoacaoDTO> getListaDoacaoDTOAVencerStr() {
		return listaDoacaoDTOAVencerStr;
	}

	public void setListaDoacaoDTOAVencerStr(List<DoacaoDTO> listaDoacaoDTOAVencerStr) {
		this.listaDoacaoDTOAVencerStr = listaDoacaoDTOAVencerStr;
	}

	public List<DoacaoDTO> getListaDoacaoDTONaoFinalizada() {
		return listaDoacaoDTONaoFinalizada;
	}

	public void setListaDoacaoDTONaoFinalizada(
			List<DoacaoDTO> listaDoacaoDTONaoFinalizada) {
		this.listaDoacaoDTONaoFinalizada = listaDoacaoDTONaoFinalizada;
	}

	public List<DoacaoDTO> getListaDoacaoDTOVencida() {
		return listaDoacaoDTOVencida;
	}

	public void setListaDoacaoDTOVencida(List<DoacaoDTO> listaDoacaoDTOVencida) {
		this.listaDoacaoDTOVencida = listaDoacaoDTOVencida;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public List<CessaoDeUsoDTO> getListaCessaoDeUsoVencerStr() {
		return listaCessaoDeUsoVencerStr;
	}

	public void setListaCessaoDeUsoVencerStr(
			List<CessaoDeUsoDTO> listaCessaoDeUsoVencerStr) {
		this.listaCessaoDeUsoVencerStr = listaCessaoDeUsoVencerStr;
	}

	public List<CessaoDeUsoDTO> getListaCessaoDeUsoEmPreenchimentoStr() {
		return listaCessaoDeUsoEmPreenchimentoStr;
	}

	public void setListaCessaoDeUsoEmPreenchimentoStr(
			List<CessaoDeUsoDTO> listaCessaoDeUsoEmPreenchimentoStr) {
		this.listaCessaoDeUsoEmPreenchimentoStr = listaCessaoDeUsoEmPreenchimentoStr;
	}

	public List<CessaoDeUsoDTO> getListaCessaoDeUsoEmRenovacaoStr() {
		return listaCessaoDeUsoEmRenovacaoStr;
	}

	public void setListaCessaoDeUsoEmRenovacaoStr(
			List<CessaoDeUsoDTO> listaCessaoDeUsoEmRenovacaoStr) {
		this.listaCessaoDeUsoEmRenovacaoStr = listaCessaoDeUsoEmRenovacaoStr;
	}

	public List<CessaoDeUsoDTO> getListaCessaoDeUsoDTOVencida() {
		return listaCessaoDeUsoDTOVencida;
	}

	public void setListaCessaoDeUsoDTOVencida(
			List<CessaoDeUsoDTO> listaCessaoDeUsoDTOVencida) {
		this.listaCessaoDeUsoDTOVencida = listaCessaoDeUsoDTOVencida;
	}

	public ParametroAgenda getParametroAgenda() {
		return parametroAgenda;
	}

	public void setParametroAgenda(ParametroAgenda parametroAgenda) {
		this.parametroAgenda = parametroAgenda;
	}

	public String getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}
	
	
}

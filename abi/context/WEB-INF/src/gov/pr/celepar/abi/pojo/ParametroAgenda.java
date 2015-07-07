package gov.pr.celepar.abi.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ParametroAgenda implements java.io.Serializable {

	private static final long serialVersionUID = 933963474672049417L;
	private Integer codParametroAgenda;
    private Integer numeroDiasVencimentoNotificacao;
    private Integer numeroDiasVencimentoCessaoDeUso;
    private Integer numeroDiasVencimentoDoacao;
    private Integer numeroDiasVencimentoVistoria;
    private Integer tempoCessao;
    private Date tsInclusao;
    private Date tsAtualizacao;
    private String cpfResponsavel;
    private Instituicao instituicao;
    private Set<ParametroAgendaEmail> listaParametroAgendaEmail = new HashSet<ParametroAgendaEmail>(0);
    
    public ParametroAgenda() {
    }


	public ParametroAgenda getInstanciaAtual() {
		return this;
	}

	
	
	public Integer getCodParametroAgenda() {
		return codParametroAgenda;
	}


	public void setCodParametroAgenda(Integer codParametroAgenda) {
		this.codParametroAgenda = codParametroAgenda;
	}


	public Integer getNumeroDiasVencimentoNotificacao() {
		return numeroDiasVencimentoNotificacao;
	}


	public void setNumeroDiasVencimentoNotificacao(
			Integer numeroDiasVencimentoNotificacao) {
		this.numeroDiasVencimentoNotificacao = numeroDiasVencimentoNotificacao;
	}


	public Integer getNumeroDiasVencimentoCessaoDeUso() {
		return numeroDiasVencimentoCessaoDeUso;
	}


	public void setNumeroDiasVencimentoCessaoDeUso(
			Integer numeroDiasVencimentoCessaoDeUso) {
		this.numeroDiasVencimentoCessaoDeUso = numeroDiasVencimentoCessaoDeUso;
	}


	public Integer getNumeroDiasVencimentoDoacao() {
		return numeroDiasVencimentoDoacao;
	}


	public void setNumeroDiasVencimentoDoacao(Integer numeroDiasVencimentoDoacao) {
		this.numeroDiasVencimentoDoacao = numeroDiasVencimentoDoacao;
	}


	public Integer getNumeroDiasVencimentoVistoria() {
		return numeroDiasVencimentoVistoria;
	}


	public void setNumeroDiasVencimentoVistoria(Integer numeroDiasVencimentoVistoria) {
		this.numeroDiasVencimentoVistoria = numeroDiasVencimentoVistoria;
	}


	public Date getTsInclusao() {
		return tsInclusao;
	}


	public void setTsInclusao(Date tsInclusao) {
		this.tsInclusao = tsInclusao;
	}


	public Date getTsAtualizacao() {
		return tsAtualizacao;
	}


	public void setTsAtualizacao(Date tsAtualizacao) {
		this.tsAtualizacao = tsAtualizacao;
	}


	public String getCpfResponsavel() {
		return cpfResponsavel;
	}


	public void setCpfResponsavel(String cpfResponsavel) {
		this.cpfResponsavel = cpfResponsavel;
	}


	public Set<ParametroAgendaEmail> getListaParametroAgendaEmail() {
		return listaParametroAgendaEmail;
	}


	public void setListaParametroAgendaEmail(
			Set<ParametroAgendaEmail> listaParametroAgendaEmail) {
		this.listaParametroAgendaEmail = listaParametroAgendaEmail;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParametroAgenda other = (ParametroAgenda) obj;
		if (codParametroAgenda == null) {
			if (other.codParametroAgenda != null)
				return false;
		} else if (!codParametroAgenda.equals(other.codParametroAgenda))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codParametroAgenda == null) ? 0 : codParametroAgenda.hashCode());
		return result;
	}


	public void setTempoCessao(Integer tempoCessao) {
		this.tempoCessao = tempoCessao;
	}


	public Integer getTempoCessao() {
		return tempoCessao;
	}


	public Instituicao getInstituicao() {
		return instituicao;
	}


	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}
	
}

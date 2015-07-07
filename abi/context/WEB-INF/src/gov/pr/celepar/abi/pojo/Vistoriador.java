package gov.pr.celepar.abi.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Vistoriador implements Serializable {

	private static final long serialVersionUID = 6923346149981581481L;

	private Integer codVistoriador;
	private Set<Vistoria> listaVistoria = new HashSet<Vistoria>(0);
	private String cpf;
	private String nome;
	private Date tsInclusao;
    private Date tsAtualizacao;
    private String cpfResponsavel;
    private Instituicao instituicao;
     				
	public Vistoriador() {
	}

	public Vistoriador(Integer codVistoriador, Set<Vistoria> listaVistoria,
			String cpf, String nome, Date tsInclusao, Date tsAtualizacao,
			String cpfResponsavel) {
		super();
		this.codVistoriador = codVistoriador;
		this.listaVistoria = listaVistoria;
		this.cpf = cpf;
		this.nome = nome;
		this.tsInclusao = tsInclusao;
		this.tsAtualizacao = tsAtualizacao;
		this.cpfResponsavel = cpfResponsavel;
	}

	public Integer getCodVistoriador() {
		return codVistoriador;
	}

	public void setCodVistoriador(Integer codVistoriador) {
		this.codVistoriador = codVistoriador;
	}

	public Set<Vistoria> getListaVistoria() {
		return listaVistoria;
	}

	public void setListaVistoria(Set<Vistoria> listaVistoria) {
		this.listaVistoria = listaVistoria;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codVistoriador == null) ? 0 : codVistoriador.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vistoriador other = (Vistoriador) obj;
		if (codVistoriador == null) {
			if (other.codVistoriador != null)
				return false;
		} else if (!codVistoriador.equals(other.codVistoriador))
			return false;
		return true;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}
    
    
    
}

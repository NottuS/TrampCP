package gov.pr.celepar.abi.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Transient;

public class Assinatura implements java.io.Serializable {

	private static final long serialVersionUID = 9172396344672049417L;
	private Integer codAssinatura;
    private Integer administracao;
    private Orgao orgao;
    private CargoAssinatura cargoAssinatura;
    private String cpf;
    private String nome;
    private Boolean indAtivo;
    private Date tsInclusao;
    private Date tsAtualizacao;
    private Date tsExclusao;
    private String cpfResponsavelInclusao;
    private String cpfResponsavelAlteracao;
    private String cpfResponsavelExclusao;
    private Instituicao instituicao;
    private Boolean indResponsavelMaximo;
    private Set<AssinaturaDoacao> listaAssinaturaDoacao = new HashSet<AssinaturaDoacao>(0);
	private Set<AssinaturaTransferencia> listaAssinaturaTransferencia = new HashSet<AssinaturaTransferencia>(0);
	private Set<AssinaturaDocTransferencia> listaAssinaturaDocTransferencia = new HashSet<AssinaturaDocTransferencia>(0);
    
	public Assinatura() {
    }

	public Assinatura(Integer codAssinatura, Orgao orgao,
			CargoAssinatura cargo, String cpf, String nome, Boolean indAtivo,
			Date tsInclusao, Date tsAtualizacao, Date tsExclusao,
			String cpfResponsavelInclusao, String cpfResponsavelAlteracao,
			String cpfResponsavelExclusao, Integer administracao) {
		super();
		this.codAssinatura = codAssinatura;
		this.orgao = orgao;
		this.cargoAssinatura = cargo;
		this.cpf = cpf;
		this.nome = nome;
		this.indAtivo = indAtivo;
		this.tsInclusao = tsInclusao;
		this.tsAtualizacao = tsAtualizacao;
		this.tsExclusao = tsExclusao;
		this.cpfResponsavelInclusao = cpfResponsavelInclusao;
		this.cpfResponsavelAlteracao = cpfResponsavelAlteracao;
		this.cpfResponsavelExclusao = cpfResponsavelExclusao;
		this.administracao = administracao;
	}


	public Integer getCodAssinatura() {
		return codAssinatura;
	}

	public void setCodAssinatura(Integer codAssinatura) {
		this.codAssinatura = codAssinatura;
	}

	public Orgao getOrgao() {
		return orgao;
	}

	public void setOrgao(Orgao orgao) {
		this.orgao = orgao;
	}

	public CargoAssinatura getCargoAssinatura() {
		return cargoAssinatura;
	}

	public void setCargoAssinatura(CargoAssinatura cargo) {
		this.cargoAssinatura = cargo;
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

	public Boolean getIndAtivo() {
		return indAtivo;
	}

	public void setIndAtivo(Boolean indAtivo) {
		this.indAtivo = indAtivo;
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

	public Date getTsExclusao() {
		return tsExclusao;
	}

	public void setTsExclusao(Date tsExclusao) {
		this.tsExclusao = tsExclusao;
	}

	public String getCpfResponsavelInclusao() {
		return cpfResponsavelInclusao;
	}

	public void setCpfResponsavelInclusao(String cpfResponsavelInclusao) {
		this.cpfResponsavelInclusao = cpfResponsavelInclusao;
	}

	public String getCpfResponsavelAlteracao() {
		return cpfResponsavelAlteracao;
	}

	public void setCpfResponsavelAlteracao(String cpfResponsavelAlteracao) {
		this.cpfResponsavelAlteracao = cpfResponsavelAlteracao;
	}

	public String getCpfResponsavelExclusao() {
		return cpfResponsavelExclusao;
	}

	public void setCpfResponsavelExclusao(String cpfResponsavelExclusao) {
		this.cpfResponsavelExclusao = cpfResponsavelExclusao;
	}

	public Set<AssinaturaDoacao> getListaAssinaturaDoacao() {
		return listaAssinaturaDoacao;
	}

	public void setListaAssinaturaDoacao(Set<AssinaturaDoacao> listaAssinaturaDoacao) {
		this.listaAssinaturaDoacao = listaAssinaturaDoacao;
	}

	public void setListaAssinaturaTransferencia(
			Set<AssinaturaTransferencia> listaAssinaturaTransferencia) {
		this.listaAssinaturaTransferencia = listaAssinaturaTransferencia;
	}

	public Set<AssinaturaTransferencia> getListaAssinaturaTransferencia() {
		return listaAssinaturaTransferencia;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Assinatura other = (Assinatura) obj;
		if (codAssinatura == null) {
			if (other.codAssinatura != null)
				return false;
		} else if (!codAssinatura.equals(other.codAssinatura))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codAssinatura == null) ? 0 : codAssinatura.hashCode());
		return result;
	}

	public void setAdministracao(Integer administracao) {
		this.administracao = administracao;
	}

	public Integer getAdministracao() {
		return administracao;
	}

	@Transient
	public Assinatura getInstanciaAtual() {
		return this;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setIndResponsavelMaximo(Boolean indResponsavelMaximo) {
		this.indResponsavelMaximo = indResponsavelMaximo;
	}

	public Boolean getIndResponsavelMaximo() {
		return indResponsavelMaximo;
	}

	@Transient
	public String getIndResponsavelMaximoDesc() {
		if (this.getIndResponsavelMaximo()) {
			return "Sim";
		} else {
			return "Não";
		}
	}

	public void setListaAssinaturaDocTransferencia(
			Set<AssinaturaDocTransferencia> listaAssinaturaDocTransferencia) {
		this.listaAssinaturaDocTransferencia = listaAssinaturaDocTransferencia;
	}

	public Set<AssinaturaDocTransferencia> getListaAssinaturaDocTransferencia() {
		return listaAssinaturaDocTransferencia;
	}

}

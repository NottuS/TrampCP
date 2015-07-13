package gov.pr.celepar.ucs_manterinstituicao.pojo;

import java.util.Date;

public class CustomList {
	private Integer idColaborador;
	private String cpf;
	private String nome;
	private Instituicao instituicao;
	private Date dataAdmissao;
	private Date dataDemissao;
	private Boolean ativo;
	
	public Integer getIdColaborador() {
		return idColaborador;
	}

	public void setIdColaborador(Integer idColaborador) {
		this.idColaborador = idColaborador;
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

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public Date getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public Date getDataDemissao() {
		return dataDemissao;
	}

	public void setDataDemissao(Date dataDemissao) {
		this.dataDemissao = dataDemissao;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public CustomList(Integer idColaborador, String cpf, String nome,
			Instituicao instituicao, Date dataAdmissao, Date dataDemissao,
			Boolean ativo) {
		super();
		this.idColaborador = idColaborador;
		this.cpf = cpf;
		this.nome = nome;
		this.instituicao = instituicao;
		this.dataAdmissao = dataAdmissao;
		this.dataDemissao = dataDemissao;
		this.ativo = ativo;
	}
}

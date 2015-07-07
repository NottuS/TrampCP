package gov.pr.celepar.abi.pojo;


public class Localidade implements java.io.Serializable {

	private static final long serialVersionUID = -6419252113067803613L;
	
	private Integer codLocalidade;
	private String tipo;
	private String localidadeSuperior;
	private String siglaUf;
	private String nome;
	private String cep;
	private String situacao;

	public Localidade() {
    }

    public Integer getCodLocalidade() {
		return codLocalidade;
	}

	public void setCodLocalidade(Integer codLocalidade) {
		this.codLocalidade = codLocalidade;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getLocalidadeSuperior() {
		return localidadeSuperior;
	}

	public void setLocalidadeSuperior(String localidadeSuperior) {
		this.localidadeSuperior = localidadeSuperior;
	}

	public String getSiglaUf() {
		return siglaUf;
	}

	public void setSiglaUf(String siglaUf) {
		this.siglaUf = siglaUf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	
	public Localidade(String cep, Integer codLocalidade, String localidadeSuperior, String nome, String siglaUf, String situacao, String tipo) {
		super();
		this.cep = cep;
		this.codLocalidade = codLocalidade;
		this.localidadeSuperior = localidadeSuperior;
		this.nome = nome;
		this.siglaUf = siglaUf;
		this.situacao = situacao;
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codLocalidade == null) ? 0 : codLocalidade.hashCode());
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
		Localidade other = (Localidade) obj;
		if (codLocalidade == null) {
			if (other.codLocalidade != null)
				return false;
		} else if (!codLocalidade.equals(other.codLocalidade))
			return false;
		return true;
	}
	
	
}

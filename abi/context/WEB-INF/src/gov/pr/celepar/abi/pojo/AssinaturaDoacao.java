package gov.pr.celepar.abi.pojo;


public class AssinaturaDoacao implements java.io.Serializable {

	private static final long serialVersionUID = 4639336432782761103L;
	private Integer codAssinaturaDoacao;
    private Doacao doacao;
    private Assinatura assinatura;
    private Integer ordem;
    
	public AssinaturaDoacao() {
    }


	public AssinaturaDoacao(Integer codAssinaturaDoacao, Doacao doacao,
			Assinatura assinatura, Integer ordem) {
		super();
		this.codAssinaturaDoacao = codAssinaturaDoacao;
		this.doacao = doacao;
		this.assinatura = assinatura;
		this.ordem = ordem;
	}


	public Integer getCodAssinaturaDoacao() {
		return codAssinaturaDoacao;
	}


	public void setCodAssinaturaDoacao(Integer codAssinaturaDoacao) {
		this.codAssinaturaDoacao = codAssinaturaDoacao;
	}


	public Doacao getDoacao() {
		return doacao;
	}


	public void setDoacao(Doacao doacao) {
		this.doacao = doacao;
	}


	public Assinatura getAssinatura() {
		return assinatura;
	}


	public void setAssinatura(Assinatura assinatura) {
		this.assinatura = assinatura;
	}


	public Integer getOrdem() {
		return ordem;
	}


	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AssinaturaDoacao other = (AssinaturaDoacao) obj;
		if (codAssinaturaDoacao == null) {
			if (other.codAssinaturaDoacao != null)
				return false;
		} else if (!codAssinaturaDoacao.equals(other.codAssinaturaDoacao))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codAssinaturaDoacao == null) ? 0 : codAssinaturaDoacao.hashCode());
		return result;
	}

}

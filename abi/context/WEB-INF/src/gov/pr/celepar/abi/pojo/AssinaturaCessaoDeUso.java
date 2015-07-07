package gov.pr.celepar.abi.pojo;


public class AssinaturaCessaoDeUso implements java.io.Serializable {

	private static final long serialVersionUID = 4639536432782761103L;
	private Integer codAssinaturaCessaoDeUso;
    private CessaoDeUso cessaoDeUso;
    private Assinatura assinatura;
    private Integer ordem;
    
	public AssinaturaCessaoDeUso() {
    }


	
	public Integer getCodAssinaturaCessaoDeUso() {
		return codAssinaturaCessaoDeUso;
	}



	public void setCodAssinaturaCessaoDeUso(Integer codAssinaturaCessaoDeUso) {
		this.codAssinaturaCessaoDeUso = codAssinaturaCessaoDeUso;
	}



	public CessaoDeUso getCessaoDeUso() {
		return cessaoDeUso;
	}



	public void setCessaoDeUso(CessaoDeUso cessaoDeUso) {
		this.cessaoDeUso = cessaoDeUso;
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
		AssinaturaCessaoDeUso other = (AssinaturaCessaoDeUso) obj;
		if (codAssinaturaCessaoDeUso == null) {
			if (other.codAssinaturaCessaoDeUso != null)
				return false;
		} else if (!codAssinaturaCessaoDeUso.equals(other.codAssinaturaCessaoDeUso))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codAssinaturaCessaoDeUso == null) ? 0 : codAssinaturaCessaoDeUso.hashCode());
		return result;
	}

}

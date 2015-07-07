package gov.pr.celepar.abi.pojo;


public class AssinaturaTransferencia implements java.io.Serializable {

	private static final long serialVersionUID = -3509540582077577061L;
	private Integer codAssinaturaTransferencia;
    private Transferencia transferencia;
    private Assinatura assinatura;
    private Integer ordem;
    
	public AssinaturaTransferencia() {
    }


	public AssinaturaTransferencia(Integer codAssinaturaTransferencia,
			Transferencia transferencia, Assinatura assinatura, Integer ordem) {
		super();
		this.codAssinaturaTransferencia = codAssinaturaTransferencia;
		this.transferencia = transferencia;
		this.assinatura = assinatura;
		this.ordem = ordem;
	}


	public Integer getCodAssinaturaTransferencia() {
		return codAssinaturaTransferencia;
	}


	public void setCodAssinaturaTransferencia(Integer codAssinaturaTransferencia) {
		this.codAssinaturaTransferencia = codAssinaturaTransferencia;
	}


	public Transferencia getTransferencia() {
		return transferencia;
	}


	public void setTransferencia(Transferencia transferencia) {
		this.transferencia = transferencia;
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
		AssinaturaTransferencia other = (AssinaturaTransferencia) obj;
		if (codAssinaturaTransferencia == null) {
			if (other.codAssinaturaTransferencia != null)
				return false;
		} else if (!codAssinaturaTransferencia.equals(other.codAssinaturaTransferencia))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codAssinaturaTransferencia == null) ? 0 : codAssinaturaTransferencia.hashCode());
		return result;
	}

}

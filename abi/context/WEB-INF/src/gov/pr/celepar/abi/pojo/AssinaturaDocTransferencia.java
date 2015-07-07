package gov.pr.celepar.abi.pojo;

public class AssinaturaDocTransferencia implements java.io.Serializable {

	private static final long serialVersionUID = 6599827084990466486L;
	private Integer codAssinaturaDocTransferencia;
    private Transferencia transferencia;
    private Assinatura assinatura;
    
	public AssinaturaDocTransferencia() {
    }

	public AssinaturaDocTransferencia(Integer codAssinaturaDocTransferencia,
			Transferencia transferencia, Assinatura assinatura) {
		super();
		this.codAssinaturaDocTransferencia = codAssinaturaDocTransferencia;
		this.transferencia = transferencia;
		this.assinatura = assinatura;
	}

	public void setCodAssinaturaDocTransferencia(
			Integer codAssinaturaDocTransferencia) {
		this.codAssinaturaDocTransferencia = codAssinaturaDocTransferencia;
	}


	public Integer getCodAssinaturaDocTransferencia() {
		return codAssinaturaDocTransferencia;
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


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AssinaturaDocTransferencia other = (AssinaturaDocTransferencia) obj;
		if (codAssinaturaDocTransferencia == null) {
			if (other.codAssinaturaDocTransferencia != null)
				return false;
		} else if (!codAssinaturaDocTransferencia.equals(other.codAssinaturaDocTransferencia))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codAssinaturaDocTransferencia == null) ? 0 : codAssinaturaDocTransferencia.hashCode());
		return result;
	}


}

package gov.pr.celepar.abi.pojo;

import java.util.HashSet;
import java.util.Set;


public class StatusTermo implements java.io.Serializable {

	private static final long serialVersionUID = 4650490777482414553L;
	private Integer codStatusTermo;
    private String descricao;
    private Set<Doacao> listaDoacao = new HashSet<Doacao>(0);
    private Set<Transferencia> listaTransferencia = new HashSet<Transferencia>(0);

    public StatusTermo() {
    }

    public StatusTermo(Integer codStatusTermo, String descricao) {
        this.codStatusTermo = codStatusTermo;
        this.descricao = descricao;
    }

    public Integer getCodStatusTermo() {
        return this.codStatusTermo;
    }

    public void setCodStatusTermo(Integer codStatusTermo) {
        this.codStatusTermo = codStatusTermo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatusTermo other = (StatusTermo) obj;
		if (codStatusTermo == null) {
			if (other.codStatusTermo != null)
				return false;
		} else if (!codStatusTermo.equals(other.codStatusTermo))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((codStatusTermo == null) ? 0 : codStatusTermo
						.hashCode());
		return result;
	}

	public void setListaDoacao(Set<Doacao> listaDoacao) {
		this.listaDoacao = listaDoacao;
	}

	public Set<Doacao> getListaDoacao() {
		return listaDoacao;
	}

	public void setListaTransferencia(Set<Transferencia> listaTransferencia) {
		this.listaTransferencia = listaTransferencia;
	}

	public Set<Transferencia> getListaTransferencia() {
		return listaTransferencia;
	}
}

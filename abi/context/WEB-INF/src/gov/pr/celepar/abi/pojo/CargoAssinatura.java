package gov.pr.celepar.abi.pojo;

import java.util.HashSet;
import java.util.Set;



public class CargoAssinatura implements java.io.Serializable {

	private static final long serialVersionUID = 4142056957995631671L;
	private Integer codCargoAssinatura;
    private String descricao;
    private Instituicao instituicao;
    private Set<Assinatura> listaAssinatura = new HashSet<Assinatura>(0);

    public CargoAssinatura() {
    }

    public CargoAssinatura(Integer codCargoAssinatura, String descricao) {
        this.codCargoAssinatura = codCargoAssinatura;
        this.descricao = descricao;
    }

    public Integer getCodCargoAssinatura() {
        return this.codCargoAssinatura;
    }

    public void setCodCargoAssinatura(Integer codCargoAssinatura) {
        this.codCargoAssinatura = codCargoAssinatura;
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
		CargoAssinatura other = (CargoAssinatura) obj;
		if (codCargoAssinatura == null) {
			if (other.codCargoAssinatura != null)
				return false;
		} else if (!codCargoAssinatura.equals(other.codCargoAssinatura))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((codCargoAssinatura == null) ? 0 : codCargoAssinatura
						.hashCode());
		return result;
	}

	public void setListaAssinatura(Set<Assinatura> listaAssinatura) {
		this.listaAssinatura = listaAssinatura;
	}

	public Set<Assinatura> getListaAssinatura() {
		return listaAssinatura;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}
	
}

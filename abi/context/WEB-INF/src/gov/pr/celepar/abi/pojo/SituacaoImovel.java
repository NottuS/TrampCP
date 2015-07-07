package gov.pr.celepar.abi.pojo;

import java.util.HashSet;
import java.util.Set;


/**
 * SituacaoImovel generated by hbm2java
 */
public class SituacaoImovel implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 893270819450520823L;
	private Integer codSituacaoImovel;
    private String descricao;
    private Set<BemImovel> bemImovels = new HashSet<BemImovel>(0);

    public SituacaoImovel() {
    }

    public SituacaoImovel(Integer codSituacaoImovel, String descricao) {
        this.codSituacaoImovel = codSituacaoImovel;
        this.descricao = descricao;
    }

    public SituacaoImovel(Integer codSituacaoImovel, String descricao,
        Set<BemImovel> bemImovels) {
        this.codSituacaoImovel = codSituacaoImovel;
        this.descricao = descricao;
        this.bemImovels = bemImovels;
    }

    public Integer getCodSituacaoImovel() {
        return this.codSituacaoImovel;
    }

    public void setCodSituacaoImovel(Integer codSituacaoImovel) {
        this.codSituacaoImovel = codSituacaoImovel;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<BemImovel> getBemImovels() {
        return this.bemImovels;
    }

    public void setBemImovels(Set<BemImovel> bemImovels) {
        this.bemImovels = bemImovels;
    }

    

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SituacaoImovel other = (SituacaoImovel) obj;
		if (codSituacaoImovel == null) {
			if (other.codSituacaoImovel != null)
				return false;
		} else if (!codSituacaoImovel.equals(other.codSituacaoImovel))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((codSituacaoImovel == null) ? 0 : codSituacaoImovel
						.hashCode());
		return result;
	}
}

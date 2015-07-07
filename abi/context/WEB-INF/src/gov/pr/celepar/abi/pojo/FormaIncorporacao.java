package gov.pr.celepar.abi.pojo;

import java.util.HashSet;
import java.util.Set;


/**
 * FormaIncorporacao generated by hbm2java
 */
public class FormaIncorporacao implements java.io.Serializable {
   
	private static final long serialVersionUID = 6658187168217916811L;
	private Integer codFormaIncorporacao;
    private String descricao;
    private Set<BemImovel> bemImovels = new HashSet<BemImovel>(0);
    private Set<Edificacao> edificacaos = new HashSet<Edificacao>(0);

    public FormaIncorporacao() {
    }

    public FormaIncorporacao(Integer codFormaIncorporacao, String descricao) {
        this.codFormaIncorporacao = codFormaIncorporacao;
        this.descricao = descricao;
    }

    public FormaIncorporacao(Integer codFormaIncorporacao, String descricao,
        Set<BemImovel> bemImovels, Set<Edificacao> edificacaos) {
        this.codFormaIncorporacao = codFormaIncorporacao;
        this.descricao = descricao;
        this.bemImovels = bemImovels;
        this.edificacaos = edificacaos;
    }

    public Integer getCodFormaIncorporacao() {
        return this.codFormaIncorporacao;
    }

    public void setCodFormaIncorporacao(Integer codFormaIncorporacao) {
        this.codFormaIncorporacao = codFormaIncorporacao;
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

    public Set<Edificacao> getEdificacaos() {
        return this.edificacaos;
    }

    public void setEdificacaos(Set<Edificacao> edificacaos) {
        this.edificacaos = edificacaos;
    }

    

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormaIncorporacao other = (FormaIncorporacao) obj;
		if (codFormaIncorporacao == null) {
			if (other.codFormaIncorporacao != null)
				return false;
		} else if (!codFormaIncorporacao.equals(other.codFormaIncorporacao))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((codFormaIncorporacao == null) ? 0 : codFormaIncorporacao
						.hashCode());
		return result;
	}
}
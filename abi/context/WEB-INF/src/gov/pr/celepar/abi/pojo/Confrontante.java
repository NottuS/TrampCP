package gov.pr.celepar.abi.pojo;

import java.util.Date;


/**
 * Confrontante generated by hbm2java
 */
public class Confrontante implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7385207796438259788L;
	private Integer codConfrontante;
    private BemImovel bemImovel;
    private String descricao;
    private Date tsInclusao;
    private Date tsAtualizacao;
    private String cpfResponsavel;

    public Confrontante() {
    }

    public Confrontante(Integer codConfrontante, BemImovel bemImovel,
        String descricao, Date tsInclusao, Date tsAtualizacao,
        String cpfResponsavel) {
        this.codConfrontante = codConfrontante;
        this.bemImovel = bemImovel;
        this.descricao = descricao;
        this.tsInclusao = tsInclusao;
        this.tsAtualizacao = tsAtualizacao;
        this.cpfResponsavel = cpfResponsavel;
    }

    public Integer getCodConfrontante() {
        return this.codConfrontante;
    }

    public void setCodConfrontante(Integer codConfrontante) {
        this.codConfrontante = codConfrontante;
    }

    public BemImovel getBemImovel() {
        return this.bemImovel;
    }

    public void setBemImovel(BemImovel bemImovel) {
        this.bemImovel = bemImovel;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getTsInclusao() {
        return this.tsInclusao;
    }

    public void setTsInclusao(Date tsInclusao) {
        this.tsInclusao = tsInclusao;
    }

    public Date getTsAtualizacao() {
        return this.tsAtualizacao;
    }

    public void setTsAtualizacao(Date tsAtualizacao) {
        this.tsAtualizacao = tsAtualizacao;
    }

    public String getCpfResponsavel() {
		return cpfResponsavel;
	}
    
    public void setCpfResponsavel(String cpfResponsavel) {
		this.cpfResponsavel = cpfResponsavel;
	}

    

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Confrontante other = (Confrontante) obj;
		if (codConfrontante == null) {
			if (other.codConfrontante != null)
				return false;
		} else if (!codConfrontante.equals(other.codConfrontante))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codConfrontante == null) ? 0 : codConfrontante.hashCode());
		return result;
	}
}
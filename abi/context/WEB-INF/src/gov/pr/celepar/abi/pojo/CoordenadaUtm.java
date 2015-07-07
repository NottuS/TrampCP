package gov.pr.celepar.abi.pojo;

import java.math.BigDecimal;
import java.util.Date;


/**
 * CoordenadaUtm generated by hbm2java
 */
public class CoordenadaUtm implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5321735246950739276L;
	private Integer codCoordenadaUtm;
    private BemImovel bemImovel;
    private BigDecimal coordenadaX;
    private BigDecimal coordenadaY;
    private Date tsInclusao;
    private Date tsAtualizacao;
    private String cpfResponsavel;

    public CoordenadaUtm() {
    }

    public CoordenadaUtm(Integer codCoordenadaUtm, BemImovel bemImovel,
        BigDecimal coordenadaX, BigDecimal coordenadaY, Date tsInclusao,
        Date tsAtualizacao, String cpfResponsavel) {
        this.codCoordenadaUtm = codCoordenadaUtm;
        this.bemImovel = bemImovel;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.tsInclusao = tsInclusao;
        this.tsAtualizacao = tsAtualizacao;
        this.cpfResponsavel = cpfResponsavel;
    }

    public Integer getCodCoordenadaUtm() {
        return this.codCoordenadaUtm;
    }

    public void setCodCoordenadaUtm(Integer codCoordenadaUtm) {
        this.codCoordenadaUtm = codCoordenadaUtm;
    }

    public BemImovel getBemImovel() {
        return this.bemImovel;
    }

    public void setBemImovel(BemImovel bemImovel) {
        this.bemImovel = bemImovel;
    }

    public BigDecimal getCoordenadaX() {
        return this.coordenadaX;
    }

    public void setCoordenadaX(BigDecimal coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public BigDecimal getCoordenadaY() {
        return this.coordenadaY;
    }

    public void setCoordenadaY(BigDecimal coordenadaY) {
        this.coordenadaY = coordenadaY;
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
		CoordenadaUtm other = (CoordenadaUtm) obj;
		if (codCoordenadaUtm == null) {
			if (other.codCoordenadaUtm != null)
				return false;
		} else if (!codCoordenadaUtm.equals(other.codCoordenadaUtm))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((codCoordenadaUtm == null) ? 0 : codCoordenadaUtm.hashCode());
		return result;
	}
}

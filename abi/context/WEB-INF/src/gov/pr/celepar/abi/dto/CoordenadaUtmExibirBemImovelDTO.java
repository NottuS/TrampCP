package gov.pr.celepar.abi.dto;

import java.math.BigDecimal;

public class CoordenadaUtmExibirBemImovelDTO {

    private Integer codCoordenadaUtm;
	private BigDecimal coordenadaX;
    private BigDecimal coordenadaY;
	public BigDecimal getCoordenadaX() {
		return coordenadaX;
	}
	public BigDecimal getCoordenadaY() {
		return coordenadaY;
	}
	public void setCoordenadaX(BigDecimal coordenadaX) {
		this.coordenadaX = coordenadaX;
	}
	public void setCoordenadaY(BigDecimal coordenadaY) {
		this.coordenadaY = coordenadaY;
	}
	public Integer getCodCoordenadaUtm() {
		return codCoordenadaUtm;
	}
	public void setCodCoordenadaUtm(Integer codCoordenadaUtm) {
		this.codCoordenadaUtm = codCoordenadaUtm;
	}
	
	

	
	
}

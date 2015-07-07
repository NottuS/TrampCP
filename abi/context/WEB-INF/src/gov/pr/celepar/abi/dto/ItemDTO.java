package gov.pr.celepar.abi.dto;

import java.math.BigDecimal;

public class ItemDTO implements java.io.Serializable {

	private static final long serialVersionUID = 2371810075970043836L;
	private BigDecimal metros;
    private BigDecimal percentual;
    
	public BigDecimal getMetros() {
		return metros;
	}
	public void setMetros(BigDecimal metros) {
		this.metros = metros;
	}
	public BigDecimal getPercentual() {
		return percentual;
	}
	public void setPercentual(BigDecimal percentual) {
		this.percentual = percentual;
	}

}

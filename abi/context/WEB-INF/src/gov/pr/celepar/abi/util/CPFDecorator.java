package gov.pr.celepar.abi.util;

import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;
import gov.pr.celepar.framework.util.CPF;

public class CPFDecorator implements Decorator{

	/**
	 * Metodo usado para populacao array cpfPessoaFisica <br>
	 * @author ginaalmeida, ramonmolossi
	 * @version 1.0
	 * @since 31/10/2008
	 * @param obj : Object
	 * @return String
	 * @throws Exception
	 */	
	public String decorate(Object obj) throws Exception {
		if (obj instanceof String) {
			String cpf = (String) obj;
			return CPF.formataCPF(cpf);
		}else{
			return "";
		}
	}
	
}
package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.pojo.Tabelionato;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;


/**
 * Classe de manipulação de objetos da classe Tabelionato.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public interface TabelionatoDAO extends GenericDAO<Tabelionato, Integer> {

	public Tabelionato obterTabelionatoPorDescricao(String descTabelionato) throws ApplicationException;
	
}
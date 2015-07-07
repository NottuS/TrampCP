 package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.pojo.ParametroAgenda;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

/**
 * Classe de manipulação de objetos da classe ParametroAgenda
 * 
 * 
 *
 */ 
public interface ParametroAgendaDAO extends GenericDAO<ParametroAgenda, Integer> {
	public ParametroAgenda obterUnico(Integer codInstituicao) throws ApplicationException;
}
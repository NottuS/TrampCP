 package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.pojo.ParametroAgendaEmail;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;

/**
 * Classe de manipulação de objetos da classe ParametroAgenda
 * 
 * 
 *
 */
public interface ParametroAgendaEmailDAO extends GenericDAO<ParametroAgendaEmail, Integer> {
	public Collection<ParametroAgendaEmail> listar(Integer codParametroAgenda) throws ApplicationException;
}
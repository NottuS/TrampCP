 package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.pojo.ParametroVistoriaDominio;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

/**
 * Classe de manipulação de objetos da classe ParametroVistoriaDominio
 * 
 * 
 *
 */
public interface ParametroVistoriaDominioDAO extends GenericDAO<ParametroVistoriaDominio, Integer> {
	public ParametroVistoriaDominio merge(ParametroVistoriaDominio parametroVistoriaDominio) throws ApplicationException;
}
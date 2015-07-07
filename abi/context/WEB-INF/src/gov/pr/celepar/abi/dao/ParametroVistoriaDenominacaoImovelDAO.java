 package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.pojo.ParametroVistoriaDenominacaoImovel;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

/**
 * Classe de manipulação de objetos da classe ParametroVistoriaDominio
 * 
 * 
 *
 */
public interface ParametroVistoriaDenominacaoImovelDAO extends GenericDAO<ParametroVistoriaDenominacaoImovel, Integer> {
	
	public void excluirPorParametroVistoria(Integer codParametroVistoria) throws ApplicationException;
	
}
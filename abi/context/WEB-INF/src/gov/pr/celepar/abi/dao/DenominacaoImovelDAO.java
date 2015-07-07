package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.pojo.DenominacaoImovel;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.List;

/**
 * Classe de manipulação de objetos da classe DenominacaoImovel.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public interface DenominacaoImovelDAO extends GenericDAO<DenominacaoImovel, Integer> {
	public List<DenominacaoImovel> listarExceto(List<Integer> listaCodDenominacaoImovel) throws ApplicationException;
	public List<DenominacaoImovel> listarContenha(List<Integer> listaCodDenominacaoImovel) throws ApplicationException;


}

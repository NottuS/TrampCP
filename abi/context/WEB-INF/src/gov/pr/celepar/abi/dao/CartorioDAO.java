package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.pojo.Cartorio;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;


/**
 * Classe de manipulação de objetos da classe Cartorio.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public interface CartorioDAO extends GenericDAO<Cartorio, Integer> {

	public Cartorio obterCartorioPorDescricao(String descCartorio) throws ApplicationException;

}

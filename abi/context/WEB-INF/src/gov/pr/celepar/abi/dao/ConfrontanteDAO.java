package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.dto.ConfrontanteExibirBemImovelDTO;
import gov.pr.celepar.abi.pojo.Confrontante;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;

/**
 * Classe de manipulação de objetos da classe Confrontante.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public interface ConfrontanteDAO extends GenericDAO<Confrontante, Integer> {

	public Collection<ConfrontanteExibirBemImovelDTO> listarPorBemImovelExibir(Integer codBemImovel) throws ApplicationException;
	
	public Collection<Confrontante> listarPorBemImovel(Integer bemImovel) throws ApplicationException;

	public Confrontante salvarConfrontante(Confrontante confrontante) throws ApplicationException;

}

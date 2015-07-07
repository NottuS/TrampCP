package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.dto.CoordenadaUtmExibirBemImovelDTO;
import gov.pr.celepar.abi.pojo.CoordenadaUtm;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;

/**
 * Classe de manipulação de objetos da classe CoordenadaUtm.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public interface CoordenadaUtmDAO extends GenericDAO<CoordenadaUtm, Integer> {

	public Collection<CoordenadaUtmExibirBemImovelDTO> listarPorBemImovelExibir(Integer codBemImovel) throws ApplicationException;

	public CoordenadaUtm salvarCoordenadaUtm(CoordenadaUtm coordenadaUtm) throws ApplicationException;

}

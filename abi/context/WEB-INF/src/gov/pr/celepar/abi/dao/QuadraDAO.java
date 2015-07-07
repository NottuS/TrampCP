package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.Quadra;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;


/**
 * Classe de manipulação de objetos da classe Quadra.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public interface QuadraDAO extends GenericDAO<Quadra, Integer> {

	public Collection<Quadra> listarPorBemImovel(Integer bemImovel) throws ApplicationException;
	public Quadra salvarQuadra(Quadra quadra) throws ApplicationException;
	public Quadra merge(Quadra quadra) throws ApplicationException;
	public Collection<Quadra> listarQuadraSemLote(BemImovel bemImovel) throws ApplicationException;
}

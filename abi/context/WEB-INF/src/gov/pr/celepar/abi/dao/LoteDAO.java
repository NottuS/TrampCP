package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.dto.LoteExibirBemImovelDTO;
import gov.pr.celepar.abi.pojo.Lote;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;

/**
 * Classe de manipulação de objetos da classe Lote.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public interface LoteDAO extends GenericDAO<Lote, Integer> {

	public Collection<Lote> listarPorBemImovel(Integer bemImovel) throws ApplicationException;
	
	public Lote obterComRelacionamento (Integer codLote) throws ApplicationException;

	public Collection<Lote> listarComRelacionamentos(Integer codBemImovel) throws ApplicationException;
	
	public Collection<LoteExibirBemImovelDTO> listarPorBemImovelExibir(Integer codBemImovel) throws ApplicationException;
	
	public Collection<Lote> listarPorQuadra(Integer quadra) throws ApplicationException;
	
	public Lote salvarLote(Lote lote) throws ApplicationException;

}

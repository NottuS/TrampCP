package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.dto.AvaliacaoExibirBemImovelDTO;
import gov.pr.celepar.abi.pojo.Avaliacao;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;

/**
 * Classe de manipulação de objetos da classe Avaliacao.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public interface AvaliacaoDAO extends GenericDAO<Avaliacao, Integer> {


	public Collection<Avaliacao> listarComRelacionamentos(Integer codBemImovel, Integer qtdPagina, Integer numPagina) throws ApplicationException;
	
	public Collection<AvaliacaoExibirBemImovelDTO> listarPorBemImovelExibir(Integer codBemImovel) throws ApplicationException;

	public Avaliacao salvarAvaliacao (Avaliacao avaliacao) throws ApplicationException;
	
}

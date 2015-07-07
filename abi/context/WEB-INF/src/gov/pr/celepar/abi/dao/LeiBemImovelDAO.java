package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.dto.LeiBemImovelExibirBemImovelDTO;
import gov.pr.celepar.abi.pojo.LeiBemImovel;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;

/**
 * Classe de manipulação de objetos da classe LeiBemImovel.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public interface LeiBemImovelDAO extends GenericDAO<LeiBemImovel, Integer> {

	public Collection<LeiBemImovel> listarComRelacionamentos(Integer codBemImovel, Integer qtdPagina, Integer numPagina) throws ApplicationException;
	
	public Collection<LeiBemImovelExibirBemImovelDTO> listarPorBemImovel(Integer codBemImovel) throws ApplicationException;

	public Collection<LeiBemImovel> listarPorBemImovelTipo(LeiBemImovel lei) throws ApplicationException;

	public Collection<LeiBemImovel> listarPorExcetoTipoLei(LeiBemImovel lei) throws ApplicationException;

	public LeiBemImovel obterCompleto(Integer codLeiBemImovel) throws ApplicationException;

	public LeiBemImovel salvarLeiBemImovel(LeiBemImovel leiBemImovel) throws ApplicationException;

	public LeiBemImovel obterByNumero(Long numeroLei) throws ApplicationException;
}

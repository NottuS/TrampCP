package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.pojo.ItemTransferencia;
import gov.pr.celepar.abi.pojo.StatusTermo;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;

/**
 * Classe de manipulação de objetos da classe Notificacao.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public interface ItemTransferenciaDAO extends GenericDAO<ItemTransferencia, Integer> {

	public Collection<ItemTransferencia> listar(Integer codTransferencia) throws ApplicationException;
	public Collection<ItemTransferencia> listarByBemImovelEdificacaoStatus(Integer codBemImovel, Integer codEdificacao, StatusTermo status) throws ApplicationException;
}


package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.pojo.CessaoDeUso;
import gov.pr.celepar.abi.pojo.ItemCessaoDeUso;
import gov.pr.celepar.abi.pojo.Orgao;
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
public interface ItemCessaoDeUsoDAO extends GenericDAO<ItemCessaoDeUso, Integer> {

	public Collection<ItemCessaoDeUso> listar(Integer codCessaoDeUso) throws ApplicationException;
	public Collection<Orgao> verificaPercentualCedido(CessaoDeUso cessaoDeUso) throws ApplicationException;
	public Collection<ItemCessaoDeUso> listarByBemImovelEdificacaoStatus(Integer codBemImovel, Integer codEdificacao, StatusTermo status) throws ApplicationException;
	
}


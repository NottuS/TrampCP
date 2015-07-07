package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.pojo.AssinaturaCessaoDeUso;
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
public interface AssinaturaCessaoDeUsoDAO extends GenericDAO<AssinaturaCessaoDeUso, Integer> {

	public Collection<AssinaturaCessaoDeUso> listar(Integer codCessaoDeUso) throws ApplicationException;
	public Collection<AssinaturaCessaoDeUso> listaVerificacao(AssinaturaCessaoDeUso assinaturaCessaoDeUso) throws ApplicationException;

}


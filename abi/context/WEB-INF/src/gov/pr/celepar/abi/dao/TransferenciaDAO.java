package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.pojo.Transferencia;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;
import java.util.List;

/**
 * Classe de manipulacao de objetos da classe Transferencia.
 * @author ginaalmeida
 * @since 02/08/2011
 * @version 1.0
 */
public interface TransferenciaDAO extends GenericDAO<Transferencia, Integer> {
	
	public Transferencia obterTransferenciaCompleto(Integer codTransferencia) throws ApplicationException;
	public Transferencia salvarTransferencia(Transferencia transferencia) throws ApplicationException;
	public Collection<Transferencia> listar(Integer qtdPagina, Integer numPagina, Transferencia transferencia, List<Integer> listaCodOrgao) throws ApplicationException;
	public List<Transferencia> listarTransferencia(Transferencia transferencia) throws ApplicationException;
	
}


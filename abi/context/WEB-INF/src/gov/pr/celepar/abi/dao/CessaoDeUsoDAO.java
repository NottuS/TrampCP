package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.pojo.CessaoDeUso;
import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.abi.pojo.ParametroAgenda;
import gov.pr.celepar.abi.pojo.StatusTermo;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;
import java.util.List;

/**
 * Classe de manipulacao de objetos da classe Notificacao.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public interface CessaoDeUsoDAO extends GenericDAO<CessaoDeUso, Integer> {
	
	public CessaoDeUso obterCessaoDeUsoCompleto(Integer codCessaoDeUso) throws ApplicationException;
	public Collection<CessaoDeUso> listarVencidaAVencer(Instituicao instituicao, ParametroAgenda parametroAgenda) throws ApplicationException;
	public List<CessaoDeUso> listarCessaoDeUsoPorStatus(Instituicao instituicao, StatusTermo statusTermo) throws ApplicationException;

	public Collection<CessaoDeUso> listar(Integer qtdPagina, Integer numPagina, CessaoDeUso cessaoDeUso) throws ApplicationException;
	public List<CessaoDeUso> listarCessaoDeUso(CessaoDeUso cessaoDeUso) throws ApplicationException;
	public CessaoDeUso salvarCessaoDeUso(CessaoDeUso cessaoDeUso) throws ApplicationException;
	public List<CessaoDeUso> listarCessaoDeUsoVinculada(CessaoDeUso cessaoDeUso) throws ApplicationException;

}


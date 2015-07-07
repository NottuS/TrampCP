package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.pojo.Doacao;
import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.abi.pojo.ParametroAgenda;
import gov.pr.celepar.abi.pojo.StatusTermo;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;
import java.util.List;

/**
 * Classe de manipulação de objetos da classe Notificacao.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public interface DoacaoDAO extends GenericDAO<Doacao, Integer> {
	public Collection<Doacao> listarVencidaAVencer(Instituicao instituicao, ParametroAgenda parametroAgenda) throws ApplicationException;
	public List<Doacao> listarDoacaoPorStatus(StatusTermo statusTermo, Instituicao instituicao) throws ApplicationException;
	public Collection<Doacao> listar(Integer qtdPagina, Integer numPagina, Doacao doacao) throws ApplicationException;
	public List<Doacao> listarDoacao(Doacao doacao) throws ApplicationException;
	public Doacao salvarDoacao(Doacao doacao) throws ApplicationException;
	public Doacao obterDoacaoCompleto(Integer codDoacao) throws ApplicationException;
}


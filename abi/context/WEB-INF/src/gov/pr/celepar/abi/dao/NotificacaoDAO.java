package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.dto.FiltroRelatorioNotificacaoBemImovelDTO;
import gov.pr.celepar.abi.dto.RelatorioNotificacaoDTO;
import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.abi.pojo.Notificacao;
import gov.pr.celepar.abi.pojo.ParametroAgenda;
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
public interface NotificacaoDAO extends GenericDAO<Notificacao, Integer> {

	public Collection<Notificacao> listarComRelacionamentos(FiltroRelatorioNotificacaoBemImovelDTO rnbiDTO) throws ApplicationException;

	public Notificacao salvarNotificacao (Notificacao notificacao) throws ApplicationException;
	public Collection<Notificacao> listarVencidaAVencer(Instituicao instituicao,ParametroAgenda parametroAgenda) throws ApplicationException;
	public Collection<RelatorioNotificacaoDTO> listarParaRelatorioNotificacao(FiltroRelatorioNotificacaoBemImovelDTO rnbiDTO) throws ApplicationException;
}

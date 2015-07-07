package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.dto.DocumentacaoNotificacaoExibirBemImovelDTO;
import gov.pr.celepar.abi.dto.DocumentacaoSemNotificacaoExibirBemImovelDTO;
import gov.pr.celepar.abi.pojo.Documentacao;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;
import java.util.List;

/**
 * Classe de manipulação de objetos da classe Documentacao.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public interface DocumentacaoDAO extends GenericDAO<Documentacao, Integer> {

	public List<Documentacao> listarDocumentacoesSemNotificacao(Integer codImovel) throws ApplicationException;	

	public List<Documentacao> listarDocumentacaoSemOcorrencia(Integer codBemImovel, Integer qtdPagina, Integer numPagina, boolean listarAnexos) throws ApplicationException;
	
	public Long buscarQtdListaDocumentacaoSemOcorrencia( Integer codBemImovel, boolean listarAnexos) throws ApplicationException;
	
	public Documentacao obterComRelacionamentos(Integer codDocumentacao) throws ApplicationException, Exception;

	public Collection<DocumentacaoNotificacaoExibirBemImovelDTO> listarDocumentacaoNotificacaoPorBemImovelExibir(Integer codBemImovel) throws ApplicationException;	

	public Collection<DocumentacaoSemNotificacaoExibirBemImovelDTO> listarDocumentacaoSemNotificacaoPorBemImovelExibir(Integer codBemImovel) throws ApplicationException;

	public Documentacao salvarDocumentacao (Documentacao documentacao) throws ApplicationException;

}

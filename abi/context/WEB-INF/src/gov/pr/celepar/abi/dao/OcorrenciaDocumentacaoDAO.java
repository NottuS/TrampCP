package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.dto.OcorrenciaDocumentacaoExibirBemImovelDTO;
import gov.pr.celepar.abi.pojo.OcorrenciaDocumentacao;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;

/**
 * Classe de manipulação de objetos da classe OcorrenciaDocumentacao.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public interface OcorrenciaDocumentacaoDAO extends GenericDAO<OcorrenciaDocumentacao, Integer> {

	public Collection<OcorrenciaDocumentacao> listarComRelacionamentos(Integer codBemImovel, Integer qtdPagina, Integer numPagina) throws ApplicationException;
	public Collection<OcorrenciaDocumentacaoExibirBemImovelDTO> listarOcorrenciaDocumentacaoPorBemImovelExibir(Integer codBemImovel) throws ApplicationException;
	public OcorrenciaDocumentacao salvarOcorrenciaDocumentacao (OcorrenciaDocumentacao ocorrenciaDocumentacao) throws ApplicationException;
}

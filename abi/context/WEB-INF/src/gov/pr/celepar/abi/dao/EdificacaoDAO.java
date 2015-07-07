package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.dto.EdificacaoDTO;
import gov.pr.celepar.abi.dto.EdificacaoExibirBemImovelDTO;
import gov.pr.celepar.abi.dto.FiltroRelatorioEdificacaoDTO;
import gov.pr.celepar.abi.dto.RelatorioEdificacaoDTO;
import gov.pr.celepar.abi.pojo.Edificacao;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;

/**
 * Classe de manipulação de objetos da classe Edificacao.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public interface EdificacaoDAO extends GenericDAO<Edificacao, Integer> {

	public Collection<EdificacaoDTO> listarComRelacionamentos(Integer codBemImovel, Integer qtdPagina, Integer numPagina) throws ApplicationException;
	public Edificacao obter(Integer codEdificacao)throws ApplicationException;
	public Collection<EdificacaoExibirBemImovelDTO> listarPorBemImovelExibir(Integer codBemImovel) throws ApplicationException;	
	public Collection<RelatorioEdificacaoDTO> listarRelatorioEdificacaoBensImoveis(FiltroRelatorioEdificacaoDTO reDTO) throws ApplicationException;
	public Collection<EdificacaoExibirBemImovelDTO> listarImpressaoEdificacaoBensImoveis(Integer codBemImovel) throws ApplicationException;	
	public Edificacao obterExibir(Integer codEdificacao)throws ApplicationException;
	public Integer obterQtdPorBemImovel(Integer codBemImovel)throws ApplicationException;
	public Edificacao salvarEdificacao (Edificacao edificacao) throws ApplicationException;
	public Collection<Edificacao> listarByBemImovel (Integer codBemImovel) throws ApplicationException;

}

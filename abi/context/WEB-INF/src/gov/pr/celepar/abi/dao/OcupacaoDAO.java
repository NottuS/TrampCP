package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.dto.FiltroRelatorioEdificacaoOcupacaoDTO;
import gov.pr.celepar.abi.dto.OcupacaoExibirBemImovelDTO;
import gov.pr.celepar.abi.dto.OcupacaoListaDTO;
import gov.pr.celepar.abi.dto.OcupacaoOrgaoResponsavelListaDTO;
import gov.pr.celepar.abi.dto.RelatorioEdificacaoOcupacaoDTO;
import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.Ocupacao;
import gov.pr.celepar.abi.pojo.Orgao;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;

/**
 * Classe de manipulação de objetos da classe Ocupacao.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public interface OcupacaoDAO extends GenericDAO<Ocupacao, Integer> {

	public Collection<OcupacaoExibirBemImovelDTO> listarPorBemImovelExibir(Integer codBemImovel) throws ApplicationException;
	public Collection<RelatorioEdificacaoOcupacaoDTO> listarRelatorioEdificacaoOcupacao(FiltroRelatorioEdificacaoOcupacaoDTO freDTO) throws ApplicationException;
	public Ocupacao obterComEdificacao(Integer codOcupacao) throws ApplicationException;
	public Collection<OcupacaoListaDTO> listarporEdificacao(Integer codEdificacao, Integer qtdPagina, Integer numPagina) throws ApplicationException; 
	public Collection<OcupacaoOrgaoResponsavelListaDTO> listarPorBemImovelTerreno(Integer codBemImovel) throws ApplicationException;
	public Integer obterQtdPorBemImovel(Integer codBemImovel, Boolean ativo) throws ApplicationException;
	public Integer verificarDuplicidadeOrgaoOcupacaoTerreno(Orgao orgao, BemImovel bemImovel) throws ApplicationException;
	public Ocupacao salvarOcupacao(Ocupacao ocupacao) throws ApplicationException;
	public Collection<Ocupacao> listarRelPorBemImovelTerreno(Integer codBemImovel) throws ApplicationException;

}

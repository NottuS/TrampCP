 package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.dto.BemImovelPesquisaDTO;
import gov.pr.celepar.abi.dto.BemImovelVistoriaDTO;
import gov.pr.celepar.abi.dto.FiltroRelatorioAreaBemImovelDTO;
import gov.pr.celepar.abi.dto.FiltroRelatorioBemImovelDTO;
import gov.pr.celepar.abi.dto.ImpressaoBemImovelDTO;
import gov.pr.celepar.abi.dto.RelatorioBemImovelDTO;
import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.ParametroAgenda;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;
import java.util.List;

/**
 * Classe de manipulação de objetos da classe BemImovel.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public interface BemImovelDAO extends GenericDAO<BemImovel, Integer> {

	public Collection<BemImovel> listar(Integer qtdPagina, Integer numPagina, BemImovelPesquisaDTO bemDTO ) throws ApplicationException;
	
	public BemImovel obterExibir(Integer codBemImovel) throws ApplicationException;
	
	public BemImovel obterExibirPorInstituicao(Integer nrBemImovel, Integer codInstituicao) throws ApplicationException;

	public Integer obterQuantidadeLista(Integer qtdPagina, Integer numPagina, BemImovelPesquisaDTO bemDTO ) throws ApplicationException;
	
	public Collection<BemImovel> listarRelatorioBensImoveis(FiltroRelatorioBemImovelDTO rbiDTO) throws ApplicationException;
	
	public Collection<RelatorioBemImovelDTO> listarRelatorioAreaBensImoveis(FiltroRelatorioAreaBemImovelDTO rbiDTO) throws ApplicationException ;
	
	public BemImovel obterComEdificacaoes(Integer codBemImovel) throws ApplicationException;

	public Integer baixarBemImovel(Integer codBemImovel) throws ApplicationException ;
	
	public ImpressaoBemImovelDTO obterImpressaoBemImovel(Integer codBemImovel) throws ApplicationException ;
	
	public Collection<BemImovel> listarRelatorioGerencialBensImoveis(FiltroRelatorioBemImovelDTO rbiDTO) throws ApplicationException;
	
	public BemImovel obterCompleto(Integer codBemImovel) throws ApplicationException ;
	
	public BemImovel merge(BemImovel bemImovel) throws ApplicationException;

	public BemImovel obterParaMigracaoPorCodigo(Integer codBemImovel) throws ApplicationException;
	
	public BemImovel salvarBemImovel(BemImovel bemImovel) throws ApplicationException;
	
	public Integer obterQuantidadeListaSimplificado(Integer qtdPagina, Integer numPagina, BemImovelPesquisaDTO bemDTO) throws ApplicationException;
	
	public Collection<BemImovel> listarSimplificado(Integer qtdPagina, Integer numPagina, BemImovelPesquisaDTO bemDTO ) throws ApplicationException;
	
	public Collection<BemImovelVistoriaDTO> listarBemImovelVistoriaDTO(ParametroAgenda parametroAgenda) throws ApplicationException;

	public BemImovel obterSimplificado(Integer codBemImovel) throws ApplicationException;

	public BemImovel listarRelatorioBemImovel(ImpressaoBemImovelDTO ibiDTO, Integer codBemImovel) throws ApplicationException;
	
	public BemImovel obterParaUsuarioOrgao(Integer codBemImovel, List<Integer> listaCodOrgao) throws ApplicationException;

}
/*
 * Este programa é licenciado de acordo com a
 * LPG-AP (LICENÇA PÚBLICA GERAL PARA PROGRAMAS DE COMPUTADOR DA ADMINISTRAÇÃO PÚBLICA),
 * versão 1.1 ou qualquer versão posterior.
 * A LPG-AP deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa.
 * Caso uma cópia da LPG-AP não esteja disponível junto com este Programa, você
 * pode contatar o LICENCIANTE ou então acessar diretamente:
 * http://www.celepar.pr.gov.br/licenca/LPG-AP.pdf
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa
 * é preciso estar de acordo com os termos da LPG-AP.
 */

package gov.pr.celepar.abi.facade;

import gov.pr.celepar.abi.dao.ArquivoDAO;
import gov.pr.celepar.abi.dao.factory.DAOFactory;
import gov.pr.celepar.abi.dao.implementation.ArquivoServidorDAO;
import gov.pr.celepar.abi.dto.AvaliacaoExibirBemImovelDTO;
import gov.pr.celepar.abi.dto.BemImovelPesquisaDTO;
import gov.pr.celepar.abi.dto.ConfrontanteExibirBemImovelDTO;
import gov.pr.celepar.abi.dto.CoordenadaUtmExibirBemImovelDTO;
import gov.pr.celepar.abi.dto.DocumentacaoNotificacaoExibirBemImovelDTO;
import gov.pr.celepar.abi.dto.DocumentacaoSemNotificacaoExibirBemImovelDTO;
import gov.pr.celepar.abi.dto.EdificacaoExibirBemImovelDTO;
import gov.pr.celepar.abi.dto.FiltroRelatorioAreaBemImovelDTO;
import gov.pr.celepar.abi.dto.FiltroRelatorioBemImovelDTO;
import gov.pr.celepar.abi.dto.FiltroRelatorioEdificacaoDTO;
import gov.pr.celepar.abi.dto.FiltroRelatorioEdificacaoOcupacaoDTO;
import gov.pr.celepar.abi.dto.FiltroRelatorioNotificacaoBemImovelDTO;
import gov.pr.celepar.abi.dto.ImpressaoBemImovelDTO;
import gov.pr.celepar.abi.dto.ItemComboDTO;
import gov.pr.celepar.abi.dto.LeiBemImovelExibirBemImovelDTO;
import gov.pr.celepar.abi.dto.LoteExibirBemImovelDTO;
import gov.pr.celepar.abi.dto.OcorrenciaDocumentacaoExibirBemImovelDTO;
import gov.pr.celepar.abi.dto.OcupacaoExibirBemImovelDTO;
import gov.pr.celepar.abi.dto.OcupacaoListaDTO;
import gov.pr.celepar.abi.dto.OcupacaoOrgaoResponsavelListaDTO;
import gov.pr.celepar.abi.dto.ParametroVistoriaDTO;
import gov.pr.celepar.abi.dto.RelatorioBemImovelDTO;
import gov.pr.celepar.abi.dto.RelatorioEdificacaoDTO;
import gov.pr.celepar.abi.dto.RelatorioEdificacaoOcupacaoDTO;
import gov.pr.celepar.abi.dto.RelatorioNotificacaoDTO;
import gov.pr.celepar.abi.dto.TabelaDocumentacaoDTO;
import gov.pr.celepar.abi.dto.TabelionatoPesquisaDTO;
import gov.pr.celepar.abi.dto.UsuarioDTO;
import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.pojo.Assinatura;
import gov.pr.celepar.abi.pojo.Avaliacao;
import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.CargoAssinatura;
import gov.pr.celepar.abi.pojo.Cartorio;
import gov.pr.celepar.abi.pojo.CessaoDeUso;
import gov.pr.celepar.abi.pojo.ClassificacaoBemImovel;
import gov.pr.celepar.abi.pojo.Confrontante;
import gov.pr.celepar.abi.pojo.CoordenadaUtm;
import gov.pr.celepar.abi.pojo.DenominacaoImovel;
import gov.pr.celepar.abi.pojo.Doacao;
import gov.pr.celepar.abi.pojo.Documentacao;
import gov.pr.celepar.abi.pojo.Edificacao;
import gov.pr.celepar.abi.pojo.FormaIncorporacao;
import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.abi.pojo.ItemCessaoDeUso;
import gov.pr.celepar.abi.pojo.ItemDoacao;
import gov.pr.celepar.abi.pojo.ItemTransferencia;
import gov.pr.celepar.abi.pojo.LeiBemImovel;
import gov.pr.celepar.abi.pojo.Lote;
import gov.pr.celepar.abi.pojo.Notificacao;
import gov.pr.celepar.abi.pojo.OcorrenciaDocumentacao;
import gov.pr.celepar.abi.pojo.Ocupacao;
import gov.pr.celepar.abi.pojo.Orgao;
import gov.pr.celepar.abi.pojo.Quadra;
import gov.pr.celepar.abi.pojo.SituacaoImovel;
import gov.pr.celepar.abi.pojo.SituacaoLegalCartorial;
import gov.pr.celepar.abi.pojo.SituacaoOcupacao;
import gov.pr.celepar.abi.pojo.StatusTermo;
import gov.pr.celepar.abi.pojo.Tabelionato;
import gov.pr.celepar.abi.pojo.TipoConstrucao;
import gov.pr.celepar.abi.pojo.TipoDocumentacao;
import gov.pr.celepar.abi.pojo.TipoEdificacao;
import gov.pr.celepar.abi.pojo.TipoLeiBemImovel;
import gov.pr.celepar.abi.pojo.Transferencia;
import gov.pr.celepar.abi.pojo.Usuario;
import gov.pr.celepar.abi.pojo.UsuarioGrupoSentinela;
import gov.pr.celepar.abi.pojo.UsuarioOrgao;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.CPF;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.framework.util.Valores;
import gov.pr.celepar.sentinela.comunicacao.SentinelaAdmInterface;
import gov.pr.celepar.sentinela.comunicacao.SentinelaAdministracao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaInterface;
import gov.pr.celepar.sentinela.comunicacao.SentinelaParam;
import gov.pr.celepar.sentinela.excecao.SentinelaException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

/**
 * @author pialarissi
 * @version 1.0
 * @since 07/01/2010
 * 
 *        Classe Exemplo: Responsável por encapsular os serviços do módulo de
 *        cadastramento de imóveis e a sua toda regra de negócio.
 */
public class CadastroFacade extends SuperFacade {

	private static Logger log4j = Logger.getLogger(CadastroFacade.class);
	private static DAOFactory hibernateFactory = DAOFactory
			.getDAOFactory(DAOFactory.HIBERNATE);

	/**
	 * Busca um objeto Classificação de Bem Imóvel através de seu código.
	 * 
	 * @param codClassificacaoBemImovel
	 *            código da Classificação de Bem Imóvel a ser localizada
	 * @return ClassificacaoBemImovel
	 * @throws ApplicationException
	 */
	public static ClassificacaoBemImovel obterClassificacaoBemImovel(
			Integer codClassificacaoBemImovel) throws ApplicationException {

		return hibernateFactory.getClassificacaoBemImovelDAO().obter(
				codClassificacaoBemImovel);

	}

	/**
	 * Lista paginada de classificações de bens imóveis.
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarClassificacaoBemImovel(Pagina pag,
			String descricao) throws ApplicationException {

		ClassificacaoBemImovel classificacaoBemImovel = new ClassificacaoBemImovel();
		classificacaoBemImovel.setDescricao(descricao);

		try {
			if (pag.getTotalRegistros() == 0) {
				pag.setTotalRegistros(hibernateFactory
						.getClassificacaoBemImovelDAO().buscarQtdLista(
								classificacaoBemImovel).intValue());
			}
			pag.setRegistros(hibernateFactory.getClassificacaoBemImovelDAO()
					.listar(classificacaoBemImovel,
							new String[] { "descricao", "asc" },
							pag.getQuantidade(), pag.getPaginaAtual()));
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Classificação de Bem Imóvel" }, e);
		}

		return pag;
	}

	/**
	 * Verifica se já existe Classificação de Bem Imóvel com mesmo nome.
	 * 
	 * @param String
	 *            descricaoNovaClassificacao
	 * @return Boolean - true se existe classificação, false se classificação
	 *         não existe.
	 * @throws ApplicationException
	 */

	public static Boolean verificaClassificacaoBemImovelDuplicado(
			String descricaoNovaClassificacao) throws ApplicationException {

		String descricaoBanco;
		String descricaoNovaClassificacaoTratada = descricaoNovaClassificacao
				.trim().toUpperCase();

		try {
			// verifica se não existe classificação com o mesma descrição
			Collection<ClassificacaoBemImovel> listaClassificacaoBemImovel = hibernateFactory
					.getClassificacaoBemImovelDAO().listar();
			for (ClassificacaoBemImovel item : listaClassificacaoBemImovel) {
				descricaoBanco = item.getDescricao().trim().toUpperCase();
				if (descricaoBanco.equals(descricaoNovaClassificacaoTratada)) {
					return true;
				}
			}
			return false;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "classificação de bem imóvel" }, e);
		}

	}

	/**
	 * Salva objeto ClassificacaoBemImovel
	 * 
	 * @param ClassificacaoBemImovel
	 *            a ser salvo.
	 * @return Valor retornado do MainFrame
	 * @throws ApplicationException
	 */
	public static void salvarClassificacaoBemImovel(
			ClassificacaoBemImovel classificacaoBemImovel)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction(); 
			hibernateFactory.getClassificacaoBemImovelDAO().salvar(classificacaoBemImovel);
			HibernateUtil.commitTransaction(); 
		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarClassificacaoBemImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarClassificacaoBemImovel()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Classificação do Bem Imóvel" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Atualiza objeto ClassificacaoBemImovel.
	 * 
	 * @param ClassificacaoBemImovel
	 *            a ser atualizado.
	 * @throws ApplicationException
	 */
	public static void alterarClassificacaoBemImovel(
			ClassificacaoBemImovel classificacaoBemImovel)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction(); 
			hibernateFactory.getClassificacaoBemImovelDAO().alterar(classificacaoBemImovel);
			HibernateUtil.commitTransaction(); 
		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarClassificacaoBemImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;

		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarClassificacaoBemImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Classificação do Bem Imóvel" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Remove objeto classificacaoBemImovel.
	 * 
	 * @param ClassificacaoBemImovel
	 *            a ser removida.
	 * @throws ApplicationException
	 */
	public static void excluirClassificacaoBemImovel(
			int codClassificacaoBemImovel) throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  

			// verifica se existe integridade referencial
			ClassificacaoBemImovel cbi = obterClassificacaoBemImovel(codClassificacaoBemImovel);
			if (cbi.getBemImovels() != null && cbi.getBemImovels().size() > 0) {
				throw new ApplicationException("AVISO.15",
						ApplicationException.ICON_AVISO);
			}
			hibernateFactory.getClassificacaoBemImovelDAO().excluir(cbi);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirClassificacaoBemImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirClassificacaoBemImovel()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Classificação do Bem Imóvel" }, ex, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Salva Bem Imóvel
	 * 
	 * @param Bem
	 *            Imóvel a ser salvo.
	 * @return voide
	 * @throws ApplicationException
	 */
	public static BemImovel salvarBemImovel(BemImovel bemImovel) throws ApplicationException {

		BemImovel bemImovelAux = null;
		try {
			HibernateUtil.currentTransaction();  
			bemImovelAux = hibernateFactory.getBemImovelDAO().salvarBemImovel(bemImovel);
			HibernateUtil.commitTransaction(); 
			
		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarBemImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarBemImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Bem Imóvel" }, ex, ApplicationException.ICON_ERRO);
		}
		return bemImovelAux;

	}

	/**
	 * Atualiza objeto Aluno.
	 * 
	 * @param Aluno
	 *            a ser atualizado.
	 * @throws ApplicationException
	 */
	public static void alterarBemImovel(BemImovel bemImovel)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getBemImovelDAO().alterar(bemImovel);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarBemImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;

		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarBemImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Bem Imóvel" }, ex, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Remove objeto bemImovel.
	 * 
	 * @param BemImovel
	 *            a ser removido.
	 * @throws ApplicationException
	 */
	public static void excluirBemImovel(int codBemImovel)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			BemImovel bi = obterBemImovel(codBemImovel);
			hibernateFactory.getBemImovelDAO().excluir(bi);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirBemImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirBemImovel()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Bem Imóvel" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**/
	public static Collection<ItemComboDTO> listarOrgaosCombo()
			throws ApplicationException {
		Collection<Orgao> lista = hibernateFactory.getOrgaoDAO().listar(
				new Orgao(), new String[] { "sigla", "asc" });
		Collection<ItemComboDTO> ret = new ArrayList<ItemComboDTO>();
		for (Orgao orgao : lista) {
			ItemComboDTO obj = new ItemComboDTO();
			obj.setCodigo(String.valueOf(orgao.getCodOrgao()));
			obj.setDescricao(orgao.getSigla().concat( " - ").concat( orgao.getDescricao()));
			ret.add(obj);
		}
		return ret;
	}
	
	/**/
	public static Collection<ItemComboDTO> listarOrgaosUsuarioLogadoCombo(Usuario usuario, HttpServletRequest request) throws ApplicationException {
		try {
			Collection<ItemComboDTO> ret = new ArrayList<ItemComboDTO>();
			if (usuario == null){
				throw new ApplicationException("AVISO.97");
			}
			if (verificarGrupoUsuarioLogado(request, GrupoSentinela.OPE_ORG_ABI.getCodigo())) {
				if (usuario.getListaUsuarioOrgao() == null || usuario.getListaUsuarioOrgao().size() < 1){
					throw new ApplicationException("AVISO.96");
				}
				for (UsuarioOrgao usOrgao : usuario.getListaUsuarioOrgao()) {
					ItemComboDTO obj = new ItemComboDTO();
					obj.setCodigo(String.valueOf(usOrgao.getOrgao().getCodOrgao()));
					obj.setDescricao(usOrgao.getOrgao().getSigla().concat( " - ").concat( usOrgao.getOrgao().getDescricao()));
					ret.add(obj);
				}
			} else {
				ret = null;
			}
			return ret;

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Órgãos por Usuário Logado" }, e);
		}

	}

	// * Métodos do Cláudio - Fim *

	// * * * * * * * * * * * * * * * *
	// * Métodos da Luciana - Início *
	// * * * * * * * * * * * * * * * *

	/**
	 * Lista paginada de formas de incorporacao de imóveis.
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarOrgao(Pagina pag, String descricao, String sigla, Integer codInstituicao)
			throws ApplicationException {

		Orgao orgao = new Orgao();
		orgao.setDescricao(descricao);
		orgao.setSigla(sigla);

		try {
			if (pag.getTotalRegistros() == 0) {
				pag.setTotalRegistros(hibernateFactory.getOrgaoDAO().obterQuantidadeLista(pag.getQuantidade(), pag.getPaginaAtual(), sigla, descricao, codInstituicao));
			}
			pag.setRegistros(hibernateFactory.getOrgaoDAO().listar(pag.getQuantidade(), pag.getPaginaAtual(), sigla, descricao, codInstituicao));
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Órgão" }, e);
		}

		return pag;
	}

	/**
	 * Lista paginada de catorios.
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarCartorio(Pagina pag, Cartorio cartorio)
			throws ApplicationException {

		try {

			if (pag.getTotalRegistros() == 0) {

				pag.setTotalRegistros(hibernateFactory.getCartorioDAO()
						.buscarQtdLista(cartorio).intValue());
			}
			pag.setRegistros(hibernateFactory.getCartorioDAO().listar(cartorio,
					new String[] { "descricao", "asc" }, pag.getQuantidade(),
					pag.getPaginaAtual()));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Cartório" }, e);
		}

		return pag;
	}
	
	
	/**
	 * Lista paginada de instituicoes.
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarInstituicao(Pagina pag, Instituicao instituicao)
			throws ApplicationException {

		try {

			if (pag.getTotalRegistros() == 0) {

				pag.setTotalRegistros(hibernateFactory.getInstituicaoDAO().buscarQtdLista(instituicao).intValue());
					
			}
			pag.setRegistros(hibernateFactory.getInstituicaoDAO().listar(instituicao,
					new String[] {"sigla", "asc"}, pag.getQuantidade(),
					pag.getPaginaAtual()));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Instituição" }, e);
		}

		return pag;
	}

	/**
	 * Lista de paginada documentacao por bem imóvel que não tenham relação com
	 * ocorrencia documentacao
	 * @param listarAnexos 
	 * 
	 * @param Integer
	 *            -codigo do Bem Imovel.
	 * @return Collection - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarDocumentacaoSemOcorrencia(Pagina pag,
			Integer codBemImovel, boolean listarAnexos) throws ApplicationException, Exception {

		Documentacao documentacao = new Documentacao();
		documentacao.setBemImovel(obterBemImovel(codBemImovel));
		List<Documentacao> listaDocs = new ArrayList<Documentacao>();
		List<TabelaDocumentacaoDTO> listaFinal = new ArrayList<TabelaDocumentacaoDTO>();

		try {

			if (pag.getTotalRegistros() == 0) {
				pag.setTotalRegistros(hibernateFactory.getDocumentacaoDAO()
						.buscarQtdListaDocumentacaoSemOcorrencia(codBemImovel, listarAnexos)
						.intValue());
			}
			listaDocs = hibernateFactory.getDocumentacaoDAO()
					.listarDocumentacaoSemOcorrencia(codBemImovel,
							pag.getQuantidade(), pag.getPaginaAtual(), listarAnexos);
			for (Documentacao item : listaDocs) {
				if (item.getNotificacaos() != null
						&& !item.getNotificacaos().isEmpty()) {
					for (Notificacao notificacao : item.getNotificacaos()) {
						TabelaDocumentacaoDTO linha = new TabelaDocumentacaoDTO();
						linha.setAnexo(item.getAnexo());
						if (item.getTipoDocumentacao()!= null){
							linha.setTipoDocumentacao(item.getTipoDocumentacao().getDescricao());
						}
						linha.setCodDocumentacao(item.getCodDocumentacao());
						linha.setDescricao(item.getDescricao());
						linha.setDescricaoAnexo(item.getDescricaoAnexo());
						linha.setResponsavelDocumentacao(item.getResponsavelDocumentacao());
						linha.setTsAtualizacao(item.getTsAtualizacao());
						linha.setTsInclusao(item.getTsInclusao());
						linha.setCodNotificacao(notificacao.getCodNotificacao());
						linha.setTsNotificacao(notificacao.getDataNotificacao());
						linha.setTsPrazoNotificacao(notificacao.getPrazoNotificacao());
						if (item.getCartorio()!= null){
							linha.setCartorio(item.getCartorio().getDescricao());
						}
						linha.setNumDocCartorial(item.getNumeroDocumentoCartorial());
						if (item.getTabelionato()!= null){
							linha.setTabelionato(item.getTabelionato().getDescricao());
						}
						linha.setNumDocTabelional(item.getNumeroDocumentoTabelional());
						linha.setNiif(item.getNiif());
						linha.setNirf(item.getNirf());
						linha.setIncra(item.getIncra());
						if (item.getBemImovel().getClassificacaoBemImovel()!= null){
							linha.setClassificacaoBemImovel(item.getBemImovel().getClassificacaoBemImovel().getCodClassificacaoBemImovel());
						}
						listaFinal.add(linha);
						
					}
				} else {
					TabelaDocumentacaoDTO linha = new TabelaDocumentacaoDTO();
					linha.setAnexo(item.getAnexo());
					linha.setCodDocumentacao(item.getCodDocumentacao());
					linha.setDescricao(item.getDescricao());
					linha.setDescricaoAnexo(item.getDescricaoAnexo());
					linha.setResponsavelDocumentacao(item.getResponsavelDocumentacao());
					linha.setTsAtualizacao(item.getTsAtualizacao());
					linha.setTsInclusao(item.getTsInclusao());
					if (item.getTipoDocumentacao()!= null){
						linha.setTipoDocumentacao(item.getTipoDocumentacao().getDescricao());
					}
					if (item.getCartorio()!= null){
						linha.setCartorio(item.getCartorio().getDescricao());
					}
					linha.setNumDocCartorial(item.getNumeroDocumentoCartorial());
					if (item.getTabelionato()!= null){
						linha.setTabelionato(item.getTabelionato().getDescricao());
					}
					linha.setNumDocTabelional(item.getNumeroDocumentoTabelional());
					linha.setNiif(item.getNiif());
					linha.setNirf(item.getNirf());
					linha.setIncra(item.getIncra());
					if (item.getBemImovel().getClassificacaoBemImovel()!= null){
						linha.setClassificacaoBemImovel(item.getBemImovel().getClassificacaoBemImovel().getCodClassificacaoBemImovel());
					}
					listaFinal.add(linha);
				}

			}
			pag.setRegistros(listaFinal);

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Documentação sem Ocorrência" }, e);
		}

		return pag;
	}

	/**
	 * Busca um objeto Orgao através de seu código.
	 * 
	 * @param codOrgao
	 *            código do órgão a ser localizado
	 * @return Orgao
	 * @throws ApplicationException
	 */
	public static Orgao obterOrgao(Integer codOrgao)
			throws ApplicationException {

		return hibernateFactory.getOrgaoDAO().obter(codOrgao);

	}
	
	/**
	 * Busca um objeto Orgao através de seu descricao.
	 * 
	 * @param descOrgao
	 *            descricao do órgão a ser localizado
	 * @return Orgao
	 * @throws ApplicationException
	 */
	public static Orgao obterOrgao(String descOrgao)
			throws ApplicationException {

		return hibernateFactory.getOrgaoDAO().obterOrgaoPorDescricao(descOrgao);

	}

	/**
	 * Busca um objeto Cartorio através de seu código.
	 * 
	 * @param codCartorio
	 *            código do cartorio a ser localizado
	 * @return Cartorio
	 * @throws ApplicationException
	 */
	public static Cartorio obterCartorio(Integer codCartorio)
			throws ApplicationException {

		return hibernateFactory.getCartorioDAO().obter(codCartorio);

	}
	
	/**
	 * Busca um objeto Cartorio através de sua descricao.
	 * 
	 * @param descCartorio
	 *            descricao do cartorio a ser localizado
	 * @return Cartorio
	 * @throws ApplicationException
	 */
	public static Cartorio obterCartorio(String descCartorio)
			throws ApplicationException {

		return hibernateFactory.getCartorioDAO().obterCartorioPorDescricao(descCartorio);

	}
	
	/**
	 * Busca um objeto Tabelionato através de sua descricao.
	 * 
	 * @param descTabelionato
	 *            descricao do Tabelionato a ser localizado
	 * @return Tabelionato
	 * @throws ApplicationException
	 */
	public static Tabelionato obterTabelionato(String descTabelionato)
			throws ApplicationException {

		return hibernateFactory.getTabelionatoDAO().obterTabelionatoPorDescricao(descTabelionato);

	}

	/**
	 * Busca um objeto Situacao Legal Cartorial através de seu código.
	 * 
	 * @param codSituacao
	 *            código da Situacao a ser localizada
	 * @return SituacaoLegalCartorial
	 * @throws ApplicationException
	 */
	public static SituacaoLegalCartorial obterSituacaoLegalCartorial(
			Integer codSituacao) throws ApplicationException {

		return hibernateFactory.getSituacaoLegalCartorialDAO().obter(
				codSituacao);
	}

	/**
	 * Verifica se ja existe o orgão com sigla e/ou descricao cadastrado
	 * 
	 * @param descricaoNovoOrgao
	 *            descricao do novo orgão , siglaNovoOrgao sigla do novo orgao
	 * @return true se ja existir orgao com essa descriçao/sigla
	 * @throws ApplicationException
	 */
	public static Boolean verificaOrgaoDuplicado(String descricaoNovoOrgao, String siglaNovoOrgao, Integer codOrgao, Integer codInstituicao) throws ApplicationException {
		try {
			Boolean existe = false;
			if (descricaoNovoOrgao != null && !descricaoNovoOrgao.isEmpty()){
				existe = hibernateFactory.getOrgaoDAO().existeSiglaDescricao(null, descricaoNovoOrgao, codOrgao, codInstituicao);
			}
			if (siglaNovoOrgao != null && !siglaNovoOrgao.isEmpty()){
				existe = hibernateFactory.getOrgaoDAO().existeSiglaDescricao(siglaNovoOrgao, null, codOrgao, codInstituicao);
			}
			return existe;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "órgão" }, e);
		}

	}

	/**
	 * Salva objeto Orgao.
	 * 
	 * @param orgao
	 *            a ser salvo.
	 * @throws ApplicationException
	 */
	public static void salvarOrgao(Orgao orgao) throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getOrgaoDAO().salvar(orgao);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarOrgao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarOrgao()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Órgão" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Salva objeto Cartorio.
	 * 
	 * @param orgao
	 *            a ser salvo.
	 * @throws ApplicationException
	 */
	public static void salvarCartorio(Cartorio cartorio)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getCartorioDAO().salvar(cartorio);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarCartorio()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarCartorio()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Cartório" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Atualiza objeto Cartorio.
	 * 
	 * @param cartorio
	 *            a ser atualizado.
	 * @throws ApplicationException
	 */
	public static void alterarCartorio(Cartorio cartorio)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getCartorioDAO().alterar(cartorio);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarCartorio()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;

		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarCartorio()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Cartório" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Atualiza objeto Orgao.
	 * 
	 * @param orgao
	 *            a ser atualizado.
	 * @throws ApplicationException
	 */
	public static void alterarOrgao(Orgao orgao) throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getOrgaoDAO().alterar(orgao);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarOrgao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;

		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarOrgao()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Órgão" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Remove objeto Orgao.
	 * 
	 * @param orgao
	 *            a ser removido.
	 * @throws ApplicationException
	 */
	public static void excluirOrgao(int codOrgao) throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  

			// verifica se existe integridade referencial
			Orgao orgao = obterOrgao(codOrgao);
			if (orgao.getBemImovels() != null
					&& orgao.getBemImovels().size() > 0) {
				throw new ApplicationException("AVISO.19",
						ApplicationException.ICON_AVISO);
			}
			if (orgao.getOcupacaos() != null && orgao.getOcupacaos().size() > 0) {
				throw new ApplicationException("AVISO.19",
						ApplicationException.ICON_AVISO);
			}

			hibernateFactory.getOrgaoDAO().excluir(orgao);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirOrgao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirOrgao()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Órgão" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Remove objeto Cartorio.
	 * 
	 * @param codigo
	 *            do Cartorio a ser removido.
	 * @throws ApplicationException
	 */
	public static void excluirCartorio(Integer codCartorio)
			throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  

			// verifica se existe integridade referencial
			Cartorio cartorio = obterCartorio(codCartorio);
			if (cartorio.getBemImovels() != null
					&& cartorio.getBemImovels().size() > 0) {
				throw new ApplicationException("AVISO.28",
						ApplicationException.ICON_AVISO);
			}
			hibernateFactory.getCartorioDAO().excluir(cartorio);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirCartorio()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirCartorio()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Cartório" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Lista de Bens Imoveis para relatorio de Bens Imoveis.
	 * 
	 * @param rbiDTO
	 *            DTO com os filtros escolhidos na tela de opcoes de pesquisa
	 * @return Collection
	 * @throws Exception
	 */
	public static Collection<BemImovel> listarRelatorioBensImoveis(
			FiltroRelatorioBemImovelDTO rbiDTO) throws ApplicationException {

		return hibernateFactory.getBemImovelDAO().listarRelatorioBensImoveis(
				rbiDTO);

	}
	
	/**
	 * Lista de Bens Imoveis para relatorio de Bens Imoveis.
	 * 
	 * @param rbiDTO
	 *            DTO com os filtros escolhidos na tela de opcoes de pesquisa
	 * @return Collection
	 * @throws Exception
	 */
	public static Collection<BemImovel> listarRelatorioGerencialBensImoveis(
			FiltroRelatorioBemImovelDTO rbiDTO) throws ApplicationException {

		return hibernateFactory.getBemImovelDAO().listarRelatorioGerencialBensImoveis(rbiDTO);

	}

	/**
	 * Lista de Bens Imoveis para relatorio de Edificacao Bens Imoveis.
	 * 
	 * @param freDTO
	 *            DTO com os filtros escolhidos na tela de opcoes de pesquisa
	 * @return Collection
	 * @throws Exception
	 */
	public static Collection<RelatorioEdificacaoOcupacaoDTO> listarRelatorioEdificacaoOcupacao(FiltroRelatorioEdificacaoOcupacaoDTO freDTO) throws ApplicationException {
		List<Integer> listaCodOrgao = new ArrayList<Integer>();
		if (freDTO.getUsuarioS() != null){
			for (UsuarioOrgao o : freDTO.getUsuarioS().getListaUsuarioOrgao()){
				listaCodOrgao.add(o.getOrgao().getCodOrgao());
			}	
			freDTO.setListaCodOrgao(listaCodOrgao);
		}else{
			freDTO.setListaCodOrgao(null);
		}
		
		return hibernateFactory.getOcupacaoDAO().listarRelatorioEdificacaoOcupacao(freDTO);

	}

	/**
	 * Lista de Bens Imoveis para relatorio de Edificacao com Ocupacao de Bens
	 * Imoveis.
	 * 
	 * @param reDTO
	 *            DTO com os filtros escolhidos na tela de opcoes de pesquisa
	 * @return Collection
	 * @throws Exception
	 */
	public static Collection<RelatorioEdificacaoDTO> listarRelatorioEdificacaoBensImoveis(FiltroRelatorioEdificacaoDTO reDTO) throws ApplicationException {
		List<Integer> listaCodOrgao = new ArrayList<Integer>();
		if (reDTO.getUsuarioS() != null){
			for (UsuarioOrgao o : reDTO.getUsuarioS().getListaUsuarioOrgao()){
				listaCodOrgao.add(o.getOrgao().getCodOrgao());
			}	
			reDTO.setListaCodOrgao(listaCodOrgao);
		}else{
			reDTO.setListaCodOrgao(null);
		}
		return hibernateFactory.getEdificacaoDAO().listarRelatorioEdificacaoBensImoveis(reDTO);

	}

	/**
	 * Lista de Bens Imoveis para relatorio de Bens Imoveis por Area.
	 * 
	 * @param rbiDTO
	 *            DTO com os filtros escolhidos na tela de opcoes de pesquisa
	 * @return Collection
	 * @throws Exception
	 */
	public static Collection<RelatorioBemImovelDTO> listarRelatorioAreaBensImoveis(FiltroRelatorioAreaBemImovelDTO rbiDTO) throws ApplicationException {

		List<Integer> listaCodOrgao = new ArrayList<Integer>();
		if (rbiDTO.getUsuarioS() != null){
			for (UsuarioOrgao o : rbiDTO.getUsuarioS().getListaUsuarioOrgao()){
				listaCodOrgao.add(o.getOrgao().getCodOrgao());
			}
			rbiDTO.setListaCodOrgao(listaCodOrgao);
		}else{
			rbiDTO.setListaCodOrgao(null);
		}
		
		return hibernateFactory.getBemImovelDAO().listarRelatorioAreaBensImoveis(rbiDTO);

	}

	/**
	 * Lista paginada de formas de incorporacao de imoveis.
	 * 
	 * @param pag
	 *            - objeto de paginacao contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginacao.
	 * @throws ApplicationException
	 */
	public static Pagina listarFormaIncorporacao(Pagina pag, String descricao)
			throws ApplicationException {

		FormaIncorporacao formaIncorporacao = new FormaIncorporacao();
		formaIncorporacao.setDescricao(descricao);

		try {

			if (pag.getTotalRegistros() == 0) {

				pag.setTotalRegistros(hibernateFactory
						.getFormaIncorporacaoDAO().buscarQtdLista(
								formaIncorporacao).intValue());
			}
			pag.setRegistros(hibernateFactory.getFormaIncorporacaoDAO().listar(
					formaIncorporacao, new String[] { "descricao", "asc" },
					pag.getQuantidade(), pag.getPaginaAtual()));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Forma de Incorporação" }, e);
		}

		return pag;
	}

	/**
	 * Busca um objeto Forma de Incorporacao atraves de seu codigo.
	 * 
	 * @param codFormaIncorporacao
	 *            código da Forma de Incorporação a ser localizada
	 * @return FormaIncorporacao
	 * @throws ApplicationException
	 */
	public static FormaIncorporacao obterFormaIncorporacao(
			Integer codFormaIncorporacao) throws ApplicationException {

		return hibernateFactory.getFormaIncorporacaoDAO().obter(
				codFormaIncorporacao);

	}

	/**
	 * Busca um objeto SituacaoOcupacao atraves de seu codigo.
	 * 
	 * @param codSituacaoOcupacao
	 *            código da Situacao de Ocupacao a ser localizada
	 * @return SituacaoOcupacao
	 * @throws ApplicationException
	 */

	public static SituacaoOcupacao obterSituacaoOcupacao(
			Integer codSituacaoOcupacao) throws ApplicationException {

		return hibernateFactory.getSituacaoOcupacaoDAO().obter(
				codSituacaoOcupacao);

	}

	/**
	 * Busca um objeto Lote atraves de seu codigo.
	 * 
	 * @param codLote
	 *            codigo do Lote ser localizado
	 * @return Lote
	 * @throws ApplicationException
	 */
	public static Lote obterLote(Integer codLote) throws ApplicationException {

		Lote lote = hibernateFactory.getLoteDAO().obter(codLote);

		return lote;

	}

	/**
	 * Busca um objeto Lote com relacionamentos atraves de seu codigo.
	 * 
	 * @param codLote
	 *            codigo do Lote ser localizado
	 * @return Lote
	 * @throws ApplicationException
	 */
	public static Lote obterLoteComRelacionamentos(Integer codLote)
			throws ApplicationException, Exception {

		Lote lote = hibernateFactory.getLoteDAO().obterComRelacionamento(
				codLote);

		return lote;

	}

	/**
	 * Busca um objeto TipoConstrucao atraves de seu codigo.
	 * 
	 * @param codTipoConstrucao
	 *            codigo do TipoConstrucao ser localizado
	 * @return TipoConstrucao
	 * @throws ApplicationException
	 */
	public static TipoConstrucao obterTipoConstrucao(Integer codTipoConstrucao)
			throws ApplicationException {

		return hibernateFactory.getTipoConstrucaoDAO().obter(codTipoConstrucao);

	}

	/**
	 * Verifica se já existe Forma de Incorporação com mesmo nome.
	 * 
	 * @param String
	 *            descricaoNovaFormaIncorporacao
	 * @param Integer
	 *            codigo - codigo do objeto que esta sendo incluido(passar zero
	 *            se estiver sendo inlcuido)/alterado
	 * @return Boolean - true se existe FormaIncorporacao, false se
	 *         FormaIncorporacao não existe.
	 * @throws ApplicationException
	 */
	public static Boolean verificaFormaIncorporacaoDuplicado(
			String descricaoNovaFormaIncorporacao, Integer codigo)
			throws ApplicationException {

		String descricaoBanco;
		String descricaoNovaFormaIncorporacaoTratada = descricaoNovaFormaIncorporacao
				.trim().toUpperCase();

		try {
			// verifica se não existe forma incorporacao com o mesma descrição
			Collection<FormaIncorporacao> listaFormaIncorporacao = hibernateFactory
					.getFormaIncorporacaoDAO().listar();
			for (FormaIncorporacao item : listaFormaIncorporacao) {
				descricaoBanco = item.getDescricao().trim().toUpperCase();
				if (descricaoBanco
						.equals(descricaoNovaFormaIncorporacaoTratada)) {
					if (item.getCodFormaIncorporacao().compareTo(codigo) != 0) {
						return true;
					}
				}
			}
			return false;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "forma de incorporação" }, e);
		}

	}

	/**
	 * Salva objeto FormaIncorporacao.
	 * 
	 * @param objeto
	 *            a ser salvo.
	 * @throws ApplicationException
	 */
	public static void salvarFormaIncorporacao(
			FormaIncorporacao formaIncorporacao) throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getFormaIncorporacaoDAO().salvar(formaIncorporacao);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarFormaIncorporacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarFormaIncorporacao()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Forma de Incorporação" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Atualiza objeto FormaIncorporacao.
	 * 
	 * @param formaIncorporacao
	 *            a ser atualizada.
	 * @throws ApplicationException
	 */
	public static void alterarFormaIncorporacao(
			FormaIncorporacao formaIncorporacao) throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getFormaIncorporacaoDAO().alterar(formaIncorporacao);
			HibernateUtil.commitTransaction(); 
		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarFormaIncorporacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;

		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarFormaIncorporacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Forma de Incorporação" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Atualiza objeto Edificacao.
	 * 
	 * @param edificacao
	 *            a ser atualizada.
	 * @throws ApplicationException
	 */
	public static void alterarEdificacao(Edificacao edificacao)
			throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  
			hibernateFactory.getEdificacaoDAO().alterar(edificacao);
			atualizarPercentualOcupacao(edificacao);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarEdificacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;

		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarEdificacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Edificação" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Verifica se ocupacao percentual total da edificacao nao ultrapassara
	 * 100%.
	 * 
	 * @param Integer
	 *            codEdificacao - codigo da Edificacao.
	 * @param double ocupacaoPercentual - ocupacao percentual a ser salva.
	 * @param Integer
	 *            codOcupacao - codigo da Ocupacao que esta sendo alterada.
	 * @return Boolean - true se existe classificação, false se classificação
	 *         não existe.
	 * @throws ApplicationException
	 */

	public static boolean verificarPercentualOcupacaoEdificacao(Integer codEdificacao,
			double ocupacaoPercentual, Integer codOcupacao)
			throws ApplicationException {

		double ocupacaoTotal = 0;

		try {
			Edificacao edificacao = obterEdificacao(codEdificacao);
			Ocupacao ocupacao = new Ocupacao();
			ocupacao.setEdificacao(edificacao);

			Collection<Ocupacao> listaOcupacao = hibernateFactory
					.getOcupacaoDAO().listar(ocupacao);
			for (Ocupacao item : listaOcupacao) {
				if (item.getCodOcupacao().intValue() == codOcupacao.intValue()) {
					continue;
				}
				if (item.getOcupacaoPercentual() != null) {
					ocupacaoTotal += item.getOcupacaoPercentual().doubleValue();
				}
			}
			ocupacaoTotal += ocupacaoPercentual;
			if (ocupacaoTotal > 100) {
				return false;
			}
			return true;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "ocupação" }, e);
		}

	}

	/**
	 * Verifica se ocupacao por metro quadrado total da edificacao nao
	 * ultrapassara area total construida.
	 * 
	 * @param Integer
	 *            codEdificacao - codigo da Edificacao.
	 * @param double ocupacaoPercentual - ocupacao percentual a ser salva.
	 * @param Integer
	 *            codOcupacao - codigo da Ocupacao que esta sendo alterada.
	 * @return Boolean - true se existe classificação, false se classificação
	 *         não existe.
	 * @throws ApplicationException
	 */

	public static boolean verificarOcupacaoMetroQuadradoEdificacao(Integer codEdificacao,
			double ocupacaoMetroQuadrado, Integer codOcupacao)
			throws ApplicationException {

		double ocupacaoTotal = 0;

		try {
			Edificacao edificacao = obterEdificacao(codEdificacao);
			Ocupacao ocupacao = new Ocupacao();
			ocupacao.setEdificacao(edificacao);

			Collection<Ocupacao> listaOcupacao = hibernateFactory
					.getOcupacaoDAO().listar(ocupacao);
			for (Ocupacao item : listaOcupacao) {
				if (item.getCodOcupacao().intValue() == codOcupacao.intValue()) {
					continue;
				}
				if (item.getOcupacaoMetroQuadrado() != null) {
					ocupacaoTotal += item.getOcupacaoMetroQuadrado()
							.doubleValue();
				}
			}
			ocupacaoTotal += ocupacaoMetroQuadrado;
			if (ocupacaoTotal > edificacao.getAreaConstruida().doubleValue()) {
				return false;
			}
			return true;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "ocupação" }, e);
		}

	}

	/**
	 * Atualiza objeto Ocupacao.
	 * 
	 * @param ocupacao
	 *            a ser atualizada.
	 * @throws ApplicationException
	 */
	public static void alterarOcupacao(Ocupacao ocupacao)
			throws ApplicationException {

		double NovaOcupacaoPercentual = 0;
		double NovaOcupacaoMetroQuadrado = 0;
		if (ocupacao.getOcupacaoPercentual() != null) {
			NovaOcupacaoPercentual = ocupacao.getOcupacaoPercentual()
					.doubleValue();
		}
		if (ocupacao.getOcupacaoMetroQuadrado() != null) {
			NovaOcupacaoMetroQuadrado = ocupacao.getOcupacaoMetroQuadrado()
					.doubleValue();
		}

		if (!verificarPercentualOcupacaoEdificacao(ocupacao.getEdificacao()
				.getCodEdificacao(), NovaOcupacaoPercentual, ocupacao
				.getCodOcupacao())) {
			throw new ApplicationException("ERRO.ocupacao.ocupacaoInvalida",
					ApplicationException.ICON_ERRO);
		}
		if (!verificarOcupacaoMetroQuadradoEdificacao(ocupacao.getEdificacao()
				.getCodEdificacao(), NovaOcupacaoMetroQuadrado, ocupacao
				.getCodOcupacao())) {
			throw new ApplicationException(
					"ERRO.ocupacao.ocupacaoInvalidaMetro",
					ApplicationException.ICON_ERRO);
		}

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getOcupacaoDAO().alterar(ocupacao);
			HibernateUtil.commitTransaction(); 
		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarOcupacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;

		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarOcupacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Ocupação" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Remove objeto formaIncorporacao.
	 * 
	 * @param formaIncorporacao
	 *            a ser removida.
	 * @throws ApplicationException
	 */
	public static void excluirFormaIncorporacao(int codFormaIncorporacao)
			throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  

			// verifica se existe integridade referencial com tabelas -> tb_bem_imovel
			FormaIncorporacao formaIncorporacao = obterFormaIncorporacao(codFormaIncorporacao);
			if (formaIncorporacao.getBemImovels() != null
					&& formaIncorporacao.getBemImovels().size() > 0) {
				throw new ApplicationException("AVISO.17",
						ApplicationException.ICON_AVISO);
			}
			if (formaIncorporacao.getEdificacaos() != null
					&& formaIncorporacao.getEdificacaos().size() > 0) {
				throw new ApplicationException("AVISO.17",
						ApplicationException.ICON_AVISO);
			}

			hibernateFactory.getFormaIncorporacaoDAO().excluir(
					formaIncorporacao);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirFormaIncorporacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirFormaIncorporacao()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Forma de Incorporação" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Lista paginada de tipos de documentacao de imóveis.
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarTipoDocumentacao(Pagina pag, String descricao)
			throws ApplicationException {

		TipoDocumentacao tipoDocumentacao = new TipoDocumentacao();
		tipoDocumentacao.setDescricao(descricao);

		try {

			if (pag.getTotalRegistros() == 0) {

				pag.setTotalRegistros(hibernateFactory.getTipoDocumentacaoDAO()
						.buscarQtdLista(tipoDocumentacao).intValue());
			}
			pag.setRegistros(hibernateFactory.getTipoDocumentacaoDAO().listar(
					tipoDocumentacao, new String[] { "descricao", "asc" },
					pag.getQuantidade(), pag.getPaginaAtual()));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Tipo de Documentação" }, e);
		}

		return pag;
	}

	/**
	 * Busca um objeto TipoDocumentacao através de seu código.
	 * 
	 * @param codTipoDocumentacao
	 *            código do tipo de documentacao a ser localizado
	 * @return TipoDocumentacao
	 * @throws ApplicationException
	 */
	public static TipoDocumentacao obterTipoDocumentacao(
			Integer codTipoDocumentacao) throws ApplicationException {

		return hibernateFactory.getTipoDocumentacaoDAO().obter(
				codTipoDocumentacao);
	}

	/**
	 * Verifica se já existe Coordenada UTM.
	 * 
	 * @param CoordenadaUtm
	 *            coordenada
	 * @return Boolean - true se existe Coordenada X ou Coordenada Y, false se
	 *         classificação não existe.
	 * @throws ApplicationException
	 */

	public static boolean verificaCoordenadaUTMDuplicado(
			CoordenadaUtm coordenada) throws ApplicationException {

		CoordenadaUtm aux = new CoordenadaUtm();
		aux.setBemImovel(coordenada.getBemImovel());

		try {

			Collection<CoordenadaUtm> listaCoordenada = hibernateFactory
					.getCoordenadaUtmDAO().listar(aux);
			for (CoordenadaUtm item : listaCoordenada) {
				if (item.getCoordenadaX().equals(coordenada.getCoordenadaX())) {
					if (item.getCoordenadaY().equals(
							coordenada.getCoordenadaY())) {
						if (!item.getCodCoordenadaUtm().equals(
								coordenada.getCodCoordenadaUtm())) {
							return true;
						}
					}
				}
			}
			return false;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "coordenada UTM" }, e);
		}

	}

	/**
	 * Verifica se já existe Tipo de documentação com mesmo nome.
	 * 
	 * @param String
	 *            descricaoNovoTipoDocumento
	 * @param Integer
	 *            codigo - codigo do objeto que esta sendo incluido(passar zero
	 *            se estiver sendo inlcuido)/alterado
	 * @return Boolean - true se existe classificação, false se classificação
	 *         não existe.
	 * @throws ApplicationException
	 */

	public static Boolean verificaTipoDocumentacaoDuplicado(
			String descricaoNovoTipoDocumento, Integer codigo)
			throws ApplicationException {

		String descricaoBanco;
		String descricaoNovoTipoDocumentoTratada = descricaoNovoTipoDocumento
				.trim().toUpperCase();

		try {
			// verifica se não existe tipo de documentacao com o mesma descrição
			Collection<TipoDocumentacao> listaTipoDocumentacao = hibernateFactory
					.getTipoDocumentacaoDAO().listar();
			for (TipoDocumentacao item : listaTipoDocumentacao) {
				descricaoBanco = item.getDescricao().trim().toUpperCase();
				if (descricaoBanco.equals(descricaoNovoTipoDocumentoTratada)) {
					if (item.getCodTipoDocumentacao().compareTo(codigo) != 0) {
						return true;
					}
				}
			}
			return false;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "tipo de documentação" }, e);
		}

	}

	/**
	 * Salva objeto TipoDocumentacao.
	 * 
	 * @param objeto
	 *            a ser salvo.
	 * @throws ApplicationException
	 */
	public static void salvarTipoDocumentacao(TipoDocumentacao tipoDocumentacao)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getTipoDocumentacaoDAO().salvar(tipoDocumentacao);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarTipoDocumentacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarTipoDocumentacao()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Tipo de Documentação" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Salva objeto Ocupacao.
	 * 
	 * @param objeto
	 *            a ser salvo.
	 * @throws ApplicationException
	 */
	public static void salvarOcupacao(Ocupacao ocupacao) throws ApplicationException {

		double NovaOcupacaoPercentual = 0;
		double NovaOcupacaoMetroQuadrado = 0;
		if (ocupacao.getOcupacaoPercentual() != null) {
			NovaOcupacaoPercentual = ocupacao.getOcupacaoPercentual().doubleValue();
		}
		if (ocupacao.getOcupacaoMetroQuadrado() != null) {
			NovaOcupacaoMetroQuadrado = ocupacao.getOcupacaoMetroQuadrado().doubleValue();
		}

		if (ocupacao.getEdificacao() != null && !verificarPercentualOcupacaoEdificacao(ocupacao.getEdificacao()
				.getCodEdificacao(), NovaOcupacaoPercentual, Integer.parseInt("0"))) {
			throw new ApplicationException("ERRO.ocupacao.ocupacaoInvalida", ApplicationException.ICON_ERRO);
		}
		if (ocupacao.getEdificacao() != null && !verificarOcupacaoMetroQuadradoEdificacao(ocupacao.getEdificacao()
				.getCodEdificacao(), NovaOcupacaoMetroQuadrado, Integer.parseInt("0"))) {
			throw new ApplicationException("ERRO.ocupacao.ocupacaoInvalidaMetro", ApplicationException.ICON_ERRO);
		}

		
		if (ocupacao.getBemImovel() != null && !verificarPercentualOcupacaoBemImovel(ocupacao.getBemImovel()
				.getCodBemImovel(), NovaOcupacaoPercentual, Integer.parseInt("0"))) {
			throw new ApplicationException("ERRO.ocupacao.ocupacaoInvalida", ApplicationException.ICON_ERRO);
		}
		if (ocupacao.getBemImovel() != null && !verificarOcupacaoMetroQuadradoBemImovel(ocupacao.getBemImovel()
				.getCodBemImovel(), NovaOcupacaoMetroQuadrado, Integer.parseInt("0"))) {
			throw new ApplicationException("ERRO.ocupacao.ocupacaoInvalidaMetroResponsavel", ApplicationException.ICON_ERRO);
		}

		try {
			HibernateUtil.currentTransaction();  
			ocupacao.setAtivo(Boolean.TRUE);
			hibernateFactory.getOcupacaoDAO().salvar(ocupacao);
			HibernateUtil.commitTransaction(); 
		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarOcupacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarOcupacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Ocupação" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Atualiza objeto TipoDocumentacao.
	 * 
	 * @param TipoDocumentacao
	 *            a ser atualizado.
	 * @throws ApplicationException
	 */
	public static void alterarTipoDocumentacao(TipoDocumentacao tipoDocumentacao)
			throws ApplicationException {

		// verifica se não é notificação, esse tipo de documentação não pode ser
		// alterado nem excluído
		if (tipoDocumentacao.getCodTipoDocumentacao().intValue() == Dominios.TIPO_DOCUMENTACAO_NOTIFICACAO) {
			throw new ApplicationException("ERRO.tipoDocumentacao.notificacao",
					ApplicationException.ICON_AVISO);
		}
		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getTipoDocumentacaoDAO().alterar(tipoDocumentacao);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarTipoDocumentacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;

		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarTipoDocumentacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Tipo de Documentação" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Remove objeto Notificacao.
	 * 
	 * @param Integer
	 *            codNotificacao - codigo da notificacao a ser excluida
	 * @throws ApplicationException
	 */
	public static void excluirNotificacao(Notificacao notificacao)
			throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  
			hibernateFactory.getNotificacaoDAO().excluir(notificacao);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirNotificacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirNotificacao()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Notificação" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Remove objeto TipoDocumentacao.
	 * 
	 * @param TipoDocumentacao
	 *            a ser removido.
	 * @throws ApplicationException
	 */
	public static void excluirTipoDocumentacao(int codTipoDocumentacao)
			throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  
			// verifica se não é notificação, esse tipo de documentação não pode
			// ser alterado nem excluído
			if (codTipoDocumentacao == Dominios.TIPO_DOCUMENTACAO_NOTIFICACAO) {
				throw new ApplicationException("AVISO.32",
						ApplicationException.ICON_AVISO);
			}

			// verifica se existe integridade referencial com tabelas -> tb_bem_imovel
			TipoDocumentacao tipoDocumentacao = obterTipoDocumentacao(codTipoDocumentacao);
			if (tipoDocumentacao.getDocumentacaos() != null
					&& tipoDocumentacao.getDocumentacaos().size() > 0) {
				throw new ApplicationException("AVISO.32",
						ApplicationException.ICON_AVISO);
			}

			hibernateFactory.getTipoDocumentacaoDAO().excluir(tipoDocumentacao);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirTipoDocumentacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirTipoDocumentacao()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Tipo de Documentação" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Salva objeto OcorrenciaDocumentacao e objetos Documentacao, Notificacao e
	 * Arquivo Anexo relacionados.
	 * 
	 * @param documentacao
	 *            Documentacao relacionada com objeto OcorrenciaDocumentacao a
	 *            ser salvo.
	 * @param ocorrenciaDocumentacao
	 *            OcorrenciaDocumentacao a ser salvo.
	 * @param notificacao
	 *            Notificacao relacionada com objeto OcorrenciaDocumentacao a
	 *            ser salvo.
	 * @param anexo
	 *            FormFile relacionada com objeto OcorrenciaDocumentacao a ser
	 *            salvo.
	 * @throws ApplicationException
	 */
	public static void salvarOcorrenciaDocumentacao(Documentacao documentacao,
			OcorrenciaDocumentacao ocorrenciaDocumentacao,
			Notificacao notificacao, FormFile anexo)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			if (documentacao != null) {
				salvarDocumentacao(documentacao, notificacao, anexo);
			}

			hibernateFactory.getOcorrenciaDocumentacaoDAO().salvar(ocorrenciaDocumentacao);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarOcorrenciaDocumentacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarOcorrenciaDocumentacao()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Ocorrência da Documentação" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Busca um objeto Notificacao através de seu código.
	 * 
	 * @param codNotificacao
	 *            código da Notificacao a ser localizada
	 * @return Notificacao
	 * @throws ApplicationException
	 */
	public static Notificacao obterNotificacao(Integer codNotificacao)
			throws ApplicationException {
		return hibernateFactory.getNotificacaoDAO().obter(codNotificacao);
	}

	/**
	 * Busca um objeto Documentacao através de seu código.
	 * 
	 * @param codDocumentacao
	 *            código da Documentacao a ser localizada
	 * @return Documentacao
	 * @throws ApplicationException
	 */
	public static Documentacao obterDocumentacao(Integer codDocumentacao)
			throws ApplicationException {
		return hibernateFactory.getDocumentacaoDAO().obter(codDocumentacao);
	}

	/**
	 * Busca um objeto Documentacao com Relacionamentos através de seu código.
	 * 
	 * @param codDocumentacao
	 *            código da Documentacao a ser localizada
	 * @return Documentacao
	 * @throws ApplicationException
	 *             , Exception
	 */
	public static Documentacao obterDocumentacaoComRelacionamentos(
			Integer codDocumentacao) throws ApplicationException, Exception {
		return hibernateFactory.getDocumentacaoDAO().obterComRelacionamentos(
				codDocumentacao);
	}

	/**
	 * Busca um objeto Edificacao com o atributo lotes inicializado através de
	 * seu código.
	 * 
	 * @param codEdificacao
	 *            código da Edificacao a ser localizada
	 * @return Edificacao
	 * @throws ApplicationException
	 */
	public static Edificacao obterEdificacao(Integer codEdificacao)
			throws ApplicationException {

		Edificacao edificacao = hibernateFactory.getEdificacaoDAO().obter(
				codEdificacao);

		return edificacao;
	}

	/**
	 * Busca um objeto Edificacao com relacionamentosatravés de seu código.
	 * 
	 * @param codEdificacao
	 *            código da Edificacao a ser localizada
	 * @return Edificacao
	 * @throws ApplicationException
	 */
	public static Edificacao obterEdificacaoExibir(Integer codEdificacao)
			throws ApplicationException {

		return hibernateFactory.getEdificacaoDAO().obterExibir(codEdificacao);

	}

	/**
	 * Salva objeto Avaliacao
	 * 
	 * @param objeto
	 *            a ser salvo
	 * @throws ApplicationException
	 */
	public static void salvarAvaliacao(Avaliacao avaliacao)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getAvaliacaoDAO().salvar(avaliacao);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarAvalicao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarAvalicao()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Avaliação" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Salva objeto Edificacao
	 * 
	 * @param objeto
	 *            a ser salvo
	 * @throws ApplicationException
	 */
	public static void salvarEdificacao(Edificacao edificacao)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getEdificacaoDAO().salvar(edificacao);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarEdificacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarEdificacao()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Edificação" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Atualiza area construida e utilizada do Objeto BemImovel de acordo com a
	 * area de suas edificacoes
	 * 
	 * @param codigo
	 *            do bem imovel a ser atualizado.
	 * @throws ApplicationException
	 */
	public static void atualizarAreaBemImovel(BemImovel bemImovel) throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			Edificacao edificacao = new Edificacao();
			edificacao.setBemImovel(bemImovel);
			Collection<Edificacao> edificacoes = listarEdificacoes(edificacao);
			BigDecimal areaUtilizada = new BigDecimal(0);
			BigDecimal areaConstruida = new BigDecimal(0);
			for (Edificacao item : edificacoes) {
				areaConstruida = areaConstruida
						.add(item.getAreaConstruida() != null ? item
								.getAreaConstruida() : BigDecimal.ZERO);
				areaUtilizada = areaUtilizada
						.add(item.getAreaUtilizada() != null ? item
								.getAreaUtilizada() : BigDecimal.ZERO);
			}
			BigDecimal zero = new BigDecimal(0);
			bemImovel.setAreaConstruida(areaConstruida != null ? areaConstruida
					: zero);
			if (bemImovel.getAreaTerreno() != null) {
				bemImovel.setAreaDispoNivel(bemImovel.getAreaTerreno()
						.subtract(areaUtilizada));
			} else {
				bemImovel.setAreaDispoNivel(null);
			}
			hibernateFactory.getBemImovelDAO().alterar(bemImovel);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			log4j.error("ERRO", appEx.getCausaRaiz());
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em atualizarAreaBemImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			log4j.error("ERRO", ex);
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em atualizarAreaBemImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao incluir/alterar Área do Bem Imóvel" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Atualiza percentual de ocupacao da edificacao quando a area da mesma for
	 * alterada
	 * 
	 * @param codigo
	 *            do bem imovel a ser atualizado.
	 * @throws ApplicationException
	 */
	public static void atualizarPercentualOcupacao(Edificacao edificacao) throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			Ocupacao o = new Ocupacao();
			o.setEdificacao(edificacao);
			Collection<Ocupacao> ocupacoes = hibernateFactory.getOcupacaoDAO()
					.listar(o);

			Double areaConstruida = new Double(0);
			if (edificacao.getAreaConstruida() != null) {
				areaConstruida = edificacao.getAreaConstruida().doubleValue();
			}

			Double ocupacaoTotal = new Double(0);
			Double zero = new Double(0);

			for (Ocupacao item : ocupacoes) {
				Double ocupacaoMetroQuadrado = new Double(0);
				if (item.getOcupacaoMetroQuadrado() != null) {
					ocupacaoMetroQuadrado = item.getOcupacaoMetroQuadrado()
							.doubleValue();
				}

				Double ocupacaoPercentual = new Double(0);
				if ((areaConstruida != null) && (!areaConstruida.equals(zero))) {
					ocupacaoPercentual = ocupacaoMetroQuadrado / areaConstruida;
				}
				ocupacaoPercentual = Valores.arredondar((ocupacaoPercentual * 100), 2);
				ocupacaoTotal += ocupacaoPercentual;
				if (ocupacaoTotal > 100) {
					throw new ApplicationException(
							"ERRO.ocupacao.ocupacaoInvalidaMetro");
				}
				item.setOcupacaoPercentual(BigDecimal
						.valueOf(ocupacaoPercentual));
				hibernateFactory.getOcupacaoDAO().alterar(item);
			}

			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			log4j.error("ERRO", appEx.getCausaRaiz());
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em atualizarPercentualOcupacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			log4j.error("ERRO", ex);
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em atualizarPercentualOcupacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao incluir/alterar Área do Bem Imóvel" }, ex, ApplicationException.ICON_ERRO);

		}

	}

	/**
	 * Atualiza area construida e utilizada do Objeto BemImovel de acordo com a
	 * area de suas edificacoes
	 * 
	 * @param codigo
	 *            do bem imovel a ser atualizado.
	 * @throws ApplicationException
	 */
	public static void atualizarAreaBemImovel(Integer codBemImovel) throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			BemImovel bemImovel = obterBemImovelComEdificacoes(codBemImovel);

			BigDecimal areaUtilizada = new BigDecimal(0);
			BigDecimal areaConstruida = new BigDecimal(0);
			for (Edificacao item : bemImovel.getEdificacaos()) {
				areaConstruida = areaConstruida
						.add(item.getAreaConstruida() != null ? item
								.getAreaConstruida() : BigDecimal.ZERO);
				areaUtilizada = areaUtilizada
						.add(item.getAreaUtilizada() != null ? item
								.getAreaUtilizada() : BigDecimal.ZERO);

			}
			bemImovel.setAreaConstruida(areaConstruida != null ? areaConstruida
					: BigDecimal.ZERO);
			if (bemImovel.getAreaTerreno() != null) {
				bemImovel.setAreaDispoNivel(bemImovel.getAreaTerreno()
						.subtract(areaUtilizada));
			} else {
				bemImovel.setAreaDispoNivel(null);
			}
			bemImovel.setAreaConstruida(areaConstruida != null ? areaConstruida
					: BigDecimal.ZERO);
			bemImovel.setAreaDispoNivel(bemImovel.getAreaTerreno().subtract(
					areaUtilizada != null ? areaUtilizada : BigDecimal.ZERO));
			hibernateFactory.getBemImovelDAO().alterar(bemImovel);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			log4j.error("ERRO", appEx.getCausaRaiz());
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em atualizarAreaBemImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			log4j.error("ERRO", ex);
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em atualizarAreaBemImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao incluir/alterar Área do Bem Imóvel" }, ex, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Lista paginada de avaliações de imóveis por bem imóvel
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarAvaliacao(Pagina pag, BemImovel bemImovel) throws ApplicationException {

		Avaliacao avaliacao = new Avaliacao();
		avaliacao.setBemImovel(bemImovel);

		try {

			if (pag.getTotalRegistros() == 0) {

				pag.setTotalRegistros(hibernateFactory.getAvaliacaoDAO()
						.buscarQtdLista(avaliacao).intValue());
			}
			pag.setRegistros(hibernateFactory.getAvaliacaoDAO()
					.listarComRelacionamentos(bemImovel.getCodBemImovel(),
							pag.getQuantidade(), pag.getPaginaAtual()));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Avaliação" }, e);
		}

		return pag;
	}

	/**
	 * Lista paginada de edificações por bem imóvel
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarEdificacao(Pagina pag, Integer codBemImovel)
			throws ApplicationException {

		try {
			Edificacao edificacao = new Edificacao();
			edificacao.setBemImovel(obterBemImovel(codBemImovel));
			if (pag.getTotalRegistros() == 0) {

				pag.setTotalRegistros(hibernateFactory.getEdificacaoDAO()
						.buscarQtdLista(edificacao).intValue());
			}
			pag.setRegistros(hibernateFactory.getEdificacaoDAO()
					.listarComRelacionamentos(codBemImovel,
							pag.getQuantidade(), pag.getPaginaAtual()));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Edificação" }, e);
		}

		return pag;
	}

	/**
	 * Lista paginada de ocupações por edificação
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 *             , Exception
	 */
	public static Pagina listarOcupacao(Pagina pag, Integer codEdificacao)
			throws ApplicationException {

		Ocupacao ocupacao = new Ocupacao();
		ocupacao.setEdificacao(obterEdificacao(codEdificacao));

		try {
			
			if (pag.getTotalRegistros() == 0) {
				pag.setTotalRegistros(hibernateFactory.getOcupacaoDAO()
						.buscarQtdLista(ocupacao).intValue());
			}
		
			Collection<OcupacaoListaDTO> itensLista = hibernateFactory.getOcupacaoDAO().listarporEdificacao(codEdificacao, pag.getQuantidade(), pag.getPaginaAtual());

			pag.setRegistros(itensLista);
		
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Ocupação" }, e);
		}

		return pag;
	}

	/**
	 * Busca um objeto Avalicação através de seu código.
	 * 
	 * @param codAvaliacao
	 *            código do Avalicação a ser localizado
	 * @return BemImovel
	 * @throws ApplicationException
	 */
	public static Avaliacao obterAvaliacao(Integer codAvaliacao)
			throws ApplicationException {

		return hibernateFactory.getAvaliacaoDAO().obter(codAvaliacao);
	}

	/**
	 * Busca um objeto Ocupacao através de seu código.
	 * 
	 * @param codOcupacao
	 *            código da Ocupação a ser localizada
	 * @return Ocupacao
	 * @throws ApplicationException
	 *             , Exception
	 */
	public static Ocupacao obterOcupacao(Integer codOcupacao)
			throws ApplicationException, Exception {
		return hibernateFactory.getOcupacaoDAO().obterComEdificacao(codOcupacao);
	}

	/**
	 * Atualiza objeto Avaliacao.
	 * 
	 * @param Avaliacao
	 *            a ser atualizado.
	 * @throws ApplicationException
	 */
	public static void alterarAvaliacao(Avaliacao avaliacao)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getAvaliacaoDAO().alterar(avaliacao);
			HibernateUtil.commitTransaction(); 
		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarAvaliacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;

		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarAvaliacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Avaliação" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Remove objeto Documentacao e todos os seus relacionamentos
	 * (notificacao/ocorrencia documetnacao/documentacaos)
	 * 
	 * @param Integer
	 *            codDocumentacao - codigo da Documentacao a ser excluida
	 * @throws ApplicationException
	 */
	public static void excluirDocumentacao(Integer codDocumentacao)
			throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  
			Documentacao documentacao = obterDocumentacaoComRelacionamentos(codDocumentacao);
			if (!documentacao.getDocumentacaos().isEmpty()) {
				throw new ApplicationException("AVISO.38",
						ApplicationException.ICON_AVISO);
			}
			if (!documentacao.getOcorrenciaDocumentacaos().isEmpty()) {
				throw new ApplicationException("AVISO.38",
						ApplicationException.ICON_AVISO);
			}

			hibernateFactory.getDocumentacaoDAO().excluir(documentacao);

			HibernateUtil.commitTransaction(); 
			excluirAnexoDocumentacao(codDocumentacao);

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirDocumentacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirDocumentacao()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Documentação" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Remove objeto Avaliacao.
	 * 
	 * @param avaliacao
	 *            a ser removida.
	 * @throws ApplicationException
	 */
	public static void excluirAvaliacao(int codAvaliacao)
			throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  
			Avaliacao avaliacao = obterAvaliacao(codAvaliacao);
			hibernateFactory.getAvaliacaoDAO().excluir(avaliacao);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirAvaliacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirAvaliacao()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Avaliação" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Remove objeto Edificação.
	 * 
	 * @param edificação
	 *            a ser removida.
	 * @throws ApplicationException
	 */
	public static void excluirEdificacao(int codEdificacao)
			throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  
			// verifica se existe integridade referencial
			Edificacao edificacao = obterEdificacao(codEdificacao);
			if (edificacao.getOcupacaos() != null) {
				for (Ocupacao item : edificacao.getOcupacaos()) {
					hibernateFactory.getOcupacaoDAO().excluir(item);
				}
			}
			if (!edificacao.getAvaliacaos().isEmpty()) {
				for (Avaliacao item : edificacao.getAvaliacaos()) {
					hibernateFactory.getAvaliacaoDAO().excluir(item);
				}
			}
			if (!edificacao.getDocumentacaos().isEmpty()) {
				for (Documentacao item : edificacao.getDocumentacaos()) {
					if (!item.getNotificacaos().isEmpty()) {
						for (Notificacao not : item.getNotificacaos()) {
							hibernateFactory.getNotificacaoDAO().excluir(not);
						}
					}
					if (!item.getOcorrenciaDocumentacaos().isEmpty()) {
						for (OcorrenciaDocumentacao ocor : item
								.getOcorrenciaDocumentacaos()) {
							hibernateFactory.getOcorrenciaDocumentacaoDAO()
									.excluir(ocor);
						}
					}
					hibernateFactory.getDocumentacaoDAO().excluir(item);
				}
			}

			hibernateFactory.getEdificacaoDAO().excluir(edificacao);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirEdificacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirEdificacao()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Edificação" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Remove objeto Ocupação.
	 * 
	 * @param codOcupacao
	 *            a ser removida.
	 * @throws ApplicationException
	 */
	public static void excluirOcupacao(int codOcupacao)
			throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  
			Ocupacao ocupacao = obterOcupacao(codOcupacao);
			hibernateFactory.getOcupacaoDAO().excluir(ocupacao);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirOcupacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirOcupacao()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Ocupação" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Lista paginada de coordenadas de imóveis por bem imóvel
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarCoordenadasUTM(Pagina pag, BemImovel bemImovel)
			throws ApplicationException {

		CoordenadaUtm coordenadaUtm = new CoordenadaUtm();
		coordenadaUtm.setBemImovel(bemImovel);

		try {

			if (pag.getTotalRegistros() == 0) {

				pag.setTotalRegistros(hibernateFactory.getCoordenadaUtmDAO()
						.buscarQtdLista(coordenadaUtm).intValue());
			}
			pag.setRegistros(hibernateFactory.getCoordenadaUtmDAO().listar(
					coordenadaUtm, new String[] { "coordenadaX", "asc" },
					pag.getQuantidade(), pag.getPaginaAtual()));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Coordenada UTM" }, e);
		}

		return pag;
	}

	/**
	 * Salva objeto CoordenadaUtm.
	 * 
	 * @param CoordenadaUtm
	 *            a ser salvo.
	 * @throws ApplicationException
	 */
	public static void salvarCoordenadasUTM(CoordenadaUtm coordenadaUtm)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			if (!verificaCoordenadaUTMDuplicado(coordenadaUtm)) {
				hibernateFactory.getCoordenadaUtmDAO().salvar(coordenadaUtm);
			} else {
				throw new ApplicationException("AVISO.40", new String[] {
						coordenadaUtm.getCoordenadaX().toString(),
						coordenadaUtm.getCoordenadaY().toString() },
						ApplicationException.ICON_AVISO);
			}
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarCoordenadasUTM()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarCoordenadasUTM()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Coordenada UTM" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Busca um objeto CoordenadaUtm através de seu código.
	 * 
	 * @param codCoordenada
	 *            código da CoordenadaUtm a ser localizada
	 * @return CoordenadaUtm
	 * @throws ApplicationException
	 *             , Exception
	 */
	public static CoordenadaUtm obterCoordenadasUTM(Integer codCoordenada)
			throws ApplicationException {

		return hibernateFactory.getCoordenadaUtmDAO().obter(codCoordenada);
	}

	/**
	 * Altera objeto CoordenadaUtm.
	 * 
	 * @param CoordenadaUtm
	 *            a ser alterado.
	 * @throws ApplicationException
	 */
	public static void alterarCoordenadasUTM(CoordenadaUtm coordenada)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getCoordenadaUtmDAO().alterar(coordenada);
			HibernateUtil.commitTransaction(); 
		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarCoordenadasUTM()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;

		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarCoordenadasUTM()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Coordenada UTM" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Exclui objeto CoordenadaUtm.
	 * 
	 * @param CoordenadaUtm
	 *            a ser excluido.
	 * @throws ApplicationException
	 */
	public static void excluirCoordenadasUTM(int codCoordenada)
			throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  
			CoordenadaUtm coordenadaUtm = obterCoordenadasUTM(codCoordenada);
			hibernateFactory.getCoordenadaUtmDAO().excluir(coordenadaUtm);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirCoordenadasUTM()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirCoordenadasUTM()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Coordenada UTM" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Lista paginada de ocorrencias de documentacao por bem imóvel
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarOcorrenciaDocumentacao(Pagina pag,
			Integer codBemImovel) throws ApplicationException, Exception {

		OcorrenciaDocumentacao ocorrenciaDocumentacao = new OcorrenciaDocumentacao();
		ocorrenciaDocumentacao.setBemImovel(obterBemImovel(codBemImovel));

		try {

			if (pag.getTotalRegistros() == 0) {

				pag.setTotalRegistros(hibernateFactory
						.getOcorrenciaDocumentacaoDAO().buscarQtdLista(
								ocorrenciaDocumentacao).intValue());
			}
			pag.setRegistros(hibernateFactory.getOcorrenciaDocumentacaoDAO()
					.listarComRelacionamentos(codBemImovel,
							pag.getQuantidade(), pag.getPaginaAtual()));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Ocorrência da Documentação" }, e);
		}

		return pag;
	}

	/**
	 * Faz upload do arquivo referente a documentação
	 * 
	 * @param anexo
	 *            - objeto contendo os dados do arquivo a ser salvo.
	 * @throws ApplicationException
	 */
	public static void salvarAnexoDocumentacao(FormFile anexo,
			Integer codDocumentacao) throws ApplicationException {
		ArquivoDAO arquivoDao = new ArquivoServidorDAO();

		try {
			arquivoDao.uploadArquivo(codDocumentacao.toString(), anexo
					.getFileData());
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao realizar o upload do arquivo da Documentação" }, e);
		}

	}

	/**
	 * Exclui o arquivo referente a documentação
	 * 
	 * @param anexo
	 *            - objeto contendo os dados do arquivo a ser salvo.
	 * @throws ApplicationException
	 */
	public static void excluirAnexoDocumentacao(Integer codDocumentacao)
			throws ApplicationException {
		ArquivoDAO arquivoDao = new ArquivoServidorDAO();

		try {
			arquivoDao.excluirAnexoDocumentacao(codDocumentacao.toString());
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao excluir arquivo da Documentação" }, e);
		}

	}

	/**
	 * Salva objeto Documentacao
	 * 
	 * @param documentacao
	 *            - objeto documentacao salvo.
	 * @throws ApplicationException
	 */
	public static void salvarDocumentacao(Documentacao documentacao,
			Notificacao notificacao, FormFile anexo)
			throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  
			hibernateFactory.getDocumentacaoDAO().salvar(documentacao);
			if (notificacao != null) {
				salvarNotificacao(notificacao);
			}
			if (anexo != null && anexo.getFileSize() != 0) {
				salvarAnexoDocumentacao(anexo, documentacao.getCodDocumentacao());
			}
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarDocumentacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarDocumentacao()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Documentação" }, ex,ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Altera objeto Documentacao
	 * 
	 * @param documentacao
	 *            - objeto documentacao alterado.
	 * @throws ApplicationException
	 */
	public static void alterarDocumentacao(Documentacao documentacao,
			Notificacao notificacao, String flagNotificacao, FormFile anexo)
			throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  
			hibernateFactory.getDocumentacaoDAO().alterar(documentacao);
			if (notificacao != null) {
				if ("inclusao".equals(flagNotificacao)) {
					salvarNotificacao(notificacao);
				} else if ("alteracao".equals(flagNotificacao)) {
					alterarNotificacao(notificacao);
				} else if ("exclusao".equals(flagNotificacao)) {
					excluirNotificacao(notificacao);
				}

			}
			if (anexo != null && anexo.getFileSize() != 0) {
				excluirAnexoDocumentacao(documentacao.getCodDocumentacao());
				salvarAnexoDocumentacao(anexo, documentacao
						.getCodDocumentacao());
			}
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarDocumentacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarDocumentacao()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Documentação" }, ex,ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Faz download do arquivo referente a documentação
	 * 
	 * @param anexo
	 *            - objeto contendo os dados do arquivo a ser salvo.
	 * @throws ApplicationException
	 */
	public static byte[] obterAnexoDocumentacao(Integer codDocumentacao)
			throws ApplicationException {
		ArquivoDAO arquivoDao = new ArquivoServidorDAO();

		try {
			return arquivoDao.downloadArquivo(codDocumentacao.toString());

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao obter o arquivo da Documentação" }, e);
		}

	}
	
	/**
	 * Faz download do arquivo referente a documentação
	 * 
	 * @param anexo
	 *            - objeto contendo os dados do arquivo a ser salvo.
	 * @throws ApplicationException
	 */
	public static byte[] obterLogoInstituicao(String nomeArquivo)
			throws ApplicationException {
		try {
			ArquivoDAO arquivoDao = new ArquivoServidorDAO();
			

			return arquivoDao.downloadLogotipo(nomeArquivo);

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao obter o arquivo da Logo da Instituição" }, e);
		}

	}

	/**
	 * Salva objeto Notificacao
	 * 
	 * @param notificacao
	 *            - objeto Notificacao a ser salvo.
	 * @throws ApplicationException
	 */
	public static void salvarNotificacao(Notificacao notificacao)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getNotificacaoDAO().salvar(notificacao);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarNotificacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarNotificacao()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Notificação" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Verifica se já existe cartorio com mesmo nome na mesma cidade.
	 * 
	 * @param String
	 *            descricao
	 * @param String
	 *            codMunicipio
	 * @param Integer
	 *            codCartorio ( zero se for inclusao)
	 * @return Boolean - true se existe cartorio, false se cartorio não existe.
	 * @throws ApplicationException
	 */
	public static Boolean verificarCartorioDuplicado(String descricao,
			String codMunicipio, Integer codCartorio)
			throws ApplicationException {

		String descricaoBanco;
		String descricaoNova = descricao.trim().toUpperCase();

		try {
			Cartorio cartorio = new Cartorio();
			cartorio.setCodMunicipio(Integer.parseInt(codMunicipio));
			// verifica se não existe tipo com o mesma descrição
			Collection<Cartorio> listaCartorio = hibernateFactory
					.getCartorioDAO().listar(cartorio);
			for (Cartorio item : listaCartorio) {
				descricaoBanco = item.getDescricao().trim().toUpperCase();
				if (descricaoBanco.equals(descricaoNova)) {
					if (item.getCodCartorio().compareTo(codCartorio) != 0) {
						return true;
					}
				}
			}
			return false;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "cartório" }, e);
		}

	}

	/**
	 * Alterar objeto Notificacao
	 * 
	 * @param notificacao
	 *            - objeto Notificacao a ser alterado.
	 * @throws ApplicationException
	 */
	public static void alterarNotificacao(Notificacao notificacao)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getNotificacaoDAO().alterar(notificacao);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarNotificacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarNotificacao()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Notificação" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Retorna lista de objetos Documentacao de acordo com parametros setados em
	 * um objeto Documentacao
	 * 
	 * @param documentacao
	 *            - objeto Documentacao com os atributos setados de acordo com a
	 *            pequisa a ser realizada.
	 * @return Collection - encapsula resultados da pesquisa.
	 * @throws ApplicationException
	 */
	public static Collection<Documentacao> listarDocumentacoes(
			Documentacao documentacao) throws ApplicationException {

		return hibernateFactory.getDocumentacaoDAO().listar(documentacao,
				new String[] { "descricao", "asc" });
	}

	/**
	 * Lista paginada de situação de imóveis.
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarSituacaoImovel(Pagina pag, String descricao)
			throws ApplicationException {

		SituacaoImovel situacao = new SituacaoImovel();
		situacao.setDescricao(descricao);

		try {

			if (pag.getTotalRegistros() == 0) {

				pag.setTotalRegistros(hibernateFactory.getSituacaoImovelDAO()
						.buscarQtdLista(situacao).intValue());
			}
			pag.setRegistros(hibernateFactory.getSituacaoImovelDAO().listar(
					situacao, new String[] { "descricao", "asc" },
					pag.getQuantidade(), pag.getPaginaAtual()));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Situação do Imóvel" }, e);
		}

		return pag;
	}

	/**
	 * Busca um objeto Situacao do Imovel através de seu código.
	 * 
	 * @param codSituacaoImovel
	 *            código da Situacao do imovel a ser localizada
	 * @return SituacaoImovel
	 * @throws ApplicationException
	 */
	public static SituacaoImovel obterSituacaoImovel(Integer codSituacaoImovel)
			throws ApplicationException {

		return hibernateFactory.getSituacaoImovelDAO().obter(codSituacaoImovel);
	}

	/**
	 * Verifica se já existe SituaçãoImovel com mesma Descricao
	 * 
	 * @param String
	 *            descricaoNovaSituacaoImovel
	 * @param Integer
	 *            codigo - codigo do objeto que esta sendo incluido(passar zero
	 *            se estiver sendo inlcuido)/alterado
	 * @return Boolean - true se existe SituaçãoImovel, false se SituaçãoImovel
	 *         não existe.
	 * @throws ApplicationException
	 */
	public static Boolean verificarSituacaoImovelDuplicado(
			String descricaoNovaSituacaoImovel, Integer codigo)
			throws ApplicationException {

		String descricaoBanco;
		String descricaoNovaSituacaoImovelTratada = descricaoNovaSituacaoImovel
				.trim().toUpperCase();

		try {
			// verifica se não existe forma incorporacao com o mesma descrição
			Collection<SituacaoImovel> listaSituacaoImovel = hibernateFactory
					.getSituacaoImovelDAO().listar();
			for (SituacaoImovel item : listaSituacaoImovel) {
				descricaoBanco = item.getDescricao().trim().toUpperCase();
				if (descricaoBanco.equals(descricaoNovaSituacaoImovelTratada)) {
					if (item.getCodSituacaoImovel().compareTo(codigo) != 0) {
						return true;
					}
				}
			}
			return false;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "situação do imóvel" }, e);
		}

	}

	/**
	 * Salva objeto SituacaoImovel.
	 * 
	 * @param objeto
	 *            a ser salvo.
	 * @throws ApplicationException
	 */
	public static void salvarSituacaoImovel(SituacaoImovel situacaoImovel)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getSituacaoImovelDAO().salvar(situacaoImovel);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarSituacaoImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarSituacaoImovel()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Situação do Imóvel" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Atualiza objeto SituacaoImovel.
	 * 
	 * @param SituacaoImovel
	 *            a ser atualizada.
	 * @throws ApplicationException
	 */
	public static void alterarSituacaoImovel(SituacaoImovel situacaoImovel)
			throws ApplicationException {
		try {

			HibernateUtil.currentTransaction();  
			hibernateFactory.getSituacaoImovelDAO().alterar(situacaoImovel);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarSituacaoImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;

		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarSituacaoImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Situação do Imóvel" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Remove objeto SituacaoImovel.
	 * 
	 * @param formaIncorporacao
	 *            a ser removida.
	 * @throws ApplicationException
	 */
	public static void excluirSituacaoImovel(int codSituacaoImovel)
			throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  

			// verifica se existe integridade referencial com tabelas -> tb_bem_imovel
			SituacaoImovel situacaoImovel = obterSituacaoImovel(codSituacaoImovel);
			if (situacaoImovel.getBemImovels() != null
					&& situacaoImovel.getBemImovels().size() > 0) {
				throw new ApplicationException("AVISO.22",
						ApplicationException.ICON_AVISO);
			}

			hibernateFactory.getSituacaoImovelDAO().excluir(situacaoImovel);

			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirSituacaoImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirSituacaoImovel()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Situação do Imóvel" }, ex, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Lista paginada de tipos de edificação.
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarTipoEdificacao(Pagina pag, String descricao)
			throws ApplicationException {

		TipoEdificacao tipoEdificacao = new TipoEdificacao();
		tipoEdificacao.setDescricao(descricao);

		try {

			if (pag.getTotalRegistros() == 0) {

				pag.setTotalRegistros(hibernateFactory.getTipoEdificacaoDAO()
						.buscarQtdLista(tipoEdificacao).intValue());
			}
			pag.setRegistros(hibernateFactory.getTipoEdificacaoDAO().listar(
					tipoEdificacao, new String[] { "descricao", "asc" },
					pag.getQuantidade(), pag.getPaginaAtual()));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Tipo de Edificação" }, e);
		}

		return pag;
	}

	/**
	 * Busca um objeto Tipo Edificacao através de seu código.
	 * 
	 * @param codTipoEdificacao
	 *            código do tipo de edificação a ser localizado
	 * @return TipoEdificacao
	 * @throws ApplicationException
	 */
	public static TipoEdificacao obterTipoEdificacao(Integer codTipoEdificacao)
			throws ApplicationException {

		return hibernateFactory.getTipoEdificacaoDAO().obter(codTipoEdificacao);
	}

	/**
	 * Verifica se já existe TipoEdificacao com mesma Descricao
	 * 
	 * @param String
	 *            descricaoNova
	 * @param Integer
	 *            codigoAtual - codigo do objeto que esta sendo incluido(passar
	 *            zero se estiver sendo inlcuido)/alterado
	 * @return Boolean - true se existe TipoEdificacao, false se TipoEdificacao
	 *         não existe.
	 * @throws ApplicationException
	 */
	public static Boolean verificarTipoEdificacaoDuplicado(
			String descricaoNova, Integer codigoAtual)
			throws ApplicationException {

		String descricaoBanco;
		String descricaoNovaTratada = descricaoNova.trim().toUpperCase();

		try {
			// verifica se não existe forma incorporacao com o mesma descrição
			Collection<TipoEdificacao> listaTipoEdificacao = hibernateFactory
					.getTipoEdificacaoDAO().listar();
			for (TipoEdificacao item : listaTipoEdificacao) {
				descricaoBanco = item.getDescricao().trim().toUpperCase();
				if (descricaoBanco.equals(descricaoNovaTratada)) {
					if (codigoAtual.compareTo(item.getCodTipoEdificacao()) != 0) {
						return true;
					}
				}
			}
			return false;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "situação do imóvel" }, e);
		}

	}

	/**
	 * Salva objeto TipoEdificacao.
	 * 
	 * @param objeto
	 *            a ser salvo.
	 * @throws ApplicationException
	 */
	public static void salvarTipoEdificacao(TipoEdificacao tipoEdificacao)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getTipoEdificacaoDAO().salvar(tipoEdificacao);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarTipoEdificacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarTipoEdificacao()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Tipo de Edificação" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Atualiza objeto TipoEdificacao.
	 * 
	 * @param TipoEdificacao
	 *            a ser atualizado.
	 * @throws ApplicationException
	 */
	public static void alterarTipoEdificacao(TipoEdificacao tipoEdificacao)
			throws ApplicationException {
		try {

			HibernateUtil.currentTransaction();  
			hibernateFactory.getTipoEdificacaoDAO().alterar(tipoEdificacao);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarTipoEdificacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;

		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarTipoEdificacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Tipo de Edificação" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Remove objeto TipoEdificacao.
	 * 
	 * @param tipoEdificacao
	 *            a ser removido.
	 * @throws ApplicationException
	 */
	public static void excluirTipoEdificacao(int codTipoEdificacao)
			throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  

			// verifica se existe integridade referencial com tabelas -> tb_bem_imovel
			TipoEdificacao tipoEdificacao = obterTipoEdificacao(codTipoEdificacao);
			if (tipoEdificacao.getEdificacaos() != null
					&& tipoEdificacao.getEdificacaos().size() > 0) {
				throw new ApplicationException("AVISO.34",
						ApplicationException.ICON_AVISO);
			}

			hibernateFactory.getTipoEdificacaoDAO().excluir(tipoEdificacao);

			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirTipoEdificacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirTipoEdificacao()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Tipo de Edificação" }, ex, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Busca um objeto Bem Imovel através de seu código e Edificações
	 * inicializadas.
	 * 
	 * @param codBemImovel
	 *            código do BemImovel a ser localizado
	 * @return BemImovel
	 * @throws Exception
	 */
	public static BemImovel obterBemImovelComEdificacoes(Integer codBemImovel)
			throws ApplicationException {

		return hibernateFactory.getBemImovelDAO().obterComEdificacaoes(
				codBemImovel);

	}

	/**
	 * Lista paginada de tipos de lei de bem imovel.
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarTipoLeiBemImovel(Pagina pag, String descricao)
			throws ApplicationException {

		TipoLeiBemImovel tipoLeiBemImovel = new TipoLeiBemImovel();
		tipoLeiBemImovel.setDescricao(descricao);

		try {

			if (pag.getTotalRegistros() == 0) {

				pag.setTotalRegistros(hibernateFactory.getTipoLeiBemImovelDAO()
						.buscarQtdLista(tipoLeiBemImovel).intValue());
			}
			pag.setRegistros(hibernateFactory.getTipoLeiBemImovelDAO().listar(
					tipoLeiBemImovel, new String[] { "descricao", "asc" },
					pag.getQuantidade(), pag.getPaginaAtual()));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Tipo de Lei do Bem Imóvel" }, e);
		}

		return pag;
	}

	/**
	 * Busca um objeto TipoLeiBemImovel através de seu código.
	 * 
	 * @param codTipoLeiBemImovel
	 *            código do tipo de lei de bem imovel a ser localizado
	 * @return TipoLeiBemImovel
	 * @throws ApplicationException
	 */
	public static TipoLeiBemImovel obterTipoLeiBemImovel(
			Integer codTipoLeiBemImovel) throws ApplicationException {

		return hibernateFactory.getTipoLeiBemImovelDAO().obter(
				codTipoLeiBemImovel);
	}

	/**
	 * Verifica se já existe TipoLeiBemImovel com mesma Descricao
	 * 
	 * @param String
	 *            descricaoNova
	 * @param Integer
	 *            codigoAtual - codigo do objeto que esta sendo incluido(passar
	 *            zero se estiver sendo inlcuido)/alterado
	 * @return Boolean - true se existe TipoLeiBemImovel, false se
	 *         TipoLeiBemImovel não existe.
	 * @throws ApplicationException
	 */
	public static Boolean verificarTipoLeiBemImovelDuplicado(
			String descricaoNova, Integer codigoAtual)
			throws ApplicationException {

		String descricaoBanco;
		String descricaoNovaTratada = descricaoNova.trim().toUpperCase();

		try {
			// verifica se não existe forma incorporacao com o mesma descrição
			Collection<TipoLeiBemImovel> listaTipoLeiBemImovel = hibernateFactory
					.getTipoLeiBemImovelDAO().listar();
			for (TipoLeiBemImovel item : listaTipoLeiBemImovel) {
				descricaoBanco = item.getDescricao().trim().toUpperCase();
				if (descricaoBanco.equals(descricaoNovaTratada)) {
					if (codigoAtual.compareTo(item.getCodTipoLeiBemImovel()) != 0) {
						return true;
					}
				}
			}
			return false;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "tipo de lei de bem imóvel" }, e);
		}

	}

	/**
	 * Salva objeto TipoLeiBemImovel.
	 * 
	 * @param objeto
	 *            a ser salvo.
	 * @throws ApplicationException
	 */
	public static void salvarTipoLeiBemImovel(TipoLeiBemImovel tipoLeiBemImovel)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getTipoLeiBemImovelDAO().salvar(tipoLeiBemImovel);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarTipoLeiBemImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarTipoLeiBemImovel()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Tipo de Lei do Bem Imóvel" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Atualiza objeto TipoEdificacao.
	 * 
	 * @param TipoEdificacao
	 *            a ser atualizado.
	 * @throws ApplicationException
	 */
	public static void alterarTipoLeiBemImovel(TipoLeiBemImovel tipoLeiBemImovel)
			throws ApplicationException {
		try {

			HibernateUtil.currentTransaction();  
			hibernateFactory.getTipoLeiBemImovelDAO().alterar(tipoLeiBemImovel);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarTipoLeiBemImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;

		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarTipoLeiBemImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Tipo de Lei do Bem Imóvel" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Remove objeto TipoLeiBemImovel.
	 * 
	 * @param tipoLeiBemImovel
	 *            a ser removido.
	 * @throws ApplicationException
	 */
	public static void excluirTipoLeiBemImovel(int codTipoLeiBemImovel)
			throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  

			// verifica se existe integridade referencial com tabelas -> tb_bem_imovel
			TipoLeiBemImovel tipoLeiBemImovel = obterTipoLeiBemImovel(codTipoLeiBemImovel);
			if (tipoLeiBemImovel.getLeiBemImovels() != null
					&& tipoLeiBemImovel.getLeiBemImovels().size() > 0) {
				throw new ApplicationException("AVISO.36",
						ApplicationException.ICON_AVISO);
			}

			hibernateFactory.getTipoLeiBemImovelDAO().excluir(tipoLeiBemImovel);

			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirTipoLeiBemImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirTipoLeiBemImovel()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Tipo de Lei do Bem Imóvel" }, ex, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Retorna lista de objetos Notificacao de acordo com parametros setados em
	 * um objeto FiltroRelatorioNotificacaoBemImovelDTO
	 * 
	 * @param rnbiDTO
	 *            - objeto FiltroRelatorioNotificacaoBemImovelDTO com os
	 *            atributos setados de acordo com a pequisa a ser realizada.
	 * @return Collection - encapsula resultados da pesquisa.
	 * @throws ApplicationException
	 */
	public static Collection<RelatorioNotificacaoDTO> listarNotificacaoParaRelatorio(
			FiltroRelatorioNotificacaoBemImovelDTO rnbiDTO)
			throws ApplicationException {
		return hibernateFactory.getNotificacaoDAO().listarParaRelatorioNotificacao(rnbiDTO);
	}

	/**
	 * Retorna lista de objetos Lote por BemImovel
	 * 
	 * @param codBemImovel
	 *            - codigo do Bem Imovel
	 * @return Collection - encapsula resultados da pesquisa.
	 * @throws ApplicationException
	 */
	public static Collection<Lote> listarLotesPorBemImovel(Integer codBemImovel)
			throws ApplicationException {

		return hibernateFactory.getLoteDAO().listarPorBemImovel(codBemImovel);
	}

	/**
	 * Retorna lista de objetos ItemComboDTO com Lotes por BemImovel
	 * 
	 * @param codBemImovel
	 *            - codigo do Bem Imovel
	 * @return Collection - encapsula resultados da pesquisa.
	 * @throws ApplicationException
	 */
	public static Collection<ItemComboDTO> listarLotesCombo(Integer codBemImovel)
			throws ApplicationException {
		Collection<Lote> lista = listarLotesPorBemImovel(codBemImovel);
		Collection<ItemComboDTO> ret = new ArrayList<ItemComboDTO>();
		for (Lote lote : lista) {
			ItemComboDTO obj = new ItemComboDTO();
			obj.setCodigo(String.valueOf(lote.getCodLote()));
			obj.setDescricao("Quadra: ".concat( lote.getQuadra().getDescricao()).concat(
					 " /Lote: ").concat( lote.getDescricao()));
			ret.add(obj);
		}
		return ret;
	}

	// * Métodos da Luciana - Fim *
	// * * * * * * * * * * * * * * * *
	// * Métodos do Rafael - Início *
	// * * * * * * * * * * * * * * * *
	/**
	 * Busca um objeto Denominação de Imóvel através de seu código.
	 * 
	 * @param codDenominacao
	 *            código da Denominação de Imóvel a ser localizada
	 * @return Denominacao
	 * @throws ApplicationException
	 */
	public static DenominacaoImovel obterDenominacaoImovel(
			Integer codDenominacaoImovel) throws ApplicationException {
		return hibernateFactory.getDenominacaoImovelDAO().obter(
				codDenominacaoImovel);
	}

	/**
	 * Lista paginada de denominações de imóveis.
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarDenominacaoImovel(Pagina pag, String descricao)
			throws ApplicationException {

		DenominacaoImovel denominacaoImovel = new DenominacaoImovel();
		denominacaoImovel.setDescricao(descricao);

		try {

			if (pag.getTotalRegistros() == 0) {
				pag.setTotalRegistros(hibernateFactory
						.getDenominacaoImovelDAO().buscarQtdLista(
								denominacaoImovel).intValue());
			}
			pag.setRegistros(hibernateFactory.getDenominacaoImovelDAO().listar(
					denominacaoImovel, new String[] { "descricao", "asc" },
					pag.getQuantidade(), pag.getPaginaAtual()));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Denominação do Imóvel" }, e);
		}

		return pag;
	}

	/**
	 * Verifica se já existe Denominação de Imóvel com mesmo nome.
	 * 
	 * @param String
	 *            descricaoNovaDenominacao
	 * @return Boolean - true se existe denominação, false se denominação não
	 *         existe.
	 * @throws ApplicationException
	 */
	public static Boolean verificaDenominacaoImovelDuplicado(
			String descricaoNovaDemoninacao) throws ApplicationException {

		String descricaoBanco;
		String descricaoNovaDemoninacaoTratada = descricaoNovaDemoninacao
				.trim().toUpperCase();

		try {
			// verifica se não existe denominação com o mesma descrição
			Collection<DenominacaoImovel> listaDenominacaoImovel = hibernateFactory
					.getDenominacaoImovelDAO().listar();
			for (DenominacaoImovel item : listaDenominacaoImovel) {
				descricaoBanco = item.getDescricao().trim().toUpperCase();
				if (descricaoBanco.equals(descricaoNovaDemoninacaoTratada)) {
					return true;
				}
			}
			return false;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "denominação de imóvel" }, e);
		}

	}

	/**
	 * Salva objeto Denominação de Imóvel.
	 * 
	 * @param denominacao
	 *            de imovel a ser salva.
	 * @return void.
	 * @throws ApplicationException
	 */
	public static void salvarDenominacaoImovel(
			DenominacaoImovel denominacaoImovel) throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getDenominacaoImovelDAO().salvar(denominacaoImovel);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarDenominacaoImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarDenominacaoImovel()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Denominação do Imóvel" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Atualiza objeto Denominação de Imóvel.
	 * 
	 * @param Denominação
	 *            de Imóvel a ser atualizado.
	 * @throws ApplicationException
	 */
	public static void alterarDenominacaoImovel(
			DenominacaoImovel denominacaoImovel) throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  
			hibernateFactory.getDenominacaoImovelDAO().alterar(denominacaoImovel);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarDenominacaoImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;

		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarDenominacaoImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Denominação do Imóvel" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Remove objeto denominacaoImovel.
	 * 
	 * @param Denominação
	 *            a ser removida.
	 * @throws ApplicationException
	 */
	public static void excluirDenominacaoImovel(int codDenominacaoImovel)
			throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  

			// verifica se existe integridade referencial
			DenominacaoImovel di = obterDenominacaoImovel(codDenominacaoImovel);
			if (di.getBemImovels() != null && di.getBemImovels().size() > 0) {
				throw new ApplicationException("AVISO.15",
						ApplicationException.ICON_AVISO);
			}

			hibernateFactory.getDenominacaoImovelDAO().excluir(di);

			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirDenominacaoImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirDenominacaoImovel()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Denominação do Imóvel" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Busca um objeto Tabelionato através de seu código.
	 * 
	 * @param codTabelionato
	 *            código do Tabelionato a ser localizado
	 * @return Tabelionato
	 * @throws ApplicationException
	 */
	public static Tabelionato obterTabelionato(Integer codTabelionato)
			throws ApplicationException {

		return hibernateFactory.getTabelionatoDAO().obter(codTabelionato);
	}

	/**
	 * Lista paginada de tabelionatos.
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarTabelionato(Pagina pag,
			TabelionatoPesquisaDTO tabelPesqDTO) throws ApplicationException {

		Tabelionato tabelionato = new Tabelionato();
		tabelionato.setDescricao(tabelPesqDTO.getDescricao());
		tabelionato.setUf(tabelPesqDTO.getUf());
		if (tabelPesqDTO.getMunicipio() != null){
			tabelionato.setCodMunicipio(Integer.valueOf(tabelPesqDTO.getMunicipio()));	
		}
		
		try {

			if (pag.getTotalRegistros() == 0) {
				pag.setTotalRegistros(hibernateFactory.getTabelionatoDAO()
						.buscarQtdLista(tabelionato).intValue());
			}
			pag.setRegistros(hibernateFactory.getTabelionatoDAO().listar(
					tabelionato, new String[] { "descricao", "asc" },
					pag.getQuantidade(), pag.getPaginaAtual()));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Tabelionato" }, e);
		}

		return pag;
	}

	/**
	 * Verifica se já existe Tabelionato com mesmo nome.
	 * 
	 * @param String
	 *            descricaoNovoTabelionato
	 * @return Boolean - true se existe tabelionato, false se tabelionato não
	 *         existe.
	 * @throws ApplicationException
	 */

	public static Boolean verificaTabelionatoDuplicado(int codTabelionato,
			TabelionatoPesquisaDTO tabelPesqDTO) throws ApplicationException {

		Tabelionato tabelionato = new Tabelionato();
		tabelionato.setDescricao(tabelPesqDTO.getDescricao());
		tabelionato.setUf(tabelPesqDTO.getUf());
		tabelionato.setMunicipio(tabelPesqDTO.getMunicipio());

		try {
			// verifica se não existe tabelionato com o mesma descrição, estado e município
			boolean achouSemCod = hibernateFactory.getTabelionatoDAO().verificarExistencia(tabelionato).booleanValue();
			if (codTabelionato == 0) {
				return new Boolean(achouSemCod);
			}
			tabelionato.setCodTabelionato(Integer.valueOf(codTabelionato));
			boolean achouComCod = hibernateFactory.getTabelionatoDAO()
					.verificarExistencia(tabelionato).booleanValue();
			return new Boolean(achouSemCod && !achouComCod);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "tabelionato" }, e);
		}
	}

	/**
	 * Salva objeto tabelionato.
	 * 
	 * @param Tabelionato
	 *            a ser salvo.
	 * @return void
	 * @throws ApplicationException
	 */
	public static void salvarTabelionato(Tabelionato tabelionato)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getTabelionatoDAO().salvar(tabelionato);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarTabelionato()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarTabelionato()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Tabelionato" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Atualiza objeto Tabelionato.
	 * 
	 * @param Tabelionato
	 *            a ser atualizado.
	 * @throws ApplicationException
	 */
	public static void alterarTabelionato(Tabelionato tabelionato)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getTabelionatoDAO().alterar(tabelionato);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarTabelionato()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;

		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarTabelionato()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Tabelionato" }, ex, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Remove objeto tabelionato.
	 * 
	 * @param Tabelionato
	 *            a ser removido.
	 * @throws ApplicationException
	 */
	public static void excluirTabelionato(int codTabelionato)
			throws ApplicationException {

		try {

			// verifica se existe integridade referencial
			HibernateUtil.currentTransaction();  

			Tabelionato tabelionato = obterTabelionato(codTabelionato);
			if (tabelionato.getBemImovels() != null
					&& tabelionato.getBemImovels().size() > 0) {
				throw new ApplicationException("AVISO.15",
						ApplicationException.ICON_AVISO);
			}

			hibernateFactory.getTabelionatoDAO().excluir(tabelionato);

			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirTabelionato()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirTabelionato()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Tabelionato" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Busca um objeto Tabelionato através de seu código.
	 * 
	 * @param codBemImovel
	 *            código do Tabelionato a ser localizado
	 * @return BemImovel
	 * @throws Exception
	 */
	public static BemImovel obterBemImovel(Integer codBemImovel)
			throws ApplicationException {

		return hibernateFactory.getBemImovelDAO().obter(codBemImovel);

	}

	/**
	 * Busca um objeto Tabelionato através de seu código.
	 * 
	 * @param codBemImovel
	 *            código do Tabelionato a ser localizado
	 * @return BemImovel
	 * @throws Exception
	 */
	public static BemImovel obterBemImovelExibir(Integer codBemImovel)
			throws ApplicationException {

		return hibernateFactory.getBemImovelDAO().obterExibir(codBemImovel);

	}

	/**
	 * Lista pagina de bens imóveis.
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarBemImovel(Pagina pag, BemImovelPesquisaDTO bemDTO)
			throws ApplicationException {

		try {
			//Tratamento para usuario operador de orgao
			List<Integer> listaCodOrgao = new ArrayList<Integer>();
			if (bemDTO.getListaOrgao() != null){
				for (Orgao o : bemDTO.getListaOrgao()){
					listaCodOrgao.add(o.getCodOrgao());
				}
				bemDTO.setListaCodOrgao(listaCodOrgao);
			}

			if (pag.getTotalRegistros() == 0) {
				pag.setTotalRegistros(hibernateFactory.getBemImovelDAO()
						.obterQuantidadeLista(pag.getQuantidade(),
								pag.getPaginaAtual(), bemDTO));
			}

			pag.setRegistros(hibernateFactory.getBemImovelDAO().listar(
					pag.getQuantidade(), pag.getPaginaAtual(), bemDTO));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Bem Imóvel" }, e);
		}

		return pag;
	}

	/**
	 * Lista pagina de bens imóveis.
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarBemImovel(Pagina pag, String codBemImovel)
			throws ApplicationException {

		BemImovel bemImovel = new BemImovel();
		bemImovel.setCodBemImovel(Integer.parseInt(codBemImovel));

		try {

			if (pag.getTotalRegistros() == 0) {
				pag.setTotalRegistros(hibernateFactory.getBemImovelDAO()
						.buscarQtdLista(bemImovel).intValue());
			}

			pag.setRegistros(hibernateFactory.getBemImovelDAO().listar(
					bemImovel, pag.getQuantidade(), pag.getPaginaAtual()));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Bem Imóvel" }, e);
		}

		return pag;
	}

	public static Collection<ClassificacaoBemImovel> listarClassificacaoBemImovels()
			throws ApplicationException {

		return hibernateFactory.getClassificacaoBemImovelDAO().listar(
				new ClassificacaoBemImovel(),
				new String[] { "descricao", "asc" });
	}

	public static Collection<SituacaoLegalCartorial> listarSituacaoLegalCartorials()
			throws ApplicationException {

		return hibernateFactory.getSituacaoLegalCartorialDAO().listar(
				new SituacaoLegalCartorial(),
				new String[] { "descricao", "asc" });
	}

	public static Collection<Cartorio> listarCartorios()
			throws ApplicationException {
		return hibernateFactory.getCartorioDAO().listar(null,
				new String[] { "descricao", "asc" });
	}

	public static Collection<Tabelionato> listarTabelionatos()
			throws ApplicationException {
		return hibernateFactory.getTabelionatoDAO().listar(null,
				new String[] { "descricao", "asc" });
	}

	public static Collection<FormaIncorporacao> listarFormaIncorporacaos()
			throws ApplicationException {
		return hibernateFactory.getFormaIncorporacaoDAO().listar(null,
				new String[] { "descricao", "asc" });
	}

	public static Collection<TipoConstrucao> listarTipoConstrucaos()
			throws ApplicationException {

		return hibernateFactory.getTipoConstrucaoDAO().listar(
				new TipoConstrucao(), new String[] { "descricao", "asc" });
	}

	public static Collection<TipoEdificacao> listarTipoEdificacaos()
			throws ApplicationException {

		return hibernateFactory.getTipoEdificacaoDAO().listar(
				new TipoEdificacao(), new String[] { "descricao", "asc" });
	}

	public static Collection<TipoDocumentacao> listarTipoDocumentacaos()
			throws ApplicationException {

		return hibernateFactory.getTipoDocumentacaoDAO().listar(
				new TipoDocumentacao(), new String[] { "descricao", "asc" });
	}

	public static Collection<SituacaoOcupacao> listarSituacaoOcupacaos()
			throws ApplicationException {

		return hibernateFactory.getSituacaoOcupacaoDAO().listar(
				new SituacaoOcupacao(), new String[] { "descricao", "asc" });
	}

	public static Collection<DenominacaoImovel> listarDenominacaoImovels()
			throws ApplicationException {

		return hibernateFactory.getDenominacaoImovelDAO().listar(
				new DenominacaoImovel(), new String[] { "descricao", "asc" });
	}

	public static Collection<SituacaoImovel> listarSituacaoImovels()
			throws ApplicationException {

		return hibernateFactory.getSituacaoImovelDAO().listar(
				new SituacaoImovel(), new String[] { "descricao", "asc" });
	}

	public static Collection<TipoLeiBemImovel> listarTipoLeiBemImovels()
			throws ApplicationException {

		return hibernateFactory.getTipoLeiBemImovelDAO().listar(
				new TipoLeiBemImovel(), new String[] { "descricao", "asc" });
	}

	public static Collection<Edificacao> listarEdificacoes(Edificacao edificacao)
			throws ApplicationException {

		return hibernateFactory.getEdificacaoDAO().listar(edificacao,
				new String[] { "especificacao", "asc" });
	}

	public static Collection<TipoDocumentacao> listarTipoDocumentacao()
			throws ApplicationException {

		return hibernateFactory.getTipoDocumentacaoDAO().listar(
				new TipoDocumentacao(), new String[] { "descricao", "asc" });
	}

	public static Collection<Documentacao> listarDocumentacaosSemNotificacao(
			Integer codImovel) throws ApplicationException {

		return hibernateFactory.getDocumentacaoDAO()
				.listarDocumentacoesSemNotificacao(codImovel);
	}

	/**
	 * Lista paginada de Leis de bens imóveis por bem imóvel
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarLeiBemImovel(Pagina pag, BemImovel bemImovel)
			throws ApplicationException {

		LeiBemImovel leiBemImovel = new LeiBemImovel();
		leiBemImovel.setBemImovel(bemImovel);
		bemImovel.setNumero(null);

		try {

			if (pag.getTotalRegistros() == 0) {

				pag.setTotalRegistros(hibernateFactory.getLeiBemImovelDAO()
						.buscarQtdLista(leiBemImovel).intValue());

			}
			pag.setRegistros(hibernateFactory.getLeiBemImovelDAO()
					.listarComRelacionamentos(bemImovel.getCodBemImovel(),
							pag.getQuantidade(), pag.getPaginaAtual()));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Lei do Bem Imóvel" }, e);
		}

		return pag;
	}

	/**
	 * Lista paginada de Leis de bens imóveis por bem imóvel
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarLeiBemImovel(Pagina pag, Integer codBemImovel)
			throws ApplicationException, Exception {

		LeiBemImovel leiBemImovel = new LeiBemImovel();
		leiBemImovel.setBemImovel(obterBemImovel(codBemImovel));
		leiBemImovel.getBemImovel().setNumero(null);

		try {

			if (pag.getTotalRegistros() == 0) {

				pag.setTotalRegistros(hibernateFactory.getLeiBemImovelDAO()
						.buscarQtdLista(leiBemImovel).intValue());
			}
			pag.setRegistros(hibernateFactory.getLeiBemImovelDAO()
					.listarComRelacionamentos(codBemImovel,
							pag.getQuantidade(), pag.getPaginaAtual()));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Lei do Bem Imóvel" }, e);
		}

		return pag;
	}

	public static void salvarLeiBemImovel(LeiBemImovel leiBemImovel)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getLeiBemImovelDAO().salvar(leiBemImovel);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarLeiBemImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarLeiBemImovel()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Lei do Bem Imóvel" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	public static LeiBemImovel obterLeiBemImovel(Integer codLeiBemImovel)
			throws ApplicationException {

		return hibernateFactory.getLeiBemImovelDAO().obter(codLeiBemImovel);
	}

	public static LeiBemImovel obterLeiBemImovelCompleto(Integer codLeiBemImovel) throws ApplicationException {
		return hibernateFactory.getLeiBemImovelDAO().obterCompleto(codLeiBemImovel);
	}

	public static void alterarLeiBemImovel(LeiBemImovel leiBemImovel)
			throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  
			hibernateFactory.getLeiBemImovelDAO().alterar(leiBemImovel);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarLeiBemImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;

		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarLeiBemImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Lei do Bem Imóvel" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	public static void excluirLeiBemImovel(int codLeiBemImovel)
			throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  

			LeiBemImovel leiBemImovel = obterLeiBemImovel(codLeiBemImovel);
			hibernateFactory.getLeiBemImovelDAO().excluir(leiBemImovel);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirLeiBemImovel()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirLeiBemImovel()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Lei do Bem Imóvel" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Lista paginada de lotes de imóveis por bem imóvel
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarLotes(Pagina pag, BemImovel bemImovel)
			throws ApplicationException {

		Lote lote = new Lote();
		try {

			if (pag.getTotalRegistros() == 0) {

				pag.setTotalRegistros(hibernateFactory.getLoteDAO()
						.buscarQtdLista(lote).intValue());
			}
			pag.setRegistros(hibernateFactory.getLoteDAO().listar(lote,
					new String[] { "descricao", "asc" }, pag.getQuantidade(),
					pag.getPaginaAtual()));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Lote" }, e);
		}

		return pag;
	}

	public static void salvarLote(Lote lote) throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getLoteDAO().salvar(lote);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarLote()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarLote()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Lote" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	public static void alterarLote(Lote lote) throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getLoteDAO().alterar(lote);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarLote()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;

		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarLote()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Lote" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	public static void excluirLote(int codLote) throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  
			Lote lote = obterLote(codLote);
			hibernateFactory.getLoteDAO().excluir(lote);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirLote()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirLote()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Lote" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Lista paginada de Confrontantes de imóveis por bem imóvel
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * 
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * 
	 * @throws ApplicationException
	 */
	public static Pagina listarConfrontantes(Pagina pag, BemImovel bemImovel)
			throws ApplicationException {

		Confrontante confrontante = new Confrontante();
		confrontante.setBemImovel(bemImovel);

		try {

			if (pag.getTotalRegistros() == 0) {

				pag.setTotalRegistros(hibernateFactory.getConfrontanteDAO()
						.buscarQtdLista(confrontante).intValue());
			}
			pag.setRegistros(hibernateFactory.getConfrontanteDAO().listar(
					confrontante, new String[] { "descricao", "asc" },
					pag.getQuantidade(), pag.getPaginaAtual()));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Confrontante" }, e);
		}

		return pag;
	}

	public static void salvarConfrontante(Confrontante confrontante)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getConfrontanteDAO().salvar(confrontante);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarConfrontante()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarConfrontante()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Confrontante" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	public static Confrontante obterConfrontante(Integer codConfrontante)
			throws ApplicationException {

		return hibernateFactory.getConfrontanteDAO().obter(codConfrontante);
	}

	public static void alterarConfrontante(Confrontante confrontante)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getConfrontanteDAO().alterar(confrontante);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarConfrontante()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;

		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarConfrontante()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Confrontante" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	public static void excluirConfrontante(int codConfrontante)
			throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  
			Confrontante confrontante = obterConfrontante(codConfrontante);
			hibernateFactory.getConfrontanteDAO().excluir(confrontante);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirConfrontante()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirConfrontante()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Confrontante" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	public static Collection<Quadra> listarQuadrasPorBemImovel(
			Integer codBemImovel) throws ApplicationException {
		return hibernateFactory.getQuadraDAO().listarPorBemImovel(codBemImovel);
	}

	public static Collection<ItemComboDTO> listarQuadrasCombo(
			Integer codBemImovel) throws ApplicationException {
		Collection<Quadra> lista = listarQuadrasPorBemImovel(codBemImovel);
		Collection<ItemComboDTO> ret = new ArrayList<ItemComboDTO>();
		for (Quadra quadra : lista) {
			ItemComboDTO obj = new ItemComboDTO();
			obj.setCodigo(String.valueOf(quadra.getCodQuadra()));
			obj.setDescricao("Quadra: ".concat( quadra.getDescricao()));
			ret.add(obj);
		}
		return ret;
	}

	/**
	 * Lista paginada de edificações por bem imóvel
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarLote(Pagina pag, Integer codBemImovel) throws ApplicationException {

		try {
			pag.setRegistros(hibernateFactory.getLoteDAO().listarComRelacionamentos(codBemImovel));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Lote" }, e);
		}

		return pag;
	}

	/**
	 * Lista paginada de quadras de imóveis por bem imóvel
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarQuadrasSemLote(Pagina pag, BemImovel bemImovel)
			throws ApplicationException {

		try {
			pag.setRegistros(hibernateFactory.getQuadraDAO().listarQuadraSemLote(bemImovel));
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Quadra sem Lote" }, e);
		}

		return pag;
	}

	public static Quadra salvarQuadra(Quadra quadra) throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			quadra = hibernateFactory.getQuadraDAO().salvarQuadra(quadra);
			HibernateUtil.commitTransaction(); 
			return quadra;
		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarQuadra()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarQuadra()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Quadra" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	public static Quadra obterQuadra(Integer codQuadra)
			throws ApplicationException {

		return hibernateFactory.getQuadraDAO().obter(codQuadra);
	}

	public static void alterarQuadra(Quadra quadra) throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getQuadraDAO().alterar(quadra);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarQuadra()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;

		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarQuadra()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Quadra" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	public static void excluirQuadra(int codQuadra) throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  
			Quadra quadra = obterQuadra(codQuadra);
			hibernateFactory.getQuadraDAO().excluir(quadra);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirQuadra()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirQuadra()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Quadra" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * 
	 * @param pag
	 * @param bemImovel
	 * @return
	 * @throws ApplicationException
	 */
	public static Pagina listarLeiBemImovelExibir(Pagina pag,
			BemImovel bemImovel) throws ApplicationException {

		LeiBemImovel leiBemImovel = new LeiBemImovel();
		leiBemImovel.setBemImovel(bemImovel);
		bemImovel.setNumero(null);

		try {

			if (pag.getTotalRegistros() == 0) {

				pag.setTotalRegistros(hibernateFactory.getLeiBemImovelDAO()
						.buscarQtdLista(leiBemImovel).intValue());

			}
			pag.setRegistros(hibernateFactory.getLeiBemImovelDAO()
					.listarComRelacionamentos(bemImovel.getCodBemImovel(),
							pag.getQuantidade(), pag.getPaginaAtual()));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Lei do Bem Imóvel" }, e);
		}

		return pag;
	}

	/**
	 * Lista paginada de situações de ocupação.
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarSituacaoOcupacao(Pagina pag, String descricao)
			throws ApplicationException {

		SituacaoOcupacao situacaoOcupacao = new SituacaoOcupacao();
		situacaoOcupacao.setDescricao(descricao);

		try {

			if (pag.getTotalRegistros() == 0) {
				pag.setTotalRegistros(hibernateFactory.getSituacaoOcupacaoDAO()
						.buscarQtdLista(situacaoOcupacao).intValue());
			}
			pag.setRegistros(hibernateFactory.getSituacaoOcupacaoDAO().listar(
					situacaoOcupacao, new String[] { "descricao", "asc" },
					pag.getQuantidade(), pag.getPaginaAtual()));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Situação da Ocupação" }, e);
		}

		return pag;
	}

	/**
	 * Verifica se já existe situacao de ocupação com mesmo nome.
	 * 
	 * @param String
	 *            descricaoNovaSituacaoOcupacao
	 * @return Boolean - true se existe situacao, senao false.
	 * @throws ApplicationException
	 */
	public static Boolean verificaSituacaoOcupacaoDuplicado(
			String descricaoNovaSituacaoOcupacao) throws ApplicationException {

		String descricaoBanco;
		String descricaoNovaSituacaoOcupacaoTratada = descricaoNovaSituacaoOcupacao
				.trim().toUpperCase();

		try {
			// verifica se não existe situação com o mesma descrição
			Collection<SituacaoOcupacao> listaSituacaoOcupacao = hibernateFactory
					.getSituacaoOcupacaoDAO().listar();
			for (SituacaoOcupacao item : listaSituacaoOcupacao) {
				descricaoBanco = item.getDescricao().trim().toUpperCase();
				if (descricaoBanco.equals(descricaoNovaSituacaoOcupacaoTratada)) {
					return true;
				}
			}
			return false;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "situação de ocupação" }, e);
		}

	}

	/**
	 * Salva objeto Situação de Ocupaçao.
	 * 
	 * @param situacao
	 *            de ocupacao de imovel a ser salva.
	 * @return void.
	 * @throws ApplicationException
	 */
	public static void salvarSituacaoOcupacao(SituacaoOcupacao situacaoOcupacao)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getSituacaoOcupacaoDAO().salvar(situacaoOcupacao);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarSituacaoOcupacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarSituacaoOcupacao()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Situação da Ocupação" }, ex,
					ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Atualiza objeto Situacao de ocupacao.
	 * 
	 * @param situacao
	 *            de ocupacao a ser atualizado.
	 * @throws ApplicationException
	 */
	public static void alterarSituacaoOcupacao(SituacaoOcupacao situacaoOcupacao)
			throws ApplicationException {

		// verifica se não é situacao ocupacao utilizada na validacao do form,
		// esse tipo de situacao não pode ser alterado nem excluído
		if ((situacaoOcupacao.getCodSituacaoOcupacao().intValue() == Dominios.SITUACAO_OCUPACAO_ADMINISTRACAO_ESTADUAL)
				|| (situacaoOcupacao.getCodSituacaoOcupacao().intValue() == Dominios.SITUACAO_OCUPACAO_ADMINISTRACAO_FEDERAL)
				|| (situacaoOcupacao.getCodSituacaoOcupacao().intValue() == Dominios.SITUACAO_OCUPACAO_ADMINISTRACAO_MUNICIPAL)
				|| (situacaoOcupacao.getCodSituacaoOcupacao().intValue() == Dominios.SITUACAO_OCUPACAO_TERCEIRO_AUTORIZADO)
				|| (situacaoOcupacao.getCodSituacaoOcupacao().intValue() == Dominios.SITUACAO_OCUPACAO_TERCEIRO_NAO_AUTORIZADO)) {
			throw new ApplicationException("ERRO.situacaoOcupacao",
					ApplicationException.ICON_AVISO);
		}
		try {

			HibernateUtil.currentTransaction();  
			hibernateFactory.getSituacaoOcupacaoDAO().alterar(situacaoOcupacao);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarSituacaoOcupacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;

		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarSituacaoOcupacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Situação da Ocupação" }, ex,
					ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Remove objeto situacaoOcupacao.
	 * 
	 * @param Situacao
	 *            de Ocupacao a ser removida.
	 * @throws ApplicationException
	 */
	public static void excluirSituacaoOcupacao(int codSituacaoOcupacao)
			throws ApplicationException {
		// verifica se não é situacao ocupacao utilizada na validacao do form,
		// esse tipo de situacao não pode ser alterado nem excluído
		if ((codSituacaoOcupacao == Dominios.SITUACAO_OCUPACAO_ADMINISTRACAO_ESTADUAL)
				|| (codSituacaoOcupacao == Dominios.SITUACAO_OCUPACAO_ADMINISTRACAO_FEDERAL)
				|| (codSituacaoOcupacao == Dominios.SITUACAO_OCUPACAO_ADMINISTRACAO_MUNICIPAL)
				|| (codSituacaoOcupacao == Dominios.SITUACAO_OCUPACAO_TERCEIRO_AUTORIZADO)
				|| (codSituacaoOcupacao == Dominios.SITUACAO_OCUPACAO_TERCEIRO_NAO_AUTORIZADO)) {
			throw new ApplicationException("ERRO.situacaoOcupacao",
					ApplicationException.ICON_AVISO);
		}
		try {

			HibernateUtil.currentTransaction();  

			// verifica se existe integridade referencial
			SituacaoOcupacao di = obterSituacaoOcupacao(codSituacaoOcupacao);
			if (di.getOcupacaos() != null && di.getOcupacaos().size() > 0) {
				throw new ApplicationException("AVISO.26",
						ApplicationException.ICON_AVISO);
			}

			hibernateFactory.getSituacaoOcupacaoDAO().excluir(di);

			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirSituacaoOcupacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirSituacaoOcupacao()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Situação da Ocupação" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	public static Collection<LeiBemImovelExibirBemImovelDTO> listarLeiBemImovelsExibir(
			Integer codBemImovel) throws ApplicationException {
		return hibernateFactory.getLeiBemImovelDAO().listarPorBemImovel(
				codBemImovel);
	}

	public static Collection<LoteExibirBemImovelDTO> listarLotesExibir(
			Integer codBemImovel) throws ApplicationException {
		return hibernateFactory.getLoteDAO().listarPorBemImovelExibir(
				codBemImovel);
	}

	public static Collection<ConfrontanteExibirBemImovelDTO> listarConfrontantesExibir(
			Integer codBemImovel) throws ApplicationException {
		return hibernateFactory.getConfrontanteDAO().listarPorBemImovelExibir(
				codBemImovel);
	}

	public static Collection<AvaliacaoExibirBemImovelDTO> listarAvaliacaosExibir(
			Integer codBemImovel) throws ApplicationException {
		return hibernateFactory.getAvaliacaoDAO().listarPorBemImovelExibir(
				codBemImovel);
	}

	public static Collection<CoordenadaUtmExibirBemImovelDTO> listarCoordenadaUtmsExibir(
			Integer codBemImovel) throws ApplicationException {
		return hibernateFactory.getCoordenadaUtmDAO().listarPorBemImovelExibir(
				codBemImovel);
	}

	public static Collection<EdificacaoExibirBemImovelDTO> listarEdificacaosExibir(
			Integer codBemImovel) throws ApplicationException {
		return hibernateFactory.getEdificacaoDAO().listarPorBemImovelExibir(
				codBemImovel);
	}
	
	public static Collection<EdificacaoExibirBemImovelDTO> listarImpressaoEdificacaoBensImoveis(
			Integer codBemImovel) throws ApplicationException {
		return hibernateFactory.getEdificacaoDAO().listarImpressaoEdificacaoBensImoveis(
				codBemImovel);
	}
	

	public static Collection<OcupacaoExibirBemImovelDTO> listarOcupacaosExibir(
			Integer codBemImovel) throws ApplicationException {
		return hibernateFactory.getOcupacaoDAO().listarPorBemImovelExibir(
				codBemImovel);
	}

	public static Collection<DocumentacaoNotificacaoExibirBemImovelDTO> listarDocumentacaoNotificacaosExibir(
			Integer codBemImovel) throws ApplicationException {
		return hibernateFactory.getDocumentacaoDAO()
				.listarDocumentacaoNotificacaoPorBemImovelExibir(codBemImovel);
	}

	public static Collection<DocumentacaoSemNotificacaoExibirBemImovelDTO> listarDocumentacaoSemNotificacaosExibir(
			Integer codBemImovel) throws ApplicationException {
		return hibernateFactory.getDocumentacaoDAO()
				.listarDocumentacaoSemNotificacaoPorBemImovelExibir(
						codBemImovel);
	}

	public static Collection<OcorrenciaDocumentacaoExibirBemImovelDTO> listarOcorrenciaDocumentacaosExibir(
			Integer codBemImovel) throws ApplicationException {
		return hibernateFactory.getOcorrenciaDocumentacaoDAO()
				.listarOcorrenciaDocumentacaoPorBemImovelExibir(codBemImovel);
	}

	/**
	 * Lista paginada de situações legais cartoriais.
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarSituacaoLegalCartorial(Pagina pag,
			String descricao) throws ApplicationException {

		SituacaoLegalCartorial situacaoLegalCartorial = new SituacaoLegalCartorial();
		situacaoLegalCartorial.setDescricao(descricao);

		try {

			if (pag.getTotalRegistros() == 0) {
				pag.setTotalRegistros(hibernateFactory
						.getSituacaoLegalCartorialDAO().buscarQtdLista(
								situacaoLegalCartorial).intValue());
			}
			pag.setRegistros(hibernateFactory.getSituacaoLegalCartorialDAO()
					.listar(situacaoLegalCartorial,
							new String[] { "descricao", "asc" },
							pag.getQuantidade(), pag.getPaginaAtual()));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Situação Legal Cartorial" }, e);
		}

		return pag;
	}

	/**
	 * Verifica se já existe Situação Legal Cartorial com mesmo nome.
	 * 
	 * @param String
	 *            descricaoNovaSituacaoLegal
	 * @return Boolean - true se existe denominação, false se denominação não
	 *         existe.
	 * @throws ApplicationException
	 */
	public static Boolean verificaSituacaoLegalCartorialDuplicado(
			String descricaoNovaSituacaoLegal) throws ApplicationException {

		String descricaoBanco;
		String descricaoNovaSituacaoLegalTratada = descricaoNovaSituacaoLegal
				.trim().toUpperCase();

		try {
			// verifica se não existe denominação com o mesma descrição
			Collection<SituacaoLegalCartorial> listaSituacaoLegalCartorial = hibernateFactory
					.getSituacaoLegalCartorialDAO().listar();
			for (SituacaoLegalCartorial item : listaSituacaoLegalCartorial) {
				descricaoBanco = item.getDescricao().trim().toUpperCase();
				if (descricaoBanco.equals(descricaoNovaSituacaoLegalTratada)) {
					return true;
				}
			}
			return false;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "situacao legal cartorial" }, e);
		}

	}

	/**
	 * Salva objeto Situação Legal Cartorial.
	 * 
	 * @param situacaoLegalCartorial
	 *            a ser salva.
	 * @return void.
	 * @throws ApplicationException
	 */
	public static void salvarSituacaoLegalCartorial(
			SituacaoLegalCartorial situacaoLegalCartorial)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getSituacaoLegalCartorialDAO().salvar(situacaoLegalCartorial);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarSituacaoLegalCartorial()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarSituacaoLegalCartorial()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Situação Legal Cartorial" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Atualiza objeto Situação Legal Cartorial.
	 * 
	 * @param Denominação
	 *            de Imóvel a ser atualizado.
	 * @throws ApplicationException
	 */
	public static void alterarSituacaoLegalCartorial(
			SituacaoLegalCartorial situacaoLegalCartorial)
			throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  
			hibernateFactory.getSituacaoLegalCartorialDAO().alterar(
					situacaoLegalCartorial);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarSituacaoLegalCartorial()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;

		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarSituacaoLegalCartorial()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Situação Legal Cartorial" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Remove objeto situacaoLegalCartorial.
	 * 
	 * @param Denominação
	 *            a ser removida.
	 * @throws ApplicationException
	 */
	public static void excluirSituacaoLegalCartorial(
			int codSituacaoLegalCartorial) throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  

			// verifica se existe integridade referencial
			SituacaoLegalCartorial di = obterSituacaoLegalCartorial(codSituacaoLegalCartorial);
			if (di.getBemImovels() != null && di.getBemImovels().size() > 0) {
				throw new ApplicationException("AVISO.25",
						ApplicationException.ICON_AVISO);
			}

			hibernateFactory.getSituacaoLegalCartorialDAO().excluir(di);

			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirSituacaoLegalCartorial()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirSituacaoLegalCartorial()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Situação Legal Cartorial" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Lista paginada de tipos de construcao.
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarTipoConstrucao(Pagina pag, String descricao)
			throws ApplicationException {

		TipoConstrucao tipoConstrucao = new TipoConstrucao();
		tipoConstrucao.setDescricao(descricao);

		try {

			if (pag.getTotalRegistros() == 0) {
				pag.setTotalRegistros(hibernateFactory.getTipoConstrucaoDAO()
						.buscarQtdLista(tipoConstrucao).intValue());
			}
			pag.setRegistros(hibernateFactory.getTipoConstrucaoDAO().listar(
					tipoConstrucao, new String[] { "descricao", "asc" },
					pag.getQuantidade(), pag.getPaginaAtual()));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Tipo de Construção" }, e);
		}

		return pag;
	}

	/**
	 * Verifica se já existe Situação Legal Cartorial com mesmo nome.
	 * 
	 * @param String
	 *            descricaoNovaSituacaoLegal
	 * @return Boolean - true se existe tipo de contrução, false se tipo não
	 *         existe.
	 * @throws ApplicationException
	 */
	public static Boolean verificaTipoConstrucaoDuplicado(
			String descricaoNovaSituacaoLegal) throws ApplicationException {

		String descricaoBanco;
		String descricaoNovaSituacaoLegalTratada = descricaoNovaSituacaoLegal
				.trim().toUpperCase();

		try {
			// verifica se não existe tipo com o mesma descrição
			Collection<TipoConstrucao> listaTipoConstrucao = hibernateFactory
					.getTipoConstrucaoDAO().listar();
			for (TipoConstrucao item : listaTipoConstrucao) {
				descricaoBanco = item.getDescricao().trim().toUpperCase();
				if (descricaoBanco.equals(descricaoNovaSituacaoLegalTratada)) {
					return true;
				}
			}
			return false;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "tipo de construcao" }, e);
		}

	}

	/**
	 * Salva objeto Situação Legal Cartorial.
	 * 
	 * @param tipoConstrucao
	 *            a ser salva.
	 * @return void.
	 * @throws ApplicationException
	 */
	public static void salvarTipoConstrucao(TipoConstrucao tipoConstrucao)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getTipoConstrucaoDAO().salvar(tipoConstrucao);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarTipoConstrucao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarTipoConstrucao()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Tipo de Construção" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Atualiza objeto tipoConstrucao.
	 * 
	 * @param tipoConstrucao
	 *            de Edificacao a ser atualizado.
	 * @throws ApplicationException
	 */
	public static void alterarTipoConstrucao(TipoConstrucao tipoConstrucao)
			throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  
			hibernateFactory.getTipoConstrucaoDAO().alterar(tipoConstrucao);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarTipoConstrucao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;

		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarTipoConstrucao()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Tipo de Construção" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Remove objeto tipoConstrucao.
	 * 
	 * @param tipoConstrucao
	 *            a ser removida.
	 * @throws ApplicationException
	 */
	public static void excluirTipoConstrucao(int codTipoConstrucao)
			throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  

			// verifica se existe integridade referencial
			TipoConstrucao di = obterTipoConstrucao(codTipoConstrucao);
			if (di.getEdificacaos() != null && di.getEdificacaos().size() > 0) {
				throw new ApplicationException("AVISO.30",
						ApplicationException.ICON_AVISO);
			}

			hibernateFactory.getTipoConstrucaoDAO().excluir(di);

			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirTipoConstrucao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirTipoConstrucao()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Tipo de Construção" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Verifica se já existe Quadra de Imóvel com mesmo nome.
	 * 
	 * @param String
	 *            descricaoNovaDenominacao
	 * @return Boolean - true se existe denominação, false se denominação não
	 *         existe.
	 * @throws ApplicationException
	 */
	public static Boolean verificaQuadraDuplicado(String descricaoNovaQuadra,
			Integer codBemImovel) throws ApplicationException {

		String descricaoBanco;
		String descricaoNovaQuadraTratada = descricaoNovaQuadra.trim()
				.toUpperCase();

		try {
			// verifica se não existe denominação com o mesma descrição
			Collection<Quadra> listaQuadra = hibernateFactory.getQuadraDAO()
					.listarPorBemImovel(codBemImovel);
			for (Quadra item : listaQuadra) {
				descricaoBanco = item.getDescricao().trim().toUpperCase();
				if (descricaoBanco.equals(descricaoNovaQuadraTratada)) {
					return true;
				}
			}
			return false;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "quadra" }, e);
		}

	}

	/**
	 * Verifica se já existe Confrontante de Imóvel com mesmo nome.
	 * 
	 * @param String
	 *            descricaoNovaDenominacao
	 * @return Boolean - true se existe denominação, false se denominação não
	 *         existe.
	 * @throws ApplicationException
	 */
	public static Boolean verificaConfrontanteDuplicado(
			String descricaoNovaConfrontante, Integer codBemImovel)
			throws ApplicationException {

		String descricaoBanco;
		String descricaoNovaConfrontanteTratada = descricaoNovaConfrontante
				.trim().toUpperCase();

		try {
			// verifica se não existe denominação com o mesma descrição
			Collection<Confrontante> listaConfrontante = hibernateFactory
					.getConfrontanteDAO().listarPorBemImovel(codBemImovel);
			for (Confrontante item : listaConfrontante) {
				descricaoBanco = item.getDescricao().trim().toUpperCase();
				if (descricaoBanco.equals(descricaoNovaConfrontanteTratada)) {
					return true;
				}
			}
			return false;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "confrontante" }, e);
		}

	}

	/**
	 * Verifica se já existe Lote de Imóvel com mesmo nome.
	 * 
	 * @param String
	 *            descricaoNovoLote
	 * @return Boolean - true se existe lote/quadra, false se lote/quadra não
	 *         existe.
	 * @throws ApplicationException
	 */
	public static Boolean verificaLoteDuplicado(String descricaoNovaLote,
			Integer codQuadra) throws ApplicationException {

		String descricaoBanco;
		String descricaoNovaLoteTratada = descricaoNovaLote.trim()
				.toUpperCase();

		try {
			// verifica se não existe denominação com o mesma descrição
			Collection<Lote> listaLote = hibernateFactory.getLoteDAO()
					.listarPorQuadra(codQuadra);
			for (Lote item : listaLote) {
				descricaoBanco = item.getDescricao().trim().toUpperCase();
				if (descricaoBanco.equals(descricaoNovaLoteTratada)) {
					return true;
				}
			}
			return false;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "lote" },
					e);
		}

	}

	public static ImpressaoBemImovelDTO obterBemImovelImpressao(
			Integer codBemImovel) throws ApplicationException, Exception {
		return hibernateFactory.getBemImovelDAO().obterImpressaoBemImovel(
				codBemImovel);
	}

	public static String executarMigracao(List<BemImovel> listaBens) throws ApplicationException{
		StringBuffer msgErros = new StringBuffer();

		try {
			HibernateUtil.currentTransaction();
			
			for (BemImovel bemImovel : listaBens){
				BemImovel bemJaExistente = hibernateFactory.getBemImovelDAO().obter(bemImovel.getCodBemImovel());
				if (bemJaExistente != null){
					if (msgErros.toString().trim().length() == 0) {
						msgErros.append("Bens imóveis substituídos.");
						msgErros.append("\n");
						msgErros.append(bemImovel.getCodBemImovel());
						msgErros.append(", ");
						msgErros.append("\n");
					} else {
						msgErros.append(bemImovel.getCodBemImovel());
						msgErros.append(", ");
						msgErros.append("\n");
					}
					bemJaExistente.setAdministracao(bemImovel.getAdministracao());
					bemJaExistente.setClassificacaoBemImovel(bemImovel.getClassificacaoBemImovel());
					bemJaExistente.setSituacaoLegalCartorial(bemImovel.getSituacaoLegalCartorial());
					bemJaExistente.setCep(bemImovel.getCep());
					bemJaExistente.setLogradouro(bemImovel.getLogradouro());
					bemJaExistente.setNumero(bemImovel.getNumero());
					bemJaExistente.setDenominacaoImovel(bemImovel.getDenominacaoImovel());
					bemJaExistente.setComplemento(bemImovel.getComplemento());
					bemJaExistente.setBairroDistrito(bemImovel.getBairroDistrito());
					bemJaExistente.setCodMunicipio(bemImovel.getCodMunicipio());
					bemJaExistente.setMunicipio(bemImovel.getMunicipio());
					bemJaExistente.setUf(bemImovel.getUf());
					bemJaExistente.setAreaTerreno(bemImovel.getAreaTerreno());
					bemJaExistente.setAreaDispoNivel(bemImovel.getAreaDispoNivel());
					bemJaExistente.setAreaConstruida(bemImovel.getAreaConstruida());
					bemJaExistente.setObservacoesMigracao(bemImovel.getObservacoesMigracao());
					bemJaExistente.setOrgao(bemImovel.getOrgao());
					
					//remove Quadras e Lotes
					for (Quadra q: bemJaExistente.getQuadras()){
						for (Lote l : q.getLotes()){
							hibernateFactory.getLoteDAO().excluir(l);
						}
						hibernateFactory.getQuadraDAO().excluir(q);
					}
					bemJaExistente.setQuadras(null);
					Set<Quadra> listaQuadra  = new HashSet<Quadra>();
					if (bemImovel.getQuadras() != null){
						for (Quadra q :bemImovel.getQuadras()){
							Quadra quadraNova = new Quadra();
							quadraNova.setDescricao(q.getDescricao());
							quadraNova.setCpfResponsavel(q.getCpfResponsavel());
							quadraNova.setTsInclusao(q.getTsInclusao());
							quadraNova.setBemImovel(bemJaExistente);
							hibernateFactory.getQuadraDAO().salvar(quadraNova);
							Set<Lote> listaLote  = new HashSet<Lote>();
							if (q.getLotes() != null){
								for (Lote l : q.getLotes()){
									Lote loteNovo = new Lote();
									loteNovo.setDescricao(l.getDescricao());
									loteNovo.setCpfResponsavel(l.getCpfResponsavel());
									loteNovo.setTsInclusao(l.getTsInclusao());
									loteNovo.setQuadra(quadraNova);
									hibernateFactory.getLoteDAO().salvar(loteNovo);
									listaLote.add(loteNovo);
								}
								for (Lote loteNovo : listaLote){
									quadraNova.getLotes().add(loteNovo);
								}	
							}
							
							listaQuadra.add(quadraNova);
						}	
						bemJaExistente.setQuadras(listaQuadra);
					}else{
						bemJaExistente.setQuadras(null);
					}					
					
					//remove Confrontantes
					for (Confrontante c: bemJaExistente.getConfrontantes()){
						hibernateFactory.getConfrontanteDAO().excluir(c);
					}
					bemJaExistente.getConfrontantes().clear();
					for (Confrontante conf : bemImovel.getConfrontantes()){
						Confrontante confrontanteNovo = new Confrontante();
						confrontanteNovo.setBemImovel(bemJaExistente);
						confrontanteNovo.setCpfResponsavel(conf.getCpfResponsavel());
						confrontanteNovo.setDescricao(conf.getDescricao());
						confrontanteNovo.setTsInclusao(conf.getTsInclusao());
						hibernateFactory.getConfrontanteDAO().salvar(confrontanteNovo);
						bemJaExistente.getConfrontantes().add(confrontanteNovo);
					}
										
					//remove Coordenadas
					for (CoordenadaUtm c: bemJaExistente.getCoordenadaUtms()){
						hibernateFactory.getCoordenadaUtmDAO().excluir(c);
					}
					bemJaExistente.getCoordenadaUtms().clear();
					for (CoordenadaUtm coord : bemImovel.getCoordenadaUtms()){
						CoordenadaUtm coordNovo = new CoordenadaUtm();
						coordNovo.setBemImovel(bemJaExistente);
						coordNovo.setCpfResponsavel(coord.getCpfResponsavel());
						coordNovo.setCoordenadaX(coord.getCoordenadaX());
						coordNovo.setCoordenadaY(coord.getCoordenadaY());
						coordNovo.setTsInclusao(coord.getTsInclusao());
						hibernateFactory.getCoordenadaUtmDAO().salvar(coordNovo);
						bemJaExistente.getCoordenadaUtms().add(coordNovo);
						
					}

					//remove Documentacao
					for (Documentacao d: bemJaExistente.getDocumentacaos()){
						for (Documentacao dd : d.getDocumentacaos()){
							hibernateFactory.getDocumentacaoDAO().excluir(dd);
						}
						d.getDocumentacaos().clear();
						for (Notificacao n : d.getNotificacaos()){
							hibernateFactory.getNotificacaoDAO().excluir(n);
						}
						d.getNotificacaos().clear();
						for (OcorrenciaDocumentacao o : d.getOcorrenciaDocumentacaos()){
							hibernateFactory.getOcorrenciaDocumentacaoDAO().excluir(o);
						}
						d.getOcorrenciaDocumentacaos().clear();
						hibernateFactory.getDocumentacaoDAO().excluir(d);
					}
					
					bemJaExistente.getDocumentacaos().clear();
					bemJaExistente.setDocumentacaos(bemImovel.getDocumentacaos());

					//remove Edificacao
					
					List<Edificacao> listaEdificacaoAux  = Util.setToList(bemJaExistente.getEdificacaos());
					
					bemJaExistente.getEdificacaos().clear();
					
					for (Edificacao e: listaEdificacaoAux){
						for (Documentacao d : e.getDocumentacaos()){
							hibernateFactory.getDocumentacaoDAO().excluir(d);
						}
						e.getDocumentacaos().clear();
						for (Lote l : e.getLotes()){
							hibernateFactory.getLoteDAO().excluir(l);
						}
						e.getLotes().clear();
						for (Avaliacao a : e.getAvaliacaos()){
							hibernateFactory.getAvaliacaoDAO().excluir(a);
						}
						e.getAvaliacaos().clear();
						for (Ocupacao o : e.getOcupacaos()){
							hibernateFactory.getOcupacaoDAO().excluir(o);
						}
						e.getOcupacaos().clear();
						hibernateFactory.getEdificacaoDAO().excluir(e);
					}
					
					if (bemImovel.getEdificacaos() != null) {
						//insere Edificacao
						for (Edificacao e: bemImovel.getEdificacaos()){
							Edificacao eNova = new Edificacao();
							eNova.setAreaConstruida(e.getAreaConstruida());
							eNova.setAreaUtilizada(e.getAreaUtilizada());
							eNova.setBemImovel(bemJaExistente);
							eNova.setCpfResponsavel(e.getCpfResponsavel());
							eNova.setDataAverbacao(e.getDataAverbacao());
							eNova.setEspecificacao(e.getEspecificacao());
							eNova.setLogradouro(e.getLogradouro());
							eNova.setTipoConstrucao(e.getTipoConstrucao());
							eNova.setTipoEdificacao(e.getTipoEdificacao());
							eNova.setTsInclusao(e.getTsInclusao());
							eNova.setOcupacaos(null);
							eNova.setLotes(null);
							eNova.setAvaliacaos(null);
							eNova.setDocumentacaos(null);
							hibernateFactory.getEdificacaoDAO().salvar(eNova);
							
							//ocupação de edificacao
							
							if (e.getOcupacaos() != null && !e.getOcupacaos().isEmpty()){
								Set<Ocupacao> listaOcupacao  = new HashSet<Ocupacao>();
								for (Ocupacao o : e.getOcupacaos()){
									Ocupacao ocupacaoNova = new Ocupacao();
									ocupacaoNova.setBemImovel(bemJaExistente);
									ocupacaoNova.setCpfResponsavel(o.getCpfResponsavel());
									ocupacaoNova.setEdificacao(eNova);
									ocupacaoNova.setTsInclusao(o.getTsInclusao());
									ocupacaoNova.setDescricao(o.getDescricao());
									ocupacaoNova.setAtivo(true);
									ocupacaoNova.setDataLei(o.getDataLei());
									ocupacaoNova.setDataOcupacao(o.getDataOcupacao());
									ocupacaoNova.setNumeroLei(o.getNumeroLei());
									ocupacaoNova.setOcupacaoMetroQuadrado(o.getOcupacaoMetroQuadrado());
									ocupacaoNova.setOcupacaoPercentual(o.getOcupacaoPercentual());
									ocupacaoNova.setOrgao(o.getOrgao());
									ocupacaoNova.setSituacaoOcupacao(o.getSituacaoOcupacao());
									hibernateFactory.getOcupacaoDAO().salvar(ocupacaoNova);
									listaOcupacao.add(ocupacaoNova);
								}
								eNova.setOcupacaos(listaOcupacao);	
							}
							//avaliação
							if (e.getAvaliacaos() != null && !e.getAvaliacaos().isEmpty()){
								Set<Avaliacao> listaAvaliacao  = new HashSet<Avaliacao>();
								for (Avaliacao ava : e.getAvaliacaos()){
									Avaliacao avaliacaoNova = new Avaliacao();
									avaliacaoNova.setCpfResponsavel(eNova.getCpfResponsavel());
									avaliacaoNova.setEdificacao(eNova);
									avaliacaoNova.setTsInclusao(eNova.getTsInclusao());
									avaliacaoNova.setValor(ava.getValor());
									avaliacaoNova.setBemImovel(ava.getBemImovel());
									avaliacaoNova.setDataAvaliacao(ava.getDataAvaliacao());
									avaliacaoNova.setTsAtualizacao(new Date());
									hibernateFactory.getAvaliacaoDAO().salvar(avaliacaoNova);
									listaAvaliacao.add(avaliacaoNova);
								}
								eNova.setAvaliacaos(listaAvaliacao);	
							}
							bemJaExistente.getEdificacaos().add(eNova);
						}
					}					
					
					bemJaExistente = hibernateFactory.getBemImovelDAO().merge(bemJaExistente);
					hibernateFactory.getBemImovelDAO().alterar(bemJaExistente);
				} else { //bem nao existe ainda , salvar o bem imovel.
					Set<Quadra> listaQuadra = bemImovel.getQuadras();
					Set<Confrontante> listaConfrontante = bemImovel.getConfrontantes();
					Set<CoordenadaUtm> listaCoordenadaUtm = bemImovel.getCoordenadaUtms();
					Set<Documentacao> listaDocumentacao = bemImovel.getDocumentacaos();
					Set<Edificacao> listaEdificacao = bemImovel.getEdificacaos();
					
					bemImovel.setQuadras(null);
					bemImovel.setConfrontantes(null);
					bemImovel.setCoordenadaUtms(null);
					bemImovel.setDocumentacaos(null);
					bemImovel.setEdificacaos(null);
					
					bemImovel = hibernateFactory.getBemImovelDAO().salvarBemImovel(bemImovel);
					
					bemImovel.setQuadras(listaQuadra);
					bemImovel.setConfrontantes(listaConfrontante);
					bemImovel.setCoordenadaUtms(listaCoordenadaUtm);
					bemImovel.setDocumentacaos(listaDocumentacao);
					bemImovel.setEdificacaos(listaEdificacao);
					
					//insere Quadras e Lotes
					if (bemImovel.getQuadras() != null) {
						Set<Lote> listaLote  = new HashSet<Lote>();
						listaQuadra = new HashSet<Quadra>();
						for (Quadra q: bemImovel.getQuadras()){
							listaLote = q.getLotes();
							q.setLotes(null);
							q = hibernateFactory.getQuadraDAO().salvarQuadra(q);
							q.setLotes(listaLote);
							for (Lote l : q.getLotes()){
								l.setQuadra(q);
								l = hibernateFactory.getLoteDAO().salvarLote(l);
								listaLote.add(l);
							}
							q.setLotes(listaLote);
							listaQuadra.add(q);
						}
						bemImovel.setQuadras(listaQuadra);
						
					}
					
					//insere Confrontantes
					if (bemImovel.getConfrontantes() != null) {
						listaConfrontante = new HashSet<Confrontante>();
						for (Confrontante c: bemImovel.getConfrontantes()){
							c = hibernateFactory.getConfrontanteDAO().salvarConfrontante(c);
							listaConfrontante.add(c);
						}
						bemImovel.setConfrontantes(listaConfrontante);
					}
					
					//insere Coordenadas
					if (bemImovel.getCoordenadaUtms() != null) {
						listaCoordenadaUtm = new HashSet<CoordenadaUtm>();
						for (CoordenadaUtm c: bemImovel.getCoordenadaUtms()){
							c = hibernateFactory.getCoordenadaUtmDAO().salvarCoordenadaUtm(c);
							listaCoordenadaUtm.add(c);
						}
						bemImovel.setCoordenadaUtms(listaCoordenadaUtm);
					}

					if (bemImovel.getDocumentacaos() != null) {
						//insere Documentacao
						listaDocumentacao = new HashSet<Documentacao>();
						Set<Documentacao> listaDocumentacaoDoc = new HashSet<Documentacao>();
						Set<Notificacao> listaNotificacao = new HashSet<Notificacao>();
						Set<OcorrenciaDocumentacao> listaOcorrenciaDocumentacao = new HashSet<OcorrenciaDocumentacao>();
						for (Documentacao d: bemImovel.getDocumentacaos()){
							d = hibernateFactory.getDocumentacaoDAO().salvarDocumentacao(d);
							for (Documentacao dd : d.getDocumentacaos()){
								dd.setDocumentacao(d);
								dd = hibernateFactory.getDocumentacaoDAO().salvarDocumentacao(dd);
								listaDocumentacaoDoc.add(dd);
							}
							d.setDocumentacaos(listaDocumentacaoDoc);
							for (Notificacao n : d.getNotificacaos()){
								n.setDocumentacao(d);
								n = hibernateFactory.getNotificacaoDAO().salvarNotificacao(n);
								listaNotificacao.add(n);
							}
							d.setNotificacaos(listaNotificacao);
							for (OcorrenciaDocumentacao o : d.getOcorrenciaDocumentacaos()){
								o.setDocumentacao(d);
								o = hibernateFactory.getOcorrenciaDocumentacaoDAO().salvarOcorrenciaDocumentacao(o);
								listaOcorrenciaDocumentacao.add(o);
							}
							d.setOcorrenciaDocumentacaos(listaOcorrenciaDocumentacao);
							listaDocumentacao.add(d);
							
						}
						bemImovel.setDocumentacaos(listaDocumentacao);
					}

					if (bemImovel.getEdificacaos() != null) {
						//insere Edificacao
						listaEdificacao = new HashSet<Edificacao>();
						listaDocumentacao = new HashSet<Documentacao>();
						Set<Lote> listaLote  = new HashSet<Lote>();
						Set<Avaliacao> listaAvaliacao = new HashSet<Avaliacao>();
						Set<Ocupacao> listaOcupacao = new HashSet<Ocupacao>();
						for (Edificacao e: bemImovel.getEdificacaos()){
							e = hibernateFactory.getEdificacaoDAO().salvarEdificacao(e);
							for (Documentacao d : e.getDocumentacaos()){
								d.setEdificacao(e);
								d = hibernateFactory.getDocumentacaoDAO().salvarDocumentacao(d);
								listaDocumentacao.add(d);
							}
							e.setDocumentacaos(listaDocumentacao);
							for (Lote l : e.getLotes()){
								l = hibernateFactory.getLoteDAO().salvarLote(l);
								listaLote.add(l);
							}
							e.setLotes(listaLote);
							for (Avaliacao a : e.getAvaliacaos()){
								a.setEdificacao(e);
								a.setTsInclusao(new Date());
								a.setTsAtualizacao(new Date());
								a.setCpfResponsavel(e.getCpfResponsavel());
								a = hibernateFactory.getAvaliacaoDAO().salvarAvaliacao(a);
								listaAvaliacao.add(a);
							}
							e.setAvaliacaos(listaAvaliacao);
							for (Ocupacao o : e.getOcupacaos()){
								o.setEdificacao(e);
								o = hibernateFactory.getOcupacaoDAO().salvarOcupacao(o);
								listaOcupacao.add(o);
							}
							e.setOcupacaos(listaOcupacao);
							listaEdificacao.add(e);
						}
						bemImovel.setEdificacaos(listaEdificacao);
						
					}
					hibernateFactory.getBemImovelDAO().alterar(bemImovel);	
				}
				
			}//fim for
			HibernateUtil.commitTransaction();

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em executarMigracao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em executarMigracao()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao executar a Migração do Bem Imóvel" }, ex, ApplicationException.ICON_ERRO);
		}
		return msgErros.toString();
	}
	// * Métodos do Rafael - Fim *

	public static Collection<ItemComboDTO> listarOrgaosComboByTipoAdm(Integer tipoAdm) throws ApplicationException {
		Collection<Orgao> lista = hibernateFactory.getOrgaoDAO().listarByTipoAdm(tipoAdm);
		Collection<ItemComboDTO> ret = new ArrayList<ItemComboDTO>();
		for (Orgao orgao : lista) {
			ItemComboDTO obj = new ItemComboDTO();
			obj.setCodigo(String.valueOf(orgao.getCodOrgao()));
			obj.setDescricao(orgao.getSigla().concat( " - ").concat( orgao.getDescricao()));
			ret.add(obj);
		}
		return ret;
	}

	/**
	 * Busca um objeto Bem Imovel através de seu código
	 * @param codBemImovel
	 * @return BemImovel
	 * @throws Exception
	 */
	public static BemImovel obterBemImovelParaMigracaoPorCodigo(Integer codBemImovel)
			throws ApplicationException {

		return hibernateFactory.getBemImovelDAO().obterParaMigracaoPorCodigo(codBemImovel);

	}

	/**
	 * Inicio dos métodos para incluir a ocupação no BemImovel 
	 */
	/**
	 * Lista paginada de ocupações por bem imóvel
	 * @param codBemImovel 
	 * @param pag - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarOcupacaoPorBemImovelTerreno(Pagina pag, Integer codBemImovel)
			throws ApplicationException {

		try {
			Collection<OcupacaoOrgaoResponsavelListaDTO> itensLista = hibernateFactory.getOcupacaoDAO().listarPorBemImovelTerreno(codBemImovel);
			pag.setQuantidade(itensLista.size());
			pag.setRegistros(itensLista);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Ocupação Por Bem Imóvel Somente Com Terreno" }, e);
		}

		return pag;
	}
	
	/**
	 * Lista de ocupações por bem imóvel
	 * @param codBemImovel 
	 * @return list
	 * @throws ApplicationException
	 */
	public static Collection<Ocupacao> listarOcupacaoPorBemImovelTerreno(Integer codBemImovel)
			throws ApplicationException {
		Collection<Ocupacao> itensLista = null;
		try {
			itensLista = hibernateFactory.getOcupacaoDAO().listarRelPorBemImovelTerreno(codBemImovel);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Ocupação Por Bem Imóvel Somente Com Terreno"}, e);
		}

		return itensLista;
	}

	/**
	 * Verifica se ocupacao percentual total da edificacao nao ultrapassara
	 * 100%.
	 * 
	 * @param Integer
	 *            codBemImovel - codigo do Bem Imóvel.
	 * @param double ocupacaoPercentual - ocupacao percentual a ser salva.
	 * @param Integer
	 *            codOcupacao - codigo da Ocupacao que esta sendo alterada.
	 * @return Boolean - true se existe classificação, false se classificação
	 *         não existe.
	 * @throws ApplicationException
	 */

	public static boolean verificarPercentualOcupacaoBemImovel(Integer codBemImovel,
			double ocupacaoPercentual, Integer codOcupacao) throws ApplicationException {

		double ocupacaoTotal = 0;

		try {
			BemImovel bemImovel = obterBemImovel(codBemImovel);
			Ocupacao ocupacao = new Ocupacao();
			ocupacao.setBemImovel(bemImovel);

			Collection<Ocupacao> listaOcupacao = hibernateFactory
					.getOcupacaoDAO().listar(ocupacao);
			for (Ocupacao item : listaOcupacao) {
				if (item.getCodOcupacao().intValue() == codOcupacao.intValue()) {
					continue;
				}
				if (item.getOcupacaoPercentual() != null) {
					ocupacaoTotal += item.getOcupacaoPercentual().doubleValue();
				}
			}
			ocupacaoTotal += ocupacaoPercentual;
			if (ocupacaoTotal > 100) {
				return false;
			}
			return true;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "ocupação" }, e);
		}

	}

	/**
	 * Verifica se ocupacao por metro quadrado total da edificacao nao
	 * ultrapassara area total da área.
	 * 
	 * @param Integer
	 *            codBemImovel - codigo do Bem Imovel.
	 * @param double ocupacaoPercentual - ocupacao percentual a ser salva.
	 * @param Integer
	 *            codOcupacao - codigo da Ocupacao que esta sendo alterada.
	 * @return Boolean - true se existe classificação, false se classificação
	 *         não existe.
	 * @throws ApplicationException
	 */

	public static boolean verificarOcupacaoMetroQuadradoBemImovel(Integer codBemImovel,
			double ocupacaoMetroQuadrado, Integer codOcupacao)
			throws ApplicationException {

		double ocupacaoTotal = 0;

		try {
			BemImovel bemImovel = obterBemImovel(codBemImovel);
			Ocupacao ocupacao = new Ocupacao();
			ocupacao.setBemImovel(bemImovel);

			Collection<Ocupacao> listaOcupacao = hibernateFactory
					.getOcupacaoDAO().listar(ocupacao);
			for (Ocupacao item : listaOcupacao) {
				if (item.getCodOcupacao().intValue() == codOcupacao.intValue()) {
					continue;
				}
				if (item.getOcupacaoMetroQuadrado() != null) {
					ocupacaoTotal += item.getOcupacaoMetroQuadrado()
							.doubleValue();
				}
			}
			ocupacaoTotal += ocupacaoMetroQuadrado;
			if (ocupacaoTotal > bemImovel.getAreaTerreno().doubleValue()) {
				return false;
			}
			return true;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "ocupação" }, e);
		}

	}

	public static int obterQtdOcupacaoOrgaoResponsavelPorBemImovel(String codBemImovel, Boolean ativo) throws ApplicationException {
		Integer result;
		try {
			result = hibernateFactory.getOcupacaoDAO().obterQtdPorBemImovel(Integer.valueOf(codBemImovel), ativo);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao obter Qtd de Ocupação do Órgão Responsável Por Bem Imóvel"}, e);
		}
		return result;
	}

	public static int obterQtdEdificacaoPorBemImovel(String codBemImovel) throws ApplicationException {
		Integer result;
		try {
			result = hibernateFactory.getEdificacaoDAO().obterQtdPorBemImovel(Integer.valueOf(codBemImovel));
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao obter Qtd de Edificação Por Bem Imóvel"}, e);
		}
		return result;
	}

	public static void excluirEdificacoesDoBemImovel(Integer codBemImovel) throws ApplicationException {
		try {
			BemImovel bemImovel = hibernateFactory.getBemImovelDAO().obterCompleto(codBemImovel);
			if (bemImovel != null){
				HibernateUtil.currentTransaction();  
				for (Edificacao e: bemImovel.getEdificacaos()){
					for (Ocupacao o : e.getOcupacaos()){
						hibernateFactory.getOcupacaoDAO().excluir(o);
					}
					hibernateFactory.getEdificacaoDAO().excluir(e);
				}
				HibernateUtil.commitTransaction(); 
			}
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Edificações do Bem Imóvel"}, e);
		}
		
	}
	
	public static void inativarOrgaoOcupanteDoBemImovel(Integer codBemImovel) throws ApplicationException {
		try {
			List<Ocupacao> list = (List<Ocupacao>) hibernateFactory.getOcupacaoDAO().listarRelPorBemImovelTerreno(codBemImovel);
			if (list != null && list.size() > 0){
				HibernateUtil.currentTransaction();  
				for (Ocupacao ocupacao : list) {
					ocupacao.setAtivo(Boolean.FALSE);
					hibernateFactory.getOcupacaoDAO().alterar(ocupacao);
				}
				HibernateUtil.commitTransaction(); 
			}
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao inativar o Órgão Ocupante do Bem Imóvel"}, e);
		}
		
	}

	public static void reativarOrgaoOcupanteDoBemImovel(Integer codBemImovel) throws ApplicationException {
		try {
			List<Ocupacao> list = (List<Ocupacao>) hibernateFactory.getOcupacaoDAO().listarRelPorBemImovelTerreno(codBemImovel);
			if (list != null && list.size() > 0){
				HibernateUtil.currentTransaction();  
				for (Ocupacao ocupacao : list) {
					ocupacao.setAtivo(Boolean.TRUE);
					hibernateFactory.getOcupacaoDAO().alterar(ocupacao);
				}
				HibernateUtil.commitTransaction(); 
			}
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao reativar o Órgão Ocupante do Bem Imóvel"}, e);
		}
		
	}

	public static void verificarDuplicidadeOrgaoOcupacaoTerreno(Orgao orgao, BemImovel bemImovel) throws ApplicationException {
		try {
			int result = hibernateFactory.getOcupacaoDAO().verificarDuplicidadeOrgaoOcupacaoTerreno(orgao, bemImovel);
			if (result > 0) {
				throw new ApplicationException("AVISO.47");
			}
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao excluir o Órgão Ocupante do Bem Imóvel"}, e);
		}
	}

	
	/**
	 * Lista pagina de bens imóveis.
	 * Autor: Oksana
	 * Data: 22/06/2011
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarBemImovelSimplificado(Pagina pag, BemImovelPesquisaDTO bemDTO) throws ApplicationException {
		try {
			if (pag.getTotalRegistros() == 0) {
				pag.setTotalRegistros(hibernateFactory.getBemImovelDAO().obterQuantidadeListaSimplificado(pag.getQuantidade(),pag.getPaginaAtual(), bemDTO));
			}
			pag.setRegistros(hibernateFactory.getBemImovelDAO().listarSimplificado(pag.getQuantidade(), pag.getPaginaAtual(), bemDTO));
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Bem Imóvel Simplificado"}, e);
		}
		return pag;
	}

	/**
	 * Lista parametroVistoria.
	 * Autor: Oksana
	 * Data: 22/06/2011
	 * @param parametroVistoriaDTO: ParametroVistoriaDTO
	 * @return Pagina
	 * @throws ApplicationException
	 */
	public static Pagina listarParametroVistoria(Pagina pag, ParametroVistoriaDTO parametroVistoriaDTO) throws ApplicationException {
		try {
			pag.setRegistros(hibernateFactory.getParametroVistoriaDAO().listar(parametroVistoriaDTO));
			pag.setTotalRegistros(pag.getRegistros().size());
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Parâmetro da Vistoria"}, e);
		}
		return pag;
	}
	
	/** INICIO: UC Manter Cargo Assinatura*/
	/**
	 * Lista paginada de CargoAssinatura.
	 * 
	 * @param pag - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarCargoAssinatura(Pagina pag, String descricao, Integer codInstituicao)
			throws ApplicationException {

		CargoAssinatura cargoAssinatura = new CargoAssinatura();
		cargoAssinatura.setDescricao(descricao);
		if(codInstituicao!= null)
		{
			Instituicao instituicao = new Instituicao();
			instituicao.setCodInstituicao(codInstituicao);
			cargoAssinatura.setInstituicao(instituicao);
		}
		Collection<CargoAssinatura> list = null; 
		try {
			if (pag.getTotalRegistros() == 0) {
				list = hibernateFactory.getCargoAssinaturaDAO().listarCargoAssinatura(cargoAssinatura, null);
			} else {
				list = hibernateFactory.getCargoAssinaturaDAO().listarCargoAssinatura(cargoAssinatura, pag);
			}
			pag.setTotalRegistros(list.size());
			pag.setRegistros(list);

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Cargo da Assinatura" }, e);
		}

		return pag;
	}

	/**
	 * Busca um objeto CargoAssinatura através de seu código.
	 * 
	 * @param codCargoAssinatura - código do cargo da assinatura a ser localizado
	 * @return CargoAssinatura
	 * @throws ApplicationException
	 */
	public static CargoAssinatura obterCargoAssinatura(Integer codCargoAssinatura) throws ApplicationException {

		return hibernateFactory.getCargoAssinaturaDAO().obter(codCargoAssinatura);
	}

	/**
	 * Verifica se já existe CargoAssinatura com mesma Descricao
	 * 
	 * @param String - descricaoNova
	 * @param Integer
	 *            codigoAtual - codigo do objeto que esta sendo incluido(passar
	 *            zero se estiver sendo inlcuido)/alterado
	 * @return Boolean - true se existe CargoAssinatura, false se
	 *         CargoAssinatura não existe.
	 * @throws ApplicationException
	 */
	public static Boolean verificarCargoAssinaturaDuplicado(
			String descricaoNova, Integer codigoAtual, Integer  codInstituicao)
			throws ApplicationException {

		String descricaoBanco;
		String descricaoNovaTratada = descricaoNova.trim().toUpperCase();

		try {
			// verifica se não existe com o mesma descrição
			CargoAssinatura ca = new CargoAssinatura();
			Instituicao instituicao = new Instituicao();
			instituicao.setCodInstituicao(codInstituicao);
			ca.setDescricao(descricaoNovaTratada);
			ca.setInstituicao(instituicao);
			Collection<CargoAssinatura> listaCargoAssinatura = hibernateFactory.getCargoAssinaturaDAO().listar(ca);
			for (CargoAssinatura item : listaCargoAssinatura) {
				descricaoBanco = item.getDescricao().trim().toUpperCase();
				if (descricaoBanco.equals(descricaoNovaTratada)) {
					if (codigoAtual == 0){
						return true;
					}else{
						if (!codigoAtual.equals(item.getCodCargoAssinatura())) {
							return true;
						}
					}
				}
			}
			return false;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "cargo da assinatura" }, e);
		}

	}

	/**
	 * Salva objeto CargoAssinatura.
	 * 
	 * @param objeto a ser salvo.
	 * @throws ApplicationException
	 */
	public static void salvarCargoAssinatura(CargoAssinatura cargoAssinatura)
			throws ApplicationException {
		
		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getCargoAssinaturaDAO().salvar(cargoAssinatura);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarCargoAssinatura()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarCargoAssinatura()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Cargo da Assinatura" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Atualiza objeto CargoAssinatura.
	 * 
	 * @param CargoAssinatura - a ser atualizado.
	 * @throws ApplicationException
	 */
	public static void alterarCargoAssinatura(CargoAssinatura cargoAssinatura)
			throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();  
			// verifica se existe integridade referencial com tabela Assinatura 
			CargoAssinatura cargoAssinaturaAux = obterCargoAssinatura(cargoAssinatura.getCodCargoAssinatura());
			if (cargoAssinaturaAux.getListaAssinatura() != null && cargoAssinaturaAux.getListaAssinatura().size() > 0) {
				throw new ApplicationException("AVISO.75", new String[]{"alterada"}, ApplicationException.ICON_AVISO);
			}
			hibernateFactory.getCargoAssinaturaDAO().alterar(cargoAssinatura);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarCargoAssinatura()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;

		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarCargoAssinatura()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Cargo da Assinatura" }, ex,ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Remove objeto CargoAssinatura.
	 * 
	 * @param CargoAssinatura - a ser removido.
	 * @throws ApplicationException
	 */
	public static void excluirCargoAssinatura(int codCargoAssinatura)
			throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  
			// verifica se existe integridade referencial com tabela Assinatura 
			CargoAssinatura cargoAssinatura = obterCargoAssinatura(codCargoAssinatura);
			if (cargoAssinatura.getListaAssinatura() != null && cargoAssinatura.getListaAssinatura().size() > 0) {
				throw new ApplicationException("AVISO.75", new String[]{"excluída"}, ApplicationException.ICON_AVISO);
			}

			hibernateFactory.getCargoAssinaturaDAO().excluir(cargoAssinatura);

			HibernateUtil.commitTransaction();

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirCargoAssinatura()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirCargoAssinatura()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Cargo da Assinatura" }, ex,ApplicationException.ICON_ERRO);
		}
	}

	/** FIM: UC Manter Cargo de Assinatura*/

	/** INICIO: UC Manter Assinatura*/
	/**
	 * Lista paginada de Assinatura.
	 * 
	 * @param pag - objeto de paginação contendo parametros para pesquisa.
	 * @param integer 
	 * @param codUsuario 
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarAssinatura(Pagina pag, Assinatura assinatura)
			throws ApplicationException {

		try {
			if (pag.getTotalRegistros() == 0) {
				pag.setTotalRegistros(hibernateFactory.getAssinaturaDAO().listarByInstituicao(
						null, null, assinatura).size());
			}
			pag.setRegistros(hibernateFactory.getAssinaturaDAO().listarByInstituicao(
					pag.getQuantidade(), pag.getPaginaAtual(), assinatura));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Assinatura" }, e);
		}

		return pag;
	}

	/**
	 * Busca um objeto Assinatura através de seu código.
	 * 
	 * @param codAssinatura - código da assinatura a ser localizado
	 * @return Assinatura
	 * @throws ApplicationException
	 */
	public static Assinatura obterAssinatura(Integer codAssinatura) throws ApplicationException {
		return hibernateFactory.getAssinaturaDAO().obterCompleto(codAssinatura);
	}

	/**
	 * Verifica se já existe Assinatura com o mesmo CPF, Instituição, Órgáo e Cargo
	 * 
	 * @param assinaturaProposta
	 * @return Integer
	 * @throws ApplicationException
	 */
	public static Collection<Assinatura> verificarAssinaturaDuplicada(Assinatura assinaturaProposta, Integer codInstituicao)
			throws ApplicationException {

		try {
			if (codInstituicao != null && codInstituicao > 0) {
				assinaturaProposta.setInstituicao(obterInstituicao(codInstituicao));
			}
			return hibernateFactory.getAssinaturaDAO().listarAssinaturaByInstituicao(assinaturaProposta);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "assinatura" }, e);
		}

	}

	/**
	 * Salva objeto Assinatura.
	 * 
	 * @param objeto a ser salvo.
	 * @throws ApplicationException
	 */
	public static void salvarAssinatura(Assinatura assinatura)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getAssinaturaDAO().salvar(assinatura);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarAssinatura()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarAssinatura()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Assinatura" }, ex, ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Atualiza objeto Assinatura.
	 * 
	 * @param Assinatura - a ser atualizado.
	 * @throws ApplicationException
	 */
	public static void alterarInativarAssinatura(Assinatura assinatura)
			throws ApplicationException {
		try {

			HibernateUtil.currentTransaction();  
			hibernateFactory.getAssinaturaDAO().alterar(assinatura);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarInativarAssinatura()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;

		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarInativarAssinatura()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Assinatura" }, ex,ApplicationException.ICON_ERRO);
		}

	}

	/**
	 * Remove objeto Assinatura.
	 * 
	 * @param Assinatura - a ser removido.
	 * @throws ApplicationException
	 */
	public static void excluirAssinatura(Assinatura assinatura)
			throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			if (assinatura.getListaAssinaturaDoacao() != null && assinatura.getListaAssinaturaDoacao().size() > 0) {
				throw new ApplicationException("AVISO.74", ApplicationException.ICON_AVISO);
			}
			if (assinatura.getListaAssinaturaTransferencia() != null && assinatura.getListaAssinaturaTransferencia().size() > 0) {
				throw new ApplicationException("AVISO.74", ApplicationException.ICON_AVISO);
			}

			hibernateFactory.getAssinaturaDAO().excluir(assinatura);

			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirAssinatura()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirAssinatura()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Assinatura" }, ex,ApplicationException.ICON_ERRO);
		}
	}

	/** FIM: UC Manter Assinatura*/
	
	public static Collection<DenominacaoImovel> listarDenominacaoImovelsExceto(List<Integer> listaCodDenominacaoImovel) throws ApplicationException {
		return hibernateFactory.getDenominacaoImovelDAO().listarExceto(listaCodDenominacaoImovel);
	}
	public static Collection<DenominacaoImovel> listarDenominacaoImovelsContenha(List<Integer> listaCodDenominacaoImovel) throws ApplicationException {
		return hibernateFactory.getDenominacaoImovelDAO().listarContenha(listaCodDenominacaoImovel);
	}
	
	/**
	 * Busca um objeto BemImovel através de seu código.
	 * 
	 * @param codBemImovel
	 *            código do BemImovel a ser localizado
	 * @return BemImovel
	 * @throws Exception
	 */
	public static BemImovel obterBemImovelSimplificado(Integer codBemImovel)
			throws ApplicationException {

		return hibernateFactory.getBemImovelDAO().obterSimplificado(codBemImovel);

	}

	/**
	 * Lista nomes por cargo e órgão da assinatura
	 * @return Collection<ItemComboDTO>
	 * @param codOrgao
	 * @throws ApplicationException
	 */
	public static Collection<ItemComboDTO> listarNomeAssinaturaCombo(Integer codOrgao, Integer codCargo, Integer codInstituicao) throws ApplicationException {
		Assinatura cst = new Assinatura();
		cst.setOrgao(obterOrgao(codOrgao));
		cst.setCargoAssinatura(obterCargoAssinatura(codCargo));
		if (codInstituicao != null && codInstituicao > 0) {
			cst.setInstituicao(obterInstituicao(codInstituicao));
		}
		Collection<Assinatura> lista;
		lista = hibernateFactory.getAssinaturaDAO().listarAssinaturaByInstituicao(cst);
		Collection<ItemComboDTO> ret = new ArrayList<ItemComboDTO>();
		for (Assinatura assinatura : lista) {
			ItemComboDTO obj = new ItemComboDTO();
			obj.setCodigo(String.valueOf(assinatura.getCodAssinatura()));
			obj.setDescricao(assinatura.getNome());
			ret.add(obj);
		}
		return ret;
	}

	/**
	 * Lista nomes por cargo e órgão da assinatura
	 * @return Collection<ItemComboDTO>
	 * @param Orgao
	 * @param Cargo
	 * @throws ApplicationException
	 */
	public static Collection<ItemComboDTO> listarAssinaturasComboByInstituicao(Instituicao instituicao) throws ApplicationException {
		Assinatura cst = new Assinatura();
		cst.setInstituicao(instituicao);
		Collection<Assinatura> lista;

		lista = hibernateFactory.getAssinaturaDAO().listarAssinaturaByInstituicao(cst);
		Collection<ItemComboDTO> ret = new ArrayList<ItemComboDTO>();
		for (Assinatura assinatura : lista) {
			ItemComboDTO obj = new ItemComboDTO();
			obj.setCodigo(String.valueOf(assinatura.getCodAssinatura()));
			obj.setDescricao(assinatura.getCargoAssinatura().getDescricao().concat(" - ").concat(assinatura.getNome()));
			ret.add(obj);
		}
		return ret;
	}

	/**
	 * Obtem o statusTermo
	 * @param codStatus
	 * @return
	 * @throws ApplicationException
	 */
	public static StatusTermo obterStatusTermo(Integer codStatus) throws ApplicationException {
		return hibernateFactory.getStatusTermoDAO().obter(codStatus);
	}

	/**
	 * Lista paginada de Leis de bens imóveis por bem imóvel e tipo de lei
	 * 
	 * @param pag
	 *            - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarLeiBemImovelTipoLei(Pagina pag, LeiBemImovel lei)
			throws ApplicationException {

		try {
			Collection<LeiBemImovel> result = hibernateFactory.getLeiBemImovelDAO().listarPorBemImovelTipo(lei); 
			pag.setRegistros(result);
			pag.setTotalRegistros(result.size());

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Lei do Bem Imóvel Por Tipo Lei" }, e);
		}

		return pag;
	}

	public static BemImovel listarRelatorioBemImovel(ImpressaoBemImovelDTO ibiDTO, Integer codBemImovel) throws ApplicationException {
		return hibernateFactory.getBemImovelDAO().listarRelatorioBemImovel(ibiDTO, codBemImovel);
	}

	/**
	 * Busca um objeto Orgao através de sua sigla.
	 * 
	 * @param sigla
	 *            sigla do órgão a ser localizado
	 * @return Orgao
	 * @throws ApplicationException
	 */
	public static Orgao obterOrgaoBySigla(String sigla) throws ApplicationException {
		return hibernateFactory.getOrgaoDAO().obterOrgaoPorSigla(sigla);
	}

	/**
	 * Realiza a validação dos valores informados do item para Operacoes (doação, transferência...)
	 * @param codBemImovel: Nr do Bem Imóvel
	 * @param tipoOperacao: 1 - Cessão
	 * 						2 - Doação
	 * 						3 - Transferência
	 * @param codigo: código da operação
	 * @return boolean: se a área/percentual solicitado for válido, retorna true, senão false; 
	 * @throws ApplicationException
	 */
	public static boolean validaValoresInformados(Integer codBemImovel, Integer codigo, Integer tipoOperacao) throws ApplicationException {
		
		BemImovel bemImovel = hibernateFactory.getBemImovelDAO().obterCompleto(codBemImovel);
		BigDecimal valorTotal = new BigDecimal(0);
		switch (tipoOperacao) {
		case 1: //Cessão
			Collection<ItemCessaoDeUso> listItemC = hibernateFactory.getItemCessaoDeUsoDAO().listar(codigo);
			for (ItemCessaoDeUso item : listItemC) {
				switch (item.getTipo()) {
					case 1: //Total
						valorTotal = new BigDecimal(0);
						if (bemImovel.getAreaConstruida() != null && bemImovel.getAreaTerreno() != null) {
							valorTotal = bemImovel.getAreaConstruida().add(bemImovel.getAreaTerreno());
						} else if (bemImovel.getAreaConstruida() == null && bemImovel.getAreaTerreno() != null) {
							valorTotal = bemImovel.getAreaTerreno();
						} else if (bemImovel.getAreaConstruida() != null && bemImovel.getAreaTerreno() == null) {
							valorTotal = bemImovel.getAreaConstruida();
						}
						if (valorTotal.compareTo(new BigDecimal(0))== 0) {
							return false;
						}
						if (item.getAreaMetroQuadrado().compareTo(valorTotal) > 0) {
							return false;
						}
					break;
					case 2: // Edificação
						if (item.getEdificacao() != null && item.getEdificacao().getCodEdificacao() > 0) {
							Edificacao edificacaoBI = hibernateFactory.getEdificacaoDAO().obter(item.getEdificacao().getCodEdificacao());
							if (item.getAreaMetroQuadrado().compareTo(edificacaoBI.getAreaConstruida()) > 0) {
								return false;
							}
						} else {
							return false;
						}
					break;
					case 3: //Terreno
						valorTotal = new BigDecimal(0);
						if (bemImovel.getAreaTerreno() != null) {
							valorTotal = bemImovel.getAreaTerreno();
						}
						if (valorTotal.compareTo(new BigDecimal(0))== 0) {
							return false;
						}
						if (item.getAreaMetroQuadrado().compareTo(valorTotal) > 0) {
							return false;
						}
					break;
				}
			}
			break;
		case 2: //Doação
			Collection<ItemDoacao> listItemD = hibernateFactory.getItemDoacaoDAO().listar(codigo);
			for (ItemDoacao item : listItemD) {
				switch (item.getTipo()) {
					case 1: //Total
						valorTotal = new BigDecimal(0);
						if (bemImovel.getAreaConstruida() != null && bemImovel.getAreaTerreno() != null) {
							valorTotal = bemImovel.getAreaConstruida().add(bemImovel.getAreaTerreno());
						} else if (bemImovel.getAreaConstruida() == null && bemImovel.getAreaTerreno() != null) {
							valorTotal = bemImovel.getAreaTerreno();
						} else if (bemImovel.getAreaConstruida() != null && bemImovel.getAreaTerreno() == null) {
							valorTotal = bemImovel.getAreaConstruida();
						}
						if (valorTotal.compareTo(new BigDecimal(0))== 0) {
							return false;
						}
						if (item.getDoacaoMetros().compareTo(valorTotal) > 0) {
							return false;
						}
					break;
					case 2: // Edificação
						if (item.getEdificacao() != null && item.getEdificacao().getCodEdificacao() > 0) {
							Edificacao edificacaoBI = hibernateFactory.getEdificacaoDAO().obter(item.getEdificacao().getCodEdificacao());
							if (item.getDoacaoMetros().compareTo(edificacaoBI.getAreaConstruida()) > 0) {
								return false;
							}
						} else {
							return false;
						}
					break;
					case 3: //Terreno
						valorTotal = new BigDecimal(0);
						if (bemImovel.getAreaTerreno() != null) {
							valorTotal = bemImovel.getAreaTerreno();
						}
						if (valorTotal.compareTo(new BigDecimal(0))== 0) {
							return false;
						}
						if (item.getDoacaoMetros().compareTo(valorTotal) > 0) {
							return false;
						}
					break;
				}
			}
			break;
		case 3: //Transferência
			Collection<ItemTransferencia> listItemT = hibernateFactory.getItemTransferenciaDAO().listar(codigo);
			for (ItemTransferencia item : listItemT) {
				switch (item.getTipo()) {
					case 1: //Total
						valorTotal = new BigDecimal(0);
						if (bemImovel.getAreaConstruida() != null && bemImovel.getAreaTerreno() != null) {
							valorTotal = bemImovel.getAreaConstruida().add(bemImovel.getAreaTerreno());
						} else if (bemImovel.getAreaConstruida() == null && bemImovel.getAreaTerreno() != null) {
							valorTotal = bemImovel.getAreaTerreno();
						} else if (bemImovel.getAreaConstruida() != null && bemImovel.getAreaTerreno() == null) {
							valorTotal = bemImovel.getAreaConstruida();
						}
						if (valorTotal.compareTo(new BigDecimal(0))== 0) {
							return false;
						}
						if (item.getTransferenciaMetros().compareTo(valorTotal) > 0) {
							return false;
						}
					break;
					case 2: // Edificação
						if (item.getEdificacao() != null && item.getEdificacao().getCodEdificacao() > 0) {
							Edificacao edificacaoBI = hibernateFactory.getEdificacaoDAO().obter(item.getEdificacao().getCodEdificacao());
							if (item.getTransferenciaMetros().compareTo(edificacaoBI.getAreaConstruida()) > 0) {
								return false;
							}
						} else {
							return false;
						}
					break;
					case 3: //Terreno
						valorTotal = new BigDecimal(0);
						if (bemImovel.getAreaTerreno() != null) {
							valorTotal = bemImovel.getAreaTerreno();
						}
						if (valorTotal.compareTo(new BigDecimal(0))== 0) {
							return false;
						}
						if (item.getTransferenciaMetros().compareTo(valorTotal) > 0) {
							return false;
						}
					break;
				}
			}
			break;
		}
		
		return true;
	}
	
	
	
	public static String obterDescricaoCessaoDeUso(Integer codBemImovel) throws ApplicationException {
		try {
				String aux = "";
				CessaoDeUso cessaoDeUso = new CessaoDeUso();
				BemImovel b = new BemImovel();
				b.setCodBemImovel(codBemImovel);
				cessaoDeUso.setBemImovel(b);
				List<CessaoDeUso> listaCessaoDeUso = hibernateFactory.getCessaoDeUsoDAO().listarCessaoDeUso(cessaoDeUso);
				for (CessaoDeUso c: listaCessaoDeUso){
					if (!"".equals(aux)){
						aux = aux.concat("<br><br>");
					}
					aux = aux.concat(c.getNumeroTermo().concat(" (")).concat(c.getStatusTermo().getDescricao()).concat(")");
					aux = aux.concat("<br>").concat("Cessionário: ").concat(c.getOrgaoCessionario().getSigla());
				}
				return aux;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao obter descrição da Cessão De Uso" }, e);
		}
	}
	
	public static String obterDescricaoDoacao(Integer codBemImovel) throws ApplicationException {
		try {
				String aux = "";
				Doacao doacao = new Doacao();
				BemImovel b = new BemImovel();
				b.setCodBemImovel(codBemImovel);
				doacao.setBemImovel(b);
				List<Doacao> listaDoacao = hibernateFactory.getDoacaoDAO().listarDoacao(doacao);
				for (Doacao c: listaDoacao){
					if (!"".equals(aux)){
						aux = aux.concat("<br><br>");
					}
					aux = aux.concat(c.getNumeroTermo().concat(" (")).concat(c.getStatusTermo().getDescricao()).concat(")");
					aux = aux.concat("<br>").concat("Órgão: ").concat(c.getOrgaoResponsavel().getSigla());
				}
				return aux;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao obter descrição da Doação" }, e);
		}
	}
	
	public static String obterDescricaoTransferencia(Integer codBemImovel) throws ApplicationException {
		try {
				String aux = "";
				Transferencia transferencia = new Transferencia();
				BemImovel b = new BemImovel();
				b.setCodBemImovel(codBemImovel);
				transferencia.setBemImovel(b);
				List<Transferencia> listaTransferencia = hibernateFactory.getTransferenciaDAO().listarTransferencia(transferencia);
				for (Transferencia c: listaTransferencia){
					if (!"".equals(aux)){
						aux = aux.concat("<br><br>");
					}
					aux = aux.concat(c.getNumeroTermo().concat(" (")).concat(c.getStatusTermo().getDescricao()).concat(")");
					aux = aux.concat("<br>").concat("Órgão: ").concat(c.getOrgaoCessionario().getSigla());
				}
				return aux;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao obter descrição da Transferência" }, e);
		}
	}
	
	
	/**
	 * Verificar se usuario locago está no grupo informado.
	 * @param codUsuarioSentinela:Long
	 * @param request:HttpServletRequest
	 * @return Boolean
	 * @throws ApplicationException
	 */
	public static boolean verificarGrupoUsuarioLogado(HttpServletRequest request, Integer codGrupo) throws ApplicationException{
		try{
			List<GrupoSentinela> listaGrupoSentinela = new ArrayList<GrupoSentinela>();
			listaGrupoSentinela.add(GrupoSentinela.getGrupoSentinela(codGrupo));

			SentinelaParam[] sentinelaParam = SentinelaComunicacao.getInstance(request).getGrupos();
			boolean pertence = false;
			
			if(sentinelaParam != null){
				for (SentinelaParam sentinelaParam2 : sentinelaParam){
					for (GrupoSentinela grupo : listaGrupoSentinela){
						if(sentinelaParam2.getNome().trim().equals(grupo.getDescricao().trim())){
							pertence = true;
						}
					}
				}
			}
			return pertence;
		}catch(Exception e){
			throw new ApplicationException("mensagem.erro.9001", new String[]{CadastroFacade.class.getSimpleName() + ".verificarOperadorOrgao()"}, e, ApplicationException.ICON_ERRO);
		}
	}

	public static Usuario obterUsuarioPorIdSentinela(Long idSentinela) throws ApplicationException {
		return hibernateFactory.getUsuarioDAO().obterPorIdSentinela(idSentinela);
	}
	
	
	/**
	 * Verificar se usuario sentinela está no grupo de operadores dos órgãos e bem imóvel associado.
	 * @param codUsuarioSentinela:Long
	 * @param request:HttpServletRequest
	 * @return Boolean
	 * @throws ApplicationException
	 */
	public static Boolean verificarOperadorOrgaoBemImovel(Integer codBemImovel, Long codUsuarioSentinela, HttpServletRequest request) throws ApplicationException{
		try{
			Boolean pertence = true;

			if (verificarGrupoUsuarioLogado(request, GrupoSentinela.OPE_ORG_ABI.getCodigo())){
				Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(codUsuarioSentinela);
				List<Integer> listaCodOrgao = new ArrayList<Integer>();
				for (UsuarioOrgao o : usuario.getListaUsuarioOrgao()){
					listaCodOrgao.add(o.getOrgao().getCodOrgao());
				}
				BemImovel b = hibernateFactory.getBemImovelDAO().obterParaUsuarioOrgao(codBemImovel, listaCodOrgao);
				if (b == null){
					pertence = false;
				}
			}
			return pertence;
		}catch(Exception e){
			throw new ApplicationException("mensagem.erro.9001", new String[]{CadastroFacade.class.getSimpleName() + ".verificarOperadorOrgaoBemImovel()"}, e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Lista a documentação para preencher o termo de Informação da Transferência
	 * @param codBemImovel
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public static List<Documentacao> listarDocumentacaoSemOcorrencia(Integer codBemImovel) throws ApplicationException, Exception {
		try {
			return hibernateFactory.getDocumentacaoDAO().listarDocumentacaoSemOcorrencia(codBemImovel, null, null, false);
		}catch(Exception e){
			throw new ApplicationException("mensagem.erro.9001", new String[]{CadastroFacade.class.getSimpleName() + ".verificarOperadorOrgaoBemImovel()"}, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Obtem cargo pela descrição
	 * @param descricao
	 * @return
	 * @throws ApplicationException
	 */
	public static CargoAssinatura obterCargoAssinaturaByDescricao(String descricao) throws ApplicationException {

		CargoAssinatura cargoAssinatura = new CargoAssinatura();
		cargoAssinatura.setDescricao(descricao);
		try {
			cargoAssinatura = hibernateFactory.getCargoAssinaturaDAO().listarCargoAssinatura(cargoAssinatura, null).iterator().next();
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Cargo da Assinatura" }, e);
		}
		
		return cargoAssinatura;
	}

	public static Pagina listarGruposByUsuario(Pagina pag, Integer codUsuario) throws ApplicationException {
		try {
			Usuario usuario = obterUsuario(codUsuario);
			
			Collection<UsuarioGrupoSentinela> coll = hibernateFactory.getUsuarioGrupoSentinelaDAO().listar(pag.getQuantidade(), pag.getPaginaAtual(), usuario);
			pag.setTotalRegistros(coll.size());
			pag.setRegistros(Util.listToSet((ArrayList<UsuarioGrupoSentinela>)coll));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Grupos por Usuário" }, e);
		}

		return pag;
	}

	public static Pagina listarOrgaosByUsuario(Pagina pag, Integer codUsuario) throws ApplicationException {
		try {
			Usuario usuario = obterUsuario(codUsuario);
			
			Collection<UsuarioOrgao> coll = hibernateFactory.getUsuarioOrgaoDAO().listar(pag.getQuantidade(), pag.getPaginaAtual(), usuario);
			pag.setTotalRegistros(coll.size());
			pag.setRegistros(Util.listToSet((ArrayList<UsuarioOrgao>)coll));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Órgãos por Usuário" }, e);
		}

		return pag;
	}

	public static Pagina listarUsuario(Pagina pag, Usuario objPesquisa) throws ApplicationException {
		try {
			Collection<Usuario> coll = hibernateFactory.getUsuarioDAO().listar(pag.getQuantidade(), pag.getPaginaAtual(), objPesquisa);
			Collection<UsuarioDTO> result = new ArrayList<UsuarioDTO>();
			
			String grupo;
			String orgao;
			for (Usuario us : coll) {
				grupo = "";
				orgao = "";
				UsuarioDTO aux = new UsuarioDTO();
				aux.setCodUsuario(us.getCodUsuario());
				aux.setUsuario(CPF.formataCPF(us.getCpf()).concat("/").concat(us.getNome()));
				aux.setSituacao(us.getSituacaoDescricao());

				for (UsuarioGrupoSentinela gp : us.getListaUsuarioGrupoSentinela()) {
					grupo = grupo.concat(gp.getGrupoSentinela().getDescricaoGrupo()).concat(", ");
				}
				if (grupo.trim().length() > 1) {
					grupo = grupo.substring(0, grupo.length() -2);
				}
				aux.setGrupo(grupo);
				
				for (UsuarioOrgao org : us.getListaUsuarioOrgao()) {
					orgao = orgao.concat(org.getOrgao().getSigla().concat("/").concat(org.getOrgao().getDescricao())).concat(", ");
				}
				if (orgao.trim().length() > 1) {
					orgao = orgao.substring(0, orgao.length() -2);
				}
				aux.setOrgao(orgao);
				if (us.getInstituicao()!= null){
					aux.setInstituicao(us.getInstituicao().getSiglaDescricao());
				}
				
				result.add(aux);
			}
			
			pag.setTotalRegistros(result.size());
			pag.setRegistros(result);

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Usuário" }, e);
		}

		return pag;
	}

	public static Usuario obterUsuario(Integer codUsuario) throws ApplicationException {
		return hibernateFactory.getUsuarioDAO().obterCompleto(codUsuario);
	}

	public static Usuario salvarUsuario(Usuario usuario, HttpServletRequest request) throws ApplicationException {
		try {
			
			
			HibernateUtil.currentTransaction();
			usuario = manterUsuarioSentinela(usuario, request);
			
			if (usuario.getListaUsuarioGrupoSentinela() != null){
				for (UsuarioGrupoSentinela u: usuario.getListaUsuarioGrupoSentinela()){
					u.setUsuario(usuario);
				}
			}
			if (usuario.getListaUsuarioOrgao()!= null){
				for (UsuarioOrgao uo: usuario.getListaUsuarioOrgao()){
					uo.setUsuario(usuario);
				}
			}
			
			if (usuario.getCodUsuario() == null){
				usuario = hibernateFactory.getUsuarioDAO().salvarUsuario(usuario);
			}else{
				hibernateFactory.getUsuarioGrupoSentinelaDAO().excluirPorUsuario(usuario);
				hibernateFactory.getUsuarioOrgaoDAO().excluirPorUsuario(usuario);
				hibernateFactory.getUsuarioDAO().alterar(usuario);
			}
			
			//atualizar grupos de acesso cadastrados no sentinela
			//desvinculando de todos os grupos pre-existentes referentes ao sistema
			SentinelaAdmInterface sentinelaAdmInterface = SentinelaAdministracao.getInstance(request);
			SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
			SentinelaParam[] grupos = sentinelaInterface.getGruposByUsuario(usuario.getIdSentinela());

			if (grupos != null && grupos.length > 0) {
				for (SentinelaParam sentinelaParam : grupos) {
					sentinelaAdmInterface.desvincularUsuarioGrupo(usuario.getIdSentinela(), sentinelaParam.getCodigo());
				}
			}
			
			grupos = sentinelaInterface.getGeralGrupos();
			
			//vinculando os grupos selecionados
			for (UsuarioGrupoSentinela item : usuario.getListaUsuarioGrupoSentinela()) {
				for (SentinelaParam sentinelaParam : grupos) {
					if (sentinelaParam.getNome().equalsIgnoreCase(item.getGrupoSentinela().getDescricaoSentinela())) {
						sentinelaAdmInterface.sistemaVincularUsuarioGrupo(usuario.getIdSentinela(), sentinelaParam.getCodigo());
						break;
					}
				}
			}

			HibernateUtil.commitTransaction();
			return usuario;
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarUsuario()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarUsuario()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao incluir Usuário" }, e, ApplicationException.ICON_ERRO);
		}
	}

	private static Usuario manterUsuarioSentinela(Usuario usuario, HttpServletRequest request) throws ApplicationException {
		SentinelaAdmInterface sentinelaAdmInterface = SentinelaAdministracao.getInstance(request);
		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
		if (usuario.getIdSentinela() != null && usuario.getIdSentinela() > 0) {
			try {
				sentinelaAdmInterface.alterarUsuario(usuario.getIdSentinela(), usuario.getNome(), usuario.getCpf(), null, null, usuario.getMail(), null);
			} catch (SentinelaException e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao alterar Usuário no Sentinela!"}, ApplicationException.ICON_AVISO);
			}
		} else {
			try {
				sentinelaAdmInterface.incluirUsuario(usuario.getLoginSistema(), usuario.getNome(), null, null, usuario.getMail(), usuario.getCpf(), null, null, true);
			} catch (SentinelaException e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao incluir Usuário no Sentinela!"}, ApplicationException.ICON_AVISO);
			}
		}
		SentinelaParam usuarioSentinela = sentinelaInterface.getUsuarioByCPF(usuario.getCpf());
		usuario.setIdSentinela(usuarioSentinela.getCodigo());
		return usuario;
		
	}

	public static void excluirUsuario(Usuario usuario, HttpServletRequest request) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();  
			SentinelaAdmInterface sentinelaAdmInterface = SentinelaAdministracao.getInstance(request);
			SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
			SentinelaParam[] grupos = sentinelaInterface.getGruposByUsuario(usuario.getIdSentinela());

			//desvincula dos grupos no sentinela
			for (SentinelaParam sentinelaParam : grupos) {
				sentinelaAdmInterface.desvincularUsuarioGrupo(usuario.getIdSentinela(), sentinelaParam.getCodigo());
			}
			//exclui da tabela de usuarios
			hibernateFactory.getUsuarioGrupoSentinelaDAO().excluirPorUsuario(usuario);
			hibernateFactory.getUsuarioOrgaoDAO().excluirPorUsuario(usuario);
			hibernateFactory.getUsuarioDAO().excluir(usuario);
			
			HibernateUtil.commitTransaction(); 
		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirUsuario()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirUsuario()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Usuário" }, ex, ApplicationException.ICON_ERRO);
		}
	}

	public static Collection<UsuarioOrgao> listarOrgaosByUsuario(Usuario usuario) throws ApplicationException {
		return hibernateFactory.getUsuarioOrgaoDAO().listar(null, null, usuario);
	}

	public static void salvarUsuarioOrgao(UsuarioOrgao usuarioOrgao) throws ApplicationException {
		try {
			UsuarioOrgao aux = obterUsuarioOrgao(usuarioOrgao.getUsuario().getCodUsuario(), usuarioOrgao.getOrgao().getCodOrgao());
			HibernateUtil.currentTransaction();
			if (aux == null){
				hibernateFactory.getUsuarioOrgaoDAO().salvar(usuarioOrgao);
			}else{
				hibernateFactory.getUsuarioOrgaoDAO().alterar(usuarioOrgao);
			}
			HibernateUtil.commitTransaction();
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarUsuarioOrgao()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarUsuarioOrgao()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao incluir Órgão do Usuário" }, e, ApplicationException.ICON_ERRO);
		}
	}

	public static UsuarioOrgao obterUsuarioOrgao(Integer codUsuario, Integer codOrgao) throws ApplicationException {
		UsuarioOrgao aux = new UsuarioOrgao();
		aux.setOrgao(obterOrgao(codOrgao));
		aux.setUsuario(obterUsuario(codUsuario));
		return hibernateFactory.getUsuarioOrgaoDAO().obter(aux);
	}

	public static void excluirUsuarioOrgao(UsuarioOrgao usuarioOrgao) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			hibernateFactory.getUsuarioOrgaoDAO().excluir(usuarioOrgao);
			HibernateUtil.commitTransaction();
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirUsuarioOrgao()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirUsuarioOrgao()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao excluir Órgão do Usuário" }, e, ApplicationException.ICON_ERRO);
		}
	}

	public static Collection<UsuarioGrupoSentinela> listarGruposByUsuario(Usuario usuario) throws ApplicationException {
		return hibernateFactory.getUsuarioGrupoSentinelaDAO().listar(null, null, usuario);
	}

	public static void salvarUsuarioGrupoSentinela(UsuarioGrupoSentinela grupo, HttpServletRequest request, boolean usuarioExistenteSentinela) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			UsuarioGrupoSentinela aux = obterUsuarioGrupo(grupo.getUsuario().getCodUsuario(), grupo.getGrupoSentinela().getCodGrupoSentinela());
			if (aux == null){
				hibernateFactory.getUsuarioGrupoSentinelaDAO().salvar(grupo);
			}
			SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
			SentinelaParam[] grupos = sentinelaInterface.getGruposByUsuario(grupo.getUsuario().getIdSentinela());
			if (usuarioExistenteSentinela) {
				gov.pr.celepar.abi.pojo.GrupoSentinela gp;
				UsuarioGrupoSentinela grupoSent;
				for (SentinelaParam sentinelaParam : grupos) {
					gp = obterGrupoSentinelaByDescSentinela(sentinelaParam.getNome());
					aux = obterUsuarioGrupo(grupo.getUsuario().getCodUsuario(), gp.getCodGrupoSentinela());
					if (aux == null){
						grupoSent = new UsuarioGrupoSentinela();
						grupoSent.setUsuario(grupo.getUsuario());
						grupoSent.setGrupoSentinela(gp);
						hibernateFactory.getUsuarioGrupoSentinelaDAO().salvar(grupoSent);
					}
				}
			}
			boolean vincular = true;
			for (SentinelaParam sentinelaParam : grupos) {
				if (sentinelaParam.getNome().equalsIgnoreCase(grupo.getGrupoSentinela().getDescricaoSentinela())) {
					vincular = false;
				}
			}
			if (vincular) {
				grupos = sentinelaInterface.getGeralGrupos();
				long codGrupoSentinela = 0;
				for (SentinelaParam sentinelaParam : grupos) {
					if (sentinelaParam.getNome().equalsIgnoreCase(grupo.getGrupoSentinela().getDescricaoSentinela())) {
						codGrupoSentinela = sentinelaParam.getCodigo();
					}
				}
				SentinelaAdmInterface sentinelaAdmInterface = SentinelaAdministracao.getInstance(request);
				sentinelaAdmInterface.vincularUsuarioGrupo(grupo.getUsuario().getIdSentinela(), codGrupoSentinela);
			}
			HibernateUtil.commitTransaction();
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarUsuarioGrupoSentinela()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarUsuarioGrupoSentinela()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao incluir Grupo Sentinela do Usuário" }, e, ApplicationException.ICON_ERRO);
		}
	}

	public static UsuarioGrupoSentinela obterUsuarioGrupo(Integer codUsuario, Integer codGrupo) throws ApplicationException {
		UsuarioGrupoSentinela aux = new UsuarioGrupoSentinela();
		aux.setGrupoSentinela(new gov.pr.celepar.abi.pojo.GrupoSentinela());
		aux.getGrupoSentinela().setCodGrupoSentinela(codGrupo);
		aux.setUsuario(obterUsuario(codUsuario));
		return hibernateFactory.getUsuarioGrupoSentinelaDAO().obter(aux);
	}

	public static void excluirUsuarioGrupoSentinela(UsuarioGrupoSentinela grupo, HttpServletRequest request, boolean excluirOrgaos, Set<UsuarioOrgao> listaOrgaos) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			
			if (excluirOrgaos && listaOrgaos != null && listaOrgaos.size() > 0) {
				for (UsuarioOrgao item : listaOrgaos) {
					hibernateFactory.getUsuarioOrgaoDAO().excluir(item);
				}
			}
			
			hibernateFactory.getUsuarioGrupoSentinelaDAO().excluir(grupo);

			SentinelaAdmInterface sentinelaAdmInterface = SentinelaAdministracao.getInstance(request);
			SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
			SentinelaParam[] grupos = sentinelaInterface.getGeralGrupos();
			grupo.setGrupoSentinela(obterGrupoSentinelaByCodigo(grupo.getGrupoSentinela().getCodGrupoSentinela()));
			long codGrupoSentinela = 0;
			for (SentinelaParam sentinelaParam : grupos) {
				if (sentinelaParam.getNome().equalsIgnoreCase(grupo.getGrupoSentinela().getDescricaoSentinela())) {
					codGrupoSentinela = sentinelaParam.getCodigo();
					break;
				}
			}
			sentinelaAdmInterface.desvincularUsuarioGrupo(grupo.getUsuario().getIdSentinela(), codGrupoSentinela);
			
			HibernateUtil.commitTransaction();
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirUsuarioGrupoSentinela()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirUsuarioGrupoSentinela()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao excluir Grupo Sentinela do Usuário" }, e, ApplicationException.ICON_ERRO);
		}
	}

	public static Usuario obterUsuarioByCPF(String cpf) throws ApplicationException {
		return hibernateFactory.getUsuarioDAO().obterByCPF(cpf);
	}
	
	public static Collection<ItemComboDTO> listarGrupoSentinelaCombo(Integer codUsuario) throws ApplicationException {
		Collection<ItemComboDTO> ret = new ArrayList<ItemComboDTO>();
		Collection<gov.pr.celepar.abi.pojo.GrupoSentinela> lista = null;
		if (codUsuario == null || codUsuario.equals(Integer.valueOf(0))) {
			lista = hibernateFactory.getGrupoSentinelaDAO().listar(new gov.pr.celepar.abi.pojo.GrupoSentinela(),new String[] { "descricaoSentinela", "asc" });
			for (gov.pr.celepar.abi.pojo.GrupoSentinela gp : lista) {
				ItemComboDTO obj = new ItemComboDTO();
				obj.setCodigo(gp.getDescricaoSentinela());
				obj.setDescricao(gp.getDescricaoGrupo());
				ret.add(obj);
			}
		} else {
			lista = hibernateFactory.getGrupoSentinelaDAO().listarByUsuario(codUsuario);
			for (gov.pr.celepar.abi.pojo.GrupoSentinela gp : lista) {
				ItemComboDTO obj = new ItemComboDTO();
				obj.setCodigo(gp.getDescricaoSentinela());
				obj.setDescricao(gp.getDescricaoGrupo());
				ret.add(obj);
			}
		}
		return ret;
	}

	public static Collection<ItemComboDTO> listarOrgaosComboByTipoAdmUsuario(Integer tipoAdm, Integer codUsuario) throws ApplicationException {
		Collection<ItemComboDTO> ret = new ArrayList<ItemComboDTO>();
		if (codUsuario == null || codUsuario.equals(Integer.valueOf(0))) {
			Collection<Orgao> lista = hibernateFactory.getOrgaoDAO().listarByTipoAdm(tipoAdm);
			for (Orgao orgao : lista) {
				ItemComboDTO obj = new ItemComboDTO();
				obj.setCodigo(String.valueOf(orgao.getCodOrgao()));
				obj.setDescricao(orgao.getSigla().concat( " - ").concat( orgao.getDescricao()));
				ret.add(obj);
			}
		} else {
			Collection<Orgao> lista = hibernateFactory.getOrgaoDAO().listarByTipoAdmUsuario(tipoAdm, codUsuario);
			for (Orgao orgao : lista) {
				ItemComboDTO obj = new ItemComboDTO();
				obj.setCodigo(String.valueOf(orgao.getCodOrgao()));
				obj.setDescricao(orgao.getSigla().concat( " - ").concat( orgao.getDescricao()));
				ret.add(obj);
			}
		}
		return ret;
	}

	public static gov.pr.celepar.abi.pojo.GrupoSentinela obterGrupoSentinelaByDescSentinela(String grupo) throws ApplicationException {
		return hibernateFactory.getGrupoSentinelaDAO().obterByDescSentinela(grupo);
	}

	public static gov.pr.celepar.abi.pojo.GrupoSentinela obterGrupoSentinelaByCodigo(Integer codigo) throws ApplicationException {
		return hibernateFactory.getGrupoSentinelaDAO().obter(codigo);
	}

	/**
	 * Verificar se existee bem imóvel.
	 * @param codBemImovel
	 * @param codInstituicao 
	 * @return Boolean
	 * @throws ApplicationException
	 */
	public static Boolean existeBemImovel(Integer nrBemImovel, Integer codInstituicao) throws ApplicationException{
		try{
			Boolean existe = false;
			BemImovel b = hibernateFactory.getBemImovelDAO().obterExibirPorInstituicao(nrBemImovel, codInstituicao);
			if (b != null){
				existe = true;
			}
			
			return existe;
		}catch(Exception e){
			throw new ApplicationException("mensagem.erro.9001", new String[]{CadastroFacade.class.getSimpleName() + ".existeBemImovel()"}, e, ApplicationException.ICON_ERRO);
		}
	}

	public static void excluirGruposUsuarioInativo(Usuario usuario, HttpServletRequest request) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			SentinelaAdmInterface sentinelaAdmInterface = SentinelaAdministracao.getInstance(request);
			SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
			SentinelaParam[] grupos = sentinelaInterface.getGruposByUsuario(usuario.getIdSentinela());

			for (SentinelaParam sentinelaParam : grupos) {
				sentinelaAdmInterface.desvincularUsuarioGrupo(usuario.getIdSentinela(), sentinelaParam.getCodigo());
			}
			
			hibernateFactory.getUsuarioGrupoSentinelaDAO().excluirPorUsuario(usuario);
			
			HibernateUtil.commitTransaction();
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirGruposUsuarioInativo()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirGruposUsuarioInativo()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao excluir Grupos  do Usuário Inativo" }, 
					e, ApplicationException.ICON_ERRO);
		}

	}
	
	public static Collection<Instituicao> listarInstituicao() throws ApplicationException {
		return hibernateFactory.getInstituicaoDAO().listar(null,new String[] { "nome", "asc" });
	}
	
	/**
	 * Verifica se já existe Instituicao com mesmo nome.
	 * 
	 * @return Boolean - true se existe Instituicao, false se Instituicao não
	 *         existe.
	 * @throws ApplicationException
	 */

	public static Boolean verificaInstituicaoDuplicado(Integer codInstituicao,
			Instituicao instituicao) throws ApplicationException {

		try {
			Instituicao pesq = new Instituicao();
			pesq.setSigla(instituicao.getSigla());
			
			boolean achouSigla = hibernateFactory.getInstituicaoDAO().verificarExistencia(pesq).booleanValue();
			if (codInstituicao == null && achouSigla) {
				throw new ApplicationException("AVISO.98", new String[] { instituicao.getSigla() });
			}
			pesq.setSigla(null);
			pesq.setNome(instituicao.getNome());
			boolean achouNome = hibernateFactory.getInstituicaoDAO().verificarExistencia(pesq).booleanValue();
			if (codInstituicao == null && achouNome) {
				throw new ApplicationException("AVISO.98", new String[] { instituicao.getNome() });
			}
			if (codInstituicao != null){
				//alteração de instituição
				if (!achouSigla && !achouNome){
					//nao existe nenhuma com essa sigla ou com esse nome.
					return Boolean.FALSE;
				}else{
					//existe com a silga ou com o nome, verificar se é o mesmo objeto que quero alterar
					if (achouNome){
						Collection<Instituicao> listaBanco = hibernateFactory.getInstituicaoDAO().listar(pesq);
						for (Instituicao aux:listaBanco){
							if(!aux.getCodInstituicao().equals(codInstituicao) && aux.getNome().equals(pesq.getNome())){
								throw new ApplicationException("AVISO.98", new String[] { instituicao.getNome() });
							}
						}
					}
					if (achouSigla){
						pesq.setNome(null);
						pesq.setSigla(instituicao.getSigla());
						Collection<Instituicao> listaBanco = hibernateFactory.getInstituicaoDAO().listar(pesq);
						for (Instituicao aux:listaBanco){
							if(!aux.getCodInstituicao().equals(codInstituicao) && aux.getSigla().equals(pesq.getSigla())){
								throw new ApplicationException("AVISO.98", new String[] { instituicao.getSigla() });
							}
						}
					}
				}
			}
				
			return Boolean.FALSE;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "Instituição" }, e);
		}
	}
	
	/**
	 * Salva objeto Instituição.
	 * 
	 * @param orgao
	 *            a ser salvo.
	 * @throws ApplicationException
	 */
	public static void salvarInstituicao(Instituicao instituicao, byte[] anexo) throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getInstituicaoDAO().salvar(instituicao);
			//salvar a logo na pasta data/abi/logo com o nome = codInstituicao
			ArquivoDAO arquivoDao = new ArquivoServidorDAO();
			StringBuffer nomeLogo = new StringBuffer().append(instituicao.getCodInstituicao()).append(instituicao.getLogoInstituicao());
			
			arquivoDao.uploadLogoInstituicao(nomeLogo.toString(), anexo);
		
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarInstituicao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarInstituicao()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Instituição" }, ex,ApplicationException.ICON_ERRO);
		}

	}

	public static Instituicao obterInstituicao(Integer codInstituicao) throws ApplicationException {

		return hibernateFactory.getInstituicaoDAO().obter(codInstituicao);
	}
	
	/**
	 * Atualiza objeto Instituicao.
	 * 
	 * @param instituicao
	 *            a ser atualizado.
	 * @param formFile 
	 * @throws ApplicationException
	 */
	public static void alterarInstituicao(Instituicao instituicao, byte[] formFile) throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();  
			hibernateFactory.getInstituicaoDAO().alterar(instituicao);
			
			ArquivoDAO arquivoDao = new ArquivoServidorDAO();
			StringBuffer nomeLogo = new StringBuffer().append(instituicao.getCodInstituicao()).append(instituicao.getLogoInstituicao());
			
			
			arquivoDao.excluirLogoInstituicao(instituicao.getCodInstituicao().toString());
			arquivoDao.uploadLogoInstituicao(nomeLogo.toString(), formFile);
			
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarInstituicao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;

		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarInstituicao()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Instituição" }, ex, ApplicationException.ICON_ERRO);
		}

	}
	
	
	/**
	 * Remove objeto Instituicao.
	 * 
	 * @param codigo
	 *            do Cartorio a ser removido.
	 * @throws ApplicationException
	 */
	public static void excluirInstituicao(Integer codInstituicao)
			throws ApplicationException {

		try {

			HibernateUtil.currentTransaction();  

			// verifica se existe integridade referencial
			Instituicao instituicao = hibernateFactory.getInstituicaoDAO().obter(codInstituicao);
			if (instituicao.getListaBemImovel() != null	&& instituicao.getListaBemImovel().size() > 0) {
				throw new ApplicationException("AVISO.99",new String[]{"Bens Imóveis"} ,ApplicationException.ICON_AVISO);
			}
			if (instituicao.getListaCargoAssinatura() != null	&& instituicao.getListaCargoAssinatura().size() > 0) {
				throw new ApplicationException("AVISO.99" ,new String[]{"Assinaturas"},ApplicationException.ICON_AVISO);
			}
			if (instituicao.getListaUsuario() != null	&& instituicao.getListaCargoAssinatura().size() > 0) {
				throw new ApplicationException("AVISO.99",new String[]{"Usuários"}, ApplicationException.ICON_AVISO);
			}
			hibernateFactory.getInstituicaoDAO().excluir(instituicao);
			ArquivoDAO arquivoDao = new ArquivoServidorDAO();
			arquivoDao.excluirLogoInstituicao(instituicao.getCodInstituicao().toString());
			
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirInstituicao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirInstituicao()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Instituição" }, ex, ApplicationException.ICON_ERRO);
		}

	}
	
	/**
	 * Lista de Orgaos por tipo de Administracao e Instituicao vinculada ao usuario logado.<br>
 	 * @param tipoAdm Integer
	 * @param codUsuarioSentinela Long
	 * @return Collection<ItemComboDTO>
	 * @throws ApplicationException
	 */
	
	public static Collection<ItemComboDTO> listarOrgaosComboPorTipoAdmEUsuarioSentinela(Integer tipoAdm, Long codUsuarioSentinela) throws ApplicationException {
		Collection<ItemComboDTO> lista = hibernateFactory.getOrgaoDAO().listarPorTipoAdmEUsuarioSentinela(tipoAdm, codUsuarioSentinela);

		return lista;
	}
	
	
	/**
	 * Lista de Orgaos por tipo de Administracao e Instituicao selecionada.<br>
 	 * @param tipoAdm Integer
	 * @param codInstituicao Integer
	 * @return Collection<ItemComboDTO>
	 * @throws ApplicationException
	 */
	public static Collection<ItemComboDTO> listarOrgaosComboPorTipoAdmECodInstituicao(Integer tipoAdm, Integer codInstituicao) throws ApplicationException {
		Collection<ItemComboDTO> lista = hibernateFactory.getOrgaoDAO().listarPorTipoAdmECodInstituicao(tipoAdm, codInstituicao);

		return lista;
	}

	public static Instituicao obterInstituicaoUsuario(Long codUsuarioSentinela) throws ApplicationException {
		return hibernateFactory.getInstituicaoDAO().obterByCodUsuarioSentinela(codUsuarioSentinela);
	}

	public static Collection listarComboInstituicao() throws ApplicationException {
		Collection<Instituicao> result = new ArrayList<Instituicao>();
		Collection<Instituicao> list = listarInstituicao();
		if (list != null && list.size() > 1) {
			Instituicao obj = new Instituicao();
			obj.setCodInstituicao(Integer.valueOf(0));
			obj.setSigla(null);
			obj.setNome("- Selecione -");
			result.add(obj);
		}
		result.addAll(list);
		return Util.htmlEncodeCollection(result);
	}

	public static Collection<ItemComboDTO> listarComboCargoAssinaturaByInstituicao(Integer codOrgao, Integer codInstituicao) throws ApplicationException {
		Collection<CargoAssinatura> lista = hibernateFactory.getCargoAssinaturaDAO().listarCargoAssinaturaByOrgaoInstituicao(codOrgao, codInstituicao);
		Collection<ItemComboDTO> ret = new ArrayList<ItemComboDTO>();
		for (CargoAssinatura cargo : lista) {
			ItemComboDTO obj = new ItemComboDTO();
			obj.setCodigo(String.valueOf(cargo.getCodCargoAssinatura()));
			obj.setDescricao(cargo.getDescricao());
			ret.add(obj);
		}
		return ret;
	}

	public static Collection<ItemComboDTO> listarCargoAssinaturaCombo(CargoAssinatura cargo) throws ApplicationException {
		Collection<CargoAssinatura> lista = hibernateFactory.getCargoAssinaturaDAO().listarCargoAssinaturaByInstituicao(cargo);
		Collection<ItemComboDTO> ret = new ArrayList<ItemComboDTO>();
		for (CargoAssinatura cargoAssinatura : lista) {
			ItemComboDTO obj = new ItemComboDTO();
			obj.setCodigo(String.valueOf(cargoAssinatura.getCodCargoAssinatura()));
			obj.setDescricao(cargoAssinatura.getDescricao());
			ret.add(obj);
		}
		return ret;
	}

	public static Collection<ItemComboDTO> listarOrgaosAssinaturaComboPorTipoAdmEInstituicao(int tipoAdm, Integer codInstituicao) throws ApplicationException {
		Collection<ItemComboDTO> lista = hibernateFactory.getOrgaoDAO().listarParaAssinaturaPorTipoAdmECodInstituicao(tipoAdm, codInstituicao);
		return lista;
	}

	/**
	 * 
	 * @param codBemImovel
	 * @return BemImovel
	 * @throws Exception
	 */
	public static BemImovel obterBemImovelInstituicaoExibir(Integer nrBemImovel, Integer codInstituicao, Usuario usuario) throws ApplicationException {
		if (codInstituicao.equals(0)){
			if (usuario.getInstituicao() != null){
				codInstituicao = usuario.getInstituicao().getCodInstituicao();
			}
		}
		return hibernateFactory.getBemImovelDAO().obterExibirPorInstituicao(nrBemImovel, codInstituicao);

	}
	
	/**
	 * Lista paginada de Leis de bens imóveis por bem imóvel e exceto pelo tipo de lei informado
	 * 
	 * @param pag - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarLeiBemImovelExcetoTipoLei(Pagina pag, LeiBemImovel lei)
			throws ApplicationException {

		try {
			Collection<LeiBemImovel> result = hibernateFactory.getLeiBemImovelDAO().listarPorExcetoTipoLei(lei); 
			pag.setRegistros(result);
			pag.setTotalRegistros(result.size());

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Lei Bem Imovel Exceto pelo Tipo Lei" }, e);
		}

		return pag;
	}

	public static LeiBemImovel obterLeiBemImovelPorNumero(Long numeroLei) throws ApplicationException {
		return hibernateFactory.getLeiBemImovelDAO().obterByNumero(numeroLei);
	}

}
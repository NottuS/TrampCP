package gov.pr.celepar.abi.facade;

import gov.pr.celepar.abi.action.RelatorioIReportAction;
import gov.pr.celepar.abi.dao.factory.DAOFactory;
import gov.pr.celepar.abi.dto.AgendaDTO;
import gov.pr.celepar.abi.dto.AssinaturaDTO;
import gov.pr.celepar.abi.dto.BemImovelVistoriaDTO;
import gov.pr.celepar.abi.dto.CessaoDeUsoDTO;
import gov.pr.celepar.abi.dto.DoacaoDTO;
import gov.pr.celepar.abi.dto.DocInformacaoDTO;
import gov.pr.celepar.abi.dto.ItemCessaoDeUsoDTO;
import gov.pr.celepar.abi.dto.ItemComboDTO;
import gov.pr.celepar.abi.dto.ItemDTO;
import gov.pr.celepar.abi.dto.ItemDoacaoDTO;
import gov.pr.celepar.abi.dto.ItemTransferenciaDTO;
import gov.pr.celepar.abi.dto.NotificacaoDTO;
import gov.pr.celepar.abi.dto.TransferenciaDTO;
import gov.pr.celepar.abi.dto.VistoriaDTO;
import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.enumeration.SituacaoVistoria;
import gov.pr.celepar.abi.pojo.AnexoMail;
import gov.pr.celepar.abi.pojo.Assinatura;
import gov.pr.celepar.abi.pojo.AssinaturaCessaoDeUso;
import gov.pr.celepar.abi.pojo.AssinaturaDoacao;
import gov.pr.celepar.abi.pojo.AssinaturaDocTransferencia;
import gov.pr.celepar.abi.pojo.AssinaturaTransferencia;
import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.CessaoDeUso;
import gov.pr.celepar.abi.pojo.Doacao;
import gov.pr.celepar.abi.pojo.Edificacao;
import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.abi.pojo.ItemCessaoDeUso;
import gov.pr.celepar.abi.pojo.ItemDoacao;
import gov.pr.celepar.abi.pojo.ItemTransferencia;
import gov.pr.celepar.abi.pojo.ItemVistoria;
import gov.pr.celepar.abi.pojo.ItemVistoriaDominio;
import gov.pr.celepar.abi.pojo.LeiBemImovel;
import gov.pr.celepar.abi.pojo.Notificacao;
import gov.pr.celepar.abi.pojo.Orgao;
import gov.pr.celepar.abi.pojo.ParametroAgenda;
import gov.pr.celepar.abi.pojo.ParametroAgendaEmail;
import gov.pr.celepar.abi.pojo.ParametroVistoria;
import gov.pr.celepar.abi.pojo.ParametroVistoriaDominio;
import gov.pr.celepar.abi.pojo.StatusTermo;
import gov.pr.celepar.abi.pojo.StatusVistoria;
import gov.pr.celepar.abi.pojo.Transferencia;
import gov.pr.celepar.abi.pojo.Usuario;
import gov.pr.celepar.abi.pojo.UsuarioOrgao;
import gov.pr.celepar.abi.pojo.Vistoria;
import gov.pr.celepar.abi.pojo.Vistoriador;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Log;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.mensagem.Mensagem;
import gov.pr.celepar.framework.util.Data;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.framework.util.StringUtil;
import gov.pr.celepar.framework.util.Valores;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaInterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Facade responsavel pelos metodos do GPI - Modulo 2.<br>
 */
public class OperacaoFacade extends SuperFacade {
	
	private static Logger log4j = Logger.getLogger(OperacaoFacade.class);
	private static DAOFactory hibernateFactory = DAOFactory.getDAOFactory(DAOFactory.HIBERNATE);
	
	/**
	 * Obter lista de Vistoria paginado.<br>
	 * @author tatianapires
	 * @since  28/06/2011
	 * @param  qtdePagina : Integer
	 * @param  numPagina : Integer
	 * @param  totalRegistros : Integer
	 * @return Pagina
	 * @throws ApplicationException
	 */
	public static Pagina listarVistoriaBemImovelPaginado(Integer codBemImovel, Date dataInicial, Date dataFinal, Integer situacaoImovel, Integer qtdePagina, Integer numPagina, Integer totalRegistros, Integer indOperadorOrgao, Usuario usuario, Integer codInstituicao, HttpServletRequest request) throws ApplicationException {
		try {
			if (codInstituicao == null){
				throw new ApplicationException("AVISO.88", new String[] { OperacaoFacade.class.getSimpleName() + ".obterVistoriaBemImovelPaginado()" }, 
						ApplicationException.ICON_ERRO);
			}
			if (qtdePagina == null || numPagina == null) {
				throw new ApplicationException("ERRO.204", new String[] { OperacaoFacade.class.getSimpleName() + ".obterVistoriaBemImovelPaginado()" }, 
						ApplicationException.ICON_ERRO);
			}

			Pagina pagina = new Pagina();
			
			List<Integer> listaCodOrgao = new ArrayList<Integer>();
			if (usuario == null){
				throw new ApplicationException("AVISO.97");
			}
			if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.OPE_ORG_ABI.getCodigo())) {
				if (usuario.getListaUsuarioOrgao() == null || usuario.getListaUsuarioOrgao().size() < 1){
					throw new ApplicationException("AVISO.96");
				}
				for (UsuarioOrgao o : usuario.getListaUsuarioOrgao()){
					listaCodOrgao.add(o.getOrgao().getCodOrgao());
				}
			}else{
				listaCodOrgao = null;
			}
			
			
			pagina.setRegistros(hibernateFactory.getVistoriaDAO().listarVistoriaBemImovelPaginado(codBemImovel, dataInicial, dataFinal, situacaoImovel, qtdePagina, numPagina, listaCodOrgao, codInstituicao));

			if(totalRegistros ==null || totalRegistros==0){
				totalRegistros = hibernateFactory.getVistoriaDAO().buscarQtdLista().intValue();
			}

			pagina.setTotalRegistros(totalRegistros);
			pagina.setQuantidade(qtdePagina);
			pagina.setPaginaAtual(numPagina);

			return pagina;

		}catch (ApplicationException ae) {
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao obter Vistoria do Bem Imóvel Paginado" }, e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Obter Vistoria completa.<br>
	 * @author tatianapires
	 * @since  28/06/2011
	 * @param  codVistoria : Integer
	 * @return Vistoria
	 * @throws ApplicationException
	 */
	public static Vistoria obterVistoriaCompleta(Integer codVistoria) throws ApplicationException {
		try {
			if (codVistoria == null) {
				throw new ApplicationException("ERRO.204", new String[] { OperacaoFacade.class.getSimpleName() + ".obterVistoriaCompleta()" }, 
						ApplicationException.ICON_ERRO);
			}

			Vistoria vistoria = hibernateFactory.getVistoriaDAO().obterVistoriaCompleta(codVistoria);
			return vistoria;

		}catch (ApplicationException ae) {
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao obter Vistoria Completa" }, e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Obter CessaoDeUso completo.<br>
	 * @author ginaalmeida
	 * @since  01/08/2011
	 * @param  codCessaoDeUso : Integer
	 * @return CessaoDeUso
	 * @throws ApplicationException
	 */
	public static CessaoDeUso obterCessaoDeUsoCompleto(Integer codCessaoDeUso) throws ApplicationException {
		try {
			if (codCessaoDeUso == null) {
				throw new ApplicationException("ERRO.204", new String[] {OperacaoFacade.class.getSimpleName() + ".obterCessaoDeUsoCompleto()"}, ApplicationException.ICON_ERRO);
			}

			CessaoDeUso cessaoDeUso = hibernateFactory.getCessaoDeUsoDAO().obterCessaoDeUsoCompleto(codCessaoDeUso);
			return cessaoDeUso;

		}catch (ApplicationException ae) {
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] {"ao obter Cessão De Uso Completo"}, e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Obter Transferencia completo.<br>
	 * @author ginaalmeida
	 * @since  02/08/2011
	 * @param  codTransferencia : Integer
	 * @return Transferencia
	 * @throws ApplicationException
	 */
	public static Transferencia obterTransferenciaCompleto(Integer codTransferencia) throws ApplicationException {
		try {
			if (codTransferencia == null) {
				throw new ApplicationException("ERRO.204", new String[] {OperacaoFacade.class.getSimpleName() + ".obterTransferenciaCompleto()"}, ApplicationException.ICON_ERRO);
			}

			Transferencia transferencia = hibernateFactory.getTransferenciaDAO().obterTransferenciaCompleto(codTransferencia);
			return transferencia;

		}catch (ApplicationException ae) {
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] {"ao obter Transferência Completo"}, e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Obter Vistoria .<br>
	 * @author tatianapires
	 * @since  28/06/2011
	 * @param  codVistoria : Integer
	 * @return Vistoria
	 * @throws ApplicationException
	 */
	public static Vistoria obterVistoria(Integer codVistoria) throws ApplicationException {
		try {
			if (codVistoria == null) {
				throw new ApplicationException("ERRO.204", new String[] { OperacaoFacade.class.getSimpleName() + ".obterVistoria()" }, 
						ApplicationException.ICON_ERRO);
			}

			Vistoria vistoria = hibernateFactory.getVistoriaDAO().obter(codVistoria);
			return vistoria;

		}catch (ApplicationException ae) {
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao obter Vistoria" }, e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Obter CessaoDeUso.<br>
	 * @author ginaalmeida
	 * @since  01/08/2011
	 * @param  codCessaoDeUso : Integer
	 * @return CessaoDeUso
	 * @throws ApplicationException
	 */
	public static CessaoDeUso obterCessaoDeUso(Integer codCessaoDeUso) throws ApplicationException {
		try {
			if (codCessaoDeUso == null) {
				throw new ApplicationException("ERRO.204", new String[] {OperacaoFacade.class.getSimpleName() + ".obterCessaoDeUso()"}, ApplicationException.ICON_ERRO);
			}

			CessaoDeUso cessaoDeUso = hibernateFactory.getCessaoDeUsoDAO().obter(codCessaoDeUso);
			return cessaoDeUso;

		}catch (ApplicationException ae) {
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] {"ao obter Cessão De Uso"}, e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Listar Parametro Vistoria com Denominacao de Bem Imovel ativo.<br>
	 * @author tatianapires
	 * @since 28/06/2011
	 * @param codBemImovel : Integer
	 * @return List<ParametroVistoria> 
	 * @throws ApplicationException
	 */
	public static List<ParametroVistoria> listarParametroVistoriaComDenominacaoBemImovel(BemImovel bemImovel) throws ApplicationException {
		try {
			if (bemImovel.getCodBemImovel() == null) {
				throw new ApplicationException("ERRO.204", new String[] { OperacaoFacade.class.getSimpleName() + ".listarParametroVistoriaComDenominacaoBemImovel()" }, 
						ApplicationException.ICON_ERRO);
			}
			Integer codDenominacaoImovel = 0;
			if (bemImovel.getDenominacaoImovel() != null){
				codDenominacaoImovel = bemImovel.getDenominacaoImovel().getCodDenominacaoImovel();
			}
			List<ParametroVistoria> listaParametroVistoria = (List<ParametroVistoria>) hibernateFactory.getParametroVistoriaDAO().listarParametroVistoriaComDenominacaoBemImovel(codDenominacaoImovel);			
			return listaParametroVistoria;
		}catch (ApplicationException ae) {
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao listar Parâmetro Vistoria Com Denominação do Bem Imóvel" }, e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Listar Parametro Vistoria com Denominacao de Bem Imovel ativo 
	 * e que nao esteja contido na lista informada.<br>
	 * @author tatianapires
	 * @since 05/07/2011
	 * @param codBemImovel : Integer
	 * @param listaCodParametroVistoria : List<Integer>
	 * @return List<ParametroVistoria>  
	 * @throws ApplicationException
	 */ 
	public static List<ParametroVistoria> listarParametroVistoriaComDenominacaoBemImovelExceto(Vistoria vistoria,  List<Integer> listaCodParametroVistoria) throws ApplicationException {
		try {
			if (vistoria.getBemImovel().getInstituicao().getCodInstituicao() == null) {
				throw new ApplicationException("ERRO.204", new String[] { OperacaoFacade.class.getSimpleName() + ".listarParametroVistoriaComDenominacaoBemImovel()" }, 
						ApplicationException.ICON_ERRO);
			}
			Integer codDenominacaoImovel = 0;
			if (vistoria.getBemImovel().getDenominacaoImovel() != null){
				codDenominacaoImovel = vistoria.getBemImovel().getDenominacaoImovel().getCodDenominacaoImovel();
			}
			List<ParametroVistoria> listaParametroVistoria = (List<ParametroVistoria>) hibernateFactory.getParametroVistoriaDAO().listarParametroVistoriaComDenominacaoBemImovelExceto(vistoria.getBemImovel().getInstituicao().getCodInstituicao(), codDenominacaoImovel, listaCodParametroVistoria);
			
			return listaParametroVistoria;
		}catch (ApplicationException ae) {
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao listar Parâmetro Vistoria Com Denominação do Bem Imóvel" }, e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Obter status Vistoria.<br>
	 * @author tatianapires
	 * @since 28/06/2011
	 * @param codBemImovel
	 * @return StatusVistoria 
	 * @throws ApplicationException
	 */
	public static StatusVistoria obterStatusVistoria(Integer codStatusVistoria) throws ApplicationException {
		try {
			if (codStatusVistoria == null) {
				throw new ApplicationException("ERRO.204", new String[] { OperacaoFacade.class.getSimpleName() + ".obterStatusVistoria()" }, 
						ApplicationException.ICON_ERRO);
			}
			StatusVistoria statusVistoria = hibernateFactory.getStatusVistoriaDAO().obter(codStatusVistoria);
			
			return statusVistoria;
		}catch (ApplicationException ae) {
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao obter Status Vistoria" }, e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Salvar Item de Vistoria.<br>
	 * @author tatianapires
	 * @since  04/07/2011
	 * @param  vistoria : Vistoria
	 * @throws ApplicationException
	 */
	public static void salvarItemVistoria(ItemVistoria itemVistoria)throws ApplicationException{
		try{
			if(itemVistoria.getCodItemVistoria() == null){
				
				hibernateFactory.getItemVistoriaDAO().salvar(itemVistoria);

			} else{
				hibernateFactory.getItemVistoriaDAO().alterar(itemVistoria);
			} 

		}catch(ApplicationException ae){
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao incluir Item Vistoria" }, e, ApplicationException.ICON_ERRO);
		}
	}
	
	
	
	/**
	 * Salvar Vistoria ao incluir.<br>
	 * @author tatianapires
	 * @since  30/06/2011
	 * @param  vistoria : Vistoria
	 * @throws ApplicationException
	 */
	public static void salvarVistoria(String cpfResponsavel, Integer codEdificacao, Integer codVistoriador, Integer codBemImovel, Integer codInstituicao,  List<ParametroVistoria> listaParametroVistoriaChecados, Usuario usuario)throws ApplicationException{
		try{
			log4j.debug(Log.INICIO_TRANSACAO);
			HibernateUtil.currentTransaction();
			
			Vistoria vistoriaNova = new Vistoria();
			StatusVistoria statusVistoria = OperacaoFacade.obterStatusVistoria(SituacaoVistoria.ABERTA.getId());
			Edificacao edificacao = null;
			if (codEdificacao != null) {
				edificacao = CadastroFacade.obterEdificacao(codEdificacao);
			}
			Vistoriador vistoriador = OperacaoFacade.obterVistoriador(Integer.valueOf(codVistoriador));
			vistoriaNova.setBemImovel(CadastroFacade.obterBemImovelInstituicaoExibir(Integer.valueOf(codBemImovel), codInstituicao, usuario));
			vistoriaNova.setCpfResponsavel(cpfResponsavel);
			vistoriaNova.setTsInclusao(new Date());
			vistoriaNova.setStatusVistoria(statusVistoria);
			vistoriaNova.setEdificacao(edificacao);
			vistoriaNova.setVistoriador(vistoriador);
			
			List<ItemVistoria> listaItemVistoriaASalvar = new ArrayList<ItemVistoria>();
			int cont = 1;
			for (ParametroVistoria parametroVistoriaTela : listaParametroVistoriaChecados) {
				ItemVistoria itemVistoriaNovo = new ItemVistoria();
				itemVistoriaNovo.setVistoria(vistoriaNova);
				itemVistoriaNovo.setCodItemVistoria(cont);
				itemVistoriaNovo.setParametroVistoria(parametroVistoriaTela);
				itemVistoriaNovo.setIndTipoParametro(parametroVistoriaTela.getIndTipoParametro());
				itemVistoriaNovo.setDescricao(parametroVistoriaTela.getDescricao());
				if (Integer.valueOf(1).equals(itemVistoriaNovo.getIndTipoParametro())) { 
					itemVistoriaNovo.setTextoDominio("");
				}
				List<ItemVistoriaDominio> listItemVistoriaDominio = new ArrayList<ItemVistoriaDominio>();
				if (itemVistoriaNovo.getIndTipoParametro() != 1) { // nao for texto
					int contDomi = 1;
					for (ParametroVistoriaDominio parametroVistoriaDominio : parametroVistoriaTela.getListaParametroVistoriaDominio()) {
						ItemVistoriaDominio itemVistoriaDominioNovo = new ItemVistoriaDominio();
						itemVistoriaDominioNovo.setCodItemVistoriaDominio(contDomi);
						itemVistoriaDominioNovo.setItemVistoria(itemVistoriaNovo);
						itemVistoriaDominioNovo.setDescricao(parametroVistoriaDominio.getDescricao());
						itemVistoriaDominioNovo.setIndSelecionado(null);
						listItemVistoriaDominio.add(itemVistoriaDominioNovo);
						contDomi++;
					}
				}
				itemVistoriaNovo.setListaItemVistoriaDominio(Util.listToSet(listItemVistoriaDominio));
				listaItemVistoriaASalvar.add(itemVistoriaNovo);
				cont++;
			}
			vistoriaNova.setListaItemVistoria(Util.listToSet(listaItemVistoriaASalvar));
			
			for (ItemVistoria itemVistoriaS : vistoriaNova.getListaItemVistoria()) {
				itemVistoriaS.setCodItemVistoria(null);
				
				for (ItemVistoriaDominio itemVistoriaDomS : itemVistoriaS.getListaItemVistoriaDominio()) {
					itemVistoriaDomS.setCodItemVistoriaDominio(null);
				}
			}
			
			OperacaoFacade.salvarVistoria(vistoriaNova);
			
			log4j.debug(Log.FIM_TRANSACAO);
			HibernateUtil.commitTransaction();
			
		}catch (ApplicationException ae) {
			try {
				log4j.debug(Log.ERRO_ENCERRAMENTO_TRANSACAO);
				HibernateUtil.rollbackTransaction();
			}catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarVistoria()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				log4j.debug(Log.ERRO_ENCERRAMENTO_TRANSACAO);
				HibernateUtil.rollbackTransaction();
			}catch (Exception ex) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarVistoria()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao incluir Vistoria" }, e, ApplicationException.ICON_ERRO);
		}	
	}
	
	/**
	 * Salvar Vistoria ao alterar.<br>
	 * @author tatianapires
	 * @since  30/06/2011
	 * @param  vistoria : Vistoria
	 * @throws ApplicationException
	 */
	public static void salvarVistoria(Integer codVistoria, Integer codEdificacao, Integer codVistoriador, Date dataExecucao, Integer idadeAparente, 
			String observacao, List<ItemVistoria> listaItemVistoriaChecados, Boolean concluirVistoria) 
			throws ApplicationException{
		try{
			log4j.debug(Log.INICIO_TRANSACAO);
			HibernateUtil.currentTransaction();
			
			Vistoria vistoria = OperacaoFacade.obterVistoriaCompleta(codVistoria);
			
			if (codEdificacao != null) {
				Edificacao edificacao = CadastroFacade.obterEdificacao(codEdificacao);
				vistoria.setEdificacao(edificacao);
			}
			
			Vistoriador vistoriador = OperacaoFacade.obterVistoriador(codVistoriador);
			vistoria.setVistoriador(vistoriador);
			vistoria.setDataVistoria(dataExecucao);
			vistoria.setIdadeAparente(idadeAparente);
			vistoria.setObservacao(observacao);
			vistoria.setTsAtualizacao(new Date());
			vistoria.setListaItemVistoria(Util.listToSet(listaItemVistoriaChecados));
			if (concluirVistoria) {
				StatusVistoria statusVistoria = OperacaoFacade.obterStatusVistoria(SituacaoVistoria.FINALIZADA.getId());
				vistoria.setStatusVistoria(statusVistoria);
			}
			
			hibernateFactory.getVistoriaDAO().alterarVistoria(vistoria);
			
			log4j.debug(Log.FIM_TRANSACAO);
			HibernateUtil.commitTransaction();
			
		}catch (ApplicationException ae) {
			try {
				log4j.debug(Log.ERRO_ENCERRAMENTO_TRANSACAO);
				HibernateUtil.rollbackTransaction();
			}catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarVistoria()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				log4j.debug(Log.ERRO_ENCERRAMENTO_TRANSACAO);
				HibernateUtil.rollbackTransaction();
			}catch (Exception ex) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarVistoria()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao incluir Vistoria" }, e, ApplicationException.ICON_ERRO);
		}	
	}
	
	/**
	 * Salvar Vistoria.<br>
	 * @author tatianapires
	 * @since  30/06/2011
	 * @param  vistoria : Vistoria
	 * @throws ApplicationException
	 */
	public static void salvarVistoria(Vistoria vistoria)throws ApplicationException{
		try{
			if(vistoria.getCodVistoria() == null){
				hibernateFactory.getVistoriaDAO().salvar(vistoria);

			} else {
				Vistoria vistoriaASalvar = vistoria;
				
				log4j.debug(Log.INICIO_TRANSACAO);
				HibernateUtil.currentTransaction();
				hibernateFactory.getVistoriaDAO().alterar(vistoria);
				log4j.debug(Log.FIM_TRANSACAO);
				HibernateUtil.commitTransaction();
				
				// - simula o DELETE_ORPHAN do hibernate para as listas CASCADE
				// Inicio
				Vistoria vistoriaPersistida =hibernateFactory.getVistoriaDAO().obterVistoriaCompleta(vistoria.getCodVistoria());
				
				List<ItemVistoria> listaItemVistoriaDeletar = new ArrayList<ItemVistoria>();
				Boolean achouItem = Boolean.FALSE;
				for (ItemVistoria itemVistoriaPersis : vistoriaPersistida.getListaItemVistoria()) {
					for (ItemVistoria itemVistoria : vistoriaASalvar.getListaItemVistoria()) {
						if ((itemVistoriaPersis.getCodItemVistoria().equals(itemVistoria.getCodItemVistoria()))) {
							achouItem = Boolean.TRUE;
						}
					}
					if (!achouItem) {
						listaItemVistoriaDeletar.add(itemVistoriaPersis);
					}
					achouItem = Boolean.FALSE;
				}
				
				for (ItemVistoria item : listaItemVistoriaDeletar) {
					hibernateFactory.getItemVistoriaDAO().excluir(item);
				}
				// Fim
			}
		}catch (ApplicationException ae) {
			try {
				log4j.debug(Log.ERRO_ENCERRAMENTO_TRANSACAO);
				HibernateUtil.rollbackTransaction();
			}catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarVistoria()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				log4j.debug(Log.ERRO_ENCERRAMENTO_TRANSACAO);
				HibernateUtil.rollbackTransaction();
			}catch (Exception ex) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarVistoria()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao incluir Vistoria" }, 
					e, ApplicationException.ICON_ERRO);
		}	
	}

	/**
	 * Excluir Vistoria .<br>
	 * @author tatianapires
	 * @since  28/06/2011
	 * @param  codVistoria : Integer
	 * @return Vistoria
	 * @throws ApplicationException
	 */
	public static void excluirVistoria(Vistoria vistoria) throws ApplicationException {
		try {
			if (vistoria == null) {
				throw new ApplicationException("ERRO.204", new String[] { OperacaoFacade.class.getSimpleName() + ".excluirVistoria()" }, 
						ApplicationException.ICON_ERRO);
			}

			hibernateFactory.getVistoriaDAO().excluir(vistoria);

		}catch (ApplicationException ae) {
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao excluir Vistoria" }, 
					e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Listar edificacao com vinculo em bem imovel.<br>
	 * @author tatianapires
	 * @since 26/06/2011
	 * @param codBemImovel
	 * @return List<Edificacao> 
	 * @throws ApplicationException
	 */
	public static List<Edificacao> listarEdificacaoComVinculoBemImovel(Integer codBemImovel, Integer codInstituicao) throws ApplicationException {
		try {
			if (codBemImovel == null) {
				throw new ApplicationException("ERRO.204", new String[] { OperacaoFacade.class.getSimpleName() + ".listarEdificacaoComVinculoBemImovel()" }, 
						ApplicationException.ICON_ERRO);
			}

			Edificacao edificacao = new Edificacao();
			BemImovel bemImovel = hibernateFactory.getBemImovelDAO().obterExibirPorInstituicao(codBemImovel, codInstituicao);
			
			edificacao.setBemImovel(bemImovel);
			
			List<Edificacao> listaEdificacao = (List<Edificacao>) hibernateFactory.getEdificacaoDAO().listar(edificacao, new String[]{"especificacao", GenericHibernateDAO.ORDEM_ASC});
			
			return listaEdificacao;
		}catch (ApplicationException ae) {
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao listar Edificação Com Vínculo de Bem Imóvel" }, e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Obter Vistoriador .<br>
	 * @author tatianapires
	 * @since  28/06/2011
	 * @param  codVistoria : Integer
	 * @return Vistoria
	 * @throws ApplicationException
	 */
	public static Vistoriador obterVistoriador(Integer codVistoriador) throws ApplicationException {
		try {
			if (codVistoriador == null) {
				throw new ApplicationException("ERRO.204", new String[] { OperacaoFacade.class.getSimpleName() + ".obterVistoriador()" }, 
						ApplicationException.ICON_ERRO);
			}

			return hibernateFactory.getVistoriadorDAO().obter(codVistoriador);

		}catch (ApplicationException ae) {
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao obter Vistoriador" }, e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Listar Vistoriador.<br>
	 * @author tatianapires
	 * @since 28/06/2011
	 * @param codBemImovel
	 * @return List<Edificacao> 
	 * @throws ApplicationException
	 */
	public static List<Vistoriador> listarVistoriador(Integer codInstituicao) throws ApplicationException {
		try {
			Vistoriador v = new Vistoriador();
			Instituicao i = new Instituicao();
			if (codInstituicao == null){
				codInstituicao = 0;
			}
			i.setCodInstituicao(codInstituicao);
			v.setInstituicao(i);
			List<Vistoriador> listaVistoriador = (List<Vistoriador>) hibernateFactory.getVistoriadorDAO().listar(v);
			
			return listaVistoriador;
		}catch (ApplicationException ae) {
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao listar Vistoriador" }, e, ApplicationException.ICON_ERRO);
		}
	}


	/**
	 * Incluir ParametroVistoria.<br>
	 * @author oksana
	 * @since 30/06/2011
	 * @param parametroVistoria: ParametroVistoria
	 * @return void 
	 * @throws ApplicationException
	 */
	public static void salvarParametroVistoria(ParametroVistoria parametroVistoria, List<ParametroVistoriaDominio> listaParametroVistoriaDominio) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			hibernateFactory.getParametroVistoriaDAO().salvar(parametroVistoria);
			for (ParametroVistoriaDominio pvd: listaParametroVistoriaDominio){
				pvd.setParametroVistoria(parametroVistoria);
				hibernateFactory.getParametroVistoriaDominioDAO().salvar(pvd);
			}
			HibernateUtil.commitTransaction();
			
			
		}catch (ApplicationException ae) {
			try {
				log4j.debug(Log.ERRO_ENCERRAMENTO_TRANSACAO);
				HibernateUtil.rollbackTransaction();
			}catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarParametroVistoria()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				log4j.debug(Log.ERRO_ENCERRAMENTO_TRANSACAO);
				HibernateUtil.rollbackTransaction();
			}catch (Exception ex) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarParametroVistoria()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao incluir Parâmetro Vistoria" }, e, ApplicationException.ICON_ERRO);
		}	
	}

	/**
	 * Obter ParametroVistoria.<br>
	 * @author oksana
	 * @since 30/06/2011
	 * @param codParametroVistoria: Integer
	 * @return ParametroVistoria
	 * @throws ApplicationException
	 */
	public static ParametroVistoria obterParametroVistoria(Integer codParametroVistoria) throws ApplicationException {
		try {
			return hibernateFactory.getParametroVistoriaDAO().obter(codParametroVistoria);
		}catch (ApplicationException ae) {
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao obter Parâmetro Vistoria" }, e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Alterar ParametroVistoria.<br>
	 * @author oksana
	 * @since 01/07/2011
	 * @param parametroVistoria: ParametroVistoria
	 * @return void 
	 * @throws ApplicationException 
	 */
	public static void alterarParametroVistoria(ParametroVistoria parametroVistoria, List<ParametroVistoriaDominio> listaParametroVistoriaDominio) throws ApplicationException {
		try {
			ParametroVistoria parametroVistoriaAntigo = hibernateFactory.getParametroVistoriaDAO().obter(parametroVistoria.getCodParametroVistoria());
			HibernateUtil.currentTransaction();
			
			for (ParametroVistoriaDominio pvd: parametroVistoriaAntigo.getListaParametroVistoriaDominio()){
				Boolean existe = false;
				for (ParametroVistoriaDominio pvdNovo: listaParametroVistoriaDominio){
					if (pvd.getDescricao().equals(pvdNovo.getDescricao())){
						existe = true;
					}
				}
				if (!existe){
				//	pvd = hibernateFactory.getParametroVistoriaDominioDAO().merge(pvd);
					hibernateFactory.getParametroVistoriaDominioDAO().excluir(pvd);
				}
			}
			for (ParametroVistoriaDominio pvdNovo: listaParametroVistoriaDominio){
				Boolean existe = false;
				for (ParametroVistoriaDominio pvd: parametroVistoriaAntigo.getListaParametroVistoriaDominio()){
					if (pvd.getDescricao().equals(pvdNovo.getDescricao())){
						existe = true;
					}
				}
				if (!existe){
					pvdNovo.setParametroVistoria(parametroVistoria);
					hibernateFactory.getParametroVistoriaDominioDAO().salvar(pvdNovo);
				}
			}
			
			hibernateFactory.getParametroVistoriaDenominacaoImovelDAO().excluirPorParametroVistoria(parametroVistoria.getCodParametroVistoria());
			
			hibernateFactory.getParametroVistoriaDAO().alterar(parametroVistoria);
			HibernateUtil.commitTransaction();
			
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarParametroVistoria()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarParametroVistoria()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao alterar Parâmetro Vistoria" }, e, ApplicationException.ICON_ERRO);
		}
	}
	/**
	 * Verifica se já existe descricao para o ParametroVistoria.<br>
	 * @author oksana
	 * @since 01/07/2011
	 * @param codParametroVistoria: Integer
	 * @param descricao: String
	 * @return Boolean
	 * @throws ApplicationException 
	 */
	public static Boolean existeDescricaoParametroVistoria(Integer codParametroVistoria, String descricao, Integer codInstituicao) throws ApplicationException {
		try {
			if (codInstituicao == null){
				ParametroVistoria p = hibernateFactory.getParametroVistoriaDAO().obter(codParametroVistoria);
				codInstituicao = p.getInstituicao().getCodInstituicao();
			}
			return hibernateFactory.getParametroVistoriaDAO().existeDescricao(codParametroVistoria, descricao, codInstituicao);
		}catch (ApplicationException ae) {
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao verificar existência de descrição do Parâmetro Vistoria" }, e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Salvar parametro em vistoria.<br>
	 * @author tatianapires
	 * @since  30/06/2011
	 * @param  vistoria : Vistoria
	 * @param listaParametroVistoriaChecados : List<ParametroVistoria> 
	 * @throws ApplicationException
	 */
	public static void salvarParametroAdicionadoEmVistoria(Vistoria vistoria, List<ParametroVistoria> listaParametroVistoriaChecados)throws ApplicationException{
		try{
			log4j.debug(Log.INICIO_TRANSACAO);
			HibernateUtil.currentTransaction();
			
			if (vistoria == null || !Util.validarLista(listaParametroVistoriaChecados)) {
				throw new ApplicationException("ERRO.204", new String[] { OperacaoFacade.class.getSimpleName() + ".salvarParametroVistoriaAdicionado()" }, 
						ApplicationException.ICON_ERRO);
			}
			
			List<ItemVistoria> listItemVistoria = new ArrayList<ItemVistoria>();
			int cont = 1;
			for (ParametroVistoria parametroVistoria : listaParametroVistoriaChecados) {
				ItemVistoria itemVistoriaNovo = new ItemVistoria();
				itemVistoriaNovo.setCodItemVistoria(-cont);
				itemVistoriaNovo.setVistoria(vistoria);
				itemVistoriaNovo.setParametroVistoria(parametroVistoria);
				itemVistoriaNovo.setIndTipoParametro(parametroVistoria.getIndTipoParametro());
				itemVistoriaNovo.setDescricao(parametroVistoria.getDescricao());
				if (itemVistoriaNovo.getIndTipoParametro() != 1) { //texto
					int cont2 = 1;
					List<ItemVistoriaDominio> listItemVistoriaDominio = new ArrayList<ItemVistoriaDominio>();
					for (ParametroVistoriaDominio parametroVistoriaDominio : parametroVistoria.getListaParametroVistoriaDominio()) {
						ItemVistoriaDominio itemVistoriaDominioNovo = new ItemVistoriaDominio();
						itemVistoriaDominioNovo.setCodItemVistoriaDominio(-cont2);
						itemVistoriaDominioNovo.setItemVistoria(itemVistoriaNovo);
						itemVistoriaDominioNovo.setDescricao(parametroVistoriaDominio.getDescricao());
						itemVistoriaDominioNovo.setIndSelecionado(null);
						listItemVistoriaDominio.add(itemVistoriaDominioNovo);
						cont2++;
					}
					itemVistoriaNovo.setListaItemVistoriaDominio(Util.listToSet(listItemVistoriaDominio));
				}
				listItemVistoria.add(itemVistoriaNovo);
				cont++;
			}
			
			for (ItemVistoria itemVistoriaChecado : listItemVistoria) {
				vistoria.getListaItemVistoria().add(itemVistoriaChecado);
			}
		
			for (ItemVistoria itemVistoriaS : vistoria.getListaItemVistoria()) {
				if (itemVistoriaS.getCodItemVistoria() < 0) {
					itemVistoriaS.setCodItemVistoria(null);
					
					for (ItemVistoriaDominio itemVistoriaDomS : itemVistoriaS.getListaItemVistoriaDominio()) {
						itemVistoriaDomS.setCodItemVistoriaDominio(null);
					}
				}
			}
			
			OperacaoFacade.salvarVistoria(vistoria);
			
			log4j.debug(Log.FIM_TRANSACAO);
			HibernateUtil.commitTransaction();
			
		}catch (ApplicationException ae) {
			try {
				log4j.debug(Log.ERRO_ENCERRAMENTO_TRANSACAO);
				HibernateUtil.rollbackTransaction();
			}catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarParametroAdicionadoEmVistoria()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				log4j.debug(Log.ERRO_ENCERRAMENTO_TRANSACAO);
				HibernateUtil.rollbackTransaction();
			}catch (Exception ex) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarParametroAdicionadoEmVistoria()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao incluir Parâmetro Adicionado Em Vistoria" }, e, ApplicationException.ICON_ERRO);
		}	
	}
	
	/**
	 * Ativar/Desativar ParametroVistoria.<br>
	 * @author oksana
	 * @since 01/07/2011
	 * @param parametroVistoria: ParametroVistoria
	 * @return void 
	 * @throws ApplicationException 
	 */
	public static void ativarDesativarParametroVistoria(ParametroVistoria parametroVistoria, Boolean situacao) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			parametroVistoria.setIndAtivo(situacao);
			hibernateFactory.getParametroVistoriaDAO().alterar(parametroVistoria);
			HibernateUtil.commitTransaction();
			
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em ativarDesativarParametroVistoria()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em ativarDesativarParametroVistoria()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao ativar Desativar Parâmetro Vistoria" }, e, ApplicationException.ICON_ERRO);
		}
	}
	/**
	 * Excluir ParametroVistoria.<br>
	 * @author oksana
	 * @since 01/07/2011
	 * @param parametroVistoria: ParametroVistoria
	 * @return void 
	 * @throws ApplicationException 
	 */
	public static void excluirParametroVistoria(ParametroVistoria parametroVistoria) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			for (ParametroVistoriaDominio pvd: parametroVistoria.getListaParametroVistoriaDominio()){
				hibernateFactory.getParametroVistoriaDominioDAO().excluir(pvd);
			}
			hibernateFactory.getParametroVistoriaDAO().excluir(parametroVistoria);
			HibernateUtil.commitTransaction();
			
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirParametroVistoria()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirParametroVistoria()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao excluir Parâmetro Vistoria" }, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Obter ParametroAgenda.<br>
	 * @author oksana
	 * @since  05/07/2011
	 * @param  codParametroAgenda : Integer
	 * @return ParametroAgenda
	 * @throws ApplicationException
	 */
	public static ParametroAgenda obterParametroAgendaUnico(Integer codInstituicao) throws ApplicationException {
		try {
			ParametroAgenda parametroAgenda = hibernateFactory.getParametroAgendaDAO().obterUnico(codInstituicao);
			return parametroAgenda;

		}catch (ApplicationException ae) {
			log4j.warn(ae.getMessage(), ae); 
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao obter Parâmetro Agenda" }, e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * SalvarAlterar ParametroAgenda.<br>
	 * @author oksana
	 * @since 06/07/2011
	 * @param parametroAgenda: ParametroAgenda
	 * @param listaParametroAgendaEmail: List<ParametroAgendaEmail> 
	 * @return void 
	 * @throws ApplicationException 
	 */
	public static void salvarParametroAgenda(ParametroAgenda parametroAgenda, List<ParametroAgendaEmail> listaParametroAgendaEmail) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			
			if (parametroAgenda.getCodParametroAgenda() == null){
				hibernateFactory.getParametroAgendaDAO().salvar(parametroAgenda);
			}else{
				hibernateFactory.getParametroAgendaDAO().alterar(parametroAgenda);
				List<ParametroAgendaEmail> lstPae = (List<ParametroAgendaEmail>) hibernateFactory.getParametroAgendaEmailDAO().listar(parametroAgenda.getCodParametroAgenda());
				for (ParametroAgendaEmail pae: lstPae){
					hibernateFactory.getParametroAgendaEmailDAO().excluir(pae);
				}	
			}
			for (ParametroAgendaEmail pae: listaParametroAgendaEmail){
				hibernateFactory.getParametroAgendaEmailDAO().salvar(pae);
			}
			HibernateUtil.commitTransaction();
			
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarParametroAgenda()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarParametroAgenda()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao incluir Parâmetro Agenda" }, e, ApplicationException.ICON_ERRO);
		}
	}
	

	
	
	

	/**
	 * Gerar AgendaDTO.<br>
	 * @author oksana
	 * @since 06/07/2011
	 * @param  
	 * @return AgendaDTO
	 * @throws ApplicationException 
	 */
	public static AgendaDTO gerarAgendaDTO(Instituicao instituicao) throws ApplicationException {
		try {
			ParametroAgenda parametroAgenda = hibernateFactory.getParametroAgendaDAO().obterUnico(instituicao.getCodInstituicao());
			AgendaDTO agendaDTO = new AgendaDTO();
//			if (parametroAgenda == null){
//				throw new ApplicationException("AVISO.86", new String[] { OperacaoFacade.class.getSimpleName() + ".gerarAgendaDTO()" }, ApplicationException.ICON_ERRO);
//			}
//			if (parametroAgenda.getListaParametroAgendaEmail() == null || parametroAgenda.getListaParametroAgendaEmail().isEmpty()){
//				throw new ApplicationException("AVISO.87", new String[] { OperacaoFacade.class.getSimpleName() + ".gerarAgendaDTO()" }, ApplicationException.ICON_ERRO);
//			}
			if (parametroAgenda != null){
				agendaDTO.setParametroAgenda(parametroAgenda);
				agendaDTO.setInstituicao(parametroAgenda.getInstituicao().getSiglaDescricao());
				//Notificacoes a vencer
				List<NotificacaoDTO> listaNotificacaoDTOAVencerStr = OperacaoFacade.listarNotificacao(parametroAgenda.getInstituicao(),parametroAgenda);
				agendaDTO.setListaNotificacaoDTOAVencerStr(listaNotificacaoDTOAVencerStr);
				
				//Notificacoes vencidas
				List<NotificacaoDTO> listaNotificacaoDTOVencidaStr = OperacaoFacade.listarNotificacao(parametroAgenda.getInstituicao(), null);
				agendaDTO.setListaNotificacaoDTOVencidaStr(listaNotificacaoDTOVencidaStr);
				
				//Vistoria Vencida
				List<VistoriaDTO> listaVistoriaDTOVencidaStr = OperacaoFacade.listarVistoria(parametroAgenda);
				agendaDTO.setListaVistoriaDTOVencidaStr(listaVistoriaDTOVencidaStr);
				 
				//Vistoria nao finalizada
				List<VistoriaDTO> listaVistoriaDTONaoFinalizada = OperacaoFacade.listarVistoriaNaoFinalizada(parametroAgenda);
				agendaDTO.setListaVistoriaDTONaoFinalizada(listaVistoriaDTONaoFinalizada);

				//Doacao a vencer
				List<DoacaoDTO> listaDoacaoDTOAVencerStr = OperacaoFacade.listarDoacao(parametroAgenda.getInstituicao(),parametroAgenda);
				agendaDTO.setListaDoacaoDTOAVencerStr(listaDoacaoDTOAVencerStr);
				//Doacao nao finalizada
				List<DoacaoDTO> listaDoacaoDTONaoFinalizada = OperacaoFacade.listarDoacaoNaoFinalizada(parametroAgenda.getInstituicao());
				agendaDTO.setListaDoacaoDTONaoFinalizada(listaDoacaoDTONaoFinalizada);
				
				//Cessao a vencer
				List<CessaoDeUsoDTO> listaCessaoDeUsoDTOAVencerStr = OperacaoFacade.listarCessaoDeUso(parametroAgenda.getInstituicao(), parametroAgenda);
				agendaDTO.setListaCessaoDeUsoVencerStr(listaCessaoDeUsoDTOAVencerStr);
				
				//Cessao em preenchimento
				StatusTermo statusTermo = new StatusTermo();
				statusTermo.setCodStatusTermo(0); //em rascunho
				List<CessaoDeUsoDTO> listaCessaoDeUsoDTOEmPreenchimento = OperacaoFacade.listarCessaoDeUsoPorStatus(parametroAgenda.getInstituicao(), statusTermo);
				agendaDTO.setListaCessaoDeUsoEmPreenchimentoStr(listaCessaoDeUsoDTOEmPreenchimento);
				
				//Cessao em renovação
				statusTermo.setCodStatusTermo(5); //em renovação
				List<CessaoDeUsoDTO> listaCessaoDeUsoDTOEmRenovacao = OperacaoFacade.listarCessaoDeUsoPorStatus(parametroAgenda.getInstituicao(), statusTermo);
				agendaDTO.setListaCessaoDeUsoEmRenovacaoStr(listaCessaoDeUsoDTOEmRenovacao);
				
				HibernateUtil.currentTransaction();
				//atualizar status de doacao vencida
				List<DoacaoDTO> listaDoacaoDTOVencida = OperacaoFacade.listarDoacao(parametroAgenda.getInstituicao(), null);
				agendaDTO.setListaDoacaoDTOVencida(listaDoacaoDTOVencida);
				StatusTermo status = new StatusTermo();
				status.setCodStatusTermo(2);//finalizado
				OperacaoFacade.atualizarDoacaoVencida(listaDoacaoDTOVencida, status);

				//atualizar status de cessoes vencidas
				List<CessaoDeUsoDTO> listaCessaoDeUsoDTOVencida = OperacaoFacade.listarCessaoDeUso(parametroAgenda.getInstituicao(), null);
				agendaDTO.setListaCessaoDeUsoDTOVencida(listaCessaoDeUsoDTOVencida);
				status.setCodStatusTermo(2);//finalizado
				OperacaoFacade.atualizarCessaoDeUsoVencida(listaCessaoDeUsoDTOVencida, status);
				
				HibernateUtil.commitTransaction();
			}
			return agendaDTO;
			
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em gerarAgendaDTO()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em gerarAgendaDTO()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao gerar AgendaDTO" }, e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Listar Notificacao.<br>
	 * @author oksana
	 * @since 06/07/2011
	 * @param  ParametroAgenda
	 * @return List<String>
	 * @throws ApplicationException 
	 */
	public static List<NotificacaoDTO> listarNotificacao(Instituicao instituicao, ParametroAgenda parametroAgenda) throws ApplicationException {
		try {
			List<NotificacaoDTO> listaNotificacaoStr = new ArrayList<NotificacaoDTO>();
			List<Notificacao> listaNotificacao = new ArrayList<Notificacao>();
			if (parametroAgenda != null && parametroAgenda.getNumeroDiasVencimentoNotificacao() != null && parametroAgenda.getNumeroDiasVencimentoNotificacao() > 0){
				//a vencer
				listaNotificacao = (List<Notificacao>) hibernateFactory.getNotificacaoDAO().listarVencidaAVencer(parametroAgenda.getInstituicao(), parametroAgenda);
			}else{
				//vencidas
				listaNotificacao = (List<Notificacao>) hibernateFactory.getNotificacaoDAO().listarVencidaAVencer(instituicao, null);
			}
			for (Notificacao n: listaNotificacao){
				NotificacaoDTO notificacaoDTO = new NotificacaoDTO();
				notificacaoDTO.setCodNotificacao(n.getCodNotificacao());
				notificacaoDTO.setCodBemImovel(n.getDocumentacao().getBemImovel().getCodBemImovel());
				notificacaoDTO.setDescricao(n.getDescricao());
				if (n.getPrazoNotificacao() != null){
					notificacaoDTO.setPrazo(Util.formataDataSemHora(n.getPrazoNotificacao()));	
				} else {
					notificacaoDTO.setPrazo(" - ");					
				}
				listaNotificacaoStr.add(notificacaoDTO);
			}
			return listaNotificacaoStr;
		}catch (ApplicationException ae) {
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao listar Notificação" }, e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Listar Vistoria.<br>
	 * @author oksana
	 * @since 07/07/2011
	 * @param  ParametroAgenda
	 * @return List<String>
	 * @throws ApplicationException 
	 */
	public static List<VistoriaDTO> listarVistoria(ParametroAgenda parametroAgenda) throws ApplicationException {
		try {
			List<VistoriaDTO> listaVistoriaStr = new ArrayList<VistoriaDTO>();
			if (parametroAgenda != null && parametroAgenda.getNumeroDiasVencimentoVistoria() != null && parametroAgenda.getNumeroDiasVencimentoVistoria() > 0){
				//Vencida
				Collection<BemImovelVistoriaDTO> listaBemImovelVistoriaDTO = hibernateFactory.getBemImovelDAO().listarBemImovelVistoriaDTO(parametroAgenda);
				List<BemImovelVistoriaDTO> lista = (List<BemImovelVistoriaDTO>) listaBemImovelVistoriaDTO;
				for (BemImovelVistoriaDTO b: lista){
					VistoriaDTO vistoriaDTO = new VistoriaDTO();
					vistoriaDTO.setCodVistoria(null);
					vistoriaDTO.setCodBemImovel(b.getCodBemImovel());
					if (b.getDataUltimaVistoria() != null){
						vistoriaDTO.setDataVistoriaFormatada(Util.formataDataSemHora(b.getDataUltimaVistoria()));	
					}else{
						vistoriaDTO.setDataVistoriaFormatada(" - ");
					}
					vistoriaDTO.setDenominacao(b.getDenominacao());
					vistoriaDTO.setVistoriador(null);
					
					listaVistoriaStr.add(vistoriaDTO);
				}
			}
			return listaVistoriaStr;
		}catch (ApplicationException ae) {
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao listar Vistoria" }, e, ApplicationException.ICON_ERRO);
		}
	}
	
	
	
	/**
	 * Listar Vistoria nao finalizada.<br>
	 * @author oksana
	 * @since 07/07/2011
	 * @param  ParametroAgenda
	 * @return List<String>
	 * @throws ApplicationException 
	 */
	public static List<VistoriaDTO> listarVistoriaNaoFinalizada(ParametroAgenda parametroAgenda) throws ApplicationException {
		try {
			List<VistoriaDTO> listaVistoriaStr = new ArrayList<VistoriaDTO>();
			StatusVistoria status = hibernateFactory.getStatusVistoriaDAO().obter(1); //aberta
			List<Vistoria> listaVistoria = hibernateFactory.getVistoriaDAO().listarVistoriaPorStatus(status, parametroAgenda);
 
			for (Vistoria v: listaVistoria){
				VistoriaDTO vistoriaDTO = new VistoriaDTO();
				vistoriaDTO.setCodBemImovel(v.getBemImovel().getCodBemImovel());
				vistoriaDTO.setCodVistoria(v.getCodVistoria());
				if (v.getDataVistoria() != null){
					vistoriaDTO.setDataVistoriaFormatada(Util.formataDataSemHora(v.getDataVistoria()));	
				}else{
					vistoriaDTO.setDataVistoriaFormatada(" - ");
				}
				if (v.getVistoriador() != null){
					vistoriaDTO.setVistoriador(v.getVistoriador().getNome());	
				}else{
					vistoriaDTO.setVistoriador(" - ");
				}
				listaVistoriaStr.add(vistoriaDTO);
			}
			return listaVistoriaStr;
		}catch (ApplicationException ae) {
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao listar Vistoria Não Finalizada" }, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Listar Doacao.<br>
	 * @author oksana
	 * @since 08/07/2011
	 * @param  ParametroAgenda
	 * @return List<String>
	 * @throws ApplicationException 
	 */
	public static List<DoacaoDTO> listarDoacao(Instituicao instituicao, ParametroAgenda parametroAgenda) throws ApplicationException {
		try {
			List<DoacaoDTO> listaDoacaoStr = new ArrayList<DoacaoDTO>();
			List<Doacao> listaDoacao = new ArrayList<Doacao>();
			if (parametroAgenda != null && parametroAgenda.getNumeroDiasVencimentoDoacao() != null && parametroAgenda.getNumeroDiasVencimentoDoacao() > 0){
				//a vencer
				listaDoacao = (List<Doacao>) hibernateFactory.getDoacaoDAO().listarVencidaAVencer(instituicao, parametroAgenda);
			}else{
				//vencidas
				listaDoacao = (List<Doacao>) hibernateFactory.getDoacaoDAO().listarVencidaAVencer(instituicao, null);
			}
			for (Doacao d: listaDoacao){
				DoacaoDTO doacaoDTO = new DoacaoDTO();
				doacaoDTO.setCodDoacao(d.getCodDoacao());
				if(d.getDtInicioVigencia() != null){
					Calendar cal = new GregorianCalendar();
					cal.setTime(d.getDtInicioVigencia());
					int ano = cal.get(Calendar.YEAR);
					doacaoDTO.setNumeroAnoTermo(d.getCodDoacao()+"/" + ano);
				} else{
					doacaoDTO.setNumeroAnoTermo(d.getCodDoacao().toString());
				}	
				if (d.getProtocolo() != null && d.getProtocolo().trim().length() > 0) {
					doacaoDTO.setProtocolo(d.getProtocoloFormatado());
				} else {
					doacaoDTO.setProtocolo(" - ");
				}
				doacaoDTO.setCodBemImovel(d.getBemImovel().getCodBemImovel());
				if (d.getDtInicioVigencia() != null){
					doacaoDTO.setDataInicioFormatada(Util.formataDataSemHora(d.getDtInicioVigencia()));
				}
				if (d.getDtFimVigencia() != null){
					doacaoDTO.setDataFimFormatada(Util.formataDataSemHora(d.getDtFimVigencia()));
				}
				if (d.getOrgaoResponsavel() != null){
					doacaoDTO.setOrgao(d.getOrgaoResponsavel().getDescricao());
				}
				listaDoacaoStr.add(doacaoDTO);
			}
			return listaDoacaoStr;
		}catch (ApplicationException ae) {
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao listar Doação" }, e, ApplicationException.ICON_ERRO);
		}
	}

	
	/**
	 * Popular TransferenciaDTO para exibir no relatorio.<br>
	 * @author ginaalmeida
	 * @since 02/08/2011
	 * @param transferencia : Transferencia
	 * @return List<TransferenciaDTO>
	 * @throws ApplicationException 
	 */
	public static List<TransferenciaDTO> listarTermoTransferencia(Transferencia transferencia) throws ApplicationException {
		
		try {
			
			List<TransferenciaDTO> listaTransferencia = new ArrayList<TransferenciaDTO>();
			
			TransferenciaDTO transferenciaDTO = new TransferenciaDTO();
			
			if(transferencia.getDtInicioVigencia() != null){
				Calendar cal = new GregorianCalendar();
				cal.setTime(transferencia.getDtInicioVigencia());
				int ano = cal.get(Calendar.YEAR);
				transferenciaDTO.setNumeroAnoTermo(transferencia.getCodTransferencia()+"/" + ano);
			} else{
				transferenciaDTO.setNumeroAnoTermo(transferencia.getCodTransferencia().toString());
			}
			
			transferenciaDTO.setNumeroTermo(transferencia.getNumeroTermo());
			transferenciaDTO.setCodBemImovel(transferencia.getBemImovel().getNrBemImovel());
			if (transferencia.getOrgaoCedente() != null) {
				transferenciaDTO.setCedente(transferencia.getOrgaoCedente().getSiglaDescricao());
			} else {
				if (transferencia.getInstituicao() != null && transferencia.getInstituicao().getCodInstituicao() > 0) {
					transferenciaDTO.setCedente(transferencia.getInstituicao().getSiglaDescricao());	
				}
			}
			transferenciaDTO.setCessionario(transferencia.getOrgaoCessionario().getSiglaDescricao());
			transferenciaDTO.setMotivoRevogacao(StringUtils.isNotBlank(transferencia.getMotivoRevogacao()) ? transferencia.getMotivoRevogacao() : "");
			transferenciaDTO.setNumOficio(transferencia.getNrOficio() != null ? transferencia.getNrOficio().toString() : "");

			//listar Todos os itens transferidos
			List<ItemTransferenciaDTO> listaItemTransferencia = new ArrayList<ItemTransferenciaDTO>();
			for (ItemTransferencia itemTransferencia : transferencia.getListaItemTransferencia()) {
				
				ItemTransferenciaDTO itemTransferenciaDTO = new ItemTransferenciaDTO();
				if (itemTransferencia.getEdificacao() != null) {
					itemTransferenciaDTO.setEdificacao(itemTransferencia.getEdificacao().getEspecificacao());
				}
				itemTransferenciaDTO.setCaracteristica(itemTransferencia.getCaracteristica());
				itemTransferenciaDTO.setSituacao(itemTransferencia.getSituacaoDominial());
				itemTransferenciaDTO.setUtilizacao(itemTransferencia.getUtilizacao());
				itemTransferenciaDTO.setObservacao(itemTransferencia.getObservacao());
				
				listaItemTransferencia.add(itemTransferenciaDTO);
			}
			transferenciaDTO.setListaItemTransferenciaDTO(listaItemTransferencia);
			
			if(transferencia.getDataRegistro() != null){
				transferenciaDTO.setDataPorExtenso("Curitiba, " + Data.formataData(transferencia.getDataRegistro(), "dd 'de' MMMM 'de' yyyy") + ".");
				transferenciaDTO.setDataRegistre(Data.formataData(transferencia.getDataRegistro(), "dd/MM/yyyy"));
			} else{
				transferenciaDTO.setDataPorExtenso("Curitiba, " + Data.formataData(new Date(), "dd 'de' MMMM 'de' yyyy") + ".");
				transferenciaDTO.setDataRegistre(Data.formataData(new Date(), "dd/MM/yyyy"));
			}
			
			
			List<AssinaturaTransferencia> listaAssinaturas = Util.setToList(transferencia.getListaAssinaturaTransferencia());
			for (AssinaturaTransferencia assinaturaTransferencia : listaAssinaturas) {
				//Sessao de Assinatura
				if(!assinaturaTransferencia.getAssinatura().getCargoAssinatura().getDescricao().contains("CPE")){   
					
					AssinaturaDTO assinaturaDTO = new AssinaturaDTO();
					assinaturaDTO.setNome(assinaturaTransferencia.getAssinatura().getNome());
					assinaturaDTO.setCargo(assinaturaTransferencia.getAssinatura().getCargoAssinatura().getDescricao());
					assinaturaDTO.setOrgao(assinaturaTransferencia.getAssinatura().getOrgao().getSiglaDescricao());
					
					transferenciaDTO.getListaAssinaturaDTO().add(assinaturaDTO);
					
				//Sessao de Assinatura CPE
				} else if(assinaturaTransferencia.getAssinatura().getCargoAssinatura().getDescricao().contains("CPE")){
					
					AssinaturaDTO assinaturaDTO = new AssinaturaDTO();
					assinaturaDTO.setNome(assinaturaTransferencia.getAssinatura().getNome());
					assinaturaDTO.setCargo(assinaturaTransferencia.getAssinatura().getCargoAssinatura().getDescricao());
					assinaturaDTO.setOrgao(assinaturaTransferencia.getAssinatura().getOrgao().getSiglaDescricao());
					
					transferenciaDTO.getListaAssinaturaCpeDTO().add(assinaturaDTO);
					
				}
			}
			
			if (transferencia.getTextoDocInformacao() != null && transferencia.getTextoDocInformacao().trim().length() > 0) {
				DocInformacaoDTO docDTO = new DocInformacaoDTO();
				docDTO.setNumeroTermo(transferencia.getNumeroTermo());
				docDTO.setCodBemImovel(transferencia.getBemImovel().getCodBemImovel());
				docDTO.setDataPorExtenso("Curitiba, " + Data.formataData(new Date(), "dd 'de' MMMM 'de' yyyy") + ".");
				docDTO.setSiglaCessionario(transferencia.getOrgaoCessionario().getSigla());
				docDTO.setProtocolo(transferencia.getProtocoloFormatado());
				docDTO.setTextoDocInformacao(transferencia.getTextoDocInformacao());

				if (transferencia.getListaAssinaturaDocTransferencia() != null && transferencia.getListaAssinaturaDocTransferencia().size() > 0) {
					Set<AssinaturaDocTransferencia> lista = transferencia.getListaAssinaturaDocTransferencia();
					for (AssinaturaDocTransferencia assinaturaDoc : lista) {
						Assinatura assinatura = CadastroFacade.obterAssinatura(assinaturaDoc.getAssinatura().getCodAssinatura());
						
						if (assinatura.getIndResponsavelMaximo()) {
							docDTO.setAssinaturaRespMaximo(assinatura.getNome());
							docDTO.setCargoRespMaximo(assinatura.getCargoAssinatura().getDescricao());
						} else {
							AssinaturaDTO assinaturaDTO = new AssinaturaDTO();
							assinaturaDTO.setNome(assinatura.getNome());
							assinaturaDTO.setCargo(assinatura.getCargoAssinatura().getDescricao());
							docDTO.getListaAssinaturaDTO().add(assinaturaDTO);
						}
					}
				}
				transferenciaDTO.getListaDocInformacaoDTO().add(docDTO);
			}

			listaTransferencia.add(transferenciaDTO);
			
			return listaTransferencia;
			
		} catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Termo de Transferência"}, e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Popular DoacaoDTO para exibir no relatorio.<br>
	 * @author ginaalmeida
	 * @since 01/08/2011
	 * @param doacao : Doacao
	 * @return List<DoacaoDTO>
	 * @throws ApplicationException 
	 */
	public static List<DoacaoDTO> listarTermoDoacao(Doacao doacao) throws ApplicationException {
		
		try {
			
			List<DoacaoDTO> listaDoacao = new ArrayList<DoacaoDTO>();
			
			DoacaoDTO doacaoDTO = new DoacaoDTO();
			
			if(doacao.getDtInicioVigencia() != null){
				Calendar cal = new GregorianCalendar();
				cal.setTime(doacao.getDtInicioVigencia());
				int ano = cal.get(Calendar.YEAR);
				doacaoDTO.setNumeroAnoTermo(doacao.getCodDoacao()+"/" + ano);
			} else{
				doacaoDTO.setNumeroAnoTermo(doacao.getCodDoacao().toString());
			}
			
			doacaoDTO.setNumeroTermo(doacao.getNumeroTermo());
			doacaoDTO.setCodBemImovel(doacao.getBemImovel().getNrBemImovel());
			if (doacao.getLeiBemImovel() != null) {
				doacaoDTO.setNumeroLei(doacao.getLeiBemImovel().getNumero().toString());
				doacaoDTO.setDataPublicacaoLei(Data.formataData(doacao.getLeiBemImovel().getDataAssinatura(), "dd/MM/yyyy"));
				doacaoDTO.setNumeroDioe(doacao.getLeiBemImovel().getNrDioe().toString());	
				doacaoDTO.setDataPublicacaoDioe(Data.formataData(doacao.getLeiBemImovel().getDataPublicacao(), "dd/MM/yyyy"));
			} else {
				if (doacao.getNrProjetoLei() != null) {
					doacaoDTO.setNumeroLei(doacao.getNrProjetoLei().toString());
				}
			}
			if (doacao.getOrgaoProprietario() != null){
				doacaoDTO.setCedente(doacao.getOrgaoProprietario().getDescricao());	
			} else {
				if (doacao.getInstituicao() != null && doacao.getInstituicao().getCodInstituicao() > 0) {
					doacaoDTO.setCedente(doacao.getInstituicao().getSiglaDescricao());	
				}
			}
			doacaoDTO.setCessionario(doacao.getOrgaoResponsavel().getDescricao());
			doacaoDTO.setMotivoRevogacao(StringUtils.isNotBlank(doacao.getMotivoRevogacao()) ? doacao.getMotivoRevogacao() : "");
			doacaoDTO.setNumOficio(doacao.getNrOficio() != null ? doacao.getNrOficio().toString() : "");

			//listarTodos os itens doados
			List<ItemDoacaoDTO> listaItemDoacao = new ArrayList<ItemDoacaoDTO>();
			for (ItemDoacao itemDoacao : doacao.getListaItemDoacao()) {
				
				ItemDoacaoDTO itemDoacaoDTO = new ItemDoacaoDTO();
				if (itemDoacao.getEdificacao() != null) {
					itemDoacaoDTO.setEdificacao(itemDoacao.getEdificacao().getEspecificacao());
				}
				itemDoacaoDTO.setUtilizacao(itemDoacao.getUtilizacao());
				itemDoacaoDTO.setObservacao(itemDoacao.getObservacao());
				
				listaItemDoacao.add(itemDoacaoDTO);
			}
			doacaoDTO.setListaItemDoacaoDTO(listaItemDoacao);
			
			if(doacao.getDataRegistro() != null){
				doacaoDTO.setDataPorExtenso("Curitiba, " + Data.formataData(doacao.getDataRegistro(), "dd 'de' MMMM 'de' yyyy") + ".");
				doacaoDTO.setDataRegistre(Data.formataData(doacao.getDataRegistro(), "dd/MM/yyyy"));
			} else{
				doacaoDTO.setDataPorExtenso("Curitiba, " + Data.formataData(new Date(), "dd 'de' MMMM 'de' yyyy") + ".");
				doacaoDTO.setDataRegistre(Data.formataData(new Date(), "dd/MM/yyyy"));
			}
			
			List<AssinaturaDoacao> listaAssinaturas = Util.setToList(doacao.getListaAssinaturaDoacao());
			for (AssinaturaDoacao assinaturaDoacao : listaAssinaturas) {
				//Sessao de Assinatura
				if(!assinaturaDoacao.getAssinatura().getCargoAssinatura().getDescricao().contains("CPE")){
					AssinaturaDTO assinaturaDTO = new AssinaturaDTO();
					assinaturaDTO.setNome(assinaturaDoacao.getAssinatura().getNome());
					assinaturaDTO.setCargo(assinaturaDoacao.getAssinatura().getCargoAssinatura().getDescricao());
					assinaturaDTO.setOrgao(assinaturaDoacao.getAssinatura().getOrgao().getSiglaDescricao());
					doacaoDTO.getListaAssinaturaDTO().add(assinaturaDTO);
				//Sessao de Assinatura CPE
				} else if(assinaturaDoacao.getAssinatura().getCargoAssinatura().getDescricao().contains("CPE")){
					AssinaturaDTO assinaturaDTO = new AssinaturaDTO();
					assinaturaDTO.setNome(assinaturaDoacao.getAssinatura().getNome());
					assinaturaDTO.setCargo(assinaturaDoacao.getAssinatura().getCargoAssinatura().getDescricao());
					assinaturaDTO.setOrgao(assinaturaDoacao.getAssinatura().getOrgao().getSiglaDescricao());
					doacaoDTO.getListaAssinaturaCpeDTO().add(assinaturaDTO);
				}
			} 
			
			listaDoacao.add(doacaoDTO);
			
			return listaDoacao;
			
		} catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Termo de Doação" }, e, ApplicationException.ICON_ERRO);
		}
	}
	
	
	/**
	 * Popular CessaoDeUsoDTO para exibir no relatorio.<br>
	 * @author ginaalmeida
	 * @since 01/08/2011
	 * @param cessaoDeUso : CessaoDeUso
	 * @return List<CessaoDeUsoDTO>
	 * @throws ApplicationException 
	 */
	public static List<CessaoDeUsoDTO> listarTermoCessaoDeUso(CessaoDeUso cessaoDeUso) throws ApplicationException {
		
		try {
			
			List<CessaoDeUsoDTO> listaCessao = new ArrayList<CessaoDeUsoDTO>();
			
			CessaoDeUsoDTO cessaoDTO = new CessaoDeUsoDTO();
			
			if(cessaoDeUso.getDataInicioVigencia() != null){
				Calendar cal = new GregorianCalendar();
				cal.setTime(cessaoDeUso.getDataInicioVigencia());
				int ano = cal.get(Calendar.YEAR);
				cessaoDTO.setNumeroAnoTermo(cessaoDeUso.getCodCessaoDeUso()+"/" + ano);
			} else{
				cessaoDTO.setNumeroAnoTermo(cessaoDeUso.getCodCessaoDeUso().toString());
			}

			cessaoDTO.setNumeroTermo(cessaoDeUso.getNumeroTermo());
			cessaoDTO.setCodBemImovel(cessaoDeUso.getBemImovel().getNrBemImovel());
			cessaoDTO.setProtocolo(cessaoDeUso.getProtocoloFormatado());

			if (cessaoDeUso.getLeiBemImovel() != null) {
				cessaoDTO.setNumeroLei(cessaoDeUso.getLeiBemImovel().getNumero().toString());
				cessaoDTO.setDataPublicacaoLei(Data.formataData(cessaoDeUso.getLeiBemImovel().getDataAssinatura(), "dd/MM/yyyy"));
				cessaoDTO.setNumeroDioe(cessaoDeUso.getLeiBemImovel().getNrDioe().toString());
				cessaoDTO.setDataPublicacaoDioe(Data.formataData(cessaoDeUso.getLeiBemImovel().getDataPublicacao(), "dd/MM/yyyy"));
			} else {
				cessaoDTO.setNumeroLei(cessaoDeUso.getNumeroProjetoDeLei());
			}
			if (cessaoDeUso.getOrgaoCedente() != null) {
				cessaoDTO.setCedente(cessaoDeUso.getOrgaoCedente().getDescricao());
			} else {
				if (cessaoDeUso.getInstituicao() != null && cessaoDeUso.getInstituicao().getCodInstituicao() > 0) {
					cessaoDTO.setCedente(cessaoDeUso.getInstituicao().getSiglaDescricao());	
				}
			}
			cessaoDTO.setCessionario(cessaoDeUso.getOrgaoCessionario().getDescricao());
			cessaoDTO.setMotivoRevogadaDevolucao(StringUtils.isNotBlank(cessaoDeUso.getMotivoRevogacaoDevolucao()) ? cessaoDeUso.getMotivoRevogacaoDevolucao() : "");
			
			//listar todos os itemCessaoDeUso
			List<ItemCessaoDeUsoDTO> listaItemCessao = new ArrayList<ItemCessaoDeUsoDTO>();
			for (ItemCessaoDeUso itemCessao : cessaoDeUso.getListaItemCessaoDeUso()) {
				
				ItemCessaoDeUsoDTO itemCessaoDeUsoDTO = new ItemCessaoDeUsoDTO();
				if (itemCessao.getEdificacao() != null) {
					itemCessaoDeUsoDTO.setEdificacao(StringUtils.isNotBlank(itemCessao.getEdificacao().getEspecificacao()) ? itemCessao.getEdificacao().getEspecificacao() : "");
				}
				itemCessaoDeUsoDTO.setAreaEmMetros(itemCessao.getAreaMetroQuadradoFormatado());
				itemCessaoDeUsoDTO.setAreaEmPercentual(itemCessao.getAreaPercentualFormatado());
				itemCessaoDeUsoDTO.setCaracteristica(itemCessao.getCaracteristica());
				itemCessaoDeUsoDTO.setUtilizacao(itemCessao.getUtilizacao());
				itemCessaoDeUsoDTO.setObservacao(StringUtils.isNotBlank(itemCessao.getObservacao()) ? itemCessao.getObservacao() : "");
				itemCessaoDeUsoDTO.setSituacao(StringUtils.isNotBlank(itemCessao.getSituacaoDominial()) ? itemCessao.getSituacaoDominial() : "");
				
				listaItemCessao.add(itemCessaoDeUsoDTO);
			}
			cessaoDTO.setListaItemCessaoDeUsoDTO(listaItemCessao);
			
			if(cessaoDeUso.getDataRegistro() != null){
				cessaoDTO.setDataPorExtenso("Curitiba, " + Data.formataData(cessaoDeUso.getDataRegistro(), "dd 'de' MMMM 'de' yyyy") + ".");
				cessaoDTO.setDataRegistre(Data.formataData(cessaoDeUso.getDataRegistro(), "dd/MM/yyyy"));
			} else{
				cessaoDTO.setDataPorExtenso("Curitiba, " + Data.formataData(new Date(), "dd 'de' MMMM 'de' yyyy") + ".");
				cessaoDTO.setDataRegistre(Data.formataData(new Date(), "dd/MM/yyyy"));
			}
			
			
			List<AssinaturaCessaoDeUso> listaAssinaturasCessao = Util.setToList(cessaoDeUso.getListaAssinaturaCessaoDeUso());
			for (AssinaturaCessaoDeUso assinaturaCessao : listaAssinaturasCessao) {
				
				//Sessao de Assinatura
				if(!assinaturaCessao.getAssinatura().getCargoAssinatura().getDescricao().contains("CPE")){
					
					AssinaturaDTO assinaturaDTO = new AssinaturaDTO();
					assinaturaDTO.setNome(assinaturaCessao.getAssinatura().getNome());
					assinaturaDTO.setCargo(assinaturaCessao.getAssinatura().getCargoAssinatura().getDescricao());
					assinaturaDTO.setOrgao(assinaturaCessao.getAssinatura().getOrgao().getSiglaDescricao());
					
					cessaoDTO.getListaAssinaturaDTO().add(assinaturaDTO);
					
				//Sessao de Assinatura CPE
				} else if(assinaturaCessao.getAssinatura().getCargoAssinatura().getDescricao().contains("CPE")){
					
					AssinaturaDTO assinaturaDTO = new AssinaturaDTO();
					assinaturaDTO.setNome(assinaturaCessao.getAssinatura().getNome());
					assinaturaDTO.setCargo(assinaturaCessao.getAssinatura().getCargoAssinatura().getDescricao());
					assinaturaDTO.setOrgao(assinaturaCessao.getAssinatura().getOrgao().getSiglaDescricao());
					
					cessaoDTO.getListaAssinaturaCpeDTO().add(assinaturaDTO);
					
				}
			}
			
			listaCessao.add(cessaoDTO);
			
			return listaCessao;
			
		} catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Termo de Cessão De Uso" }, e, ApplicationException.ICON_ERRO);
		}
	}

	
	/**
	 * Listar Doacao nao finalizada.<br>
	 * @author oksana
	 * @since 08/07/2011
	 * @param  ParametroAgenda
	 * @return List<String>
	 * @throws ApplicationException 
	 */
	public static List<DoacaoDTO> listarDoacaoNaoFinalizada(Instituicao instituicao) throws ApplicationException {
		try {
			List<DoacaoDTO> listaDoacaoStr = new ArrayList<DoacaoDTO>();
			StatusTermo status = new StatusTermo();
			status.setCodStatusTermo(0);
			List<Doacao> listaDoacao = hibernateFactory.getDoacaoDAO().listarDoacaoPorStatus(status, instituicao);

			for (Doacao v: listaDoacao){
				DoacaoDTO doacaoDTO = new DoacaoDTO();
				doacaoDTO.setCodBemImovel(v.getBemImovel().getCodBemImovel());
				if (v.getProtocolo() != null && v.getProtocolo().trim().length() > 0) {
					doacaoDTO.setProtocolo(v.getProtocoloFormatado());
				} else {
					doacaoDTO.setProtocolo(" - ");
				}
				doacaoDTO.setCodDoacao(v.getCodDoacao());
				if (v.getDtInicioVigencia() != null){
					doacaoDTO.setDataInicioFormatada((Util.formataDataSemHora(v.getDtInicioVigencia())));	
				}else{
					doacaoDTO.setDataInicioFormatada(" - ");
				}
				
				if (v.getDtFimVigencia() != null){
					doacaoDTO.setDataFimFormatada((Util.formataDataSemHora(v.getDtFimVigencia())));	
				}else{
					doacaoDTO.setDataFimFormatada(" - ");
				}
				if (v.getOrgaoResponsavel() != null){
					doacaoDTO.setOrgao(v.getOrgaoResponsavel().getDescricao());	
				}else{
					doacaoDTO.setOrgao(" - ");
				}
				if(v.getDtInicioVigencia() != null){
					Calendar cal = new GregorianCalendar();
					cal.setTime(v.getDtInicioVigencia());
					int ano = cal.get(Calendar.YEAR);
					doacaoDTO.setNumeroAnoTermo(v.getCodDoacao()+"/" + ano);
				} else{
					doacaoDTO.setNumeroAnoTermo(v.getCodDoacao().toString());
				}

				listaDoacaoStr.add(doacaoDTO);
			}
			return listaDoacaoStr;
		}catch (ApplicationException ae) {
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao listar Doação Não Finalizada" }, e, ApplicationException.ICON_ERRO);
		}
	}

	
	/**
	 * Atualizar Doacao vencida.<br>
	 * @author oksana
	 * @since 08/07/2011
	 * @param  DoacaoDTO
	 * @return 
	 * @throws ApplicationException 
	 */
	public static void atualizarDoacaoVencida(List<DoacaoDTO> listaDoacaoDTO, StatusTermo status) throws ApplicationException {
		try {
			for (DoacaoDTO d: listaDoacaoDTO){
				Doacao doacao = hibernateFactory.getDoacaoDAO().obter(d.getCodDoacao());
				if (doacao != null){
					doacao.setDtFimVigencia(new Date());
					doacao.setStatusTermo(status);
					hibernateFactory.getDoacaoDAO().alterar(doacao);
				}
			}
		}catch (ApplicationException ae) {
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao atualizar Doação Vencida" }, e, ApplicationException.ICON_ERRO);
		}
	}
	

	public static Collection<ItemComboDTO> listarStatusTermoCombo() throws ApplicationException {
		Collection<StatusTermo> lista = hibernateFactory.getStatusTermoDAO().listar(new StatusTermo(), new String[] { "descricao", "asc" });
		Collection<ItemComboDTO> ret = new ArrayList<ItemComboDTO>();
		for (StatusTermo statusTermo : lista) {
			ItemComboDTO obj = new ItemComboDTO();
			obj.setCodigo(String.valueOf(statusTermo.getCodStatusTermo()));
			obj.setDescricao(statusTermo.getDescricao());
			ret.add(obj);
		}
		return ret;
	}
	
	/**
	 * Realiza o cálculo de metros quadrados/percentual de acordo com os dados informados
	 * @param tipo: Terreno ou Edificação 
	 * @param metros
	 * @param percentual
	 * @param objeto: Doação, Transferência.
	 * @param codEdificacao
	 * @param codBemImovel
	 * @return ItemDTO: com os valores de percentual e metros preenchidos.
	 * @throws ApplicationException 
	 */
	public static ItemDTO calcularPercentualMetros(String tipo, String metros, String percentual, String objeto, String codEdificacao,
			String codBemImovel) throws ApplicationException {
		ItemDTO obj = new ItemDTO();
		
		if (!StringUtil.stringNotNull(metros) && !StringUtil.stringNotNull(percentual)) {			
			throw new ApplicationException("AVISO.52", new String[]{objeto});
		} 
		
		Double auxMetros = new Double(0);				
		if (!metros.isEmpty()){
			auxMetros = Valores.converterStringParaBigDecimal(metros).doubleValue();  
		}

		Double auxPercentual = new Double(0);				
		if (!percentual.isEmpty()){
			percentual = percentual.replaceAll(",", ".");  
			auxPercentual = Double.parseDouble(percentual);  
		}
		
		//se percentual informado for > 100
		if(auxPercentual > 100) {
			throw new ApplicationException("AVISO.53", new String[]{objeto});
		}
		
		BemImovel bemImovel = CadastroFacade.obterBemImovel(Integer.valueOf(codBemImovel));
		Double valor = new Double(0);				
		if (bemImovel.getSomenteTerreno().trim().equalsIgnoreCase("S") || tipo.equalsIgnoreCase("3")) {
			valor = bemImovel.getAreaTerreno().doubleValue();
		} else {
			valor = bemImovel.getAreaConstruida().doubleValue();
			if (valor == 0) {
				valor = bemImovel.getAreaTerreno().doubleValue();
			}
		}
		if (codEdificacao != null) {
			Edificacao edificacao = CadastroFacade.obterEdificacao(Integer.valueOf(codEdificacao));
			if (bemImovel.getSomenteTerreno().trim().equalsIgnoreCase("N") || tipo.equalsIgnoreCase("2")) {
				valor = edificacao.getAreaConstruida().doubleValue();
			}
		}

		//se metros informado for > o valor recuperado
		if (auxMetros.compareTo(valor) > 1) {
			throw new ApplicationException("AVISO.54", new String[]{objeto});
		}

		Double ocupacaoMetroQuadrado = new Double(0);
		Double ocupacaoPercentual =  new Double(0);	
		if (StringUtil.stringNotNull(metros) && auxMetros > 0) {
			if ((auxMetros != null) && (valor != null) || (auxMetros != 0 ) || (valor != 0 )){
				ocupacaoPercentual = auxMetros/valor;						
			}					
			ocupacaoPercentual = Valores.arredondar((ocupacaoPercentual * 100), 5);
			if (ocupacaoPercentual > 100) {
				ocupacaoPercentual = new Double(100);
			}
			obj.setPercentual(Valores.arredondar(ocupacaoPercentual, 5));
			
			ocupacaoMetroQuadrado = ocupacaoPercentual/100;
			ocupacaoMetroQuadrado = Valores.arredondar((ocupacaoMetroQuadrado * valor), 2);
			obj.setMetros(Valores.arredondar(ocupacaoMetroQuadrado, 2));
		}

		if (StringUtil.stringNotNull(percentual) && auxPercentual > 0) {
			if (auxPercentual!=null){
				ocupacaoMetroQuadrado = auxPercentual/100;
				ocupacaoMetroQuadrado = Valores.arredondar((ocupacaoMetroQuadrado * valor), 2);
			}
			obj.setMetros(Valores.arredondar(ocupacaoMetroQuadrado, 2));
			obj.setPercentual(Valores.arredondar(auxPercentual, 5));
		}

		return obj;
	}

	/**
	 * Lista paginada de Doacao.
	 * 
	 * @param pag - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarDoacao(Pagina pag, Doacao doacao) throws ApplicationException {

		try {
			if (pag.getTotalRegistros() == 0) {
				pag.setTotalRegistros(hibernateFactory.getDoacaoDAO().listar(
						null, null, doacao).size());
			}
			pag.setRegistros(hibernateFactory.getDoacaoDAO().listar(
					pag.getQuantidade(), pag.getPaginaAtual(), doacao));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Doação" }, e);
		}

		return pag;
	}

	/**
	 * Verifica se exite uma outra doação para o mesmo Bem Imóvel e Status do Termo.
	 * @param status
	 * @return
	 * @throws ApplicationException
	 */
	public static List<Doacao> verificarDoacaoByBemImovelStatusTermo(Doacao doacao) throws ApplicationException {
		try {
			return hibernateFactory.getDoacaoDAO().listarDoacao(doacao);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "doação" }, e);
		}
	}

	/**
	 * Salva a doacao e a lei bem imovel
	 * @param doacao
	 * @param leiBemImovel
	 * @return
	 * @throws ApplicationException
	 */
	public static Doacao salvarDoacaoLeiBemImovel(Doacao doacao, LeiBemImovel leiBemImovel) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			
			if (leiBemImovel.getCodLeiBemImovel() == null){
				leiBemImovel = hibernateFactory.getLeiBemImovelDAO().salvarLeiBemImovel(leiBemImovel);
				doacao.setLeiBemImovel(leiBemImovel);
			}
			if (doacao.getCodDoacao() == null){
				doacao = hibernateFactory.getDoacaoDAO().salvarDoacao(doacao);
			}else{
				hibernateFactory.getDoacaoDAO().alterar(doacao);
			}

			HibernateUtil.commitTransaction();
			return doacao;
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarDoacaoLeiBemImovel()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarDoacaoLeiBemImovel()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao incluir Doação e Lei Bem Imovel" }, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Salva a doacao
	 * @param doacao
	 * @return
	 * @throws ApplicationException
	 */
	public static Doacao salvarDoacao(Doacao doacao) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			
			if (doacao.getCodDoacao() == null){
				doacao = hibernateFactory.getDoacaoDAO().salvarDoacao(doacao);
			}else{
				hibernateFactory.getDoacaoDAO().alterar(doacao);
			}
			HibernateUtil.commitTransaction();
			return doacao;
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarDoacao()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarDoacao()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao incluir Doação" }, e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Salva a transferencia.<BR>
	 * @param transferencia : Transferencia
	 * @return Transferencia
	 * @throws ApplicationException
	 */
	public static Transferencia salvarTransferencia(Transferencia transferencia) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			
			if (transferencia.getCodTransferencia() == null){
				transferencia = hibernateFactory.getTransferenciaDAO().salvarTransferencia(transferencia);
			}else{
				hibernateFactory.getTransferenciaDAO().alterar(transferencia);
			}
			HibernateUtil.commitTransaction();
			return transferencia;
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarTransferencia()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarTransferencia()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] {"ao incluir Transferência"}, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Obtem a Doação de acordo com o código informado.
	 * @param codDoacao
	 * @return
	 * @throws ApplicationException
	 */
	public static Doacao obterDoacaoCompleto(Integer codDoacao) throws ApplicationException {
		return hibernateFactory.getDoacaoDAO().obterDoacaoCompleto(codDoacao);
	}

	/**
	 * Salva o item da Doação
	 * @param itemDoacao
	 * @throws ApplicationException
	 */
	public static void salvarItemDoacao(ItemDoacao itemDoacao) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			if (itemDoacao.getTipo().equals(Integer.valueOf(1))){
				for (ItemDoacao item : itemDoacao.getDoacao().getListaItemDoacao()) {
					hibernateFactory.getItemDoacaoDAO().excluir(item);
				}
			}
			hibernateFactory.getItemDoacaoDAO().salvar(itemDoacao);
			HibernateUtil.commitTransaction();
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarItemDoacao()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarItemDoacao()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao incluir Item da Doação" }, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Salva a Assinatura da Doação
	 * @param itemDoacao
	 * @throws ApplicationException
	 */
	public static void salvarAssinaturaDoacao(AssinaturaDoacao assinaturaDoacao) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			hibernateFactory.getAssinaturaDoacaoDAO().salvar(assinaturaDoacao);
			HibernateUtil.commitTransaction();
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarAssinaturaDoacao()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarAssinaturaDoacao()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao incluir Assinatura da Doação" }, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Excluir o item da Doação
	 * @param itemDoacao
	 * @throws ApplicationException
	 */
	public static void excluirItemDoacao(ItemDoacao itemDoacao) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			hibernateFactory.getItemDoacaoDAO().excluir(itemDoacao);
			HibernateUtil.commitTransaction();
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirItemDoacao()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirItemDoacao()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao excluir Item da Doação" }, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Excluir a Assinatura da Doação
	 * @param itemDoacao
	 * @throws ApplicationException
	 */
	public static void excluirAssinaturaDoacao(AssinaturaDoacao assinaturaDoacao) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			hibernateFactory.getAssinaturaDoacaoDAO().excluir(assinaturaDoacao);
			HibernateUtil.commitTransaction();
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirAssinaturaDoacao()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirAssinaturaDoacao()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao excluir Assinatura da Doação" }, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Lista os itens da doação
	 * @param pag
	 * @param codDoacao
	 * @return
	 * @throws ApplicationException
	 */
	public static Object listarItemDoacao(Pagina pag, Integer codDoacao) throws ApplicationException {
		try {
			Collection<ItemDoacao> itensLista = hibernateFactory.getItemDoacaoDAO().listar(codDoacao);
			pag.setQuantidade(itensLista.size());
			pag.setRegistros(itensLista);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Item da Doação" }, e);
		}

		return pag;
	}

	/**
	 * Lista as assinaturas da doação
	 * @param pag
	 * @param codDoacao
	 * @return
	 * @throws ApplicationException
	 */
	public static Object listarAssinaturaDoacao(Pagina pag, Integer codDoacao) throws ApplicationException {
		try {
			Collection<AssinaturaDoacao> itensLista = hibernateFactory.getAssinaturaDoacaoDAO().listar(codDoacao);
			pag.setQuantidade(itensLista.size());
			pag.setRegistros(itensLista);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Assinatura da Doação" }, e);
		}

		return pag;
	}

	public static Collection<AssinaturaDoacao> verificarDuplicidadeAssinaturaDoacao(
			AssinaturaDoacao assinaturaDoacao) throws ApplicationException {
		try {
			return hibernateFactory.getAssinaturaDoacaoDAO().listaVerificacao(assinaturaDoacao);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "assinatura" }, e);
		}
	}

	public static ItemDoacao obterItemDoacao(Integer codItemDoacao) throws ApplicationException {
		return hibernateFactory.getItemDoacaoDAO().obter(codItemDoacao);
	}

	public static AssinaturaDoacao obterAssinaturaDoacao(Integer codAssinaturaDoacao) throws ApplicationException {
		return hibernateFactory.getAssinaturaDoacaoDAO().obter(codAssinaturaDoacao);
	}
	
	public static Collection<ItemDoacao> verificarDuplicidadeItemDoacao(ItemDoacao itemDoacao) throws ApplicationException {
		try {
			return hibernateFactory.getItemDoacaoDAO().listar(itemDoacao.getDoacao().getCodDoacao());
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "item" }, e);
		}
	}

	/**
	 * Realiza a exclusão da doação e dos itens vinculados.
	 * @param doacao
	 * @throws ApplicationException
	 */
	public static void excluirDoacao(Doacao doacao) throws ApplicationException {
		try {
			// verifica se existe integridade referencial
			Doacao aux = hibernateFactory.getDoacaoDAO().obter(doacao.getCodDoacao());
			HibernateUtil.currentTransaction();
			if (aux.getStatusTermo().getCodStatusTermo().equals(Dominios.statusTermo.RASCUNHO.getIndex())) {
				for (AssinaturaDoacao assinaturaDoacao : doacao.getListaAssinaturaDoacao()) {
					hibernateFactory.getAssinaturaDoacaoDAO().excluir(assinaturaDoacao);
				}
				for (ItemDoacao itemDoacao : doacao.getListaItemDoacao()) {
					hibernateFactory.getItemDoacaoDAO().excluir(itemDoacao);
				}
				hibernateFactory.getDoacaoDAO().excluir(doacao);
			} else {
				salvarDoacao(doacao);
			}
			
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirDoacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirDoacao()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Doação" }, ex, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Lista vistoriadores.<br>
	 * @author Bruno J. Fernandes	
	 * @since 07/07/2011
	 * @param codParametroVistoria: Integer
	 * @param descricao: String
	 * @return Boolean
	 * @throws ApplicationException 
	 */
	public static Pagina listarVistoriador(Pagina pag, String cpf, String nome, Integer codInstituicao) throws ApplicationException {		
		try {
			if (pag.getTotalRegistros() == 0) {
				pag.setTotalRegistros(hibernateFactory.getVistoriadorDAO().obterQuantidadeLista(pag.getQuantidade(), pag.getPaginaAtual(), cpf, nome, codInstituicao));
			}
			pag.setRegistros(hibernateFactory.getVistoriadorDAO().listar(pag.getQuantidade(), pag.getPaginaAtual(), cpf, nome, codInstituicao));						
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Vistoriador" }, e);
		}
		return pag;		
	}
	
	public static Boolean verificaVistoriadorDuplicado(String cpfNovoVistoriador, Integer codInstituicao) throws ApplicationException {

		try {
			Boolean existe = false;
			if (cpfNovoVistoriador != null && !cpfNovoVistoriador.isEmpty()){
				existe = hibernateFactory.getVistoriadorDAO().existeVistoriador(cpfNovoVistoriador, codInstituicao);
			}
			return existe;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { " Vistoriador" }, e);
		}
	}
	
	/**
	 * Salva objeto Vistoriador.
	 * 
	 * @param vistoriador
	 *            a ser salvo.
	 * @throws Exception 
	 */
	public static void salvarVistoriador(Vistoriador vistoriador) throws Exception {
		try {	
			HibernateUtil.currentTransaction();  
			hibernateFactory.getVistoriadorDAO().salvar(vistoriador);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
				HibernateUtil.rollbackTransaction();
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarVistoriador()!"}, ApplicationException.ICON_AVISO);
		} catch (Exception ex) {
				HibernateUtil.rollbackTransaction();
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarVistoriador()!"}, ApplicationException.ICON_AVISO);
		
		}

	}
//
		
			
	/**
	 * Atualiza objeto Vistoriador.
	 * 
	 * @param vistoriador
	 *            a ser atualizado.
	 * @throws ApplicationException
	 */
	public static void alterarVistoriador(Vistoriador vistoriador) throws ApplicationException {

		try {
			HibernateUtil.currentTransaction();
			hibernateFactory.getVistoriadorDAO().alterar(vistoriador);
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarVistoriador()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;

		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em alterarVistoriador()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao alterar Vistoriador" }, ex, ApplicationException.ICON_ERRO);
		}

	}
	
	/**
	 * Remove objeto Vistoriador.
	 * 
	 * @param vistoriador
	 *            a ser removido.
	 * @throws ApplicationException
	 */	
	public static void excluirVistoriador(int codVistoriador) throws ApplicationException {
		
		try {

			HibernateUtil.currentTransaction(); 
			Vistoriador vistoriador = hibernateFactory.getVistoriadorDAO().obter(codVistoriador);
			
			if (vistoriador == null) {
				throw new ApplicationException("ERRO.2", new String[] { OperacaoFacade.class.getSimpleName() + ".excluirVistoriador()" }, 
						ApplicationException.ICON_ERRO);
			}

			Vistoria vistoria = new Vistoria();
			vistoria.setVistoriador(vistoriador);
			
			if(hibernateFactory.getVistoriaDAO().verificarExistencia(vistoria)){
				StringBuffer str = new StringBuffer();
				Collection<Integer> listarBemImovelPorVistoriador = hibernateFactory.getVistoriaDAO().listarBemImovelPorVistoriador(codVistoriador);
				
				for (Integer codBemImovel : listarBemImovelPorVistoriador){
					str.append("Bem imóvel " + codBemImovel + " - " + "(" + hibernateFactory.getVistoriaDAO().listarVistoriadlPorBemImovel(codVistoriador, codBemImovel) + "\n");
				}
			
				throw new ApplicationException("AVISO.48", new String[]{str.toString()}, ApplicationException.ICON_AVISO);
			}					
			
			hibernateFactory.getVistoriadorDAO().excluir(vistoriador);
			HibernateUtil.commitTransaction();

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirVistoriador()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirVistoriador()!"}, ApplicationException.ICON_AVISO);
			}

			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Vistoriador" }, ex, ApplicationException.ICON_ERRO);
		}
	}

	
	/**
	 * Listar CessaoDeUsoDTO.<br>
	 * @author oksana
	 * @since 02/08/2011
	 * @param  ParametroAgenda
	 * @return List<CessaoDeUsoDTO>
	 * @throws ApplicationException 
	 */
	public static List<CessaoDeUsoDTO> listarCessaoDeUso(Instituicao instituicao, ParametroAgenda parametroAgenda) throws ApplicationException {
		try {
			List<CessaoDeUsoDTO> listaCessaoDeUsoStr = new ArrayList<CessaoDeUsoDTO>();
			List<CessaoDeUso> listaCessaoDeUso = new ArrayList<CessaoDeUso>();
			if (parametroAgenda != null && parametroAgenda.getNumeroDiasVencimentoCessaoDeUso() != null && parametroAgenda.getNumeroDiasVencimentoCessaoDeUso() > 0){
				//a vencer
				listaCessaoDeUso = (List<CessaoDeUso>) hibernateFactory.getCessaoDeUsoDAO().listarVencidaAVencer(instituicao, parametroAgenda);
			}else{
				//vencidas 
				listaCessaoDeUso = (List<CessaoDeUso>) hibernateFactory.getCessaoDeUsoDAO().listarVencidaAVencer(instituicao, null);
			}
			for (CessaoDeUso d: listaCessaoDeUso){
				CessaoDeUsoDTO cessaoDeUsoDTO = new CessaoDeUsoDTO();
				cessaoDeUsoDTO.setCodCessaoDeUso(d.getCodCessaoDeUso());
				Calendar cal = new GregorianCalendar();
				cal.setTime(d.getDataInicioVigencia());
				int ano = cal.get(Calendar.YEAR);
				cessaoDeUsoDTO.setNumeroAnoTermo(d.getCodCessaoDeUso()+"/" + ano);
				cessaoDeUsoDTO.setCodBemImovel(d.getBemImovel().getCodBemImovel());
				if (d.getProtocolo() != null && d.getProtocolo().trim().length() > 0) {
					cessaoDeUsoDTO.setProtocolo(d.getProtocoloFormatado());
				} else {
					cessaoDeUsoDTO.setProtocolo(" - ");
				}
				if (d.getDataInicioVigencia() != null){
					cessaoDeUsoDTO.setDataInicioFormatada(Util.formataDataSemHora(d.getDataInicioVigencia()));
				}
				if (d.getDataFinalVigenciaPrevisao() != null){
					cessaoDeUsoDTO.setDataFimFormatada(Util.formataDataSemHora(d.getDataFinalVigenciaPrevisao()));
				}
				if (d.getOrgaoCessionario() != null){
					cessaoDeUsoDTO.setOrgao(d.getOrgaoCessionario().getDescricao());
				}
				listaCessaoDeUsoStr.add(cessaoDeUsoDTO);
			}
			return listaCessaoDeUsoStr;
		}catch (ApplicationException ae) {
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao listar Cessão De Uso" }, e, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Listar CessaoDeUso Por Status.<br>
	 * @author oksana
	 * @since 02/08/2011
	 * @param  StatusTermo
	 * @return List<CessaoDeUsoDTO>
	 * @throws ApplicationException 
	 */
	public static List<CessaoDeUsoDTO> listarCessaoDeUsoPorStatus(Instituicao instituicao, StatusTermo statusTermo) throws ApplicationException {
		try {
			List<CessaoDeUsoDTO> listaCessaoDeUsoStr = new ArrayList<CessaoDeUsoDTO>();
			List<CessaoDeUso> listaCessaoDeUso = hibernateFactory.getCessaoDeUsoDAO().listarCessaoDeUsoPorStatus(instituicao, statusTermo);

			for (CessaoDeUso c: listaCessaoDeUso){
				CessaoDeUsoDTO cessaoDeUsoDTO = new CessaoDeUsoDTO();
				cessaoDeUsoDTO.setCodCessaoDeUso(c.getCodCessaoDeUso());
				Calendar cal = new GregorianCalendar();
				if (c.getDataInicioVigencia() != null){
					cal.setTime(c.getDataInicioVigencia());	
				}else{
					cal.setTime(c.getTsInclusao());
				}
				
				int ano = cal.get(Calendar.YEAR);
				cessaoDeUsoDTO.setNumeroAnoTermo(c.getCodCessaoDeUso()+"/" + ano);
				cessaoDeUsoDTO.setCodBemImovel(c.getBemImovel().getCodBemImovel());
				if (c.getProtocolo() != null && c.getProtocolo().trim().length() > 0) {
					cessaoDeUsoDTO.setProtocolo(c.getProtocoloFormatado());
				} else {
					cessaoDeUsoDTO.setProtocolo(" - ");
				}
				if (c.getDataInicioVigencia() != null){
					cessaoDeUsoDTO.setDataInicioFormatada(Util.formataDataSemHora(c.getDataInicioVigencia()));
				}else{
					cessaoDeUsoDTO.setDataInicioFormatada(" - ");
				}
				if (c.getDataFinalVigenciaPrevisao() != null){
					cessaoDeUsoDTO.setDataFimFormatada(Util.formataDataSemHora(c.getDataFinalVigenciaPrevisao()));
				}else{
					cessaoDeUsoDTO.setDataFimFormatada(" - ");
				}
				if (c.getOrgaoCessionario() != null){
					cessaoDeUsoDTO.setOrgao(c.getOrgaoCessionario().getDescricao());
				}else{
					cessaoDeUsoDTO.setOrgao(" - ");
				}
				listaCessaoDeUsoStr.add(cessaoDeUsoDTO);
			}
			return listaCessaoDeUsoStr;
		}catch (ApplicationException ae) {
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao listar Cessão De Uso Por Status" }, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Atualizar CessaoDeUso vencida.<br>
	 * @author oksana
	 * @since 02/08/2011
	 * @param  CessaoDeUsoDTO
	 * @return 
	 * @throws ApplicationException 
	 */
	public static void atualizarCessaoDeUsoVencida(List<CessaoDeUsoDTO> listaCessaoDeUsoDTO, StatusTermo status) throws ApplicationException {
		try {
			for (CessaoDeUsoDTO d: listaCessaoDeUsoDTO){
				CessaoDeUso cessaoDeUso = hibernateFactory.getCessaoDeUsoDAO().obter(d.getCodCessaoDeUso());
				if (cessaoDeUso != null){
					cessaoDeUso.setDataFinalVigencia(new Date());
					cessaoDeUso.setStatusTermo(status);
					hibernateFactory.getCessaoDeUsoDAO().alterar(cessaoDeUso);
				}
			}
		}catch (ApplicationException ae) {
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao atualizar Cessão De Uso Vencida" }, e, ApplicationException.ICON_ERRO);
		}
	}
	

	
	/**
	 * Metodo para enviar eMail.<br>
	 * 
	 * @author jacquesotomaior
	 * @author rodrigoalbani
	 * @since 07/10/2008
	 * @since 04/07/2009
	 * @param tipoEMail
	 *            : TipoEMail
	 * @param listaEMail
	 *            : List<Email>
	 * @param listaAnexo
	 *            : List<File>
	 * @param textoAdicional
	 *            : String
	 * @param codFornecedor
	 *            : Integer
	 * @throws ApplicationException
	 */
	public static void enviarEMail(List<String> listaEMail, String nomeAnexo, List<File> listaAnexo, String textoAdicional, String cpfUsuarioLogado) throws ApplicationException {
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			String identificacaoFornecedor = "ABI";

			// variaveis abaixo criadas para a chamada do metodo enviarEmail da
			String assunto = "Monitoramento Agenda ABI - Administração de Bens Imóveis";
			String remetente = "ABI";
			String destinatarioPara = "";
			StringBuffer texto = new StringBuffer();
			String mensagem = "";
			String assinaturaTexto = "Sistema ABI";
			StringBuffer conteudo = new StringBuffer();
			mensagem = "Monitoramento executado em ".concat(Util.formatarData(new Date(), "dd/MM/yyyy HH:mm")).concat(", anexo PDF com as informações.");
			
			assinaturaTexto = "Sistema ABI";

			conteudo.append(identificacaoFornecedor + "<br>");

			conteudo.append(mensagem + "<br>");
			if (StringUtils.isNotBlank(textoAdicional)) {
				conteudo.append(textoAdicional + "<br>");
			}

			texto.append("<HTML>");
			texto.append("<HEAD>");
			texto
					.append("<META HTTP-EQUIV=\"CONTENT-TYPE\" CONTENT=\" charset=iso-8859-1\">");
			texto.append("<TITLE></TITLE>");
			texto.append("</HEAD>");
			texto.append("<BODY LANG=\"pt-BR\" DIR=\"LTR\">");
			texto
					.append("<TABLE WIDTH=100% BORDER=0 BORDERCOLOR=\"#000000\" CELLPADDING=3 CELLSPACING=0 RULES=NONE STYLE=\"page-break-before: always\">");
			texto.append("<COL WIDTH=10%>");
			texto.append("<COL WIDTH=80%>");
			texto.append("<COL WIDTH=10%>");
			texto.append("<TD WIDTH=100%>");
			texto.append("<P ALIGN=CENTER STYLE=\"margin-bottom: 0.5cm\"><FONT FACE=\"Arial, sans-serif\"><B>ESTADO ");
			texto.append("DO PARAN&Aacute; </B></FONT></P>");
			texto.append("<P ALIGN=CENTER STYLE=\"margin-bottom: 0.5cm\"><FONT FACE=\"Arial, sans-serif\"><B>Secretaria ");
			texto.append("de Estado da Administra&ccedil;&atilde;o e da Previd&ecirc;ncia - SEAP</B></FONT></P>");
			texto.append("<P ALIGN=CENTER STYLE=\"margin-bottom: 0.5cm\"><FONT FACE=\"Arial, sans-serif\"><B>Coordenadoria de Patrimônio do Estado - CPE ");
			texto.append("</B></FONT></P>");
			texto.append("<P ALIGN=CENTER><FONT FACE=\"Arial, sans-serif\"><B>GEST&Atilde;O DE PATRIMÔNIO IMOBILIÁRIO - GPI</B></FONT></P>");
			texto.append("</TD>");
			texto.append("</TR>");
			texto.append("<TR>");
			texto.append("<TD COLSPAN=3 WIDTH=591 VALIGN=TOP>");
			texto	.append("<P STYLE=\"margin-left: 1cm; margin-right: 1cm; margin-top: 1cm\"><A NAME=\"body_12275_r\"></A>");
			texto.append("<FONT FACE=\"Arial, sans-serif\">"
					+ conteudo.toString() + "</FONT></P>");
			texto.append("</TD>");
			texto.append("</TR>");
			texto.append("<TR>");
			texto.append("<TD COLSPAN=3 WIDTH=591 VALIGN=TOP>");
			texto
					.append("<P ALIGN=RIGHT STYLE=\"margin-left: 1cm; margin-right: 1cm; margin-top: 1cm\">");
			texto
					.append("<FONT FACE=\"Arial, sans-serif\" ALIGN=RIGHT>Curitiba, "
							+ Util.formatarData(new Date(), "dd 'de' MMMM 'de' yyyy") + "</FONT></P>");
			texto.append("</TD>");
			texto.append("</TR>");
			texto.append("<TR>");
			texto.append("<TD COLSPAN=3 WIDTH=591 VALIGN=TOP>");
			texto.append("<P ALIGN=RIGHT><FONT FACE=\"Arial, sans-serif\">"
					+ assinaturaTexto + "</FONT></P>");
			texto.append("</TD>");
			texto.append("</TR>");
			texto.append("<TR>");
			texto.append("<TD COLSPAN=3 WIDTH=591 VALIGN=TOP>");
			texto.append("</TD>");
			texto.append("</TR>");
			texto.append("</TABLE>");
			texto.append("</BODY>");
			texto.append("</HTML>");
			Integer cont = 0;

			for (String email : listaEMail) {
				if (!destinatarioPara.contentEquals(email.trim())){ //verifica se e-mail já existe em destinatarioPara
					if (!destinatarioPara.isEmpty()){
						destinatarioPara = destinatarioPara.concat(", ");
					}
					destinatarioPara =  destinatarioPara.concat(email.trim());	
				}
				
				cont = cont + 1;
			}

			Util.enviarEmail(assunto,  nomeAnexo, remetente, texto.toString(), destinatarioPara, null, null, listaAnexo, null);
			//Log de email
		} catch (ApplicationException ae) {
			throw ae;
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.9001", new String[] { OperacaoFacade.class.getSimpleName() + ".enviarEmail()" }, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				if (fileReader != null)
					fileReader.close();
				if (bufferedReader != null)
					bufferedReader.close();
			} catch (IOException e) {
				throw new ApplicationException(
						"mensagem.erro.9001",
						new String[] { "Erro ao fechar arquivo no enviarEMail" },
						e, ApplicationException.ICON_ERRO);
			}
		}
	}

	

	/**
	 * Monitorar Agenda.<br>
	 * @author oksana
	 * @since 03/08/2011
	 * @param  
	 * @return AgendaDTO
	 * @throws ApplicationException 
	 */
	public static void monitorarAgenda(String path, SentinelaInterface sentinelaInterface, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try {
			List<Instituicao> listaInstituicao = (List<Instituicao>) hibernateFactory.getInstituicaoDAO().listar();
			for (Instituicao instituicao: listaInstituicao){
				AgendaDTO agendaDTO = OperacaoFacade.gerarAgendaDTO(instituicao);
				agendaDTO.setUsuario(sentinelaInterface.getNome());
 
				String image1 = path + File.separator + "images" + File.separator + "logo_parana.png";
			    String image2 = path + File.separator + "images" + File.separator + "logo_sistema_relatorio.jpg";
						
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("nomeRelatorioJasper", "Agenda.jasper");
				parametros.put("tituloRelatorio", "Monitoramento de Gestão de Bens Imóveis ");
				parametros.put("pathCabecalho", Dominios.PATH_RELATORIO + "CabecalhoPadraoRetrato.jasper");
				parametros.put("pathSubRelatorioNotificacaoAVencer", Dominios.PATH_RELATORIO + "SubRelatorioNotificacaoAVencer.jasper");
				parametros.put("pathSubRelatorioNotificacaoVencida", Dominios.PATH_RELATORIO + "SubRelatorioNotificacaoVencida.jasper");
				parametros.put("pathSubRelatorioDoacaoAVencer", Dominios.PATH_RELATORIO + "SubRelatorioDoacaoAVencer.jasper");
				parametros.put("pathSubRelatorioDoacaoVencida", Dominios.PATH_RELATORIO + "SubRelatorioDoacaoVencida.jasper");
				parametros.put("pathSubRelatorioDoacaoNaoFinalizada", Dominios.PATH_RELATORIO + "SubRelatorioDoacaoNaoFinalizada.jasper");
				parametros.put("pathSubRelatorioVistoriaNecessaria", Dominios.PATH_RELATORIO + "SubRelatorioVistoriaNecessidade.jasper");
				parametros.put("pathSubRelatorioVistoriaNaoFinalizada", Dominios.PATH_RELATORIO + "SubRelatorioVistoriaNaoFinalizada.jasper");
				parametros.put("pathSubRelatorioCessaoDeUsoAVencer", Dominios.PATH_RELATORIO + "SubRelatorioCessaoDeUsoAVencer.jasper");
				
				parametros.put("pathSubRelatorioCessaoDeUsoRascunho", Dominios.PATH_RELATORIO + "SubRelatorioCessaoDeUsoRascunho.jasper");
				parametros.put("pathSubRelatorioCessaoDeUsoEmRenovacao", Dominios.PATH_RELATORIO + "SubRelatorioCessaoDeUsoEmRenovacao.jasper");
				parametros.put("pathSubRelatorioCessaoDeUsoVencida", Dominios.PATH_RELATORIO + "SubRelatorioCessaoDeUsoVencida.jasper");
				
				parametros.put("image1", image1);
				parametros.put("image2", image2); 	 
				List<AgendaDTO> listaAgenda = new ArrayList<AgendaDTO>();
				listaAgenda.add(agendaDTO);

				RelatorioIReportAction.processarRelatorioRetorno(listaAgenda, parametros, form, request, mapping, response);
				
				byte[] relatorioProcessado = (byte[])request.getSession().getAttribute("relatorioProcessado");
				if(relatorioProcessado != null){
				
					String cpfUsuarioLogado = SentinelaComunicacao.getInstance(request).getCpf();	
					List<String> listaEmail = new ArrayList<String>();
					List<ParametroAgendaEmail> listaParametroAgendaEmail = (List<ParametroAgendaEmail>) hibernateFactory.getParametroAgendaEmailDAO().listar();
					for (ParametroAgendaEmail p: listaParametroAgendaEmail){
						listaEmail.add(p.getEmail());
					}
					List<File> listArquivo = new ArrayList<File>();
					File arquivoTemporario = File.createTempFile("relatorioProcessado", ".pdf");
					FileOutputStream fos = new FileOutputStream(arquivoTemporario);
					fos.write(relatorioProcessado);
					
					fos.flush();
					fos.close();				

					listArquivo.add(arquivoTemporario);					

					OperacaoFacade.enviarEMail(listaEmail, "agenda.pdf",  listArquivo, "", cpfUsuarioLogado);
					arquivoTemporario.deleteOnExit();
					request.getSession().removeAttribute("relatorioProcessado");
				}
	
			}
			
			
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em monitorarAgenda()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em monitorarAgenda()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao monitorar Agenda" }, e, ApplicationException.ICON_ERRO);
		}
	}

	
	
	/**
	 * Monitorar Agenda Quartz.<br>
	 * @author oksana
	 * @since 04/08/2011
	 * @param  
	 * @return 
	 * @throws ApplicationException 
	 */
	public static void monitorarAgendaQuartz(String path) throws ApplicationException {
		try {
			//lista de instituicoes
			List<Instituicao> listaInstituicao = (List<Instituicao>) hibernateFactory.getInstituicaoDAO().listar();
			for (Instituicao instituicao: listaInstituicao){
				AgendaDTO agendaDTO = OperacaoFacade.gerarAgendaDTO(instituicao);
				if (agendaDTO.getParametroAgenda() != null && agendaDTO.getParametroAgenda().getListaParametroAgendaEmail() != null){
					agendaDTO.setUsuario("Quartz (Sistema)");

				    List<String> listaEmail = new ArrayList<String>();
					List<ParametroAgendaEmail> listaParametroAgendaEmail = (List<ParametroAgendaEmail>) hibernateFactory.getParametroAgendaEmailDAO().listar(agendaDTO.getParametroAgenda().getCodParametroAgenda());
					for (ParametroAgendaEmail p: listaParametroAgendaEmail){
						listaEmail.add(p.getEmail());
					}
					enviarEMailQuartz(listaEmail, agendaDTO);
				}
			}
						
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em monitorarAgendaQuartz()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em monitorarAgendaQuartz()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao monitorar Agenda Quartz" }, e, ApplicationException.ICON_ERRO);
		}
	}

	
	/**
	 * Metodo para enviar eMail.<br>
	 * @author oksana
	 * @since 04/08/2011
	 * @param List<String> listaEMail
	 * @param AgendaDTO agendaDTO
	 * @throws ApplicationException
	 */
	public static void enviarEMailQuartz(List<String> listaEMail, AgendaDTO agendaDTO)	throws ApplicationException {
		try {
			String procedimentoAbrirArquivo = "Para visualizar o conteúdo do arquivo, favor realizar os seguintes procedimentos passo-a-passo. <br>";
			procedimentoAbrirArquivo = procedimentoAbrirArquivo.concat("1- Clique no arquivo e salve em uma pasta, não alterando o seu nome. <br>");
			procedimentoAbrirArquivo = procedimentoAbrirArquivo.concat("2- Clique com o botão direito sobre o arquivo, e abra com o aplicativo 'BrOffice.org Calc' ou similar.<br>");
			procedimentoAbrirArquivo = procedimentoAbrirArquivo.concat("3- No campo 'Conjunto de Caracteres', informe 'ISO-8859-9'. <br>");
			procedimentoAbrirArquivo = procedimentoAbrirArquivo.concat("4- No campo 'Idioma', informe 'Português(Brasil)'. <br>");
			procedimentoAbrirArquivo = procedimentoAbrirArquivo.concat("5- Na seção 'Opções de Separadores', selecione 'Separador por'. <br>");
			procedimentoAbrirArquivo = procedimentoAbrirArquivo.concat("6- Nas opções disponibilizadas ao selecionar 'Separador por', selecione somente a opção 'Ponto-e-vírgula'. <br>");
			procedimentoAbrirArquivo = procedimentoAbrirArquivo.concat("7- Na seção 'Outras opções', selecione 'Campo citado como texto'. <br>");
			procedimentoAbrirArquivo = procedimentoAbrirArquivo.concat("8- Clique em OK.<br>");
			procedimentoAbrirArquivo = procedimentoAbrirArquivo.concat("9- O arquivo será aberto para visualização.<br>");
			procedimentoAbrirArquivo = procedimentoAbrirArquivo.concat("10- Caso deseje, salve o arquivo.");

			String msg = Mensagem.getInstance().getMessage("email_texto").concat(" ");
			msg = msg.concat(Util.formatarData(new Date(), "dd/MM/yyyy"));
			msg = msg.concat(" às ").concat(Util.formatarData(new Date(), "HH:mm")).concat(".");

			StringBuffer texto = new StringBuffer();
			texto.append("<HTML><HEAD><META HTTP-EQUIV=\"CONTENT-TYPE\" CONTENT=\" charset=iso-8859-1\">");
			texto.append("<TITLE></TITLE></HEAD><BODY LANG=\"pt-BR\" DIR=\"LTR\">");
			texto.append("<TABLE WIDTH=80% BORDER=1 BORDERCOLOR=\"#000000\" CELLPADDING=3 CELLSPACING=0 RULES=NONE STYLE=\"page-break-before: always\">");
			texto.append("<TR VALIGN=MIDDLE><TD BORDER=0 ALIGN=CENTER>");
			texto.append("<IMG SRC=\"" + Mensagem.getInstance().getMessage("email_link_IMAGEM") + "\" NAME=\"figura1\" BORDER=0/><BR>");
			texto.append("</TD></TR></TABLE>");
			texto.append("<P>");
			texto.append("<P>");
			texto.append(agendaDTO.getInstituicao());
			texto.append("<P>");
			texto.append("<P>");
			texto.append(msg);

			texto.append("<P> <FONT COLOR='GRAY'>");
			texto.append(procedimentoAbrirArquivo);
			texto.append("</FONT>");
	 		texto.append(Mensagem.getInstance().getMessage("email_link_SISTEMA"));
	 		texto.append("<P>");
			texto.append("</BODY>");
			texto.append("</HTML>");
			
			// variaveis abaixo criadas para a chamada do metodo enviarEmail da
			String destinatarioPara = "";
			for (String email : listaEMail) {
				if (!destinatarioPara.contentEquals(email.trim())){ //verifica se e-mail já existe em destinatarioPara
					if (!destinatarioPara.isEmpty()){
						destinatarioPara = destinatarioPara.concat(", ");
					}
					destinatarioPara =  destinatarioPara.concat(email.trim());	
				}
			}
			List<AnexoMail> listaAnexos = new ArrayList<AnexoMail>();
			StringBuffer sbArquivo = gerarConteudoEmailAgenda(agendaDTO);

			AnexoMail arquivo = new AnexoMail(Mensagem.getInstance().getMessage("arquivo_agenda"), sbArquivo.toString());
			listaAnexos.add(arquivo);
			
			Util obj = new Util();
			obj.enviarEmailQuartz(Mensagem.getInstance().getMessage("EMAIL_FROM"), texto.toString(), Mensagem.getInstance().getMessage("email_assunto"), destinatarioPara, null, null, listaAnexos, true);

		} catch (ApplicationException ae) {
			throw ae;
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.9001", new String[] { OperacaoFacade.class.getSimpleName() + ".enviarEMailQuartz()" }, e, ApplicationException.ICON_ERRO);
		} 
	}
	
	/**
	 * Metodo para gerar conteudo de eMail da agenda.<br>
	 * @author oksana
	 * @since 04/08/2011
	 * @param AgendaDTO agendaDTO
	 * @throws ApplicationException
	 */
	public static StringBuffer gerarConteudoEmailAgenda(AgendaDTO agendaDTO) throws ApplicationException {
		StringBuffer conteudo = new StringBuffer();
		//------------------- Instituicao-------------------
		conteudo.append(agendaDTO.getInstituicao());
		conteudo.append("\n\n\n\n");
		//------------------- Conteudo de Notificacao-------------------
		conteudo.append(OperacaoFacade.gerarConteudoEmailAgendaNotificacao(agendaDTO));
		
		//------------------- Cessao de Uso -------------------
		conteudo.append(OperacaoFacade.gerarConteudoEmailAgendaCessaoDeUso(agendaDTO));
		
		//------------------- Doacao -------------------
		conteudo.append(OperacaoFacade.gerarConteudoEmailAgendaDoacao(agendaDTO));
		
		//------------------- Vistoria -------------------
		conteudo.append(OperacaoFacade.gerarConteudoEmailAgendaVistoria(agendaDTO));
		
		return conteudo;
	}

	/**
	 * Metodo para gerar conteudo de eMail da agenda - Notificacoes.<br>
	 * @author oksana
	 * @since 04/08/2011
	 * @param AgendaDTO agendaDTO
	 * @throws ApplicationException
	 */
	public static StringBuffer gerarConteudoEmailAgendaNotificacao(AgendaDTO agendaDTO)	throws ApplicationException {
		StringBuffer texto = new StringBuffer();
		//---------------------------------- Notificacao a vencer ---------------------------------//		
		//notificacao a Vencer - titulo
		texto.append("Notificações a Vencer").append(";");
		texto.append("\n");
		//notificacao - conteudo
		if (agendaDTO.getListaNotificacaoDTOAVencerStr() != null &&!agendaDTO.getListaNotificacaoDTOAVencerStr().isEmpty()){
			//primeira linha - subtitulo
			texto.append("Bem Imóvel").append(";");
			texto.append("Descrição").append(";");
			texto.append("Prazo").append(";");
			texto.append("\n");
			//linhas de detalhe
			for (NotificacaoDTO notificacaoDTO: agendaDTO.getListaNotificacaoDTOAVencerStr()){
				texto.append(notificacaoDTO.getCodBemImovel().toString()).append(";");
				texto.append(notificacaoDTO.getDescricao()).append(";");
				texto.append(notificacaoDTO.getPrazo()).append(";");
				texto.append("\n");	
			}
		}else{
			texto.append("Nada consta.").append(";");
			texto.append("\n");
		}
		texto.append("\n");
		
		//---------------------------------- Notificacao vencida ---------------------------------//
		//notificacao Vencida- titulo
		texto.append("\n");
		texto.append("Notificações Vencidas").append(";");
		texto.append("\n");
		//notificacao - conteudo
		if (agendaDTO.getListaNotificacaoDTOVencidaStr() != null &&!agendaDTO.getListaNotificacaoDTOVencidaStr().isEmpty()){
			//primeira linha - subtitulo
			texto.append("Bem Imóvel").append(";");
			texto.append("Descrição").append(";");
			texto.append("Prazo").append(";");
			texto.append("\n");
			//linhas de detalhe
			for (NotificacaoDTO notificacaoDTO: agendaDTO.getListaNotificacaoDTOVencidaStr()){
				texto.append(notificacaoDTO.getCodBemImovel().toString()).append(";");
				texto.append(notificacaoDTO.getDescricao()).append(";");
				texto.append(notificacaoDTO.getPrazo()).append(";");
				texto.append("\n");	
			}
		}else{
			texto.append("Nada consta.").append(";");
			texto.append("\n");
		}
		texto.append("\n");
		return texto;
	}
	
	
	/**
	 * Metodo para gerar conteudo de eMail da agenda - Cessao De uso.<br>
	 * @author oksana
	 * @since 04/08/2011
	 * @param AgendaDTO agendaDTO
	 * @throws ApplicationException
	 */
	public static StringBuffer gerarConteudoEmailAgendaCessaoDeUso(AgendaDTO agendaDTO)	throws ApplicationException {
		StringBuffer texto = new StringBuffer();
	
		//---------------------------------- Cessao de uso a vencer ---------------------------------//		
		//a Vencer - titulo
		texto.append("Cessões de Uso a Vencer").append(";");
		texto.append("\n");
		//cessaoDeUso - conteudo
		if (agendaDTO.getListaCessaoDeUsoVencerStr() != null &&!agendaDTO.getListaCessaoDeUsoVencerStr().isEmpty()){
			//primeira linha - subtitulo
			texto.append("Bem Imóvel").append(";");
			texto.append("Protocolo").append(";");
			texto.append("Termo").append(";");
			texto.append("Cessionário").append(";");
			texto.append("Prazo").append(";");
			texto.append("\n");
			//linhas de detalhe
			for (CessaoDeUsoDTO cessaoDeUsoDTO: agendaDTO.getListaCessaoDeUsoVencerStr()){
				texto.append(cessaoDeUsoDTO.getCodBemImovel().toString()).append(";");
				texto.append(cessaoDeUsoDTO.getProtocolo()).append(";");
				texto.append(cessaoDeUsoDTO.getNumeroAnoTermo()).append(";");
				texto.append(cessaoDeUsoDTO.getOrgao()).append(";");
				texto.append(cessaoDeUsoDTO.getDataFimFormatada()).append(";");
				texto.append("\n");	
			}
		}else{
			texto.append("Nada consta.").append(";");
			texto.append("\n");
		}
		texto.append("\n");

		//---------------------------------- Cessao de uso vencida ---------------------------------//		
		//titulo
		texto.append("\n");
		texto.append("Cessões Vencidas").append(";");
		texto.append("\n");
		//cessaoDeUso - conteudo
		if (agendaDTO.getListaCessaoDeUsoDTOVencida() != null &&!agendaDTO.getListaCessaoDeUsoDTOVencida().isEmpty()){
			//primeira linha - subtitulo
			texto.append("Bem Imóvel").append(";");
			texto.append("Protocolo").append(";");
			texto.append("Termo").append(";");
			texto.append("Cessionário").append(";");
			texto.append("Prazo").append(";");
			texto.append("\n");
			//linhas de detalhe
			for (CessaoDeUsoDTO cessaoDeUsoDTO: agendaDTO.getListaCessaoDeUsoDTOVencida()){
				texto.append(cessaoDeUsoDTO.getCodBemImovel().toString()).append(";");
				texto.append(cessaoDeUsoDTO.getProtocolo()).append(";");
				texto.append(cessaoDeUsoDTO.getNumeroAnoTermo()).append(";");
				texto.append(cessaoDeUsoDTO.getOrgao()).append(";");
				texto.append(cessaoDeUsoDTO.getDataFimFormatada()).append(";");
				texto.append("\n");	
			}
		}else{
			texto.append("Nada consta.").append(";");
			texto.append("\n");
		}
		texto.append("\n");


		//---------------------------------- Cessao de uso em preenchimento ---------------------------------//		
		//titulo
		texto.append("\n");
		texto.append("Cessões de Uso em preenchimento (falta finalização)").append(";");
		texto.append("\n");
		//cessaoDeUso - conteudo
		if (agendaDTO.getListaCessaoDeUsoEmPreenchimentoStr() != null &&!agendaDTO.getListaCessaoDeUsoEmPreenchimentoStr().isEmpty()){
			//primeira linha - subtitulo
			texto.append("Bem Imóvel").append(";");
			texto.append("Protocolo").append(";");
			texto.append("Termo").append(";");
			texto.append("Cessionário").append(";");
			texto.append("Prazo").append(";");
			texto.append("\n");
			//linhas de detalhe
			for (CessaoDeUsoDTO cessaoDeUsoDTO: agendaDTO.getListaCessaoDeUsoEmPreenchimentoStr()){
				texto.append(cessaoDeUsoDTO.getCodBemImovel().toString()).append(";");
				texto.append(cessaoDeUsoDTO.getProtocolo()).append(";");
				texto.append(cessaoDeUsoDTO.getNumeroAnoTermo()).append(";");
				texto.append(cessaoDeUsoDTO.getOrgao()).append(";");
				texto.append(cessaoDeUsoDTO.getDataFimFormatada()).append(";");
				texto.append("\n");	
			}
		}else{
			texto.append("Nada consta.").append(";");
			texto.append("\n");
		}
		texto.append("\n");

		//---------------------------------- Cessao de uso em renovação ---------------------------------//		
		//titulo
		texto.append("\n");
		texto.append("Cessões de Uso em renovação (falta finalização) ").append(";");
		texto.append("\n");
		//cessaoDeUso - conteudo
		if (agendaDTO.getListaCessaoDeUsoEmRenovacaoStr() != null &&!agendaDTO.getListaCessaoDeUsoEmRenovacaoStr().isEmpty()){
			//primeira linha - subtitulo
			texto.append("Bem Imóvel").append(";");
			texto.append("Protocolo").append(";");
			texto.append("Termo").append(";");
			texto.append("Cessionário").append(";");
			texto.append("Prazo").append(";");
			texto.append("\n");
			//linhas de detalhe
			for (CessaoDeUsoDTO cessaoDeUsoDTO: agendaDTO.getListaCessaoDeUsoEmRenovacaoStr()){
				texto.append(cessaoDeUsoDTO.getCodBemImovel().toString()).append(";");
				texto.append(cessaoDeUsoDTO.getProtocolo()).append(";");
				texto.append(cessaoDeUsoDTO.getNumeroAnoTermo()).append(";");
				texto.append(cessaoDeUsoDTO.getOrgao()).append(";");
				texto.append(cessaoDeUsoDTO.getDataFimFormatada()).append(";");
				texto.append("\n");	
			}
		}else{
			texto.append("Nada consta.").append(";");
			texto.append("\n");
		}
		texto.append("\n");
		return texto;
	}
	
	
	/**
	 * Metodo para gerar conteudo de eMail da agenda - Doacoes.<br>
	 * @author oksana
	 * @since 04/08/2011
	 * @param AgendaDTO agendaDTO
	 * @throws ApplicationException
	 */
	public static StringBuffer gerarConteudoEmailAgendaDoacao(AgendaDTO agendaDTO)	throws ApplicationException {
		StringBuffer texto = new StringBuffer();

		//---------------------------------- Doacao a vencer ---------------------------------//		
		//doacao a Vencer - titulo
		texto.append("\n");
		texto.append("Doações a Vencer").append(";");
		texto.append("\n");
		//notificacao - conteudo
		if (agendaDTO.getListaDoacaoDTOAVencerStr() != null &&!agendaDTO.getListaDoacaoDTOAVencerStr().isEmpty()){
			//primeira linha - subtitulo
			texto.append("Bem Imóvel").append(";");
			texto.append("Protocolo").append(";");
			texto.append("Termo").append(";");
			texto.append("Órgão").append(";");
			texto.append("Prazo").append(";");
			texto.append("\n");
			//linhas de detalhe
			for (DoacaoDTO doacaoDTO: agendaDTO.getListaDoacaoDTOAVencerStr()){
				texto.append(doacaoDTO.getCodBemImovel().toString()).append(";");
				texto.append(doacaoDTO.getProtocolo()).append(";");
				texto.append(doacaoDTO.getNumeroAnoTermo()).append(";");
				texto.append(doacaoDTO.getOrgao()).append(";");
				texto.append(doacaoDTO.getDataFimFormatada()).append(";");
				texto.append("\n");	
			}
		}else{
			texto.append("Nada consta.").append(";");
			texto.append("\n");
		}
		texto.append("\n");
		
		//---------------------------------- Doacao nao Finalizada ---------------------------------//		
		//titulo
		texto.append("\n");
		texto.append("Doações em rascunho (falta finalização)").append(";");
		texto.append("\n");
		//notificacao - conteudo
		if (agendaDTO.getListaDoacaoDTONaoFinalizada() != null &&!agendaDTO.getListaDoacaoDTONaoFinalizada().isEmpty()){
			//primeira linha - subtitulo
			texto.append("Bem Imóvel").append(";");
			texto.append("Protocolo").append(";");
			texto.append("Termo").append(";");
			texto.append("Órgão").append(";");
			texto.append("Prazo").append(";");
			texto.append("\n");
			//linhas de detalhe
			for (DoacaoDTO doacaoDTO: agendaDTO.getListaDoacaoDTONaoFinalizada()){
				texto.append(doacaoDTO.getCodBemImovel().toString()).append(";");
				texto.append(doacaoDTO.getProtocolo()).append(";");
				texto.append(doacaoDTO.getNumeroAnoTermo()).append(";");
				texto.append(doacaoDTO.getOrgao()).append(";");
				texto.append(doacaoDTO.getDataFimFormatada()).append(";");
				texto.append("\n");	
			}
		}else{
			texto.append("Nada consta.").append(";");
			texto.append("\n");
		}
		texto.append("\n");
		
		//---------------------------------- Doacao vencida ---------------------------------//		
		//titulo
		texto.append("\n");
		texto.append("Doações Vencidas").append(";");
		texto.append("\n");
		//notificacao - conteudo
		if (agendaDTO.getListaDoacaoDTOVencida() != null &&!agendaDTO.getListaDoacaoDTOVencida().isEmpty()){
			//primeira linha - subtitulo
			texto.append("Bem Imóvel").append(";");
			texto.append("Protocolo").append(";");
			texto.append("Termo").append(";");
			texto.append("Órgão").append(";");
			texto.append("Prazo").append(";");
			texto.append("\n");
			//linhas de detalhe
			for (DoacaoDTO doacaoDTO: agendaDTO.getListaDoacaoDTOVencida()){
				texto.append(doacaoDTO.getCodBemImovel().toString()).append(";");
				texto.append(doacaoDTO.getProtocolo()).append(";");
				texto.append(doacaoDTO.getNumeroAnoTermo()).append(";");
				texto.append(doacaoDTO.getOrgao()).append(";");
				texto.append(doacaoDTO.getDataFimFormatada()).append(";");
				texto.append("\n");	
			}
		}else{
			texto.append("Nada consta.").append(";");
			texto.append("\n");
		}
		texto.append("\n");
		
		return texto;
	}
	
	/**
	 * Metodo para gerar conteudo de eMail da agenda - Vistorias.<br>
	 * @author oksana
	 * @since 04/08/2011
	 * @param AgendaDTO agendaDTO
	 * @throws ApplicationException
	 */
	public static StringBuffer gerarConteudoEmailAgendaVistoria(AgendaDTO agendaDTO)	throws ApplicationException {
		StringBuffer texto = new StringBuffer();
		
		//---------------------------------- Vistoria nao finalizada ---------------------------------//		
		//titulo
		texto.append("\n");
		texto.append("Vistorias em processamento (falta finalização)").append(";");
		texto.append("\n");
		//conteudo
		if (agendaDTO.getListaVistoriaDTONaoFinalizada() != null &&!agendaDTO.getListaVistoriaDTONaoFinalizada().isEmpty()){
			//primeira linha - subtitulo
			texto.append("Bem Imóvel").append(";");
			texto.append("Vistoriador").append(";");
			texto.append("Data da vistoria").append(";");
			texto.append("\n");
			//linhas de detalhe
			for (VistoriaDTO vistoriaDTO: agendaDTO.getListaVistoriaDTONaoFinalizada()){
				texto.append(vistoriaDTO.getCodBemImovel().toString()).append(";");
				texto.append(vistoriaDTO.getVistoriador()).append(";");
				texto.append(vistoriaDTO.getDataVistoriaFormatada()).append(";");
				texto.append("\n");	
			}
		}else{
			texto.append("Nada consta.").append(";");
			texto.append("\n");
		}
		texto.append("\n");

		//---------------------------------- Vistoria vencida ---------------------------------//		
		//titulo
		texto.append("\n");
		texto.append("Vistorias vencida").append(";");
		texto.append("\n");
		//conteudo
		if (agendaDTO.getListaVistoriaDTOVencidaStr() != null &&!agendaDTO.getListaVistoriaDTOVencidaStr().isEmpty()){
			//primeira linha - subtitulo
			texto.append("Bem Imóvel").append(";");
			texto.append("Data da vistoria").append(";");
			texto.append("\n");
			//linhas de detalhe
			for (VistoriaDTO vistoriaDTO: agendaDTO.getListaVistoriaDTOVencidaStr()){
				texto.append(vistoriaDTO.getCodBemImovel().toString()).append(";");
				texto.append(vistoriaDTO.getDataVistoriaFormatada()).append(";");
				texto.append("\n");	
			}
		}else{
			texto.append("Nada consta.").append(";");
			texto.append("\n");
		}
		texto.append("\n");

		return texto;
	}

	public static Doacao gerarNovaDoacao(Integer codDoacao, String cpfLogado) throws ApplicationException {
		Doacao doacao = new Doacao();
		try {
			Date dataAtual = new Date();
			Doacao doacaoOld = obterDoacaoCompleto(codDoacao);
			
			doacaoOld.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.FINALIZADO.getIndex()));
			doacaoOld.setTsAtualizacao(dataAtual);
			doacaoOld.setCpfResponsavelAlteracao(cpfLogado);
	
			doacao.setBemImovel(doacaoOld.getBemImovel());
			doacao.setInstituicao(doacaoOld.getInstituicao());
			doacao.setOrgaoProprietario(doacaoOld.getOrgaoProprietario());
			doacao.setAdministracao(doacaoOld.getAdministracao());
			doacao.setOrgaoResponsavel(doacaoOld.getOrgaoResponsavel());
			doacao.setProtocolo(doacaoOld.getProtocolo());
			doacao.setLeiBemImovel(doacaoOld.getLeiBemImovel());
			doacao.setNrProjetoLei(doacaoOld.getNrProjetoLei());
			doacao.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.RASCUNHO.getIndex()));
			doacao.setTsInclusao(dataAtual);
			doacao.setCpfResponsavelInclusao(cpfLogado);
			doacao.setDtInicioVigencia(doacaoOld.getDtFimVigencia());
	
			log4j.debug(Log.INICIO_TRANSACAO);
			HibernateUtil.currentTransaction();
			doacao = hibernateFactory.getDoacaoDAO().salvarDoacao(doacao);
			hibernateFactory.getDoacaoDAO().alterar(doacaoOld);
			HibernateUtil.commitTransaction();
			log4j.debug(Log.FIM_TRANSACAO);
			
		}catch (ApplicationException ae) {
			try {
				log4j.debug(Log.ERRO_ENCERRAMENTO_TRANSACAO);
				HibernateUtil.rollbackTransaction();
			}catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em gerarNovaDoacao()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				log4j.debug(Log.ERRO_ENCERRAMENTO_TRANSACAO);
				HibernateUtil.rollbackTransaction();
			}catch (Exception ex) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em gerarNovaDoacao()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao gerar Nova Doação" }, e, ApplicationException.ICON_ERRO);
		}	
		
		return doacao;
	}

	/**
	 * Lista paginada de Transferencia.
	 * 
	 * @param pag - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarTransferencia(Pagina pag, Transferencia transferencia, Usuario usuario, Integer indOperadorOrgao, HttpServletRequest request)
			throws ApplicationException {

		try {
			//tratamento para usuario logado = operador de orgao
			List<Integer> listaCodOrgao = new ArrayList<Integer>();
			if (usuario == null){
				throw new ApplicationException("AVISO.97");
			}
			if (CadastroFacade.verificarGrupoUsuarioLogado(request, GrupoSentinela.OPE_ORG_ABI.getCodigo())) {
				if (usuario.getListaUsuarioOrgao() == null || usuario.getListaUsuarioOrgao().size() < 1){
					throw new ApplicationException("AVISO.96");
				}
				for (UsuarioOrgao o : usuario.getListaUsuarioOrgao()){
					listaCodOrgao.add(o.getOrgao().getCodOrgao());
				}
			}else{
				listaCodOrgao = null;
			}
			
			if (pag.getTotalRegistros() == 0) {
				pag.setTotalRegistros(hibernateFactory.getTransferenciaDAO().listar(null, null, transferencia, listaCodOrgao).size());
			}
			pag.setRegistros(hibernateFactory.getTransferenciaDAO().listar(pag.getQuantidade(), pag.getPaginaAtual(), transferencia, listaCodOrgao));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Transferência" }, e);
		}

		return pag;
	}

	/**
	 * Verifica se exite uma outra Transferencia para o mesmo Bem Imóvel e Status do Termo.
	 * @param status
	 * @return
	 * @throws ApplicationException
	 */
	public static List<Transferencia> verificarTransferenciaByBemImovelStatusTermo(Transferencia transferencia) throws ApplicationException {
		try {
			return hibernateFactory.getTransferenciaDAO().listarTransferencia(transferencia);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "transferência" }, e);
		}
	}

	/**
	 * Salva o item da Transferencia
	 * @param itemTransferencia
	 * @throws ApplicationException
	 */
	public static void salvarItemTransferencia(ItemTransferencia itemTransferencia) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			if (itemTransferencia.getTipo().equals(Integer.valueOf(1))){
				for (ItemTransferencia item : itemTransferencia.getTransferencia().getListaItemTransferencia()) {
					hibernateFactory.getItemTransferenciaDAO().excluir(item);
				}
			}
			hibernateFactory.getItemTransferenciaDAO().salvar(itemTransferencia);
			HibernateUtil.commitTransaction();
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarItemTransferencia()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarItemTransferencia()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao incluir Item da Transferência" }, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Salva a Assinatura da Transferencia
	 * @param itemTransferencia
	 * @throws ApplicationException
	 */
	public static void salvarAssinaturaTransferencia(AssinaturaTransferencia assinaturaTransferencia) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			hibernateFactory.getAssinaturaTransferenciaDAO().salvar(assinaturaTransferencia);
			HibernateUtil.commitTransaction();
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarAssinaturaTransferencia()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarAssinaturaTransferencia()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao incluir Assinatura da Transferência" }, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Excluir o item da Transferencia
	 * @param itemTransferencia
	 * @throws ApplicationException
	 */
	public static void excluirItemTransferencia(ItemTransferencia itemTransferencia) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			hibernateFactory.getItemTransferenciaDAO().excluir(itemTransferencia);
			HibernateUtil.commitTransaction();
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirItemTransferencia()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirItemTransferencia()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao excluir Item da Transferência" }, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Excluir a Assinatura da Transferencia
	 * @param itemTransferencia
	 * @throws ApplicationException
	 */
	public static void excluirAssinaturaTransferencia(AssinaturaTransferencia assinaturaTransferencia) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			hibernateFactory.getAssinaturaTransferenciaDAO().excluir(assinaturaTransferencia);
			HibernateUtil.commitTransaction();
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirAssinaturaTransferencia()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirAssinaturaTransferencia()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao excluir Assinatura da Transferência" }, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Lista os itens da Transferencia
	 * @param pag
	 * @param codTransferencia
	 * @return
	 * @throws ApplicationException
	 */
	public static Object listarItemTransferencia(Pagina pag, Integer codTransferencia) throws ApplicationException {
		try {
			Collection<ItemTransferencia> itensLista = hibernateFactory.getItemTransferenciaDAO().listar(codTransferencia);
			pag.setQuantidade(itensLista.size());
			pag.setRegistros(itensLista);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Item da Transferência" }, e);
		}

		return pag;
	}

	/**
	 * Lista as assinaturas da Transferencia
	 * @param pag
	 * @param codTransferencia
	 * @return
	 * @throws ApplicationException
	 */
	public static Object listarAssinaturaTransferencia(Pagina pag, Integer codTransferencia) throws ApplicationException {
		try {
			Collection<AssinaturaTransferencia> itensLista = hibernateFactory.getAssinaturaTransferenciaDAO().listar(codTransferencia);
			pag.setQuantidade(itensLista.size());
			pag.setRegistros(itensLista);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Assinatura da Transferência" }, e);
		}

		return pag;
	}

	public static Collection<AssinaturaTransferencia> verificarDuplicidadeAssinaturaTransferencia(
			AssinaturaTransferencia assinaturaTransferencia) throws ApplicationException {
		try {
			return hibernateFactory.getAssinaturaTransferenciaDAO().listaVerificacao(assinaturaTransferencia);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "assinatura" }, e);
		}
	}

	public static ItemTransferencia obterItemTransferencia(Integer codItemTransferencia) throws ApplicationException {
		return hibernateFactory.getItemTransferenciaDAO().obter(codItemTransferencia);
	}

	public static AssinaturaTransferencia obterAssinaturaTransferencia(Integer codAssinaturaTransferencia) throws ApplicationException {
		return hibernateFactory.getAssinaturaTransferenciaDAO().obter(codAssinaturaTransferencia);
	}
	
	public static Collection<ItemTransferencia> verificarDuplicidadeItemTransferencia(ItemTransferencia itemTransferencia) throws ApplicationException {
		try {
			return hibernateFactory.getItemTransferenciaDAO().listar(itemTransferencia.getTransferencia().getCodTransferencia());
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "item" }, e);
		}
	}

	/**
	 * Realiza a exclusão da Transferencia e dos itens vinculados.
	 * @param Transferencia
	 * @throws ApplicationException
	 */
	public static void excluirTransferencia(Transferencia transferencia) throws ApplicationException {
		try {
			// verifica se existe integridade referencial
			Transferencia aux = hibernateFactory.getTransferenciaDAO().obter(transferencia.getCodTransferencia());
			HibernateUtil.currentTransaction(); 
			if (aux.getStatusTermo().getCodStatusTermo().equals(Dominios.statusTermo.RASCUNHO.getIndex())) {
				for (AssinaturaTransferencia assinaturaTransferencia : transferencia.getListaAssinaturaTransferencia()) {
					hibernateFactory.getAssinaturaTransferenciaDAO().excluir(assinaturaTransferencia);
				}
				for (ItemTransferencia itemTransferencia : transferencia.getListaItemTransferencia()) {
					hibernateFactory.getItemTransferenciaDAO().excluir(itemTransferencia);
				}
				hibernateFactory.getTransferenciaDAO().excluir(transferencia);
			} else {
				salvarTransferencia(transferencia);
			}
			
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirTransferencia()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirTransferencia()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Transferência" }, ex, ApplicationException.ICON_ERRO);
		}
	}

	public static Transferencia gerarNovaTransferencia(Integer codTransferencia, String cpfLogado) throws ApplicationException {
		Transferencia transferencia = new Transferencia();
		try {
			Date dataAtual = new Date();
			Transferencia transferenciaOld = obterTransferenciaCompleto(codTransferencia);
			
			transferenciaOld.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.FINALIZADO.getIndex()));
			transferenciaOld.setTsAtualizacao(dataAtual);
			transferenciaOld.setCpfResponsavelAlteracao(cpfLogado);

			transferencia.setBemImovel(transferenciaOld.getBemImovel());
			transferencia.setInstituicao(transferenciaOld.getInstituicao());
			transferencia.setOrgaoCedente(transferenciaOld.getOrgaoCedente());
			transferencia.setOrgaoCessionario(transferenciaOld.getOrgaoCessionario());
			transferencia.setProtocolo(transferenciaOld.getProtocolo());
			transferencia.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.RASCUNHO.getIndex()));
			transferencia.setTsInclusao(dataAtual);
			transferencia.setCpfResponsavelInclusao(cpfLogado);
			transferencia.setDtInicioVigencia(transferenciaOld.getDtFimVigencia());

			log4j.debug(Log.INICIO_TRANSACAO);
			HibernateUtil.currentTransaction();
			transferencia = hibernateFactory.getTransferenciaDAO().salvarTransferencia(transferencia);
			hibernateFactory.getTransferenciaDAO().alterar(transferenciaOld);
			HibernateUtil.commitTransaction();
			log4j.debug(Log.FIM_TRANSACAO);
			
		}catch (ApplicationException ae) {
			try {
				log4j.debug(Log.ERRO_ENCERRAMENTO_TRANSACAO);
				HibernateUtil.rollbackTransaction();
			}catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em gerarNovaTransferencia()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				log4j.debug(Log.ERRO_ENCERRAMENTO_TRANSACAO);
				HibernateUtil.rollbackTransaction();
			}catch (Exception ex) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em gerarNovaTransferencia()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao gerar Nova Transferência"}, e, ApplicationException.ICON_ERRO);
		}	
		return transferencia;
	}

	/**
	 * Lista as assinaturas da doação
	 * @param codDoacao
	 * @return
	 * @throws ApplicationException
	 */
	public static Collection<AssinaturaDoacao> listarAssinaturaDoacao(Integer codDoacao) throws ApplicationException {
		try {
			return hibernateFactory.getAssinaturaDoacaoDAO().listar(codDoacao);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Assinatura da Doação" }, e);
		}
	}

	/**
	 * Lista as itens da doação
	 * @param codDoacao
	 * @return
	 * @throws ApplicationException
	 */
	public static Collection<ItemDoacao> listarItemDoacao(Integer codDoacao) throws ApplicationException {
		try {
			return hibernateFactory.getItemDoacaoDAO().listar(codDoacao);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Item da Doação" }, e);
		}
	}

	/**
	 * Lista as assinaturas da Transferencia
	 * @param codTransferencia
	 * @return
	 * @throws ApplicationException
	 */
	public static Collection<AssinaturaTransferencia> listarAssinaturaTransferencia(Integer codTransferencia) throws ApplicationException {
		try {
			return hibernateFactory.getAssinaturaTransferenciaDAO().listar(codTransferencia);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Assinatura da Doação" }, e);
		}
	}

	/**
	 * Lista as itens da Transferencia
	 * @param codDoacao
	 * @return
	 * @throws ApplicationException
	 */
	public static Collection<ItemTransferencia> listarItemTransferencia(Integer codTransferencia) throws ApplicationException {
		try {
			return hibernateFactory.getItemTransferenciaDAO().listar(codTransferencia);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Item da Doação" }, e);
		}
	}
	
	
	/**
	 * Salva a CessaoDeUso e a lei bem imovel
	 * @param doacao
	 * @param leiBemImovel
	 * @return
	 * @throws ApplicationException
	 */
	public static CessaoDeUso salvarCessaoDeUsoLeiBemImovel(CessaoDeUso cessaoDeUso, LeiBemImovel leiBemImovel) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			
			if (leiBemImovel.getCodLeiBemImovel() == null){
				leiBemImovel = hibernateFactory.getLeiBemImovelDAO().salvarLeiBemImovel(leiBemImovel);
				cessaoDeUso.setLeiBemImovel(leiBemImovel);
			}
			if (cessaoDeUso.getCodCessaoDeUso() == null){
				cessaoDeUso = hibernateFactory.getCessaoDeUsoDAO().salvarCessaoDeUso(cessaoDeUso);
			}else{
				hibernateFactory.getCessaoDeUsoDAO().alterar(cessaoDeUso);
			}

			HibernateUtil.commitTransaction();
			return cessaoDeUso;
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarCessaoDeUsoLeiBemImovel()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarCessaoDeUsoLeiBemImovel()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao incluir Cessão De Uso e Lei Bem Imovel" }, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Salva a CessaoDeUso
	 * @param CessaoDeUso
	 * @return
	 * @throws ApplicationException
	 */
	public static CessaoDeUso salvarCessaoDeUso(CessaoDeUso cessaoDeUso) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			
			if (cessaoDeUso.getCodCessaoDeUso() == null){
				cessaoDeUso = hibernateFactory.getCessaoDeUsoDAO().salvarCessaoDeUso(cessaoDeUso);
			}else{
				hibernateFactory.getCessaoDeUsoDAO().alterar(cessaoDeUso);
			}
			HibernateUtil.commitTransaction();
			return cessaoDeUso;
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarCessaoDeUso()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarCessaoDeUso()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao incluir Cessão De Uso" }, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Lista paginada de Cessao de Uso.
	 * 
	 * @param pag - objeto de paginação contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para
	 *         paginação.
	 * @throws ApplicationException
	 */
	public static Pagina listarCessaoDeUso(Pagina pag, CessaoDeUso cessaoDeUso)
			throws ApplicationException {

		try {
			if (pag.getTotalRegistros() == 0) {
				pag.setTotalRegistros(hibernateFactory.getCessaoDeUsoDAO().listar(
						null, null, cessaoDeUso).size());
			}
			pag.setRegistros(hibernateFactory.getCessaoDeUsoDAO().listar(
					pag.getQuantidade(), pag.getPaginaAtual(), cessaoDeUso));

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Cessão De Uso" }, e);
		}

		return pag;
	}

	/**
	 * Verifica se exite uma outra Cessao De Uso para o mesmo Bem Imóvel e Status do Termo.
	 * @param status
	 * @return
	 * @throws ApplicationException
	 */
	public static List<CessaoDeUso> verificarCessaoDeUsoByBemImovelStatusTermo(CessaoDeUso cessaoDeUso) throws ApplicationException {
		try {
			return hibernateFactory.getCessaoDeUsoDAO().listarCessaoDeUso(cessaoDeUso);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "cessão de uso" }, e);
		}
	}

	/**
	 * Verifica se exite uma outra Cessao De Uso Vinculada para o mesmo Bem Imóvel e Status do Termo.
	 * @param status
	 * @return
	 * @throws ApplicationException
	 */
	public static List<CessaoDeUso> verificarCessaoDeUsoVinculadaByStatusTermo(CessaoDeUso cessaoDeUso) throws ApplicationException {
		try {
			return hibernateFactory.getCessaoDeUsoDAO().listarCessaoDeUsoVinculada(cessaoDeUso);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "cessão de uso" }, e);
		}
	}

	/**
	 * Salva o item da CessaoDeUso
	 * @param itemCessaoDeUso
	 * @throws ApplicationException
	 */
	public static void salvarItemCessaoDeUso(ItemCessaoDeUso itemCessaoDeUso) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			if (itemCessaoDeUso.getTipo().equals(Integer.valueOf(1))){
				for (ItemCessaoDeUso item : itemCessaoDeUso.getCessaoDeUso().getListaItemCessaoDeUso()) {
					hibernateFactory.getItemCessaoDeUsoDAO().excluir(item);
				}
			}
			hibernateFactory.getItemCessaoDeUsoDAO().salvar(itemCessaoDeUso);
			HibernateUtil.commitTransaction();
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarItemCessaoDeUso()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarItemCessaoDeUso()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao incluir Item da Cessão De Uso" }, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Salva a Assinatura da CessaoDeUso
	 * @param itemCessaoDeUso
	 * @throws ApplicationException
	 */
	public static void salvarAssinaturaCessaoDeUso(AssinaturaCessaoDeUso assinaturaCessaoDeUso) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			hibernateFactory.getAssinaturaCessaoDeUsoDAO().salvar(assinaturaCessaoDeUso);
			HibernateUtil.commitTransaction();
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarAssinaturaCessaoDeUso()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarAssinaturaCessaoDeUso()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao incluir Assinatura da Cessão De Uso" }, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Excluir o item da CessaoDeUso
	 * @param itemCessaoDeUso
	 * @throws ApplicationException
	 */
	public static void excluirItemCessaoDeUso(ItemCessaoDeUso itemCessaoDeUso) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			hibernateFactory.getItemCessaoDeUsoDAO().excluir(itemCessaoDeUso);
			HibernateUtil.commitTransaction();
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirItemCessaoDeUso()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirItemCessaoDeUso()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao excluir Item da Cessão De Uso" }, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Excluir a Assinatura da CessaoDeUso
	 * @param itemCessaoDeUso
	 * @throws ApplicationException
	 */
	public static void excluirAssinaturaCessaoDeUso(AssinaturaCessaoDeUso assinaturaCessaoDeUso) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			hibernateFactory.getAssinaturaCessaoDeUsoDAO().excluir(assinaturaCessaoDeUso);
			HibernateUtil.commitTransaction();
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirAssinaturaCessaoDeUso()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirAssinaturaCessaoDeUso()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao excluir Assinatura da Cessão De Uso" }, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Lista os itens da CessaoDeUso
	 * @param pag
	 * @param codCessaoDeUso
	 * @return
	 * @throws ApplicationException
	 */
	public static Object listarItemCessaoDeUso(Pagina pag, Integer codCessaoDeUso) throws ApplicationException {
		try {
			Collection<ItemCessaoDeUso> itensLista = hibernateFactory.getItemCessaoDeUsoDAO().listar(codCessaoDeUso);
			pag.setQuantidade(itensLista.size());
			pag.setRegistros(itensLista);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Item da Cessão De Uso" }, e);
		}

		return pag;
	}

	/**
	 * Lista as assinaturas da CessaoDeUso
	 * @param pag
	 * @param codCessaoDeUso
	 * @return
	 * @throws ApplicationException
	 */
	public static Object listarAssinaturaCessaoDeUso(Pagina pag, Integer codCessaoDeUso) throws ApplicationException {
		try {
			Collection<AssinaturaCessaoDeUso> itensLista = hibernateFactory.getAssinaturaCessaoDeUsoDAO().listar(codCessaoDeUso);
			pag.setQuantidade(itensLista.size());
			pag.setRegistros(itensLista);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Assinatura da Cessão De Uso" }, e);
		}

		return pag;
	}

	/**
	 * Lista os itens da CessaoDeUso
	 * @param codCessaoDeUso
	 * @return
	 * @throws ApplicationException
	 */
	public static Collection<ItemCessaoDeUso> listarItemCessaoDeUso(Integer codCessaoDeUso) throws ApplicationException {
		try {
			return hibernateFactory.getItemCessaoDeUsoDAO().listar(codCessaoDeUso);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Item da Cessão De Uso" }, e);
		}
	}

	/**
	 * Lista as assinaturas da CessaoDeUso
	 * @param pag
	 * @param codCessaoDeUso
	 * @return
	 * @throws ApplicationException
	 */
	public static Collection<AssinaturaCessaoDeUso> listarAssinaturaCessaoDeUso(Integer codCessaoDeUso) throws ApplicationException {
		try {
			return hibernateFactory.getAssinaturaCessaoDeUsoDAO().listar(codCessaoDeUso);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] {"ao listar Assinatura da Cessão De Uso" }, e);
		}
	}

	public static Collection<AssinaturaCessaoDeUso> verificarDuplicidadeAssinaturaCessaoDeUso(
			AssinaturaCessaoDeUso assinaturaCessaoDeUso) throws ApplicationException {
		try {
			return hibernateFactory.getAssinaturaCessaoDeUsoDAO().listaVerificacao(assinaturaCessaoDeUso);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "assinatura" }, e);
		}
	}

	public static ItemCessaoDeUso obterItemCessaoDeUso(Integer codItemCessaoDeUso) throws ApplicationException {
		return hibernateFactory.getItemCessaoDeUsoDAO().obter(codItemCessaoDeUso);
	}

	public static AssinaturaCessaoDeUso obterAssinaturaCessaoDeUso(Integer codAssinaturaCessaoDeUso) throws ApplicationException {
		return hibernateFactory.getAssinaturaCessaoDeUsoDAO().obter(codAssinaturaCessaoDeUso);
	}
	
	public static Collection<ItemCessaoDeUso> verificarDuplicidadeItemCessaoDeUso(ItemCessaoDeUso itemCessaoDeUso) throws ApplicationException {
		try {
			return hibernateFactory.getItemCessaoDeUsoDAO().listar(itemCessaoDeUso.getCessaoDeUso().getCodCessaoDeUso());
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "item" }, e);
		}
	}

	/**
	 * Realiza a exclusão da CessaoDeUso e dos itens vinculados.
	 * @param CessaoDeUso
	 * @throws ApplicationException
	 */
	public static void excluirCessaoDeUso(CessaoDeUso cessaoDeUso) throws ApplicationException {
		try {
			// verifica se existe integridade referencial
			CessaoDeUso aux = hibernateFactory.getCessaoDeUsoDAO().obter(cessaoDeUso.getCodCessaoDeUso());
			HibernateUtil.currentTransaction(); 
			if (aux.getStatusTermo().getCodStatusTermo().equals(Dominios.statusTermo.RASCUNHO.getIndex())) {
				for (AssinaturaCessaoDeUso assinaturaCessaoDeUso : cessaoDeUso.getListaAssinaturaCessaoDeUso()) {
					hibernateFactory.getAssinaturaCessaoDeUsoDAO().excluir(assinaturaCessaoDeUso);
				}
				for (ItemCessaoDeUso itemCessaoDeUso : cessaoDeUso.getListaItemCessaoDeUso()) {
					hibernateFactory.getItemCessaoDeUsoDAO().excluir(itemCessaoDeUso);
				}
				hibernateFactory.getCessaoDeUsoDAO().excluir(cessaoDeUso);
			} else {
				salvarCessaoDeUso(cessaoDeUso);
			}
			
			HibernateUtil.commitTransaction(); 

		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirCessaoDeUso()!"}, ApplicationException.ICON_AVISO);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirCessaoDeUso()!"}, ApplicationException.ICON_AVISO);
			}
			throw new ApplicationException("ERRO.201", new String[] {"ao excluir Cessão De Uso" }, ex, ApplicationException.ICON_ERRO);
		}
	}

	public static CessaoDeUso gerarNovaCessaoDeUso(Integer codCessaoDeUso, String cpfLogado) throws ApplicationException {
		CessaoDeUso cessaoDeUso = new CessaoDeUso();
		
		try {
			Date dataAtual = new Date();
			CessaoDeUso cessaoDeUsoOld = obterCessaoDeUsoCompleto(codCessaoDeUso);
			
			cessaoDeUsoOld.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.FINALIZADO.getIndex()));
			cessaoDeUsoOld.setTsAlteracao(dataAtual);
			cessaoDeUsoOld.setCpfResponsavelAlteracao(cpfLogado);
	
			cessaoDeUso.setBemImovel(cessaoDeUsoOld.getBemImovel());
			cessaoDeUso.setInstituicao(cessaoDeUsoOld.getInstituicao());
			cessaoDeUso.setOrgaoCedente(cessaoDeUsoOld.getOrgaoCedente());
			cessaoDeUso.setOrgaoCessionario(cessaoDeUsoOld.getOrgaoCessionario());
			cessaoDeUso.setProtocolo(cessaoDeUsoOld.getProtocolo());
			cessaoDeUso.setLeiBemImovel(cessaoDeUsoOld.getLeiBemImovel());
			cessaoDeUso.setNumeroProjetoDeLei(cessaoDeUsoOld.getNumeroProjetoDeLei());
			cessaoDeUso.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.RASCUNHO.getIndex()));
			cessaoDeUso.setTsInclusao(dataAtual);
			cessaoDeUso.setCpfResponsavelInclusao(cpfLogado);
			cessaoDeUso.setDataInicioVigencia(cessaoDeUsoOld.getDataFinalVigencia());
	
			HibernateUtil.currentTransaction();
			cessaoDeUso = hibernateFactory.getCessaoDeUsoDAO().salvarCessaoDeUso(cessaoDeUso);
			hibernateFactory.getCessaoDeUsoDAO().alterar(cessaoDeUsoOld);
			HibernateUtil.commitTransaction();
			log4j.debug(Log.FIM_TRANSACAO);
			
		}catch (ApplicationException ae) {
			try {
				log4j.debug(Log.ERRO_ENCERRAMENTO_TRANSACAO);
				HibernateUtil.rollbackTransaction();
			}catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em gerarNovaCessaoDeUso()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				log4j.debug(Log.ERRO_ENCERRAMENTO_TRANSACAO);
				HibernateUtil.rollbackTransaction();
			}catch (Exception ex) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em gerarNovaCessaoDeUso()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao gerar Nova Cessão De Uso" }, e, ApplicationException.ICON_ERRO);
		}	
		return cessaoDeUso;
	}

	
	/**
	 * Verifica se o Imóvel informado está totalmente cedido para um órgão.
	 * Se estiver, retorna o(s) órgão(s) para o qual está cedido.
	 * @param cessaoDeUso, contendo codBemImovelSimpl e código da cessão
	 * @return String
	 * @throws ApplicationException 
	 */
	public static String verificarCessaoTotalBemImovel(CessaoDeUso cessaoDeUso) throws ApplicationException {
		try {
			Collection<Orgao> list = hibernateFactory.getItemCessaoDeUsoDAO().verificaPercentualCedido(cessaoDeUso);
			if (list != null && list.size() > 0) {
				String cedidoPara = "";
				for (Orgao orgao2 : list) {
					cedidoPara = cedidoPara.concat(orgao2.getSiglaDescricao()).concat(" | ");
				}
				cedidoPara = cedidoPara.substring(0, cedidoPara.length()-2);
				return cedidoPara;
			}
			return null;
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "cessão total" }, e);
		}
	}

	
	public static void excluirCessaoDeUsoBemImovelRascunhoRenovacao(CessaoDeUso cessaoDeUso) throws ApplicationException {
		cessaoDeUso.setCodCessaoDeUso(null);
		cessaoDeUso.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.RASCUNHO.getIndex()));
		Collection<CessaoDeUso> list = verificarCessaoDeUsoByBemImovelStatusTermo(cessaoDeUso); 
		if (list.size() > 0) {
			for (Iterator<CessaoDeUso> iterator  = list.iterator(); iterator.hasNext();) {
				CessaoDeUso cessaoDeUsoDB = (CessaoDeUso) iterator .next();
				excluirCessaoDeUso(cessaoDeUsoDB);
			}
		}
		cessaoDeUso.setStatusTermo(CadastroFacade.obterStatusTermo(Dominios.statusTermo.EM_RENOVACAO.getIndex()));
		list = verificarCessaoDeUsoByBemImovelStatusTermo(cessaoDeUso); 
		if (list.size() > 0) {
			for (Iterator<CessaoDeUso> iterator  = list.iterator(); iterator.hasNext();) {
				CessaoDeUso cessaoDeUsoDB = (CessaoDeUso) iterator .next();
				excluirCessaoDeUso(cessaoDeUsoDB);
			}
		}
	}

	/**
	 * Salva Cessão de Uso, Itens e Assinaturas.
	 * @param cessaoDeUso
	 * @return
	 * @throws ApplicationException 
	 */
	public static CessaoDeUso salvarCessaoDeUsoComFilhos(CessaoDeUso cessaoDeUso) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			
			if (cessaoDeUso.getCodCessaoDeUso() == null){
				cessaoDeUso = hibernateFactory.getCessaoDeUsoDAO().salvarCessaoDeUso(cessaoDeUso);
			}else{
				hibernateFactory.getCessaoDeUsoDAO().alterar(cessaoDeUso);
			}

			for (ItemCessaoDeUso itemCessaoDeUso : cessaoDeUso.getListaItemCessaoDeUso()) {
				itemCessaoDeUso.setCessaoDeUso(cessaoDeUso);
				hibernateFactory.getItemCessaoDeUsoDAO().salvar(itemCessaoDeUso);
			}

			for (AssinaturaCessaoDeUso assinaturaCessaoDeUso : cessaoDeUso.getListaAssinaturaCessaoDeUso()) {
				assinaturaCessaoDeUso.setCessaoDeUso(cessaoDeUso);
				hibernateFactory.getAssinaturaCessaoDeUsoDAO().salvar(assinaturaCessaoDeUso);
			}

			HibernateUtil.commitTransaction();
			return cessaoDeUso;
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarCessaoDeUsoComFilhos()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em salvarCessaoDeUsoComFilhos()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao incluir Cessão De Uso Com Filhos" }, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Realiza a validação da disponibilidade do item para Operacoes (doação, transferência...)
	 * @param codBemImovel: Nr do Bem Imóvel
	 * @param tipoOperacao: 1 - Cessão
	 * 						2 - Doação
	 * 						3 - Transferência
	 * @param codigo: código da operação
	 * @return String: se a área solicitado estiver disponível, retorna null, senão retorna por quem está sendo utilizado; 
	 * @throws ApplicationException 
	 */
	public static String validaDisponibilidade(Integer codBemImovel, Integer codigo, Integer tipoOperacao) throws ApplicationException {
		
		BemImovel bemImovel = hibernateFactory.getBemImovelDAO().obterCompleto(codBemImovel);
		StatusTermo statusVigente = CadastroFacade.obterStatusTermo(Dominios.statusTermo.VIGENTE.getIndex());
		
		String orgaoRespCess = "";
		BigDecimal valorComprometido = new BigDecimal(0);
		BigDecimal valorDisponivel = new BigDecimal(0);
		ArrayList<String> validacao = new ArrayList<String>();
		switch (tipoOperacao) {
		case 1: //Cessão
			Collection<ItemCessaoDeUso> listItemC = hibernateFactory.getItemCessaoDeUsoDAO().listar(codigo);
			orgaoRespCess = "";
			for (ItemCessaoDeUso itemAvaliado : listItemC) {
				valorComprometido = new BigDecimal(0);
				if (itemAvaliado.getTipo().compareTo(Integer.valueOf(Dominios.tipoOperacaoBemImovel.EDIFICACAO.getIndex())) == 0) {
					valorDisponivel =  hibernateFactory.getEdificacaoDAO().obter(itemAvaliado.getEdificacao().getCodEdificacao()).getAreaConstruida();
					orgaoRespCess = obterValorComprometido(codBemImovel, itemAvaliado.getEdificacao().getCodEdificacao(), statusVigente, orgaoRespCess);
					valorComprometido = new BigDecimal(orgaoRespCess.substring(0, orgaoRespCess.indexOf("@")));
					orgaoRespCess = orgaoRespCess.substring(orgaoRespCess.indexOf("@")+2, orgaoRespCess.length());
					valorDisponivel = valorDisponivel.subtract(valorComprometido.add(itemAvaliado.getAreaMetroQuadrado()));
					if (valorDisponivel.compareTo(new BigDecimal(0)) < 0) {
						validacao.add(orgaoRespCess);
					} 
				}
				if (itemAvaliado.getTipo().compareTo(Integer.valueOf(Dominios.tipoOperacaoBemImovel.TERRENO.getIndex())) == 0) {
					if (bemImovel.getAreaConstruida() != null && bemImovel.getAreaTerreno() != null) {
						valorDisponivel = bemImovel.getAreaConstruida().add(bemImovel.getAreaTerreno());
					} else if (bemImovel.getAreaConstruida() == null && bemImovel.getAreaTerreno() != null) {
						valorDisponivel = bemImovel.getAreaTerreno();
					} else if (bemImovel.getAreaConstruida() != null && bemImovel.getAreaTerreno() == null) {
						valorDisponivel = bemImovel.getAreaConstruida();
					}
					orgaoRespCess = obterValorComprometido(codBemImovel, null, statusVigente, orgaoRespCess);
					valorComprometido = new BigDecimal(orgaoRespCess.substring(0, orgaoRespCess.indexOf("@")));
					orgaoRespCess = orgaoRespCess.substring(orgaoRespCess.indexOf("@")+2, orgaoRespCess.length());
					valorDisponivel = valorDisponivel.subtract(valorComprometido.add(itemAvaliado.getAreaMetroQuadrado()));
					if (valorDisponivel.compareTo(new BigDecimal(0)) < 0) {
						validacao.add(orgaoRespCess);
					} 
				}
				if (itemAvaliado.getTipo().compareTo(Integer.valueOf(Dominios.tipoOperacaoBemImovel.TOTAL.getIndex())) == 0) {
					/*Collection<Edificacao> listEdificacao = hibernateFactory.getEdificacaoDAO().listarComRelacionamentos(codBemImovel, null, null);
					for (Edificacao edificacao : listEdificacao) {
						valorDisponivel = valorDisponivel.add(edificacao.getAreaConstruida());
					}*/
					if (bemImovel.getAreaConstruida() != null && bemImovel.getAreaTerreno() != null) {
						valorDisponivel = valorDisponivel.add(bemImovel.getAreaConstruida().add(bemImovel.getAreaTerreno()));
					} else if (bemImovel.getAreaConstruida() == null && bemImovel.getAreaTerreno() != null) {
						valorDisponivel = valorDisponivel.add(bemImovel.getAreaTerreno());
					} else if (bemImovel.getAreaConstruida() != null && bemImovel.getAreaTerreno() == null) {
						valorDisponivel = valorDisponivel.add(bemImovel.getAreaConstruida());
					}
					orgaoRespCess = obterValorComprometido(codBemImovel, null, statusVigente, orgaoRespCess);
					valorComprometido = new BigDecimal(orgaoRespCess.substring(0, orgaoRespCess.indexOf("@")));
					orgaoRespCess = orgaoRespCess.substring(orgaoRespCess.indexOf("@")+2, orgaoRespCess.length());
					valorDisponivel = valorDisponivel.subtract(valorComprometido.add(itemAvaliado.getAreaMetroQuadrado()));
					if (valorDisponivel.compareTo(new BigDecimal(0)) < 0) {
						validacao.add(orgaoRespCess);
					} 
				}
			}
			break;
		case 2: //Doação
			Collection<ItemDoacao> listItemD = hibernateFactory.getItemDoacaoDAO().listar(codigo);
			orgaoRespCess = "";
			for (ItemDoacao itemAvaliado : listItemD) {
				valorComprometido = new BigDecimal(0);
				if (itemAvaliado.getTipo().compareTo(Integer.valueOf(Dominios.tipoOperacaoBemImovel.EDIFICACAO.getIndex())) == 0) {
					valorDisponivel =  hibernateFactory.getEdificacaoDAO().obter(itemAvaliado.getEdificacao().getCodEdificacao()).getAreaConstruida();
					orgaoRespCess = obterValorComprometido(codBemImovel, itemAvaliado.getEdificacao().getCodEdificacao(), statusVigente, orgaoRespCess);
					valorComprometido = new BigDecimal(orgaoRespCess.substring(0, orgaoRespCess.indexOf("@")));
					orgaoRespCess = orgaoRespCess.substring(orgaoRespCess.indexOf("@")+1, orgaoRespCess.length());
					valorDisponivel = valorDisponivel.subtract(valorComprometido.add(itemAvaliado.getDoacaoMetros()));
					if (valorDisponivel.compareTo(new BigDecimal(0)) < 0) {
						validacao.add(orgaoRespCess);
					} 
				}
				if (itemAvaliado.getTipo().compareTo(Integer.valueOf(Dominios.tipoOperacaoBemImovel.TERRENO.getIndex())) == 0) {
					if (bemImovel.getAreaConstruida() != null && bemImovel.getAreaTerreno() != null) {
						valorDisponivel = bemImovel.getAreaConstruida().add(bemImovel.getAreaTerreno());
					} else if (bemImovel.getAreaConstruida() == null && bemImovel.getAreaTerreno() != null) {
						valorDisponivel = bemImovel.getAreaTerreno();
					} else if (bemImovel.getAreaConstruida() != null && bemImovel.getAreaTerreno() == null) {
						valorDisponivel = bemImovel.getAreaConstruida();
					}
					orgaoRespCess = obterValorComprometido(codBemImovel, null, statusVigente, orgaoRespCess);
					valorComprometido = new BigDecimal(orgaoRespCess.substring(0, orgaoRespCess.indexOf("@")));
					orgaoRespCess = orgaoRespCess.substring(orgaoRespCess.indexOf("@")+1, orgaoRespCess.length());
					valorDisponivel = valorDisponivel.subtract(valorComprometido.add(itemAvaliado.getDoacaoMetros()));
					if (valorDisponivel.compareTo(new BigDecimal(0)) < 0) {
						validacao.add(orgaoRespCess);
					} 
				}
				if (itemAvaliado.getTipo().compareTo(Integer.valueOf(Dominios.tipoOperacaoBemImovel.TOTAL.getIndex())) == 0) {
					/*Collection<Edificacao> listEdificacao = hibernateFactory.getEdificacaoDAO().listarComRelacionamentos(codBemImovel, null, null);
					for (Edificacao edificacao : listEdificacao) {
						valorDisponivel = valorDisponivel.add(edificacao.getAreaConstruida());
					}*/
					if (bemImovel.getAreaConstruida() != null && bemImovel.getAreaTerreno() != null) {
						valorDisponivel = valorDisponivel.add(bemImovel.getAreaConstruida().add(bemImovel.getAreaTerreno()));
					} else if (bemImovel.getAreaConstruida() == null && bemImovel.getAreaTerreno() != null) {
						valorDisponivel = valorDisponivel.add(bemImovel.getAreaTerreno());
					} else if (bemImovel.getAreaConstruida() != null && bemImovel.getAreaTerreno() == null) {
						valorDisponivel = valorDisponivel.add(bemImovel.getAreaConstruida());
					}
					orgaoRespCess = obterValorComprometido(codBemImovel, null, statusVigente, orgaoRespCess);
					valorComprometido = new BigDecimal(orgaoRespCess.substring(0, orgaoRespCess.indexOf("@")));
					orgaoRespCess = orgaoRespCess.substring(orgaoRespCess.indexOf("@")+1, orgaoRespCess.length());
					valorDisponivel = valorDisponivel.subtract(valorComprometido.add(itemAvaliado.getDoacaoMetros()));
					if (valorDisponivel.compareTo(new BigDecimal(0)) < 0) {
						validacao.add(orgaoRespCess);
					} 
				}
			}
			break;
		case 3: //Transferência
			Collection<ItemTransferencia> listItemT = hibernateFactory.getItemTransferenciaDAO().listar(codigo);
			orgaoRespCess = "";
			for (ItemTransferencia itemAvaliado : listItemT) {
				valorComprometido = new BigDecimal(0);
				if (itemAvaliado.getTipo().compareTo(Integer.valueOf(Dominios.tipoOperacaoBemImovel.EDIFICACAO.getIndex())) == 0) {
					valorDisponivel =  hibernateFactory.getEdificacaoDAO().obter(itemAvaliado.getEdificacao().getCodEdificacao()).getAreaConstruida();
					orgaoRespCess = obterValorComprometido(codBemImovel, itemAvaliado.getEdificacao().getCodEdificacao(), statusVigente, orgaoRespCess);
					valorComprometido = new BigDecimal(orgaoRespCess.substring(0, orgaoRespCess.indexOf("@")));
					orgaoRespCess = orgaoRespCess.substring(orgaoRespCess.indexOf("@")+1, orgaoRespCess.length());
					valorDisponivel = valorDisponivel.subtract(valorComprometido.add(itemAvaliado.getTransferenciaMetros()));
					if (valorDisponivel.compareTo(new BigDecimal(0)) < 0) {
						validacao.add(orgaoRespCess);
					} 
				}
				if (itemAvaliado.getTipo().compareTo(Integer.valueOf(Dominios.tipoOperacaoBemImovel.TERRENO.getIndex())) == 0) {
					if (bemImovel.getAreaConstruida() != null && bemImovel.getAreaTerreno() != null) {
						valorDisponivel = bemImovel.getAreaConstruida().add(bemImovel.getAreaTerreno());
					} else if (bemImovel.getAreaConstruida() == null && bemImovel.getAreaTerreno() != null) {
						valorDisponivel = bemImovel.getAreaTerreno();
					} else if (bemImovel.getAreaConstruida() != null && bemImovel.getAreaTerreno() == null) {
						valorDisponivel = bemImovel.getAreaConstruida();
					}
					orgaoRespCess = obterValorComprometido(codBemImovel, null, statusVigente, orgaoRespCess);
					valorComprometido = new BigDecimal(orgaoRespCess.substring(0, orgaoRespCess.indexOf("@")));
					orgaoRespCess = orgaoRespCess.substring(orgaoRespCess.indexOf("@")+1, orgaoRespCess.length());
					valorDisponivel = valorDisponivel.subtract(valorComprometido.add(itemAvaliado.getTransferenciaMetros()));
					if (valorDisponivel.compareTo(new BigDecimal(0)) < 0) {
						validacao.add(orgaoRespCess);
					} 
				}
				if (itemAvaliado.getTipo().compareTo(Integer.valueOf(Dominios.tipoOperacaoBemImovel.TOTAL.getIndex())) == 0) {
					if (bemImovel.getAreaConstruida() != null && bemImovel.getAreaTerreno() != null) {
						valorDisponivel = valorDisponivel.add(bemImovel.getAreaConstruida().add(bemImovel.getAreaTerreno()));
					} else if (bemImovel.getAreaConstruida() == null && bemImovel.getAreaTerreno() != null) {
						valorDisponivel = valorDisponivel.add(bemImovel.getAreaTerreno());
					} else if (bemImovel.getAreaConstruida() != null && bemImovel.getAreaTerreno() == null) {
						valorDisponivel = valorDisponivel.add(bemImovel.getAreaConstruida());
					}
					orgaoRespCess = obterValorComprometido(codBemImovel, null, statusVigente, orgaoRespCess);
					valorComprometido = new BigDecimal(orgaoRespCess.substring(0, orgaoRespCess.indexOf("@")));
					orgaoRespCess = orgaoRespCess.substring(orgaoRespCess.indexOf("@")+1, orgaoRespCess.length());
					valorDisponivel = valorDisponivel.subtract(valorComprometido.add(itemAvaliado.getTransferenciaMetros()));
					if (valorDisponivel.compareTo(new BigDecimal(0)) < 0) {
						validacao.add(orgaoRespCess);
					} 
				}
			}
			break;
		}
			
		if (validacao == null || validacao.size() == 0) {
			return null; 
		} else {
			return trataRetorno(orgaoRespCess);
		}
	}

	/**
	 * Recupera o valor já comprometido em outros termos (Doação, Cessão e Transferência) que estão vigentes para esse BI/Edificação
	 * @param codBemImovel
	 * @param codEdificacao
	 * @param statusVigente
	 * @param valorComprometido
	 * @param orgaoRespCess
	 * @return informações para quem está comprometido para exibir caso não o valor solicitado não esteja disponível 
	 * @throws ApplicationException
	 */
	private static String obterValorComprometido(Integer codBemImovel, Integer codEdificacao, StatusTermo statusVigente, String orgaoRespCess) throws ApplicationException {
		String orgaoD = "#D";
		String orgaoC = "#C";
		String orgaoT = "#T";
		BigDecimal valorComprometido = new BigDecimal(0);
		
		//verifica se o Bem Imóvel está DOADO
		Collection<ItemDoacao> listDoacao = hibernateFactory.getItemDoacaoDAO().listarByBemImovelEdificacaoStatus(codBemImovel, codEdificacao, statusVigente);
		for (ItemDoacao item : listDoacao) {
			valorComprometido = valorComprometido.add(item.getDoacaoMetros());
			orgaoD = orgaoD.concat(item.getDoacao().getNumeroTermo().toString()).concat(";");
			orgaoD = orgaoD.concat(item.getDoacao().getOrgaoResponsavel().getSiglaDescricao()).concat(";");
			orgaoD = orgaoD.concat(item.getDoacaoMetros().toString()).concat(",");
		}
		
		//verifica se o Bem Imóvel está CEDIDO 
		Collection<ItemCessaoDeUso> listCessao = hibernateFactory.getItemCessaoDeUsoDAO().listarByBemImovelEdificacaoStatus(codBemImovel, codEdificacao, statusVigente);
		for (ItemCessaoDeUso item : listCessao) {
			valorComprometido = valorComprometido.add(item.getAreaMetroQuadrado());
			orgaoC = orgaoC.concat(item.getCessaoDeUso().getNumeroTermo().toString()).concat(";");
			orgaoC = orgaoC.concat(item.getCessaoDeUso().getOrgaoCessionario().getSiglaDescricao()).concat(";");
			orgaoC = orgaoC.concat(item.getAreaMetroQuadrado().toString()).concat(",");
		}

		//verifica se o Bem Imóvel está TRANSFERIDO 
		Collection<ItemTransferencia> listTransferencia = hibernateFactory.getItemTransferenciaDAO().listarByBemImovelEdificacaoStatus(codBemImovel, codEdificacao, statusVigente);
		for (ItemTransferencia item : listTransferencia) {
			valorComprometido = valorComprometido.add(item.getTransferenciaMetros());
			orgaoT = orgaoT.concat(item.getTransferencia().getNumeroTermo().toString()).concat(";");
			orgaoT = orgaoT.concat(item.getTransferencia().getOrgaoCessionario().getSiglaDescricao()).concat(";");
			orgaoT = orgaoT.concat(item.getTransferenciaMetros().toString()).concat(",");
		}
		
		orgaoRespCess = orgaoRespCess.concat(String.valueOf(valorComprometido)).concat("@");
		orgaoRespCess = orgaoRespCess.concat(orgaoC.concat(orgaoD).concat(orgaoT));

		return orgaoRespCess;
	}

	/**
	 * Formata as informações para quem está comprometido para exibir caso não o valor solicitado não esteja disponível
	 * @param orgaoRespCess
	 * @return
	 * @throws ApplicationException
	 */
	private static String trataRetorno(String orgaoRespCess) throws ApplicationException {
		String[] aux = null;
		String[] aux2 = null;
		String listOrgaos = null;
		String orgaoD = "";
		String orgaoC = "";
		String orgaoT = "";
		
		String[] result = orgaoRespCess.split("#");
		for (String tipo : result) {
			if (tipo.startsWith("D")) {
				orgaoD = tipo.substring(1,tipo.length());
				//Doação
				aux = null;
				aux2 = null;
				if (orgaoD != null && orgaoD.contains(",")) {
					aux = orgaoD.split(",");
					for (String valor : aux) {
						aux2 = valor.split(";");
						listOrgaos = "N° Termo de Doação: ".concat(aux2[0]).concat(" Órgão Responsável: ").concat(aux2[1]);
						listOrgaos = listOrgaos.concat(" Quantidade: ").concat(Valores.formatarParaDecimal(new BigDecimal(aux2[2]), 2).toString()).concat(" m². §");
					}
				}
			} else if (tipo.startsWith("C")) {
				orgaoC = tipo.substring(1,tipo.length());
				//Cessão de Uso
				aux = null;
				aux2 = null;
				if (orgaoC != null && orgaoC.contains(",")) {
					aux = orgaoC.split(",");
					for (String valor : aux) {
						aux2 = valor.split(";");
						listOrgaos = "N° Termo de Cessão de Uso: ".concat(aux2[0]).concat(" Órgão Cessionário: ").concat(aux2[1]);
						listOrgaos = listOrgaos.concat(" Quantidade: ").concat(Valores.formatarParaDecimal(new BigDecimal(aux2[2]), 2).toString()).concat(" m². §");
					}
				}
			} else if (tipo.startsWith("T")) {
				orgaoT = tipo.substring(1,tipo.length());
				//Transferência
				aux = null;
				aux2 = null;
				if (orgaoT != null && orgaoT.contains(",")) {
					aux = orgaoT.split(",");
					for (String valor : aux) {
						aux2 = valor.split(";");
						listOrgaos = "N° Termo de Transferência: ".concat(aux2[0]).concat(" Órgão Cessionário: ").concat(aux2[1]);
						listOrgaos = listOrgaos.concat(" Quantidade: ").concat(Valores.formatarParaDecimal(new BigDecimal(aux2[2]), 2).toString()).concat(" m². §");
					}
				}
			}
		}

		return listOrgaos.substring(0, listOrgaos.length()-1);
	}

	/**
	 * Lista edificações por bem imóvel para Operacoes (doação, transferência...)
	 * @return Collection<ItemComboDTO>
	 * @throws ApplicationException
	 */
	public static Collection<ItemComboDTO> listarEdificacaoComboParaOperacoes(Integer codBemImovel) throws ApplicationException {
		Collection<Edificacao> lista = hibernateFactory.getEdificacaoDAO().listarByBemImovel(codBemImovel);
		StatusTermo statusVigente = CadastroFacade.obterStatusTermo(Dominios.statusTermo.VIGENTE.getIndex());

		Collection<ItemComboDTO> ret = new ArrayList<ItemComboDTO>();
		String desc = "";
		BigDecimal percentualTotal = new BigDecimal(100);
		BigDecimal percentualDisponivel = new BigDecimal(0);
		BigDecimal valorUtilizado = new BigDecimal(0);
		BigDecimal valorDisponivel = new BigDecimal(0);
		Collection<ItemDoacao> listDoacao = null;
		Collection<ItemCessaoDeUso> listCessao = null;
		Collection<ItemTransferencia> listTransferencia = null;

		for (Edificacao edificacao : lista) {
			listDoacao = hibernateFactory.getItemDoacaoDAO().listarByBemImovelEdificacaoStatus(null, edificacao.getCodEdificacao(), statusVigente);
			for (ItemDoacao item : listDoacao) {
				valorUtilizado = valorUtilizado.add(item.getDoacaoMetros());
			}
			listCessao = hibernateFactory.getItemCessaoDeUsoDAO().listarByBemImovelEdificacaoStatus(null, edificacao.getCodEdificacao(), statusVigente);
			for (ItemCessaoDeUso item : listCessao) {
				valorUtilizado = valorUtilizado.add(item.getAreaMetroQuadrado());
			}
			listTransferencia = hibernateFactory.getItemTransferenciaDAO().listarByBemImovelEdificacaoStatus(null, edificacao.getCodEdificacao(), statusVigente);
			for (ItemTransferencia item : listTransferencia) {
				valorUtilizado = valorUtilizado.add(item.getTransferenciaMetros());
			}

			ItemComboDTO obj = new ItemComboDTO();
			obj.setCodigo(String.valueOf(edificacao.getCodEdificacao()));
			if (edificacao.getAreaConstruida() != null && edificacao.getAreaConstruida().compareTo(new BigDecimal(0)) > 0) {
				if (edificacao.getTipoConstrucao() != null) {
					desc = desc.concat(edificacao.getTipoConstrucao().getDescricao().concat(" - "));
				} else {
					desc = desc.concat(" - ");
				}
				if (edificacao.getTipoEdificacao() != null) {
					desc = desc.concat(edificacao.getTipoEdificacao().getDescricao().concat(" - "));
				} else {
					desc = desc.concat(" - ");
				}
				if (edificacao.getEspecificacao() != null) {
					desc = desc.concat(edificacao.getEspecificacao().concat(" - "));
				} else {
					desc = desc.concat(" - ");
				}
				valorDisponivel = edificacao.getAreaConstruida().subtract(valorUtilizado);
				percentualDisponivel = (percentualTotal.multiply(valorDisponivel)).divide(edificacao.getAreaConstruida());
				desc = desc.concat(String.valueOf(valorDisponivel).concat("m² - "));
				desc = desc.concat(String.valueOf(percentualDisponivel).concat("%"));
			} else {
				desc = desc.concat("**** Não foi possível mensurar, favor ajustar cadastro.****");
			}
			obj.setDescricao(desc);
			ret.add(obj);
		}
		
		return ret;
	}

	public static void excluirDominioParametroVistoria(ParametroVistoria parametroVistoria) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			for (ParametroVistoriaDominio item: parametroVistoria.getListaParametroVistoriaDominio()){
				hibernateFactory.getParametroVistoriaDominioDAO().excluir(item);
			}
			HibernateUtil.commitTransaction();
			
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirDominioParametroVistoria()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirDominioParametroVistoria()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao excluir Dominio Parâmetro Vistoria" }, e, ApplicationException.ICON_ERRO);
		}
	}

	public static Collection<AssinaturaDocTransferencia> verificarDuplicidadeAssinaturaDocTransferencia(AssinaturaDocTransferencia assinatura) throws ApplicationException {
		try {
			return hibernateFactory.getAssinaturaDocTransferenciaDAO().listaVerificacao(assinatura);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.202", new String[] { "assinatura" }, e);
		}
	}

	public static void salvarAssinaturaDocTransferencia(AssinaturaDocTransferencia assinatura) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			hibernateFactory.getAssinaturaDocTransferenciaDAO().salvar(assinatura);
			HibernateUtil.commitTransaction();
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em AssinaturaDocTransferencia()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em AssinaturaDocTransferencia()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao incluir Assinatura do Documento de Transferência" }, e, ApplicationException.ICON_ERRO);
		}
	}

	public static Integer obtemQtdResponsavelMaximoListaAssinatura(AssinaturaDocTransferencia assinatura) throws ApplicationException {
		Integer possui = 0;
		try {
			Collection<AssinaturaDocTransferencia> lista = hibernateFactory.getAssinaturaDocTransferenciaDAO().listaVerificacao(assinatura);
			for (AssinaturaDocTransferencia assinaturaDocTransferencia : lista) {
				if (assinaturaDocTransferencia.getAssinatura().getIndResponsavelMaximo()) {
					possui = possui + 1;
				}
			}
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] { "ao obter quantidade de responsável máximo" }, e);
		}
		return possui;
	}

	public static AssinaturaDocTransferencia obterAssinaturaDocTransferencia(Integer codigo) throws ApplicationException {
		return hibernateFactory.getAssinaturaDocTransferenciaDAO().obter(codigo);
	}

	public static void excluirAssinaturaDocTransferencia(AssinaturaDocTransferencia assinatura) throws ApplicationException {
		try {
			HibernateUtil.currentTransaction();
			hibernateFactory.getAssinaturaDocTransferenciaDAO().excluir(assinatura);
			HibernateUtil.commitTransaction();
		}catch (ApplicationException ae) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirAssinaturaDocTransferencia()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.warn(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e1) {
				throw new ApplicationException("mensagem.erro.geral", new String[]{"Erro ao realizar rollback em excluirAssinaturaDocTransferencia()!"}, ApplicationException.ICON_AVISO);
			}
			log4j.error(e.getMessage(), e);
			throw new ApplicationException("ERRO.201", new String[] { "ao excluir Assinatura do Documento de Transferência" }, e, ApplicationException.ICON_ERRO);
		}
	}

	public static Object listarAssinaturaDocTransferencia(Pagina pag, Transferencia transferencia) throws ApplicationException {
		try {
			Collection<AssinaturaDocTransferencia> itensLista = hibernateFactory.getAssinaturaDocTransferenciaDAO().listar(transferencia.getCodTransferencia());
			List<AssinaturaDTO> lista = new ArrayList<AssinaturaDTO>();
			for (AssinaturaDocTransferencia assinaturaDocTransferencia : itensLista) {
				AssinaturaDTO dto = new AssinaturaDTO();
				dto.setCodigo(assinaturaDocTransferencia.getCodAssinaturaDocTransferencia());
				dto.setOrgao(assinaturaDocTransferencia.getAssinatura().getOrgao().getSiglaDescricao());
				dto.setCargo(assinaturaDocTransferencia.getAssinatura().getCargoAssinatura().getDescricao());
				dto.setNome(assinaturaDocTransferencia.getAssinatura().getNome());
				dto.setRespMaximo(assinaturaDocTransferencia.getAssinatura().getIndResponsavelMaximoDesc());
				lista.add(dto);
			}
			pag.setQuantidade(lista.size());
			pag.setRegistros(lista);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] { "ao listar Assinatura do Documento de Transferência" }, e);
		}
		return pag;
	}

	public static String obterListaFormatadaParametroAgenda() throws ApplicationException {
		String retorno = "";
		try {
			Collection<ParametroAgenda> lista = hibernateFactory.getParametroAgendaDAO().listar();
			for (ParametroAgenda param : lista) {
				if (param.getInstituicao() != null && param.getInstituicao().getCodInstituicao() > 0) {
					retorno = retorno.concat(param.getInstituicao().getCodInstituicao().toString());
					retorno = retorno.concat("=");
					retorno = retorno.concat(param.getTempoCessao().toString());
					retorno = retorno.concat("|");
				}
			}
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201", new String[] { "ao listar Parâmetros da Agenda" }, e);
		}
		return retorno;
	}

}

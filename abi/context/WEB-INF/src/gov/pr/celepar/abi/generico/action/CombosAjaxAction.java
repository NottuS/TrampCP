package gov.pr.celepar.abi.generico.action;

import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.facade.OperacaoFacade;
import gov.pr.celepar.abi.pojo.Edificacao;
import gov.pr.celepar.abi.pojo.Usuario;
import gov.pr.celepar.abi.pojo.Vistoriador;
import gov.pr.celepar.abi.util.XmlBuilder;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CombosAjaxAction extends BaseDispatchAction{

	private static final Logger log = Logger.getLogger(CombosAjaxAction.class);

	private static final String ITENS = "itens"; 
	private static final String ITEM  = "item";
	private static final String XML   = "xml";
	private static final String ERRO_COMBO_AJAX =	"mensagem.erro";	
	
	/**
	 * Construtor
	 */
	public CombosAjaxAction() {
		super();
		log.debug(this.getClass().getName()+" criado");
	}

	/**
	 * Gera tag de combos
	 * @author kolling
	 * @param XmlBuilder builder
	 * @param Object cod
	 * @param Object descr
	 */
	private void generateTag(final XmlBuilder builder, final Object cod, final Object descr){
		builder.addClosedNode(ITEM, "cod=\"" + cod + "\" descr=\"" + descr + "\"");
	}
	
	/**
	  * Carregar combo Edificacao. <br>
	  * @author tatianapires
	  * @since 29/06/2011
	  * @param mapping
	  * @param actionForm
	  * @param request
	  * @param response
	  * @return ActionForward
	  * @throws ApplicationException
	  */
	 public ActionForward carregarComboEdificacao(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response)  throws ApplicationException{

		 final XmlBuilder builder = new XmlBuilder();
		 builder.addNode(ITENS);
		 generateTag(builder, "", "- Todos -");
		 
		 Integer codBemImovel = StringUtils.isNotBlank(request.getParameter("codBemImovel")) ? Integer.valueOf(request.getParameter("codBemImovel")) : null;
		 Integer codInstituicao = StringUtils.isNotBlank(request.getParameter("codInstituicao")) ? Integer.valueOf(request.getParameter("codInstituicao")) : null;
		 if (codInstituicao == null){
			 Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
			 codInstituicao = usuario.getInstituicao().getCodInstituicao();	 
		 }
		 List<Edificacao> lstEdificacao = OperacaoFacade.listarEdificacaoComVinculoBemImovel(codBemImovel, codInstituicao);
		 for(Edificacao edificacao : lstEdificacao){
			 generateTag(builder, edificacao.getCodEdificacao(), (edificacao.getEspecificacao() == null ? edificacao.getCodEdificacao().toString().concat("- sem especificação -") : edificacao.getEspecificacao()));
		 }

		 builder.closeNode(ITENS);
		 request.setAttribute(XML, builder.toString());
		 
		 try {
			 final PrintWriter pWriter = response.getWriter();
			 pWriter.write(builder.toString());
		 } catch (IOException e) {
			 log.fatal("Erro ao gerar a combo de Edificacao", e);
			 throw new ApplicationException(ERRO_COMBO_AJAX);
		 }
		 
		 return null;
	 }
		/**
	  * Carregar combo Vistoriador. <br>
	  * @param mapping
	  * @param actionForm
	  * @param request
	  * @param response
	  * @return ActionForward
	  * @throws ApplicationException
	  */
	 public ActionForward carregarComboVistoriador(final ActionMapping mapping, final ActionForm actionForm, final HttpServletRequest request, final HttpServletResponse response)  throws ApplicationException{

		 final XmlBuilder builder = new XmlBuilder();
		 builder.addNode(ITENS);
		 generateTag(builder, "", "- Selecione -");
		 
		 Integer codInstituicao = StringUtils.isNotBlank(request.getParameter("codInstituicao")) ? Integer.valueOf(request.getParameter("codInstituicao")) : null;
		 if (codInstituicao == null){
			 Usuario usuario = CadastroFacade.obterUsuarioPorIdSentinela(SentinelaComunicacao.getInstance(request).getCodUsuario());
			 codInstituicao = usuario.getInstituicao().getCodInstituicao();	 
		 }
		 List<Vistoriador> lstVistoriador = OperacaoFacade.listarVistoriador(codInstituicao);
		 for(Vistoriador vistoriador : lstVistoriador){
			 generateTag(builder, vistoriador.getCodVistoriador(), (vistoriador.getNome() == null ? "-" : vistoriador.getNome()));
		 }

		 builder.closeNode(ITENS);
		 request.setAttribute(XML, builder.toString());
		 
		 try {
			 final PrintWriter pWriter = response.getWriter();
			 pWriter.write(builder.toString());
		 } catch (IOException e) {
			 log.fatal("Erro ao gerar a combo de Vistoriador", e);
			 throw new ApplicationException(ERRO_COMBO_AJAX);
		 }
		 
		 return null;
	 }

}

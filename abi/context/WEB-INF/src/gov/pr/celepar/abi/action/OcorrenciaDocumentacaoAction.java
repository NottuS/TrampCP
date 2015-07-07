package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.OcorrenciaDocumentacaoForm;
import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.Documentacao;
import gov.pr.celepar.abi.pojo.Notificacao;
import gov.pr.celepar.abi.pojo.OcorrenciaDocumentacao;
import gov.pr.celepar.abi.pojo.TipoDocumentacao;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.framework.util.StringUtil;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaInterface;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Luciana R. Bélico
 * @version 1.0
 * @since 22/01/2010
 * 
 * Classe Action:
 * Responsável por manipular as requisições dos usuários
 */

public class OcorrenciaDocumentacaoAction extends BaseDispatchAction {

	private static Logger log4j = Logger.getLogger(OcorrenciaDocumentacaoAction.class);

	private void verificarTodasOperacoes( HttpServletRequest request ){
		SentinelaInterface com = SentinelaComunicacao.getInstance(request);
		String operacoes = com.getOperacoes();
		log4j.info("Operações do usuário "+com.getLogin()+ ": " + operacoes);
		verificarOperacao('O', operacoes, request); //incluir ocorrencia
	}
	
	private void verificarOperacao(char operacao, String operacoes, HttpServletRequest request){
		try {
			request.setAttribute(String.valueOf(operacao), Boolean.valueOf( operacoes.indexOf(operacao)!= -1 ) );
		}catch (Exception e) {
			request.setAttribute(String.valueOf(operacao), "false" );
			log4j.info("Não autorizado para a operação: "+ operacao, e );			
		}
	}
	
	/**
	 * Realiza carga da página de listagem manutenção de ocorrencia do imovel.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward carregarPgEditOcorrenciaDocumentacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try {
			
			verificarTodasOperacoes( request );
		
			if (! Boolean.valueOf(request.getAttribute("O").toString())){
				setActionForward(mapping.findForward("pgEntrada"));
				throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
			}
			
			setActionForward(mapping.findForward("pgEditOcorrenciaDocumentacao"));
			this.carregarListaOcorrenciaDocumentacao(form, request);
			
		} catch (ApplicationException appEx) {
			throw appEx;

		} catch (Exception e) {
			throw new ApplicationException("ERRO.200", new String[]{"edição","ocorrência documentação"}, e);
		}	

		return getActionForward();
    }

	public ActionForward salvarOcorrenciaDocumentacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {
		
		verificarTodasOperacoes( request );
		
		if (! Boolean.valueOf(request.getAttribute("O").toString())){
			setActionForward(mapping.findForward("pgEntrada"));
			throw new ApplicationException("mensagem.erro.acesso", ApplicationException.ICON_ERRO);
		}
		
		OcorrenciaDocumentacaoForm ocorrenciaDocumentacaoForm = (OcorrenciaDocumentacaoForm) form;
		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date dataInclusao = new Date();
		setActionForward(mapping.findForward("pgEditOcorrenciaDocumentacao"));
		
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			this.carregarListaOcorrenciaDocumentacao(ocorrenciaDocumentacaoForm, request);
			return getActionForward();
		}	
		
		//validações de campos obrigatórios que não foi possível realizar com struts validator
		
		if (ocorrenciaDocumentacaoForm.getRelDocumentacao() != null  && ocorrenciaDocumentacaoForm.getRelDocumentacao().equals("on"))
		{
			if(ocorrenciaDocumentacaoForm.getDocExistente() != null && ocorrenciaDocumentacaoForm.getDocExistente().equals("on"))
			{
				//documentacao
				if(ocorrenciaDocumentacaoForm.getDocumentacao() == null || ocorrenciaDocumentacaoForm.getDocumentacao().equals(""))
				{
					throw new ApplicationException("ERRO.ocorrenciaDocumentacao.requerido", new String[]{"Documentação"});
				}
			}
			else
			{
				//tipo documentação
				//arquivo
				if(ocorrenciaDocumentacaoForm.getTipoDocumentacao() == null || ocorrenciaDocumentacaoForm.getTipoDocumentacao().equals(""))
				{
					throw new ApplicationException("ERRO.ocorrenciaDocumentacao.requerido", new String[]{"Tipo Documentação"});
				}
				if(ocorrenciaDocumentacaoForm.getAnexo().getFileName().equals(""))
				{
					throw new ApplicationException("ERRO.ocorrenciaDocumentacao.requerido", new String[]{"Arquivo"});
				}
				
				if(Integer.parseInt(ocorrenciaDocumentacaoForm.getTipoDocumentacao()) == Dominios.TIPO_DOCUMENTACAO_NOTIFICACAO)
				{
					try{
						if(ocorrenciaDocumentacaoForm.getTsNotificacao() == null || ocorrenciaDocumentacaoForm.getTsNotificacao().equals(""))
						{
							throw new ApplicationException("ERRO.ocorrenciaDocumentacao.requerido", new String[]{"Data Notificação"});
						}

						Date dataNotificacao = sdf.parse(ocorrenciaDocumentacaoForm.getTsNotificacao());
						if (dataNotificacao.after(dataInclusao))
						{
							throw new ApplicationException("ERRO.ocorrenciaDocumentacao.data", new String[]{"Data Notificação"});
						}
						if(ocorrenciaDocumentacaoForm.getTsPrazoNotificacao() == null || ocorrenciaDocumentacaoForm.getTsPrazoNotificacao().equals(""))
						{
							throw new ApplicationException("ERRO.ocorrenciaDocumentacao.requerido", new String[]{"Prazo da Notificação"});
						}

						Date prazoNotificacao = sdf.parse(ocorrenciaDocumentacaoForm.getTsPrazoNotificacao());
						if (prazoNotificacao.after(dataInclusao))
						{
							throw new ApplicationException("ERRO.ocorrenciaDocumentacao.data", new String[]{"Prazo da Notificação"});
						}
						if(ocorrenciaDocumentacaoForm.getDescricaoNotificacao() == null || ocorrenciaDocumentacaoForm.getDescricaoNotificacao().equals(""))
						{
							throw new ApplicationException("ERRO.ocorrenciaDocumentacao.requerido", new String[]{"Descrição da Notificação"});
						}
						if(ocorrenciaDocumentacaoForm.getTsSolucao() != null && !ocorrenciaDocumentacaoForm.getTsSolucao().equals(""))
						{
							Date dataSolucao = sdf.parse(ocorrenciaDocumentacaoForm.getTsSolucao());
							if (dataSolucao.after(dataInclusao))
							{
								throw new ApplicationException("ERRO.ocorrenciaDocumentacao.data", new String[]{"Data da Solução"});
							}
							if (dataSolucao.before(dataNotificacao))
							{
								throw new ApplicationException("ERRO.ocorrenciaDocumentacao.data2", new String[]{"Data da Solução", "Data da Notificação"});
							}
							if(ocorrenciaDocumentacaoForm.getMotivoSolucao() == null || ocorrenciaDocumentacaoForm.getMotivoSolucao().equals(""))
							{
								throw new ApplicationException("ERRO.ocorrenciaDocumentacao.requerido", new String[]{"Motivo da Solução"});
							}
						}
					}
					catch (ParseException e)
					{
						throw new ApplicationException("ERRO.201", new String[]{"ao incluir Ocorrência de Documentação"}, e);
					}
				}
			}
		}
		
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			throw new ApplicationException("AVISO.200");
		}	
		
		try	{			
			OcorrenciaDocumentacao ocorrenciaDocumentacao = new OcorrenciaDocumentacao();
			Documentacao documentacao= null;
			Notificacao notificacao=null;
			BemImovel bemImovel = CadastroFacade.obterBemImovel(Integer.valueOf(ocorrenciaDocumentacaoForm.getCodBemImovel()));
			ocorrenciaDocumentacao.setBemImovel(bemImovel);
			ocorrenciaDocumentacao.setDescricao(ocorrenciaDocumentacaoForm.getDescricaoOcorrencia().trim());
			
			ocorrenciaDocumentacao.setTsInclusao(dataInclusao);
			ocorrenciaDocumentacao.setCpfResponsavel(sentinelaInterface.getCpf());
			ocorrenciaDocumentacao.setTsAtualizacao(dataInclusao);

			if (ocorrenciaDocumentacaoForm.getRelDocumentacao() != null  && ocorrenciaDocumentacaoForm.getRelDocumentacao().equals("on"))
			{
				if(ocorrenciaDocumentacaoForm.getDocExistente() != null && ocorrenciaDocumentacaoForm.getDocExistente().equals("on"))
				{
					//documentação ja existente, carregar ela para dentro do objeto ocorrenciaDocumentacao.documentacao
					
					ocorrenciaDocumentacao.setDocumentacao(CadastroFacade.obterDocumentacao(Integer.valueOf(ocorrenciaDocumentacaoForm.getDocumentacao())));
					
				}
				else{
					//relacionado a documentação porém ela nao existe, criar um novo objeto documentação
					SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
					documentacao = new Documentacao();
					documentacao.setBemImovel(bemImovel);
					documentacao.setCpfResponsavel(sentinelaInterface.getCpf());
					if (ocorrenciaDocumentacaoForm.getDocumentacaoOriginal()!= null && !ocorrenciaDocumentacaoForm.getDocumentacaoOriginal().equals("") )
					{
						// carregar documentação original e salvar em documentacao.documentacao
						documentacao.setDocumentacao(CadastroFacade.obterDocumentacao(Integer.valueOf(ocorrenciaDocumentacaoForm.getDocumentacaoOriginal())));
					}
					TipoDocumentacao tipo = CadastroFacade.obterTipoDocumentacao(Integer.valueOf (ocorrenciaDocumentacaoForm.getTipoDocumentacao()));
					documentacao.setTipoDocumentacao(tipo);
					documentacao.setAnexo(ocorrenciaDocumentacaoForm.getAnexo().getFileName());
					StringBuffer descricao =new StringBuffer().append( "Documentação incluída/alterada  em: ").append( sdf2.format(dataInclusao));
					descricao.append( "\n Por: ").append( sentinelaInterface.getNome()).append("\n Tipo de Documentação: ").append(tipo.getDescricao()).append( "\n Anexo: ").append(documentacao.getAnexo());
					documentacao.setDescricao(descricao.toString());
					documentacao.setTsInclusao(dataInclusao);
					documentacao.setTsAtualizacao(dataInclusao);
					documentacao.setResponsavelDocumentacao(sentinelaInterface.getNome());
									
					ocorrenciaDocumentacao.setDocumentacao(documentacao);
	
					if(Integer.parseInt(ocorrenciaDocumentacaoForm.getTipoDocumentacao()) == Dominios.TIPO_DOCUMENTACAO_NOTIFICACAO)
					{
						// tipo documentação é uma notificação. Criar objeto Notificação com os dados da tela e salvar 
						
						notificacao = new Notificacao();
						notificacao.setCpfResponsavel(sentinelaInterface.getCpf());
						notificacao.setDescricao(ocorrenciaDocumentacaoForm.getDescricaoNotificacao());
						notificacao.setMotivo(ocorrenciaDocumentacaoForm.getMotivoSolucao());
						notificacao.setDocumentacao(documentacao);
						notificacao.setDataNotificacao(sdf.parse(ocorrenciaDocumentacaoForm.getTsNotificacao()));
						notificacao.setPrazoNotificacao(sdf.parse(ocorrenciaDocumentacaoForm.getTsPrazoNotificacao()));
						notificacao.setTsInclusao(dataInclusao);
						notificacao.setTsAlteracao(dataInclusao);
						
						if(ocorrenciaDocumentacaoForm.getTsSolucao() != null && !ocorrenciaDocumentacaoForm.getTsSolucao().equals(""))
						{
							notificacao.setDataSolucao(sdf.parse(ocorrenciaDocumentacaoForm.getTsSolucao()));
							notificacao.setMotivo(ocorrenciaDocumentacaoForm.getMotivoSolucao());
						}						
					}
				}
			}
			
			CadastroFacade.salvarOcorrenciaDocumentacao(documentacao,ocorrenciaDocumentacao,notificacao, ocorrenciaDocumentacaoForm.getAnexo());
			
			//limpar formulario
			ocorrenciaDocumentacaoForm.setDescricaoNotificacao("");
			ocorrenciaDocumentacaoForm.setDescricaoOcorrencia("");
			ocorrenciaDocumentacaoForm.setDocExistente("");
			ocorrenciaDocumentacaoForm.setDocumentacao("");
			ocorrenciaDocumentacaoForm.setDocumentacaoOriginal("");
			ocorrenciaDocumentacaoForm.setMotivoSolucao("");
			ocorrenciaDocumentacaoForm.setRelDocumentacao("");
			ocorrenciaDocumentacaoForm.setTipoDocumentacao("");
			ocorrenciaDocumentacaoForm.setTsInclusao("");
			ocorrenciaDocumentacaoForm.setTsNotificacao("");
			ocorrenciaDocumentacaoForm.setTsPrazoNotificacao("");
			ocorrenciaDocumentacaoForm.setTsSolucao("");
			
			
			addMessage("SUCESSO.23", request);	
			this.carregarListaOcorrenciaDocumentacao(form, request);
			
		} catch (ApplicationException appEx) {
			this.carregarListaOcorrenciaDocumentacao(ocorrenciaDocumentacaoForm, request);
			throw appEx;
		} catch (Exception e) {
			this.carregarListaOcorrenciaDocumentacao(ocorrenciaDocumentacaoForm, request);
			throw new ApplicationException("ERRO.201", new String[]{"ao incluir Ocorrência de Documentação"}, e);
		}

		return getActionForward();
    }
	
	@SuppressWarnings("unchecked")
	public void carregarListaOcorrenciaDocumentacao(ActionForm form, HttpServletRequest request ) throws ApplicationException, Exception {
		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		OcorrenciaDocumentacaoForm ocorrenciaDocumentacaoForm = (OcorrenciaDocumentacaoForm) form;
		SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
		if (ocorrenciaDocumentacaoForm.getCodBemImovel()!= null) {
			
			request.setAttribute("tiposDocumentacao", Util.htmlEncodeCollection(CadastroFacade.listarTipoDocumentacao()));
			
			Collection<Documentacao> documentacaos = Util.htmlEncodeCollection(CadastroFacade.listarDocumentacaosSemNotificacao(Integer.valueOf(ocorrenciaDocumentacaoForm.getCodBemImovel())));
			if(documentacaos != null && documentacaos.size() > 0) {
				request.setAttribute("documentacaos", documentacaos);
			}
			
			Documentacao doc = new Documentacao();
			doc.setBemImovel(CadastroFacade.obterBemImovel(Integer.valueOf (ocorrenciaDocumentacaoForm.getCodBemImovel())));
			Collection<Documentacao> documentacaoOriginal = Util.htmlEncodeCollection(CadastroFacade.listarDocumentacoes(doc));
			if(documentacaoOriginal != null && documentacaoOriginal.size() > 0) {
				request.setAttribute("documentacaosOriginal", documentacaoOriginal);
			}
			
			String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
			String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
					
			Pagina pagina = new Pagina(Integer.valueOf(Dominios.QTD_PAGINA), Integer.valueOf(indicePagina),Integer.valueOf(totalRegistros));
			pagina = CadastroFacade.listarOcorrenciaDocumentacao(pagina, Integer.valueOf(ocorrenciaDocumentacaoForm.getCodBemImovel()));
			Util.htmlEncodeCollection(pagina.getRegistros());
			Collection<OcorrenciaDocumentacao> coll = (ArrayList<OcorrenciaDocumentacao>) pagina.getRegistros();
			for(OcorrenciaDocumentacao ocorrenciaDocumentacao : coll) {
				ocorrenciaDocumentacao.setNomeResponsavel(sentinelaInterface.getUsuarioByCPF(ocorrenciaDocumentacao.getCpfResponsavel()).getNome());
			}
			pagina.setRegistros(coll);
			request.setAttribute("pagina", pagina);
			request.setAttribute("notificacao", Dominios.TIPO_DOCUMENTACAO_NOTIFICACAO);
		}
    }
	
	public void carregarAnexoDocumentacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws ApplicationException, Exception {

		try {
			if(StringUtil.stringNotNull(request.getParameter("codDocumentacao"))) {
				Documentacao doc = CadastroFacade.obterDocumentacao(Integer.valueOf (request.getParameter("codDocumentacao")));
				String nomeArquivo = doc.getAnexo().replaceAll(" ", "_");
				byte [] bytes = CadastroFacade.obterAnexoDocumentacao(Integer.valueOf (request.getParameter("codDocumentacao")));
				
				response.setHeader("Content-disposition", "attachment;filename="+nomeArquivo);
				response.setContentType("text/plain");  
				response.setContentLength(bytes.length);  
				ServletOutputStream ouputStream;

				ouputStream = response.getOutputStream();

				ouputStream.write(bytes, 0, bytes.length);  
				ouputStream.flush();  
				ouputStream.close(); 
			}
		}
		catch(ApplicationException appEx) {
			this.carregarListaOcorrenciaDocumentacao(form, request);
			setActionForward(mapping.findForward("pgEditOcorrenciaDocumentacao"));
			throw appEx;
		}
		catch(IOException e) {
			this.carregarListaOcorrenciaDocumentacao(form, request);
			setActionForward(mapping.findForward("pgEditOcorrenciaDocumentacao"));
			throw new ApplicationException("ERRO.200", new String[]{"visualizar documento","ocorrência de documentação"}, e);
		}  

	}
		
}
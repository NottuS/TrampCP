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

package gov.pr.celepar.framework.action;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.exception.ConstraintViolationException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import gov.pr.celepar.framework.form.MatriculaForm; 
import gov.pr.celepar.framework.facade.MatriculaFacade;
import gov.pr.celepar.framework.pojo.Aluno;
import gov.pr.celepar.framework.pojo.Endereco;
import gov.pr.celepar.framework.report.MatriculaGenerator;
import gov.pr.celepar.framework.util.CPF;
import gov.pr.celepar.framework.util.Dominios;
import gov.pr.celepar.framework.util.Pagina;
import gov.pr.celepar.framework.util.StringUtil;
import gov.pr.celepar.framework.exception.ApplicationException;


/**
 * @author GIC
 * @version 1.0
 * @since 18/06/2005
 * 
 * Classe Exemplo:
 * Responsável por manipular as requisições dos usuários - Módulo Matrículas.
 */
public class MatriculaAction extends BaseDispatchAction {
	
	/**
	 * Realiza carga da página de listagem de alunos.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward carregarPgListAlunos(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		return mapping.findForward("pgListAluno");
    }

	/**
	 * Realiza a carga da página de visualização dos dados do aluno.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward carregarPgViewAluno(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		MatriculaForm matriculaForm = (MatriculaForm) form;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			if (matriculaForm.getIdAluno() != null) {
				Aluno aluno = MatriculaFacade.obterAluno(new Integer(matriculaForm.getIdAluno()));
				
				matriculaForm.setNomeAluno(aluno.getNomeAluno());
				matriculaForm.setDtNascAluno(sdf.format(aluno.getNascimentoAluno()));
				matriculaForm.setNomeMaeAluno(aluno.getMaeAluno());
				matriculaForm.setNomePaiAluno(aluno.getPaiAluno());
				matriculaForm.setCpfAluno(CPF.formataCPF(aluno.getCpfAluno()));				
				matriculaForm.setLogradouro(aluno.getEndereco().getLogradouroEndereco());
				matriculaForm.setCep(aluno.getEndereco().getCepEndereco().toString());
				matriculaForm.setNumero(aluno.getEndereco().getNumeroEndereco());
				matriculaForm.setComplemento(aluno.getEndereco().getComplementoEndereco());
				matriculaForm.setBairro(aluno.getEndereco().getBairroEndereco());
				matriculaForm.setCidade(aluno.getEndereco().getCidadeEndereco());
				matriculaForm.setUf(aluno.getEndereco().getUfEndereco());
				matriculaForm.setIdLocalidade(aluno.getEndereco().getIdLocalidade().toString());				
			}
			
			return mapping.findForward("pgViewAluno");
						
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListAluno"));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListAluno"));
			throw new ApplicationException("mensagem.erro.matricula.carregarPgViewAluno", e);
		}
	}
	
	/**
	 * Carrega pagina para alteracao com os dados do aluno selecionado.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward carregarPgEditAluno(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		// Salva o TOKEN para evitar duplo submit
		saveToken(request);
		
		MatriculaForm matriculaForm = (MatriculaForm) form;
	
		try {
			if (matriculaForm.getIdAluno() != null) {
				Aluno aluno = MatriculaFacade.obterAluno(new Integer(matriculaForm.getIdAluno()));
				
				matriculaForm.setCpfAluno(aluno.getCpfAluno());
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				matriculaForm.setDtNascAluno(sdf.format(aluno.getNascimentoAluno()));
				matriculaForm.setNomeAluno(aluno.getNomeAluno());
				matriculaForm.setNomeMaeAluno(aluno.getMaeAluno());
				matriculaForm.setNomePaiAluno(aluno.getPaiAluno());
				matriculaForm.setLogradouro(aluno.getEndereco().getLogradouroEndereco());
				matriculaForm.setCep(aluno.getEndereco().getCepEndereco().toString());
				matriculaForm.setNumero(aluno.getEndereco().getNumeroEndereco());
				matriculaForm.setComplemento(aluno.getEndereco().getComplementoEndereco());
				matriculaForm.setBairro(aluno.getEndereco().getBairroEndereco());
				matriculaForm.setCidade(aluno.getEndereco().getCidadeEndereco());
				matriculaForm.setUf(aluno.getEndereco().getUfEndereco());
				matriculaForm.setIdLocalidade(aluno.getEndereco().getIdLocalidade().toString());
				
			}
			return mapping.findForward("pgEditAluno");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListAluno"));
			throw appEx;
			
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListAluno"));
			throw new ApplicationException("mensagem.erro.matricula.carregarPgEditAluno", e);
		}		
	}	
	
	/**
	 * Realiza o encaminhamento necessário para executar a listagem paginada de alunos. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward pesquisarAlunos (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {		
		MatriculaForm matriculaForm = (MatriculaForm) form;
		
		String indicePagina = request.getParameter("indice") == null ? "1" : request.getParameter("indice");		
		String totalRegistros = request.getParameter("totalRegistros") == null ? "0" : request.getParameter("totalRegistros");
				
		Pagina pagina = new Pagina(new Integer(Dominios.QTD_PAGINA), new Integer(indicePagina), new Integer(totalRegistros));

		try {
			pagina = MatriculaFacade.listarAluno(pagina, matriculaForm.getNomeAluno());
			
			request.setAttribute("pagina", pagina);

			return mapping.findForward("pgListAluno");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgListAluno"));
			throw appEx;
			
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgListAluno"));
			throw new ApplicationException("mensagem.erro.matricula.listarAluno", e);
		}
	}
	
	/**
	 * Realiza o encaminhamento necessário para salvar o aluno.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward salvarAluno(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		MatriculaForm matriculaForm = (MatriculaForm) form;
				
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			return mapping.findForward("pgEditAluno");
		}		
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			setActionForward(mapping.findForward("pgEditAluno"));
			throw new ApplicationException("mensagem.aviso.matricula.double.submit");
		}	
		
		try	{			
			Aluno aluno = new Aluno();			
			aluno.setNomeAluno(matriculaForm.getNomeAluno());		
			aluno.setMaeAluno(matriculaForm.getNomeMaeAluno());
			aluno.setPaiAluno(matriculaForm.getNomePaiAluno());
			aluno.setCpfAluno(matriculaForm.getCpfAluno().toString());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");				
			aluno.setNascimentoAluno(sdf.parse(matriculaForm.getDtNascAluno()));
					
			Endereco endereco = new Endereco();
			endereco.setLogradouroEndereco(matriculaForm.getLogradouro());
			endereco.setNumeroEndereco(matriculaForm.getNumero());
			endereco.setComplementoEndereco(matriculaForm.getComplemento());
			endereco.setBairroEndereco(matriculaForm.getBairro());
			endereco.setCidadeEndereco(matriculaForm.getCidade());
			endereco.setUfEndereco(matriculaForm.getUf());
			endereco.setCepEndereco(new Integer (StringUtil.removeFormatacao(matriculaForm.getCep())));
			endereco.setIdLocalidade(new Integer(matriculaForm.getIdLocalidade()));
			
			aluno.setEndereco(endereco);			

			String ret = MatriculaFacade.salvarAluno(aluno);
			addMessage("mensagem.sucesso.matricula.incluirAluno", new String[]{ret}, request);
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditAluno"));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditAluno"));
			throw new ApplicationException("mensagem.erro.matricula.servico.salvarAluno", e);
		}
		
		return mapping.findForward("pgListAluno");
	}	
	
	/**
	 * Realiza o encaminhamento necessário para atualizar os dados do aluno.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward alterarAluno(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		MatriculaForm matriculaForm = (MatriculaForm) form;	
		
		// Aciona a validação do Form
		if(!validaForm(mapping,form,request)) {
			return mapping.findForward("pgEditAluno");
		}
		
		// Verifica se o TOKEN existe para evitar duplo submit
		if(isTokenValid(request)) {
			resetToken(request);
		} else {
			setActionForward(mapping.findForward("pgEditAluno"));
			throw new ApplicationException("mensagem.aviso.matricula.double.submit");
		}
		
		try	{
			Aluno aluno = MatriculaFacade.obterAluno(new Integer(matriculaForm.getIdAluno()));

			aluno.setNomeAluno(matriculaForm.getNomeAluno());		
			aluno.setMaeAluno(matriculaForm.getNomeMaeAluno());
			aluno.setPaiAluno(matriculaForm.getNomePaiAluno());
			aluno.setCpfAluno(matriculaForm.getCpfAluno().toString());
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");				
			aluno.setNascimentoAluno(sdf.parse(matriculaForm.getDtNascAluno()));
			
			Endereco endereco = aluno.getEndereco();
			endereco.setLogradouroEndereco(matriculaForm.getLogradouro());
			endereco.setComplementoEndereco(matriculaForm.getComplemento());
			endereco.setBairroEndereco(matriculaForm.getBairro());
			endereco.setCidadeEndereco(matriculaForm.getCidade());
			endereco.setUfEndereco(matriculaForm.getUf());
			endereco.setIdLocalidade(new Integer(matriculaForm.getIdLocalidade()));
			endereco.setNumeroEndereco(matriculaForm.getNumero());
			endereco.setCepEndereco(new Integer(StringUtil.removeFormatacao(matriculaForm.getCep())));
			
			MatriculaFacade.alterarAluno(aluno);
			addMessage("mensagem.sucesso.matricula.alterarAluno", request);
			
			matriculaForm.setNomeAluno("");
			
			return mapping.findForward("pgListAluno");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditAluno"));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditAluno"));
			throw new ApplicationException("mensagem.erro.matricula.servico.alterarAluno", e);
		}		
	}
	
	/**
	 * Realiza o encaminhamento para exclusão de aluno.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward excluirAluno(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		MatriculaForm matriculaForm = (MatriculaForm) form;
			
		try {
			if (matriculaForm.getIdAluno() != null) {
				Aluno aluno = MatriculaFacade.obterAluno(new Integer(matriculaForm.getIdAluno()));
				MatriculaFacade.excluirAluno(aluno);
			}
			
			addMessage("mensagem.sucesso.matricula.excluirAluno", request);			
			
			return mapping.findForward("pgListAluno");
			
		} catch (ApplicationException appEx) {
			if(appEx.getCausaRaiz() instanceof ConstraintViolationException){
				setActionForward(carregarPgViewAluno(mapping, matriculaForm, request, response));
			}
			throw appEx;
		} catch (Exception e) {
			setActionForward(carregarPgViewAluno(mapping, matriculaForm, request, response));
			throw new ApplicationException("mensagem.erro.matricula.servico.excluirAluno", e);
		}
	}
	
	/**
	 * Realiza o encaminhamento para geração do relatório on-line.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward relatMatriculaOnline(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		String path = request.getSession().getServletContext().getRealPath("");
		try {
			File jasperFile = new File(path + File.separator + "reports" + File.separator + "jasper" + File.separator + "MatriculaReport.jasper");
	        String subJasperFile = path + File.separator + "reports" + File.separator + "jasper" + File.separator + "MatriculaSubReport.jasper";
	        String image1 = path + File.separator + "images" + File.separator + "logo_gov_parana.png";
	        String image2 = path + File.separator + "images" + File.separator + "logo_sfw_livre_pr.png";	        
	        
	        HashMap <String, Object> filterParameters = new HashMap <String, Object>();
	        filterParameters.put("pathSubRel", subJasperFile);
	        filterParameters.put("image1", image1);
	        filterParameters.put("image2", image2); 	        
	        MatriculaGenerator matriculaGenerator = new MatriculaGenerator(filterParameters, jasperFile, path);	        
	        matriculaGenerator.execute();
	        
	        byte[] relatorio = matriculaGenerator.getBufferPdf();
	        	        
	        // configura o response	        
	        response.setHeader("Content-disposition", "attachment;filename=MatriculaReport.pdf"); //inline->abre no browser; attachment->baixa o arquivo 
	        response.setContentType("application/pdf");
	        response.setContentLength(relatorio.length);
	        // envia arquivo e fecha o stream
	        ServletOutputStream outPutStream = response.getOutputStream();
	        outPutStream.write(relatorio, 0, relatorio.length);
	        outPutStream.flush();
	        outPutStream.close();
	     
	        /* retorna null pois o relatório (pdf) é aberto sem a necessidade de um jsp */
			return null;			
		} catch (ApplicationException appEx) {
			setActionForward(new ActionForward("/entrada.do?action=carregarEntrada"));
			throw appEx;
		} catch (Throwable e) { //Catch de Throwable pois pode ocurrer uma RunTimeException -> evita tela em branco
			setActionForward(new ActionForward("/entrada.do?action=carregarEntrada"));
			throw new ApplicationException("mensagem.erro.matricula.relatorio", e);
		}		
	}

	/**
	 * Realiza o encaminhamento para agendamento da geração do relatório. 
	 * O relatório será apresentado caso já tenha sido agendado e concluído. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward relatMatriculaBatch(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		Calendar calendar = Calendar.getInstance();
		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		int mes = calendar.get(Calendar.MONTH) + 1;
		int ano = calendar.get(Calendar.YEAR);		
		String nomeRel = "MatriculaReport" + dia + mes + ano;
		boolean relatorioExiste = false;
				
		// Verificar se o relatorio solicitado existe
		try {
			relatorioExiste = MatriculaFacade.verificarRelatorioAgendado(nomeRel);
			
			if (relatorioExiste) { //Se existir mostra-lo
				byte[] relatorio = MatriculaFacade.recuperarRelatorioMatricula(nomeRel);
				// configura o response
		        response.setHeader("Content-disposition", "attachment;filename=MatriculaReport.pdf"); //inline->abre no browser; attachment->baixa o arquivo
		        response.setContentType("application/pdf");
		        response.setContentLength(relatorio.length);
		        // envia arquivo e fecha o stream
		        ServletOutputStream outPutStream = response.getOutputStream();
		        outPutStream.write(relatorio, 0, relatorio.length);
		        outPutStream.flush();
		        outPutStream.close();		        
				return null; //retorna null pois o relatório (pdf) é aberto sem a necessidade de um jsp.
			} else { //Senão agendar				
				String path = request.getSession().getServletContext().getRealPath("");	
				MatriculaFacade.agendarRelatorioMatricula(nomeRel, path);
				addMessage("mensagem.sucesso.matricula.relatBatch", request);

				return new ActionForward("/entrada.do?action=carregarEntrada");
			}							
		} catch (ApplicationException appEx) {
			setActionForward(new ActionForward("/entrada.do?action=carregarEntrada"));
			throw appEx;
		} catch (Throwable e) { //Catch de Throwable pois pode ocurrer uma RunTimeException -> evita tela em branco
			setActionForward(new ActionForward("/entrada.do?action=carregarEntrada"));
			throw new ApplicationException("mensagem.erro.matricula.relatorioBatch", e);
		}		
	}	
}
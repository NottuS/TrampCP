package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Vanessak
 * @version 1.0
 * @since 29/06/2011
 * 
 * Classe Action:
 * Responsável por manipular as requisições dos usuários
 */

public class ManualUsuarioAction extends BaseDispatchAction {
	
	/**
	 * Carrega o manual do usuário. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward carregarArquivoManual (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {		
		try {
			File arquivo = new File(Dominios.PATH_MANUAL);  
			FileInputStream fis = new FileInputStream(arquivo);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[8192];
			int bytesRead = 0;
			while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {  
				baos.write(buffer, 0, bytesRead);  
			}  
			byte[] arquivoProcessado = baos.toByteArray();
			
			response.setHeader("Content-disposition", "attachment;filename=ABI_Manual_Usuario.pdf");
			response.setContentType("application/pdf");
			response.setContentLength(arquivoProcessado.length);
			ServletOutputStream outPutStream = response.getOutputStream();
			outPutStream.write(arquivoProcessado, 0, arquivoProcessado.length);
			outPutStream.flush();
			outPutStream.close();
			baos.close();
			fis.close();

			return null;
		} catch (FileNotFoundException ex) {
			setActionForward(mapping.findForward("pgErroManualUsuario"));
			throw new ApplicationException("ERRO.carregarArquivoManual.download", ex, ApplicationException.ICON_ERRO);
		} catch (Exception ex) {
			setActionForward(mapping.findForward("pgErroManualUsuario"));
			throw new ApplicationException("ERRO.201", new String[]{"no download do manual"}, ex, ApplicationException.ICON_ERRO);
		}
	}
	
	/**
	 * Carrega o manual do usuário. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward carregarArquivoTutorial (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException, Exception {		
		try {
			File arquivo = null;
			if (request.getSession().getAttribute("indGrupoSentinela").equals(GrupoSentinela.ADM_INST.getDescricao())) {
				arquivo = new File(Dominios.PATH_TUTORIAL_ADM_INST);  
			}
			if (request.getSession().getAttribute("indGrupoSentinela").equals(GrupoSentinela.LEI_ABI.getDescricao())) {
				arquivo = new File(Dominios.PATH_TUTORIAL_LEI_ABI);  
			}
			if (request.getSession().getAttribute("indGrupoSentinela").equals(GrupoSentinela.OPE_ABI.getDescricao())) {
				arquivo = new File(Dominios.PATH_TUTORIAL_OPE_ABI);  
			}
			if (request.getSession().getAttribute("indGrupoSentinela").equals(GrupoSentinela.OPE_ORG_ABI.getDescricao())) {
				arquivo = new File(Dominios.PATH_TUTORIAL_OPE_ORG_ABI);  
			}
			
			if (arquivo != null) {
				FileInputStream fis = new FileInputStream(arquivo);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[8192];
				int bytesRead = 0;
				while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {  
					baos.write(buffer, 0, bytesRead);  
				}  
				byte[] arquivoProcessado = baos.toByteArray();
				
				response.setHeader("Content-disposition", "attachment;filename=Tutorial_ABI.pdf");
				response.setContentType("application/pdf");
				response.setContentLength(arquivoProcessado.length);
				ServletOutputStream outPutStream = response.getOutputStream();
				outPutStream.write(arquivoProcessado, 0, arquivoProcessado.length);
				outPutStream.flush();
				outPutStream.close();
				baos.close();
				fis.close();

				return null;
			} else {
				setActionForward(mapping.findForward("pgErroManualUsuario"));
				addMessage("ERRO.ocorrenciaDocumentacao.download", request);
				return getActionForward();
			}
		} catch (FileNotFoundException ex) {
			setActionForward(mapping.findForward("pgErroManualUsuario"));
			throw new ApplicationException("ERRO.carregarArquivoTutorial.download", ex, ApplicationException.ICON_ERRO);
		} catch (Exception ex) {
			setActionForward(mapping.findForward("pgErroManualUsuario"));
			throw new ApplicationException("ERRO.201", new String[]{"no download do manual"}, ex, ApplicationException.ICON_ERRO);
		}
	}
}

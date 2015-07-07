package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class RelatorioIReportAction extends BaseDispatchAction{
	
	private static Logger log4j = Logger.getLogger(RelatorioIReportAction.class);
	
	/**
	 * Gera o relatorio e adiciona a sessao. Utilizado quando for necessario retornar para outro UCS apos mostrar o relatorio.<br>
	 * @author Daniel
	 * @since 13/11/2007
	 * @param lista : List<?>
	 * @param parametros : Map<String, Object>
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param mapping : ActionMapping
	 * @param response : HttpServletResponse
	 * @throws ApplicationException
	 */
	public static void processarRelatorioRetorno(List<? extends Object> lista, Map<String, Object> parametros, 
			ActionForm form, HttpServletRequest request, ActionMapping mapping, HttpServletResponse response) throws ApplicationException, Exception {
		processaDados(lista, parametros, request);
	}

	/**
	 * Gera o relatorio e adiciona a sessao. Utilizado quando for necessario gerar o relatorio sem recarregar a pagina.<br>
	 * @author Daniel
	 * @since  13/11/2007
	 * @param lista : List<?>
	 * @param parametros : Map<String, Object>
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param mapping : ActionMapping
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public static ActionForward processarRelatorio(List<? extends Object> lista, Map<String, Object> parametros, ActionForm form, 
			HttpServletRequest request, ActionMapping mapping, HttpServletResponse response) throws ApplicationException, Exception {
		processaDados(lista, parametros, request);	
		return imprimirRelatorio(mapping, form, request, response);
	}

	/**
	 * Responsavel por processar o relatorio, receber os parametros especificos do UCS chamador, transformar o relatorio no padrao pdf e inclui-lo na sessao.<br>
	 * @author Daniel
	 * @since 20/10/2007
	 * @param lista : List<?>
	 * @param parametros : Map<String, Object>
	 * @param request : HttpServletRequest
	 * @throws ApplicationException
	 */
	private static void processaDados(List<? extends Object> lista, Map<String, Object> parametros, HttpServletRequest request) {
		if(lista != null && !lista.isEmpty()) {
	    	JRBeanArrayDataSource beanDS = new JRBeanArrayDataSource(lista.toArray());
	    	try {
				InputStream inputStream = RelatorioIReportAction.class.getClassLoader().getResourceAsStream(Dominios.PATH_RELATORIO+parametros.get("nomeRelatorioJasper"));
				JasperPrint print = JasperFillManager.fillReport(inputStream, parametros, beanDS);
				byte[] relatorio  =  JasperExportManager.exportReportToPdf(print);
				request.getSession().setAttribute("relatorioProcessado", relatorio);				
			} catch (JRException e) {
				log4j.error(e.getStackTrace());
				log4j.error(e.getCause());
				log4j.error(e.getMessage());
			}
    	}
	}
	
	/**
	 * Metodo para exibir relatorios processados, conta com a chamada do aplication layout.<br>
	 * @author Daniel
	 * @since 20/07/2007
	 * @param mapping : ActionMapping
	 * @param form : ActionForm
	 * @param request : HttpServletRequest
	 * @param response : HttpServletResponse
	 * @return ActionForward
	 * @throws ApplicationException
	 */
	public static ActionForward imprimirRelatorio(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
				throws ApplicationException, Exception {
		try {
			byte[] relatorio = (byte[])request.getSession().getAttribute("relatorioProcessado");
			if(relatorio != null) {
				response.setHeader("Content-disposition", "attachment;filename=relatorioProcessado.pdf");
				response.setContentType("application/pdf");
				response.setContentLength(relatorio.length);
			}
			ServletOutputStream outPutStream = response.getOutputStream();
			if(relatorio != null) {
				outPutStream.write(relatorio, 0, relatorio.length);
			}
			outPutStream.flush();
			outPutStream.close();
		}
		catch (IOException e) {
			throw new ApplicationException("mensagem.padrao");
		}
		request.getSession().setAttribute("relatorioProcessado", null);		
		request.setAttribute("relatorioProcessado", null);
		
		return null;
	}	
}
package gov.pr.celepar.abi.servlet;

import gov.pr.celepar.abi.quartz.VerificacaoAgendaABIQuartz;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.framework.exception.ApplicationException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

public class SistemaInicializacao extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(SistemaInicializacao.class);

	public void init(ServletConfig conf) throws ServletException {
        super.init(conf);

    	try{
    		//criando link simbolico para pasta de logotipos
    		StringBuffer comando = new StringBuffer();
    		comando.append("ln -s ").append(Dominios.PATH_LOGO).append(" ").append(Dominios.PATH_IMAGEM_LOGO);
    		Runtime.getRuntime().exec(comando.toString()); 
        	VerificacaoAgendaABIQuartz.agendar();
    		logger.error("Processamento do Monitoramento da Agenda do ABI.");
    	}catch (ApplicationException e) {
			logger.error("Erro App",((ApplicationException)e).getCausaRaiz());
    	}catch (Exception e) {
			logger.error("Erro",e);
		}
    }   
    
}

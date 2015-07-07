/*
 * Este programa � licenciado de acordo com a
 * LPG-AP (LICEN�A P�BLICA GERAL PARA PROGRAMAS DE COMPUTADOR DA ADMINISTRA��O P�BLICA),
 * vers�o 1.1 ou qualquer vers�o posterior.
 * A LPG-AP deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa.
 * Caso uma c�pia da LPG-AP n�o esteja dispon�vel junto com este Programa, voc�
 * pode contatar o LICENCIANTE ou ent�o acessar diretamente:
 * http://www.celepar.pr.gov.br/licenca/LPG-AP.pdf
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa
 * � preciso estar de acordo com os termos da LPG-AP.
 */

package gov.pr.celepar.framework.facade;

import gov.pr.celepar.framework.dao.AlunoDAO;
import gov.pr.celepar.framework.dao.factory.DAOFactory;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.pojo.Aluno;
import gov.pr.celepar.framework.pojo.Serie;
import gov.pr.celepar.framework.report.Agendador;
import gov.pr.celepar.framework.report.MatriculaGenerator;
import gov.pr.celepar.framework.util.Pagina;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Logger;


/**
 * @author Grupo Framework - Componentes
 * @version 1.0
 * @since 18/01/2005
 * 
 * Classe Exemplo:
 * Respons�vel por encapsular os servi�os de matr�cula e a sua toda regra de neg�cio.
 */
public class MatriculaFacade extends SuperFacade {
	
	private static Logger log = Logger.getLogger(MatriculaFacade.class);
	
	/**
	 * Busca um objeto Aluno atrav�s de seu c�digo.
	 * @param codAluno c�digo do Aluno a ser localizado
	 * @return Aluno 
	 * @throws ApplicationException
	 */
	public static Aluno obterAluno(Integer codAluno) throws ApplicationException {		
		DAOFactory hibernateFactory = DAOFactory.getDAOFactory(DAOFactory.HIBERNATE);
		return hibernateFactory.getAlunoDAO().obter(codAluno);
	}
				
	/**
	 * Lista paginada de alunos.
	 * @param pag - objeto de pagina��o contendo parametros para pesquisa.
	 * @return Pagina - encapsula resultados da pesquisa e parametros para pagina��o. 
	 * @throws ApplicationException
	 */
	public static Pagina listarAluno(Pagina pag, String nomeAluno) throws ApplicationException {		
		DAOFactory hibernateFactory = DAOFactory.getDAOFactory(DAOFactory.HIBERNATE);
		Aluno aluno = new Aluno();
		aluno.setNomeAluno(nomeAluno);
		
		try {
			
			if (pag.getTotalRegistros() == 0) {
			 	pag.setTotalRegistros(hibernateFactory.getAlunoDAO().buscarQtdLista(aluno).intValue());
			}
			pag.setRegistros(hibernateFactory.getAlunoDAO().listar(aluno, pag.getQuantidade(), pag.getPaginaAtual()));
		
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.matricula.listarAluno", e);
		}	
		
		return pag;
	}
	
	/**
	 * Salva objeto Aluno.
	 * @param Aluno a ser salvo.
	 * @return Valor retornado do MainFrame
	 * @throws ApplicationException
	 */
	public static String salvarAluno(Aluno aluno) throws ApplicationException {
		String retornoNatural = null;
		DAOFactory hibernateFactory = DAOFactory.getDAOFactory(DAOFactory.HIBERNATE);
		try {			
			HibernateUtil.currentTransaction(); //Abre sess�o e transa��o
						
			AlunoDAO alunoDao = hibernateFactory.getAlunoDAO();
			alunoDao.salvar(aluno);
			retornoNatural = alunoDao.consultarMainFrame();
		
			HibernateUtil.commitTransaction(); //Fecha sess�o e transa��o
			
		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				log.error("Problema ao tentar realizar rollback na transacao: salvarAluno", e);
			}
			throw appEx;
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				log.error("Problema ao tentar realizar rollback na transacao: salvarAluno", e);
			}
			throw new ApplicationException("mensagem.erro.matricula.servico.salvarAluno", ex, ApplicationException.ICON_ERRO);						
		}
		
		return retornoNatural;
	}
	
	
	/**
	 * Atualiza objeto Aluno.
	 * @param Aluno a ser atualizado.
	 * @throws ApplicationException
	 */
	public static void alterarAluno(Aluno aluno) throws ApplicationException {
		DAOFactory hibernateFactory = DAOFactory.getDAOFactory(DAOFactory.HIBERNATE);
		
		try {
			HibernateUtil.currentTransaction(); //Abre sess�o e transa��o
			
			hibernateFactory.getAlunoDAO().alterar(aluno);
			
			HibernateUtil.commitTransaction(); //Fecha sess�o e transa��o
		
		} catch (ApplicationException appEx) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				log.error("Problema ao tentar realizar rollback na transacao: alterarAluno", e);
			}
			throw appEx;
			
		} catch (Exception ex) {
			try {
				HibernateUtil.rollbackTransaction();
			} catch (Exception e) {
				log.error("Problema ao tentar realizar rollback na transacao: alterarAluno", e);
			}
			throw new ApplicationException("mensagem.erro.matricula.servico.alterarAluno", ex, ApplicationException.ICON_ERRO);						
		}
	}
	
	
	/**
	 * Remove objeto Aluno.
	 * @param Aluno a ser removido.
	 * @throws ApplicationException
	 */
	public static void excluirAluno(Aluno aluno) throws ApplicationException {
		DAOFactory hibernateFactory = DAOFactory.getDAOFactory(DAOFactory.HIBERNATE);
		try {		
			hibernateFactory.getAlunoDAO().excluir(aluno);
		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception ex) {
			throw new ApplicationException("mensagem.erro.matricula.servico.excluirAluno", ex, ApplicationException.ICON_ERRO);						
		}
		
	}
	
	
	/**
	 * Recupera uma lista de S�ries e seus objetos relacionados.
	 * @throws ApplicationException
	 */
	public static Collection<Serie> listarSeriesComRelacionamentos() throws ApplicationException {
		Collection <Serie>coll = null;	
		
		DAOFactory hibernateFactory = DAOFactory.getDAOFactory(DAOFactory.HIBERNATE);
		
		try {
			
			coll = hibernateFactory.getSerieDAO().listarComRelacionamentos();
			if(coll.size() == 0) {
				throw new ApplicationException("mensagem.aviso.matricula.facade.listarSeries");
			}
			
		}catch (ApplicationException appEx) {
			throw appEx;			
		}catch (Exception e) {
			throw new ApplicationException("mensagem.erro.matricula.listarSeries.generico");
		}
		
		return coll;
	}
	
	
	/**
	 * Verifica se existe relatorio gerado com o nome informado.
	 * @param nomeRel - Nome do relat�rio a ser pesquisado.
	 * @return - true: caso exista e false: caso n�o exista
	 * @throws Exception
	 */
	public static boolean verificarRelatorioAgendado(String nomeRel) throws ApplicationException {		
		String[] listaRelatorios = null;
		
		try {
			Agendador agendador = new Agendador("GTF-EXEMPLO");
			
			listaRelatorios = agendador.listarTrabalhos();
			for (String nomeRelatRecuperado : listaRelatorios) {
				if(nomeRelatRecuperado.equals(nomeRel)){
					return true;
				}
			}
		}catch (Exception ex) {
			throw new ApplicationException("mensagem.erro.matricula.facade.verificarRelatorioAgendado", ex);
		}
		return false;
	}
	
	
	/**
	 * Recupera o relat�rio de matr�cula gerado pelo agendador de relat�rios.
	 * @param nomeRelatorio - nome correspondente ao relat�rio gerado.
	 * @return byte array com o conte�do do relat�rio ou nulo caso ocorra algum problema.
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public static byte[] recuperarRelatorioMatricula(String nomeRelatorio) throws ApplicationException {
		byte[] relatorio = null;
		
		try {
			Agendador agendador = new Agendador("GTF-EXEMPLO");
			relatorio = agendador.recuperarRelatorio(nomeRelatorio, false);		
		} catch (Exception ex) {
			throw new ApplicationException("mensagem.erro.matricula.facade.recuperarRelatorioMatricula", new String[]{nomeRelatorio}, ex);			
		}
		
		return relatorio;
	}
	
	
	/**
	 * Realiza o agendamento do relat�rio informado.
	 * @param nomeRelatorio - nome do arquivo/agendamento a ser criado.
	 * @param applicationPath - diret�rio da aplica��o no servidor (necess�rio para informar o diret�rio onde o relat�rio sera gerado).
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public static void agendarRelatorioMatricula(String nomeRelatorio, String applicationPath) throws ApplicationException {
		try {
	        File jasperFile = new File(applicationPath + File.separator + "reports" + File.separator + "jasper" + File.separator + "MatriculaReport.jasper");
	        String subJasperFile = applicationPath + File.separator + "reports" + File.separator + "jasper" + File.separator + "MatriculaSubReport.jasper";
	        String image1 = applicationPath + File.separator + "images" + File.separator + "logo_gov_parana.png";
	        String image2 = applicationPath + File.separator + "images" + File.separator + "logo_sfw_livre_pr.png";	        
	        
	        HashMap <String, Object> filterParameters = new HashMap <String, Object>();
	        filterParameters.put("pathSubRel", subJasperFile);
	        filterParameters.put("image1", image1);
	        filterParameters.put("image2", image2);
	        MatriculaGenerator matriculaGenerator = new MatriculaGenerator(filterParameters, jasperFile, applicationPath);	        
	    	        
			Calendar dataExecucao = Calendar.getInstance();
			dataExecucao.set(Calendar.HOUR_OF_DAY, 11);
			dataExecucao.set(Calendar.MINUTE, 00);
			
			Calendar dataAtual = Calendar.getInstance();
			if(dataAtual.after(dataExecucao)) { //Se a data atual for maior que a data de execucao agendar para o proximo dia
				dataExecucao.set(Calendar.DAY_OF_MONTH, dataExecucao.get(Calendar.DAY_OF_MONTH)+1);
			}			
			
			Agendador agendador = new Agendador("GTF-EXEMPLO");
			agendador.agendarData(nomeRelatorio, matriculaGenerator, dataExecucao.getTime());	        

		} catch (ApplicationException appEx) {
			throw appEx;
		} catch (Exception e) {						
			throw new ApplicationException("mensagem.erro.matricula.agendarRelatorioMatricula", e);
		}
	}
}
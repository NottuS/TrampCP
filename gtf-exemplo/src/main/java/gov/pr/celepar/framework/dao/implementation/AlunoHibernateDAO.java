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

package gov.pr.celepar.framework.dao.implementation;

import gov.celepar.adabas.Natural;
import gov.pr.celepar.framework.dao.AlunoDAO;
//import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.pojo.Aluno;
import java.util.ArrayList;
import java.util.Collection;
import org.hibernate.Query;
import org.hibernate.Session;
import org.apache.log4j.Logger;

/**
 * @author Grupo Framework
 * @version 1.0
 * @since 18/01/2005
 * 
 * Classe Exemplo:
 * Responsavel por manipular os dados da tabela de aluno.
 */
public class AlunoHibernateDAO extends GenericHibernateDAO<Aluno, Integer> implements AlunoDAO {
	 
	private static Logger log = Logger.getLogger(AlunoHibernateDAO.class);
	private static Logger logAuditoria = Logger.getLogger("AUDITORIA");
	
		
	
	/**
	 * Recupera lista paginada de alunos pequisando pelo nome. Se nao for informado 
	 * nenhum nome serao retornados todos os alunos. 
	 * @param qtdPagina - quantidade de objetos por pagina.
	 * @param numPagina - numero da pagina a ser pesquisada.
	 * @param nome do aluno a ser pesquisado   
	 * @return Collection de alunos 
	 * @throws ApplicationException
	 */
	@SuppressWarnings("unchecked")
	public Collection<Aluno> listar(Aluno aluno, Integer qtdPagina, Integer numPagina) throws ApplicationException {		
		Collection<Aluno> coll = new ArrayList<Aluno>();
		
		try {
			Session session = HibernateUtil.currentSession();
			
			// Duas formas de fazer a mesma coisa
			// Query q = session.getNamedQuery("buscaAlunos"); //Uso de named query
			Query q = session.createQuery("from Aluno a where a.nomeAluno like :arg");
			q.setString("arg", "%" + aluno.getNomeAluno() + "%");
			
			if (qtdPagina != null && numPagina != null) {
				q.setMaxResults(qtdPagina.intValue());
				q.setFirstResult( ((numPagina.intValue()-1) * qtdPagina.intValue()));
			}
			coll = q.list();
			logAuditoria.info("PESQUISA DE ALUNOS: Realizada pelo usuario Xxxxxx");
		} catch (Exception e) {
			log.debug("Problema ao realizar a funcao: pesquisarAlunos", e);
			throw new ApplicationException("mensagem.erro.matricula.pesquisarAlunos", e);		
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log.error("Problema ao tentar fechar conexao com o banco de dados: pesquisarAlunos", e);
			}
		}
		
		return coll;
	}
	
	
	/**
	 * Recupera quantidade total de alunos.
	 * @return Collection 
	 * @throws ApplicationException
	 */
	public Long buscarQtdLista(Aluno aluno) throws ApplicationException {
		Long qtd = null;
		
		try {
			Session session = HibernateUtil.currentSession();
					
			Query q = session.createQuery("select count(*) from Aluno A where A.nomeAluno like :arg");
			q.setString("arg", "%" + aluno.getNomeAluno() + "%");
			qtd = (Long) q.uniqueResult();
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.matricula.qtdDeAlunos", e);
		}catch (Throwable t) {
			t.printStackTrace();
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log.error("Problema ao tentar fechar conexao com o banco de dados: buscarQtdAlunos", e);
			}
		}
		
		return qtd;
	}
	
	
	/**
	 * Salva objeto Aluno.
	 * @param Aluno a ser salvo.
	 * @throws ApplicationException
	 */
	public void salvar(Aluno aluno) throws ApplicationException {				
		try {			
			Session session = HibernateUtil.currentSession();			
			session.save(aluno);			
						
			logAuditoria.info("INSERCAO DE ALUNO: Aluno "+ aluno.getNomeAluno() +" inserido por Xxxxxx");
		} catch (Exception he) {
			throw new ApplicationException("mensagem.erro.matricula.salvarAluno", he);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log.error("Problema ao tentar fechar conexao com o banco de dados: salvarAluno", e);
			}
		}
	}
	
	
	/**
	 * Atualiza objeto Aluno.
	 * @param Aluno a ser atualizado.
	 * @throws ApplicationException
	 */
	public void alterar(Aluno aluno) throws ApplicationException {		
		try {						
			Session session = HibernateUtil.currentSession();
			session.update(aluno);
			
			logAuditoria.info("ALTERACAO DE ALUNO: Aluno "+ aluno.getNomeAluno() +" alterado por Xxxxxx");			
		} catch (Exception e) {			
			throw new ApplicationException("mensagem.erro.matricula.alterarAluno", e);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log.error("Problema ao tentar fechar conexao com o banco de dados: alterarAluno", e);
			}
		}
	}
	
	
	/**
	 * Executa consulta ao MainFrame.
	 * 
	 * @return texto de retorno
	 * @throws ApplicationException Caso consulta tenha problema.
	 */
	public String consultarMainFrame() throws ApplicationException {
		
		/* executa o natural. Nome do programa depois 
		    os valores passados a ele. */		
		Natural natural = new Natural("TREJAVA", "01");
		
		/* codigo de retorno 0 significa:
		    tudo ok!!! */
		if (natural.getRC() == 0) {
			return natural.getDataParameter();
		}
		
		throw new ApplicationException("mensagem.erro.matricula.consultaNatural", new String[]{natural.getMSG()});
	}
}
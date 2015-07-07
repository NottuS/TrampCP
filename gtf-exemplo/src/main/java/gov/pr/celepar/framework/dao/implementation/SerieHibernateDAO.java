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

import java.util.ArrayList;
import java.util.Collection;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.apache.log4j.Logger;
import gov.pr.celepar.framework.dao.SerieDAO;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.pojo.Serie;


public class SerieHibernateDAO extends GenericHibernateDAO<Serie, Integer> implements SerieDAO {
	
	private static Logger log = Logger.getLogger(SerieHibernateDAO.class);
	private static Logger logAuditoria = Logger.getLogger("AUDITORIA");
		
	@SuppressWarnings("unchecked")
	public Collection<Serie> listarComRelacionamentos() throws ApplicationException {
		Collection<Serie> coll = new ArrayList<Serie>();

		try {
			Session session = HibernateUtil.currentSession();
			
			// Duas formas de fazer a mesma coisa:			
			/*
			Query q = session.createQuery("from Serie s left join fetch s.matriculas");			 
			for (Serie serie : coll) {
				for (Matricula matricula : serie.getMatriculas()) {
					Hibernate.initialize(matricula.getAluno());
					Hibernate.initialize(matricula.getEscola());
					Hibernate.initialize(matricula.getAluno().getEndereco());
					Hibernate.initialize(matricula.getEscola().getEndereco());
				}				
			}
			*/
			
			StringBuilder sql = new StringBuilder();
			sql.append("from Serie s left join fetch s.matriculas m ");
			sql.append("left join fetch m.aluno a ");
			sql.append("left join fetch m.escola e ");
			sql.append("left join fetch a.endereco ");
			sql.append("left join fetch e.endereco ");
		
			Query q = session.createQuery(sql.toString());
									
			coll = q.list();			

			logAuditoria.info("LISTAGEM DE SERIES: Realizada pelo usuário Xxxxxx");
		} catch (HibernateException he) {
			throw new ApplicationException("mensagem.erro.matricula.listarSeries", he);
		} catch (Exception e) {
			throw new ApplicationException("mensagem.erro.matricula.listarSeries.generico", e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log.error("Problema ao tentar fechar conexao com o banco de dados: listarSeriesComRelacionamentos", e);
			}		
		}
		
		return coll;
	}

}
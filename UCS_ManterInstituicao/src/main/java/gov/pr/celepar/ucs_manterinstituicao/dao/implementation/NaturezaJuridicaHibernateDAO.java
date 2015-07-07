package gov.pr.celepar.ucs_manterinstituicao.dao.implementation;

import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.ucs_manterinstituicao.dao.dao.NaturezaJuridicaDAO;
import gov.pr.celepar.ucs_manterinstituicao.pojo.NaturezaJuridica;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

public class NaturezaJuridicaHibernateDAO extends GenericHibernateDAO<NaturezaJuridica, Long> 
							implements NaturezaJuridicaDAO{
	private static Logger log = Logger.getLogger(NaturezaJuridicaHibernateDAO.class);
	
	@SuppressWarnings("unchecked")
	public Collection<NaturezaJuridica> listar() throws ApplicationException{
		Collection<NaturezaJuridica> coll = new ArrayList<NaturezaJuridica>();
		try {
			Session session = HibernateUtil.currentSession();
			Query q =  session.createQuery("from NaturezaJuridica nj");
			coll = q.list();
		} catch (Exception e) {
			log.debug("Problema ao realizar a funcao: listar()", e);
			throw new ApplicationException("mensasem.erro.natureza.listar", e);
		} finally {
			try {
				HibernateUtil.closeSession();
				
			}catch (Exception e) {
				log.error("Problema ao tentar fechar conexao com o banco de dados: pesquisarAlunos", e);
			}
		}
		return coll;
	}

	@Override
	public NaturezaJuridica obter(Integer codNaturezaJuridica)
			throws ApplicationException {
		NaturezaJuridica naturezaJuridica = null;
		try {
			Session session = HibernateUtil.currentSession();
			StringBuilder sql = new StringBuilder("from NaturezaJuridica nj ");
			sql.append("where nj.codNaturezaJuridica = :cnj ");
			
			Query q = session.createQuery(sql.toString());
			q.setInteger("cnj", codNaturezaJuridica);
			naturezaJuridica = (NaturezaJuridica)q.uniqueResult();
		} catch (Exception e) {
			log.debug("Problema ao realizar a funcao: obter", e);
			throw new ApplicationException("mensagem.erro.natureza.obter", e);
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log.error("Problema ao tentar fechar conexao com o banco de dados: obter Natureza juridica", e);
			}
		}
		
		return naturezaJuridica;
	}
}

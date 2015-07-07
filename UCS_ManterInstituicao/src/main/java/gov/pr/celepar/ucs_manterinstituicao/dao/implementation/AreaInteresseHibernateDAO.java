package gov.pr.celepar.ucs_manterinstituicao.dao.implementation;

import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.ucs_manterinstituicao.dao.dao.AreaInteresseDAO;
import gov.pr.celepar.ucs_manterinstituicao.pojo.AreaInteresse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

public class AreaInteresseHibernateDAO extends GenericHibernateDAO<AreaInteresse, Long> implements AreaInteresseDAO {
	private static Logger log = Logger.getLogger(AreaInteresseHibernateDAO.class);
	@Override
	public Collection<AreaInteresse> listar() throws ApplicationException {
		return super.listar();
	}

	@SuppressWarnings("unchecked")
	public Set<AreaInteresse> listarSeleciondas(
			String[] areasInteresseSelecionadas) throws ApplicationException{
		Collection<AreaInteresse> coll = new ArrayList<AreaInteresse>();
		try {
			if(areasInteresseSelecionadas.length > 0) {
				Session session = HibernateUtil.currentSession();
				StringBuilder sql = new StringBuilder("from AreaInteresse ai where ai.codAreaInteresse = :cai0 ");

				for (int i = 1; i < areasInteresseSelecionadas.length; i++) {
					sql.append("or ai.codAreaInteresse = :cai" + i + " ");
				}
				
				Query q = session.createQuery(sql.toString());
				
				int i = 0;
				for (String string : areasInteresseSelecionadas) {
					q.setInteger("cai" + i, Integer.parseInt(string));
					i++;
				}
				
				coll = q.list();
			}
		} catch (Exception e) {
			log.debug("Problema ao realizar a funcao: buscarQtdLista", e);
			throw new ApplicationException("mensagem.erro.instituicao.listar", e);
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log.error("Problema ao tentar fechar conexao com o banco de dados: buscarQtdAlunos", e);
			}
		}
		return new HashSet<AreaInteresse>(coll);
	}
}

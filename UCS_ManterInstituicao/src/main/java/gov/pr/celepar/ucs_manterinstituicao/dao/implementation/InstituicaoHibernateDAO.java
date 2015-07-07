package gov.pr.celepar.ucs_manterinstituicao.dao.implementation;

import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.ucs_manterinstituicao.dao.dao.InstituicaoDAO;
import gov.pr.celepar.ucs_manterinstituicao.form.ManterInstituicaoForm;
import gov.pr.celepar.ucs_manterinstituicao.pojo.Instituicao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

public class InstituicaoHibernateDAO extends GenericHibernateDAO<Instituicao, Long> implements InstituicaoDAO{
	private static Logger log = Logger.getLogger(InstituicaoHibernateDAO.class);
	

	private String createCustomQuery(ManterInstituicaoForm mif, StringBuilder sql) {
		//TODO fazer pesquisa not case sensitive	
		if(mif.getNaturezaJuridica() != null && !mif.getNaturezaJuridica().isEmpty()
				&& mif.getNaturezaJuridica().compareTo("0") != 0){
			sql.append("and i.naturezaJuridica = :nj ");
		}
		if(mif.getRazaoSocial() != null && !mif.getRazaoSocial().isEmpty()){
			sql.append("and i.razaoSocial like :rs ");
		}
		if(mif.getPorte() != null && !mif.getPorte().isEmpty() 
				&& mif.getPorte().compareTo("0") != 0){
			sql.append("and i.porte = :porte ");
		}
		
		if(mif.getDataCadastroInicio() != null && !mif.getDataCadastroInicio().isEmpty()){
			sql.append("and i.dataCriacao >= :dci ");
		}
		if(mif.getDataCadastroFim() != null && !mif.getDataCadastroFim().isEmpty()){
			sql.append("and i.dataCriacao <= :dcf");
		}
		return sql.toString();
	}
	
	private void setKeyQueries(Query q, ManterInstituicaoForm mif) throws ParseException {
		if(mif.getCnpj() == null || mif.getCnpj().isEmpty() )
			q.setString("cnpj", "%%");
		else {
			String cnpj = mif.getCnpj();
			q.setString("cnpj", cnpj.replace(".", "").replace("/","").replace("-", ""));
		}
		if(mif.getNaturezaJuridica() != null && !mif.getNaturezaJuridica().isEmpty() 
				&& mif.getNaturezaJuridica().compareTo("0") != 0){
			q.setInteger("nj", Integer.parseInt(mif.getNaturezaJuridica()));
		}
		if(mif.getRazaoSocial() != null && !mif.getRazaoSocial().isEmpty()){
			q.setString("rs", "%" + mif.getRazaoSocial() + "%");
		}
		if(mif.getPorte() != null && !mif.getPorte().isEmpty()
				&& mif.getPorte().compareTo("0") != 0){
			q.setInteger("porte", Integer.parseInt(mif.getPorte()));
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(mif.getDataCadastroInicio() != null && !mif.getDataCadastroInicio().isEmpty()){
			q.setTimestamp("dci", sdf.parse(mif.getDataCadastroInicio()));
		}
		if(mif.getDataCadastroFim() != null && !mif.getDataCadastroFim().isEmpty()){
			q.setTimestamp("dcf", sdf.parse(mif.getDataCadastroFim()));
		}
	}
	
	@Override
	public Long buscarQtdLista(ManterInstituicaoForm mif)
			throws ApplicationException {
		Long qtd = null;
		try {
			Session session = HibernateUtil.currentSession();

			StringBuilder sql = new StringBuilder("select count(*) from Instituicao i where i.cnpj like :cnpj ");
			Query q = session.createQuery(createCustomQuery(mif, sql));
			setKeyQueries(q, mif);
			qtd = (Long) q.uniqueResult();
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
		return qtd;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Instituicao> listar(ManterInstituicaoForm mif,
			Integer quantidade, Integer paginaAtual) throws ApplicationException {
		Collection<Instituicao> coll = new ArrayList<Instituicao>();
		try {
			Session session = HibernateUtil.currentSession();
			StringBuilder sql = new StringBuilder("from Instituicao i where i.cnpj like :cnpj ");
			Query q = session.createQuery(createCustomQuery(mif, sql));
			setKeyQueries(q, mif);
			
			if (quantidade!= null && paginaAtual != null) {
				q.setMaxResults(quantidade.intValue());
				q.setFirstResult( ((paginaAtual.intValue()-1) * quantidade.intValue()));
			}
			
			coll = q.list();
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
		return coll;
	}

	@Override
	public Instituicao obter(Integer codInstituicao)
			throws ApplicationException {
		Instituicao instituicao = null;
		try {
			Session session = HibernateUtil.currentSession();
			StringBuilder sql = new StringBuilder("from Instituicao i ");
			sql.append("left join fetch i.instituicaoTelefones t ");
			sql.append("left join fetch i.areaInteresses a ");
			sql.append("where i.codInstituicao = :ci ");
			
			Query q = session.createQuery(sql.toString());
			q.setInteger("ci", codInstituicao);
			instituicao = (Instituicao)q.uniqueResult();
			/*CriteriaQuery instituicaoCriteria = (CriteriaQuery) session.createCriteria(Instituicao.class);
			instituicao*/

		} catch (Exception e) {
			log.debug("Problema ao realizar a funcao: obter", e);
			throw new ApplicationException("mensagem.erro.instituicao.obter", e);
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log.error("Problema ao tentar fechar conexao com o banco de dados: obter Instituicao", e);
			}
		}
		return instituicao;
	}
	
	public void salvar(Instituicao instituicao) throws ApplicationException {				
		try {			
			Session session = HibernateUtil.currentSession();			
			session.save(instituicao);
		} catch (Exception he) {
			throw new ApplicationException("mensagem.erro.instituicao.salvar", he);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log.error("Problema ao tentar fechar conexao com o banco de dados: salvarInstuicao", e);
			}
		}
	}
	
	public void alterar(Instituicao instituicao) throws ApplicationException {		
		try {						
			Session session = HibernateUtil.currentSession();
			session.update(instituicao);
			
		} catch (Exception e) {			
			throw new ApplicationException("mensagem.erro.instituicao.alterar", e);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log.error("Problema ao tentar fechar conexao com o banco de dados: alterarInstituição", e);
			}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<String> listarCNPJ() throws ApplicationException {
		Collection<String> coll = new ArrayList<String>();
		try {
			Session session = HibernateUtil.currentSession();
			StringBuilder sql = new StringBuilder("select i.cnpj from Instituicao i");
			Query q = session.createQuery(sql.toString());
			coll = q.list();
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
		return coll;
	}
}

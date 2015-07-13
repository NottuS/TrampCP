package gov.pr.celepar.ucs_manterinstituicao.dao.implementation;

import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.ucs_manterinstituicao.dao.dao.ColaboradorDAO;
import gov.pr.celepar.ucs_manterinstituicao.pojo.Colaborador;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

public class ColaboradorHibernateDAO extends GenericHibernateDAO<Colaborador, Integer> implements ColaboradorDAO{
	private static Logger log = Logger.getLogger(InstituicaoHibernateDAO.class);

	private String createCustomQuery(Colaborador colaborador, StringBuilder sql, Date dataInicial, Date dataFinal, String situacao) {

		if(colaborador.getNome() != null && !colaborador.getNome().isEmpty()){
			sql.append("and upper(c.nome) like upper(:nome) ");
		}
		if(dataInicial != null){
			sql.append("and c.dataAdmissao > :dataIncial and (c.dataDEmissao is null or c.dataDEmissao > :dataAtual) ");
		}
		//TODO testar datas de novo
		if(dataFinal != null){
			sql.append("and c.dataAdmissao < :dataFinal and (c.dataDEmissao is null or c.dataDEmissao > :dataAtual)");
		}
		if(situacao != null){
			if(situacao.compareTo("Ativos") == 0){
				sql.append("and c.dataDEmissao is null ");
			} 
			if(situacao.compareTo("Inativos") == 0){
				sql.append("and c.dataDEmissao is not null and c.dataDEmissao < :dataAtual ");
			}
		}
		
		return sql.toString();
	}
	
	private void setKeyQueries(Query q, Colaborador colaborador, Date dataInicial, Date dataFinal, String situacao) {
		if(colaborador.getCpf() != null && !colaborador.getCpf().isEmpty()){
			q.setString("cpf", colaborador.getCpf());
		} else {
			q.setString("cpf", "%%");
		}
		if(colaborador.getNome() != null && !colaborador.getNome().isEmpty()){
			q.setString("nome", colaborador.getNome() + "%");
		}
		if(dataInicial != null){
			q.setTimestamp( "dataIncial", dataInicial);
		}
		
		if(dataFinal != null){
			q.setTimestamp("dataFinal", dataFinal);
		}
	}

	@Override
	public Long buscarQtdLista(Colaborador colaborador, Date dataInicial, Date dataFinal, String situacao)
			throws ApplicationException {
		Long qtd = null;
		try {
			Session session = HibernateUtil.currentSession();

			StringBuilder sql = new StringBuilder("select count(*) from Colaborador c where c.cpf like :cpf ");
			Query q = session.createQuery(createCustomQuery(colaborador, sql, dataInicial, dataFinal, situacao));
			setKeyQueries(q, colaborador, dataInicial, dataFinal, situacao);
			if(situacao != null && (situacao.compareTo("Inativos") == 0 || dataInicial != null 
					|| dataFinal != null)){
				q.setTimestamp("dataAtual", new Date());
			}
			qtd = (Long) q.uniqueResult();
		} catch (Exception e) {
			log.debug("Problema ao realizar a funcao: buscarQtdLista", e);
			throw new ApplicationException("mensagem.erro.colaborador.listar", e);
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log.error("Problema ao tentar fechar conexao com o banco de dados: buscarQtdColaborador", e);
			}
		}
		return qtd;
	}

	@SuppressWarnings("unchecked")
	public Collection<Colaborador> listar(Colaborador colaborador, Date dataInicial, Date dataFinal, String situacao,
			Integer quantidade, Integer paginaAtual) throws ApplicationException{
		Collection<Colaborador> collection = new ArrayList<Colaborador>();
		try {
			Session session = HibernateUtil.currentSession();
			
			StringBuilder sql = new StringBuilder("select new gov.pr.celepar.ucs_manterinstituicao.pojo.CustomList("
					+ "c.idColaborador, c.cpf, c.nome, c.instituicao, c.dataAdmissao, c.dataDEmissao, "
					+ "(case when (c.dataDEmissao is null or c.dataDEmissao > :dataAtual) then true else false end)) "
					+ "from Colaborador c where c.cpf like :cpf ");
			Query q = session.createQuery(createCustomQuery(colaborador, sql, dataInicial, dataFinal, situacao).concat(" order by c.nome "));
			setKeyQueries(q, colaborador, dataInicial, dataFinal, situacao);
			
			if (quantidade!= null && paginaAtual != null) {
				q.setMaxResults(quantidade.intValue());
				q.setFirstResult( ((paginaAtual.intValue()-1) * quantidade.intValue()));
			}
			q.setTimestamp("dataAtual", new Date());
			collection = q.list();
		} catch (Exception e) {
			log.debug("Problema ao realizar a funcao: listar colaborador", e);
			throw new ApplicationException("mensagem.erro.colaborador.listar", e);
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log.error("Problema ao tentar fechar conexao com o banco de dados:listar colaborador", e);
			}
		}
		return collection;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Colaborador> listar() throws ApplicationException{
		Collection<Colaborador> collection = new ArrayList<Colaborador>();
		try {
			Session session = HibernateUtil.currentSession();
			
			StringBuilder sql = new StringBuilder(" From Colaborador");
			Query q = session.createQuery(sql.toString());
			collection = q.list();
		} catch (Exception e) {
			log.debug("Problema ao realizar a funcao: listar colaborador", e);
			throw new ApplicationException("mensagem.erro.colaborador.listar", e);
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log.error("Problema ao tentar fechar conexao com o banco de dados:listar colaborador", e);
			}
		}
		return collection;
	}
}

package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.NotificacaoDAO;
import gov.pr.celepar.abi.dto.FiltroRelatorioNotificacaoBemImovelDTO;
import gov.pr.celepar.abi.dto.RelatorioNotificacaoDTO;
import gov.pr.celepar.abi.pojo.Instituicao;
import gov.pr.celepar.abi.pojo.Notificacao;
import gov.pr.celepar.abi.pojo.Orgao;
import gov.pr.celepar.abi.pojo.ParametroAgenda;
import gov.pr.celepar.abi.util.Dominios.statusTermo;
import gov.pr.celepar.framework.database.GenericHibernateDAO;
import gov.pr.celepar.framework.database.HibernateUtil;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.StringUtil;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 * Classe de manipulação de objetos da classe Notificacao.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public class NotificacaoHibernateDAO extends GenericHibernateDAO<Notificacao, Integer> implements NotificacaoDAO {

	private static Logger log4j = Logger.getLogger(DocumentacaoHibernateDAO.class);
	@SuppressWarnings("unchecked")
	public Collection<Notificacao> listarComRelacionamentos(FiltroRelatorioNotificacaoBemImovelDTO rnbiDTO) throws ApplicationException {
		
		Collection<Notificacao> coll = new ArrayList<Notificacao>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");	
		
		try {
						
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM Notificacao n left join fetch n.documentacao d left join  d.bemImovel b");
			hql.append(	" left join fetch b.classificacaoBemImovel");
			hql.append(	" left join fetch b.situacaoLegalCartorial") ;
			hql.append(	" left join fetch b.situacaoImovel") ;
			//Incluido para adequar a disponibilizacao para orgaos
			hql.append(	" left join  b.ocupacaosTerreno ot") ;
			hql.append(	" left join  ot.orgao orgTer") ;
			hql.append(	" left join  b.edificacaos edi");
			hql.append(" LEFT JOIN  edi.ocupacaos ocu ");
			hql.append(" LEFT JOIN  ocu.orgao orgOcu ");
			hql.append(	" left join  b.listaTransferencia transferencia") ;
			hql.append(	" left join  transferencia.orgaoCessionario orgaoTrans") ;
			hql.append(" LEFT JOIN FETCH b.instituicao i ") ;
			
			//
			
			hql.append(	" WHERE 1=1  ");
			if ( StringUtil.stringNotNull(rnbiDTO.getUf()) && !rnbiDTO.getUf().equals("0")) {
				hql.append("AND n.documentacao.bemImovel.uf =:uf ");
			}	
			if ( StringUtil.stringNotNull(rnbiDTO.getCodMunicipio()) && !rnbiDTO.getCodMunicipio().equals("0") ) {
				hql.append("AND n.documentacao.bemImovel.codMunicipio =:codMunicipio ");
			}
			if ( StringUtil.stringNotNull(rnbiDTO.getCodClassificacao())) {
				hql.append("AND n.documentacao.bemImovel.classificacaoBemImovel.codClassificacaoBemImovel =:codClassificacaoBemImovel ");
			}			
			if ( StringUtil.stringNotNull(rnbiDTO.getCodSituacao())) {
				hql.append("AND n.documentacao.bemImovel.situacaoImovel.codSituacaoImovel =:codSituacao ");
			}
			if ( StringUtil.stringNotNull(rnbiDTO.getFiltroDataNotificacaoDe())) {
				hql.append("AND n.dataNotificacao >= :dataDe ");
			}
			if ( StringUtil.stringNotNull(rnbiDTO.getFiltroDataNotificacaoAte())) {
				hql.append("AND n.dataNotificacao <= :dataAte ");
			}
			if ( StringUtil.stringNotNull(rnbiDTO.getFiltroTerreno())) {
				if (rnbiDTO.getFiltroTerreno().equals("2")) //com edificações
				{
					hql.append("AND n.documentacao.bemImovel.edificacaos is not empty ");
				}else if (rnbiDTO.getFiltroTerreno().equals("3")) //sem edificações
				{
					hql.append("AND n.documentacao.bemImovel.edificacaos is empty ");
				}
			}
			if ( StringUtil.stringNotNull(rnbiDTO.getFiltroAdministracao())) {
				hql.append("AND b.administracao =:administracao ");
			}

			//Incluido para adequar a disponibilizacao para orgaos
			if (rnbiDTO.getIndOperadorOrgao() != null && rnbiDTO.getIndOperadorOrgao()){
				hql.append("AND (");
				hql.append(" (orgTer.codOrgao IN (:listaCodOrgao)) OR");
				hql.append(" (orgOcu.codOrgao IN (:listaCodOrgao)) OR");
				hql.append(" (transferencia.statusTermo.codStatusTermo = :codStatusTermo AND transferencia.orgaoCessionario.codOrgao IN (:listaCodOrgao))");
				hql.append(")");
			}
			if (rnbiDTO.getInstituicao()!= null){
				hql.append(" AND i.codInstituicao= :codInstituicao ") ;
			}
			//
			
			hql.append("ORDER BY n.documentacao.bemImovel.uf, n.documentacao.bemImovel.municipio, n.documentacao.bemImovel.codBemImovel ");
			
			Query q = session.createQuery(hql.toString());
			
			
			if ( StringUtil.stringNotNull(rnbiDTO.getUf()) && !rnbiDTO.getUf().equals("0")) {
				q.setString("uf", rnbiDTO.getUf());	
			}
			
			if ( StringUtil.stringNotNull(rnbiDTO.getCodMunicipio()) && !rnbiDTO.getCodMunicipio().equals("0") ) {
				q.setInteger("codMunicipio", Integer.valueOf(rnbiDTO.getCodMunicipio()));
			}

			if ( StringUtil.stringNotNull(rnbiDTO.getCodClassificacao())) {
				q.setInteger("codClassificacaoBemImovel", Integer.valueOf(rnbiDTO.getCodClassificacao()));				
			}
			
			if ( StringUtil.stringNotNull(rnbiDTO.getCodSituacao())) {
				q.setInteger("codSituacao", Integer.valueOf(rnbiDTO.getCodSituacao()));
			}
			if ( StringUtil.stringNotNull(rnbiDTO.getFiltroDataNotificacaoDe())) {
				q.setDate("dataDe", sdf.parse(rnbiDTO.getFiltroDataNotificacaoDe()));
			}
			if ( StringUtil.stringNotNull(rnbiDTO.getFiltroDataNotificacaoAte())) {
				q.setDate("dataAte", sdf.parse(rnbiDTO.getFiltroDataNotificacaoAte()));
			}
			if ( StringUtil.stringNotNull(rnbiDTO.getFiltroAdministracao())) {
				q.setInteger("administracao", Integer.valueOf(rnbiDTO.getFiltroAdministracao()));
			}
			
			//Incluido para adequar a disponibilizacao para orgaos
			if (rnbiDTO.getIndOperadorOrgao() != null && rnbiDTO.getIndOperadorOrgao()){
				q.setInteger("codStatusTermo", statusTermo.VIGENTE.getIndex());
				List<Integer> listaCodOrgao = new ArrayList<Integer>();
				for (Orgao o : rnbiDTO.getListaOrgao()){
					listaCodOrgao.add(o.getCodOrgao());
				}
				q.setParameterList("listaCodOrgao", listaCodOrgao);
			}
			//
			if (rnbiDTO.getInstituicao()!= null){
				q.setInteger("codInstituicao", rnbiDTO.getInstituicao().getCodInstituicao());
			}
			coll= q.list();
				

		} catch (HibernateException he) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Notificação"});
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Notificação"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarNotificacaoComRelacionamentos", e);
			}		
		}
		
		return coll;
	}
	@Override
	public Notificacao salvarNotificacao(Notificacao notificacao) throws ApplicationException {
		Notificacao retorno = new Notificacao();
		// Validando parâmetro
		if (notificacao == null){
			throw new ApplicationException("mensagem.erro.9002", new String[]{"NotificacaoHibernateDAO.salvarNotificacao()"}, ApplicationException.ICON_ERRO);
		}
		
		try {
			log4j.debug("Obtendo sessao corrente...");
			Session session = HibernateUtil.currentSession();
			session.clear();
			Serializable chave = session.save(notificacao);
			session.flush();
			log4j.info("SALVAR:" + notificacao.getClass().getName() + " salvo.");
			retorno = (Notificacao) session.get(Notificacao.class, chave);
			log4j.info("GET:" + notificacao.getClass().getName() + " q acabou de ser salvo.");
						
		} catch (ConstraintViolationException cve) {
			log4j.error("NotificacaoHibernateDAO.salvarNotificacao()" + cve);
			throw new ApplicationException("mensagem.erro.9003", new String[]{notificacao.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
		} catch (HibernateException he) {
			log4j.error("NotificacaoHibernateDAO.salvarNotificacao()" + he);
			throw new ApplicationException("mensagem.erro.9001", new String[]{"NotificacaoHibernateDAO.salvarNotificacao()"}, he, ApplicationException.ICON_ERRO);
		} catch (Exception e) {
			log4j.error("NotificacaoHibernateDAO.salvarNotificacao()" + e);
			throw new ApplicationException("mensagem.erro.9001",new String[]{"NotificacaoHibernateDAO.salvarNotificacao()"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				log4j.debug("Fechando sessao corrente.");
				HibernateUtil.closeSession();
			} catch (Exception e) {
				throw new ApplicationException("mensagem.erro.9003", new String[]{notificacao.getClass().getSimpleName()}, ApplicationException.ICON_AVISO);
			}
		}
		
		return retorno;
	}

	public Collection<Notificacao> listarVencidaAVencer(Instituicao instituicao, ParametroAgenda parametroAgenda) throws ApplicationException {
		
		Collection<Notificacao> coll = new ArrayList<Notificacao>();
		
		try {
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" FROM Notificacao n ");
			hql.append(" left join fetch n.documentacao d ");
			hql.append(" left join fetch d.bemImovel b ");
			hql.append(" WHERE n.dataSolucao is null  ");
			hql.append(" AND b.instituicao.codInstituicao = :codInstituicao");
			if (parametroAgenda != null) {
				hql.append(" AND (n.prazoNotificacao - :dias) <= :dataAtual AND  n.prazoNotificacao >= :dataAtual");
			}else{
				hql.append(" AND n.prazoNotificacao < :dataAtual ");
			}
			
			Query q = session.createQuery(hql.toString());
			

			if (parametroAgenda != null) {
				q.setInteger("dias", parametroAgenda.getNumeroDiasVencimentoNotificacao());				
			}
			q.setInteger("codInstituicao", instituicao.getCodInstituicao());
			q.setDate("dataAtual", new Date());
				
			coll= q.list();
				

		} catch (HibernateException he) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Notifcação Vencida e a Vencer"});
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Notifcação Vencida e a Vencer"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarVencidaAVencer", e);
			}		
		}
		
		return coll;
	}
	
public Collection<RelatorioNotificacaoDTO> listarParaRelatorioNotificacao(FiltroRelatorioNotificacaoBemImovelDTO rnbiDTO) throws ApplicationException {
		
		Collection<RelatorioNotificacaoDTO> coll;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");	
		
		try {
						
			Session session = HibernateUtil.currentSession();
			StringBuffer hql = new StringBuffer();
			hql.append(" select distinct new gov.pr.celepar.abi.dto.RelatorioNotificacaoDTO ( ");
			hql.append(" n.codNotificacao, ");
			hql.append(" b.codBemImovel, ");
			hql.append(" n.prazoNotificacao, ");
			hql.append(" n.dataNotificacao, ");
			hql.append(" si.descricao, ");
			hql.append(" slc.descricao, ");
			hql.append(" ci.descricao, ");
			hql.append(" d.numeroDocumentoCartorial, ");
			hql.append(" b.nrBemImovel, ");
			hql.append(" i.codInstituicao, ");
			hql.append(" i.sigla, ");
			hql.append(" b.uf, ");
			hql.append(" b.municipio ");
			hql.append(" ) ");
						
			hql.append(" FROM Notificacao n left join  n.documentacao d left join  d.bemImovel b");
			hql.append(	" left join  b.classificacaoBemImovel ci");
			hql.append(	" left join  b.situacaoLegalCartorial slc") ;
			hql.append(	" left join  b.situacaoImovel si") ;
			//Incluido para adequar a disponibilizacao para orgaos
			hql.append(	" left join  b.ocupacaosTerreno ot") ;
			hql.append(	" left join  ot.orgao orgTer") ;
			hql.append(	" left join  b.edificacaos edi");
			hql.append(" LEFT JOIN  edi.ocupacaos ocu ");
			hql.append(" LEFT JOIN  ocu.orgao orgOcu ");
			hql.append(	" left join  b.listaTransferencia transferencia") ;
			hql.append(	" left join  transferencia.orgaoCessionario orgaoTrans") ;
			hql.append(" LEFT JOIN  b.instituicao i ") ;
			
			//
			
			hql.append(	" WHERE 1=1  ");
			if ( StringUtil.stringNotNull(rnbiDTO.getUf()) && !rnbiDTO.getUf().equals("0")) {
				hql.append("AND n.documentacao.bemImovel.uf =:uf ");
			}	
			if ( StringUtil.stringNotNull(rnbiDTO.getCodMunicipio()) && !rnbiDTO.getCodMunicipio().equals("0") ) {
				hql.append("AND n.documentacao.bemImovel.codMunicipio =:codMunicipio ");
			}
			if ( StringUtil.stringNotNull(rnbiDTO.getCodClassificacao())) {
				hql.append("AND n.documentacao.bemImovel.classificacaoBemImovel.codClassificacaoBemImovel =:codClassificacaoBemImovel ");
			}			
			if ( StringUtil.stringNotNull(rnbiDTO.getCodSituacao())) {
				hql.append("AND n.documentacao.bemImovel.situacaoImovel.codSituacaoImovel =:codSituacao ");
			}
			if ( StringUtil.stringNotNull(rnbiDTO.getFiltroDataNotificacaoDe())) {
				hql.append("AND n.dataNotificacao >= :dataDe ");
			}
			if ( StringUtil.stringNotNull(rnbiDTO.getFiltroDataNotificacaoAte())) {
				hql.append("AND n.dataNotificacao <= :dataAte ");
			}
			if ( StringUtil.stringNotNull(rnbiDTO.getFiltroTerreno())) {
				if (rnbiDTO.getFiltroTerreno().equals("2")) //com edificações
				{
					hql.append("AND n.documentacao.bemImovel.edificacaos is not empty ");
				}else if (rnbiDTO.getFiltroTerreno().equals("3")) //sem edificações
				{
					hql.append("AND n.documentacao.bemImovel.edificacaos is empty ");
				}
			}
			if ( StringUtil.stringNotNull(rnbiDTO.getFiltroAdministracao())) {
				hql.append("AND b.administracao =:administracao ");
			}

			//Incluido para adequar a disponibilizacao para orgaos
			if (rnbiDTO.getIndOperadorOrgao() != null && rnbiDTO.getIndOperadorOrgao()){
				hql.append("AND (");
				hql.append(" (orgTer.codOrgao IN (:listaCodOrgao)) OR");
				hql.append(" (orgOcu.codOrgao IN (:listaCodOrgao)) OR");
				hql.append(" (transferencia.statusTermo.codStatusTermo = :codStatusTermo AND transferencia.orgaoCessionario.codOrgao IN (:listaCodOrgao))");
				hql.append(")");
			}
			if (rnbiDTO.getInstituicao()!= null){
				hql.append(" AND i.codInstituicao= :codInstituicao ") ;
			}
			//
			
			hql.append("ORDER BY b.uf, b.municipio, b.codBemImovel ");
			
			Query q = session.createQuery(hql.toString());
			
			
			if ( StringUtil.stringNotNull(rnbiDTO.getUf()) && !rnbiDTO.getUf().equals("0")) {
				q.setString("uf", rnbiDTO.getUf());	
			}
			
			if ( StringUtil.stringNotNull(rnbiDTO.getCodMunicipio()) && !rnbiDTO.getCodMunicipio().equals("0") ) {
				q.setInteger("codMunicipio", Integer.valueOf(rnbiDTO.getCodMunicipio()));
			}

			if ( StringUtil.stringNotNull(rnbiDTO.getCodClassificacao())) {
				q.setInteger("codClassificacaoBemImovel", Integer.valueOf(rnbiDTO.getCodClassificacao()));				
			}
			
			if ( StringUtil.stringNotNull(rnbiDTO.getCodSituacao())) {
				q.setInteger("codSituacao", Integer.valueOf(rnbiDTO.getCodSituacao()));
			}
			if ( StringUtil.stringNotNull(rnbiDTO.getFiltroDataNotificacaoDe())) {
				q.setDate("dataDe", sdf.parse(rnbiDTO.getFiltroDataNotificacaoDe()));
			}
			if ( StringUtil.stringNotNull(rnbiDTO.getFiltroDataNotificacaoAte())) {
				q.setDate("dataAte", sdf.parse(rnbiDTO.getFiltroDataNotificacaoAte()));
			}
			if ( StringUtil.stringNotNull(rnbiDTO.getFiltroAdministracao())) {
				q.setInteger("administracao", Integer.valueOf(rnbiDTO.getFiltroAdministracao()));
			}
			
			//Incluido para adequar a disponibilizacao para orgaos
			if (rnbiDTO.getIndOperadorOrgao() != null && rnbiDTO.getIndOperadorOrgao()){
				q.setInteger("codStatusTermo", statusTermo.VIGENTE.getIndex());
				List<Integer> listaCodOrgao = new ArrayList<Integer>();
				for (Orgao o : rnbiDTO.getListaOrgao()){
					listaCodOrgao.add(o.getCodOrgao());
				}
				q.setParameterList("listaCodOrgao", listaCodOrgao);
			}
			//
			if (rnbiDTO.getInstituicao()!= null){
				q.setInteger("codInstituicao", rnbiDTO.getInstituicao().getCodInstituicao());
			}
			coll= q.list();
				

		} catch (HibernateException he) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Notificação para relatório"});
		} catch (Exception e) {
			throw new ApplicationException("ERRO.201",  new String[]{"ao listar Notificação para relatório"}, e, ApplicationException.ICON_ERRO);
		} finally {
			try {
				HibernateUtil.closeSession();
			}catch (Exception e) {
				log4j.error("Problema ao tentar fechar conexao com o banco de dados: listarNotificacaoComRelacionamentos", e);
			}		
		}
		
		return coll;
	}


}

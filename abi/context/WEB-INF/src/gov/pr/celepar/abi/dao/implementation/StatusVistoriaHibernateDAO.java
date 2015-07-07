package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.StatusVistoriaDAO;
import gov.pr.celepar.abi.pojo.StatusVistoria;
import gov.pr.celepar.framework.database.GenericHibernateDAO;

public class StatusVistoriaHibernateDAO extends GenericHibernateDAO<StatusVistoria, Integer> implements StatusVistoriaDAO {
	
	protected static final String LOGINICIO = "Abrindo Sessao Hibernate";
	protected static final String LOGFIM = "Fechando Sessao Hibernate";
	protected static final String LOGERROFECHARSESSAO = "Problema ao tentar fechar sessao do Hibernate";
	
}
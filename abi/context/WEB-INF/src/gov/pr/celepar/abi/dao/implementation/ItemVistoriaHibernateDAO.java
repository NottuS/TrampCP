package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.ItemVistoriaDAO;
import gov.pr.celepar.abi.pojo.ItemVistoria;
import gov.pr.celepar.framework.database.GenericHibernateDAO;

public class ItemVistoriaHibernateDAO extends GenericHibernateDAO<ItemVistoria, Integer> implements ItemVistoriaDAO {
	
	protected static final String LOGINICIO = "Abrindo Sessao Hibernate";
	protected static final String LOGFIM = "Fechando Sessao Hibernate";
	protected static final String LOGERROFECHARSESSAO = "Problema ao tentar fechar sessao do Hibernate";

}

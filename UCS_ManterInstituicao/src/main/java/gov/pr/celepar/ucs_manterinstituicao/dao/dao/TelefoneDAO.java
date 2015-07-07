package gov.pr.celepar.ucs_manterinstituicao.dao.dao;

import java.util.Collection;

import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.ucs_manterinstituicao.pojo.Telefone;

public interface TelefoneDAO extends GenericDAO<Telefone, Long>{
	public Collection<Telefone> listar(Telefone telefone, Integer qtdPagina, Integer numPagina) throws ApplicationException;
	public Collection<Telefone> listar(Telefone telefone) throws ApplicationException;
	public Collection<Telefone> listar() throws ApplicationException;
	public void delete(Collection<Telefone> telefones) throws ApplicationException;
}

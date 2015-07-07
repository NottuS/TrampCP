package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.pojo.Usuario;
import gov.pr.celepar.abi.pojo.UsuarioOrgao;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;

public interface UsuarioOrgaoDAO extends GenericDAO<UsuarioOrgao, Integer> {

	public Collection<UsuarioOrgao> listar(Integer qtdPagina, Integer numPagina, Usuario usuario) throws ApplicationException;

	public UsuarioOrgao obter(UsuarioOrgao aux) throws ApplicationException ;
	
	public void excluirPorUsuario( Usuario usuario) throws ApplicationException;


}

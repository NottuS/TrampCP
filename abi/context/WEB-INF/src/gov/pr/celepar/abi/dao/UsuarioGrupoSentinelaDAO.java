package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.pojo.Usuario;
import gov.pr.celepar.abi.pojo.UsuarioGrupoSentinela;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;

public interface UsuarioGrupoSentinelaDAO extends GenericDAO<UsuarioGrupoSentinela, Integer> {

	public Collection<UsuarioGrupoSentinela> listar(Integer qtdPagina, Integer numPagina, Usuario usuario) throws ApplicationException;

	public UsuarioGrupoSentinela obter(UsuarioGrupoSentinela aux) throws ApplicationException;

	public void excluirPorUsuario( Usuario usuario) throws ApplicationException;
}

package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.pojo.Usuario;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;

/**
 * Classe de manipulação de objetos da classe Edificacao.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public interface UsuarioDAO extends GenericDAO<Usuario, Integer> {
	
	public Usuario obterPorIdSentinela(Long idSentinela) throws ApplicationException;

	public Collection<Usuario> listar(Integer qtdPagina, Integer numPagina, Usuario objPesquisa) throws ApplicationException;

	public Usuario salvarUsuario(Usuario usuario) throws ApplicationException;

	public Usuario obterByCPF(String cpf) throws ApplicationException;

	public Usuario obterCompleto(Integer codUsuario) throws ApplicationException;

}

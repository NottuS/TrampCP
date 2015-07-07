package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.dto.ItemComboDTO;
import gov.pr.celepar.abi.pojo.Orgao;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;

/**
 * Classe de manipulação de objetos da classe Orgao.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, 24/12/2009
 *
 */
public interface OrgaoDAO extends GenericDAO<Orgao, Integer> {


	public Orgao obterOrgaoPorDescricao(String descOrgao) throws ApplicationException;
	public Orgao obterOrgaoPorSigla(String sigla) throws ApplicationException;
	public Collection<Orgao> listarByTipoAdm(Integer tipoAdm) throws ApplicationException;
	public Collection<Orgao> listarByTipoAdmUsuario(Integer tipoAdm, Integer codUsuario) throws ApplicationException;
	public Collection<Orgao> listar(Integer qtdPagina, Integer numPagina, String sigla, String descricao, Integer codInstituicao) throws ApplicationException;
	public Integer obterQuantidadeLista(Integer qtdPagina, Integer numPagina, String sigla, String descricao, Integer codInstituicao) throws ApplicationException;
	public boolean existeSiglaDescricao(String sigla, String descricao, Integer codOrgao, Integer codInstituicao) throws ApplicationException;
	public Collection<ItemComboDTO> listarPorTipoAdmEUsuarioSentinela(Integer tipoAdm, Long codUsuarioSentinela) throws ApplicationException;
	public Collection<ItemComboDTO> listarPorTipoAdmECodInstituicao(Integer tipoAdm, Integer codInstituicao) throws ApplicationException;
	public Collection<ItemComboDTO> listarParaAssinaturaPorTipoAdmECodInstituicao(int tipoAdm, Integer codInstituicao) throws ApplicationException;

}

package gov.pr.celepar.ucs_manterinstituicao.dao.dao;

import java.util.Collection;

import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.ucs_manterinstituicao.form.ManterInstituicaoForm;
import gov.pr.celepar.ucs_manterinstituicao.pojo.Instituicao;

public interface InstituicaoDAO extends GenericDAO<Instituicao, Long>{
	public Collection<Instituicao> listar(Instituicao instituicao, Integer qtdPagina, Integer numPagina) throws ApplicationException;
	
	public Long buscarQtdLista(ManterInstituicaoForm mif) throws ApplicationException;	
	
	public void salvar(Instituicao instituicao) throws ApplicationException;
	
	public void alterar(Instituicao instituicao) throws ApplicationException;
	
	public void excluir(Instituicao instituicao) throws ApplicationException;

	public Collection<Instituicao> listar(ManterInstituicaoForm mif, Integer quantidade,
			Integer paginaAtual) throws ApplicationException;
	
	public Instituicao obter(Integer codInstituicao) throws ApplicationException;
	
	public Collection<String> listarCNPJ() throws ApplicationException;
}

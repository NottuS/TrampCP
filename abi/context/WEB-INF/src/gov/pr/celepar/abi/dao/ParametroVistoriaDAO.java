 package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.dto.ParametroVistoriaDTO;
import gov.pr.celepar.abi.pojo.ParametroVistoria;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;
import java.util.List;

/**
 * Classe de manipulação de objetos da classe ParametroVistoria
 * 
 * 
 *
 */
public interface ParametroVistoriaDAO extends GenericDAO<ParametroVistoria, Integer> {
	public List<ParametroVistoria> listarParametroVistoriaComDenominacaoBemImovel(Integer codBemImovel) throws ApplicationException;
	public List<ParametroVistoria> listarParametroVistoriaComDenominacaoBemImovelExceto(Integer codInstituicao, Integer codDenominacaoImovel, List<Integer> listaCodParametroVistoria) throws ApplicationException;
	public Collection<ParametroVistoria> listar(ParametroVistoriaDTO parametroVistoriaDTO ) throws ApplicationException;
	public Boolean existeDescricao(Integer codParametroVistoria, String descricao, Integer codInstituicao) throws ApplicationException;
	public ParametroVistoria merge(ParametroVistoria parametroVistoria) throws ApplicationException;
}
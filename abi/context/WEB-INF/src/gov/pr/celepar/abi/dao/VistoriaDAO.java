package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.pojo.ParametroAgenda;
import gov.pr.celepar.abi.pojo.StatusVistoria;
import gov.pr.celepar.abi.pojo.Vistoria;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface VistoriaDAO extends GenericDAO<Vistoria, Integer>{
	
	public List<Vistoria> listarVistoriaBemImovelPaginado(Integer codBemImovel, Date dataInicial, Date dataFinal, Integer situacaoImovel, Integer qtdePagina, Integer numPagina, List<Integer> listaCodOrgao, Integer codInstituicao) throws ApplicationException;
	public Vistoria obterVistoriaCompleta(Integer codVistoria) throws ApplicationException;
	public List<Vistoria> listarVistoriaPorStatus(StatusVistoria statusVistoria, ParametroAgenda parametroAgenda) throws ApplicationException;
	public Collection<Integer> listarBemImovelPorVistoriador(int codVistoriador) throws ApplicationException;
	public StringBuffer listarVistoriadlPorBemImovel(int codVistoriador, int codBemImovel) throws ApplicationException;
	public void alterarVistoria(Vistoria vistoria) throws ApplicationException;

}

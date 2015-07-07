package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.pojo.Assinatura;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.util.Collection;

/**
 * Classe de manipulacao de objetos da classe CargoAssinatura.
 * 
 * @author Vanessak
 * @since 1.0
 * @version 1.0, 29/06/2011
 *
 */
public interface AssinaturaDAO extends GenericDAO<Assinatura, Integer> {

	public Collection<Assinatura> listar(Integer qtdPagina, Integer numPagina, Assinatura assinatura) throws ApplicationException;
	public Assinatura obterCompleto(Integer codAssinatura) throws ApplicationException;
	public Collection<Assinatura> listarAssinaturaByInstituicao(Assinatura assinatura) throws ApplicationException;
	public Collection<Assinatura> listarByInstituicao(Integer qtdPagina, Integer numPagina, Assinatura assinatura) throws ApplicationException;

}

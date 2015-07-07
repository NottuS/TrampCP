package gov.pr.celepar.abi.dao;

import gov.pr.celepar.abi.pojo.CargoAssinatura;
import gov.pr.celepar.framework.database.GenericDAO;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Pagina;

import java.util.Collection;

/**
 * Classe de manipulacao de objetos da classe CargoAssinatura.
 * 
 * @author Vanessak
 * @since 1.0
 * @version 1.0, 29/06/2011
 *
 */
public interface CargoAssinaturaDAO extends GenericDAO<CargoAssinatura, Integer> {

	public Collection<CargoAssinatura> listarCargoAssinatura(CargoAssinatura cargoAssinatura, Pagina pag) throws ApplicationException;

	public Collection<CargoAssinatura> listarCargoAssinaturaByOrgaoInstituicao(Integer codOrgao, Integer codInstituicao) throws ApplicationException;

	public Collection<CargoAssinatura> listarCargoAssinaturaByInstituicao(CargoAssinatura cargoAssinatura) throws ApplicationException;
}

package gov.pr.celepar.abi.dao;

import gov.pr.celepar.framework.exception.ApplicationException;


/**
 * Classe de manipula��o de Arquivos.
 * 
 * @author Luciana R. B�lico
 * @since 1.0
 * @version 1.0, 03/02/2009
 *
 */
public interface ArquivoDAO  {

	public void uploadArquivo(String nomeArquivo, byte[]data) throws ApplicationException;
	public byte[] downloadArquivo (String nomeArquivo) throws ApplicationException;
	public void excluirAnexoDocumentacao( String nomeArquivo) throws ApplicationException;
	public void uploadLogoInstituicao(String nomeArquivo, byte[] data) throws ApplicationException;
	public  void excluirLogoInstituicao( String nomeArquivo) throws ApplicationException;
	public byte[] downloadLogotipo (String nomeArquivo) throws ApplicationException;

}

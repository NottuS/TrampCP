package gov.pr.celepar.abi.dao.implementation;

import gov.pr.celepar.abi.dao.ArquivoDAO;
import gov.pr.celepar.abi.dao.VistoriaDAO;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.framework.exception.ApplicationException;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;

/**
 * Classe de manipulação de Arquivos.
 * 
 * @author Luciana R. Bélico
 * @since 1.0
 * @version 1.0, 03/02/2010
 *
 */
public class ArquivoServidorDAO  implements ArquivoDAO {

	private static final Logger log4j = Logger.getLogger(VistoriaDAO.class);

	@Override
	public void uploadArquivo(String nomeArquivo, byte[] data)
			throws ApplicationException {
		
	
		 File documento = new File(Dominios.PATH_DOCUMENTACAO+"/"+ nomeArquivo);
         FileOutputStream fos = null;
         try {
             fos = new FileOutputStream(documento);
             fos.write(data);
             fos.close();
         
         }catch (Exception ex)
         {
        	 throw new ApplicationException("ERRO.201", new String[]{"ao realizar o upload do arquivo da Ocorrência de Documentação"}, ex, ApplicationException.ICON_ERRO);
         }    
   
	}
	
	
	public void uploadLogoInstituicao(String nomeArquivo, byte[] data) throws ApplicationException {


		try {
			File documento = new File(Dominios.PATH_LOGO+"/"+ nomeArquivo);
			FileOutputStream fos = new FileOutputStream(documento);
			fos.write(data);
			fos.close();

		}catch (Exception ex) {
			log4j.error(Dominios.PATH_LOGO);
			log4j.error(nomeArquivo);
			log4j.error(Util.logarErro(ex));
			throw new ApplicationException("ERRO.201", new String[]{"ao realizar o upload do arquivo da Logo da Instituição"}, ex, ApplicationException.ICON_ERRO);
		}    

	}
	
	public byte[] downloadArquivo (String nomeArquivo) throws ApplicationException
	{
		  
			
		try {

			File documento = new File(Dominios.PATH_DOCUMENTACAO+"/"+ nomeArquivo);  
			FileInputStream fis = new FileInputStream(documento);
			BufferedInputStream buffReader = new BufferedInputStream(fis); 
			DataInputStream   data = new DataInputStream(buffReader); 
			byte[] dados = new byte[(int) documento.length()];
			data.read(dados);
			
			return dados;

		}
		catch (FileNotFoundException ex)
		{
			throw new ApplicationException("ERRO.ocorrenciaDocumentacao.download", ex, ApplicationException.ICON_ERRO);
		}    catch (Exception ex)
		{
			throw new ApplicationException("ERRO.201", new String[]{"ao realizar o download do arquivo da Ocorrência de Documentação"}, ex, ApplicationException.ICON_ERRO);
		}    
		
	}
	public  void excluirAnexoDocumentacao( String nomeArquivo) throws ApplicationException{
		
         try {
        	File documento = new File(Dominios.PATH_DOCUMENTACAO+"/"+ nomeArquivo);
 		 	if (documento.exists()){
 		 		documento.delete();
 		 	}
         
         }catch (Exception ex)
         {
               	throw new ApplicationException("ERRO.201", new String[]{"ao excluir o arquivo da Ocorrência de Documentação"}, ex, ApplicationException.ICON_ERRO);
         }    
		
	}
	
	public  void excluirLogoInstituicao( String nomeArquivo) throws ApplicationException{
		
        try {
       	File documento = new File(Dominios.PATH_LOGO+"/"+ nomeArquivo+".JPG");
		 	if (documento.exists()){
		 		documento.delete();
		 	}
			 documento = new File(Dominios.PATH_LOGO+"/"+ nomeArquivo+".PNG");
		 	if (documento.exists()){
		 		documento.delete();
		 	}
        
        }catch (Exception ex)
        {
              	throw new ApplicationException("ERRO.201", new String[]{"ao excluir o arquivo do Logotipo da Instituição"}, ex, ApplicationException.ICON_ERRO);
        }    
		
	}
	
	public byte[] downloadLogotipo (String nomeArquivo) throws ApplicationException
	{
		  
			
		try {

			File documento = new File(Dominios.PATH_LOGO+"/"+ nomeArquivo);  
			FileInputStream fis = new FileInputStream(documento);
			BufferedInputStream buffReader = new BufferedInputStream(fis); 
			DataInputStream   data = new DataInputStream(buffReader); 
			byte[] dados = new byte[(int) documento.length()];
			data.read(dados);
			
			return dados;

		}
		catch (FileNotFoundException ex)
		{
			throw new ApplicationException("ERRO.ocorrenciaDocumentacao.download", ex, ApplicationException.ICON_ERRO);
		}    catch (Exception ex)
		{
			throw new ApplicationException("ERRO.201", new String[]{"ao realizar o download do arquivo do Logotipo da Instituição"}, ex, ApplicationException.ICON_ERRO);
		}    
		
	}
	 

}

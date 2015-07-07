package gov.pr.celepar.abi.util;

import gov.pr.celepar.abi.enumeration.GrupoSentinela;
import gov.pr.celepar.abi.pojo.AnexoMail;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.mensagem.Mensagem;
import gov.pr.celepar.framework.util.Reflexao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaParam;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class Util {
	

	private static Logger log = Logger.getLogger(Util.class);
	 /**
	 * Converte um Set em Lista.<br>
	 * @author ed_rodrigues
	 * @since 20/07/2007
	 * @param Set<T> : set, caso nulo retorna list vazio.
	 * @return List<T>
	 * @throws Exception
	 * @review Digam
	 */
	public static <T> List<T> setToList(Set<T> set){
		if(set == null || set.isEmpty()){
			return new ArrayList<T>(0);
		}
		return new ArrayList<T>(set);
	}

	/**
	 * Converte uma Lista em Set.<br>
	 * @author ed_rodrigues
	 * @since 20/07/2007
	 * @param List<T> : list, caso nulo retorna set vazio.
	 * @return Set<T>
	 * @throws Exception
	 * @review Digam
	 */
	public static <T> Set<T> listToSet(List<T> list) {
		if(list == null || list.isEmpty()){
			return new HashSet<T>(0);
		}
		return new HashSet<T>(list);
	}
	
	
	public static String htmlEncode(String str){
		return str.replaceAll(">", "&gt;").replaceAll("<", "&lt;");
	}

	public static Object htmlEncodeBean(Object o) throws ApplicationException {
		if(o == null) return null;
		Collection<Field> listaAtrib = Reflexao.listaAtributos(o);
		String str;
		try {
			for(Field atrib : listaAtrib) {
				if("String".equals(atrib.getType().getSimpleName()) && (str = (String)Reflexao.invocaGet(o, atrib.getName())) != null) {
					Reflexao.invocaSet(o, atrib.getName(), new Object[] {htmlEncode(str)});
				}
			}
		}
		catch(Exception e) {
			throw new ApplicationException("ERRO.GERAL", new String[] {"Erro ao executar Util.htmlEncodeBean"}, ApplicationException.ICON_AVISO);
		}
		return o;
	}

	public static Collection htmlEncodeCollection(Collection lista) throws ApplicationException {
		if(lista == null) return null;
		for(Object object : lista) {
			htmlEncodeBean(object);
		}
		return lista;
	}

	
	public static boolean strEmBranco(String str) {
		return str == null || "".equals(str.replaceAll(" ",""));
	}

	public static Double converteDecimal(String str) throws Exception {
		if(!strEmBranco(str)){
			if(str.indexOf(".")!=-1 && str.indexOf(",")!=-1) {
				int posPt = str.indexOf(".");
				boolean temMaisDeUmPt = str.indexOf(".", posPt+1)!=-1; 
				int posVirg = str.indexOf(",");
				boolean temMaisDeUmVirg = str.indexOf(",", posVirg+1)!=-1;
				if(posPt<posVirg){
					if(!temMaisDeUmVirg){
						str = str.replaceAll("\\.", "").replaceAll(",", ".");
					}else{
						throw new ApplicationException("ERRO.GERAL",new String[] {"Há mais de uma vírgula no \"Valor\": "+str},ApplicationException.ICON_AVISO);
					}
				}else{
					if(!temMaisDeUmPt){
						str = str.replaceAll(",", "");
					}else{
						throw new ApplicationException("ERRO.GERAL",new String[] {"Há mais de um ponto no \"Valor\": "+str},ApplicationException.ICON_AVISO);
					}					
				}
			}else if(str.indexOf(".")!=-1 ){
				int posPt = str.indexOf(".");
				if( str.indexOf(".", posPt+1)==-1 ){
					str = str.replaceAll("\\.", "");
				}else{
					if( str.indexOf(".", posPt+1)==posPt+3 ){
						str = str.replaceAll("\\.", "");	
					}else{
						throw new ApplicationException("ERRO.GERAL",new String[] {"Há mais de um ponto no \"Valor\": "+str},ApplicationException.ICON_AVISO);
					}
				}
			}else if(str.indexOf(",")!=-1 ){
				int posVirg = str.indexOf(",");
				if(str.indexOf(",", posVirg+1)==-1){
					str = str.replaceAll(",", ".");
				}else{
					throw new ApplicationException("ERRO.GERAL",new String[] {"Há mais de uma vírgula no \"Valor\": "+str},ApplicationException.ICON_AVISO);
				}
			}
			return Double.parseDouble( str );
		}else{
			return null;
		}
	}
	public static String converterDecimal(double num) throws Exception {
 	    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
 		dfs.setDecimalSeparator(',');
 		DecimalFormat df = new DecimalFormat("##0.00",dfs);
 		return df.format(num);
    }

	/**
     * Exibe objeto Date no formato "dd/mm/aaaa hh:mm". <br>
     * @param data : Date
     * @return String
     */
	public static String formataDataSemHora(final Date data) {
        return(data != null ? new SimpleDateFormat("dd/MM/yyyy").format(data) : "");
    }

	/**
	 * Verifica a permissão de reativar órgãos ocupantes de terreno
	 * @return boolean
	 * @param sentinelaParam
	 * @throws ApplicationException
	 */
	public static boolean validaPermissaoReativar(SentinelaParam[] sentinelaParam) throws ApplicationException {
		boolean usuarioPerm = false;
		if (sentinelaParam != null) {
			for (SentinelaParam sentinelaParam2 : sentinelaParam) {
				if (GrupoSentinela.ADM_GERAL.getDescricao().trim().equals(sentinelaParam2.getNome().trim())) {
					usuarioPerm = true;
					break;
				}
			}
		}
		return usuarioPerm;
	}
	
	/**
	 * Retorna um numero com formatado com 2 casas decimais e separador de
	 * milhar. Formato de retorno 1.000.000,00
	 * 
	 * @author evandro
	 * @param BigDecimal
	 *            valor monetario
	 * @return String valor monetario formatado
	 */
	public static String formataNumeroMonetario(final BigDecimal number) {
		if(number == null){
			return null;
		}else{
			final NumberFormat formatter = new DecimalFormat(
			"###,###,###,###,###,###,##0.00");
			return formatter.format(number);
		}		
	}
	
	/**
	 * Verifica se uma determinada lista é diferente de null e contém 1 ou mais elementos.<br>
	 * @author gabriel.ortiz
	 * @since 01/08/2006
	 * @param Collection (List, Set, Map)
	 * @return boolean
	 * @review Digam
	 */
	public static boolean validarLista(Collection<?> lista) {
		return (lista != null && lista.size() > 0);
	}


    /**
     * Remove a Formatação do Número do CPF
     * @param  String cpf com a mascara 000.000.000-00
     * @return String Numero do CPF
     */
    public static String removerFormatacaoCPF(final String cpf) {
        if(cpf == null) 
        	return null;
        
        String aux1 = cpf;
        aux1 = aux1.replace(".", "");
        aux1 = aux1.replace("-", "");
        
        return aux1;       
    }
    
	/**
	 * Formata uma data de acordo com o padrão especificado.
	 * <pre>
	 * Exemplos de Padrao:
	 * "HH:mm"                   = 14:30
	 * "HH:mm:ss"                = 14:30:35
	 * "dd/MM/yyyy"              = 10/04/2008
	 * "dd/MM/yyyy HH:mm"        = 10/04/2008 14:30
	 * "dd 'de' MMMM 'de' yyyy"  = 07 de Abril de 2008
	 * "yyyy-MM-dd HH:mm:ss.SSS" = 2008-04-07 18:16:43.991
	 * </pre>
	 * @author Digam
	 * @since 05/04/2008
	 * @param Date 
	 * @return String 
	 */
	public static String formatarData(Date date, String padrao){

		String data = "";
		if(date != null && StringUtils.isNotBlank(padrao)){
			data = new SimpleDateFormat(padrao).format(date);
		}
		return data;

	}
	
	/**
	 * Envia email
	 * @param texto
	 * @param assunto
	 * @param destinatarioPara
	 * @param destinatarioCc
	 * @param destinatarioBcc
	 * @param listaAnexos
	 * @throws ApplicationException
	 */
	public void enviarEmailQuartz(String remetente, String texto, String assunto, String destinatarioPara, final String destinatarioCc, 
			String destinatarioBcc, List<AnexoMail> listaAnexos, boolean logarErro) throws ApplicationException {
		try{
			final Properties mailProps = new Properties();
			mailProps.put("mail.smtp.host", Mensagem.getInstance().getMessage("email_server"));
			final Session mailSession = Session.getInstance(mailProps, null);
			mailSession.setDebug(false);

			final Message email = new MimeMessage(mailSession);
			email.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatarioPara));

			if (destinatarioCc != null && StringUtils.isNotEmpty(destinatarioCc.trim())) {
				email.setRecipients(Message.RecipientType.CC, InternetAddress.parse(destinatarioCc));
			}
			if (destinatarioBcc != null && StringUtils.isNotEmpty(destinatarioBcc.trim())) {
				email.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(destinatarioBcc));
			}
			if (StringUtils.isNotBlank(remetente) ){
				email.setFrom(new InternetAddress(remetente));	
			}

			email.setSubject(assunto);
			
			BodyPart messageBodyPart = null;
			
			Multipart multipart = new MimeMultipart();
			
			if(listaAnexos != null && !listaAnexos.isEmpty()){
				for(AnexoMail anexoMail : listaAnexos){
					messageBodyPart = new MimeBodyPart();
					File file= File.createTempFile("mail", "anexo");
					file.deleteOnExit();
					BufferedWriter out = new BufferedWriter(new FileWriter(file));
			        out.write(anexoMail.getConteudoArquivo());
			        out.close();
					DataSource source = new FileDataSource(file);
					messageBodyPart.setDataHandler(new DataHandler(source));
					messageBodyPart.setFileName(anexoMail.getNomeArquivo());
					multipart.addBodyPart(messageBodyPart);
				}
			}
			
			messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(texto,"text/html");
			multipart.addBodyPart(messageBodyPart);
			email.setContent(multipart);

			log.info("E-MAIL - ENVIANDO...");
			try {
				Transport.send(email);
			} catch (javax.mail.SendFailedException e) {
				log.error(e.getMessage());
				if (logarErro) {
					String erro = logarErro(e);
					log.error("Util.enviarEmail - Erro ao enviar o email - SendFailedException: Exception ! Msg " + e.getMessage() + " === " + erro);
				} else {
					throw new ApplicationException("Erro ao enviar o email.");
				}
			} catch (MessagingException e) {
				log.error(e.getMessage());
				if (logarErro) {
					String erro = logarErro(e);
					log.error("Util.enviarEmail - Erro ao enviar o email - MessagingException: Exception ! Msg " + e.getMessage() + " === " + erro);
				} else {
					throw new ApplicationException("Erro ao enviar o email.");
				}
			}
			log.info("E-MAIL - FINALIZADO");
		} catch (Exception e) {
			String erro = logarErro(e);
			log.error("Util.enviarEmail: Exception ! Msg " + e.getMessage() + " === " + erro);
			throw new ApplicationException("mensagem.erro.9001", new String[]{"Util.enviarEmail(String, String, String, String, String, String, List)"}, e, ApplicationException.ICON_ERRO);
		}

	}

	public static String logarErro (Exception e) {
		StackTraceElement[] list = e.getStackTrace();
		StringBuffer erro = new StringBuffer();
		for (StackTraceElement stackTraceElement : list) {
			erro.append(stackTraceElement.toString());
			erro.append("\n");
		}
		return erro.toString();
	}
	
	/**
	 * Envia Email com ou sem anexos
	 * @author jacquesotomaior
	 * @since 16/10/2008
	 * @param String assunto
	 * @param String remetente
	 * @param String texto
	 * @param String destinatarioPara
	 * @param String destinatarioCc
	 * @param String destinatarioBcc
	 * @param String nomeAnexo
	 * @param String textoAnexo
	 * @return void
	 * @throws ApplicationException
	 */

	public static void enviarEmail(final String assunto, final String nomeAnexo, final String remetente, String texto, final String destinatarioPara, final String destinatarioCc, String destinatarioBcc, List<File> listaAnexos, byte[] imagemAnexo) throws ApplicationException{

		try{
			final Properties mailProps = new Properties();
			mailProps.put("mail.smtp.host", Mensagem.getInstance().getMessage("email_server"));
			final Session mailSession = Session.getInstance(mailProps, null);
			mailSession.setDebug(false);

			final Message email = new MimeMessage(mailSession);
			email.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatarioPara));

			if (destinatarioCc != null && StringUtils.isNotEmpty(destinatarioCc.trim())) {
				email.setRecipients(Message.RecipientType.CC, InternetAddress.parse(destinatarioCc));
			}
			if (destinatarioBcc != null && StringUtils.isNotEmpty(destinatarioBcc.trim())) {
				email.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(destinatarioBcc));
			}
			if (StringUtils.isNotBlank(remetente) ){
				email.setFrom(new InternetAddress(remetente));	
			}

			email.setSubject(assunto);
			
			BodyPart messageBodyPart = null;
			


			Multipart multipart = new MimeMultipart();
			
			Integer i = 0;
			if(listaAnexos != null && !listaAnexos.isEmpty()){
				for(File file : listaAnexos){
					messageBodyPart = new MimeBodyPart();
					messageBodyPart.setDataHandler(new DataHandler(new FileDataSource(file)));
					if (Integer.valueOf(0).equals(i)){
						messageBodyPart.setFileName(nomeAnexo);	
					}else{
						messageBodyPart.setFileName(nomeAnexo.concat(i.toString()));
					}
					i = i ++;
					messageBodyPart.setDisposition("inline");	
					multipart.addBodyPart(messageBodyPart);
				}
			}
			messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(texto,"text/html");
			multipart.addBodyPart(messageBodyPart);
			email.setContent(multipart);

			try {
				Transport.send(email);
			} catch (javax.mail.SendFailedException e) {
				throw new ApplicationException("Erro ao enviar o email.");
			} catch (MessagingException e) {
				throw new ApplicationException("Erro ao enviar o email.");
			}
		} catch (javax.mail.SendFailedException e) {
			throw new ApplicationException("E-mail não enviado");
		} catch (Exception e) {
			throw new ApplicationException("Erro ao enviar email.", new String[]{"Util.enviarEmail"}, e);
		}

		//-----------------------------------------------------------FIM------------------------------------------------------------------

	}

	/**
	 * Validar E-mail.<br>
	 * @author vanessak
	 * @since 15/05/2010
	 * @param email
	 * @return boolean: O email é válido ou inválido
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public static boolean validarEMAIL(final String email) throws ApplicationException, Exception {
    	String expressao = regExEmail();        			
    	Pattern p = Pattern.compile(expressao);
		Matcher m = p.matcher(email); 
		if(!m.find()) 
			return false;

		return true;
	}

	private static String regExEmail() throws Exception {
	   return "^([0-9A-Za-z_-]+\\.)*[0-9A-Za-z_-]+@([0-9A-Za-z_-]+\\.)+[0-9A-Za-z_-]+$";
	}

}

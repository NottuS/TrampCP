package gov.pr.celepar.abi.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.Resources;

public class ABIValidator {

	@SuppressWarnings("deprecation")
	public static boolean validateDataServidor(Object bean, ValidatorAction va,
			Field field, ActionMessages errors, HttpServletRequest request){
		try {
		String value = ValidatorUtils.getValueAsString(bean, field
				.getProperty());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
			Date valueDate = sdf.parse(value);
			if(valueDate.after(new Date()) && !field.getProperty().equals("tsPrazoNotificacao"))
			{
				errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
				return false;
			}
				
		} catch (ParseException e) {
			return true;
		}
		
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public static boolean validateDataAntiga(Object bean, ValidatorAction va,
			Field field, ActionMessages errors, HttpServletRequest request){
		try {
		String value = ValidatorUtils.getValueAsString(bean, field
				.getProperty());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
			Date valueDate = sdf.parse(value);
			if(valueDate.before(sdf.parse("04/12/1889")))
			{
				errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
				return false;
			}
				
		} catch (ParseException e) {
			return true;
		}
		
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public static boolean validateOcupacao(Object bean, ValidatorAction va,
			Field field, ActionMessages errors, HttpServletRequest request){
	
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		String sProperty2 = field.getVarValue("situacaoOcupacao");
		String value2 = ValidatorUtils.getValueAsString(bean, sProperty2);
		if(Integer.parseInt(value2) == Dominios.SITUACAO_OCUPACAO_ADMINISTRACAO_ESTADUAL){
			/* if (field.getProperty().equals("dataOcupacao")){
				if (value.equals("")){
					errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
					return false;
				}
			}
			if (field.getProperty().equals("termoTransferencia")){
				if (value.equals("")){
					errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
					return false;
				}
			} */
		}
		else if(Integer.parseInt(value2) == Dominios.SITUACAO_OCUPACAO_ADMINISTRACAO_FEDERAL ||
				Integer.parseInt(value2) == Dominios.SITUACAO_OCUPACAO_ADMINISTRACAO_MUNICIPAL ||
				Integer.parseInt(value2) == Dominios.SITUACAO_OCUPACAO_TERCEIRO_AUTORIZADO){
			if (field.getProperty().equals("numeroLei")){
				if ("".equals( value)){
					errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
					return false;
				}
			}
			if ( "dataLei".equals( field.getProperty())){
				if ("".equals( value)){
					errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
					return false;
				}
			}
			if ("vigenciaLei".equals(field.getProperty())){
				if ("".equals( value)){
					errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
					return false;
				}
			}
			
		}else if(Integer.parseInt(value2) == Dominios.SITUACAO_OCUPACAO_TERCEIRO_NAO_AUTORIZADO){
			if (field.getProperty().equals("numeroNotificacao")){
				if ("".equals( value)){
					errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
					return false;
				}
			}
			if (field.getProperty().equals("prazoNotificacao")){
				if ("".equals( value)){
					errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
					return false;
				}
			}
			if (field.getProperty().equals("protocoloNotificacaoSpi")){
				if ("".equals( value)){
					errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
					return false;
				}
			}
		}
	
		return true;
	}
	
	
	@SuppressWarnings("deprecation")
	public static boolean validateDocumentacao(Object bean, ValidatorAction va,
			Field field, ActionMessages errors, HttpServletRequest request){
	
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		String sProperty2 = field.getVarValue("tipoDocumentacao");
		String value2 = ValidatorUtils.getValueAsString(bean, sProperty2);
		if(Integer.parseInt(value2) == Dominios.TIPO_DOCUMENTACAO_NOTIFICACAO){
			if (field.getProperty().equals("tsNotificacao")){
				if ("".equals( value)){
					errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
					return false;
				}
			}
			if (field.getProperty().equals("tsPrazoNotificacao")){
				if ("".equals( value)){
					errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
					return false;
				}
			}
			if (field.getProperty().equals("descricaoNotificacao")){
				if ("".equals( value)){
					errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
					return false;
				}
			}
		}
		
		return true;
	}
}

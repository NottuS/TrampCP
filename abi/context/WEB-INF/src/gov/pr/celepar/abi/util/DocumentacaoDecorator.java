package gov.pr.celepar.abi.util;



import gov.pr.celepar.abi.dto.DocumentacaoNotificacaoExibirBemImovelDTO;
import gov.pr.celepar.abi.dto.DocumentacaoSemNotificacaoExibirBemImovelDTO;
import gov.pr.celepar.abi.dto.OcorrenciaDocumentacaoExibirBemImovelDTO;
import gov.pr.celepar.abi.dto.TabelaDocumentacaoDTO;
import gov.pr.celepar.abi.pojo.OcorrenciaDocumentacao;
import gov.pr.celepar.framework.taglib.grid.decorator.Decorator;

public class DocumentacaoDecorator implements Decorator {
	
	public String decorate(Object object) throws Exception {
		StringBuffer campo = new StringBuffer();
		if(object == null){
			campo.append("");
		}else if(object instanceof TabelaDocumentacaoDTO){
			TabelaDocumentacaoDTO documentacao= (TabelaDocumentacaoDTO) object;
			if (documentacao.getAnexo() != null && !documentacao.getAnexo().equals("")){ 
				campo.append("<a href=\"javascript:exibirDocumentacao(\'" ).append( documentacao.getCodDocumentacao()) .append("')\"><img src=\"images/icon_exibir.png\" border=\"0\"></img></a>");
			}else{
				campo.append("<img src=\"images/icon_exibir_desabilitado.png\"></img>");
			}
				
		}else if (object instanceof DocumentacaoSemNotificacaoExibirBemImovelDTO){
			DocumentacaoSemNotificacaoExibirBemImovelDTO documentacao= (DocumentacaoSemNotificacaoExibirBemImovelDTO) object;
			if (documentacao.getAnexo() != null && !documentacao.getAnexo().equals("")){ 
				campo.append("<a href=\"javascript:exibirDocumentacao(\'").append( documentacao.getCodDocumentacao()).append( "')\"><img src=\"images/icon_exibir.png\" border=\"0\"></img></a>");
			}else{
				campo.append("<img src=\"images/icon_exibir_desabilitado.png\"></img>");
			}
		}else if(object instanceof OcorrenciaDocumentacao){
			OcorrenciaDocumentacao documentacao= (OcorrenciaDocumentacao) object;
			if (documentacao.getDocumentacao()!= null &&  documentacao.getDocumentacao().getAnexo() != null && !documentacao.getDocumentacao().getAnexo().equals("")){ 
				campo.append("<a href=\"javascript:exibirDocumentacao(\'" ).append( documentacao.getDocumentacao().getCodDocumentacao()).append( "')\"><img src=\"images/icon_exibir.png\" border=\"0\"></img></a>");
			}else{
				campo.append("<img src=\"images/icon_exibir_desabilitado.png\"></img>");
			}
		}else if(object instanceof OcorrenciaDocumentacaoExibirBemImovelDTO){
			OcorrenciaDocumentacaoExibirBemImovelDTO documentacao= (OcorrenciaDocumentacaoExibirBemImovelDTO) object;
			if ( documentacao.getAnexo() != null && !documentacao.getAnexo().equals("")){ 
				campo.append("<a href=\"javascript:exibirDocumentacao(\'").append( documentacao.getCodDocumentacao()).append( "')\"><img src=\"images/icon_exibir.png\" border=\"0\"></img></a>");
			}else{
				campo.append("<img src=\"images/icon_exibir_desabilitado.png\"></img>");
			}
		}else if(object instanceof DocumentacaoNotificacaoExibirBemImovelDTO){
			DocumentacaoNotificacaoExibirBemImovelDTO documentacao= (DocumentacaoNotificacaoExibirBemImovelDTO) object;
			if ( documentacao.getAnexo() != null && !documentacao.getAnexo().equals("")){ 
				campo.append("<a href=\"javascript:exibirDocumentacao(\'").append(documentacao.getCodDocumentacao()).append( "')\"><img src=\"images/icon_exibir.png\" border=\"0\"></img></a>");
			}else{
				campo.append("<img src=\"images/icon_exibir_desabilitado.png\"></img>");
			}
		}
		
		
		return campo.toString();
	}

}
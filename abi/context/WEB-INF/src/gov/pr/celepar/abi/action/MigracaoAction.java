package gov.pr.celepar.abi.action;

import gov.pr.celepar.abi.facade.CadastroFacade;
import gov.pr.celepar.abi.form.MigracaoForm;
import gov.pr.celepar.abi.pojo.Avaliacao;
import gov.pr.celepar.abi.pojo.BemImovel;
import gov.pr.celepar.abi.pojo.Cartorio;
import gov.pr.celepar.abi.pojo.Confrontante;
import gov.pr.celepar.abi.pojo.CoordenadaUtm;
import gov.pr.celepar.abi.pojo.Documentacao;
import gov.pr.celepar.abi.pojo.Edificacao;
import gov.pr.celepar.abi.pojo.Localidade;
import gov.pr.celepar.abi.pojo.Lote;
import gov.pr.celepar.abi.pojo.Ocupacao;
import gov.pr.celepar.abi.pojo.Orgao;
import gov.pr.celepar.abi.pojo.Quadra;
import gov.pr.celepar.abi.pojo.Tabelionato;
import gov.pr.celepar.abi.pojo.TipoConstrucao;
import gov.pr.celepar.abi.util.Dominios;
import gov.pr.celepar.abi.util.TipoCodificacaoLocalidade;
import gov.pr.celepar.abi.util.Util;
import gov.pr.celepar.bedel.cep.services.EnderecamentoInterfaceService;
import gov.pr.celepar.bedel.cep.services.EnderecamentoInterfaceServiceLocator;
import gov.pr.celepar.bedel.cep.services.EnderecamentoInterface_PortType;
import gov.pr.celepar.bedel.client.BedelConfig;
import gov.pr.celepar.framework.action.BaseDispatchAction;
import gov.pr.celepar.framework.exception.ApplicationException;
import gov.pr.celepar.framework.util.Valores;
import gov.pr.celepar.sentinela.comunicacao.SentinelaComunicacao;
import gov.pr.celepar.sentinela.comunicacao.SentinelaInterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Luciana R. Bélico
 * @version 1.0
 * @since 19/01/2010
 * 
 * Classe Action:
 * Responsável por manipular as requisições dos usuários
 */
public class MigracaoAction  extends BaseDispatchAction {
	
	/**
	 * Realiza o encaminhamento necessário para executar a listagem paginada de FormaIncorporacao. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward validarSenhaMigracao (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {		
		MigracaoForm localForm = (MigracaoForm) form;
		
		try {
			
			// Aciona a validação do Form
			if(!validaForm(mapping,localForm,request)) {
				return mapping.findForward("pgEditSenhaMigracao");
			}	
			if(!Dominios.SENHA_MIGRACAO.equals(localForm.getSenha())) {
				throw new ApplicationException("ERRO.migracao.senha", ApplicationException.ICON_AVISO);
			}
			
			request.getSession().setAttribute("senhaOK", Boolean.TRUE);
			localForm.setSenhaOK("true");
			// Salva o TOKEN para evitar duplo submit
			saveToken(request);
			
			return mapping.findForward("pgEditArquivoMigracao");
			
		} catch (ApplicationException appEx) {
			setActionForward(mapping.findForward("pgEditSenhaMigracao"));
			throw appEx;
		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditSenhaMigracao"));
			throw new ApplicationException("ERRO.200", new String[]{"validação","senha migração"}, e);
		}
	}
	
	
	/**
	 * Realiza o encaminhamento necessário para executar a listagem paginada de FormaIncorporacao. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward validarArquivo (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {		
		MigracaoForm localForm = (MigracaoForm) form;

		try {
			// Aciona a validação do Form
			if(!validaForm(mapping,localForm,request)) {
				// Salva o TOKEN para evitar duplo submit
				saveToken(request);
				return mapping.findForward("pgEditArquivoMigracao");
			}	
			
			ArrayList<BemImovel> listaBens = new ArrayList<BemImovel>();
			
			BufferedReader in = new BufferedReader (new InputStreamReader( localForm.getArquivo().getInputStream()));
			SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
			String msgErros = validaArquivo(in, listaBens, true, sentinelaInterface.getCpf());

			String path = request.getSession().getServletContext().getRealPath("");
			String image1 = path + File.separator + "images" + File.separator + "logo_parana.png";
			String image2 = path + File.separator + "images" + File.separator + "logo_sistema_relatorio.jpg";

			SentinelaInterface sentinelaComunicacao = SentinelaComunicacao.getInstance(request);

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("pathSubRelatorioQuadras", Dominios.PATH_RELATORIO +"SubRelatorioQuadrasMigracao.jasper");
			parametros.put("pathSubRelatorioConfrontantes", Dominios.PATH_RELATORIO +"SubRelatorioConfrontantesMigracao.jasper");				
			parametros.put("pathSubRelatorioCoordenadasUtm", Dominios.PATH_RELATORIO +"SubRelatorioCoordenadasUtm.jasper");
			parametros.put("pathSubRelatorioEdificacoes", Dominios.PATH_RELATORIO +"SubRelatorioEdificacaoMigracao.jasper");
			parametros.put("pathSubRelatorioOcupacao", Dominios.PATH_RELATORIO +"SubRelatorioImpressaoEdificacaoBensImoveisOcupacoes.jasper");				
			parametros.put("pathSubRelatorioDocumentacao", Dominios.PATH_RELATORIO +"SubRelatorioDocumentacaoMigracao.jasper");
			parametros.put("emitidoPor", "Impresso por ".concat(sentinelaComunicacao.getNome()));
			parametros.put("erros", msgErros);
			parametros.put("comOcupacao", new Boolean(true));
			parametros.put("nomeRelatorioJasper", "MigracaoBemImovel.jasper");
			parametros.put("tituloRelatorio", "Relatório de Verificação dos Dados para Migração");
			parametros.put("pathCabecalho", Dominios.PATH_RELATORIO + "CabecalhoPadraoRetrato.jasper");
			parametros.put("image1", image1);
			parametros.put("image2", image2);
			RelatorioIReportAction.processarRelatorio(listaBens, parametros, form, request, mapping, response);			
			
			return null;
		}  catch (Exception e) {
			setActionForward(mapping.findForward("pgEditArquivoMigracao"));
			throw new ApplicationException("ERRO.200", new String[]{"validação","arquivo"}, e);
		}
	}
	
	/**
	 * Realiza a validação do arquivo. <br>
	 * Esse método tem o objetivo de unificar a validação do arquivo realizada pelos métodos de validação e execução do arquivo. 
	 * @author vanessak
	 * @param in
	 * @param listaBens
	 * @param somenteValida: true para validar e false para executar.
	 * @return msgErros: erros relatados qdo a opção é por validar o arquivo 
	 * @throws ApplicationException
	 * @throws Exception
	 */
	private String validaArquivo(BufferedReader in, Collection<BemImovel> listaBens, boolean somenteValida, String cpf) throws ApplicationException, Exception {

		String str;
		BemImovel b = null;
		StringBuffer msgErros = new StringBuffer();
		boolean possuiErros = false;

		msgErros.append("Ocorreram erros nas seguintes linhas: ").append("\n");
		
		int countLinha=0;
		List<Integer> listaPasta = new ArrayList<Integer>();
		Date dataAtual = new Date();
		while (in.ready()) {
			try{
				str = in.readLine();
				countLinha ++;
				StringBuffer msgEspecifica=new StringBuffer();
				if (str.startsWith("0")){
					if (b != null) {
						listaBens.add(b);
					}
					b = this.processaBemImovel(str, msgEspecifica, cpf, dataAtual);
					if (!msgEspecifica.toString().isEmpty()){
						possuiErros = true;
						if (somenteValida) {
							msgErros.append("linha ").append(countLinha).append(" :").append(msgEspecifica.toString()).append("\n");
						}
					}else{
						//verifica se bem Imovel já existe no arquivo
						if (listaPasta.contains(b.getCodBemImovel())){
							possuiErros = true;
							if (somenteValida) {
								msgErros.append("linha ").append(countLinha).append(" :").append("Bem Imóvel duplicado no arquivo:".concat(b.getCodBemImovel().toString())).append("\n");
							}
						}else{
							listaPasta.add(b.getCodBemImovel());	
						}
						
					}
				}else if (str.startsWith("1")){
					Edificacao e = this.processaEdificacao(str, msgEspecifica, b, cpf, dataAtual);
					if (!msgEspecifica.toString().isEmpty()){
						possuiErros = true;
						if (somenteValida) {
							msgErros.append("linha ").append(countLinha).append(" :").append(msgEspecifica.toString()).append("\n");
						}
					}
					if (b.getEdificacaos() == null || b.getEdificacaos().isEmpty()) {
						b.setEdificacaos(new HashSet<Edificacao>());
					}
					e.setCodEdificacao(Integer.valueOf(b.getEdificacaos().size()));
					b.getEdificacaos().add(e);
				}else{
					throw new ApplicationException("ERRO.migracao.validacao", new String[]{str});
				}
			} catch(ApplicationException ae){
				throw ae;
			} catch(Exception a){
				possuiErros = true;
				if (somenteValida) {
					msgErros.append("linha ").append(countLinha).append(" :").append(a.getMessage()).append("\n");
				}
			}
		}
		listaBens.add(b);
		in.close();		
		
		if (!possuiErros) {
			msgErros = new StringBuffer();
		}
		
		return msgErros.toString();
	}


	private BemImovel processaBemImovel(String str, StringBuffer msgEspecifica, String cpf, Date dataAtual) throws ApplicationException {
		BemImovel b = null;

		try {
			String[] aux = str.split(";");
			if (aux.length < 51) {
				throw new ApplicationException("ERRO.migracao.formatacao", new String[]{str});
			} else {
				Integer nrPastaCPE = Integer.valueOf(aux[50]);
				b = new BemImovel();
				b.setCodBemImovel(nrPastaCPE);
				b.setTsInclusao(dataAtual);
				b.setTsAtualizacao(dataAtual);
				b.setCpfResponsavel(cpf);
				b.setSituacaoLocal(Dominios.situacaoLocal.SITUACAO_LOCAL_NAOLOCALIZADA.getIndex());
				b.setSomenteTerreno("S");

				try{
					b.setAdministracao(Integer.valueOf(aux[2]));
				}catch(Exception ex){
					if (msgEspecifica != null && msgEspecifica.toString().length() > 0) {
						msgEspecifica.append("\n");
					}
					msgEspecifica.append("Administração não encontrada: ").append(aux[2]).append(" ");
				}
				try{
					b.setClassificacaoBemImovel(CadastroFacade.obterClassificacaoBemImovel(Integer.valueOf(aux[3])));
				}catch(Exception ex){
					if (msgEspecifica != null && msgEspecifica.toString().length() > 0) {
						msgEspecifica.append("\n");
					}
					msgEspecifica.append("Código Classificação Bem Imóvel não encontrado: ").append(aux[3]).append(" ");
				}
				try{
					b.setSituacaoLegalCartorial(CadastroFacade.obterSituacaoLegalCartorial(Dominios.situacaoLegalMigracao.getSituacaoLegalMigracaoByIndex(Integer.valueOf(aux[4])).getCodBancoDados()));
				}catch(Exception ex){
					if (msgEspecifica != null && msgEspecifica.toString().length() > 0) {
						msgEspecifica.append("\n");
					}
					msgEspecifica.append("Código Situação Legal Cartorial não encontrado: ").append(aux[4]).append(" ");
				}
				b.setCep(aux[5]);
				Localidade localidade = obterMunicipioPorCEP(Integer.valueOf(b.getCep()), servlet.getServletContext());
				
				b.setLogradouro(aux[6]);
				b.setNumero(aux[7]);
				try{
					b.setDenominacaoImovel(CadastroFacade.obterDenominacaoImovel(Dominios.denominacaoImovelMigracao.getdenominacaoImovelMigracaoByIndex(Integer.valueOf(aux[8])).getCodBancoDados()));
				}catch(Exception ex){
					if (msgEspecifica != null && msgEspecifica.toString().length() > 0) {
						msgEspecifica.append("\n");
					}
					msgEspecifica.append("Código Denominação Imóvel não encontrado: ").append(aux[8]).append(" ");
				}
				b.setComplemento(aux[9]);
				b.setBairroDistrito(aux[10]);
				// 11 é o distrito, verificar onde deve ser salvo
				try{
					//b.setCodMunicipio(Integer.valueOf(aux[12])); TODO: Campo obtido pela busca do endereço por CEP
					if (localidade != null && localidade.getCodLocalidade() > 0) {
						b.setCodMunicipio(localidade.getCodLocalidade());
						b.setMunicipio(localidade.getNome());
						b.setUf(localidade.getSiglaUf());
					} else {
						if (msgEspecifica != null && msgEspecifica.toString().length() > 0) {
							msgEspecifica.append("\n");
						}
						msgEspecifica.append("CEP não encontrado: ").append(aux[5]).append(" ");
					}
				}catch(Exception ex){
					if (msgEspecifica != null && msgEspecifica.toString().length() > 0) {
						msgEspecifica.append("\n");
					}
					msgEspecifica.append("Erro conversão Código Município: ").append(aux[12]).append(" ");
				}
				try{
					b.setAreaTerreno(Valores.converterStringParaBigDecimal(aux[13]));
				}catch(Exception ex){
					if (msgEspecifica != null && msgEspecifica.toString().length() > 0) {
						msgEspecifica.append("\n");
					}
					msgEspecifica.append("Erro conversão Área Terreno: ").append(aux[13]).append(" ");
				}
				try{
					b.setAreaDispoNivel(Valores.converterStringParaBigDecimal(aux[14]));
				}catch(Exception ex){
					if (msgEspecifica != null && msgEspecifica.toString().length() > 0) {
						msgEspecifica.append("\n");
					}
					msgEspecifica.append("Erro conversão Área Disponível: ").append(aux[14]).append(" ");
				}
				try{
					b.setAreaConstruida(Valores.converterStringParaBigDecimal(aux[15]));
				}catch(Exception ex){
					if (msgEspecifica != null && msgEspecifica.toString().length() > 0) {
						msgEspecifica.append("\n");
					}
					msgEspecifica.append("Erro conversão Área Construída: ").append(aux[15]).append(" ");
				}
				//POSIÇAO 17: INCREMENTA O EXISTENTE
				if (b.getObservacoesMigracao() != null && b.getObservacoesMigracao().trim().length() > 0) {
					String obsMig = b.getObservacoesMigracao();
					obsMig= obsMig.concat("    " + aux[16]);
				} else {
					b.setObservacoesMigracao(aux[16]);
				}
				
				//Quadras e Lotes
				Set<Quadra> listaQuadra = null;
				
				if (!Util.strEmBranco(aux[17])){
					listaQuadra = new HashSet<Quadra>();
					Quadra q = new Quadra();
					q.setBemImovel(b);
					q.setTsInclusao(dataAtual);
					q.setCpfResponsavel(cpf);
					Set<Lote> listaLote = null;
					q.setDescricao(aux[17]);
					q.setCodQuadra(Integer.valueOf(0));
					if (!Util.strEmBranco(aux[25])){
						listaLote = new HashSet<Lote>();
						Lote l = new Lote();
						l.setTsInclusao(dataAtual);
						l.setCpfResponsavel(cpf);
						l.setDescricao(aux[25]);
						l.setCodLote(Integer.valueOf(0));
						listaLote.add(l);
					}
					q.setLotes(listaLote);
					listaQuadra.add(q);
				}
				if (!Util.strEmBranco(aux[18])){
					Quadra q = new Quadra();
					q.setBemImovel(b);
					q.setTsInclusao(dataAtual);
					q.setCpfResponsavel(cpf);
					Set<Lote> listaLote = null;
					q.setDescricao(aux[18]);
					q.setCodQuadra(Integer.valueOf(1));
					if (!Util.strEmBranco(aux[26])){
						listaLote = new HashSet<Lote>();
						Lote l = new Lote();
						l.setTsInclusao(dataAtual);
						l.setCpfResponsavel(cpf);
						l.setCodLote(Integer.valueOf(0));
						l.setDescricao(aux[26]);
						listaLote.add(l);
					}
					q.setLotes(listaLote);
					listaQuadra.add(q);
				}
				if (!Util.strEmBranco(aux[19])){
					Quadra q = new Quadra();
					q.setBemImovel(b);
					q.setTsInclusao(dataAtual);
					q.setCpfResponsavel(cpf);
					Set<Lote> listaLote = null;
					q.setDescricao(aux[19]);
					q.setCodQuadra(Integer.valueOf(2));
					if (!Util.strEmBranco(aux[27])){
						listaLote = new HashSet<Lote>();
						Lote l = new Lote();
						l.setTsInclusao(dataAtual);
						l.setCpfResponsavel(cpf);
						l.setCodLote(Integer.valueOf(0));
						l.setDescricao(aux[27]);
						listaLote.add(l);
					}
					q.setLotes(listaLote);
					listaQuadra.add(q);
				}
				if (!Util.strEmBranco(aux[20])){
					Quadra q = new Quadra();
					q.setBemImovel(b);
					q.setTsInclusao(dataAtual);
					q.setCpfResponsavel(cpf);
					Set<Lote> listaLote = null;
					q.setDescricao(aux[20]);
					q.setCodQuadra(Integer.valueOf(3));
					if (!Util.strEmBranco(aux[28])){
						listaLote = new HashSet<Lote>();
						Lote l = new Lote();
						l.setTsInclusao(dataAtual);
						l.setCpfResponsavel(cpf);
						l.setCodLote(Integer.valueOf(0));
						l.setDescricao(aux[28]);
						listaLote.add(l);
					}
					q.setLotes(listaLote);
					listaQuadra.add(q);
				}
				if (!Util.strEmBranco(aux[21])){
					Quadra q = new Quadra();
					q.setBemImovel(b);
					q.setTsInclusao(dataAtual);
					q.setCpfResponsavel(cpf);
					Set<Lote> listaLote = null;
					q.setDescricao(aux[21]);
					q.setCodQuadra(Integer.valueOf(4));
					if (!Util.strEmBranco(aux[29])){
						listaLote = new HashSet<Lote>();
						Lote l = new Lote();
						l.setTsInclusao(dataAtual);
						l.setCpfResponsavel(cpf);
						l.setCodLote(Integer.valueOf(0));
						l.setDescricao(aux[29]);
						listaLote.add(l);
					}
					q.setLotes(listaLote);
					listaQuadra.add(q);
				}
				if (!Util.strEmBranco(aux[22])){
					Quadra q = new Quadra();
					q.setBemImovel(b);
					q.setTsInclusao(dataAtual);
					q.setCpfResponsavel(cpf);
					Set<Lote> listaLote = null;
					q.setDescricao(aux[22]);
					q.setCodQuadra(Integer.valueOf(5));
					if (!Util.strEmBranco(aux[30])){
						listaLote = new HashSet<Lote>();
						Lote l = new Lote();
						l.setTsInclusao(dataAtual);
						l.setCpfResponsavel(cpf);
						l.setCodLote(Integer.valueOf(0));
						l.setDescricao(aux[30]);
						listaLote.add(l);
					}
					q.setLotes(listaLote);
					listaQuadra.add(q);
				}
				if (!Util.strEmBranco(aux[23])){
					Quadra q = new Quadra();
					q.setBemImovel(b);
					q.setTsInclusao(dataAtual);
					q.setCpfResponsavel(cpf);
					Set<Lote> listaLote = null;
					q.setDescricao(aux[23]);
					q.setCodQuadra(Integer.valueOf(6));
					if (!Util.strEmBranco(aux[31])){
						listaLote = new HashSet<Lote>();
						Lote l = new Lote();
						l.setTsInclusao(dataAtual);
						l.setCpfResponsavel(cpf);
						l.setCodLote(Integer.valueOf(0));
						l.setDescricao(aux[31]);
						listaLote.add(l);
					}
					q.setLotes(listaLote);
					listaQuadra.add(q);
				}
				if (!Util.strEmBranco(aux[24])){
					Quadra q = new Quadra();
					q.setBemImovel(b);
					q.setTsInclusao(dataAtual);
					q.setCpfResponsavel(cpf);
					Set<Lote> listaLote = null;
					q.setDescricao(aux[24]);
					q.setCodQuadra(Integer.valueOf(7));
					if (!Util.strEmBranco(aux[32])){
						listaLote = new HashSet<Lote>();
						Lote l = new Lote();
						l.setTsInclusao(dataAtual);
						l.setCpfResponsavel(cpf);
						l.setCodLote(Integer.valueOf(0));
						l.setDescricao(aux[32]);
						listaLote.add(l);
					}
					q.setLotes(listaLote);
					listaQuadra.add(q);
				}
				b.setQuadras(listaQuadra);
				
				//33 é a qtde de confrontantes, mas é fixo em 4
				//Confrontantes
				Set<Confrontante> listaConfrontante = null;
				if (!Util.strEmBranco(aux[34])){
					listaConfrontante = new HashSet<Confrontante>();
					Confrontante c = new Confrontante();
					c.setBemImovel(b);
					c.setTsInclusao(dataAtual);
					c.setCpfResponsavel(cpf);
					c.setDescricao(aux[34]);
					c.setCodConfrontante(Integer.valueOf(0));
					listaConfrontante.add(c);
				}
				if (!Util.strEmBranco(aux[35])){
					Confrontante c = new Confrontante();
					c.setBemImovel(b);
					c.setTsInclusao(dataAtual);
					c.setCpfResponsavel(cpf);
					c.setDescricao(aux[35]);
					c.setCodConfrontante(Integer.valueOf(1));
					listaConfrontante.add(c);
				}
				if (!Util.strEmBranco(aux[36])){
					Confrontante c = new Confrontante();
					c.setBemImovel(b);
					c.setTsInclusao(dataAtual);
					c.setCpfResponsavel(cpf);
					c.setDescricao(aux[36]);
					c.setCodConfrontante(Integer.valueOf(2));
					listaConfrontante.add(c);
				}
				if (!Util.strEmBranco(aux[37])){
					Confrontante c = new Confrontante();
					c.setBemImovel(b);
					c.setTsInclusao(dataAtual);
					c.setCpfResponsavel(cpf);
					c.setDescricao(aux[37]);
					c.setCodConfrontante(Integer.valueOf(3));
					listaConfrontante.add(c);
				}
				b.setConfrontantes(listaConfrontante);
				
				//Coordenadas
				Set<CoordenadaUtm> listaCoordenadas = null;
				int cont=39;
				try{
					if (!Util.strEmBranco(aux[38])){
						int qdteCoordenadas = Integer.parseInt(aux[38]);
						listaCoordenadas = new HashSet<CoordenadaUtm>();
						for(int i=1; i<=qdteCoordenadas; i++){
							CoordenadaUtm c = new CoordenadaUtm();
							c.setBemImovel(b);
							c.setTsInclusao(dataAtual);
							c.setCpfResponsavel(cpf);
							c.setCoordenadaX(Valores.converterStringParaBigDecimal(aux[cont]));
							cont++;
							c.setCoordenadaY(Valores.converterStringParaBigDecimal(aux[cont]));
							cont++;
							c.setCodCoordenadaUtm(Integer.valueOf(i));
							listaCoordenadas.add(c);
						}
					}
				}catch(Exception ex){
					if (msgEspecifica != null && msgEspecifica.toString().length() > 0) {
						msgEspecifica.append("\n");
					}
					msgEspecifica.append("Erro conversão Coordenadas: ").append(aux[38]).append(" ");
				}
				b.setCoordenadaUtms(listaCoordenadas);
		
				//41 - data avaliacao
				cont++;
				
				Set<Documentacao> listaDocumentacao = new HashSet<Documentacao>();
				//42 - nome do cartorio
				if (!Util.strEmBranco(aux[cont])){
					Documentacao d = new Documentacao();
					d.setDescricao(" - ");
					d.setResponsavelDocumentacao(" - ");
					d.setBemImovel(b);
					d.setTsInclusao(dataAtual);
					d.setCpfResponsavel(cpf);
					Cartorio c = CadastroFacade.obterCartorio(aux[cont]);
					if (c != null){
						d.setCartorio(c);
					}else{
						if (msgEspecifica != null && msgEspecifica.toString().length() > 0) {
							msgEspecifica.append("\n");
						}
						msgEspecifica.append("Cartório não encontrado: ").append(aux[cont]);
					}
					cont++;
					d.setNumeroDocumentoCartorial(aux[cont]);
					d.setCodDocumentacao(Integer.valueOf(cont));
					d.setClassificacaoImovel(Integer.valueOf(aux[3]));
					cont++;
					listaDocumentacao.add(d);
				}else{
					cont+=2;
				}
				//44 - nome do tabelionato
				if (!Util.strEmBranco(aux[cont])){
					Documentacao d =  new Documentacao();
					d.setDescricao(" - ");
					d.setResponsavelDocumentacao(" - ");
					d.setBemImovel(b);
					d.setTsInclusao(dataAtual);
					d.setCpfResponsavel(cpf);
					Tabelionato t = CadastroFacade.obterTabelionato(aux[cont]);
					if (t!=null){
						d.setTabelionato(t);
					}else{
						if (msgEspecifica != null && msgEspecifica.toString().length() > 0) {
							msgEspecifica.append("\n");
						}
						msgEspecifica.append("Tabelionato não encontrado: ").append(aux[cont]);
					}
					cont++;
					d.setNumeroDocumentoTabelional(aux[cont]);
					d.setCodDocumentacao(Integer.valueOf(cont));
					d.setClassificacaoImovel(Integer.valueOf(aux[3]));
					cont++;
					listaDocumentacao.add(d);
				}else{
					cont+=2;
				}
				//incra, nirf e niif
				if (!Util.strEmBranco(aux[cont]) || !Util.strEmBranco(aux[cont+1]) || !Util.strEmBranco(aux[cont+2])){
					Documentacao d =  new Documentacao();
					d.setDescricao(" - ");
					d.setResponsavelDocumentacao(" - ");
					d.setBemImovel(b);
					d.setTsInclusao(dataAtual);
					d.setCpfResponsavel(cpf);
					d.setNirf(aux[cont]);
					d.setIncra(aux[cont+1]);
					d.setNiif(aux[cont+2]);
					d.setCodDocumentacao(Integer.valueOf(cont));
					d.setClassificacaoImovel(Integer.valueOf(aux[3]));
					listaDocumentacao.add(d);
				}
				b.setDocumentacaos(listaDocumentacao);
				
				/** 
				 * POSICAO 49: PROPRIETÁRIO REFERE-SE AO PROPRIETARIO DO BEM
				 */
				if (!aux[49].isEmpty() && aux[49]!= null && !Util.strEmBranco(aux[49])){
					Orgao or = CadastroFacade.obterOrgao(aux[49]);
					if (or != null){
						b.setOrgao(or);
					}else{
						if (msgEspecifica != null && msgEspecifica.toString().length() > 0) {
							msgEspecifica.append("\n");
						}
						msgEspecifica.append("Órgão Proprietário não encontrado: ").append(aux[49]);
					}
				}
				
				return b;
			}
		} catch (ApplicationException ae) {
			throw ae;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.migracao.validacao", new String[]{str}, e);
		}
		
	}
	
	private Edificacao processaEdificacao(String str,  StringBuffer msgEspecifica, BemImovel b, String cpf, Date dataAtual) throws ApplicationException {
		try {
			Edificacao e = new Edificacao();
			e.setBemImovel(b);
			e.setTsInclusao(dataAtual);
			e.setCpfResponsavel(cpf);
			b.setSomenteTerreno("N");
			String[] aux = str.split(";");
			try{
				TipoConstrucao tc = CadastroFacade.obterTipoConstrucao(Dominios.tipoConstrucaoMigracao.getTipoConstrucaoMigracaoByIndex(Integer.parseInt(aux[1])).getCodBancoDados());
				if (tc!=null){
					e.setTipoConstrucao(tc);
				}else{
					if (msgEspecifica != null && msgEspecifica.toString().length() > 0) {
						msgEspecifica.append("\n");
					}
					msgEspecifica.append("Tipo Construção não encontrado : ").append(aux[1]).append(" ");
				}
			}
			catch(Exception ex){
				if (msgEspecifica != null && msgEspecifica.toString().length() > 0) {
					msgEspecifica.append("\n");
				}
				msgEspecifica.append("Código Tipo Construção não encontrado: ").append(aux[1]).append(" ");
			}
			try{
				e.setTipoEdificacao(CadastroFacade.obterTipoEdificacao(Dominios.tipoEdificacaoMigracao.getTipoEdificacaoMigracaoByIndex(Integer.parseInt(aux[2])).getCodBancoDados()));
			}
			catch(Exception ex){
				if (msgEspecifica != null && msgEspecifica.toString().length() > 0) {
					msgEspecifica.append("\n");
				}
				msgEspecifica.append("Código Tipo Edificação não encontrado: ").append(aux[2]).append(" ");
			}
			//area construida
			try{
				e.setAreaConstruida(Valores.converterStringParaBigDecimal(aux[4]));
			}
			catch(Exception ex){
				if (msgEspecifica != null && msgEspecifica.toString().length() > 0) {
					msgEspecifica.append("\n");
				}
				msgEspecifica.append("Erro Conversão Área Construída: ").append(aux[4]).append(" ");
			}
			
			//3 avaliação do imovel
			try{
				Avaliacao avaliacao = new Avaliacao();
				avaliacao.setDataAvaliacao(new Date());
				avaliacao.setEdificacao(e);
				avaliacao.setBemImovel(b);
				avaliacao.setTsInclusao(new Date());
				avaliacao.setValor(Valores.converterStringParaBigDecimal(aux[3]));
				e.getAvaliacaos().add(avaliacao);
			}
			catch(Exception ex){
				if (msgEspecifica != null && msgEspecifica.toString().length() > 0) {
					msgEspecifica.append("\n");
				}
				msgEspecifica.append("Erro Conversão Área Construída: ").append(aux[4]).append(" ");
			}
			
			
			
			
			e.setLogradouro(aux[5]);
			Set<Ocupacao> listaOcupacao = null;
			 if (!Util.strEmBranco(aux[6]) || !Util.strEmBranco(aux[7]) || (aux[8] != null && !Util.strEmBranco(aux[8]) )){
				Ocupacao o = new Ocupacao();
				o.setTsInclusao(dataAtual);
				o.setAtivo(Boolean.TRUE);
				o.setCpfResponsavel(cpf);
				o.setDescricao(aux[6]);
				o.setSituacaoOcupacao(CadastroFacade.obterSituacaoOcupacao(Dominios.situacaoOcupacaoMigracao.getSituacaoOcupacaoMigracaoByIndex(Integer.parseInt(aux[7])).getCodBancoDados()));
				if (!aux[8].isEmpty() && aux[8]!= null && !Util.strEmBranco(aux[8])){
					Orgao or = CadastroFacade.obterOrgao(aux[8]);
					if (or != null){
						o.setOrgao(or);
					}else{
						if (msgEspecifica != null && msgEspecifica.toString().length() > 0) {
							msgEspecifica.append("\n");
						}
						msgEspecifica.append("Órgão da Ocupação não encontrado: ").append(aux[8]);
					}
				}
				o.setCodOcupacao(Integer.valueOf(1));
				listaOcupacao = new HashSet<Ocupacao>();
				listaOcupacao.add(o);
			 }
			 e.setOcupacaos(listaOcupacao);

			 return e;
		} catch (ApplicationException ae) {
			throw ae;
		} catch (Exception e) {
			throw new ApplicationException("ERRO.migracao.validacao", new String[]{str}, e);
		}

	}
	
	
	/**
	 * Carrega pagina para alteração com os dados da forma de incorporacao selecionada.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws Exception
	 */
	public ActionForward carregarPgInicial(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		try{
			return mapping.findForward("pgEditSenhaMigracao");

		} catch (Exception e) {
			setActionForward(mapping.findForward("pgEditSenhaMigracao"));
			throw new ApplicationException("ERRO.200", new String[]{"senha","migração"}, e);
		}		
	}
	
	/**
	 * Realiza carga da página de listagem de forma de incorporação.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward carregarPgListFormaIncorporacao(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		return mapping.findForward("pgListFormaIncorporacao");

    }
	
	/**
	 * Realiza o encaminhamento necessário para salvar a forma de incorporacao.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws ApplicationException
	 * @throws Exception
	 */
	
	public ActionForward executarArquivo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		
		MigracaoForm localForm = (MigracaoForm) form;

		try {
			// Aciona a validação do Form
			if(!validaForm(mapping,localForm,request)) {
				// Salva o TOKEN para evitar duplo submit
				saveToken(request);
				return mapping.findForward("pgEditArquivoMigracao");
			}	

			ArrayList<BemImovel> listaBens = new ArrayList<BemImovel>();

			BufferedReader in = new BufferedReader( new InputStreamReader( localForm.getArquivo().getInputStream() ) );  

			SentinelaInterface sentinelaInterface = SentinelaComunicacao.getInstance(request);
			String msgErros = validaArquivo(in, listaBens, false, sentinelaInterface.getCpf());

			msgErros = CadastroFacade.executarMigracao(listaBens);
			msgErros = msgErros.substring(0, msgErros.lastIndexOf(","));
			msgErros = msgErros.concat(".");

			String path = request.getSession().getServletContext().getRealPath("");
			String image1 = path + File.separator + "images" + File.separator + "logo_parana.png";
			String image2 = path + File.separator + "images" + File.separator + "logo_sistema_relatorio.jpg";

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("pathSubRelatorioQuadras", Dominios.PATH_RELATORIO +"SubRelatorioQuadrasMigracao.jasper");
			parametros.put("pathSubRelatorioConfrontantes", Dominios.PATH_RELATORIO +"SubRelatorioConfrontantesMigracao.jasper");				
			parametros.put("pathSubRelatorioCoordenadasUtm", Dominios.PATH_RELATORIO +"SubRelatorioCoordenadasUtm.jasper");
			parametros.put("pathSubRelatorioEdificacoes", Dominios.PATH_RELATORIO +"SubRelatorioEdificacaoMigracao.jasper");
			parametros.put("pathSubRelatorioOcupacao", Dominios.PATH_RELATORIO +"SubRelatorioImpressaoEdificacaoBensImoveisOcupacoes.jasper");				
			parametros.put("pathSubRelatorioDocumentacao", Dominios.PATH_RELATORIO +"SubRelatorioDocumentacaoMigracao.jasper");
			parametros.put("emitidoPor", "Impresso por ".concat(sentinelaInterface.getNome()));
			parametros.put("erros", msgErros);
			parametros.put("comOcupacao", new Boolean(true));
			parametros.put("nomeRelatorioJasper", "MigracaoBemImovel.jasper");
			parametros.put("tituloRelatorio", "Relatório de Verificação dos Dados para Migração");
			parametros.put("pathCabecalho", Dominios.PATH_RELATORIO + "CabecalhoPadraoRetrato.jasper");
			parametros.put("image1", image1);
			parametros.put("image2", image2);

			RelatorioIReportAction.processarRelatorio(listaBens, parametros, form, request, mapping, response);			

			return null;

		}  catch (Exception e) {
			setActionForward(mapping.findForward("pgEditArquivoMigracao"));
			throw new ApplicationException("ERRO.200", new String[]{"validação","arquivo"}, e);
		}
	}	

	/**
	 * Obter o nome da cidade existente na base do CEP, de acordo com a UF e o código. <be>
	 * @param cep : Integer
	 * @param context : ServletContext
	 * @return Localidade
	 * @throws ApplicationException
	 */
	private static Localidade obterMunicipioPorCEP(Integer cep, ServletContext context) throws ApplicationException{
		try{
			log.info("Método obterMunicipioPorCEP processando...");	
			EnderecamentoInterface_PortType enderecamento = obterEnderecamento(context);
			String localidadeCEP[][] = enderecamento.buscarEnderecoPorCep(cep, TipoCodificacaoLocalidade.CORREIOS.getCodigo().byteValue());

			Localidade localidade = null;
			if (localidadeCEP.length > 0) {
				localidade = new Localidade();
				localidade.setCodLocalidade(Integer.valueOf(localidadeCEP[0][5]));
				localidade.setCep(localidadeCEP[0][0]);
				localidade.setSiglaUf(localidadeCEP[0][6]);
				String[] retorno = enderecamento.obterLocalidade(localidade.getCodLocalidade(), TipoCodificacaoLocalidade.CORREIOS.getCodigo().byteValue());
				localidade.setNome(retorno [4]);
			}
			log.info("Fim");
			return localidade;
		}catch (ApplicationException ae) {
			log.info(ae.getMessage(), ae);
			throw ae;
		}catch (Exception e) {
			log.info(e.getMessage(), e);
			throw new ApplicationException("mensagem.erro.9001", new String[]{MigracaoAction.class.getSimpleName()+".obterMunicipioPorCEP()"}, e, ApplicationException.ICON_ERRO);
		}
	}

	/**
	 * Metodo responsavel por instanciar Enderecamento, que contem as funcoes de cep. <br>
	 * @author ledann
	 * @since 10/02/20009
	 * @param context : ServletContext
	 * @return EnderecamentoInterface_PortType
	 * @throws ApplicationException
	 */
	private static final EnderecamentoInterface_PortType obterEnderecamento(ServletContext context) throws ApplicationException{
		try{
			/*
			 Passar na action servlet.getServletContext() como parametro.  (ledann)
			 */
			BedelConfig config = BedelConfig.getInstance(context); 
			EnderecamentoInterfaceService service = new EnderecamentoInterfaceServiceLocator(config);
			return service.getEnderecamentoInterface();
		}catch (ApplicationException ae) {
			throw ae;
		}catch (Exception e) {
			throw new ApplicationException("mensagem.erro.9001", new String[]{MigracaoAction.class.getSimpleName()+".obterEnderecamento()"}, e, ApplicationException.ICON_ERRO);			
		}
	}

}

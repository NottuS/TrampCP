package gov.pr.celepar.abi.util;

import java.io.File;

import org.jboss.varia.property.SystemPropertiesService;

/**
 * @author pialarissi
 * @version 1.0
 * @since 07/01/2010
 * 
 * Classe Exemplo:
 * Contem todos os domínios configuráveis que a aplicação necessita.
 */
public class Dominios {
	
	public final static String SENHA_MIGRACAO = "di20vi11no";
	
	/** Quantidade de objetos por página **/
	public final static int QTD_PAGINA = 20;
	
	private final static String JBOSS_DATADIR =  new StringBuffer().append(new SystemPropertiesService().get("jboss.server.home.dir")).toString();
	
	public final static String PATH_DATA = new StringBuffer().append(new SystemPropertiesService().get("jboss.server.data.dir")).append(File.separator).append("abi").toString();
	public final static String PATH_ARMAZENAMENTO_RELATORIOS = new StringBuffer(PATH_DATA).append(File.separator).append("relatorios").toString();
	public final static String PATH_SERVIDOR = "http://www.bensimoveis.pr.gov.br/abi/";
	
	/** caminho do servidor que será guardado os documentos **/
	public final static String PATH_DOCUMENTACAO =   new StringBuffer().append(PATH_DATA).append(File.separator).append("documentacao").toString();
	public final static String PATH_LOGO =   new StringBuffer().append(PATH_DATA).append(File.separator).append("logo").toString();
	public final static String PATH_IMAGEM_LOGO = new StringBuffer(JBOSS_DATADIR).append(File.separator).append("deploy").append(File.separator).append("abi.war").append(File.separator).append("images").toString();

	/** caminho do servidor que está o Manual **/
	public final static String PATH_MANUAL =   new StringBuffer().append(PATH_DATA).append(File.separator).append("manual").append(File.separator).append("ABI_Manual_Usuario.pdf").toString();

	/** caminho do servidor que está o Tutorial **/
	public final static String PATH_TUTORIAL_ADM_INST =   new StringBuffer().append(PATH_DATA).append(File.separator).append("manual").append(File.separator).append("ABI_Tutorial_AdmInstituicao.pdf").toString();
	public final static String PATH_TUTORIAL_OPE_ORG_ABI =   new StringBuffer().append(PATH_DATA).append(File.separator).append("manual").append(File.separator).append("ABI_Tutorial_OperadorOrgao.pdf").toString();
	public final static String PATH_TUTORIAL_OPE_ABI =   new StringBuffer().append(PATH_DATA).append(File.separator).append("manual").append(File.separator).append("ABI_Tutorial_Operador.pdf").toString();
	public final static String PATH_TUTORIAL_LEI_ABI =   new StringBuffer().append(PATH_DATA).append(File.separator).append("manual").append(File.separator).append("ABI_Tutorial_Leitor.pdf").toString();

	/** codigo de TipoDocumentacao referente a Notificação deve ser igual ao codigo armazenado na tb_tipo_documentacao**/
	public static final int TIPO_DOCUMENTACAO_NOTIFICACAO=1;
	
	/**Caminho padrão para relatórios**/
	public static final String PATH_RELATORIO="reports/jasper/";
	
	public static final int SITUACAO_OCUPACAO_ADMINISTRACAO_ESTADUAL=1;
	public static final int SITUACAO_OCUPACAO_ADMINISTRACAO_MUNICIPAL=2;
	public static final int SITUACAO_OCUPACAO_ADMINISTRACAO_FEDERAL=4;
	public static final int SITUACAO_OCUPACAO_TERCEIRO_AUTORIZADO=5;
	public static final int SITUACAO_OCUPACAO_TERCEIRO_NAO_AUTORIZADO=6;
	
	/** Situação de Localização de Bem Imóvel **/
	public enum situacaoLocal {
		SITUACAO_LOCAL_LOCALIZADA(1, "Localizado"), 
		SITUACAO_LOCAL_NAOLOCALIZADA(2, "Não Localizado");

		int i;
		String label;
		situacaoLocal(int i, String label){this.i = i; this.label = label;};
		public int getIndex(){return this.i;};
		public String getLabel(){return this.label;};
		public static situacaoLocal getSituacaoLocalByIndex(int index) {
			for (situacaoLocal e : situacaoLocal.values()) {
				if( e.i == index ){
					return e;
				}
			}
			return null;
		}
	}
	
	/** Classificacao do Bem Imóvel **/
	public enum classificacaoImovel {
		CLASSIFICACAO_RURAL(2, "Rural"), 
		CLASSIFICACAO_URBANO(1, "Urbano");

		Integer i;
		String label;
		classificacaoImovel(int i, String label){this.i = i; this.label = label;};
		public Integer getIndex(){return this.i;};
		public String getLabel(){return this.label;};
		public static classificacaoImovel getclassificacaoImovelByIndex(int index) {
			for (classificacaoImovel e : classificacaoImovel.values()) {
				if( e.i == index ){
					return e;
				}
			}
			return null;
		}
	}
	
	
	/** Administracao do Bem Imóvel **/
	public enum administracaoImovel {
		ADMINISTRACAO_DIRETA(1, "Direta"), 
		ADMINISTRACAO_INDIRETA(2, "Indireta"),
		ADMINISTRACAO_TERCEIROS(3,"Terceiros");

		int i;
		String label;
		administracaoImovel(int i, String label){this.i = i; this.label = label;};
		public int getIndex(){return this.i;};
		public String getLabel(){return this.label;};
		public static administracaoImovel getAdministracaoImovelByIndex(int index) {
			for (administracaoImovel e : administracaoImovel.values()) {
				if( e.i == index ){
					return e;
				}
			}
			return null;
		}
	}
	
	
	
	//valores para ambiente de desenvolvimento, quando for para Produção os valores da segunda coluna devem ser alterados
	public enum situacaoLegalMigracao {
		
		
		CESSAO_DE_DIREITOS (1, 16),
		COM_ESCRITURA_DE_COMPRA_E_VENDA(2,2),
		COM_ESCRITURA_DE_DOACAO(3,3),
		COM_ESCRITURA_DE_DACAO(4,4),
		COM_ESCRITURA_DE_ADJUDICACAO_MATRICULA(5,15),
		COM_ESCRITURA_DE_ADJUDICACAO_TRANSACAO(6,15),
		COM_MATRICULA(7,6),
		COM_TRANSCRICAO(8,7),
		SEM_AVERBACAO(9,8),
		COM_AVERBACAO_EDIFICACAO(10,9),
		COM_AVERBACAO_DEMOLICAO(11,10),
		COM_AVERBACAO_AMPLIACAO(12,11),
		COM_AVERBACAO_UNIFICACAO(13,12),
		COM_AVERBACAO_DESMEMBRAMENTO(14,13),
		SEM_DOCUMENTO(15,14),
		OUTROS(16,15);

		int i;
		Integer codBancoDados;
		situacaoLegalMigracao(int i, Integer codBanco){this.i = i; this.codBancoDados = codBanco;};
		public int getIndex(){return this.i;};
		public Integer getCodBancoDados(){return this.codBancoDados;};
		public static situacaoLegalMigracao getSituacaoLegalMigracaoByIndex(int index) {
			for (situacaoLegalMigracao e : situacaoLegalMigracao.values()) {
				if( e.i == index ){
					return e;
				}
			}
			return null;
		}
	}
	
	//valores para ambiente de desenvolvimento, quando for para Produção os valores da segunda coluna devem ser alterados
	public enum denominacaoImovelMigracao {

		AREA(1,1),
		CHACARA(2,2),
		LOTE(3,6),
		FAZENDA(4,4),
		GLEBA(5,5),
		SITIO(6,7),
		TERRENO(7,8),
		OUTROS(8,9);

		int i;
		Integer codBancoDados;
		denominacaoImovelMigracao(int i, Integer codBanco){this.i = i; this.codBancoDados = codBanco;};
		public int getIndex(){return this.i;};
		public Integer getCodBancoDados(){return this.codBancoDados;};
		public static denominacaoImovelMigracao getdenominacaoImovelMigracaoByIndex(int index) {
			for (denominacaoImovelMigracao e : denominacaoImovelMigracao.values()) {
				if( e.i == index ){
					return e;
				}
			}
			return null;
		}
	}

	//valores para ambiente de desenvolvimento, quando for para Produção os valores da segunda coluna devem ser alterados
	public enum tipoConstrucaoMigracao {

		ALVENARIA(1,1),
		MADEIRA(2,2),
		MISTA(3,3),
		OUTRAS(4,5);

		int i;
		Integer codBancoDados;
		tipoConstrucaoMigracao(int i, Integer codBanco){this.i = i; this.codBancoDados = codBanco;};
		public int getIndex(){return this.i;};
		public Integer getCodBancoDados(){return this.codBancoDados;};
		public static tipoConstrucaoMigracao getTipoConstrucaoMigracaoByIndex(int index) {
			for (tipoConstrucaoMigracao e : tipoConstrucaoMigracao.values()) {
				if( e.i == index ){
					return e;
				}
			}
			return null;
		}
	}

	//valores para ambiente de desenvolvimento, quando for para Produção os valores da segunda coluna devem ser alterados
	public enum tipoEdificacaoMigracao {

		CASA(1,1),
		GALPAO(2,3),
		GINASIO(3,4),
		PREDIO(4,7),
		UNIDADE_ESCOLAR(5,2),
		OUTROS(6,8);

		int i;
		Integer codBancoDados;
		tipoEdificacaoMigracao(int i, Integer codBanco){this.i = i; this.codBancoDados = codBanco;};
		public int getIndex(){return this.i;};
		public Integer getCodBancoDados(){return this.codBancoDados;};
		public static tipoEdificacaoMigracao getTipoEdificacaoMigracaoByIndex(int index) {
			for (tipoEdificacaoMigracao e : tipoEdificacaoMigracao.values()) {
				if( e.i == index ){
					return e;
				}
			}
			return null;
		}
	}
	
	//valores para ambiente de desenvolvimento, quando for para Produção os valores da segunda coluna devem ser alterados
	public enum situacaoOcupacaoMigracao {

		OCUPADO_ESTADO(1,1),
		OCUPADO_MUNICIPIO(2,2),
		OCUPADO_UNIAO(3,4),
		TERCEIROS_AUTORIZADOS(4,5),
		TERCEIROS_NAO_AUTORIZADOS(5,6),
		EDIFICACAO_NAO_OCUPADA(6,7),
		TERRENO_NU(7,9),
		OUTROS(8,8);

		int i;
		Integer codBancoDados;
		situacaoOcupacaoMigracao(int i, Integer codBanco){this.i = i; this.codBancoDados = codBanco;};
		public int getIndex(){return this.i;};
		public Integer getCodBancoDados(){return this.codBancoDados;};
		public static situacaoOcupacaoMigracao getSituacaoOcupacaoMigracaoByIndex(int index) {
			for (situacaoOcupacaoMigracao e : situacaoOcupacaoMigracao.values()) {
				if( e.i == index ){
					return e;
				}
			}
			return null;
		}
	}
	
	/** Faixas de Pesquisas de Municipios 
	 * (uso de numeros negativos para nao conflitar com codigos de municipios) **/
	public enum faixasPesquisasMunicipios {
		FAIXA_MENOR_C (-1, "...Até C ..."),
		FAIXA_CURITIBA(-2, "...Curitiba..."),
		FAIXA_D_F     (-3, "de D... até F..."), 
		FAIXA_G_I     (-4, "de G... até I..."), 
		FAIXA_J_M     (-5, "de J... até M..."), 
		FAIXA_N_P     (-6, "de N... até P..."), 
		FAIXA_Q_S     (-7, "de Q... até S..."), 
		FAIXA_T_Z     (-8, "de T... até Z..."); 

		Integer i;
		String label;
		faixasPesquisasMunicipios(int i, String label){this.i = i; this.label = label;};
		public Integer getIndex(){return this.i;};
		public String getLabel(){return this.label;};
		public static faixasPesquisasMunicipios getFaixasPesquisasMunicipiosByIndex(int index) {
			for (faixasPesquisasMunicipios e : faixasPesquisasMunicipios.values()) {
				if( e.i == index ){
					return e;
				}
			}
			return null;
		}
	}
	
	
	public enum statusTermo {
		RASCUNHO(0, "Rascunho"), 
		VIGENTE(1, "Vigente"),
		FINALIZADO(2, "Finalizado"),
		REVOGADO(3, "Revogado"),
		DEVOLVIDO(4, "Devolvido"),
		EM_RENOVACAO(5,"Em Renovação");

		int i;
		String label;
		statusTermo(int i, String label){this.i = i; this.label = label;};
		public int getIndex(){return this.i;};
		public String getLabel(){return this.label;};
		public static statusTermo getStatusTermoByIndex(int index) {
			for (statusTermo e : statusTermo.values()) {
				if( e.i == index ){
					return e;
				}
			}
			return null;
		}
	}

	public enum tipoOperacaoBemImovel {
		TOTAL(1, "Total"), 
		EDIFICACAO(2, "Edificação"),
		TERRENO(3, "Terreno");

		int i;
		String label;
		tipoOperacaoBemImovel(int i, String label){this.i = i; this.label = label;};
		public int getIndex(){return this.i;};
		public String getLabel(){return this.label;};
		public static tipoOperacaoBemImovel getTipoOperacaoBemImovelByIndex(int index) {
			for (tipoOperacaoBemImovel e : tipoOperacaoBemImovel.values()) {
				if( e.i == index ){
					return e;
				}
			}
			return null;
		}
	}

	public enum statusTermoRel {
		RASCUNHO(0, "Rascunho"), 
		VIGENTE(1, "Vigente"),
		FINALIZADO(2, "Finalização"),
		REVOGADO(3, "Revogação"),
		DEVOLVIDO(4, "Devolução"),
		EM_RENOVACAO(5,"Renovação");

		int i;
		String label;
		statusTermoRel(int i, String label){this.i = i; this.label = label;};
		public int getIndex(){return this.i;};
		public String getLabel(){return this.label;};
		public static statusTermoRel getStatusTermoByIndex(int index) {
			for (statusTermoRel e : statusTermoRel.values()) {
				if( e.i == index ){
					return e;
				}
			}
			return null;
		}
	}

}

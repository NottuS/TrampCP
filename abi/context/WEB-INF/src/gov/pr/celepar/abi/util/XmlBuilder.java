package gov.pr.celepar.abi.util;

/**
 * Classe responsável pela geração de arquivos XML, obedecendo os respectivos padrões. 
 * @author rodrigo.hjort
 */
public class XmlBuilder {
	
    private int level = 0;
    private StringBuffer textoCompleto;
    public static final String QUEBRALINHA = System.getProperty("line.separator");
    
    /**
     * Construtor da classe.
     */
    public XmlBuilder() {
        textoCompleto = new StringBuffer(1000);
        textoCompleto.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
    }
    
    /**
     * Construtor da classe, incluindo linha de cabeçalho.
     */
    public XmlBuilder(String cabecalho) {
        textoCompleto = new StringBuffer(1000);
        if (cabecalho != null){
            textoCompleto.append(cabecalho);
        }
    }
    
    /**
     * @return	texto referente ao XML
     */
    public StringBuffer toStringBuffer() {
        return textoCompleto;
    }

    /**
     * @return	texto referente ao XML
     */
    public String toString() {
        return textoCompleto.toString();
    }

    /**
     * Inclui um novo nó, fechando-o em seguida. Ex:<p>
     * <blockquote><pre>
     *   &lt;periodo/&gt;
	 * </pre></blockquote>
     * @param name:	nome do nó
     */
    public void addClosedNode(final String name) {
        textoCompleto.append(getTabs() + "<" + name + "/>");
        textoCompleto.append(QUEBRALINHA);
    }
    
    /**
     * Inclui um novo nó, fechando-o em seguida. Ex:<p>
     * <blockquote><pre>
     *   &lt;periodo/&gt;
	 * </pre></blockquote>
     * @param name:	nome do nó
     */
    public void addClosedNodeInicio(final String name) {
        textoCompleto.append(getTabs() + "</" + name + ">");
        textoCompleto.append(QUEBRALINHA);
    }
    
    /**
     * Inclui um novo nó com campos, fechando-o em seguida. Ex:<p>
     * <blockquote><pre>
     *   &lt;cabecalho data="03/01/2005" anoletivo="2005"/&gt;
	 * </pre></blockquote>
     * @param name:	nome do nó
     */
    public void addClosedNode(final String name, final String fields) {
        textoCompleto.append(getTabs() + "<" + name + " " + fields + "/>");
        textoCompleto.append(QUEBRALINHA);
    }
    
    /**
     * Inclui um novo nó e aumenta o nível no documento. Ex:<p>
     * <blockquote><pre>
     *   &lt;dados&gt;
	 * </pre></blockquote>
     * @param name:	nome do nó
     */
    public void addNode(final String name) {
        textoCompleto.append(getTabs() + "<" + name + ">");
        textoCompleto.append(QUEBRALINHA);
        level++;
    }

    /**
     * Inclui um novo nó com campos e aumenta o nível no documento. Ex:<p>
     * <blockquote><pre>
     *   &lt;turma codigo="3" nome="A"&gt;
	 * </pre></blockquote>
     * @param name:	nome do nó
     */
    public void addNode(final String name, final String fields) {
        textoCompleto.append(getTabs() + "<" + name + " " + normalizar(fields, false) + ">");
        textoCompleto.append(QUEBRALINHA);
        level++;
    }
    
    /**
     * Inclui um novo nó com valor, fechando-o em seguida. Ex:<p>
     * <blockquote><pre>
     *   &lt;curso&gt;ENS MEDIO - E.J.A. Pressencial&lt;/curso&gt;
	 * </pre></blockquote>
     * @param name:	nome do nó
     */
    public void addAndCloseNode(final String name, final String value) {
        textoCompleto.append(getTabs() + "<" + name + ">" + normalizar(value, true) + "</" + name + ">");
        textoCompleto.append(QUEBRALINHA);
    }
    
    /**
     * Fecha um novo nó e diminui o nível no documento. Ex:<p> 
     * <blockquote><pre>
     *   &lt;/dados&gt;
	 * </pre></blockquote>
     * @param name:	nome do nó
     */
    public void closeNode(final String name) {
        level--;
        textoCompleto.append(getTabs() + "</" + name + ">");
        textoCompleto.append(QUEBRALINHA);
    }
    
    /**
     * Inclui um texto livre. <b>Usar com cautela!</b>
     * @param text:	texto qualquer
     * @see	tratamento de símbolos especiais
     */
    public void addFreeText(final String text) {
        textoCompleto.append(text);
    }
    
    /**
     * Uso interno.
     * @return	o número de tabulações necessário de acordo com o nível no documento
     */
    private String getTabs() {
        final StringBuilder ret = new StringBuilder();
        for (int ii = 0; ii < level; ii++){
            ret.append("\t");
        }
        return ret.toString();
    }
    
    /**
     * Normaliza caracteres
     * @author kolling
     * @since 01/08/2006
     * @param String : s
     * @param StringBuffer : str
     * @param int : i
     * @param boolean : tratarAspas
     */
    
    private void normalizarCaracteres(final String texto,final StringBuffer str, final int posicao, final boolean tratarAspas) {
        final char codigoChar = texto.toCharArray()[posicao];
        if(tratarAspas && codigoChar == 34){
        	 str.append("&quot;"); 
        } else{
        	switch (codigoChar) {
	            case 60: // '<'
	                str.append("&lt;"); 
	                break;
	            case 62: // '>'
	                str.append("&gt;"); 
	                break;
	            case 38: // '&'
	                str.append("&amp;"); 
	                break;
	            case 39: // '''
	                str.append("&apos;"); 
	                break;
	            default:
	                str.append(codigoChar);
	                break;
	        }
        }
    }
    
    /**
     * Normaliza
     * @author Kolling
     * @since 01/08/2006
     * @param String : s
     * @param StringBuffer : str
     * @param int : i
     */
    
    private void normalizarEnter(final String texto,final  StringBuffer str, final int posicao) {
        final char codigoChar = texto.toCharArray()[posicao];
        switch (codigoChar) {
            case 10: // '\n'
            case 13: // '\r'
                str.append("&#"); 
                str.append(Integer.toString(codigoChar));
                str.append(';');
                break;
            default:break;
        }
    }
    
    /** Normaliza o String para apresenta-lo em HTML sem retirar o retorno de linha.
     * @author kolling
	 * @since 01/08/2006
	 * @return String
	 * @param String : s
	 * @param boolean : tratarAspas
     */
    public String normalizar(final String textoATratar,final boolean tratarAspas) {
        final StringBuffer str = new StringBuffer(50);
        final int len = (textoATratar == null) ? 0 : textoATratar.length();
        for (int i = 0; i < len; i++) {
            normalizarCaracteres(textoATratar, str, i, tratarAspas);
            normalizarEnter(textoATratar, str, i);
        }
        return str.toString();
    }
    
    /** Normaliza o String para apresenta-lo em HTML retirando também o retorno de linha.
     * @author kolling
	 * @since 01/08/2006
	 * @return String
	 * @param String : s
	 * @param boolean : tratarAspas
     */
    protected String normalizarComEnter(final String textATratar, final boolean tratarAspas) {
        final StringBuffer str = new StringBuffer(50);
        final int len = (textATratar == null) ? 0 : textATratar.length();
        for (int i = 0; i < len; i++){
            normalizarCaracteres(textATratar, str, i, tratarAspas);
        }
        return str.toString();
    }
    
    /** Limpa todo o texto adicionado. */
     public void clear() {        
        textoCompleto = new StringBuffer(1000);
    }

	public int getLevel() {
		return level;
	}

	public void setLevel(final int level) {
		this.level = level;
	}

	public StringBuffer getTextoCompleto() {
		return textoCompleto;
	}

	public void setTextoCompleto(final StringBuffer textoCompleto) {
		this.textoCompleto = textoCompleto;
	}
    
}

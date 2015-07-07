/*
 * Este programa é licenciado de acordo com a
 * LPG-AP (LICENÇA PÚBLICA GERAL PARA PROGRAMAS DE COMPUTADOR DA ADMINISTRAÇÃO PÚBLICA),
 * versão 1.1 ou qualquer versão posterior.
 * A LPG-AP deve acompanhar todas PUBLICAÇÕES, DISTRIBUIÇÕES e REPRODUÇÕES deste Programa.
 * Caso uma cópia da LPG-AP não esteja disponível junto com este Programa, você
 * pode contatar o LICENCIANTE ou então acessar diretamente:
 * http://www.celepar.pr.gov.br/licenca/LPG-AP.pdf
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa
 * é preciso estar de acordo com os termos da LPG-AP.
 */

package gov.pr.celepar.framework.pojo;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

import org.apache.commons.lang.builder.*;


@Entity
@Table(name = "tb_endereco", schema = "exemplo")
public class Endereco implements Serializable {

	private Integer idEndereco;
    private String logradouroEndereco;
    private String numeroEndereco;
    private String complementoEndereco;
    private String bairroEndereco;
    private String cidadeEndereco;
    private String ufEndereco;
    private Integer cepEndereco;
    private Set<Aluno> alunos;
    private Set<Escola> escolas;
    private Integer idLocalidade;

    /** full constructor */
    public Endereco(Integer idEndereco, String logradouroEndereco, String numeroEndereco, String complementoEndereco, String bairroEndereco, String cidadeEndereco, String ufEndereco, Integer cepEndereco, Set<Aluno> alunos, Set<Escola> escolas, Integer idLocalidade) {
        this.idEndereco = idEndereco;
        this.logradouroEndereco = logradouroEndereco;
        this.numeroEndereco = numeroEndereco;
        this.complementoEndereco = complementoEndereco;
        this.bairroEndereco = bairroEndereco;
        this.cidadeEndereco = cidadeEndereco;
        this.ufEndereco = ufEndereco;
        this.cepEndereco = cepEndereco;
        this.alunos = alunos;
        this.escolas = escolas;
        this.idLocalidade = idLocalidade;
    }

    /** default constructor */
    public Endereco() {
    }

    /** minimal constructor */
    public Endereco(Integer idEndereco, String logradouroEndereco, String numeroEndereco, String bairroEndereco, String cidadeEndereco, String ufEndereco, Integer cepEndereco, Set<Aluno> alunos, Set<Escola> escolas, Integer idLocalidade) {
        this.idEndereco = idEndereco;
        this.logradouroEndereco = logradouroEndereco;
        this.numeroEndereco = numeroEndereco;
        this.bairroEndereco = bairroEndereco;
        this.cidadeEndereco = cidadeEndereco;
        this.ufEndereco = ufEndereco;
        this.cepEndereco = cepEndereco;
        this.alunos = alunos;
        this.escolas = escolas;
        this.idLocalidade = idLocalidade;
    }

    
    @Id
    @SequenceGenerator(name="ENDERECO_ID_SEQ", sequenceName="tb_endereco_id_endereco_seq")
    @GeneratedValue(strategy=GenerationType.AUTO, generator="ENDERECO_ID_SEQ")
    @Column(name = "id_endereco", nullable = false)
    public Integer getIdEndereco() {
        return this.idEndereco;
    }

    public void setIdEndereco(Integer idEndereco) {
        this.idEndereco = idEndereco;
    }

    @Column(name = "logradouro_endereco", nullable = false, length = 128)
    public String getLogradouroEndereco() {
        return this.logradouroEndereco;
    }
    
    public void setLogradouroEndereco(String logradouroEndereco) {
        this.logradouroEndereco = logradouroEndereco;
    }
    
    @Column(name = "numero_endereco", nullable = false, length = 16)
    public String getNumeroEndereco() {
        return this.numeroEndereco;
    }

    public void setNumeroEndereco(String numeroEndereco) {
        this.numeroEndereco = numeroEndereco;
    }

    @Column(name = "complemento_endereco", length = 32)
    public String getComplementoEndereco() {
        return this.complementoEndereco;
    }

    public void setComplementoEndereco(String complementoEndereco) {
        this.complementoEndereco = complementoEndereco;
    }

    @Column(name = "bairro_endereco", nullable = false, length = 64)
    public String getBairroEndereco() {
        return this.bairroEndereco;
    }

    public void setBairroEndereco(String bairroEndereco) {
        this.bairroEndereco = bairroEndereco;
    }

    @Column(name = "cidade_endereco", nullable = false, length = 128)
    public String getCidadeEndereco() {
        return this.cidadeEndereco;
    }

    public void setCidadeEndereco(String cidadeEndereco) {
        this.cidadeEndereco = cidadeEndereco;
    }

    @Column(name = "uf_endereco", nullable = false, length = 2)
    public String getUfEndereco() {
        return this.ufEndereco;
    }

    public void setUfEndereco(String ufEndereco) {
        this.ufEndereco = ufEndereco;
    }

    @Column(name = "cep_endereco", nullable = false, precision = 8, scale = 0)
    public Integer getCepEndereco() {
        return this.cepEndereco;
    }

    public void setCepEndereco(Integer cepEndereco) {
        this.cepEndereco = cepEndereco;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "endereco")    
    public Set<Aluno> getAlunos() {
        return this.alunos;
    }

    public void setAlunos(Set<Aluno> alunos) {
        this.alunos = alunos;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "endereco")
    public Set<Escola> getEscolas() {
        return this.escolas;
    }

    public void setEscolas(Set<Escola> escolas) {
        this.escolas = escolas;
    }

    @Column(name = "id_localidade", nullable = false, precision = 8, scale = 0)
	public Integer getIdLocalidade() {
		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade) {
		this.idLocalidade = idLocalidade;
	}
    
    public boolean equals(Object other) {
        if ( !(other instanceof Endereco) ) return false;
        Endereco castOther = (Endereco) other;
        return new EqualsBuilder()
            .append(this.getIdEndereco(), castOther.getIdEndereco())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getIdEndereco())
            .toHashCode();
    }

}

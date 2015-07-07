package gov.pr.celepar.abi.pojo;

import java.util.HashSet;
import java.util.Set;


/**
 * TipoLeiBemImovel generated by hbm2java
 */
public class TipoLeiBemImovel implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7203193103679727337L;
	private Integer codTipoLeiBemImovel;
    private String descricao;
    private Set<LeiBemImovel> leiBemImovels = new HashSet<LeiBemImovel>(0);

    public TipoLeiBemImovel() {
    }

    public TipoLeiBemImovel(Integer codTipoLeiBemImovel, String descricao) {
        this.codTipoLeiBemImovel = codTipoLeiBemImovel;
        this.descricao = descricao;
    }

    public TipoLeiBemImovel(Integer codTipoLeiBemImovel, String descricao,
        Set<LeiBemImovel> leiBemImovels) {
        this.codTipoLeiBemImovel = codTipoLeiBemImovel;
        this.descricao = descricao;
        this.leiBemImovels = leiBemImovels;
    }

    public Integer getCodTipoLeiBemImovel() {
        return this.codTipoLeiBemImovel;
    }

    public void setCodTipoLeiBemImovel(Integer codTipoLeiBemImovel) {
        this.codTipoLeiBemImovel = codTipoLeiBemImovel;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<LeiBemImovel> getLeiBemImovels() {
        return this.leiBemImovels;
    }

    public void setLeiBemImovels(Set<LeiBemImovel> leiBemImovels) {
        this.leiBemImovels = leiBemImovels;
    }

   

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoLeiBemImovel other = (TipoLeiBemImovel) obj;
		if (codTipoLeiBemImovel == null) {
			if (other.codTipoLeiBemImovel != null)
				return false;
		} else if (!codTipoLeiBemImovel.equals(other.codTipoLeiBemImovel))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((codTipoLeiBemImovel == null) ? 0 : codTipoLeiBemImovel
						.hashCode());
		return result;
	}
}

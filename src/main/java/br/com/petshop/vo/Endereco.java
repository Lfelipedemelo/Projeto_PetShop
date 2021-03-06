package br.com.petshop.vo;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "endereco")
public class Endereco implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column (nullable = false)
	private String logradouro;
	@Column (nullable = false)
	private String bairro;
	@Column (nullable = false)
	private String numero;
	@Column (nullable = false)
	private String localidade;
	@Column (nullable = false)
	private String uf;
	@Column (nullable = false)
	private String complemento;
	@Column (nullable = false)
	private String cep;
	
	@OneToOne
	@JoinColumn(name = "id_dono")
	private Dono dono;



	public Endereco(String logradouro, String bairro, String numero, String localidade, String uf, String complemento,
			String cep, Dono dono) {
		super();
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.numero = numero;
		this.localidade = localidade;
		this.uf = uf;
		this.complemento = complemento;
		this.cep = cep;
		this.dono = dono;
	}

	public Endereco() {
		super();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String cidade) {
		this.localidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String estado) {
		this.uf = estado;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}


	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Endereco other = (Endereco) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Dono getDono() {
		return dono;
	}

	public void setDono(Dono dono) {
		this.dono = dono;
	}


}

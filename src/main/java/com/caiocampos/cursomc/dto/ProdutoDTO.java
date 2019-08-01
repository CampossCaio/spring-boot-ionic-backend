package com.caiocampos.cursomc.dto;

import java.io.Serializable;

import com.caiocampos.cursomc.domain.Produto;

public class ProdutoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nome;
	private double preço;
	
	public ProdutoDTO() {}
	
	public ProdutoDTO(Produto obj) {
		id = obj.getId();
		nome= obj.getNome();
		preço = obj.getPreço();
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public double getPreço() {
		return preço;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setPreço(double preço) {
		this.preço = preço;
	}
	
	


}

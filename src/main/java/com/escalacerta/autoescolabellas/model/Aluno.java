package com.escalacerta.autoescolabellas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "alunos")
public class Aluno {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long id;

	@Column(name = "ladv")
	private String ladv;

	@Column(name = "nome")
	private String nome;

	@Column(name = "email")
	private String email;

	@Column(name = "telefone")
	private String telefone;

	@Column(name = "categoria")
	private String categoria;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLadv() {
		return ladv;
	}
	public void setLadv(String ladv) {
		this.ladv = ladv;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {this.email = email;}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {this.telefone = telefone;}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {this.categoria = categoria;}
}

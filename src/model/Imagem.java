package model;

import java.io.FileInputStream;

public class Imagem {

	private Long id;
	private String nome;
	private FileInputStream imagem;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public FileInputStream getImagem() {
		return imagem;
	}
	public void setImagem(FileInputStream fis) {
		this.imagem = fis;
	}
}

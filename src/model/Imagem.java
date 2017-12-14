package model;

public class Imagem {

	private Long id;
	private String nome;
	private byte[] imagem;
	
	public byte[] getImagem() {
		return imagem;
	}
	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}
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
	
	@Override
	public String toString() {
		return "Imagem [id=" + id + ", nome=" + nome + ", imagem=" + imagem + "]";
	}
	
	
}

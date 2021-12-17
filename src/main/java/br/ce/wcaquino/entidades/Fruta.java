package br.ce.wcaquino.entidades;

public class Fruta {
	
	private String nome;
	
	private int quantidadeVenda;
	
	private Double preco;
	
	public Fruta() {
		
	}
	
	public Fruta(String nome, int quantidadeVenda, Double preco) {
		super();
		this.nome = nome;
		this.quantidadeVenda = quantidadeVenda;
		this.preco = preco;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getQuantidadeVenda() {
		return quantidadeVenda;
	}

	public void setQuantidadeVenda(int quantidadeVenda) {
		this.quantidadeVenda = quantidadeVenda;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	

}

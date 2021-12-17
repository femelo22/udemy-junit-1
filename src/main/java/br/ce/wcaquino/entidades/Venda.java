package br.ce.wcaquino.entidades;

public class Venda {
	
	private Fruta fruta;
	
	private Cliente cliente;
	
	private Double precoVenda;
	
	public Venda(Fruta fruta, Cliente cliente, Double precoVenda) {
		this.fruta = fruta;
		this.cliente = cliente;
		this.precoVenda = precoVenda;
	}

	public Fruta getFruta() {
		return fruta;
	}

	public void setFruta(Fruta fruta) {
		this.fruta = fruta;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Double getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(Double precoVenda) {
		this.precoVenda = precoVenda;
	}

}

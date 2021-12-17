package br.ce.wcaquino.entidades;

import java.util.List;

public class Venda {
	
	private List<Fruta> frutas;
	
	private Cliente cliente;
	
	private Double precoVenda;

	public Venda(List<Fruta> frutas, Cliente cliente, Double precoVenda) {
		super();
		this.frutas = frutas;
		this.cliente = cliente;
		this.precoVenda = precoVenda;
	}

	public Venda() {
		
	}
	
	public List<Fruta> getFrutas() {
		return frutas;
	}


	public void setFrutas(List<Fruta> frutas) {
		this.frutas = frutas;
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

package br.ce.wcaquino.servicos;

import java.util.List;

import br.ce.wcaquino.entidades.Cliente;
import br.ce.wcaquino.entidades.Fruta;
import br.ce.wcaquino.entidades.Venda;
import br.ce.wcaquino.exceptions.VendaFrutaException;

public class VendaService {
	
	public Venda venderFruta(List<Fruta> frutas, Cliente cliente) throws VendaFrutaException {

		this.validacaoVenda(cliente, frutas);
		
		Venda venda = new Venda();
		
		venda.setCliente(cliente);
		
		venda.setFrutas(frutas);
		
		Double precoTotal = 0d;
		
		for(int i = 0; i < frutas.size(); i++) {
			precoTotal += frutas.get(i).getPreco() * frutas.get(i).getQuantidadeVenda();
		}
		
		venda.setPrecoVenda(precoTotal);
		
		return venda;
		
	}
	
	public void validacaoVenda(Cliente cliente, List<Fruta> frutas) throws VendaFrutaException {
		
		if(cliente == null) {
			throw new VendaFrutaException("Venda deve ter cliente");
		}
		
		if(frutas == null) {
			throw new VendaFrutaException("Venda deve ter fruta(s)");
		}
		
	}

}

package br.ce.wcaquino.servicos;

import java.util.List;

import br.ce.wcaquino.entidades.Cliente;
import br.ce.wcaquino.entidades.Fruta;
import br.ce.wcaquino.entidades.Venda;
import br.ce.wcaquino.exceptions.VendaSemClienteException;

public class VendaService {
	
	public Venda venderFruta(List<Fruta> frutas, Cliente cliente) throws VendaSemClienteException {
		
		if(cliente == null) {
			throw new VendaSemClienteException("Venda deve ter cliente");
		}
		
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

}

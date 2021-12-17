package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Cliente;
import br.ce.wcaquino.entidades.Fruta;
import br.ce.wcaquino.entidades.Venda;

public class VendaService {
	
	public Venda venderFruta(Fruta fruta, Cliente cliente) {
		
		Venda venda = new Venda(fruta, cliente, fruta.getPreco());
		
		return venda;
		
	}

}

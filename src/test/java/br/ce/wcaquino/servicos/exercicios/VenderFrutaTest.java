package br.ce.wcaquino.servicos.exercicios;

import static org.hamcrest.CoreMatchers.is;

import org.hamcrest.CoreMatchers;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.ce.wcaquino.entidades.Cliente;
import br.ce.wcaquino.entidades.Fruta;
import br.ce.wcaquino.entidades.Venda;
import br.ce.wcaquino.servicos.VendaService;

public class VenderFrutaTest {
	
	VendaService service;
	
	@Before
	public void setup() {
		service = new VendaService();
	}
	
	@Test
	public void test_deveVenderFruta() {
		
		Fruta fruta = new Fruta("maça", 2, 2.0);
		
		Cliente cliente = new Cliente("nome");
		
		Venda venda = service.venderFruta(fruta, cliente);
		
		Assert.assertThat(venda.getPrecoVenda(), is(2.0));
		
	}

}

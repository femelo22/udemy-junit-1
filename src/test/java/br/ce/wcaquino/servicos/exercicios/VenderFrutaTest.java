package br.ce.wcaquino.servicos.exercicios;

import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Cliente;
import br.ce.wcaquino.entidades.Fruta;
import br.ce.wcaquino.entidades.Venda;
import br.ce.wcaquino.exceptions.VendaFrutaException;
import br.ce.wcaquino.servicos.VendaService;

public class VenderFrutaTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	VendaService service;
	List<Fruta> frutas;
	
	@Before
	public void setup() {
		service = new VendaService();
		frutas = new ArrayList<>();
	}
	
	@Test
	public void test_deveVenderFruta() throws VendaFrutaException {
		
		Fruta maca = new Fruta("maça", 2, 1.0);
		Fruta manga = new Fruta("manga", 3, 2.0);
		
		frutas.add(maca);
		frutas.add(manga);
		
		Cliente cliente = new Cliente("nome");
		
		Venda venda = service.venderFruta(frutas, cliente);
		
		Assert.assertThat(venda.getPrecoVenda(), is(8.0));
		
	}
	
	@Test
	public void test_deveTerClienteNaVenda() throws VendaFrutaException {
		Fruta mamao = new Fruta("mamao", 1, 4.0);
		frutas.add(mamao);
		
		exception.expect(VendaFrutaException.class);
		exception.expectMessage("Venda deve ter cliente");
		
		service.venderFruta(frutas, null);
	}
	
	@Test
	public void test_deveTerFrutaNaVenda() throws VendaFrutaException {
		Cliente cliente = new Cliente("Fernando");
		
		exception.expect(VendaFrutaException.class);
		exception.expectMessage("Venda deve ter fruta(s)");
		
		service.venderFruta(null, cliente);
	}
	
	

}

package br.ce.wcaquino.servicos.recursos;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrdemTest {
	
	public static int contador = 0;
	
	public void inicia() {
		contador = 1;
	}
	
	public void verifica() {
		Assert.assertEquals(1, contador);
	}
	
	/**
	 * O "teste geral" está organizando a ORDEM que os testes irao rodar
	 */
	
	@Test
	public void testGeral() {
		inicia();
		verifica();
	}
	
	

}

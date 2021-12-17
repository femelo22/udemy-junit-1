package br.ce.wcaquino.servicos.exercicios;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;
import br.ce.wcaquino.servicos.Calculadora;


public class CalculadoraTest {
	
	private Calculadora calc;
	
	@Before
	public void setup() {
		calc = new Calculadora();
	}
	
	@Test
	public void test_DeveSomarDoisValores() {
		//cenario
		int a = 5;
		int b = 3;
		
		//acao
		int resultado = calc.somar(a, b);
		
		//verificacao
		assertEquals(8, resultado);
	}
	
	
	@Test
	public void test_DeveSubtrairDoisNumeros() {
		//cenario
		int a = 5;
		int b = 5;
		
		//acao
		int resultado = calc.subtrair(a, b);
		
		//verificacao
		assertEquals(0, resultado);
	}
	
	
	@Test
	public void test_DeveDividirDoisValores() throws NaoPodeDividirPorZeroException {
		//cenario
		int a = 6;
		int b = 3;
	
		//acao
		int resultado = calc.dividir(a,b);
		
		//verificao
		assertEquals(2, resultado);
	}

	
	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void test_DeveLancarExcecaoDividirPorZero() throws NaoPodeDividirPorZeroException {
		int a = 10;
		int b = 0;
		
		calc.dividir(a, b);
	}
	
	
	

}

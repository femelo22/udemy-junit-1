package br.ce.wcaquino.servicos.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import br.ce.wcaquino.servicos.Calculadora;

public class CalculadoraMockTest {
	
	@Mock
	private Calculadora calcMock;
	
	@Spy
	private Calculadora calcSpy;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	
	@Test
	public void deveMostrarDiferencaEntreSpyEMock() {
		//gravando a expectativa (MOCK)
		Mockito.when(calcMock.somar(1, 2)).thenReturn(8);
		
		//gravando a expectativa (SPY)
		Mockito.when(calcSpy.somar(1, 2)).thenReturn(8);
	
		/**
		 *  --> thenCallRealMethod(); 
		 * Podemos utilziar esse método para retornar o valor real apartir de um Mock
		 * Sem a necessidade de utilizar o Spy
		 */
		
		
		System.out.println("Mock: " + calcMock.somar(1, 5));
		
		System.out.println("Spy: " + calcSpy.somar(1, 5));
	}
	
	@Test
	public void test() {
		Calculadora calc = Mockito.mock(Calculadora.class);
		
		ArgumentCaptor<Integer> argCapture = ArgumentCaptor.forClass(Integer.class);
		Mockito.when(calc.somar(argCapture.capture(), argCapture.capture())).thenReturn(5);
		
		Assert.assertEquals(5, calc.somar(1, 100000));
		
		//System.out.println(argCapture.getAllValues());
		
	}
	

}

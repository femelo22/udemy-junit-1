package br.ce.wcaquino.servicos.service;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import br.ce.wcaquino.servicos.Calculadora;

public class CalculadoraMockTest {
	
	
	@Test
	public void test() {
		Calculadora calc = Mockito.mock(Calculadora.class);
		
		ArgumentCaptor<Integer> argCapture = ArgumentCaptor.forClass(Integer.class);
		Mockito.when(calc.somar(argCapture.capture(), argCapture.capture())).thenReturn(5);
		
		Assert.assertEquals(5, calc.somar(1, 100000));
		
		System.out.println(argCapture.getAllValues());
		
	}
	

}

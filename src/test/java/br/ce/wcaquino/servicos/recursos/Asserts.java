package br.ce.wcaquino.servicos.recursos;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Usuario;

public class Asserts {
	
	@Test
	public void testeTrue() {
		Assert.assertTrue(true);
		
		Assert.assertFalse(false);
		
		Assert.assertEquals(1, 1);
		
		Assert.assertEquals(5.01, 5.01, 0.01);
		
		int i = 5;
		Integer i2 = 5;
		
		Assert.assertEquals(Integer.valueOf(i), i2);
		Assert.assertEquals(i, i2.intValue());
		
		Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
		
		Usuario u1 = new Usuario();
		Usuario u2 = new Usuario();
		Usuario u3 = null;
		
		Assert.assertEquals(u1, u2);
		
		Assert.assertSame(u2, u2);
		
		Assert.assertNull(u3);
	}

}

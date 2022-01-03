package br.ce.wcaquino.servicos.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.ce.wcaquino.servicos.exercicios.CalculadoraTest;
import br.ce.wcaquino.servicos.service.CalculoValorLocacaoTest;
import br.ce.wcaquino.servicos.service.LocacaoServiceTest;

@RunWith(Suite.class)
@SuiteClasses({
	CalculadoraTest.class,
	CalculoValorLocacaoTest.class,
	LocacaoServiceTest.class
})
public class SuiteRunner {

}

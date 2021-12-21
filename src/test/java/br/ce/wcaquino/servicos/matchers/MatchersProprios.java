package br.ce.wcaquino.servicos.matchers;

/**
 * 
 * @author luiz
 * 
 *  Essa classe foi para deixarmos o código mais legível
 *  
 *  Criando o método estático que faz a instanciação do nosso método principal
 *
 */

public class MatchersProprios {
	
	public static DiaSemanaMatcher caiEm(Integer diaSemana) {
		return new DiaSemanaMatcher(diaSemana);
	}
	
	public static DataDiferencaDiasMatcher ehHojeComDiferencaDeDias(Integer qtdDias) {
		return new DataDiferencaDiasMatcher(qtdDias);
	}

}

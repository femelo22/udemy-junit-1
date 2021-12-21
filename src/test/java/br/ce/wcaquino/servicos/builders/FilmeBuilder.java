package br.ce.wcaquino.servicos.builders;

import br.ce.wcaquino.entidades.Filme;

public class FilmeBuilder {
	
	private Filme filme;
	
	private FilmeBuilder() {}
	
	public static FilmeBuilder umFilme() {
		FilmeBuilder builder = new FilmeBuilder();
		builder.filme = new Filme();
		builder.filme.setNome("Filme 1");
		builder.filme.setEstoque(1);
		builder.filme.setPrecoLocacao(5.0);
		return builder;
	}
	
	public Filme agora() {
		return filme;
	}

}

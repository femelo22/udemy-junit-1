package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {
		
		this.validaUsuarioeFilme(usuario, filmes);
		
		Locacao locacao = new Locacao();
		
		for (Filme filme: filmes) {
			if(filme.getEstoque() == 0) {
				throw new FilmeSemEstoqueException("Filme sem estoque");
			}
		}
			
		locacao.setFilme(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		
		Double valorTotal = 0d;
		
		for(Filme filme: filmes) {
			valorTotal += filme.getPrecoLocacao();
		}				
		
		locacao.setValor(valorTotal);
		
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
	
		return locacao;
		
	}
	
	public void validaUsuarioeFilme(Usuario usuario, List<Filme> filmes) throws LocadoraException {
		
		if(usuario == null) {
			throw new LocadoraException("Usuario vazio");
		}
		
		if(filmes == null) {
			throw new LocadoraException("Filme vazio");
		}
	}

	
	
}
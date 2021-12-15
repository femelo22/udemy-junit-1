package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

public class LocacaoServiceTest {
	
	private LocacaoService service;
	
	private List<Filme> filmes;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup() {
		service = new LocacaoService();
		filmes = new ArrayList<>();
	}

	@Test
	public void testeLocacao() throws Exception {
		// cenario
		Usuario usuario1 = new Usuario("Usuário 1");
		Filme filme1 = new Filme("Matrix", 1, 5.00);
		
		filmes.add(filme1);

		// acao
		Locacao locacao = service.alugarFilme(usuario1, filmes);

		// verificação
		error.checkThat(locacao.getValor(), is(5.0));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
	}
	
	
	
	/**
	 * @throws Exception
	 * FORMAS DE TRATAR QUANDO UM MÉTODO ESTOURA UMA EXCECAO
	 * 1° - FORMA ELEGANTE (NOTAÇÃO, QUANDO APENAS A EXCECAO IMPORTA)
	 * 2° - FORMA ROBUSTA (PERMITE TER UM CONTROLE MAIOR DO TESTE, QUANDDO PECISA DA MENSAGEM DA EXCECAO)
	 * 3° - FORMA NOVA (UTILIZANDO UM ROLE)
	 */
	
	
	//FORMA ELEGANTE
	@Test(expected = FilmeSemEstoqueException.class)
	public void testeLocacao_filmeSemEstoque() throws Exception {
		
		Usuario usuario1 = new Usuario("Usuário 2");
		Filme filme1 = new Filme("Mad Max", 0, 5.00);
		
		filmes.add(filme1);
		
		service.alugarFilme(usuario1, filmes);
	}
	
	
	//FORMA ROBUSTA
	@Test
	public void testeLocacao_UsuarioVazio() throws FilmeSemEstoqueException {
		
		Filme filme1 = new Filme("Mad Max", 1, 5.00);
		
		filmes.add(filme1);
		
		try {
			service.alugarFilme(null, filmes);
			Assert.fail();
			
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario vazio"));
		}

	}
	
	
	//FORMA NOVA
	@Test
	public void testeLocacao_FilmeVazio() throws FilmeSemEstoqueException, LocadoraException {
		Usuario usuario1 = new Usuario("Usuário 2");
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		
		service.alugarFilme(usuario1, null);
	}
	
	
	@Test
	public void testeLocacao_filmeSemEstoque_2() {
		
		Usuario usuario1 = new Usuario("Usuário 2");
		Filme filme1 = new Filme("Mad Max", 0, 5.00);
		filmes.add(filme1);

		try {
			service.alugarFilme(usuario1, filmes);
			Assert.fail("Devia lancar excecao");
		} catch (Exception e) {
			assertThat(e.getMessage(), is("Filme sem estoque"));
		}
	}
	

}

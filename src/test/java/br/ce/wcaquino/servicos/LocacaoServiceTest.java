package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.servicos.matchers.MatchersProprios.caiEm;
import static br.ce.wcaquino.servicos.matchers.MatchersProprios.ehHojeComDiferencaDeDias;
import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static java.util.Calendar.MONDAY;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.servicos.matchers.DiaSemanaMatcher;
import br.ce.wcaquino.servicos.matchers.MatchersProprios;
import br.ce.wcaquino.utils.DataUtils;

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
	public void deveAlugarFilme() throws Exception {
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		// cenario
		Usuario usuario1 = new Usuario("Usuário 1");
		Filme filme1 = new Filme("Matrix", 1, 5.00);
		
		filmes.add(filme1);

		// acao
		Locacao locacao = service.alugarFilme(usuario1, filmes);

		// verificação
		error.checkThat(locacao.getValor(), is(5.0));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(false));
		error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDeDias(2));
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
	public void naoDeveAlugarFilmeSemEstoque() throws Exception {
		
		Usuario usuario1 = new Usuario("Usuário 2");
		Filme filme1 = new Filme("Mad Max", 0, 5.00);
		
		filmes.add(filme1);
		
		service.alugarFilme(usuario1, filmes);
	}
	
	
	//FORMA ROBUSTA
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		
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
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
		Usuario usuario1 = new Usuario("Usuário 2");
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		
		service.alugarFilme(usuario1, null);
	}
	
	
/*	@Test
	public void naoDeveAlugarFilmeSemEstoque2() {
		
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
*/
	
	@Test
	public void deveDarDescontoDe25PorCentoNoTerceiroFilme() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		Usuario usuario = new Usuario("Usuario");
		Filme f1 = new Filme("Boneco do Mal", 10, 4.0);
		Filme f2 = new Filme("Chuck", 10, 4.0);
		Filme f3 = new Filme("Hora do panico", 10, 4.0);
		
		filmes.add(f1);
		filmes.add(f2);
		filmes.add(f3);
		
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		assertThat(resultado.getValor(), is(11.0));
	}
	
	@Test
	public void deveDarDescontoDe50PorCentoNoQuartoFilme() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		Usuario usuario = new Usuario("Usuario");
		Filme f1 = new Filme("Boneco do Mal", 10, 4.0);
		Filme f2 = new Filme("Chuck", 10, 4.0);
		Filme f3 = new Filme("Hora do panico", 10, 4.0);
		Filme f4 = new Filme("Json vs Fred", 10, 4.0);
		
		filmes.add(f1);
		filmes.add(f2);
		filmes.add(f3);
		filmes.add(f4);
		
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		assertThat(resultado.getValor(), is(13.0));
	}
	
	
	@Test
	public void deveDarDescontoDe75PorCentoNoQuintoFilme() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		Usuario usuario = new Usuario("Usuario");
		Filme f1 = new Filme("Boneco do Mal", 10, 4.0);
		Filme f2 = new Filme("Chuck", 10, 4.0);
		Filme f3 = new Filme("Hora do panico", 10, 4.0);
		Filme f4 = new Filme("Json vs Fred", 10, 4.0);
		Filme f5 = new Filme("A entidade 3", 10, 4.0);
		
		filmes.add(f1);
		filmes.add(f2);
		filmes.add(f3);
		filmes.add(f4);
		filmes.add(f5);
		
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		assertThat(resultado.getValor(), is(14.0));
	}
	
	@Test
	public void deveDarDescontoDe0PorCentoNoSextoFilme() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		Usuario usuario = new Usuario("Usuario");
		Filme f1 = new Filme("Boneco do Mal", 10, 4.0);
		Filme f2 = new Filme("Chuck", 10, 4.0);
		Filme f3 = new Filme("Hora do panico", 10, 4.0);
		Filme f4 = new Filme("Json vs Fred", 10, 4.0);
		Filme f5 = new Filme("A entidade 3", 10, 4.0);
		Filme f6 = new Filme("A morte do demonio", 10, 4.0);
		
		filmes.add(f1);
		filmes.add(f2);
		filmes.add(f3);
		filmes.add(f4);
		filmes.add(f5);
		filmes.add(f6);
		
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		assertThat(resultado.getValor(), is(14.0));
	}
	
	
	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		Usuario usuario = new Usuario("usuario 1");
		Filme f1 = new Filme("filme 1", 1, 4.0);
		
		filmes.add(f1);
		
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
		assertThat(retorno.getDataRetorno(), caiEm(Calendar.MONDAY));
	}

}

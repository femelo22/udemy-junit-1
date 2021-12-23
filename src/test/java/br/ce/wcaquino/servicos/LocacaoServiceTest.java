package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.servicos.builders.FilmeBuilder.umFilme;
import static br.ce.wcaquino.servicos.builders.LocacaoBuilder.umLocacao;
import static br.ce.wcaquino.servicos.builders.UsuarioBuilder.umUsuario;
import static br.ce.wcaquino.servicos.matchers.MatchersProprios.caiEm;
import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import br.ce.wcaquino.daos.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {
	
	private LocacaoService service;
	
	private SPCService spc;
	
	private LocacaoDAO dao;
	
	private EmailService emailService;
	
	private List<Filme> filmes;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup() {
		service = new LocacaoService();
		filmes = new ArrayList<>();
		dao = Mockito.mock(LocacaoDAO.class);
		service.setLocacaoDAO(dao);
		spc = Mockito.mock(SPCService.class);
		service.setSPCService(spc);
		emailService = Mockito.mock(EmailService.class);
		service.setEmailService(emailService);
	}

	@Test
	public void deveAlugarFilme() throws Exception {
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		// cenario
		Usuario usuario1 = umUsuario().agora();
		
		Filme filme1 = umFilme().agora();
		
		filmes.add(filme1);

		// acao
		Locacao locacao = service.alugarFilme(usuario1, filmes);

		// verificação
		error.checkThat(locacao.getValor(), is(4.0));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		//error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
		//error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDeDias(2));
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
		
		Usuario usuario1 = umUsuario().agora();
		Filme filme1 = umFilme().semEstoque().agora();
		
		filmes.add(filme1);
		
		service.alugarFilme(usuario1, filmes);
	}
	
	
	//FORMA ROBUSTA
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		
		Filme filme1 = umFilme().agora();
		
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
		Usuario usuario1 = umUsuario().agora();
		
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
		Usuario usuario = umUsuario().agora();
		Filme f1 = umFilme().agora();
		Filme f2 = umFilme().agora();
		Filme f3 = umFilme().agora();
		
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
		Usuario usuario = umUsuario().agora();
		Filme f1 = umFilme().agora();
		Filme f2 = umFilme().agora();
		Filme f3 = umFilme().agora();
		Filme f4 = umFilme().agora();
		
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
		Usuario usuario = umUsuario().agora();
		Filme f1 = umFilme().agora();
		Filme f2 = umFilme().agora();
		Filme f3 = umFilme().agora();
		Filme f4 = umFilme().agora();
		Filme f5 = umFilme().agora();
		
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
		Usuario usuario = umUsuario().agora();
		Filme f1 = umFilme().agora();
		Filme f2 = umFilme().agora();
		Filme f3 = umFilme().agora();
		Filme f4 = umFilme().agora();
		Filme f5 = umFilme().agora();
		Filme f6 = umFilme().agora();
		
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
		
		Usuario usuario = umUsuario().agora();
		Filme f1 = umFilme().agora();
		
		filmes.add(f1);
		
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
		assertThat(retorno.getDataRetorno(), caiEm(Calendar.MONDAY));
	}
	
	@Test
	public void naoDeveAlugarFilmeParaUsuarioNegativadoSPC() throws FilmeSemEstoqueException {
		Usuario usuario = umUsuario().agora();
		Usuario usuario2 = umUsuario().comNome("Luiz").agora();
		
		Filme filme = umFilme().agora();
		filmes.add(filme);
		
		//Alterar o retorno padrao do Mockito
		when(spc.ehNegativado(usuario)).thenReturn(true);
		
		exception.expectMessage("Usuario negativado");
		
		try {
			service.alugarFilme(usuario, filmes);
			Assert.fail();
		}catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario negativado"));
		}
		
		verify(spc).ehNegativado(usuario);
	}
	
	@Test
	public void deveEnviarEmailParaLocacoesAtrasadas() {
		//cenario
		Usuario usuario = umUsuario().agora();
		Usuario usuario2 = umUsuario().comNome("Fernando").agora();
		
		List<Locacao> locacoesPendentes = Arrays.asList(umLocacao().comUsuario(usuario).comDataRetorno(DataUtils.obterDataComDiferencaDias(-3)).agora());
		
		Mockito.when(dao.obterLocacoesPendentes()).thenReturn(locacoesPendentes);
		
		//acao
		service.notificarAtrasosLocacao();
		
		//verificacao
		verify(emailService).notificarAtraso(usuario);
	}

}

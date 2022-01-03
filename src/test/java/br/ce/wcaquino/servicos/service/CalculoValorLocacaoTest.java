package br.ce.wcaquino.servicos.service;

import static br.ce.wcaquino.servicos.builders.UsuarioBuilder.umUsuario;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.daos.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.servicos.LocacaoService;
import br.ce.wcaquino.servicos.SPCService;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {
	
	@InjectMocks
	private LocacaoService service;
	
	@Mock
	private SPCService spc;
	
	@Mock
	private LocacaoDAO dao;
	
	/**
	 * @Parameter = nos permite definir a posição dos parametros que estamos passando para o método 
	 * que possui o @Parameters
	 */
	
	@Parameter 
	public List<Filme> filmes;
	
	@Parameter(value = 1)
	public Double valorLocacao;
	
	@Parameter(value = 2)
	public String cenario;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service = new LocacaoService();
	}
	
	private static Filme f1 = new Filme("Boneco do Mal", 10, 4.0);
	private static Filme f2 = new Filme("Chuck", 10, 4.0);
	private static Filme f3 = new Filme("Hora do panico", 10, 4.0);
	private static Filme f4 = new Filme("Boneco do Mal", 10, 4.0);
	private static Filme f5 = new Filme("Chuck", 10, 4.0);
	private static Filme f6 = new Filme("Hora do panico", 10, 4.0);
	
	
	/**
	 * @Parameters = ira nos fornecer um array de strings de parâmetros, para executar nos testes
	 * 
	 * (NAME) = nos permite imprimir de forma mais legivel os resultados do teste
	 */
	
	@Parameters(name = "TESTE {index} --> {0} / {1} / {2}") 
	public static Collection<Object[]> getParametros(){
		return Arrays.asList(new Object[][] {
			{Arrays.asList(f1,f2,f3), 11.0, " 25%"},
			{Arrays.asList(f1,f2,f3,f4), 13.0, " 50%"},
			{Arrays.asList(f1,f2,f3,f4,f5), 14.0, " 75%"},
			{Arrays.asList(f1,f2,f3,f4,f5,f6), 14.0, " 100%"}
		});
	}
	
	
	@Test
	public void deveCalcularValorLocacaoConsiderendoDescontos() throws FilmeSemEstoqueException, LocadoraException {
		//cenario
		Usuario usuario = umUsuario().agora();
		
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		assertThat(resultado.getValor(), is(valorLocacao));
	}
	
	@Test
	public void print() {
		System.out.println(valorLocacao);
	}

}

package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.is;

import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;

public class LocacaoServiceTest {
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Test
	public void testeLocacao() {
		
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario1 = new Usuario("Usuário 1");
		Filme filme1 = new Filme("Matrix", 5, 5.00);
				
		//acao
		Locacao locacao = service.alugarFilme(usuario1, filme1);
		
		//resultado
		//assertThat(locacao.getValor(), is(5.0));
		//assertThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		//assertTrue(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)));
		
		error.checkThat(locacao.getValor(), is(6.0));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(false));
		
	}

}

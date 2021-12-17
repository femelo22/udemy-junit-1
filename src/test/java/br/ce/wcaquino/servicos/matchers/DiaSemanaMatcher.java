package br.ce.wcaquino.servicos.matchers;

import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.LONG;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.ce.wcaquino.utils.DataUtils;



//<X> Define o PRIMEIRO parametros que será enviado no assertThat na classe de teste

public class DiaSemanaMatcher extends TypeSafeMatcher<Date> { 
	
	
	//Define o Segundo parametros que será enviado no asserThat na classe de teste
	private Integer diaSemana;
	
	
	public DiaSemanaMatcher(Integer diaSemana) {
		this.diaSemana = diaSemana;
	}

	
	/**
	 * DescribeTo
	 * Para imprimir na descricao do Matcher
	 */
	
	@Override
	public void describeTo(Description description) {
		Calendar data = Calendar.getInstance();
		data.set(DAY_OF_WEEK, diaSemana);
		String dataExtenso = data.getDisplayName(DAY_OF_WEEK, LONG, new Locale("pt", "BR"));
		description.appendText(dataExtenso);
	}

	
	
	@Override
	protected boolean matchesSafely(Date date) {
		return DataUtils.verificarDiaSemana(date, diaSemana);
	}

}

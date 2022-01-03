package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.ce.wcaquino.daos.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoService {
	
	private LocacaoDAO dao;
	private SPCService spcService;
	private EmailService emailService;

	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {

		this.validaUsuarioEFilme(usuario, filmes);

		Locacao locacao = new Locacao();

		for (Filme filme : filmes) {
			if (filme.getEstoque() == 0) {
				throw new FilmeSemEstoqueException("Filme sem estoque");
			}
		}

		locacao.setFilme(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());

		Double valorTotal = 0d;

		for (int i = 0; i < filmes.size(); i++) {
			Filme filme = filmes.get(i);
			Double valorFilme = filme.getPrecoLocacao();

			switch (i) {
			case 2:
				valorFilme = valorFilme * 0.75;
				break;
			case 3:
				valorFilme = valorFilme * 0.50;
				break;
			case 4:
				valorFilme = valorFilme * 0.25;
				break;
			case 5:
				valorFilme = 0d;
				break;
			default:
				break;
			}

			valorTotal += valorFilme;
		}

		locacao.setValor(valorTotal);

		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		
		if(DataUtils.verificarDiaSemana(dataEntrega, Calendar.SATURDAY)) {
			dataEntrega = adicionarDias(dataEntrega, 1);			
		}

		locacao.setDataRetorno(dataEntrega);
		
		
		// Salvando a locacao...
		dao.salvar(locacao);

		return locacao;
	}
	
	public void notificarAtrasosLocacao() {
		List<Locacao> locacoes = dao.obterLocacoesPendentes();
		
		for(Locacao locacao: locacoes) {
			if(locacao.getDataRetorno().before(new Date())) {
				emailService.notificarAtraso(locacao.getUsuario());
			}
		}
	}
	

	public void validaUsuarioEFilme(Usuario usuario, List<Filme> filmes) throws LocadoraException {

		if (usuario == null) {
			throw new LocadoraException("Usuario vazio");
		}

		if (filmes == null) {
			throw new LocadoraException("Filme vazio");
		}
		
		boolean negativado;
		
		try {
			negativado = spcService.ehNegativado(usuario);
		} catch (Exception e) {
			throw new LocadoraException("Problemas com serviço de SPC, tente novamente");
		}
		
		if(negativado) {
			throw new LocadoraException("Usuario negativado");
		}
	}

}
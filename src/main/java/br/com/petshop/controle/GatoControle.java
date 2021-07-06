package br.com.petshop.controle;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.petshop.dao.ComportamentoDao;
import br.com.petshop.dao.ComportamentoDaoImpl;
import br.com.petshop.dao.DonoDao;
import br.com.petshop.dao.DonoDaoImpl;
import br.com.petshop.dao.GatoDao;
import br.com.petshop.dao.GatoDaoImpl;
import br.com.petshop.dao.HibernateUtil;
import br.com.petshop.vo.Comportamento;
import br.com.petshop.vo.Dono;
import br.com.petshop.vo.Gato;

@ManagedBean(name = "gatoC")
@ViewScoped
public class GatoControle {

	private Gato gato;
	private GatoDao gatoDao;
	private Session sessao;
	private List<Gato> gatos;
	private Comportamento comportamento;
	private DataModel<Gato> modelGatos;
	private Dono dono;
	private int aba;

	public GatoControle() {
		gatoDao = new GatoDaoImpl();
	}

	public String pesquisarPorNome() {
		sessao = HibernateUtil.abrirSessao();
		try {
			gatos = gatoDao.pesquisarPorNome(gato.getNome(), sessao);
			modelGatos = new ListDataModel<Gato>(gatos);
			gato.setNome(null);
		} catch (Exception e) {
			System.out.println("Erro ao pesquisar " + e.getMessage());
		} finally {
			sessao.close();
		}
		return "";
	}

	public void pesquisarDono() {
		sessao = HibernateUtil.abrirSessao();
		DonoDao donoDao = new DonoDaoImpl();
		try {
			dono = donoDao.pesquisarPorCpfDono(dono.getCpf(), sessao);
			aba = 1;
		} catch (Exception e) {
			System.out.println("Nenhum dono encontrado " + e.getMessage());
		} finally {
			sessao.close();
		}
	}

	public void salvar() {
		sessao = HibernateUtil.abrirSessao();
		try {
			gato.setDono(dono);
			salvarComportamento();
			gato.setComportamento(comportamento);
			gatoDao.salvarOuAlterar(gato, sessao);
			gato = null;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso", null));
			modelGatos = null;
		} catch (HibernateException e) {
			System.out.println("Erro ao salvar: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO", "Erro ao salvar"));
		} finally {
			sessao.close();
		}
	}
	
	public void salvarComportamento() {
		try {
			ComportamentoDao cDao = new ComportamentoDaoImpl();
			cDao.salvarOuAlterar(comportamento, sessao);
		} catch (HibernateException e) {
			System.out.println("Erro ao salvar comportamento" + e.getMessage());
		}
	}

	public void prepararAlterar() {
		gato = modelGatos.getRowData();
		comportamento = gato.getComportamento();
		dono = gato.getDono();
		aba = 1;
	}

	public void limpar() {
		gato = new Gato();
		dono = new Dono();
		comportamento = new Comportamento();
		aba = 1;
	}

	public void excluir() {
		sessao = HibernateUtil.abrirSessao();
		gato = modelGatos.getRowData();
		try {
			gatoDao.excluir(gato, sessao);
			gato = null;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluido com sucesso", null));
			modelGatos = null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO", "Erro ao excluir"));
		} finally {
			sessao.close();
		}

	}

	public Gato getGato() {
		if (gato == null) {
			gato = new Gato();
		}
		return gato;
	}

	public int getAba() {
		return aba;
	}

	public List<Gato> getGatos() {
		return gatos;
	}

	public DataModel<Gato> getModelGatos() {
		return modelGatos;
	}

	public Dono getDono() {
		if (dono == null) {
			dono = new Dono();
		}
		return dono;
	}

	public void setDono(Dono dono) {
		this.dono = dono;
	}

	public Comportamento getComportamento() {
		if (comportamento == null) {
			comportamento = new Comportamento();
		}
		return comportamento;
	}

	public void setComportamento(Comportamento comportamento) {
		this.comportamento = comportamento;
	}

}

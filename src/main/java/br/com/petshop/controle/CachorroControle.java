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
import br.com.petshop.dao.CachorroDao;
import br.com.petshop.dao.CachorroDaoImpl;
import br.com.petshop.dao.HibernateUtil;
import br.com.petshop.vo.Comportamento;
import br.com.petshop.vo.Dono;
import br.com.petshop.vo.Cachorro;

@ManagedBean(name = "cachorroC")
@ViewScoped
public class CachorroControle {

	private Cachorro cachorro;
	private CachorroDao cachorroDao;
	private Session sessao;
	private List<Cachorro> cachorros;
	private Comportamento comportamento;
	private DataModel<Cachorro> modelCachorros;
	private Dono dono;
	private int aba;

	public CachorroControle() {
		cachorroDao = new CachorroDaoImpl();
	}

	public String pesquisarPorNome() {
		sessao = HibernateUtil.abrirSessao();
		try {
			cachorros = cachorroDao.pesquisarPorNome(cachorro.getNome(), sessao);
			modelCachorros = new ListDataModel<Cachorro>(cachorros);
			cachorro.setNome(null);
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
			cachorro.setDono(dono);
			salvarComportamento();
			cachorro.setComportamento(comportamento);
			cachorroDao.salvarOuAlterar(cachorro, sessao);
			cachorro = null;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso", null));
			modelCachorros = null;
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
		} finally {
		}
	}

	public void prepararAlterar() {
		cachorro = modelCachorros.getRowData();
		comportamento = cachorro.getComportamento();
		dono = cachorro.getDono();
		aba = 1;
	}

	public void limpar() {
		cachorro = new Cachorro();
		dono = new Dono();
		comportamento = new Comportamento();
		aba = 1;
	}

	public void excluir() {
		sessao = HibernateUtil.abrirSessao();
		cachorro = modelCachorros.getRowData();
		try {
			cachorroDao.excluir(cachorro, sessao);
			cachorro = null;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluido com sucesso", null));
			modelCachorros = null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO", "Erro ao excluir"));
		} finally {
			sessao.close();
		}

	}

	public Cachorro getCachorro() {
		if (cachorro == null) {
			cachorro = new Cachorro();
		}
		return cachorro;
	}

	public int getAba() {
		return aba;
	}

	public List<Cachorro> getCachorros() {
		return cachorros;
	}

	public DataModel<Cachorro> getModelCachorros() {
		return modelCachorros;
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

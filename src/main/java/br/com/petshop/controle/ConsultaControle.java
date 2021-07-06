package br.com.petshop.controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

import br.com.petshop.dao.AnimalDao;
import br.com.petshop.dao.AnimalDaoImpl;
import br.com.petshop.dao.ConsultaDao;
import br.com.petshop.dao.ConsultaDaoImpl;
import br.com.petshop.dao.HibernateUtil;
import br.com.petshop.vo.Animal;
import br.com.petshop.vo.Consulta;

@ManagedBean(name = "consultaC")
@ViewScoped
public class ConsultaControle {

	private Consulta consulta;
	private ConsultaDao consultaDao;
	private Session sessao;
	private Animal animal;
	private List<Consulta> consultas;
	private DataModel<Consulta> modelConsultas;
	private List<SelectItem> comboAnimais;
	private int aba;

	public ConsultaControle() {
		consultaDao = new ConsultaDaoImpl();
	}

	public String pesquisarPorData() {
		sessao = HibernateUtil.abrirSessao();
		try {
			consultas = consultaDao.pesquisarPorData(consulta.getDia(), sessao);
			modelConsultas = new ListDataModel<Consulta>(consultas);
			consulta.setDia(null);
		} catch (Exception e) {
			System.out.println("Erro ao pesquisar " + e.getMessage());
		} finally {
			sessao.close();
		}
		return "";
	}

	public void salvar() {
		sessao = HibernateUtil.abrirSessao();
		try {
			consulta.setAnimal(animal);
			consultaDao.salvarOuAlterar(consulta, sessao);
			consulta = null;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso", null));
			modelConsultas = null;
		} catch (HibernateException e) {
			System.out.println("Erro ao salvar: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO", "Erro ao salvar"));
		} finally {
			sessao.close();
		}
	}

	public void prepararAlterar() {
		consulta = modelConsultas.getRowData();
		aba = 1;
	}

	public void limpar() {
		consulta = new Consulta();
		aba = 1;
	}

	public void excluir() {
		sessao = HibernateUtil.abrirSessao();
		consulta = modelConsultas.getRowData();
		try {
			consultaDao.excluir(consulta, sessao);
			consulta = null;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluido com sucesso", null));
			modelConsultas = null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO", "Erro ao excluir"));
		} finally {
			sessao.close();
		}

	}
	
	public void carregarComboAnimais() {
		sessao = HibernateUtil.abrirSessao();
		AnimalDao animalDao = new AnimalDaoImpl();
		try {
			List<Animal> animais = animalDao.pesquisarTodos(sessao);
			comboAnimais = new ArrayList<>();
			for (Animal anim : animais) {
				comboAnimais.add(new SelectItem(anim.getId(), anim.getNome()));
			}

		} catch (HibernateException e) {
			System.out.println("Erro ao carregar ComboBox de Animais!" + e.getMessage());
		} finally {
			sessao.close();
		}
	}
	
    public void onTabChange(TabChangeEvent event) {
    	if(event.getTab().getTitle().equals("Novo")) {
    		carregarComboAnimais();
    	}
    }

    public void onTabClose(TabCloseEvent event) {

    }

	public Consulta getConsulta() {
		if (consulta == null) {
			consulta = new Consulta();
		}

		return consulta;
	}

	public int getAba() {
		return aba;
	}

	public List<Consulta> getConsultas() {
		return consultas;
	}

	public DataModel<Consulta> getModelConsultas() {
		return modelConsultas;
	}

	public Animal getAnimal() {
		if(animal == null) {
			animal = new Animal();
		}
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public List<SelectItem> getComboAnimais() {
		return comboAnimais;
	}

	public void setComboAnimais(List<SelectItem> comboAnimais) {
		this.comboAnimais = comboAnimais;
	}


}

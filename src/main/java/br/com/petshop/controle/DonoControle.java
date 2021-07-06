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

import br.com.petshop.dao.DonoDao;
import br.com.petshop.dao.DonoDaoImpl;
import br.com.petshop.dao.HibernateUtil;
import br.com.petshop.vo.Dono;
import br.com.petshop.vo.Endereco;
import br.com.petshop.webservice.WebServiceEndereco;

@ManagedBean(name = "donoC")
@ViewScoped
public class DonoControle {

	private Dono dono;
	private DonoDao donoDao;
	private Session sessao;
	private List<Dono> donos;
	private DataModel<Dono> modelDonos;
	private Endereco endereco;
	private List<SelectItem> comboDonos;
	private int aba;

	public DonoControle() {
		donoDao = new DonoDaoImpl();
	}

	public String pesquisarPorNome() {
		sessao = HibernateUtil.abrirSessao();
		try {
			donos = donoDao.pesquisarPorNome(dono.getNome(), sessao);
			modelDonos = new ListDataModel<Dono>(donos);
			dono.setNome(null);
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
			endereco.setDono(dono);
			dono.setEndereco(endereco);
			donoDao.salvarOuAlterar(dono, sessao);
			dono = null;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso", null));
			modelDonos = null;
		} catch (HibernateException e) {
			System.out.println("Erro ao salvar: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO", "Erro ao salvar"));
		} finally {
			sessao.close();
		}
	}

	public void prepararAlterar() {
		dono = modelDonos.getRowData();
		endereco = dono.getEndereco();
		aba = 1;
	}
	
	public void limpar() {
		dono = new Dono();
		endereco = new Endereco();
		aba = 1;
	}

	public void excluir() {
		sessao = HibernateUtil.abrirSessao();
		dono = modelDonos.getRowData();
		try {
			donoDao.excluir(dono, sessao);
			dono = null;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluido com sucesso", null));
			modelDonos = null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO", "Erro ao excluir"));
		} finally {
			sessao.close();
		}

	}
	
    public void buscarCep() {
        WebServiceEndereco webService = new WebServiceEndereco();
        endereco = webService.pesquisarCep(endereco.getCep());
        if (endereco.getLogradouro() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Não existe nenhum cep com esse valor!!", null));
        }
    }
    
    public void carregarComboDono() {
        sessao = HibernateUtil.abrirSessao();
        DonoDao donoDao = new DonoDaoImpl();
        try {
            List<Dono> donos = donoDao.pesquisarTodos(sessao);
            comboDonos = new ArrayList<>();
            for (Dono dono : donos) {
                comboDonos.add(new SelectItem(dono.getId(), dono.getNome()));
            }
        } catch (HibernateException e) {
            System.out.println("Erro ao carregar combobox dono " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

	public Dono getDono() {
		if (dono == null) {
			dono = new Dono();
		}

		return dono;
	}

	public int getAba() {
		return aba;
	}

	public List<Dono> getDonos() {
		return donos;
	}

	public DataModel<Dono> getModelDonos() {
		return modelDonos;
	}

	public Endereco getEndereco() {
		if(endereco == null) {
			endereco = new Endereco();
		}
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

}

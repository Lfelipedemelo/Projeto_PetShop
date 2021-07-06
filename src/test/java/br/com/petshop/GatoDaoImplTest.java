/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.petshop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import br.com.petshop.dao.ComportamentoDao;
import br.com.petshop.dao.ComportamentoDaoImpl;
import br.com.petshop.dao.GatoDao;
import br.com.petshop.dao.GatoDaoImpl;
import br.com.petshop.dao.HibernateUtil;
import br.com.petshop.vo.Comportamento;
import br.com.petshop.vo.Dono;
import br.com.petshop.vo.Gato;

/**
 *
 * @author Silvio
 */
public class GatoDaoImplTest {

    private Gato gato;
    private GatoDao gatoDao;
    private Session sessao;

    public GatoDaoImplTest() {
        gatoDao = new GatoDaoImpl();
    }

//    @Test
    public void testSalvar() {
        System.out.println("salvar");
        DonoDaoImplTest donoTeste = new DonoDaoImplTest();
        Dono dono = donoTeste.buscarDonoBD();

        gato = new Gato("Gata lia", "femea", "bla, bla...", new Date(), 4, true, true);
        gato.setDono(dono);
        Comportamento comportamento = pesquisarComportamento();
        gato.setComportamento(comportamento);
        sessao = HibernateUtil.abrirSessao();

        gatoDao.salvarOuAlterar(gato, sessao);
        sessao.close();
        assertNotNull(gato.getId());
    }

    private Comportamento pesquisarComportamento() {
        sessao = HibernateUtil.abrirSessao();
        ComportamentoDao comportamentoDao = new ComportamentoDaoImpl();
        Comportamento comportamento = comportamentoDao.pesquisarTodo(sessao).get(0);
        sessao.close();
        return comportamento;
    }

//    @Test
    public void testAlterar() {
        System.out.println("alterar");
        buscarGatoBD();
        gato.setNome("Gata lia alterada");
        sessao = HibernateUtil.abrirSessao();
        gatoDao.salvarOuAlterar(gato, sessao);
        sessao.close();

        sessao = HibernateUtil.abrirSessao();
        Gato gatoAlt = gatoDao.pesquisarPorId(gato.getId(), sessao);
        sessao.close();
        assertEquals(gato.getNome(), gatoAlt.getNome());

    }

//    @Test
    public void testExcluir() {
        System.out.println("excluir");
        buscarGatoBD();
        sessao = HibernateUtil.abrirSessao();
        gatoDao.excluir(gato, sessao);

        Gato gatoExc = gatoDao.pesquisarPorId(gato.getId(), sessao);
        sessao.close();
        assertNull(gatoExc);
    }

    @Test
    public void testPesquisarPorNome() {
        System.out.println("pesquisarPorNome");
        buscarGatoBD();
        sessao = HibernateUtil.abrirSessao();
        List<Gato> gatos = gatoDao.pesquisarPorNome(gato.getNome(), sessao);
        sessao.close();
        assertTrue(gatos.size() >= 1);
    }

    public Gato buscarGatoBD() {
        sessao = HibernateUtil.abrirSessao();
        Query consulta = sessao.createQuery("from Gato");
        List<Gato> gatos = consulta.list();
        sessao.close();
        if (gatos.isEmpty()) {
            testSalvar();
        } else {
            gato = gatos.get(0);
        }
        return gato;
    }

}

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

import br.com.petshop.dao.CachorroDao;
import br.com.petshop.dao.CachorroDaoImpl;
import br.com.petshop.dao.HibernateUtil;
import br.com.petshop.vo.Cachorro;
import br.com.petshop.vo.Dono;

public class CachorroDaoImplTest {

    private Cachorro cachorro;
    private CachorroDao cachorroDao;
    private Session sessao;

    public CachorroDaoImplTest() {
        cachorroDao = new CachorroDaoImpl();
    }

    @Test
    public void testSalvar() {
        System.out.println("salvar");
        DonoDaoImplTest donoTeste = new DonoDaoImplTest();
        Dono dono = donoTeste.buscarDonoBD();

        cachorro = new Cachorro("Dog Kiko", "Macho", "bla, bla...", new Date(), 4, false);
        cachorro.setDono(dono);
        sessao = HibernateUtil.abrirSessao();
        cachorroDao.salvarOuAlterar(cachorro, sessao);
        sessao.close();
        assertNotNull(cachorro.getId());
    }

    @Test
    public void testAlterar() {
        System.out.println("alterar");
        buscarCachorroBD();
        cachorro.setNome("Dog kiko alterada");
        sessao = HibernateUtil.abrirSessao();
        cachorroDao.salvarOuAlterar(cachorro, sessao);
        sessao.close();

        sessao = HibernateUtil.abrirSessao();
        Cachorro cachorroAlt = cachorroDao.pesquisarPorId(cachorro.getId(), sessao);
        sessao.close();
        assertEquals(cachorro.getNome(), cachorroAlt.getNome());
    }

    @Test
    public void testExcluir() {
        System.out.println("excluir");
        buscarCachorroBD();
        sessao = HibernateUtil.abrirSessao();
        cachorroDao.excluir(cachorro, sessao);

        Cachorro cachorroExc = cachorroDao.pesquisarPorId(cachorro.getId(), sessao);
        sessao.close();
        assertNull(cachorroExc);
    }

    @Test
    public void testPesquisarPorNome() {
        System.out.println("pesquisarPorNome");
        buscarCachorroBD();
        sessao = HibernateUtil.abrirSessao();
        List<Cachorro> cachorros = cachorroDao.pesquisarPorNome(cachorro.getNome(), sessao);
        sessao.close();
        assertTrue(cachorros.size() >= 1);
    }

    public Cachorro buscarCachorroBD() {
        sessao = HibernateUtil.abrirSessao();
        Query consulta = sessao.createQuery("from Cachorro");
        List<Cachorro> dogs = consulta.list();
        sessao.close();
        if (dogs.isEmpty()) {
            testSalvar();
        } else {
            cachorro = dogs.get(0);
        }
        return cachorro;
    }
}

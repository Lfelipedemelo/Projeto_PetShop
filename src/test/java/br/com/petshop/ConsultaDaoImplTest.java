package br.com.petshop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import br.com.petshop.dao.ConsultaDao;
import br.com.petshop.dao.ConsultaDaoImpl;
import br.com.petshop.dao.HibernateUtil;
import br.com.petshop.util.UtilGerador;
import br.com.petshop.vo.Consulta;
import br.com.petshop.vo.Gato;

public class ConsultaDaoImplTest {

    private Consulta consulta;
    private ConsultaDao consultaDao;
    private Session sessao;

    public ConsultaDaoImplTest() {
        consultaDao = new ConsultaDaoImpl();
    }

//    @Test
    public void testSalvar() {
        System.out.println("salvar");

        GatoDaoImplTest gatoTeste = new GatoDaoImplTest();
        Gato gato = gatoTeste.buscarGatoBD();
        consulta = new Consulta(new Date(), new BigDecimal(UtilGerador.gerarNumero(3)));
        consulta.setAnimal(gato);

        sessao = HibernateUtil.abrirSessao();
        consultaDao.salvarOuAlterar(consulta, sessao);
        sessao.close();
        assertNotNull(consulta.getId());
    }

//    @Test
    public void testAlterar() {
        System.out.println("alterar");
        buscaConsultaBd();
        consulta.setValor(new BigDecimal(UtilGerador.gerarNumero(3)));
        sessao = HibernateUtil.abrirSessao();

        consultaDao.salvarOuAlterar(consulta, sessao);
        sessao.close();

        sessao = HibernateUtil.abrirSessao();
        Consulta consultaAlt = consultaDao.pesquisarPorId(consulta.getId(), sessao);
        sessao.close();
        assertEquals(consultaAlt.getValor().setScale(2), consulta.getValor().setScale(2));
    }

    @Test
    public void testPesquisarPorId() {
        System.out.println("excluir");
        buscaConsultaBd();
        sessao = HibernateUtil.abrirSessao();
        consultaDao.excluir(consulta, sessao);

        Consulta consultaExc = consultaDao.pesquisarPorId(consulta.getId(), sessao);
        sessao.close();
        assertNull(consultaExc);
    }

    @Test
    public void testPesquisarPorData() {
        System.out.println("pesquisarPorData");
        testSalvar();
        testSalvar();
        sessao = HibernateUtil.abrirSessao();

        List<Consulta> consultas = this.consultaDao.pesquisarPorData(consulta.getDia(), sessao);

        sessao.close();
        assertTrue(consultas.size() >= 2);
    }

    public Consulta buscaConsultaBd() {
        sessao = HibernateUtil.abrirSessao();
        Query query = sessao.createQuery("from Consulta");
        List<Consulta> consultas = query.list();
        sessao.close();
        if (consultas.isEmpty()) {
            testSalvar();
        } else {
            consulta = consultas.get(0);
        }
        return consulta;
    }

}

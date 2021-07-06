/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.petshop;

import static br.com.petshop.util.UtilGerador.gerarCpf;
import static br.com.petshop.util.UtilGerador.gerarEmail;
import static br.com.petshop.util.UtilGerador.gerarNome;
import static br.com.petshop.util.UtilGerador.gerarTelefoneFixo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import br.com.petshop.dao.DonoDao;
import br.com.petshop.dao.DonoDaoImpl;
import br.com.petshop.dao.HibernateUtil;
import br.com.petshop.vo.Dono;

/**
 *
 * @author Silvio
 */
public class DonoDaoImplTest {

    private Dono dono;
    private DonoDao donoDao;
    private Session sessao;

    public DonoDaoImplTest() {
        donoDao = new DonoDaoImpl();
    }

//    @Test
    public void testSalvar() {
        System.out.println("Salvar");
        dono = new Dono(gerarNome(), gerarCpf(), gerarTelefoneFixo(), gerarEmail());
        sessao = HibernateUtil.abrirSessao();
        donoDao.salvarOuAlterar(dono, sessao);
        sessao.close();
        assertNotNull(dono.getId());
    }
    
//    @Test
    public void testPesquisarPorId() {
        System.out.println("pesquisarPorId");
    }

//    @Test
    public void testPesquisarPorCpfDono() {
        System.out.println("pesquisarPorCpfDono");
    }

    @Test
    public void testPesquisarPorNome() {
        System.out.println("pesquisarPorNome");
        buscarDonoBD();
        sessao = HibernateUtil.abrirSessao();
        List<Dono> donos = donoDao.pesquisarPorNome(dono.getNome().substring(0, 4), sessao);
        sessao.close();
        
        assertTrue(!donos.isEmpty());
        assertNotNull(donos.get(0).getAnimais());
    }
    
    public Dono buscarDonoBD(){
        sessao = HibernateUtil.abrirSessao();
        Query consulta = sessao.createQuery("from Dono");
        List<Dono> donos = consulta.list();
        sessao.close();
        if(donos.isEmpty()){
            testSalvar();
        }else{
            dono = donos.get(0);
        }
        return dono;
    }

}

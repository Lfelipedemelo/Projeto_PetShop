package br.com.petshop.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.petshop.vo.Gato;

public class GatoDaoImpl extends BaseDaoImpl<Gato, Long> implements GatoDao, Serializable{

    @Override
    public Gato pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (Gato) sessao.get(Gato.class, id);
    }

    @Override
    public List<Gato> pesquisarPorNome(String nome, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from Gato where nome = :nome");
        consulta.setParameter("nome", nome);
        return consulta.list();
    }
    
}

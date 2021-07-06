package br.com.petshop.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.petshop.vo.Dono;

public class DonoDaoImpl extends BaseDaoImpl<Dono, Long> implements DonoDao, Serializable {

    @Override
    public Dono pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (Dono) sessao.get(Dono.class, id);
    }

    @Override
    public List<Dono> pesquisarTodos(Session sessao) throws HibernateException{
    	return sessao.createQuery("from Dono").list();    	
    }
    
    @Override
    public Dono pesquisarPorCpfDono(String cpf, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from Dono d where cpf = :cpf");
        consulta.setParameter("cpf", cpf);
        return (Dono) consulta.uniqueResult();
    }

    @Override
    public List<Dono> pesquisarPorNome(String nome, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from Dono d where d.nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

}

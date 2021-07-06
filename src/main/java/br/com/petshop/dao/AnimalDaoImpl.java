package br.com.petshop.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.petshop.vo.Animal;
import br.com.petshop.vo.Cachorro;

public class AnimalDaoImpl extends BaseDaoImpl<Animal, Long> implements AnimalDao, Serializable{

    @Override
    public Animal pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (Animal) sessao.get(Animal.class, id);
    }

    @Override
    public List<Animal> pesquisarPorNome(String nome, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from Cachorro where nome = :nome");
        consulta.setParameter("nome", nome);
        return consulta.list();
    }

	@Override
	public List<Animal> pesquisarTodos(Session sessao) throws HibernateException {
		return sessao.createQuery("from Animal").list();
	}
    
}

package br.com.petshop.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.petshop.vo.Animal;

public interface AnimalDao extends BaseDao<Animal, Long> {

	List<Animal> pesquisarPorNome(String nome, Session sessao) throws HibernateException;

	List<Animal> pesquisarTodos(Session sessao) throws HibernateException;
}

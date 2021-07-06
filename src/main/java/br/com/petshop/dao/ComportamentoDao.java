package br.com.petshop.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.petshop.vo.Cachorro;
import br.com.petshop.vo.Comportamento;

public interface ComportamentoDao extends BaseDao<Comportamento, Long> {

	List<Comportamento> pesquisarTodo(Session sessao) throws HibernateException;
}

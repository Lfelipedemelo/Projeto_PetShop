/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.petshop.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.petshop.vo.Cachorro;
import br.com.petshop.vo.Comportamento;

/**
 *
 * @author Silvio
 */
public class ComportamentoDaoImpl extends BaseDaoImpl<Comportamento, Long> implements ComportamentoDao, Serializable {

    @Override
    public List<Comportamento> pesquisarTodo(Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from Comportamento");
        return consulta.list();
    }

	@Override
	public Comportamento pesquisarPorId(Long id, Session sessao) throws HibernateException {
		return (Comportamento) sessao.get(Comportamento.class, id);
	}


}

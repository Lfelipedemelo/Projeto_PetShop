package br.com.petshop.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.petshop.vo.Dono;

public interface DonoDao extends BaseDao<Dono, Long> {

    Dono pesquisarPorCpfDono(String cpf, Session sessao) throws HibernateException;
    
    List<Dono> pesquisarTodos(Session sessao) throws HibernateException;
    
    List<Dono> pesquisarPorNome(String nome, Session sessao) throws HibernateException;
}

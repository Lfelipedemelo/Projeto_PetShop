package br.com.petshop.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.petshop.vo.Gato;

public interface GatoDao extends BaseDao<Gato, Long>{
    
    List<Gato> pesquisarPorNome(String nome, Session sessao) throws HibernateException;
}

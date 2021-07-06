package br.com.petshop.dao;

import java.util.List;
import org.hibernate.*;

import br.com.petshop.vo.Cachorro;

public interface CachorroDao extends BaseDao<Cachorro, Long> {

    List<Cachorro> pesquisarPorNome(String nome, Session sessao) throws HibernateException;
}

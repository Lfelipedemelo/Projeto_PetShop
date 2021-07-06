/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.petshop.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.petshop.vo.Consulta;

/**
 *
 * @author Silvio
 */
public interface ConsultaDao extends BaseDao<Consulta, Long> {

    List<Consulta> pesquisarPorData(Date dia, Session sessao) throws HibernateException;
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories.impl;

import Data.User;
import Repositories.UserRepository;
import Utils.HiberneteUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author admin
 */
public class UserRepositoryHibernete implements UserRepository {

    
    @Override
    public User findByUsername(String username) {
        try(Session session = HiberneteUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery(
                    "From User where username = :username", 
                    User.class
            );
            query.setParameter("username", username);
            return query.uniqueResult();
        }
    }

    @Override
    public User findById(int id) {
        try(Session session = HiberneteUtil.getSessionFactory().openSession()) {
            return session.get(User.class, id);
        }
    }

    @Override
    public void save(User user) {
        Transaction tx = null;
        try(Session session = HiberneteUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(user);
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
    
}

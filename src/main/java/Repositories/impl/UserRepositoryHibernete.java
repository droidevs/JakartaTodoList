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
        System.out.println("UserRepositoryHibernete.findByUsername: username='" + username + "'");
        try(Session session = HiberneteUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery(
                    "from Data.User u where u.username = :username",
                    User.class
            );
            query.setParameter("username", username);
            User result = query.uniqueResult();
            System.out.println("UserRepositoryHibernete.findByUsername: result=" + (result == null ? "null" : "id=" + result.getId()));
            return result;
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
            System.out.println("UserRepositoryHibernete.save: saving user username='" + user.getUsername() + "'");
            Object generated = session.save(user);
            // set id if generated is integer
            if (generated instanceof Number) {
                user.setId(((Number) generated).intValue());
            }
            tx.commit();
            System.out.println("UserRepositoryHibernete.save: saved user id=" + user.getId());
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
    
}

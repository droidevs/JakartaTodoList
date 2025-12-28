/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories.impl;

import Data.Category;
import Repositories.CategoryRepository;
import Utils.HiberneteUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author admin
 */
public class CategoryRepositoryHibernete implements CategoryRepository {

    private final SessionFactory sessionFactory;

    public CategoryRepositoryHibernete() {
        this.sessionFactory = HiberneteUtil.getSessionFactory();
    }
    
    
    @Override
    public Category findById(int id) {
        try(Session session = sessionFactory.openSession()){
            return session.get(Category.class, id);
        }
    }

    @Override
    public Category findByName(String name) {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery(
                "FROM Category c WHERE c.name = :name", Category.class
            ).setParameter("name", name)
             .uniqueResult();
        }
    }

    @Override
    public List<Category> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                "FROM category", Category.class
            ).list();
        }
    }

    @Override
    public void save(Category category) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(category);
            tx.commit();
        }
    }

    @Override
    public void update(Category category) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(category);
            tx.commit();
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Category category = session.get(Category.class, id);
            if(category != null) {
                session.remove(category);
            }
            tx.commit();
        }
    }
    
}

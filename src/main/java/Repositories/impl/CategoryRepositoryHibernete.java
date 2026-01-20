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
        try (Session session = sessionFactory.openSession()) {
            return session.get(Category.class, id);
        }
    }

    @Override
    public Category findByIdAndUser(int id, int userId) {
        try (Session s = sessionFactory.openSession()) {
            return s.createQuery(
                    "FROM Category c\n"
                    + "WHERE c.id = :id AND c.user.id = :userId",
                    Category.class
            )
                    .setParameter("id", id)
                    .setParameter("userId", userId)
                    .uniqueResult();
        }
    }

    @Override
    public Category findByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "FROM Category c WHERE c.name = :name", Category.class
            ).setParameter("name", name)
                    .uniqueResult();
        }
    }

    @Override
    public List<Category> findAll(int userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "FROM Category c WHERE c.user.id = :userId",
                    Category.class
            ).setParameter("userId", userId)
                    .list();
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
        try (Session session = sessionFactory.openSession()) {
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
            if (category != null) {
                session.remove(category);
            }
            tx.commit();
        }
    }

    @Override
    public boolean existsByNameAndUser(String name, int userId) {
        try (Session session = sessionFactory.openSession()) {
            Long count = session.createQuery(
                    "SELECT COUNT(c) FROM Category c WHERE c.name = :name AND c.user.id = :userId",
                    Long.class
            )
                    .setParameter("name", name)
                    .setParameter("userId", userId)
                    .uniqueResult();

            return count != null && count > 0;
        }
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories.impl;

import Constants.TodoStatus;
import Data.Todo;
import Data.User;
import Data.Category;
import Repositories.TodoRepository;
import Utils.HiberneteUtil;
import java.time.LocalDate;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author admin
 */
public class TodoRepositoryHibernete implements TodoRepository {


    SessionFactory sessionFactory;

    public TodoRepositoryHibernete() {
        this.sessionFactory = HiberneteUtil.getSessionFactory();
    }

    @Override
    public Todo findById(int id) {
        try(Session session = sessionFactory.openSession()) {
            return session.get(Todo.class, id);
        }
    }

    @Override
    public List<Todo> findByUserId(int userId) {
        try(Session session = sessionFactory.openSession()) {
            // Fetch associations (category and user) to avoid LazyInitializationException
            Query<Todo> query = session.createQuery(
                "SELECT DISTINCT t FROM Data.Todo t "
                + "LEFT JOIN FETCH t.category "
                + "LEFT JOIN FETCH t.user "
                + "WHERE t.user.id = :userId",
                Todo.class
            );
            query.setParameter("userId", userId);
            return query.list();
        }
    }

    @Override
    public void save(Todo todo) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            // Attach managed associations in the same session to avoid detached-proxy issues
            if (todo.getUser() != null && todo.getUser().getId() != null) {
                User managedUser = session.get(User.class, todo.getUser().getId());
                if (managedUser == null) {
                    throw new IllegalStateException("TodoRepositoryHibernete.save: user with id=" + todo.getUser().getId() + " not found in DB");
                }
                todo.setUser(managedUser);
            }
            if (todo.getCategory() != null && todo.getCategory().getId() != null) {
                Category managedCategory = session.get(Category.class, todo.getCategory().getId());
                // category may be intentionally null (optional); if missing treat as null
                if (managedCategory == null) {
                    todo.setCategory(null);
                } else {
                    todo.setCategory(managedCategory);
                }
            }

            System.out.println("TodoRepositoryHibernete.save: preparing to save todo title='" + todo.getTitle() + "' for user=" + (todo.getUser() != null ? todo.getUser().getId() : "null"));

            // DEBUG: detailed payload
            try {
                System.out.println("TodoRepositoryHibernete.save: payload -> title='" + todo.getTitle() + "', descriptionLen=" + (todo.getDescription() == null ? 0 : todo.getDescription().length())
                        + ", userId=" + (todo.getUser() != null ? todo.getUser().getId() : "null")
                        + ", categoryId=" + (todo.getCategory() != null ? todo.getCategory().getId() : "null")
                        + ", getStatus()=" + (todo.getStatus() == null ? "null" : todo.getStatus())
                        + ", dueDate=" + todo.getDueDate());
            } catch (Throwable t) {
                System.out.println("TodoRepositoryHibernete.save: error printing payload: " + t.getMessage());
            }

            try {
                session.persist(todo);
                // ensure insert occurs and id is generated while connection is open
                session.flush();

                tx.commit();

                System.out.println("TodoRepositoryHibernete.save: saved todo id=" + todo.getId());
            } catch (Throwable persistEx) {
                // Log and rethrow so caller (servlet) gets the full exception
                System.out.println("TodoRepositoryHibernete.save: persist failed: " + persistEx.getClass().getName() + " - " + persistEx.getMessage());
                if (persistEx.getCause() != null) {
                    System.out.println("Caused by: " + persistEx.getCause().getClass().getName() + " - " + persistEx.getCause().getMessage());
                }
                if (tx != null) tx.rollback();
                persistEx.printStackTrace();
                throw new RuntimeException(persistEx);
            }
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Todo todo) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.merge(todo);
            tx.commit();
        } catch(Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Todo todo = session.get(Todo.class, id);
            if(todo != null) session.remove(todo);
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Integer getUserIdForTodo(Integer todoId) {
        Todo todo = findById(todoId);
        return todo != null && todo.getUser() != null ? todo.getUser().getId() : null;
    }

    @Override
    public void markOverdueTodos() {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            LocalDate today = LocalDate.now();
            Query<Todo> query = session.createQuery(
                    "FROM Data.Todo WHERE dueDate < :today AND status != 'COMPLETED' AND status != 'OVERDUE'",
                    Todo.class
            );
            query.setParameter("today", today);
            List<Todo> todos = query.list();
            for(Todo todo : todos) {
                todo.setStatus(TodoStatus.OVERDUE);
                session.merge(todo);
            }
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories.impl;

import Constants.TodoStatus;
import Data.Todo;
import Repositories.TodoRepository;
import Utils.HiberneteUtil;
import java.time.LocalDate;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author admin
 */
public class TodoRepositoryHibernete implements TodoRepository {
    
    @Override
    public Todo findById(int id) {
        try(Session session = HiberneteUtil.getSessionFactory().openSession()) {
            return session.get(Todo.class, id);
        }
    }

    @Override
    public List<Todo> findByUserId(int userId) {
        try(Session session = HiberneteUtil.getSessionFactory().openSession()) {
            Query<Todo> query = session.createQuery(
                "FROM Todo WHERE user.id = :userId", Todo.class
            );
            
            query.setParameter("userId", userId);
            return query.list();
        }
    }

    @Override
    public void save(Todo todo) {
        Transaction tx = null;
        
        try(Session session = HiberneteUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(todo);
            tx.commit();
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update(Todo todo) {
        Transaction tx = null;
        
        try(Session session = HiberneteUtil.getSessionFactory().openSession()) {
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
        try(Session session = HiberneteUtil.getSessionFactory().openSession()) {
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
        try(Session session = HiberneteUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            LocalDate today = LocalDate.now();
            
            Query<Todo> query = session.createQuery(
                    "FROM Todo WHERE dueDate < :today AND status != 'COMPLETED' AND status != 'OVERDUE'",
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

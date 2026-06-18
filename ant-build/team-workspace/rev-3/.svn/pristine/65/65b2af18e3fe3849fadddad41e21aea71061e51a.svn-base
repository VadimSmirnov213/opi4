package org.example.repository;

import org.example.entity.PointEntity;
import org.example.exception.RepositoryException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class ResultsRepository {
    private static final String PERSISTENCE_UNIT_NAME = "lab3-persistence-unit";

    private EntityManagerFactory emf;
    private EntityManager em;

    @PostConstruct
    public void init() throws RepositoryException {
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            em = emf.createEntityManager();
        } catch (Exception e) {
            throw new RepositoryException("Ошибка инициализации базы данных: " + e.getMessage(), e);
        }
    }

    @PreDestroy
    public void cleanup() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    private EntityManager getEntityManager() throws RepositoryException {
        if (em != null && em.isOpen()) {
            return em;
        }
        throw new RepositoryException("База данных недоступна. EntityManager не инициализирован или закрыт.");
    }

    public PointEntity save(PointEntity point) throws RepositoryException {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            PointEntity result;
            if (point.getId() == null) {
                entityManager.persist(point);
                result = point;
            } else {
                result = entityManager.merge(point);
            }
            entityManager.getTransaction().commit();
            return result;
        } catch (RepositoryException e) {
            throw e;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new RepositoryException("Ошибка сохранения точки в базу данных: " + e.getMessage(), e);
        }
    }

    public List<PointEntity> findAll() throws RepositoryException {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<PointEntity> query = entityManager.createQuery(
                "SELECT p FROM PointEntity p ORDER BY p.timestamp DESC",
                PointEntity.class
            );
            return query.getResultList();
        } catch (RepositoryException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException("Ошибка чтения данных из базы данных: " + e.getMessage(), e);
        }
    }

    public void clear() throws RepositoryException {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.createQuery("DELETE FROM PointEntity").executeUpdate();
            entityManager.getTransaction().commit();
        } catch (RepositoryException e) {
            throw e;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new RepositoryException("Ошибка очистки базы данных: " + e.getMessage(), e);
        }
    }

    public int count() throws RepositoryException {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(p) FROM PointEntity p",
                Long.class
            );
            return query.getSingleResult().intValue();
        } catch (RepositoryException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException("Ошибка подсчета записей в базе данных: " + e.getMessage(), e);
        }
    }
}

package org.example.model.tables;

import org.apache.commons.lang3.StringUtils;
import org.example.model.entities.Film;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class TableFilmsHiberImpl implements TableFilms {
    private SessionFactory sessionFactory;

    public TableFilmsHiberImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addNew(Film newFilm) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.save(newFilm);

        transaction.commit();
        session.close();
    }

    @Override
    public List<Film> getAll() throws Exception {
        return (List<Film>) sessionFactory.openSession().createQuery("FROM Film ORDER BY id ASC").getResultList();
    }

    @Override
    public List<Film> getAllFromStartId(long startId) throws Exception {
        Query query = sessionFactory.openSession().createQuery("FROM Film WHERE id >= :startId  ORDER BY id");
        query.setParameter("startId", startId);
        return (List<Film>) query.getResultList();
    }

    @Override
    public List<Film> getAllByIdOrNameOrTag(String searchValue) throws Exception {
        Query query = null;

        if (StringUtils.isNumeric(searchValue)) {
            query = sessionFactory.openSession().createQuery("FROM Film WHERE id = :searchId OR name LIKE :searchValue OR tags LIKE :searchValue ORDER BY id");

            long searchId = Long.parseLong(searchValue);

            query.setParameter("searchId", searchId);
            query.setParameter("searchValue", "%" + searchValue + "%");
        } else {
            query = sessionFactory.openSession().createQuery("FROM Film WHERE name LIKE :searchValue OR tags LIKE :searchValue ORDER BY id");
            query.setParameter("searchValue", "%" + searchValue + "%");
        }

        return (List<Film>) query.getResultList();
    }

    @Override
    public List<Film> getAllByIdOrNameOrTagFromId(String searchValue, long startId) throws Exception {
        Query query = sessionFactory.openSession().createQuery("FROM Film WHERE (name LIKE :searchValue OR tags LIKE :searchValue) AND id>=:startId ORDER BY id");

        query.setParameter("searchValue", "%" + searchValue + "%");
        query.setParameter("startId", startId);
        return (List<Film>) query.getResultList();
    }
}

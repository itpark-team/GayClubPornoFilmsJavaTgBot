package org.example.model.connection;

import org.example.model.entities.Film;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class HibernateSession {
    private SessionFactory sessionFactory;

    public HibernateSession() {
        try {
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(Film.class);

            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());

        } catch (Exception e) {
            System.out.println("HibernateSession init error:" + e);
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

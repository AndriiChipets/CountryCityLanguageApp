package org.anch.config;

import org.anch.utils.EntityScanner;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class SessionFactoryCreator implements AutoCloseable {

    private static SessionFactoryCreator instance;
    private final SessionFactory sessionFactory;

    private SessionFactoryCreator() {
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        properties.put(Environment.URL, "jdbc:p6spy:mysql://localhost:3306/world");
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "my-secret-pw");
        properties.put(Environment.HBM2DDL_AUTO, "validate");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.STATEMENT_BATCH_SIZE, "100");

        Configuration configuration = new Configuration();
        configuration.setProperties(properties);
        EntityScanner.getEntities().forEach(configuration::addAnnotatedClass);
        sessionFactory = configuration.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        if (instance == null) {
            instance = new SessionFactoryCreator();
        }
        return instance.sessionFactory;
    }

    @Override
    public void close() throws Exception {
        instance.close();
    }

}

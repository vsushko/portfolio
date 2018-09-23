package com.revolut.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * SessionFactoryUtil
 *
 * @author vsushko
 */
public class SessionFactoryUtil {

    /**
     * Returns session factory
     *
     * @return session factory
     */
    public static SessionFactory getSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }
}

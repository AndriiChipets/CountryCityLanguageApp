package org.anch.dao.impl;

import lombok.AllArgsConstructor;
import org.anch.dao.CountryDao;
import org.anch.domain.Country;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

@AllArgsConstructor
public class CountryDaoImpl implements CountryDao {
    private static final String FIND_ALL_COUNTRIES_QUERY = "select c from Country c join fetch c.languages";

    private final SessionFactory sessionFactory;

    public List<Country> getAll() {

        List<Country> countries;
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Query<Country> query = session.createQuery(FIND_ALL_COUNTRIES_QUERY, Country.class);
            countries = query.list();
            transaction.commit();
        }
        return countries;
    }

}

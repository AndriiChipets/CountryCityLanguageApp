package org.anch.dao.impl;

import lombok.AllArgsConstructor;
import org.anch.dao.CountryDao;
import org.anch.domain.Country;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

@AllArgsConstructor
public class CountryDaoImpl implements CountryDao {

    private final SessionFactory sessionFactory;

    public List<Country> getAll() {
        Query<Country> query = sessionFactory.getCurrentSession()
                .createQuery("select c from Country c join fetch c.languages", Country.class);
        return query.list();
    }

}

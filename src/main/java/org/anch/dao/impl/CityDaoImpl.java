package org.anch.dao.impl;

import lombok.AllArgsConstructor;
import org.anch.dao.CityDao;
import org.anch.domain.City;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

@AllArgsConstructor
public class CityDaoImpl implements CityDao {

    private static final String FIND_ALL_CITIES_QUERY = "select c from City c";
    private static final String GET_TOTAL_COUNT_QUERY = "select count(c) from City c";
    private static final String FIND_CITY_BY_ID_QUERY = "select c from City c join fetch c.country where c.id = :id";

    private final SessionFactory sessionFactory;

    public List<City> getItems(int offset, int limit) {

        List<City> cities;
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Query<City> query = session.createQuery(FIND_ALL_CITIES_QUERY, City.class);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            cities = query.list();
            transaction.commit();
        }
        return cities;
    }

    public int getTotalCount() {

        int totalCount;
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Query<Long> query = session.createQuery(GET_TOTAL_COUNT_QUERY, Long.class);
            totalCount = Math.toIntExact(query.uniqueResult());
            transaction.commit();
        }
        return totalCount;
    }

    public City getById(Integer id) {

        City city;
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Query<City> query = session.createQuery(FIND_CITY_BY_ID_QUERY, City.class);
            query.setParameter("id", id);
            city = query.getSingleResult();
            transaction.commit();
        }
        return city;
    }

}
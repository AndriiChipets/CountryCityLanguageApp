package org.anch;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.RedisClient;
import org.anch.config.SessionFactoryCreator;
import org.anch.dao.CountryDao;
import org.anch.dao.impl.CityDaoImpl;
import org.anch.dao.impl.CountryDaoImpl;
import org.anch.domain.City;
import org.anch.domain.Country;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.anch.dao.CityDao;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class Main {
    private final SessionFactory sessionFactory;
    private final RedisClient redisClient;
    private final ObjectMapper mapper;
    private final CityDao cityDao;
    private final CountryDao countryDao;

    public Main() {
        sessionFactory = SessionFactoryCreator.getSessionFactory();
        cityDao = new CityDaoImpl(sessionFactory);
        countryDao = new CountryDaoImpl(sessionFactory);

        redisClient = prepareRedisClient();
        mapper = new ObjectMapper();
    }

    private RedisClient prepareRedisClient() {
        return null;
    }

    private void shutdown() {
        if (nonNull(sessionFactory)) {
            sessionFactory.close();
        }
        if (nonNull(redisClient)) {
            redisClient.shutdown();
        }
    }

    private List<City> fetchData(Main main) {
        try (Session session = main.sessionFactory.getCurrentSession()) {
            List<City> allCities = new ArrayList<>();
            session.beginTransaction();

            List<Country> countries = main.countryDao.getAll();

            int totalCount = main.cityDao.getTotalCount();
            int step = 500;
            for (int i = 0; i < totalCount; i += step) {
                allCities.addAll(main.cityDao.getItems(i, step));
            }
            session.getTransaction().commit();
            return allCities;
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        List<City> allCities = main.fetchData(main);
        main.shutdown();
    }
}

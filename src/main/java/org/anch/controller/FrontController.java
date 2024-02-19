package org.anch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.RedisClient;
import lombok.AllArgsConstructor;
import org.anch.config.RedisClientCreator;
import org.anch.config.SessionFactoryCreator;
import org.anch.dao.CityDao;
import org.anch.dao.CountryDao;
import org.anch.dao.impl.CityDaoImpl;
import org.anch.dao.impl.CountryDaoImpl;
import org.anch.domain.City;
import org.anch.mapper.MapperCityCountry;
import org.anch.redis.CityCountry;
import org.anch.service.ApplicationService;
import org.anch.service.ApplicationServiceImpl;
import org.hibernate.SessionFactory;

import java.util.List;

import static java.util.Objects.nonNull;

@AllArgsConstructor
public class FrontController {

    private final static List<Integer> IDS = List.of(3, 2545, 123, 4, 189, 89, 3458, 1189, 10, 102);
    private final SessionFactory sessionFactory = SessionFactoryCreator.getSessionFactory();
    private final CityDao cityDao = new CityDaoImpl(sessionFactory);
    private final CountryDao countryDao = new CountryDaoImpl(sessionFactory);
    private final ObjectMapper mapper = new ObjectMapper();
    private final RedisClient redisClient = RedisClientCreator.prepareRedisClient();
    private final ApplicationService applicationService = new ApplicationServiceImpl(
            cityDao, countryDao, mapper, redisClient, sessionFactory
    );

    public void compareDataBasesSpeed() {

        List<City> allCities = applicationService.fetchData();
        List<CityCountry> preparedData = MapperCityCountry.transformData(allCities);
        applicationService.pushToRedis(preparedData);

        sessionFactory.getCurrentSession().close();

        long startRedis = System.currentTimeMillis();
        applicationService.testRedisData(IDS);
        long stopRedis = System.currentTimeMillis();

        long startMysql = System.currentTimeMillis();
        applicationService.testMysqlData(IDS);
        long stopMysql = System.currentTimeMillis();

        System.out.printf("%s:\t%d ms\n", "Redis", (stopRedis - startRedis));
        System.out.printf("%s:\t%d ms\n", "MySQL", (stopMysql - startMysql));
        shutdown();

    }

    private void shutdown() {

        if (nonNull(sessionFactory)) {
            sessionFactory.close();
        }
        if (nonNull(redisClient)) {
            redisClient.shutdown();
        }
    }

}

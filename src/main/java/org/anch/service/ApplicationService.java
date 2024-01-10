package org.anch.service;

import org.anch.domain.City;
import org.anch.redis.CityCountry;

import java.util.List;

public interface ApplicationService {

    List<City> fetchData();
    void pushToRedis(List<CityCountry> data);
    void testRedisData(List<Integer> ids);
    void testMysqlData(List<Integer> ids);

}

package org.anch.dao;

import org.anch.domain.City;

import java.util.List;

public interface CityDao {

    List<City> getItems(int offset, int limit);

    int getTotalCount();

    City getById(Integer id);
}

package org.anch.mapper;

import org.anch.domain.City;
import org.anch.domain.Country;
import org.anch.domain.CountryLanguage;
import org.anch.redis.CityCountry;
import org.anch.redis.Language;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MapperCityCountry {

    public static List<CityCountry> transformData(List<City> cities) {

        return cities.stream().map(city -> {
            CityCountry cityCountry = new CityCountry();
            cityCountry.setId(city.getId());
            cityCountry.setName(city.getName());
            cityCountry.setPopulation(city.getPopulation());
            cityCountry.setDistrict(city.getDistrict());

            Country country = city.getCountry();
            cityCountry.setAlternativeCountryCode(country.getAlternativeCode());
            cityCountry.setContinent(country.getContinent());
            cityCountry.setCountryCode(country.getCode());
            cityCountry.setCountryName(country.getName());
            cityCountry.setCountryPopulation(country.getPopulation());
            cityCountry.setCountryRegion(country.getRegion());
            cityCountry.setCountrySurfaceArea(country.getSurfaceArea());
            Set<CountryLanguage> countryLanguages = country.getLanguages();

            Set<Language> languages = countryLanguages.stream().map(cl -> {
                Language language = new Language();
                language.setLanguage(cl.getLanguage());
                language.setIsOfficial(cl.getIsOfficial());
                language.setPercentage(cl.getPercentage());

                return language;
            }).collect(Collectors.toSet());
            cityCountry.setLanguages(languages);

            return cityCountry;
        }).collect(Collectors.toList());
    }

}

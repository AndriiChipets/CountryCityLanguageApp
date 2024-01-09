package org.anch.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "country", catalog = "world")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(setterPrefix = "with")
public class Country {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "code", nullable = false, length = 3, columnDefinition = "varchar default ''")
    private String code;

    @Column(name = "code_2", nullable = false, length = 2, columnDefinition = "varchar default ''")
    private String alternativeCode;

    @Column(name = "name", nullable = false, length = 52, columnDefinition = "varchar default ''")
    private String name;


    @Enumerated(EnumType.ORDINAL)
    @Column(name = "continent", nullable = false,
            columnDefinition = "int default '0', " +
                    "COMMENT '0-ASIA, 1-EUROPE, 2-NORTH_AMERICA, 3-AFRICA, 4-OCEANIA, 5-ANTARCTICA, 6-SOUTH_AMERICA'")
    private Continent continent;

    @Column(name = "region", nullable = false, length = 26, columnDefinition = "varchar default ''")
    private String region;

    @Column(name = "surface_area", nullable = false, columnDefinition = "decimal(10,2) default 0.00")
    private BigDecimal surfaceArea;

    @Column(name = "indep_year")
    private Short indepYear;

    @Column(name = "population", nullable = false, columnDefinition = "int default '0'")
    private Integer population;

    @Column(name = "life_expectancy", columnDefinition = "decimal(3,1)")
    private BigDecimal lifeExpectancy;

    @Column(name = "gnp", columnDefinition = "decimal(10,2)")
    private BigDecimal gnp;

    @Column(name = "gnpo_id", columnDefinition = "decimal(10,2)")
    private BigDecimal gnpoId;

    @Column(name = "local_name", nullable = false, length = 45, columnDefinition = "varchar default ''")
    private String localName;

    @Column(name = "government_form", nullable = false, length = 45, columnDefinition = "varchar default ''")
    private String governmentForm;

    @Column(name = "head_of_state", length = 60)
    private String headOfState;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "country")
    private Set<City> cities;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    private Set<CountryLanguage> languages;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "capital")
    private City capitalCity;

}

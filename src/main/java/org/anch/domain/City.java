package org.anch.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "city", catalog = "world")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(setterPrefix = "with")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, length = 52, columnDefinition = "varchar default ''")
    private String name;

    @Column(name = "district", nullable = false, length = 20, columnDefinition = "varchar default ''")
    private String district;

    @Column(name = "population", nullable = false, columnDefinition = "int default '0'")
    private Integer population;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

}

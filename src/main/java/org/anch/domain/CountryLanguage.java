package org.anch.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "country_language", catalog = "world")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(setterPrefix = "with")
public class CountryLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "language", nullable = false, length = 30, columnDefinition = "varchar default ''")
    private String language;

    @Column(name = "is_official", length = 1, columnDefinition = "BIT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isOfficial;

    @Column(name = "percentage", columnDefinition = "decimal(4,1) default '0.0'")
    @Type(type = "org.hibernate.type.DoubleType")
    private Double percentage;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

}

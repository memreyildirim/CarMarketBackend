package com.example.springboot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Column(name = "car_model")
    private String model;


    @Lob
    @Column(name = "car_specification")
    private String carSpecification;

    @Column(name = "engine_volume")
    private Float engineVolume;

    @Column(name = "is_new")
    private Boolean isNew;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "release_datetime")
    private LocalDateTime releaseDatetime;

    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Photo photo;


}

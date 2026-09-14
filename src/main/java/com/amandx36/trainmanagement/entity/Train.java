package com.amandx36.trainmanagement.entity;

import com.amandx36.trainmanagement.enums.TrainStatus;
import com.amandx36.trainmanagement.enums.TrainType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "trains")
@Data
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer trainNumber;

    @Column(nullable = false)
    private String trainName;

    @Enumerated(EnumType.STRING)
    private TrainType trainType;

    @Column(nullable = false)
    private String sourceStation;

    @Column(nullable = false)
    private String destinationStation;

    @Column(nullable = false)
    private Integer totalSeats;

    @Column(nullable = false)
    private Integer availableSeats;

    @Enumerated(EnumType.STRING)
    private TrainStatus status;

    private Date createdAt;

    private Date updatedAt;
}

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
    private Integer id;

    private Integer trainNumber;

    private String trainName;

    @Enumerated(EnumType.STRING)
    private TrainType trainType;

    private String sourceStation;

    private String destinationStation;

    private Integer totalSeats;

    private Integer availableSeats;

    @Enumerated(EnumType.STRING)
    private TrainStatus status;

    private Date createdAt;

    private Date updatedAt;
}

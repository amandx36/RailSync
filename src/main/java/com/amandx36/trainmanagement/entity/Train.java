package com.amandx36.trainmanagement.entity;

import com.amandx36.trainmanagement.enums.TrainStatus;
import com.amandx36.trainmanagement.enums.TrainType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;


@Table(name = "Table")
@Entity
public class Train {
    @Id
    Integer id ;
    Integer trainNumber ;
    Integer trainName ;
    TrainType trainType ;
    Integer sourceStation ;
    String  destinationStation ;
    Integer totalSeats ;
    Integer availableSeats ;
    TrainStatus  status ;
    Date createdAt ;
    Date updatedAt ;
}

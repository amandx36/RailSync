package com.amandx36.trainmanagement.dto.reponse;

import com.amandx36.trainmanagement.enums.TrainStatus;
import com.amandx36.trainmanagement.enums.TrainType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainResponse {

   private Integer id;
   private Integer trainNumber;
   private String trainName;
   private TrainType trainType;
   private String sourceStation;
   private String destinationStation;
   private Integer availableSeats;
   private TrainStatus status;
}
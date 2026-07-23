package com.amandx36.trainmanagement.dto.request;

import com.amandx36.trainmanagement.enums.TrainType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainRequest {

   private Integer trainNumber;
   private String trainName;
   private TrainType trainType;
   private String sourceStation;
   private String destinationStation;
   private Integer totalSeats;
}
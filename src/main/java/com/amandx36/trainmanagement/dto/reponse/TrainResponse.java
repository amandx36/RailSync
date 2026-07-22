package com.amandx36.trainmanagement.dto.reponse;

import com.amandx36.trainmanagement.enums.TrainStatus;
import com.amandx36.trainmanagement.enums.TrainType;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrainResponse {
   Integer id ;
   Integer trainNumber ;
   TrainType trainName ;
   String sourceStation ;
   String destinationStation;
   Integer availableSeats ;
   TrainStatus status ;
}

package com.amandx36.trainmanagement.dto.request;

import com.amandx36.trainmanagement.enums.TrainType;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrainRequest {
   Integer trainNumber ;
   Integer trainName ;
   TrainType trainType ;
   String sourceStation ;
   String destinationStation ;
   Integer totalSeats ;
}

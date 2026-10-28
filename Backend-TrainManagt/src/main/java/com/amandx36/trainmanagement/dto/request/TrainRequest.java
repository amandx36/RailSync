package com.amandx36.trainmanagement.dto.request;

import com.amandx36.trainmanagement.enums.TrainType;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainRequest {

   @NotNull @Positive
   private Integer trainNumber;
   @NotBlank
   private String trainName;
   @NotNull
   private TrainType trainType;
   @NotBlank
   private String sourceStation;
   @NotBlank
   private String destinationStation;
   @NotNull @Positive
   private Integer totalSeats;
}
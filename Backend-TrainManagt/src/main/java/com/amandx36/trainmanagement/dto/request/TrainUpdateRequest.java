package com.amandx36.trainmanagement.dto.request;

import com.amandx36.trainmanagement.enums.TrainType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainUpdateRequest {

    @NotNull
    @Positive
    private Integer trainNumber;

    @NotBlank
    private String trainName;

    @NotNull
    private TrainType trainType;

    @NotBlank
    private String sourceStation;

    @NotBlank
    private String destinationStation;

    @NotNull
    @Positive
    private Integer totalSeats;
}

package com.amandx36.trainmanagement.mapper;

import com.amandx36.trainmanagement.dto.reponse.TrainResponse;
import com.amandx36.trainmanagement.dto.request.TrainUpdateRequest;
import com.amandx36.trainmanagement.entity.Train;

import java.util.Date;

public final class TrainMapper {

    private TrainMapper() {
    }

    public static TrainResponse toResponse(Train train) {
        TrainResponse response = new TrainResponse();
        response.setId(train.getId());
        response.setTrainNumber(train.getTrainNumber());
        response.setTrainName(train.getTrainName());
        response.setTrainType(train.getTrainType());
        response.setSourceStation(train.getSourceStation());
        response.setDestinationStation(train.getDestinationStation());
        response.setAvailableSeats(train.getAvailableSeats());
        response.setStatus(train.getStatus());
        return response;
    }

    public static void updateEntity(Train train, TrainUpdateRequest request) {
        train.setTrainNumber(request.getTrainNumber());
        train.setTrainName(request.getTrainName());
        train.setTrainType(request.getTrainType());
        train.setSourceStation(request.getSourceStation());
        train.setDestinationStation(request.getDestinationStation());
        train.setTotalSeats(request.getTotalSeats());
        train.setUpdatedAt(new Date());
    }
}

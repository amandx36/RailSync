package com.amandx36.trainmanagement.mapper;

import com.amandx36.trainmanagement.dto.request.TrainRequest;
import com.amandx36.trainmanagement.dto.reponse.TrainResponse;
import com.amandx36.trainmanagement.entity.Train;
import com.amandx36.trainmanagement.enums.TrainStatus;

import java.util.Date;

public class TrainMapper {

    public static Train toEntity(TrainRequest request) {

        Train train = new Train();

        train.setTrainNumber(request.getTrainNumber());
        train.setTrainName(request.getTrainName());
        train.setTrainType(request.getTrainType());
        train.setSourceStation(request.getSourceStation());
        train.setDestinationStation(request.getDestinationStation());

        train.setTotalSeats(request.getTotalSeats());
        train.setAvailableSeats(request.getTotalSeats());

        train.setStatus(TrainStatus.ACTIVE);

        train.setCreatedAt(new Date());
        train.setUpdatedAt(new Date());

        return train;
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

}
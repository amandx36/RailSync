package com.amandx36.trainmanagement.services;

import com.amandx36.trainmanagement.dto.reponse.TrainResponse;
import com.amandx36.trainmanagement.dto.request.TrainRequest;

public interface TrainService {
    TrainResponse createTrain(TrainRequest trainRequest);
}

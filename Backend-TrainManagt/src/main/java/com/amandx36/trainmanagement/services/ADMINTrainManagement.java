package com.amandx36.trainmanagement.services;

import com.amandx36.trainmanagement.dto.reponse.TrainResponse;
import com.amandx36.trainmanagement.dto.request.TrainRequest;
import com.amandx36.trainmanagement.dto.request.TrainUpdateRequest;
import com.amandx36.trainmanagement.enums.TrainType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ADMINTrainManagement {
    TrainResponse createTrain(TrainRequest trainRequest);

    Page<TrainResponse> findTrains(String source, String destination, TrainType type, Pageable pageable);

    TrainResponse getTrain(Long id);

    TrainResponse updateTrain(Long id, TrainUpdateRequest request);

    void deleteTrain(Long id);

    TrainResponse cancelTrain(Long id);

    TrainResponse setMaintenance(Long id);
}

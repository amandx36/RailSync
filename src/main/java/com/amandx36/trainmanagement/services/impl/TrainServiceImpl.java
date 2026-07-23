package com.amandx36.trainmanagement.services.impl;

import com.amandx36.trainmanagement.dto.reponse.TrainResponse;
import com.amandx36.trainmanagement.dto.request.TrainRequest;
import com.amandx36.trainmanagement.entity.Train;
import com.amandx36.trainmanagement.mapper.TrainMapper;
import com.amandx36.trainmanagement.repository.TrainRepository;
import com.amandx36.trainmanagement.services.TrainService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
public class TrainServiceImpl implements TrainService {
    final TrainRepository trainRepository ;

    @Override
    public TrainResponse createTrain(TrainRequest request) {

        Train train = TrainMapper.toEntity(request);

        Train savedTrain = trainRepository.save(train);

        return TrainMapper.toResponse(savedTrain);
    }

}

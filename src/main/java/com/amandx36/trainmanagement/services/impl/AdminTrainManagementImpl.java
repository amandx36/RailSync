package com.amandx36.trainmanagement.services.impl;

import com.amandx36.trainmanagement.dto.reponse.TrainResponse;
import com.amandx36.trainmanagement.dto.request.TrainRequest;
import com.amandx36.trainmanagement.dto.request.TrainUpdateRequest;
import com.amandx36.trainmanagement.entity.Train;
import com.amandx36.trainmanagement.mapper.AdminTrainManagement;
import com.amandx36.trainmanagement.mapper.TrainMapper;
import com.amandx36.trainmanagement.repository.TrainRepository;
import com.amandx36.trainmanagement.repository.specification.TrainSpecifications;
import com.amandx36.trainmanagement.services.ADMINTrainManagement;
import com.amandx36.trainmanagement.enums.TrainStatus;
import com.amandx36.trainmanagement.enums.TrainType;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@AllArgsConstructor
public class AdminTrainManagementImpl implements ADMINTrainManagement {
    final TrainRepository trainRepository ;

    @Override
    public TrainResponse createTrain(TrainRequest request) {

        Train train = AdminTrainManagement.toEntity(request);

        Train savedTrain = trainRepository.save(train);

        return AdminTrainManagement.toResponse(savedTrain);
    }

    @Override
    public Page<TrainResponse> findTrains(String source, String destination, TrainType type, Pageable pageable) {
        Specification<Train> specification = (root, query, builder) -> builder.conjunction();

        if (source != null && !source.isBlank()) {
            specification = specification.and(TrainSpecifications.hasSource(source));
        }
        if (destination != null && !destination.isBlank()) {
            specification = specification.and(TrainSpecifications.hasDestination(destination));
        }
        if (type != null) {
            specification = specification.and(TrainSpecifications.hasType(type));
        }

        return trainRepository.findAll(specification, pageable).map(TrainMapper::toResponse);
    }

    @Override
    public TrainResponse getTrain(Long id) {
        return TrainMapper.toResponse(findTrain(id));
    }

    @Override
    public TrainResponse updateTrain(Long id, TrainUpdateRequest request) {
        Train train = findTrain(id);

        if (trainRepository.existsByTrainNumberAndIdNot(request.getTrainNumber(), id)) {
            throw new IllegalArgumentException("Train number already exists");
        }

        int bookedSeats = train.getTotalSeats() - train.getAvailableSeats();
        if (request.getTotalSeats() < bookedSeats) {
            throw new IllegalArgumentException("Total seats cannot be lower than booked seats");
        }

        TrainMapper.updateEntity(train, request);
        train.setAvailableSeats(request.getTotalSeats() - bookedSeats);

        return TrainMapper.toResponse(trainRepository.save(train));
    }

    @Override
    public void deleteTrain(Long id) {
        trainRepository.delete(findTrain(id));
    }

    @Override
    public TrainResponse cancelTrain(Long id) {
        return updateStatus(id, TrainStatus.CANCELLED);
    }

    @Override
    public TrainResponse setMaintenance(Long id) {
        return updateStatus(id, TrainStatus.MAINTENANCE);
    }

    private TrainResponse updateStatus(Long id, TrainStatus status) {
        Train train = findTrain(id);
        train.setStatus(status);
        train.setUpdatedAt(new Date());
        return TrainMapper.toResponse(trainRepository.save(train));
    }

    private Train findTrain(Long id) {
        return trainRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Train not found: " + id));
    }

}

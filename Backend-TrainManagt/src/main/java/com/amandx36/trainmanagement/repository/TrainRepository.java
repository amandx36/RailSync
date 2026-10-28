package com.amandx36.trainmanagement.repository;

import com.amandx36.trainmanagement.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TrainRepository extends JpaRepository<Train, Long>, JpaSpecificationExecutor<Train> {
    Optional<Train> findByTrainNumber(Integer trainNumber);

    boolean existsByTrainNumber(Integer trainNumber);

    boolean existsByTrainNumberAndIdNot(Integer trainNumber, Long id);
}

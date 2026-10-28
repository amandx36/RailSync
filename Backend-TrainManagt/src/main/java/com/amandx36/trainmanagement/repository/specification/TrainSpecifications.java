package com.amandx36.trainmanagement.repository.specification;

import com.amandx36.trainmanagement.entity.Train;
import com.amandx36.trainmanagement.enums.TrainType;
import org.springframework.data.jpa.domain.Specification;

public final class TrainSpecifications {

    private TrainSpecifications() {
    }

    public static Specification<Train> hasSource(String source) {
        return (root, query, builder) -> builder.like(
                builder.lower(root.get("sourceStation")),
                "%" + source.toLowerCase() + "%"
        );
    }

    public static Specification<Train> hasDestination(String destination) {
        return (root, query, builder) -> builder.like(
                builder.lower(root.get("destinationStation")),
                "%" + destination.toLowerCase() + "%"
        );
    }

    public static Specification<Train> hasType(TrainType type) {
        return (root, query, builder) -> builder.equal(root.get("trainType"), type);
    }
}

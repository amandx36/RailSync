package com.amandx36.trainmanagement.controller;


import com.amandx36.trainmanagement.dto.reponse.TrainResponse;
import com.amandx36.trainmanagement.dto.request.TrainRequest;
import com.amandx36.trainmanagement.dto.request.TrainUpdateRequest;
import com.amandx36.trainmanagement.enums.TrainType;
import com.amandx36.trainmanagement.services.ADMINTrainManagement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("api/v1")
@AllArgsConstructor
public class AdminTrainManagementController {

    private  final ADMINTrainManagement adminTrainManagement;
    @PostMapping("/add-train")
    public ResponseEntity<TrainResponse> register(
            @Valid @RequestBody TrainRequest Trainrequest
    ){
        TrainResponse trainResponse = adminTrainManagement.createTrain(Trainrequest);
        return ResponseEntity.ok(trainResponse);
    }

    @GetMapping("/trains")
    public ResponseEntity<Page<TrainResponse>> findTrains(
            @RequestParam(required = false) String source,
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) TrainType type,
            @PageableDefault(size = 20, sort = "trainName") Pageable pageable
    ) {
        return ResponseEntity.ok(adminTrainManagement.findTrains(source, destination, type, pageable));
    }

    @GetMapping("/trains/{id}")
    public ResponseEntity<TrainResponse> getTrain(@PathVariable Long id) {
        return ResponseEntity.ok(adminTrainManagement.getTrain(id));
    }

    @PutMapping("/trains/{id}")
    public ResponseEntity<TrainResponse> updateTrain(
            @PathVariable Long id,
            @Valid @RequestBody TrainUpdateRequest request
    ) {
        return ResponseEntity.ok(adminTrainManagement.updateTrain(id, request));
    }

    @DeleteMapping("/trains/{id}")
    public ResponseEntity<Void> deleteTrain(@PathVariable Long id) {
        adminTrainManagement.deleteTrain(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/trains/{id}/cancel")
    public ResponseEntity<TrainResponse> cancelTrain(@PathVariable Long id) {
        return ResponseEntity.ok(adminTrainManagement.cancelTrain(id));
    }

    @PatchMapping("/trains/{id}/maintenance")
    public ResponseEntity<TrainResponse> setMaintenance(@PathVariable Long id) {
        return ResponseEntity.ok(adminTrainManagement.setMaintenance(id));
    }

}

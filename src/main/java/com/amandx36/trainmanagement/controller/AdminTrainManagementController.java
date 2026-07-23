package com.amandx36.trainmanagement.controller;


import com.amandx36.trainmanagement.dto.reponse.RegisterResponse;
import com.amandx36.trainmanagement.dto.reponse.TrainResponse;
import com.amandx36.trainmanagement.dto.request.RegisterRequest;
import com.amandx36.trainmanagement.dto.request.TrainRequest;
import com.amandx36.trainmanagement.services.ADMINTrainManagement;
import com.amandx36.trainmanagement.services.impl.AdminTrainManagementImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/admin")
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


}

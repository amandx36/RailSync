package com.amandx36.trainmanagement;

import com.amandx36.trainmanagement.entity.Train;
import com.amandx36.trainmanagement.enums.TrainStatus;
import com.amandx36.trainmanagement.enums.TrainType;
import com.amandx36.trainmanagement.repository.TrainRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdminTrainManagementControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanDatabase() {
        trainRepository.deleteAll();
    }

    private String buildCreateRequestJson(int trainNumber, String trainName, String source, String destination,
                                         TrainType type, int totalSeats) throws Exception {
        return objectMapper.writeValueAsString(new java.util.LinkedHashMap<String, Object>() {{
            put("trainNumber", trainNumber);
            put("trainName", trainName);
            put("trainType", type.name());
            put("sourceStation", source);
            put("destinationStation", destination);
            put("totalSeats", totalSeats);
        }});
    }

    private Train saveTrain(int trainNumber, String trainName, String source, String destination,
                            TrainType type, int totalSeats) {
        Train train = new Train();
        train.setTrainNumber(trainNumber);
        train.setTrainName(trainName);
        train.setTrainType(type);
        train.setSourceStation(source);
        train.setDestinationStation(destination);
        train.setTotalSeats(totalSeats);
        train.setAvailableSeats(totalSeats);
        train.setStatus(TrainStatus.ACTIVE);
        return trainRepository.save(train);
    }

    @Test
    void shouldCreateTrain() throws Exception {
        String requestBody = buildCreateRequestJson(
                12951,
                "Rajdhani Express",
                "Delhi",
                "Mumbai",
                TrainType.EXPRESS,
                500
        );

        mockMvc.perform(post("/api/v1/add-train")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trainNumber").value(12951))
                .andExpect(jsonPath("$.trainName").value("Rajdhani Express"))
                .andExpect(jsonPath("$.trainType").value("EXPRESS"))
                .andExpect(jsonPath("$.sourceStation").value("Delhi"))
                .andExpect(jsonPath("$.destinationStation").value("Mumbai"));
    }

    @Test
    void shouldGetAllTrains() throws Exception {
        saveTrain(1001, "Shatabdi Express", "Delhi", "Jaipur", TrainType.SUPERFAST, 240);
        saveTrain(1002, "Duronto Express", "Mumbai", "Delhi", TrainType.EXPRESS, 300);

        mockMvc.perform(get("/api/v1/trains"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)));
    }

    @Test
    void shouldGetTrainById() throws Exception {
        Train saved = saveTrain(2001, "Garib Rath", "Patna", "Delhi", TrainType.EXPRESS, 420);

        mockMvc.perform(get("/api/v1/trains/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId().intValue()))
                .andExpect(jsonPath("$.trainName").value("Garib Rath"));
    }

    @Test
    void shouldUpdateTrain() throws Exception {
        Train saved = saveTrain(3001, "Tejas Express", "Mumbai", "Pune", TrainType.EXPRESS, 250);

        String updateBody = buildCreateRequestJson(
                3009,
                "Tejas Express Updated",
                "Mumbai",
                "Goa",
                TrainType.SUPERFAST,
                320
        );

        mockMvc.perform(put("/api/v1/trains/{id}", saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trainNumber").value(3009))
                .andExpect(jsonPath("$.trainName").value("Tejas Express Updated"))
                .andExpect(jsonPath("$.destinationStation").value("Goa"))
                .andExpect(jsonPath("$.trainType").value("SUPERFAST"));
    }

    @Test
    void shouldDeleteTrain() throws Exception {
        Train saved = saveTrain(4001, "Rajya Rani", "Bhopal", "Indore", TrainType.PASSENGER, 180);

        mockMvc.perform(delete("/api/v1/trains/{id}", saved.getId()))
                .andExpect(status().isNoContent());

        org.assertj.core.api.Assertions.assertThat(trainRepository.findById(saved.getId())).isEmpty();
    }

    @Test
    void shouldCancelTrain() throws Exception {
        Train saved = saveTrain(5001, "Karnavati Express", "Ahmedabad", "Mumbai", TrainType.EXPRESS, 310);

        mockMvc.perform(patch("/api/v1/trains/{id}/cancel", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELLED"));
    }

    @Test
    void shouldSetMaintenance() throws Exception {
        Train saved = saveTrain(6001, "Vande Bharat", "Delhi", "Lucknow", TrainType.SUPERFAST, 360);

        mockMvc.perform(patch("/api/v1/trains/{id}/maintenance", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("MAINTENANCE"));
    }

    @Test
    void shouldFilterBySource() throws Exception {
        saveTrain(7001, "Express One", "Delhi", "Jaipur", TrainType.EXPRESS, 200);
        saveTrain(7002, "Express Two", "Lucknow", "Delhi", TrainType.PASSENGER, 150);

        mockMvc.perform(get("/api/v1/trains").param("source", "Delhi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)));
    }

    @Test
    void shouldFilterByDestination() throws Exception {
        saveTrain(8001, "Express One", "Delhi", "Mumbai", TrainType.EXPRESS, 240);
        saveTrain(8002, "Express Two", "Bengaluru", "Mumbai", TrainType.LOCAL, 180);

        mockMvc.perform(get("/api/v1/trains").param("destination", "Mumbai"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)));
    }

    @Test
    void shouldFilterByType() throws Exception {
        saveTrain(9001, "Express One", "Delhi", "Amritsar", TrainType.EXPRESS, 200);
        saveTrain(9002, "Express Two", "Delhi", "Lucknow", TrainType.SUPERFAST, 220);

        mockMvc.perform(get("/api/v1/trains").param("type", "EXPRESS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    void shouldHandlePagination() throws Exception {
        saveTrain(10001, "Train A", "Delhi", "Agra", TrainType.EXPRESS, 200);
        saveTrain(10002, "Train B", "Delhi", "Mathura", TrainType.EXPRESS, 200);
        saveTrain(10003, "Train C", "Delhi", "Kanpur", TrainType.EXPRESS, 200);

        mockMvc.perform(get("/api/v1/trains").param("page", "0").param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.totalElements").value(3));
    }
}
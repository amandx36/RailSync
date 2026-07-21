package com.amandx36.trainmanagement.Controller;


import com.amandx36.trainmanagement.AuthService.AuthService;
import com.amandx36.trainmanagement.dto.reponse.RegisterResponse;
import com.amandx36.trainmanagement.dto.request.RegisterRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private   AuthService authService ;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request
    ){
      RegisterResponse registerResponse = authService.register(request);
      return ResponseEntity.ok(registerResponse);
    }


}

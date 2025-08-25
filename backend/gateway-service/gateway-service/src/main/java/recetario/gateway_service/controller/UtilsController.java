package recetario.gateway_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/utils")
public class UtilsController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.getOrDefault("email", "user@example.com");

        return ResponseEntity.ok(Map.of(
            "userId", 1,
            "message", "Login ok",
            "email", email
        ));
    }
}
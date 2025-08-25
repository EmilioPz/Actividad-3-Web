// src/main/java/recetario/operator_service/controller/RecipeController.java
package recetario.operator_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import recetario.operator_service.RecipeService;
import recetario.operator_service.entities.Recipe;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recetas")
public class RecipeController {

    private static final Logger log = LoggerFactory.getLogger(RecipeController.class);

    private final RecipeService recetaService;
    private final RestTemplate restTemplate;

    public RecipeController(RecipeService recetaService, RestTemplate restTemplate) {
        this.recetaService = recetaService;
        this.restTemplate = restTemplate;
    }

    // 👇 NUEVO: listar todas (tu Home hace GET /api/recetas)
    @GetMapping
    public ResponseEntity<List<Recipe>> listarRecetas() {
        return ResponseEntity.ok(recetaService.listar());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> crearReceta(@RequestBody Recipe receta) {
        Recipe nueva = recetaService.agregarReceta(receta);

        try {
            String searchUrl = "http://search-service:8081/recetas"; // ajusta si corresponde
            restTemplate.postForEntity(searchUrl, nueva, Void.class);
        } catch (Exception ex) {
            log.warn("No se pudo notificar a search-service: {}", ex.getMessage());
        }

        Map<String, Object> body = new HashMap<>();
        body.put("recetaId", nueva.getId());
        body.put("message", "Receta creada");

        return ResponseEntity
                .created(URI.create("/api/recetas/" + nueva.getId()))
                .body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> obtenerReceta(@PathVariable Long id) {
        return recetaService.obtenerRecetaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/test")
    public String test() {
        return "Hola mundo 2 operator";
    }
}
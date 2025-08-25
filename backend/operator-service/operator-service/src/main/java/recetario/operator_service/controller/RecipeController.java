package recetario.operator_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import recetario.operator_service.RecipeService;
import recetario.operator_service.entities.Recipe;

@RestController
@RequestMapping("/api/recetas")
public class RecipeController {

    private final RecipeService recetaService;
    private final RestTemplate restTemplate;

    public RecipeController(RecipeService recetaService, RestTemplate restTemplate) {
        this.recetaService = recetaService;
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public ResponseEntity<Recipe> crearReceta(@RequestBody Recipe receta) {
        Recipe nuevaReceta = recetaService.agregarReceta(receta);
        restTemplate.postForObject(
                "http://search-service/recetas", // Eureka/Gateway resuelve
                nuevaReceta,
                Recipe.class
        );
        return ResponseEntity.ok(nuevaReceta);
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

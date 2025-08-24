package recetario.operator_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recetario.operator_service.RecipeService;
import recetario.operator_service.entities.Recipe;

@RestController
@RequestMapping("/api/recetas")
public class RecipeController {

    private final RecipeService recetaService;

    public RecipeController(RecipeService recetaService) {
        this.recetaService = recetaService;
    }

    @PostMapping
    public ResponseEntity<Recipe> crearReceta(@RequestBody Recipe receta) {
        Recipe nuevaReceta = recetaService.agregarReceta(receta);
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

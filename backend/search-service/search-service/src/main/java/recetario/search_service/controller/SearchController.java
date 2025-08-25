package recetario.search_service.controller;

import org.springframework.web.bind.annotation.*;
import recetario.search_service.RecipeService;
import recetario.search_service.entities.Recipe;

import java.util.List;

@RestController
@RequestMapping("/recetas")
public class SearchController {
    private final RecipeService service;

    public SearchController(RecipeService service) {
        this.service = service;
    }

    @PostMapping
    public Recipe guardar(@RequestBody Recipe receta) {
        return service.save(receta);
    }

    @GetMapping("/buscar")
    public List<Recipe> buscarNombre(@RequestParam String texto) {
        return service.search(texto);
    }

    @GetMapping
    public Iterable<Recipe> listar() {
        return service.list();
    }
}
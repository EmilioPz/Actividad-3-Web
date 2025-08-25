package recetario.search_service;

import org.springframework.stereotype.Service;
import recetario.search_service.entities.Recipe;
import recetario.search_service.repository.RecipeRepository;

import java.util.List;

@Service
public class RecipeService {
    private final RecipeRepository repo;

    public RecipeService(RecipeRepository repo) {
        this.repo = repo;
    }

    public Recipe save(Recipe recipe) {
        return repo.save(recipe);
    }

    public List<Recipe> search(String nombre) {
        return repo.search(nombre);
    }


    public Iterable<Recipe> list() {
        return repo.findAll();
    }
}


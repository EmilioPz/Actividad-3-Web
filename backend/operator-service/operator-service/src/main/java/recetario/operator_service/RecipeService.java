package recetario.operator_service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import recetario.operator_service.entities.Recipe;
import recetario.operator_service.repository.RecipeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;


    public Recipe agregarReceta(Recipe r) {
        return recipeRepository.save(r);
    }

    public Optional<Recipe> obtenerRecetaPorId(Long id) {
        return recipeRepository.findById(id);
    }

    public List<Recipe> listar() {
        return recipeRepository.findAll();
    }
}


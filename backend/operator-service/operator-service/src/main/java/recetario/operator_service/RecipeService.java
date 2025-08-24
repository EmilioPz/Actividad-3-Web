package recetario.operator_service;

import org.springframework.stereotype.Service;
import recetario.operator_service.entities.Recipe;
import recetario.operator_service.repository.RecipeRepository;

import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recetaRepository;

    public RecipeService(RecipeRepository recetaRepository) {
        this.recetaRepository = recetaRepository;
    }

    public Recipe agregarReceta(Recipe receta) {
        return recetaRepository.save(receta);
    }

    public Optional<Recipe> obtenerRecetaPorId(Long id) {
        return recetaRepository.findById(id);
    }
}


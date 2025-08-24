package recetario.operator_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import recetario.operator_service.entities.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}

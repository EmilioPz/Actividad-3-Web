package recetario.search_service.repository;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import recetario.search_service.entities.Recipe;

import java.util.List;

public interface RecipeRepository extends ElasticsearchRepository<Recipe, Long> {
    @Query("{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"nombre\", \"instrucciones\", \"tiempo\", \"ingredientes\"]}}")
    List<Recipe> search(String texto);

}
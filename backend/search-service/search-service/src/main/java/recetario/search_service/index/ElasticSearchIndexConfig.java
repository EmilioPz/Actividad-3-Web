package recetario.search_service.index;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.document.Document;

import java.util.Map;

@Configuration
public class ElasticSearchIndexConfig {

    private final ElasticsearchOperations operations;

    public ElasticSearchIndexConfig(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createIndexIfNotExists() {
        String indexName = "recetas";
        IndexOperations indexOps = operations.indexOps(IndexCoordinates.of(indexName));

        if (!indexOps.exists()) {
            // Settings (puedes añadir un analyzer edge_ngram si luego haces autocomplete)
            Document settings = Document.create();
            settings.put("index", Map.of(
                    "number_of_shards", 1,
                    "number_of_replicas", 0
            ));

            Document mapping = Document.parse("""
            {
              "properties": {
                "id":           { "type": "long" },
                "nombre":       { "type": "search_as_you_type"},
                "instrucciones":{ "type": "text" },
                "tiempo":       { "type": "keyword" },
                "imagen":       { "type": "keyword" }
              }
            }
            """);

            indexOps.create(settings);
            indexOps.putMapping(mapping);
        }
    }
}

package initializers;

import initializers.schemaBuilders.HeroSchemaBuilder;
import models.*;
import repository.SchemaDefRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
class DevelopmentInitializer {
    private SchemaDefRepository schemaDefRepository;
    private List<SchemaDef> storedSchemaDefList;

    @Inject
    public DevelopmentInitializer(SchemaDefRepository schemaDefRepository) {
        this.schemaDefRepository = schemaDefRepository;
        this.storedSchemaDefList = this.schemaDefRepository.getAll();
        this.init();
    }
    
    private void init() {
        List<SchemaBuilder> schemaBuilders = Collections.singletonList(
                new HeroSchemaBuilder()
        );

        List<SchemaDef> schemaDefList = schemaBuilders.parallelStream()
                .filter(x -> !x.schemaExist(this.storedSchemaDefList))
                .map(SchemaBuilder::buildSchema)
                .collect(Collectors.toList());

        schemaDefList.parallelStream()
                .forEach(this.schemaDefRepository::save);
    }
}


package initializers;

import com.avaje.ebean.Model;
import models.*;
import repository.SchemaDefRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class DevelopmentInitializer {
    private SchemaDefRepository schemaDefRepository;
    private List<SchemaDef> schemaDefList;

    @Inject
    public DevelopmentInitializer(SchemaDefRepository schemaDefRepository) {
        this.schemaDefRepository = schemaDefRepository;
        this.schemaDefList = this.schemaDefRepository.getAll();
        this.init();
    }


    private void init() {
        List<SchemaDef> schemaDefs = new ArrayList<>();
        List<SchemaBuilder> schemaBuilders = new ArrayList<>();

        schemaBuilders.parallelStream().forEach(schemaBuilder -> {
            if (!schemaBuilder.schemaExist(schemaDefs)) {
                schemaDefList.add(schemaBuilder.buildSchema());
            }
        });


        schemaDefs.forEach(Model::save);
    }
}


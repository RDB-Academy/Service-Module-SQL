package services;

import models.SchemaDef;
import models.TableDef;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Http;
import repository.SchemaDefRepository;
import services.tools.ServiceError;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * @author fabiomazzone
 */
@Singleton
public class SchemaDefService {
    private SchemaDefRepository schemaDefRepository;
    private FormFactory formFactory;

    @Inject
    public SchemaDefService(SchemaDefRepository schemaDefRepository, FormFactory formFactory) {
        this.schemaDefRepository = schemaDefRepository;
        this.formFactory = formFactory;
    }

    public Form<SchemaDef> getCreateForm() {
        return this.formFactory.form(SchemaDef.class);
    }

    public Form<TableDef> getCreateTableDefForm(long id) {
        SchemaDef schemaDef = this.schemaDefRepository.getById(id);
        TableDef tableDef = new TableDef();

        if(schemaDef == null) {
            return null;
        }

        tableDef.setSchemaDef(schemaDef);

        return this.formFactory.form(TableDef.class).fill(tableDef);
    }

    public Form<SchemaDef> getSchemaDefForm(Long id) {
        SchemaDef schemaDef = this.schemaDefRepository.getById(id);

        if(schemaDef == null) {
            return null;
        }

        return this.formFactory.form(SchemaDef.class).fill(schemaDef);
    }

    public Form<SchemaDef> getNewSchemaDef(Http.Request request) {
        Form<SchemaDef> schemaDefForm = this.formFactory.form(SchemaDef.class).bindFromRequest(request);

        if (schemaDefForm.hasErrors()) {
            return schemaDefForm;
        }

        SchemaDef schemaDef = schemaDefForm.get();
        this.schemaDefRepository.save(schemaDef);

        return schemaDefForm.fill(schemaDef);
    }

    public ServiceError deleteSchemaDef(Long id) {
        SchemaDef schemaDef = this.schemaDefRepository.getById(id);

        if(schemaDef == null) {
            return new ServiceError(ServiceError.NotFound, "SchemaDef", id);
        }

        schemaDef.delete();

        return null;
    }

    public List<SchemaDef> getSchemaDefList() {
        return this.schemaDefRepository.getAll();
    }
}

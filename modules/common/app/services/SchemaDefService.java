package services;

import models.SchemaDef;
import models.TableDef;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Http;
import repository.SchemaDefRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * @author fabiomazzone
 */
@Singleton
public class SchemaDefService extends Service {
    private SchemaDefRepository schemaDefRepository;
    private FormFactory formFactory;

    @Inject
    public SchemaDefService(SchemaDefRepository schemaDefRepository, FormFactory formFactory) {
        this.schemaDefRepository = schemaDefRepository;
        this.formFactory = formFactory;
    }


    /**
     * This function returns a new Creation Form for a new schemaDef
     * @return a new Creation Form for a new schemaDef
     */
    public Form<SchemaDef> createAsForm() {
        return this.formFactory.form(SchemaDef.class);
    }

    public Form<SchemaDef> create(Http.Request request) {
        Form<SchemaDef> schemaDefForm = this.formFactory.form(SchemaDef.class).bindFromRequest(request);

        if (schemaDefForm.hasErrors()) {
            return schemaDefForm;
        }

        SchemaDef schemaDef = schemaDefForm.get();
        this.schemaDefRepository.save(schemaDef);

        return schemaDefForm.fill(schemaDef);
    }

    public List<SchemaDef> readAll() {
        return this.schemaDefRepository.getAll();
    }

    public Form<SchemaDef> readAsForm(Long id) {
        SchemaDef schemaDef = this.schemaDefRepository.getById(id);

        if(schemaDef == null) {
            return null;
        }

        return this.formFactory.form(SchemaDef.class).fill(schemaDef);
    }

    public Form<SchemaDef> update(Long id) {
        SchemaDef schemaDef = this.schemaDefRepository.getById(id);
        Form<SchemaDef> schemaDefForm = this.formFactory.form(SchemaDef.class).bindFromRequest();

        if(schemaDef == null) {
            schemaDefForm.reject(Service.formErrorNotFound, "Schema Not Found");
            return schemaDefForm;
        }

        if(schemaDefForm.hasErrors()) {
            return schemaDefForm;
        }

        if(schemaDefForm.get().getName() == null || schemaDefForm.get().getName().isEmpty()) {
            schemaDefForm.reject("Name", "SchemaDef must be named");
            return schemaDefForm;
        }

        schemaDef.setName(schemaDefForm.get().getName());

        this.schemaDefRepository.save(schemaDef);

        return schemaDefForm;
    }

    public Form<SchemaDef> delete(Long id) {
        SchemaDef schemaDef = this.schemaDefRepository.getById(id);
        Form<SchemaDef> schemaDefForm = this.formFactory.form(SchemaDef.class);

        if(schemaDef == null) {
            schemaDefForm.reject(Service.formErrorNotFound, "Schema Not Found");
            return schemaDefForm;
        }

        schemaDef.delete();

        return schemaDefForm;
    }

/* ****************************************************************************************************************** *\
|  --- Not SchemaDef Related Stuff
\* ****************************************************************************************************************** */

    /**
     * This action returns a new Form for an TableDef;
     * @param id the id of the schemaDef object
     *
     * @return returns a new Form
     */
    public Form<TableDef> getCreateTableDefForm(long id) {
        SchemaDef schemaDef = this.schemaDefRepository.getById(id);
        TableDef tableDef = new TableDef();

        if(schemaDef == null) {
            return null;
        }

        tableDef.setSchemaDef(schemaDef);

        return this.formFactory.form(TableDef.class).fill(tableDef);
    }
}

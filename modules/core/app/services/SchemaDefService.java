package services;

import com.fasterxml.jackson.databind.JsonNode;
import models.SchemaDef;
import models.TableDef;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Http;
import repository.SchemaDefRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author fabiomazzone
 */
@Singleton
public class SchemaDefService extends Service {
    private final SchemaDefRepository schemaDefRepository;
    private final FormFactory formFactory;

    @Inject
    public SchemaDefService(
            SchemaDefRepository schemaDefRepository,
            FormFactory formFactory) {
        this.schemaDefRepository = schemaDefRepository;
        this.formFactory = formFactory;
    }

    /**
     * Returns a new SchemaDefModel
     *
     * @return the new created SchemaDefModel
     */
    public Form<SchemaDef> create(@NotNull Form<SchemaDef> schemaDefForm) {
        if(schemaDefForm.hasErrors())
        {
            return schemaDefForm;
        }
        SchemaDef schemaDef = schemaDefForm.get();

        if(schemaDefRepository.getByName(schemaDef.getName()) != null)
        {
            schemaDefForm.reject("name", "name already taken");
            return schemaDefForm;
        }

        this.schemaDefRepository.save(schemaDef);

        return schemaDefForm.fill(schemaDef);
    }

    public SchemaDef read(Long id) {
        return this.schemaDefRepository.getById(id);
    }

    public List<SchemaDef> readAll() {
        return this.schemaDefRepository.getAll();
    }

    public Form<SchemaDef> update(Long id) {
        SchemaDef schemaDef             = this.read(id);
        Form<SchemaDef> schemaDefForm   = this.getForm().bindFromRequest();

        if(schemaDef == null) {
            schemaDefForm.reject(Service.formErrorNotFound, "SchemaDef Not Found");
            return schemaDefForm;
        }

        schemaDefForm.discardErrors();

        JsonNode schemaDefPatchNode = Http.Context.current().request().body().asJson();

        if(schemaDefPatchNode.has("name") && schemaDefPatchNode.get("name").isTextual()) {
            String name = schemaDefPatchNode.get("name").asText();
            if (!name.isEmpty()) {
                schemaDef.setName(name);
            }
        }

        if(schemaDefPatchNode.has("available") && schemaDefPatchNode.get("available").isBoolean()) {
            boolean available  = schemaDefPatchNode.get("available").asBoolean();
            schemaDef.setAvailable(available);
        }

        this.schemaDefRepository.save(schemaDef);

        return schemaDefForm;
    }

    public Form<SchemaDef> delete(Long id) {
        SchemaDef schemaDef = this.read(id);
        Form<SchemaDef> schemaDefForm = this.getForm();

        if(schemaDef == null) {
            schemaDefForm.reject(Service.formErrorNotFound, "SchemaDef Not Found");
            return schemaDefForm;
        }

        schemaDef.delete();

        return schemaDefForm;
    }

    private Form<SchemaDef> getForm() {
        return this.formFactory.form(SchemaDef.class);
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
        SchemaDef schemaDef = this.read(id);
        TableDef tableDef = new TableDef();

        if(schemaDef == null) {
            return null;
        }

        tableDef.setSchemaDef(schemaDef);

        return this.formFactory.form(TableDef.class).fill(tableDef);
    }
}

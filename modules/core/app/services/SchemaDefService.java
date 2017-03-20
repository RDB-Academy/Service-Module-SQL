package services;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.ForeignKey;
import models.SchemaDef;
import models.TableDef;
import models.Task;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import repository.SchemaDefRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.constraints.NotNull;
import java.time.format.DateTimeFormatter;

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

    public SchemaDef read(Long id)
    {
        return this.schemaDefRepository.getById(id);
    }

    public ObjectNode transformBase(SchemaDef schemaDef)
    {
        ObjectNode schemaDefNode = Json.newObject();

        schemaDefNode.put("id", schemaDef.getId());
        schemaDefNode.put("name", schemaDef.getName());
        schemaDefNode.put("available", schemaDef.isAvailable());

        // schemaDefNode.set("reactions", reactionNode());

        schemaDefNode.put("createdAt", schemaDef.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME));
        schemaDefNode.put("modifiedAt", schemaDef.getModifiedAt().format(DateTimeFormatter.ISO_DATE_TIME));

        return schemaDefNode;
    }

    private ObjectNode transformTableDefBase(TableDef tableDef)
    {
        ObjectNode tableDefNode = Json.newObject();

        tableDefNode.put("id", tableDef.getId());
        tableDefNode.put("name", tableDef.getName());

        return tableDefNode;
    }

    public ObjectNode transform(SchemaDef schemaDef)
    {
        ObjectNode schemaDefNode = transformBase(schemaDef);
        ObjectNode relationNode = Json.newObject();

        ArrayNode tableDefIds = Json.newArray();
        ArrayNode foreignKeyIds = Json.newArray();
        ArrayNode taskIds = Json.newArray();

        schemaDef.getTableDefList().stream().map(this::transformTableDefBase).forEach(tableDefIds::add);
        schemaDef.getForeignKeyList().stream().map(ForeignKey::getId).forEach(foreignKeyIds::add);
        schemaDef.getTaskList().stream().map(Task::getId).forEach(taskIds::add);

        relationNode.set("tableDefList", tableDefIds);
        relationNode.set("foreignKeyList", foreignKeyIds);
        relationNode.set("taskList", taskIds);

        schemaDefNode.set("relations", relationNode);

        return schemaDefNode;
    }
}

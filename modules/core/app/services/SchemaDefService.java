package services;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.ForeignKey;
import models.SchemaDef;
import models.Task;
import play.libs.Json;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.format.DateTimeFormatter;

/**
 * @author fabiomazzone
 */
@Singleton
public class SchemaDefService extends Service
{
    private final TableDefService tableDefService;

    @Inject
    public SchemaDefService(TableDefService tableDefService) {
        this.tableDefService = tableDefService;
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

    public ObjectNode transform(SchemaDef schemaDef)
    {
        ObjectNode schemaDefNode = transformBase(schemaDef);
        ObjectNode relationNode = Json.newObject();

        ArrayNode tableDef = Json.newArray();
        ArrayNode foreignKeyIds = Json.newArray();
        ArrayNode taskIds = Json.newArray();

        schemaDef.getTableDefList().stream().map(tableDefService::transformBase).forEach(tableDef::add);
        schemaDef.getForeignKeyList().stream().map(ForeignKey::getId).forEach(foreignKeyIds::add);
        schemaDef.getTaskList().stream().map(Task::getId).forEach(taskIds::add);

        relationNode.set("tableDefList", tableDef);
        relationNode.set("foreignKeyList", foreignKeyIds);
        relationNode.set("taskList", taskIds);

        schemaDefNode.set("relations", relationNode);

        return schemaDefNode;
    }
}

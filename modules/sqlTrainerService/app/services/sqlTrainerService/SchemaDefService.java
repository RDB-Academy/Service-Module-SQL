package services.sqlTrainerService;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.sqlTrainerService.ForeignKey;
import models.sqlTrainerService.SchemaDef;
import models.sqlTrainerService.Task;
import play.libs.Json;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.format.DateTimeFormatter;

/**
 * @author fabiomazzone
 */
@Singleton
public class SchemaDefService {
    private final TableDefService tableDefService;
    private final ForeignKeyService foreignKeyService;

    @Inject
    public SchemaDefService(
            TableDefService tableDefService,
            ForeignKeyService foreignKeyService
    ) {
        this.tableDefService = tableDefService;
        this.foreignKeyService = foreignKeyService;
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
        schemaDef.getForeignKeyList().stream().map(foreignKeyService::transformBase).forEach(foreignKeyIds::add);
        schemaDef.getTaskList().stream().map(Task::getId).forEach(taskIds::add);

        schemaDefNode.put("tableCount", schemaDef.getTableDefList().size());
        schemaDefNode.put("foreignKeyCount", schemaDef.getForeignKeyList().size());
        schemaDefNode.put("tasksCount", schemaDef.getTaskList().size());

        relationNode.set("tableDefList", tableDef);
        relationNode.set("foreignKeyList", foreignKeyIds);
        relationNode.set("taskList", taskIds);

        schemaDefNode.set("relations", relationNode);

        return schemaDefNode;
    }
}

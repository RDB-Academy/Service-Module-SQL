package services;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.ForeignKey;
import models.SchemaDef;
import models.TableDef;
import models.Task;
import play.libs.Json;

import javax.inject.Singleton;
import java.time.format.DateTimeFormatter;

/**
 * @author fabiomazzone
 */
@Singleton
public class SchemaDefService extends Service
{

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

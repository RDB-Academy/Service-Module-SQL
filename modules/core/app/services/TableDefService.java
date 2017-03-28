package services;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.ColumnDef;
import models.TableDef;
import play.libs.Json;

import javax.inject.Singleton;
import java.time.format.DateTimeFormatter;

/**
 * @author fabiomazzone
 */
@Singleton
public class TableDefService extends Service
{
    public ObjectNode transform(TableDef tableDef)
    {
        ObjectNode tableDefNode = Json.newObject();

        ArrayNode columnIds = Json.newArray();

        tableDef.getColumnDefList().parallelStream().map(ColumnDef::getId).forEach(columnIds::add);

        tableDefNode.put("id", tableDef.getId());
        tableDefNode.put("name", tableDef.getName());

        tableDefNode.put("schemaDefId", tableDef.getSchemaDef().getId());

        tableDefNode.set("columnDefList", columnIds);

        tableDefNode.put("createdAt", tableDef.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME));
        tableDefNode.put("modifiedAt", tableDef.getModifiedAt().format(DateTimeFormatter.ISO_DATE_TIME));

        return tableDefNode;
    }
}

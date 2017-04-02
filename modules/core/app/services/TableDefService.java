package services;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.TableDef;
import play.libs.Json;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.format.DateTimeFormatter;

/**
 * @author fabiomazzone
 */
@Singleton
public class TableDefService extends Service
{
    private final ColumnDefService columnDefService;

    @Inject
    public TableDefService(
            ColumnDefService columnDefService
    ) {
        this.columnDefService = columnDefService;
    }


    public ObjectNode transformBase(TableDef tableDef)
    {
        ObjectNode tableDefNode = Json.newObject();

        tableDefNode.put("id", tableDef.getId());
        tableDefNode.put("name", tableDef.getName());
        tableDefNode.put("schemaDefId", tableDef.getSchemaDef().getId());

        tableDefNode.put("createdAt", tableDef.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME));
        tableDefNode.put("modifiedAt", tableDef.getModifiedAt().format(DateTimeFormatter.ISO_DATE_TIME));

        return tableDefNode;
    }

    public ObjectNode transform(TableDef tableDef)
    {
        ObjectNode tableDefNode = Json.newObject();

        ArrayNode columnIds = Json.newArray();

        tableDef.getColumnDefList().parallelStream().map(this.columnDefService::transformBase).forEach(columnIds::add);

        tableDefNode.put("id", tableDef.getId());
        tableDefNode.put("name", tableDef.getName());

        tableDefNode.put("schemaDefId", tableDef.getSchemaDef().getId());

        tableDefNode.set("columnDefList", columnIds);

        tableDefNode.put("createdAt", tableDef.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME));
        tableDefNode.put("modifiedAt", tableDef.getModifiedAt().format(DateTimeFormatter.ISO_DATE_TIME));

        return tableDefNode;
    }
}

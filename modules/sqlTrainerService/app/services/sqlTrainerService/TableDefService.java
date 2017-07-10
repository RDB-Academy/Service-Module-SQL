package services.sqlTrainerService;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.sqlTrainerService.ColumnDef;
import models.sqlTrainerService.TableDef;
import play.libs.Json;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author fabiomazzone
 */
@Singleton
public class TableDefService {
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
        tableDefNode.put("columnDefListSize", tableDef.getColumnDefList().size());

        tableDefNode.put("createdAt", tableDef.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME));
        tableDefNode.put("modifiedAt", tableDef.getModifiedAt().format(DateTimeFormatter.ISO_DATE_TIME));

        return tableDefNode;
    }

    public ObjectNode transform(TableDef tableDef)
    {
        ObjectNode tableDefNode = this.transformBase(tableDef);
        List<ColumnDef> columnDefList = tableDef.getColumnDefList();
        columnDefList.sort(ColumnDef::compareTo);

        ArrayNode columnIds = Json.newArray();

        columnDefList.stream().map(this.columnDefService::transformBase).forEach(columnIds::add);

        tableDefNode.set("columnDefList", columnIds);

        return tableDefNode;
    }
}

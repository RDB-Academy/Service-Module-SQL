package services;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.ColumnDef;
import play.libs.Json;

import javax.inject.Singleton;
import java.time.format.DateTimeFormatter;

/**
 * @author Fabio Mazzone
 */
@Singleton
public class ColumnDefService {
    public ObjectNode transform(ColumnDef columnDef) {
        ObjectNode columnDefNode = Json.newObject();

        columnDefNode.put("id", columnDef.getId());
        columnDefNode.put("name", columnDef.getName());

        columnDefNode.put("tableDefId", columnDef.getTableDef().getId());

        columnDefNode.put("dataType", columnDef.getDataType());
        columnDefNode.put("isPrimaryKey", columnDef.isPrimary());
        columnDefNode.put("isNotNull", columnDef.isNotNull());
        columnDefNode.put("MetaValueSet", columnDef.getMetaValueSetName());

        columnDefNode.put("createdAt", columnDef.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME));
        columnDefNode.put("modifiedAt", columnDef.getModifiedAt().format(DateTimeFormatter.ISO_DATE_TIME));

        return columnDefNode;
    }
}
package services;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Singleton;
import models.ForeignKey;
import models.ForeignKeyRelation;
import play.libs.Json;

import java.time.format.DateTimeFormatter;

/**
 * @author fabiomazzone
 */
@Singleton
public class ForeignKeyService
{
    public ObjectNode transform(ForeignKey foreignKey)
    {
        ObjectNode foreignKeyNode = Json.newObject();
        ArrayNode foreignKeyRelationArray = Json.newArray();

        foreignKeyNode.put("id", foreignKey.getId());
        foreignKeyNode.put("name", foreignKey.getName());

        if(foreignKey.getForeignKeyRelationList().size() > 0) {
            foreignKey.getForeignKeyRelationList().parallelStream().map(ForeignKeyRelation::getId).forEach(foreignKeyRelationArray::add);

            ForeignKeyRelation foreignKeyRelation = foreignKey.getForeignKeyRelationList().get(0);

            Long sourceColumn = foreignKeyRelation.getSourceColumn().getTableDef().getId();
            Long targetColumn = foreignKeyRelation.getTargetColumn().getTableDef().getId();

            foreignKeyNode.put("sourceTable", sourceColumn);
            foreignKeyNode.put("targetTable", targetColumn);
        }

        foreignKeyNode.set("foreignKeyRelationList", foreignKeyRelationArray);
        foreignKeyNode.put("createdAt", foreignKey.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME));
        foreignKeyNode.put("modifiedAt", foreignKey.getModifiedAt().format(DateTimeFormatter.ISO_DATE_TIME));

        return foreignKeyNode;
    }
}

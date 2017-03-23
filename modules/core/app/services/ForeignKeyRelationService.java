package services;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.ForeignKeyRelation;
import play.libs.Json;

import javax.inject.Singleton;
import java.time.format.DateTimeFormatter;

/**
 * @author Fabio Mazzone
 */
@Singleton
public class ForeignKeyRelationService
{
    public ObjectNode transform(ForeignKeyRelation foreignKeyRelation)
    {
        ObjectNode foreignKeyRelNode = Json.newObject();

        foreignKeyRelNode.put("id", foreignKeyRelation.getId());
        foreignKeyRelNode.put("foreignKey", foreignKeyRelation.getForeignKey().getId());
        foreignKeyRelNode.put("sourceColumn", foreignKeyRelation.getSourceColumn().getId());
        foreignKeyRelNode.put("targetColumn", foreignKeyRelation.getTargetColumn().getId());
        foreignKeyRelNode.put("createdAt", foreignKeyRelation.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME));
        foreignKeyRelNode.put("modifiedAt", foreignKeyRelation.getModifiedAt().format(DateTimeFormatter.ISO_DATE_TIME));

        return foreignKeyRelNode;
    }
}

package services;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.ForeignKey;
import models.ForeignKeyRelation;
import play.libs.Json;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author fabiomazzone
 */
@Singleton
public class ForeignKeyService
{
    private final ForeignKeyRelationService foreignKeyRelationService;

    @Inject
    public ForeignKeyService(
            ForeignKeyRelationService foreignKeyRelationService
    ) {
        this.foreignKeyRelationService = foreignKeyRelationService;
    }

    /**
     *
     * @param foreignKey
     * @return
     */
    public ObjectNode transformBase(ForeignKey foreignKey) {
        ObjectNode foreignKeyNode = Json.newObject();

        String sourceTable = "";
        String targetTable = "";

        if(foreignKey.getForeignKeyRelationList().size() > 0) {
            ForeignKeyRelation foreignKeyRelation = foreignKey.getForeignKeyRelationList().get(0);
            sourceTable = foreignKeyRelation.getSourceColumn().getTableDef().getName();
            targetTable = foreignKeyRelation.getTargetColumn().getTableDef().getName();
        }

        foreignKeyNode.put("id", foreignKey.getId());
        foreignKeyNode.put("name", foreignKey.getName());
        foreignKeyNode.put("schemaDefId", foreignKey.getSchemaDef().getId());

        foreignKeyNode.put("sourceTable", sourceTable);
        foreignKeyNode.put("targetTable", targetTable);

        foreignKeyNode.put("foreignKeyRelationListSize", foreignKey.getForeignKeyRelationList().size());
        foreignKeyNode.put("createdAt", foreignKey.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME));
        foreignKeyNode.put("modifiedAt", foreignKey.getModifiedAt().format(DateTimeFormatter.ISO_DATE_TIME));

        return foreignKeyNode;
    }

    /**
     *
     * @param foreignKey
     * @return
     */
    public ObjectNode transform(ForeignKey foreignKey)
    {
        ObjectNode foreignKeyNode = transformBase(foreignKey);
        List<ForeignKeyRelation> foreignKeyRelationList = foreignKey.getForeignKeyRelationList();
        ArrayNode foreignKeyRelationArray = Json.newArray();

        foreignKeyNode.put("id", foreignKey.getId());
        foreignKeyNode.put("name", foreignKey.getName());


        foreignKeyRelationList
                .stream()
                .map(this.foreignKeyRelationService::transformBase)
                .forEach(foreignKeyRelationArray::add);

        foreignKeyNode.set("foreignKeyRelationList", foreignKeyRelationArray);
        foreignKeyNode.put("createdAt", foreignKey.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME));
        foreignKeyNode.put("modifiedAt", foreignKey.getModifiedAt().format(DateTimeFormatter.ISO_DATE_TIME));

        return foreignKeyNode;
    }
}

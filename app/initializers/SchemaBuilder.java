package initializers;

import models.SchemaDef;

import java.util.List;

/**
 * @author fabiomazzone
 */
public interface SchemaBuilder {
    boolean schemaExist(List<SchemaDef> schemaDefList);

    SchemaDef buildSchema();
}

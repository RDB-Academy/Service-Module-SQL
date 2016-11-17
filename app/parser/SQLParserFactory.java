package parser;

import models.SchemaDef;
import models.Session;

/**
 * @author fabiomazzone
 */
public interface SQLParserFactory {
    SQLParser createDatabase(SchemaDef schemaDef, Session session);
}

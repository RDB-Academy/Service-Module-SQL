package models;

import java.util.List;
import java.util.ArrayList;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * @author gabrielahlers
 */

public class TableDefTest {
  @Test
  public void testGetId() {
    TableDef testTableDef = new TableDef();
    //ToDo requires setId();
    assertTrue("TableDefId", testTableDef.getId() == null); // has to be != null
  }

  @Test
  public void testGetName() {
    TableDef testTableDef = new TableDef();
    testTableDef.setName("testTableDef");

    assertEquals("getName", "testTableDef", testTableDef.getName());
  }

  @Test
  public void testGetShemaDef() {
    TableDef testTableDef = new TableDef();
    SchemaDef testSchemaDef = new SchemaDef();

    testTableDef.setSchemaDef(testSchemaDef);

    assertEquals("schemaDef", testSchemaDef, testTableDef.getSchemaDef());

  }

  @Test
  public void testGetSchemaDefId() {
    TableDef testTableDef = new TableDef();

    Long id = 1L;

    testTableDef.setSchemaDefId(id);

    assertEquals("schemaDefId", Long.valueOf(1), testTableDef.getSchemaDefId());
  }

  @Test
  public void testGetColumnDefList() {
    TableDef testTableDef = new TableDef();
    List<ColumnDef> testColumnDef = new ArrayList<>();

    testTableDef.setColumnDefList(testColumnDef);

    assertEquals("ColumnDefList", testColumnDef, testTableDef.getColumnDefList());
  }


 }

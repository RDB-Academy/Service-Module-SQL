package initializers.schemaBuilders;

import initializers.SchemaBuilder;
import models.ColumnDef;
import models.SchemaDef;
import models.TableDef;
import models.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nicolenaczk
 */
public class UsersSchemaBuilder extends SchemaBuilder{

    @Override
    public String getSchemaName() {
        return "Users";
    }

    @Override
    public SchemaDef buildSchema() {
        SchemaDef   user_schema             = this.createNewSchemaDef();
        TableDef    user                    = this.createNewTableDef("user");
        TableDef    location                = this.createNewTableDef("location");
        ColumnDef   user_firstname          = this.createNewColumnDef("firstname", "VARCHAR(255)");
        ColumnDef   user_lastname           = this.createNewColumnDef("lastname", "VARCHAR(255)");
        ColumnDef   location_name           = this.createNewColumnDef("name", "VARCHAR(255)");
        ColumnDef   location_description    = this.createNewColumnDef("description","VARCHAR(255)");


        user_firstname.setPrimary(true);
        user_firstname.setNotNull(true);
        user_firstname.setMetaValueSet(ColumnDef.META_VALUE_SET_FIRSTNAME);

        user_lastname.setNotNull(true);
        user_firstname.setMetaValueSet(ColumnDef.META_VALUE_SET_LASTNAME);

        location_name.setPrimary(true);
        location_name.setNotNull(true);
        location_name.setMetaValueSet(ColumnDef.META_VALUE_SET_LOCATION);

        location_description.setNotNull(true);
        location_name.setMetaValueSet(ColumnDef.META_VALUE_SET_LOREM_IPSUM);

        user.addColumnDef(user_firstname);
        user.addColumnDef(user_lastname);

        location.addColumnDef(location_name);
        location.addColumnDef(location_description);

        user_schema.addTableDef(user);
        user_schema.addTableDef(location);

        return user_schema;
    }

    @Override
    protected List<Task> buildTasks() {
        List<Task> taskList = new ArrayList<>();

        Task task = new Task();
        task.setName("Find Fabio");
        task.setText("Find Fabio");
        task.setReferenceStatement("SELECT * FROM User");
        taskList.add(task);

        Task task1 = new Task();
        task1.setName("Find Sören");
        task1.setText("Find Sören");
        task1.setReferenceStatement("SELECT * FROM User WHERE firstname = 'Soeren'");
        taskList.add(task1);

        Task task2 = new Task();
        task2.setName("Find Caaarl");
        task2.setText("Find Caaarl");
        task2.setReferenceStatement("SELECT * FROM User WHERE firstname = 'Caaarl'");
        taskList.add(task2);

        return taskList;
    }
}

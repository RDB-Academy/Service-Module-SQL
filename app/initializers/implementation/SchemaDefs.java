package initializers.implementation;

import models.ColumnDef;
import models.SchemaDef;
import models.TableDef;
import repository.TaskRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolenaczk on 11.11.16.
 */
class SchemaDefs {

    static void initDev(TaskRepository taskRepository) {

        /**
         * Schema with User and Location
         * */


        SchemaDef users_schema = new SchemaDef();
        users_schema.setName("Users");
        users_schema.save();

        TableDef users = new TableDef();
        users.setName("Users");
        users.setSchemaDef(users_schema);
        users.save();

        ColumnDef firstname = new ColumnDef();
        firstname.setName("firstname");
        firstname.setDatatype("VARCHAR(255)");
        firstname.setTableDef(users);
        firstname.setPrimary(true);
        firstname.setNullable(false);
        firstname.save();

        ColumnDef lastname = new ColumnDef();
        lastname.setName("lastname");
        lastname.setDatatype("VARCHAR(255)");
        lastname.setTableDef(users);
        lastname.setPrimary(false);
        lastname.setNullable(true);
        lastname.save();

        TableDef location = new TableDef();
        location.setName("Location");
        location.setSchemaDef(users_schema);
        location.save();

        ColumnDef name = new ColumnDef();
        name.setName("name");
        name.setDatatype("VARCHAR(255)");
        name.setTableDef(location);
        name.setPrimary(true);
        name.setNullable(false);
        name.save();

        ColumnDef description = new ColumnDef();
        description.setName("description");
        description.setDatatype("VARCHAR(255)");
        description.setTableDef(location);
        description.setPrimary(false);
        description.setNullable(true);
        description.save();


        taskRepository.getAll().forEach(task -> {
            task.setSchemaDef(users_schema);
            taskRepository.save(task);
        });

        /**
         * Schema Bookstore
         *

        TableDef book      = new TableDef();
        TableDef publisher = new TableDef();
        TableDef genre     = new TableDef();

        List<TableDef> bookstoreTables = new ArrayList<>();
        bookstoreTables.add(book);
        bookstoreTables.add(publisher);
        bookstoreTables.add(genre);

        SchemaDef bookstoreSchema = new SchemaDef();
        bookstoreSchema.setName("Bookstore");
        bookstoreSchema.setTableDefList(bookstoreTables);
        bookstoreSchema.save();*/

    }
}

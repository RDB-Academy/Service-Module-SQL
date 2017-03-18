package initializers.schemaBuilders;

import initializers.SchemaBuilder;
import models.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ronja on 01.12.16.
 */
public class LibrarySchemaBuilder extends SchemaBuilder {
    @Override
    protected String getSchemaName() {
        return "LibrarySchema";
    }

    @Override
    protected SchemaDef buildSchema() {
        SchemaDef           customerBookSchema           = this.createNewSchemaDef();
        TableDef            customer                     = this.createNewTableDef("customer");
        TableDef            book                         = this.createNewTableDef("book");
        TableDef            customerBook                 = this.createNewTableDef("customer_book");
        ColumnDef           customer_customer_id         = this.createNewColumnDef("id", "INT");
        ColumnDef           customer_customer_firstname  = this.createNewColumnDef("firstname", "VARCHAR(255)");
        ColumnDef           customer_customer_lastname   = this.createNewColumnDef("lastname", "VARCHAR(255)");
        ColumnDef           book_book_id                 = this.createNewColumnDef("id", "INT");
        ColumnDef           book_book_name               = this.createNewColumnDef("name", "VARCHAR(255)");
        ColumnDef           customerBook_customer_id     = this.createNewColumnDef("customer_id", "INT");
        ColumnDef           customerBook_book_id         = this.createNewColumnDef("book_id", "INT");
        ColumnDef           customerBook_lend_date       = this.createNewColumnDef("lend_date", "DATE");
        ColumnDef           customerBook_return_date     = this.createNewColumnDef("return_date", "DATE");

        ForeignKey          customerBook_customer        = this.createForeignKey("FK_CustomerBook_Customer");
        ForeignKey          customerBook_book            = this.createForeignKey("FK_CustomerBook_Book");
        ForeignKeyRelation  customerBook_customer_rel    = this.createForeignKeyRelation(customerBook_customer_id, customer_customer_id);
        ForeignKeyRelation  customerBook_book_rel        = this.createForeignKeyRelation(customerBook_book_id, book_book_id);

        customer_customer_id.setPrimary(true);
        customer_customer_id.setNotNull(true);
        customer_customer_firstname.setNotNull(true);
        customer_customer_lastname.setNotNull(true);
        customer_customer_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        customer_customer_firstname.setMetaValueSet(ColumnDef.META_VALUE_SET_FIRSTNAME);
        customer_customer_lastname.setMetaValueSet(ColumnDef.META_VALUE_SET_LASTNAME);

        book_book_id.setPrimary(true);
        book_book_id.setNotNull(true);
        book_book_name.setNotNull(true);
        book_book_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        book_book_name.setMetaValueSet(ColumnDef.META_VALUE_SET_TITLE);

        customerBook_book_id.setPrimary(true);
        customerBook_customer_id.setPrimary(true);
        customerBook_book_id.setNotNull(true);
        customerBook_customer_id.setNotNull(true);
        customerBook_lend_date.setNotNull(true);
        customerBook_return_date.setNotNull(true);
        customerBook_book_id.setMetaValueSet(ColumnDef.META_VALUE_SET_FOREIGN_KEY);
        customerBook_customer_id.setMetaValueSet(ColumnDef.META_VALUE_SET_FOREIGN_KEY);
        customerBook_lend_date.setMetaValueSet(ColumnDef.META_VALUE_SET_DATE);
        customerBook_return_date.setMetaValueSet(ColumnDef.META_VALUE_SET_DATE);

        customer.addColumnDef(customer_customer_id);
        customer.addColumnDef(customer_customer_firstname);
        customer.addColumnDef(customer_customer_lastname);

        book.addColumnDef(book_book_id);
        book.addColumnDef(book_book_name);

        customerBook.addColumnDef(customerBook_customer_id);
        customerBook.addColumnDef(customerBook_book_id);
        customerBook.addColumnDef(customerBook_lend_date);
        customerBook.addColumnDef(customerBook_return_date);

        customerBook_customer.addForeignKeyRelation(customerBook_customer_rel);
        customerBook_book.addForeignKeyRelation(customerBook_book_rel);

        customerBookSchema.addTableDef(customer);
        customerBookSchema.addTableDef(book);
        customerBookSchema.addTableDef(customerBook);

        customerBookSchema.addForeignKey(customerBook_customer);
        customerBookSchema.addForeignKey(customerBook_book);

        return customerBookSchema;
    }

    @Override
    protected List<Task> buildTasks() {
        List<Task> taskList = new ArrayList<>();

        Task task = new Task();
        task.setText("List all customers by their ID and the amount of books they have purchased.");
        task.setReferenceStatement("SELECT customer_id, count(book_id) FROM customer_book GROUP BY customer_id ORDER BY count(book_id) DESC;");
        task.setDifficulty(2);
        taskList.add(task);

        task = new Task();
        task.setText("Which customers (id,firstname,lastname) have bought more than 2 books.");
        task.setReferenceStatement("Select id, firstname, lastname FROM customer as c Join (SELECT customer_id\n" +
                " FROM customer_book \n" +
                " GROUP BY customer_id\n" +
                " HAVING count(book_id) > 1) as co ON c.id=co.customer_id;");
        task.setDifficulty(4);
        taskList.add(task);

        task = new Task();
        task.setText("What is the title of the book that has been borrowed the most often?");
        task.setReferenceStatement("SELECT b.name\n" +
                " FROM book as b \n" +
                " JOIN customer_book as cb ON b.id = cb.book_id \n" +
                " GROUP BY b.name\n" +
                " ORDER BY count(cb.customer_id) desc" +
                " Fetch first 1 row only;");
        task.setDifficulty(3);
        taskList.add(task);




        return taskList;
    }
}

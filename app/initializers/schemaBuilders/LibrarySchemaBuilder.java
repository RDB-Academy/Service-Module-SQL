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
        task.setText("What is the average number of books any customer has purchased? List all customers and there average number.");
        task.setReferenceStatement("SELECT customer_id, count(book_id) FROM customer_book Group by customer_id order by count(book_id) desc;");
        taskList.add(task);

        task = new Task();
        task.setText("What is the title of the book that have been purchased the most often?");
        task.setReferenceStatement("SELECT b.name, count(cb.customer_id) \n" +
                " FROM book as b \n" +
                " Join customer_book as cb ON b.id = cb.book_id \n" +
                " Group by b.name\n" +
                " order by count(cb.customer_id) desc;");
        taskList.add(task);




        return taskList;
    }
}

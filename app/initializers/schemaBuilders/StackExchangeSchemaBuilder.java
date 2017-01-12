package initializers.schemaBuilders;

import initializers.SchemaBuilder;
import models.*;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ronja on 15.12.16.
 */
public class StackExchangeSchemaBuilder extends SchemaBuilder {

    @Override
    protected String getSchemaName() {
        return "StackExchangeSchema";
    }

    @Override
    protected SchemaDef buildSchema() {
        SchemaDef           StackExchangeSchema         = this.createNewSchemaDef();
        TableDef            user                        = this.createNewTableDef("user");
        TableDef            post                        = this.createNewTableDef("post");
        TableDef            comment                     = this.createNewTableDef("comment");
        TableDef            badge                       = this.createNewTableDef("badge");
        TableDef            postHistoryTypes            = this.createNewTableDef("postHistoryTypes");
        TableDef            postHistory                 = this.createNewTableDef("postHistory");
        TableDef            tags                        = this.createNewTableDef("tags");
        TableDef            postTags                    = this.createNewTableDef("postTags");

        //columns for user table
        ColumnDef           user_user_id                = this.createNewColumnDef("user_id", "INT");
        ColumnDef           user_user_reputation        = this.createNewColumnDef("user_reputation", "INT");
        ColumnDef           user_user_display_name       = this.createNewColumnDef("user_display_name", "VARCHAR(255)");
        ColumnDef           user_user_day               = this.createNewColumnDef("user_day", "INT");
        ColumnDef           user_user_month             = this.createNewColumnDef("user_month", "INT");
        ColumnDef           user_user_year              = this.createNewColumnDef("user_year", "INT");
        ColumnDef           user_user_location          = this.createNewColumnDef("user_location", "VARCHAR(255)");
        ColumnDef           user_user_upvotes           = this.createNewColumnDef("user_upvotes", "INT");
        ColumnDef           user_user_downvotes         = this.createNewColumnDef("user_downvotes", "INT");
        ColumnDef           user_user_age               = this.createNewColumnDef("user_age","INT");

        //columns for post table
        ColumnDef           post_post_id                = this.createNewColumnDef("post_id", "INT");
        ColumnDef           post_post_type              = this.createNewColumnDef("post_type", "INT");
        ColumnDef           post_post_title             = this.createNewColumnDef("post_title", "VARCHAR(255)");
        ColumnDef           post_post_accepted_answer   = this.createNewColumnDef("post_accepted_answer", "INT");
        ColumnDef           post_post_parent_id         = this.createNewColumnDef("post_parent_id", "INT");
        ColumnDef           post_post_owner_id          = this.createNewColumnDef("post_owner_id", "INT");
        ColumnDef           post_post_score             = this.createNewColumnDef("post_score", "INT");
        ColumnDef           post_post_day               = this.createNewColumnDef("post_day", "INT");
        ColumnDef           post_post_month             = this.createNewColumnDef("post_month", "INT");
        ColumnDef           post_post_year              = this.createNewColumnDef("post_year", "INT");

        //columns for comment table
        ColumnDef           comment_comment_id          = this.createNewColumnDef("comment_id", "INT");
        ColumnDef           comment_comment_user_id     = this.createNewColumnDef("comment_user_id", "INT");
        ColumnDef           comment_comment_post_id     = this.createNewColumnDef("comment_post_id", "INT");
        ColumnDef           comment_comment_score       = this.createNewColumnDef("comment_score", "INT");
        ColumnDef           comment_comment_text        = this.createNewColumnDef("comment_text", "VARcHAR(255)");
        ColumnDef           comment_comment_day         = this.createNewColumnDef("comment_day", "INT");
        ColumnDef           comment_comment_month       = this.createNewColumnDef("comment_month", "INT");
        ColumnDef           comment_comment_year        = this.createNewColumnDef("comment_year", "INT");

        //columns for badge table
        ColumnDef           badge_badge_id              = this.createNewColumnDef("badge_name", "INT");
        ColumnDef           bagde_badge_user_id         = this.createNewColumnDef("badge_user_id", "INT");
        ColumnDef           badge_badge_user_id         = this.createNewColumnDef("badge_user_id", "INT");
        ColumnDef           badge_badge_name            = this.createNewColumnDef("badge_name", "VARCHAR(255)");
        ColumnDef           badge_badge_day             = this.createNewColumnDef("badge_day", "INT");
        ColumnDef           badge_badge_month           = this.createNewColumnDef("badge_month", "INT");
        ColumnDef           badge_badge_year            = this.createNewColumnDef("badge_year", "INT");

        //columns for posthistorytype
        ColumnDef           postHistoryType_postHistoryType_id      = this.createNewColumnDef("postHistorytype_id", "INT");
        ColumnDef           postHistoryType_postHistoryType_name    = this.createNewColumnDef("postHistorytype_name", "VARCHAR(255)");

        //columns for posthistory
        ColumnDef           postHistory_postHistory_id              = this.createNewColumnDef("postHistory_id", "INT");
        ColumnDef           postHistory_postHistory_type_id         = this.createNewColumnDef("postHistory_type_id", "INT");
        ColumnDef           postHistory_postHistory_post_id         = this.createNewColumnDef("postHistory_post_id", "INT");
        ColumnDef           postHistory_postHistory_user_id         = this.createNewColumnDef("postHistory_user_id", "INT");
        ColumnDef           postHistory_postHistory_day             = this.createNewColumnDef("postHistory_day", "INT");
        ColumnDef           postHistory_postHistory_month           = this.createNewColumnDef("postHistory_month", "INT");
        ColumnDef           postHistory_postHistory_year            = this.createNewColumnDef("postHistory_year", "INT");

        //columns for tag
        ColumnDef           tag_tag_id                  = this.createNewColumnDef("tag_id", "INT");
        ColumnDef           tag_tag_name                = this.createNewColumnDef("tag_name", "VARCHAR(255)");

        //columns for postTags
        ColumnDef           postTag_postTag_post_id     = this.createNewColumnDef("postTag_post_id", "INT");
        ColumnDef           postTag_postTag_tag_id      = this.createNewColumnDef("postTag_tag_id", "INT");


        //foreign keys noch aus den columns raus? oder wie referenzieren?


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
        book_book_name.setMetaValueSet(ColumnDef.META_VALUE_SET_NAME);

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

        Task bibleTask = new Task();

        bibleTask.setName("Find the Bible");
        bibleTask.setText("Find the Bible");
        bibleTask.setReferenceStatement("SELECT * FROM book WHERE book_name = \"Bible\";");

        taskList.add(bibleTask);

        return taskList;
    }
}

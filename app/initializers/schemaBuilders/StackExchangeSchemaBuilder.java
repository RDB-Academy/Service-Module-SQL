package initializers.schemaBuilders;

import initializers.SchemaBuilder;
import models.*;

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
        SchemaDef           stackExchangeSchema    = this.createNewSchemaDef();
        TableDef            user                   = this.createNewTableDef("user");
        TableDef            post                   = this.createNewTableDef("post");
        TableDef            comment                = this.createNewTableDef("comment");
        TableDef            badge                  = this.createNewTableDef("badge");
        TableDef            postHistoryType        = this.createNewTableDef("postHistoryType");
        TableDef            postHistory            = this.createNewTableDef("postHistory");
        TableDef            tag                    = this.createNewTableDef("tag");
        TableDef            postTag                = this.createNewTableDef("postTag");

        //columns for user table
        ColumnDef           user_id                = this.createNewColumnDef("id", "INT");
        ColumnDef           user_reputation        = this.createNewColumnDef("reputation", "VARCHAR(255)");
        ColumnDef           user_displayName       = this.createNewColumnDef("displayName", "VARCHAR(255)");
        ColumnDef           user_day               = this.createNewColumnDef("day", "INT");
        ColumnDef           user_month             = this.createNewColumnDef("month", "INT");
        ColumnDef           user_year              = this.createNewColumnDef("year", "INT");
        ColumnDef           user_location          = this.createNewColumnDef("location", "VARCHAR(255)");
        ColumnDef           user_upvotes           = this.createNewColumnDef("upvotes", "INT");
        ColumnDef           user_downvotes         = this.createNewColumnDef("downvotes", "INT");
        ColumnDef           user_age               = this.createNewColumnDef("age","INT");

        //columns for post table
        ColumnDef           post_id                = this.createNewColumnDef("id", "INT");
        ColumnDef           post_type              = this.createNewColumnDef("type", "INT");
        ColumnDef           post_title             = this.createNewColumnDef("title", "VARCHAR(255)");
        ColumnDef           post_acceptedAnswer    = this.createNewColumnDef("acceptedAnswer", "BOOLEAN");
        ColumnDef           post_parent_id         = this.createNewColumnDef("parent_id", "INT");
        ColumnDef           post_owner_id          = this.createNewColumnDef("owner_id", "INT");
        ColumnDef           post_score             = this.createNewColumnDef("score", "INT");
        ColumnDef           post_day               = this.createNewColumnDef("day", "INT");
        ColumnDef           post_month             = this.createNewColumnDef("month", "INT");
        ColumnDef           post_year              = this.createNewColumnDef("year", "INT");

        //columns for comment table
        ColumnDef           comment_id             = this.createNewColumnDef("id", "INT");
        ColumnDef           comment_user_id        = this.createNewColumnDef("user_id", "INT");
        ColumnDef           comment_post_id        = this.createNewColumnDef("post_id", "INT");
        ColumnDef           comment_score          = this.createNewColumnDef("score", "INT");
        ColumnDef           comment_text           = this.createNewColumnDef("text", "VARcHAR(255)");
        ColumnDef           comment_day            = this.createNewColumnDef("day", "INT");
        ColumnDef           comment_month          = this.createNewColumnDef("month", "INT");
        ColumnDef           comment_year           = this.createNewColumnDef("year", "INT");

        //columns for badge table
        ColumnDef           badge_id               = this.createNewColumnDef("id", "INT");
        ColumnDef           badge_user_id          = this.createNewColumnDef("user_id", "INT");
        ColumnDef           badge_name             = this.createNewColumnDef("name", "VARCHAR(255)");
        ColumnDef           badge_day              = this.createNewColumnDef("day", "INT");
        ColumnDef           badge_month            = this.createNewColumnDef("month", "INT");
        ColumnDef           badge_year             = this.createNewColumnDef("year", "INT");

        //columns for posthistorytype
        ColumnDef           postHistoryType_id     = this.createNewColumnDef("id", "INT");
        ColumnDef           postHistoryType_name   = this.createNewColumnDef("name", "VARCHAR(255)");
        //columns for posthistory
        ColumnDef           postHistory_id         = this.createNewColumnDef("id", "INT");
        ColumnDef           postHistory_type_id    = this.createNewColumnDef("type_id", "INT");
        ColumnDef           postHistory_post_id    = this.createNewColumnDef("post_id", "INT");
        ColumnDef           postHistory_user_id    = this.createNewColumnDef("user_id", "INT");
        ColumnDef           postHistory_day        = this.createNewColumnDef("day", "INT");
        ColumnDef           postHistory_month      = this.createNewColumnDef("month", "INT");
        ColumnDef           postHistory_year       = this.createNewColumnDef("year", "INT");

        //columns for tag
        ColumnDef           tag_id                 = this.createNewColumnDef("id", "INT");
        ColumnDef           tag_name               = this.createNewColumnDef("name", "VARCHAR(255)");

        //columns for postTags
        ColumnDef           postTag_post_id        = this.createNewColumnDef("post_id", "INT");
        ColumnDef           postTag_tag_id         = this.createNewColumnDef("tag_id", "INT");

        //foreign keys
        ForeignKey          post_owner             = this.createForeignKey("FK_Post_Post_Owner");
        ForeignKeyRelation  post_owner_rel         = this.createForeignKeyRelation(post_owner_id, user_id);
        ForeignKey          comment_user           = this.createForeignKey("FK_Comment_Comment_User");
        ForeignKey          comment_post           = this.createForeignKey("FK_Comment_Comment_Post");
        ForeignKeyRelation  comment_user_rel       = this.createForeignKeyRelation(comment_user_id, user_id);
        ForeignKeyRelation  comment_post_rel       = this.createForeignKeyRelation(comment_post_id, post_id );
        ForeignKey          badge_user             = this.createForeignKey("FK_Badges_Badges_User");
        ForeignKeyRelation  badge_user_rel         = this.createForeignKeyRelation(badge_user_id, user_id);
        ForeignKey          postHistory_type       = this.createForeignKey("FK_PostHistory_PostHistory_Type");
        ForeignKey          postHistory_post       = this.createForeignKey("FK_PostHistory_PostHistory_Post");
        ForeignKey          postHistory_user       = this.createForeignKey("PostHistory_PostHistory_User");
        ForeignKeyRelation  postHistory_type_rel   = this.createForeignKeyRelation(postHistory_type_id, postHistoryType_id);
        ForeignKeyRelation  postHistory_post_rel   = this.createForeignKeyRelation(postHistory_post_id, post_id);
        ForeignKeyRelation  postHistory_user_rel   = this.createForeignKeyRelation(postHistory_user_id, user_id);
        ForeignKey          postTag_post           = this.createForeignKey("FK_PostTag_PostTag_Post");
        ForeignKey          postTag_tag            = this.createForeignKey("FK_PostTag_PostTag_Tag");
        ForeignKeyRelation  postTag_post_rel       = this.createForeignKeyRelation(postTag_post_id, post_id);
        ForeignKeyRelation  postTag_tag_rel        = this.createForeignKeyRelation(postTag_tag_id, tag_name);

        user_id.setPrimary(true);
        user_id.setNotNull(true);
        user_reputation.setNotNull(true);
        user_displayName.setNotNull(true);
        user_location.setNotNull(true);
        user_day.setNotNull(true);
        user_month.setNotNull(true);
        user_year.setNotNull(true);
        user_age.setNotNull(true);
        user_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        user_reputation.setMetaValueSet(ColumnDef.META_VALUE_SET_METAL);
        user_displayName.setMetaValueSet(ColumnDef.META_VALUE_SET_NAME);
        user_day.setMetaValueSet(ColumnDef.META_VALUE_SET_DAY);
        user_month.setMetaValueSet(ColumnDef.META_VALUE_SET_MONTH);
        user_year.setMetaValueSet(ColumnDef.META_VALUE_SET_YEAR);
        user_location.setMetaValueSet(ColumnDef.META_VALUE_SET_CITY);
        user_upvotes.setMinValueSet(0);
        user_upvotes.setMaxValueSet(100000);
        user_downvotes.setMinValueSet(0);
        user_downvotes.setMaxValueSet(100000);
        user_age.setMinValueSet(0);
        user_age.setMaxValueSet(120);

        post_id.setPrimary(true);
        post_id.setNotNull(true);
        post_type.setNotNull(true);
        post_title.setNotNull(true);
        post_day.setNotNull(true);
        post_month.setNotNull(true);
        post_year.setNotNull(true);
        post_acceptedAnswer.setNotNull(true);
        post_parent_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        post_score.setMinValueSet(0);
        post_score.setMaxValueSet(100);
        post_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        post_day.setMetaValueSet(ColumnDef.META_VALUE_SET_DAY);
        post_month.setMetaValueSet(ColumnDef.META_VALUE_SET_MONTH);
        post_year.setMetaValueSet(ColumnDef.META_VALUE_SET_YEAR);
        post_owner_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        post_type.setMetaValueSet(ColumnDef.META_VALUE_SET_POSTTYPE);
        post_title.setMetaValueSet(ColumnDef.META_VALUE_SET_TITLE);

        comment_id.setPrimary(true);
        comment_id.setNotNull(true);
        comment_user_id.setNotNull(true);
        comment_post_id.setNotNull(true);
        comment_day.setNotNull(true);
        comment_month.setNotNull(true);
        comment_year.setNotNull(true);
        comment_text.setNotNull(true);
        comment_score.setNotNull(true);
        comment_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        comment_user_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        comment_post_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        comment_day.setMetaValueSet(ColumnDef.META_VALUE_SET_DAY);
        comment_month.setMetaValueSet(ColumnDef.META_VALUE_SET_MONTH);
        comment_year.setMetaValueSet(ColumnDef.META_VALUE_SET_YEAR);
        comment_text.setMetaValueSet(ColumnDef.META_VALUE_SET_LOREM_IPSUM);
        comment_score.setMinValueSet(0);
        comment_score.setMaxValueSet(100);

        badge_id.setPrimary(true);
        badge_id.setNotNull(true);
        badge_day.setNotNull(true);
        badge_month.setNotNull(true);
        badge_year.setNotNull(true);
        badge_name.setNotNull(true);
        badge_user_id.setNotNull(true);
        badge_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        badge_day.setMetaValueSet(ColumnDef.META_VALUE_SET_DAY);
        badge_month.setMetaValueSet(ColumnDef.META_VALUE_SET_MONTH);
        badge_year.setMetaValueSet(ColumnDef.META_VALUE_SET_YEAR);
        badge_name.setMetaValueSet(ColumnDef.META_VALUE_SET_NAME);
        badge_user_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);

        postHistoryType_id.setPrimary(true);
        postHistoryType_id.setNotNull(true);
        postHistoryType_name.setNotNull(true);
        postHistoryType_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        postHistoryType_name.setMetaValueSet(ColumnDef.META_VALUE_SET_NAME);

        postHistory_id.setPrimary(true);
        postHistory_id.setNotNull(true);
        postHistory_day.setNotNull(true);
        postHistory_month.setNotNull(true);
        postHistory_year.setNotNull(true);
        postHistory_post_id.setNotNull(true);
        postHistory_user_id.setNotNull(true);
        postHistory_type_id.setNotNull(true);
        postHistory_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        postHistory_day.setMetaValueSet(ColumnDef.META_VALUE_SET_DAY);
        postHistory_month.setMetaValueSet(ColumnDef.META_VALUE_SET_MONTH);
        postHistory_year.setMetaValueSet(ColumnDef.META_VALUE_SET_YEAR);
        postHistory_post_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        postHistory_user_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        postHistory_type_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);

        tag_id.setPrimary(true);
        tag_id.setPrimary(true);
        tag_name.setNotNull(true);
        tag_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        tag_name.setMetaValueSet(ColumnDef.META_VALUE_SET_NAME);

        postTag_post_id.setPrimary(true);
        postTag_post_id.setNotNull(true);
        postTag_tag_id.setNotNull(true);
        postTag_post_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        postTag_tag_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);

        user.addColumnDef(user_id);
        user.addColumnDef(user_reputation);
        user.addColumnDef(user_displayName);
        user.addColumnDef(user_day);
        user.addColumnDef(user_month);
        user.addColumnDef(user_year);
        user.addColumnDef(user_location);
        user.addColumnDef(user_upvotes);
        user.addColumnDef(user_downvotes);
        user.addColumnDef(user_age);

        post.addColumnDef(post_id);
        post.addColumnDef(post_type);
        post.addColumnDef(post_title);
        post.addColumnDef(post_acceptedAnswer);
        post.addColumnDef(post_parent_id);
        post.addColumnDef(post_owner_id);
        post.addColumnDef(post_score);
        post.addColumnDef(post_day);
        post.addColumnDef(post_month);
        post.addColumnDef(post_year);

        comment.addColumnDef(comment_id);
        comment.addColumnDef(comment_user_id);
        comment.addColumnDef(comment_post_id);
        comment.addColumnDef(comment_score);
        comment.addColumnDef(comment_text);
        comment.addColumnDef(comment_day);
        comment.addColumnDef(comment_month);
        comment.addColumnDef(comment_year);

        badge.addColumnDef(badge_id);
        badge.addColumnDef(badge_user_id);
        badge.addColumnDef(badge_name);
        badge.addColumnDef(badge_day);
        badge.addColumnDef(badge_month);
        badge.addColumnDef(badge_year);

        postHistoryType.addColumnDef(postHistoryType_id);
        postHistoryType.addColumnDef(postHistoryType_name);

        postHistory.addColumnDef(postHistory_id);
        postHistory.addColumnDef(postHistory_type_id);
        postHistory.addColumnDef(postHistory_post_id);
        postHistory.addColumnDef(postHistory_user_id);
        postHistory.addColumnDef(postHistory_day);
        postHistory.addColumnDef(postHistory_month);
        postHistory.addColumnDef(postHistory_year);

        tag.addColumnDef(tag_id);
        tag.addColumnDef(tag_name);

        postTag.addColumnDef(postTag_post_id);
        postTag.addColumnDef(postTag_tag_id);

        post_owner.addForeignKeyRelation(post_owner_rel);

        comment_user.addForeignKeyRelation(comment_user_rel);
        comment_post.addForeignKeyRelation(comment_post_rel);

        badge_user.addForeignKeyRelation(badge_user_rel);

        postHistory_type.addForeignKeyRelation(postHistory_type_rel);
        postHistory_post.addForeignKeyRelation(postHistory_post_rel);
        postHistory_user.addForeignKeyRelation(postHistory_user_rel);

        postTag_post.addForeignKeyRelation(postTag_post_rel);
        postTag_tag.addForeignKeyRelation(postTag_tag_rel);

        stackExchangeSchema.addTableDef(user);
        stackExchangeSchema.addTableDef(post);
        stackExchangeSchema.addTableDef(comment);
        stackExchangeSchema.addTableDef(badge);
        stackExchangeSchema.addTableDef(postHistoryType);
        stackExchangeSchema.addTableDef(postHistory);
        stackExchangeSchema.addTableDef(tag);
        stackExchangeSchema.addTableDef(postTag);

        stackExchangeSchema.addForeignKey(post_owner);
        stackExchangeSchema.addForeignKey(comment_user);
        stackExchangeSchema.addForeignKey(comment_post);
        stackExchangeSchema.addForeignKey(badge_user);
        stackExchangeSchema.addForeignKey(postHistory_type);
        stackExchangeSchema.addForeignKey(postHistory_post);
        stackExchangeSchema.addForeignKey(postHistory_user);
        stackExchangeSchema.addForeignKey(postTag_post);
        stackExchangeSchema.addForeignKey(postTag_tag);

        return stackExchangeSchema;
    }
    @Override
    protected List<Task> buildTasks() {
        List<Task> taskList = new ArrayList<>();

        Task userTask = new Task();

        userTask.setName("Find Tim");
        userTask.setText("Find Tim");
        userTask.setReferenceStatement("SELECT * FROM user WHERE user_displayName = 'Tim';");

        taskList.add(userTask);

        return taskList;
    }
}

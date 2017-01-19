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
        SchemaDef           stackExchangeSchema         = this.createNewSchemaDef();
        TableDef            user                        = this.createNewTableDef("user");
        TableDef            post                        = this.createNewTableDef("post");
        TableDef            comment                     = this.createNewTableDef("comment");
        TableDef            badge                       = this.createNewTableDef("badge");
        TableDef            postHistoryType             = this.createNewTableDef("postHistoryType");
        TableDef            postHistory                 = this.createNewTableDef("postHistory");
        TableDef            tag                         = this.createNewTableDef("tag");
        TableDef            postTag                     = this.createNewTableDef("postTag");

        //columns for user table
        ColumnDef           user_user_id                = this.createNewColumnDef("user_id", "INT");
        ColumnDef           user_user_reputation        = this.createNewColumnDef("user_reputation", "INT");
        ColumnDef           user_user_displayName       = this.createNewColumnDef("user_displayName", "VARCHAR(255)");
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
        ColumnDef           post_post_acceptedAnswer    = this.createNewColumnDef("post_acceptedAnswer", "BOOLEAN");
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
        ColumnDef           badge_badge_user_id         = this.createNewColumnDef("badge_user_id", "INT");
        ColumnDef           badge_badge_name            = this.createNewColumnDef("badge_name", "VARCHAR(255)");
        ColumnDef           badge_badge_day             = this.createNewColumnDef("badge_day", "INT");
        ColumnDef           badge_badge_month           = this.createNewColumnDef("badge_month", "INT");
        ColumnDef           badge_badge_year            = this.createNewColumnDef("badge_year", "INT");

        //columns for posthistorytype
        ColumnDef           postHistoryType_postHistoryType_id      = this.createNewColumnDef("postHistoryType_id", "INT");
        ColumnDef           postHistoryType_postHistoryType_name    = this.createNewColumnDef("postHistoryType_name", "VARCHAR(255)");

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

        //foreign keys
        ForeignKey          post_post_owner                  = this.createForeignKey("FK_Post_Post_Owner");
        ForeignKeyRelation  post_post_owner_rel              = this.createForeignKeyRelation(user_user_id, post_post_owner_id);
        ForeignKey          comment_comment_user             = this.createForeignKey("FK_Comment_Comment_User");
        ForeignKey          comment_comment_post             = this.createForeignKey("FK_Comment_Comment_Post");
        ForeignKeyRelation  comment_comment_user_rel         = this.createForeignKeyRelation(user_user_id, comment_comment_user_id);
        ForeignKeyRelation  comment_comment_post_rel         = this.createForeignKeyRelation(post_post_id, comment_comment_post_id);
        ForeignKey          badge_badge_user                 = this.createForeignKey("FK_Badges_Badges_User");
        ForeignKeyRelation  badge_badge_user_rel             = this.createForeignKeyRelation(user_user_id, badge_badge_user_id);
        ForeignKey          postHistory_postHistory_type     = this.createForeignKey("FK_PostHistory_PostHistory_Type");
        ForeignKey          postHistory_postHistory_post     = this.createForeignKey("FK_PostHistory_PostHistory_Post");
        ForeignKey          postHistory_postHistory_user     = this.createForeignKey("PostHistory_PostHistory_User");
        ForeignKeyRelation  postHistory_postHistory_type_rel = this.createForeignKeyRelation(postHistoryType_postHistoryType_id, postHistory_postHistory_type_id);
        ForeignKeyRelation  postHistory_postHistory_post_rel = this.createForeignKeyRelation(post_post_id, postHistory_postHistory_post_id);
        ForeignKeyRelation  postHistory_postHistory_user_rel = this.createForeignKeyRelation(user_user_id, postHistory_postHistory_user_id);
        ForeignKey          postTag_postTag_post            = this.createForeignKey("FK_PostTag_PostTag_Post");
        ForeignKey          postTag_postTag_tag              = this.createForeignKey("FK_PostTag_PostTag_Tag");
        ForeignKeyRelation  postTag_postTag_post_rel         = this.createForeignKeyRelation(post_post_id, postTag_postTag_post_id);
        ForeignKeyRelation  postTag_postTag_tag_rel          = this.createForeignKeyRelation(tag_tag_name, postTag_postTag_tag_id);

        user_user_id.setPrimary(true);
        user_user_id.setNotNull(true);
        user_user_reputation.setNotNull(true);
        user_user_displayName.setNotNull(true);
        user_user_location.setNotNull(true);
        user_user_day.setNotNull(true);
        user_user_month.setNotNull(true);
        user_user_year.setNotNull(true);
        user_user_age.setNotNull(true);
        user_user_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        user_user_reputation.setMetaValueSet(ColumnDef.META_VALUE_SET_METAL);
        user_user_displayName.setMetaValueSet(ColumnDef.META_VALUE_SET_NAME);
        user_user_day.setMetaValueSet(ColumnDef.META_VALUE_SET_DAY);
        user_user_month.setMetaValueSet(ColumnDef.META_VALUE_SET_MONTH);
        user_user_year.setMetaValueSet(ColumnDef.META_VALUE_SET_YEAR);
        user_user_location.setMetaValueSet(ColumnDef.META_VALUE_SET_CITY);
        user_user_upvotes.setMinValueSet(0);
        user_user_upvotes.setMaxValueSet(100000);
        user_user_downvotes.setMinValueSet(0);
        user_user_downvotes.setMaxValueSet(100000);
        user_user_age.setMinValueSet(0);
        user_user_age.setMaxValueSet(120);

        post_post_id.setPrimary(true);
        post_post_id.setNotNull(true);
        post_post_type.setNotNull(true);
        post_post_title.setNotNull(true);
        post_post_day.setNotNull(true);
        post_post_month.setNotNull(true);
        post_post_year.setNotNull(true);
        post_post_acceptedAnswer.setNotNull(true);
        post_post_parent_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        post_post_score.setMinValueSet(0);
        post_post_score.setMaxValueSet(100);
        post_post_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        post_post_day.setMetaValueSet(ColumnDef.META_VALUE_SET_DAY);
        post_post_month.setMetaValueSet(ColumnDef.META_VALUE_SET_MONTH);
        post_post_year.setMetaValueSet(ColumnDef.META_VALUE_SET_YEAR);
        post_post_owner_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        //post_post_acceptedAnswer.setMetaValueSet();
        //post_post_type.setMetaValueSet();
        //post_post_title.setMetaValueSet();

        comment_comment_id.setPrimary(true);
        comment_comment_id.setNotNull(true);
        comment_comment_user_id.setNotNull(true);
        comment_comment_post_id.setNotNull(true);
        comment_comment_day.setNotNull(true);
        comment_comment_month.setNotNull(true);
        comment_comment_year.setNotNull(true);
        comment_comment_text.setNotNull(true);
        comment_comment_score.setNotNull(true);
        comment_comment_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        comment_comment_user_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        comment_comment_post_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        comment_comment_day.setMetaValueSet(ColumnDef.META_VALUE_SET_DAY);
        comment_comment_month.setMetaValueSet(ColumnDef.META_VALUE_SET_MONTH);
        comment_comment_year.setMetaValueSet(ColumnDef.META_VALUE_SET_YEAR);
        comment_comment_text.setMetaValueSet(ColumnDef.META_VALUE_SET_LOREM_IPSUM);
        comment_comment_score.setMinValueSet(0);
        comment_comment_score.setMaxValueSet(100);

        badge_badge_id.setPrimary(true);
        badge_badge_id.setNotNull(true);
        badge_badge_day.setNotNull(true);
        badge_badge_month.setNotNull(true);
        badge_badge_year.setNotNull(true);
        badge_badge_name.setNotNull(true);
        badge_badge_user_id.setNotNull(true);
        badge_badge_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        badge_badge_day.setMetaValueSet(ColumnDef.META_VALUE_SET_DAY);
        badge_badge_month.setMetaValueSet(ColumnDef.META_VALUE_SET_MONTH);
        badge_badge_year.setMetaValueSet(ColumnDef.META_VALUE_SET_YEAR);
        badge_badge_name.setMetaValueSet(ColumnDef.META_VALUE_SET_NAME);
        badge_badge_user_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);

        postHistoryType_postHistoryType_id.setPrimary(true);
        postHistoryType_postHistoryType_id.setNotNull(true);
        postHistoryType_postHistoryType_name.setNotNull(true);
        postHistoryType_postHistoryType_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        postHistoryType_postHistoryType_name.setMetaValueSet(ColumnDef.META_VALUE_SET_NAME);

        postHistory_postHistory_id.setPrimary(true);
        postHistory_postHistory_id.setNotNull(true);
        postHistory_postHistory_day.setNotNull(true);
        postHistory_postHistory_month.setNotNull(true);
        postHistory_postHistory_year.setNotNull(true);
        postHistory_postHistory_post_id.setNotNull(true);
        postHistory_postHistory_user_id.setNotNull(true);
        postHistory_postHistory_type_id.setNotNull(true);
        postHistory_postHistory_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        postHistory_postHistory_day.setMetaValueSet(ColumnDef.META_VALUE_SET_DAY);
        postHistory_postHistory_month.setMetaValueSet(ColumnDef.META_VALUE_SET_MONTH);
        postHistory_postHistory_year.setMetaValueSet(ColumnDef.META_VALUE_SET_YEAR);
        postHistory_postHistory_post_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        postHistory_postHistory_user_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        postHistory_postHistory_type_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);

        tag_tag_id.setPrimary(true);
        tag_tag_id.setPrimary(true);
        tag_tag_name.setNotNull(true);
        tag_tag_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        tag_tag_name.setMetaValueSet(ColumnDef.META_VALUE_SET_NAME);

        postTag_postTag_post_id.setPrimary(true);
        postTag_postTag_post_id.setNotNull(true);
        postTag_postTag_tag_id.setNotNull(true);
        postTag_postTag_post_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        postTag_postTag_tag_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);

        user.addColumnDef(user_user_id);
        user.addColumnDef(user_user_reputation);
        user.addColumnDef(user_user_displayName);
        user.addColumnDef(user_user_day);
        user.addColumnDef(user_user_month);
        user.addColumnDef(user_user_year);
        user.addColumnDef(user_user_location);
        user.addColumnDef(user_user_upvotes);
        user.addColumnDef(user_user_downvotes);
        user.addColumnDef(user_user_age);

        post.addColumnDef(post_post_id);
        post.addColumnDef(post_post_type);
        post.addColumnDef(post_post_title);
        post.addColumnDef(post_post_acceptedAnswer);
        post.addColumnDef(post_post_parent_id);
        post.addColumnDef(post_post_owner_id);
        post.addColumnDef(post_post_score);
        post.addColumnDef(post_post_day);
        post.addColumnDef(post_post_month);
        post.addColumnDef(post_post_year);

        comment.addColumnDef(comment_comment_id);
        comment.addColumnDef(comment_comment_user_id);
        comment.addColumnDef(comment_comment_post_id);
        comment.addColumnDef(comment_comment_score);
        comment.addColumnDef(comment_comment_text);
        comment.addColumnDef(comment_comment_day);
        comment.addColumnDef(comment_comment_month);
        comment.addColumnDef(comment_comment_year);

        badge.addColumnDef(badge_badge_id);
        badge.addColumnDef(badge_badge_user_id);
        badge.addColumnDef(badge_badge_name);
        badge.addColumnDef(badge_badge_day);
        badge.addColumnDef(badge_badge_month);
        badge.addColumnDef(badge_badge_year);

        postHistoryType.addColumnDef(postHistoryType_postHistoryType_id);
        postHistoryType.addColumnDef(postHistoryType_postHistoryType_name);

        postHistory.addColumnDef(postHistory_postHistory_id);
        postHistory.addColumnDef(postHistory_postHistory_type_id);
        postHistory.addColumnDef(postHistory_postHistory_post_id);
        postHistory.addColumnDef(postHistory_postHistory_user_id);
        postHistory.addColumnDef(postHistory_postHistory_day);
        postHistory.addColumnDef(postHistory_postHistory_month);
        postHistory.addColumnDef(postHistory_postHistory_year);

        tag.addColumnDef(tag_tag_id);
        tag.addColumnDef(tag_tag_name);

        postTag.addColumnDef(postTag_postTag_post_id);
        postTag.addColumnDef(postTag_postTag_tag_id);

        post_post_owner.addForeignKeyRelation(post_post_owner_rel);

        comment_comment_user.addForeignKeyRelation(comment_comment_user_rel);
        comment_comment_post.addForeignKeyRelation(comment_comment_post_rel);

        badge_badge_user.addForeignKeyRelation(badge_badge_user_rel);

        postHistory_postHistory_type.addForeignKeyRelation(postHistory_postHistory_type_rel);
        postHistory_postHistory_post.addForeignKeyRelation(postHistory_postHistory_post_rel);
        postHistory_postHistory_user.addForeignKeyRelation(postHistory_postHistory_user_rel);

        postTag_postTag_post.addForeignKeyRelation(postTag_postTag_post_rel);
        postTag_postTag_tag.addForeignKeyRelation(postTag_postTag_tag_rel);

        stackExchangeSchema.addTableDef(user);
        stackExchangeSchema.addTableDef(post);
        stackExchangeSchema.addTableDef(comment);
        stackExchangeSchema.addTableDef(badge);
        stackExchangeSchema.addTableDef(postHistoryType);
        stackExchangeSchema.addTableDef(postHistory);
        stackExchangeSchema.addTableDef(tag);
        stackExchangeSchema.addTableDef(postTag);

        stackExchangeSchema.addForeignKey(post_post_owner);
        stackExchangeSchema.addForeignKey(comment_comment_user);
        stackExchangeSchema.addForeignKey(comment_comment_post);
        stackExchangeSchema.addForeignKey(badge_badge_user);
        stackExchangeSchema.addForeignKey(postHistory_postHistory_type);
        stackExchangeSchema.addForeignKey(postHistory_postHistory_post);
        stackExchangeSchema.addForeignKey(postHistory_postHistory_user);
        stackExchangeSchema.addForeignKey(postTag_postTag_post);
        stackExchangeSchema.addForeignKey(postTag_postTag_tag);

        return stackExchangeSchema;
    }
    @Override
    protected List<Task> buildTasks() {
        List<Task> taskList = new ArrayList<>();

        Task userTask = new Task();

        userTask.setName("Find Tim");
        userTask.setText("Find Tim");
        userTask.setReferenceStatement("SELECT * FROM user WHERE user_user_displayName = \"Tim\";");

        taskList.add(userTask);

        return taskList;
    }
}

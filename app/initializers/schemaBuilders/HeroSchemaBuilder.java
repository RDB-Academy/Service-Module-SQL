package initializers.schemaBuilders;

import initializers.SchemaBuilder;
import models.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @author fabiomazzone
 */
public class HeroSchemaBuilder extends SchemaBuilder {
    @Override
    protected String getSchemaName() {
        return "HeroTeamSchema";
    }

    @Override
    protected SchemaDef buildSchema() {
        SchemaDef           heroTeamSchema      = this.createNewSchemaDef();
        TableDef            hero                = this.createNewTableDef("hero");
        TableDef            team                = this.createNewTableDef("team");
        TableDef            heroTeam            = this.createNewTableDef("hero_team");
        ColumnDef           hero_hero_id        = this.createNewColumnDef("id", "INT");
        ColumnDef           hero_hero_name      = this.createNewColumnDef("name", "VARCHAR");
        ColumnDef           team_team_id        = this.createNewColumnDef("id", "INT");
        ColumnDef           team_team_name      = this.createNewColumnDef("name", "VARCHAR");
        ColumnDef           heroTeam_hero_id    = this.createNewColumnDef("hero_id", "INT");
        ColumnDef           heroTeam_team_id    = this.createNewColumnDef("team_id", "INT");
        ColumnDef           heroTeam_join_year  = this.createNewColumnDef("join_year", "INT");
        ForeignKey          heroTeam_hero       = this.createForeignKey("FK_HeroTeam_Hero");
        ForeignKey          heroTeam_team       = this.createForeignKey("FK_HeroTeam_Team");
        ForeignKeyRelation  heroTeam_hero_rel   = this.createForeignKeyRelation(heroTeam_hero_id, hero_hero_id);
        ForeignKeyRelation  heroTeam_team_rel   = this.createForeignKeyRelation(heroTeam_team_id, team_team_id);

        hero_hero_id.setPrimary(true);

        hero_hero_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        hero_hero_name.setNotNull(true);
        hero_hero_name.setMetaValueSet(ColumnDef.META_VALUE_SET_NAME);

        team_team_id.setPrimary(true);
        team_team_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        team_team_name.setNotNull(true);
        team_team_name.setMetaValueSet(ColumnDef.META_VALUE_SET_NAME);

        heroTeam_hero_id.setPrimary(true);
        heroTeam_hero_id.setMetaValueSet(ColumnDef.META_VALUE_SET_FOREIGN_KEY);
        heroTeam_team_id.setPrimary(true);
        heroTeam_team_id.setMetaValueSet(ColumnDef.META_VALUE_SET_FOREIGN_KEY);
        heroTeam_join_year.setNotNull(false);
        heroTeam_join_year.setMinValueSet(1900);
        heroTeam_join_year.setMaxValueSet(2017);

        hero.addColumnDef(hero_hero_id);
        hero.addColumnDef(hero_hero_name);

        team.addColumnDef(team_team_id);
        team.addColumnDef(team_team_name);

        heroTeam.addColumnDef(heroTeam_hero_id);
        heroTeam.addColumnDef(heroTeam_team_id);
        heroTeam.addColumnDef(heroTeam_join_year);

        heroTeam_hero.addForeignKeyRelation(heroTeam_hero_rel);
        heroTeam_team.addForeignKeyRelation(heroTeam_team_rel);

        heroTeamSchema.addTableDef(hero);
        heroTeamSchema.addTableDef(team);
        heroTeamSchema.addTableDef(heroTeam);

        heroTeamSchema.addForeignKey(heroTeam_hero);
        heroTeamSchema.addForeignKey(heroTeam_team);

        return heroTeamSchema;
    }

    @Override
    protected List<Task> buildTasks() {
        List<Task> taskList = new ArrayList<>();

        Task ironManTask = new Task();

        ironManTask.setName("Find Iron Man");
        ironManTask.setText("Find Iron Man");
        ironManTask.setReferenceStatement("SELECT name FROM hero WHERE name = 'Iron Man';");

        taskList.add(ironManTask);

        return taskList;
    }
}

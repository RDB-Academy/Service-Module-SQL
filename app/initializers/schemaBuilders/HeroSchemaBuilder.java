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
    public String getSchemaName() {
        return "HeroTeamSchema";
    }

    @Override
    public SchemaDef buildSchema() {
        SchemaDef           heroTeamSchema      = this.createNewSchemaDef(getSchemaName());
        TableDef            hero                = this.createNewTableDef("hero");
        TableDef            team                = this.createNewTableDef("team");
        TableDef            heroTeam            = this.createNewTableDef("hero_team");
        ColumnDef           hero_hero_id        = this.createNewColumnDef("hero_id", "INT");
        ColumnDef           hero_hero_name      = this.createNewColumnDef("hero_name", "VARCHAR");
        ColumnDef           team_team_id        = this.createNewColumnDef("team_id", "INT");
        ColumnDef           team_team_name      = this.createNewColumnDef("team_name", "VARCHAR");
        ColumnDef           heroTeam_hero_id    = this.createNewColumnDef("hero_id", "INT");
        ColumnDef           heroTeam_team_id    = this.createNewColumnDef("team_id", "INT");
        ColumnDef           heroTeam_join_year  = this.createNewColumnDef("join_year", "INT");
        ForeignKey          heroTeam_hero       = this.createForeignKey("FK_HeroTeam_Hero");
        ForeignKey          heroTeam_team       = this.createForeignKey("FK_HeroTeam_Team");
        ForeignKeyRelation  heroTeam_hero_rel   = this.createForeignKeyRelation(heroTeam_hero_id, hero_hero_id);
        ForeignKeyRelation  heroTeam_team_rel   = this.createForeignKeyRelation(heroTeam_team_id, team_team_id);

        hero_hero_id.setPrimary(true);
        hero_hero_name.setNullable(false);

        team_team_id.setPrimary(true);
        team_team_name.setNullable(false);

        heroTeam_hero_id.setPrimary(true);
        heroTeam_team_id.setPrimary(true);
        heroTeam_join_year.setNullable(false);

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

        heroTeamSchema.addTask(getTasks());

        return heroTeamSchema;
    }

    private List<Task> getTasks() {
        List<Task> taskList = new ArrayList<>();

        Task ironManTask = new Task();

        ironManTask.setName("Find Iron Man");
        ironManTask.setText("Find Iron Man");
        ironManTask.setReferenceStatement("SELECT * FROM hero WHERE hero_name = \"Iron Man\";");

        taskList.add(ironManTask);

        return taskList;
    }
}

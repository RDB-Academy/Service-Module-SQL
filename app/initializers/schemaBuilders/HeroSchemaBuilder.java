package initializers.schemaBuilders;

import com.google.common.collect.ImmutableMap;
import initializers.SchemaBuilder;
import models.*;
import models.ExtensionDef;

import java.util.*;


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

        hero.extensionDef = this.buildHeroExtension();
        team.extensionDef = this.buildTeamExtension();
        heroTeam.extensionDef = this.buildHeroTeamExtension();

        heroTeamSchema.addTableDef(hero);
        heroTeamSchema.addTableDef(team);
        heroTeamSchema.addTableDef(heroTeam);

        heroTeamSchema.addForeignKey(heroTeam_hero);
        heroTeamSchema.addForeignKey(heroTeam_team);

        return heroTeamSchema;
    }

    private ExtensionDef buildHeroTeamExtension() {
        ExtensionDef extensionDef;
        extensionDef = new ExtensionDef();

        List<Map<String, String>> extensionList = Arrays.asList(
                ImmutableMap.of("hero_id", "0", "team_id", "0", "join_year", "2012"),
                ImmutableMap.of("hero_id", "1", "team_id", "0", "join_year", "2012"),
                ImmutableMap.of("hero_id", "2", "team_id", "0", "join_year", "2012"),
                ImmutableMap.of("hero_id", "3", "team_id", "0", "join_year", "2012"),
                ImmutableMap.of("hero_id", "4", "team_id", "0", "join_year", "2016"),
                ImmutableMap.of("hero_id", "0", "team_id", "1", "join_year", "2016"),
                ImmutableMap.of("hero_id", "4", "team_id", "1", "join_year", "2016"),
                ImmutableMap.of("hero_id", "2", "team_id", "2", "join_year", "2016")
        );

        extensionDef.setExtensionList(extensionList);

        return extensionDef;
    }

    private ExtensionDef buildTeamExtension() {
        ExtensionDef extensionDef;
        extensionDef = new ExtensionDef();

        List<Map<String, String>> extensionList = Arrays.asList(
                ImmutableMap.of("id", "0", "name", "Avengers"),
                ImmutableMap.of("id", "1", "name", "Pro-Registration"),
                ImmutableMap.of("id", "2", "name", "Anti-Registration")
        );

        extensionDef.setExtensionList(extensionList);

        return extensionDef;
    }

    private ExtensionDef buildHeroExtension() {
        ExtensionDef extensionDef;
        extensionDef = new ExtensionDef();

        List<Map<String, String>> extensionList = Arrays.asList(
                ImmutableMap.of("id", "0", "name", "Iron Man"),
                ImmutableMap.of("id", "1", "name", "Thor"),
                ImmutableMap.of("id", "2", "name", "Captain America"),
                ImmutableMap.of("id", "3", "name", "Hulk"),
                ImmutableMap.of("id", "4", "name", "Spider Man")
        );

        extensionDef.setExtensionList(extensionList);

        return extensionDef;
    }

    @Override
    protected List<Task> buildTasks() {
        List<Task> taskList = new ArrayList<>();

        Task task = new Task();

        task.setText("Find Iron Man");
        task.setReferenceStatement("SELECT name FROM hero WHERE name = 'Iron Man';");
        taskList.add(task);

        task = new Task();
        task.setText("In how many teams is Thor?");
        task.setReferenceStatement(" Select count(ht.team_id)\n" +
                " From hero as h\n" +
                " Join hero_team as ht ON h.id = ht.hero_id\n" +
                " Where h.name = 'Thor';");
        taskList.add(task);

        task = new Task();
        task.setText("How many Heroes are part of multiple teams?");
        task.setReferenceStatement("select count(*)\n" +
                "        from (\n" +
                "                Select name as peter, count(team_id) from hero as h\n" +
                "                left outer Join hero_team as th ON h.id = th.hero_id\n" +
                "                group by id\n" +
                "                having count(team_id) > 1 )");
        taskList.add(task);

        task = new Task();
        task.setText("How many Heroes don't have a team?");
        task.setReferenceStatement("select count(*)\n" +
                "        from (\n" +
                "                Select name as peter, count(team_id) from hero as h\n" +
                "                left outer Join hero_team as th ON h.id = th.hero_id\n" +
                "                group by id\n" +
                "                having count(team_id) < 1 )");
        taskList.add(task);


        return taskList;
    }
}

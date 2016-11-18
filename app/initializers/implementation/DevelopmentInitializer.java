package initializers.implementation;

import initializers.Initializer;
import models.*;
import repository.SchemaDefRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DevelopmentInitializer implements Initializer {
    private SchemaDefRepository schemaDefRepository;


    @Inject
    public DevelopmentInitializer(SchemaDefRepository schemaDefRepository) {
        this.schemaDefRepository = schemaDefRepository;
        if(this.schemaDefRepository.getByName("HeroTeamSchema") == null) {
            this.heroSchema();
        }
    }

    private void heroSchema() {
        SchemaDef   heroTeamSchema  = new SchemaDef();
        TableDef    hero            = new TableDef();
        TableDef    team            = new TableDef();
        TableDef    heroTeam        = new TableDef();
        ColumnDef   hero_hero_id    = new ColumnDef();
        ColumnDef   hero_hero_name  = new ColumnDef();
        ColumnDef   team_team_id    = new ColumnDef();
        ColumnDef   team_team_name  = new ColumnDef();
        ColumnDef   heroTeam_hero_id    = new ColumnDef();
        ColumnDef   heroTeam_team_id    = new ColumnDef();
        ColumnDef   heroTeam_join_year  = new ColumnDef();
        ForeignKey  heroTeam_hero   = new ForeignKey();
        ForeignKey  heroTeam_team   = new ForeignKey();
        ForeignKeyRelation heroTeam_hero_rel = new ForeignKeyRelation();
        ForeignKeyRelation heroTeam_team_rel = new ForeignKeyRelation();

        hero.setName("hero");
        team.setName("team");
        heroTeam.setName("hero_team");

        hero_hero_id.setName("hero_id");
        hero_hero_id.setDatatype("INT");
        hero_hero_id.setPrimary(true);
        hero_hero_name.setName("hero_name");
        hero_hero_name.setDatatype("VARCHAR");
        hero_hero_name.setNullable(false);

        team_team_id.setName("team_id");
        team_team_id.setDatatype("INT");
        team_team_id.setPrimary(true);
        team_team_name.setName("team_name");
        team_team_name.setDatatype("VARCHAR");
        team_team_name.setNullable(false);

        heroTeam_hero_id.setName("hero_id");
        heroTeam_hero_id.setDatatype("INT");
        heroTeam_hero_id.setPrimary(true);
        heroTeam_team_id.setName("team_id");
        heroTeam_team_id.setDatatype("INT");
        heroTeam_team_id.setPrimary(true);
        heroTeam_join_year.setName("join_year");
        heroTeam_join_year.setDatatype("INT");
        heroTeam_join_year.setNullable(false);

        hero.addColumnDef(hero_hero_id);
        hero.addColumnDef(hero_hero_name);

        team.addColumnDef(team_team_id);
        team.addColumnDef(team_team_name);

        heroTeam.addColumnDef(heroTeam_hero_id);
        heroTeam.addColumnDef(heroTeam_team_id);
        heroTeam.addColumnDef(heroTeam_join_year);

        heroTeam_hero.setName("FK_HeroTeam_Hero");
        heroTeam_team.setName("FK_HeroTeam_Team");

        heroTeam_hero_rel.setSourceColumn(heroTeam_hero_id);
        heroTeam_hero_rel.setTargetColumn(hero_hero_id);

        heroTeam_team_rel.setSourceColumn(heroTeam_team_id);
        heroTeam_team_rel.setTargetColumn(team_team_id);

        heroTeam_hero.addForeignKeyRelation(heroTeam_hero_rel);
        heroTeam_team.addForeignKeyRelation(heroTeam_team_rel);

        heroTeamSchema.setName("HeroTeamSchema");
        heroTeamSchema.addTableDef(hero);
        heroTeamSchema.addTableDef(team);
        heroTeamSchema.addTableDef(heroTeam);

        heroTeamSchema.addForeignKey(heroTeam_hero);
        heroTeamSchema.addForeignKey(heroTeam_team);

        System.out.println("save");
        this.schemaDefRepository.save(heroTeamSchema);
    }
}


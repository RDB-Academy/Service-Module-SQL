package initializers.schemaBuilders;

import initializers.SchemaBuilder;
import models.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ronja on 08.12.16.
 */

public class FootballSchemaBuilder extends SchemaBuilder {
    @Override
    protected String getSchemaName() {
        return "FootballSchema";
    }

    @Override
    protected SchemaDef buildSchema() {
        SchemaDef           footballSchema              = this.createNewSchemaDef();
        TableDef            player                      = this.createNewTableDef("player");
        TableDef            team                        = this.createNewTableDef("team");
        TableDef            stadium                     = this.createNewTableDef("stadium");
        TableDef            superbowl                   = this.createNewTableDef("superbowl");
        TableDef            playerTeam                  = this.createNewTableDef("player_team");
        TableDef            teamStadium                 = this.createNewTableDef("team_stadium");
        TableDef            teamSuperbowl               = this.createNewTableDef("team_superbowl");

        ColumnDef           player_player_id            = this.createNewColumnDef("id" , "INT");
        ColumnDef           player_player_firstname     = this.createNewColumnDef("firstname", "VARCHAR(255)");
        ColumnDef           player_player_lastname      = this.createNewColumnDef("lastname", "VARCHAR(255)");
        ColumnDef           player_player_position      = this.createNewColumnDef("position", "VARCHAR(255)");
        ColumnDef           player_player_number        = this.createNewColumnDef("number", "INT");
        ColumnDef           player_player_height        = this.createNewColumnDef("height", "INT");
        ColumnDef           player_player_weight        = this.createNewColumnDef("weight", "INT");

        ColumnDef           team_team_id                = this.createNewColumnDef("id", "INT");
        ColumnDef           team_team_name              = this.createNewColumnDef("name", "VARCHAR(255)");
        ColumnDef           team_team_color1            = this.createNewColumnDef("color1", "VARCHAR(255)");
        ColumnDef           team_team_color2            = this.createNewColumnDef("color2", "VARCHAR(255)");
        ColumnDef           team_team_color3            = this.createNewColumnDef("color3", "VARCHAR(255)");

        ColumnDef           stadium_stadium_id          = this.createNewColumnDef("id", "INT");
        ColumnDef           stadium_stadium_name        = this.createNewColumnDef("name", "VARCHAR(255)");
        ColumnDef           stadium_stadium_city        = this.createNewColumnDef("city", "VARCHAR(255)");
        ColumnDef           stadium_stadium_capacity    = this.createNewColumnDef("capacity", "INT");

        ColumnDef           superbowl_superbowl_id      = this.createNewColumnDef("id", "INT");
        ColumnDef           superbowl_superbowl_name    = this.createNewColumnDef("name", "VARCHAR(255)");
        ColumnDef           superbowl_superbowl_date    = this.createNewColumnDef("date", "DATE");

        ColumnDef           playerTeam_player_id        = this.createNewColumnDef("player_id", "INT");
        ColumnDef           playerTeam_team_id          = this.createNewColumnDef("team_id", "INT");
        ColumnDef           playerTeam_join_year        = this.createNewColumnDef("join_year", "INT");
        ColumnDef           playerTeam_left_year        = this.createNewColumnDef("left_year", "INT");

        ColumnDef           teamStadium_team_id         = this.createNewColumnDef("team_id", "INT");
        ColumnDef           teamStadium_stadium_id      = this.createNewColumnDef("stadium_id", "INT");
        ColumnDef           teamStadium_owned_since     = this.createNewColumnDef("owned_since", "DATE");
        ColumnDef           teamStadium_owned_until     = this.createNewColumnDef("owned_until", "DATE");

        ColumnDef           teamSuperbowl_superbowl_id  = this.createNewColumnDef("superbowl_id", "INT");
        ColumnDef           teamSuperbowl_winner_id     = this.createNewColumnDef("team1_id", "INT");
        ColumnDef           teamSuperbowl_loser_id      = this.createNewColumnDef("team2_id", "INT");
        ColumnDef           teamSuperbowl_stadium_id    = this.createNewColumnDef("stadium_id", "INT");

        ForeignKey          playerTeam_player           = this.createForeignKey("FK_PlayerTeam_Player");
        ForeignKey          playerTeam_team             = this.createForeignKey("FK_PlayerTeam_Team");
        ForeignKeyRelation  playerTeam_player_rel       = this.createForeignKeyRelation(playerTeam_player_id, player_player_id);
        ForeignKeyRelation  playerTeam_team_rel         = this.createForeignKeyRelation(playerTeam_team_id, team_team_id);
        ForeignKey          teamStadium_team            = this.createForeignKey("FK_TeamStadium_Team");
        ForeignKey          teamStadium_stadium         = this.createForeignKey("FK_TeamStadium_Stadium");
        ForeignKeyRelation  teamStadium_team_rel        = this.createForeignKeyRelation(teamStadium_team_id, team_team_id);
        ForeignKeyRelation  teamStadium_stadium_rel     = this.createForeignKeyRelation(teamStadium_stadium_id, stadium_stadium_id);
        ForeignKey          teamSuperbowl_winner        = this.createForeignKey("FK_TeamSuperbowl_Winner");
        ForeignKey          teamSuperbowl_loser         = this.createForeignKey("FK_TeamSuperbowl_Loser");
        ForeignKey          teamSuperbowl_superbowl     = this.createForeignKey("FK_TeamSuperbowl_Superbowl");
        ForeignKeyRelation  teamSuperbowl_winner_rel    = this.createForeignKeyRelation(teamSuperbowl_superbowl_id, team_team_id);
        ForeignKeyRelation  teamSuperbowl_loser_rel     = this.createForeignKeyRelation(teamSuperbowl_loser_id, team_team_id);
        ForeignKeyRelation  teamSuperbowl_superbowl_rel = this.createForeignKeyRelation(teamSuperbowl_superbowl_id, superbowl_superbowl_id);

        player_player_id.setPrimary(true);
        player_player_id.setNotNull(true);
        player_player_firstname.setNotNull(true);
        player_player_lastname.setNotNull(true);
        player_player_height.setNotNull(true);
        player_player_weight.setNotNull(true);
        player_player_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        player_player_firstname.setMetaValueSet(ColumnDef.META_VALUE_SET_FIRSTNAME);
        player_player_lastname.setMetaValueSet(ColumnDef.META_VALUE_SET_LASTNAME);
        player_player_position.setMetaValueSet(ColumnDef.META_VALUE_SET_POSITION);
        player_player_weight.setMinValueSet(65);
        player_player_weight.setMaxValueSet(250);
        player_player_height.setMinValueSet(165);
        player_player_height.setMaxValueSet(210);
        player_player_number.setMinValueSet(1);
        player_player_number.setMaxValueSet(99);

        team_team_id.setPrimary(true);
        team_team_id.setNotNull(true);
        team_team_name.setNotNull(true);
        team_team_color1.setNotNull(true);
        team_team_color2.setNotNull(true);
        team_team_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        team_team_name.setMetaValueSet(ColumnDef.META_VALUE_SET_NAME);
        team_team_color1.setMetaValueSet(ColumnDef.META_VALUE_SET_COLOR);
        team_team_color2.setMetaValueSet(ColumnDef.META_VALUE_SET_COLOR);
        team_team_color3.setMetaValueSet(ColumnDef.META_VALUE_SET_COLOR);

        stadium_stadium_id.setPrimary(true);
        stadium_stadium_id.setNotNull(true);
        stadium_stadium_name.setNotNull(true);
        stadium_stadium_city.setNotNull(true);
        stadium_stadium_capacity.setNotNull(true);
        stadium_stadium_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        stadium_stadium_city.setMetaValueSet(ColumnDef.META_VALUE_SET_CITY);
        stadium_stadium_name.setMetaValueSet(ColumnDef.META_VALUE_SET_NAME);
        stadium_stadium_capacity.setMinValueSet(50000);
        stadium_stadium_capacity.setMaxValueSet(90000);

        superbowl_superbowl_id.setPrimary(true);
        superbowl_superbowl_id.setNotNull(true);
        superbowl_superbowl_name.setNotNull(true);
        superbowl_superbowl_date.setNotNull(true);
        superbowl_superbowl_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        superbowl_superbowl_name.setMetaValueSet(ColumnDef.META_VALUE_SET_NAME);
        superbowl_superbowl_date.setMetaValueSet(ColumnDef.META_VALUE_SET_DATE);

        playerTeam_player_id.setPrimary(true);
        playerTeam_player_id.setNotNull(true);
        playerTeam_team_id.setPrimary(true);
        playerTeam_team_id.setNotNull(true);
        playerTeam_join_year.setNotNull(true);
        playerTeam_player_id.setMetaValueSet(ColumnDef.META_VALUE_SET_FOREIGN_KEY);
        playerTeam_team_id.setMetaValueSet(ColumnDef.META_VALUE_SET_FOREIGN_KEY);
        playerTeam_join_year.setMetaValueSet(ColumnDef.META_VALUE_SET_YEAR);
        playerTeam_left_year.setMetaValueSet(ColumnDef.META_VALUE_SET_YEAR);

        teamStadium_team_id.setPrimary(true);
        teamStadium_team_id.setNotNull(true);
        teamStadium_stadium_id.setPrimary(true);
        teamStadium_stadium_id.setNotNull(true);
        teamStadium_owned_since.setNotNull(true);
        teamStadium_team_id.setMetaValueSet(ColumnDef.META_VALUE_SET_FOREIGN_KEY);
        teamStadium_stadium_id.setMetaValueSet(ColumnDef.META_VALUE_SET_FOREIGN_KEY);
        teamStadium_owned_since.setMetaValueSet(ColumnDef.META_VALUE_SET_DATE);
        teamStadium_owned_until.setMetaValueSet(ColumnDef.META_VALUE_SET_DATE);

        teamSuperbowl_superbowl_id.setPrimary(true);
        teamSuperbowl_superbowl_id.setNotNull(true);
        teamSuperbowl_winner_id.setNotNull(true);
        teamSuperbowl_loser_id.setNotNull(true);
        teamSuperbowl_stadium_id.setNotNull(true);
        teamSuperbowl_superbowl_id.setMetaValueSet(ColumnDef.META_VALUE_SET_FOREIGN_KEY);
        teamSuperbowl_winner_id.setMetaValueSet(ColumnDef.META_VALUE_SET_FOREIGN_KEY);
        teamSuperbowl_loser_id.setMetaValueSet(ColumnDef.META_VALUE_SET_FOREIGN_KEY);
        teamSuperbowl_stadium_id.setMetaValueSet(ColumnDef.META_VALUE_SET_FOREIGN_KEY);

        player.addColumnDef(player_player_id);
        player.addColumnDef(player_player_firstname);
        player.addColumnDef(player_player_lastname);
        player.addColumnDef(player_player_height);
        player.addColumnDef(player_player_weight);
        player.addColumnDef(player_player_number);
        player.addColumnDef(player_player_position);

        team.addColumnDef(team_team_id);
        team.addColumnDef(team_team_name);
        team.addColumnDef(team_team_color1);
        team.addColumnDef(team_team_color2);
        team.addColumnDef(team_team_color3);

        stadium.addColumnDef(stadium_stadium_id);
        stadium.addColumnDef(stadium_stadium_name);
        stadium.addColumnDef(stadium_stadium_city);
        stadium.addColumnDef(stadium_stadium_capacity);

        superbowl.addColumnDef(superbowl_superbowl_id);
        superbowl.addColumnDef(superbowl_superbowl_name);
        superbowl.addColumnDef(superbowl_superbowl_date);

        playerTeam.addColumnDef(playerTeam_player_id);
        playerTeam.addColumnDef(playerTeam_team_id);
        playerTeam.addColumnDef(playerTeam_join_year);
        playerTeam.addColumnDef(playerTeam_left_year);

        teamStadium.addColumnDef(teamStadium_stadium_id);
        teamStadium.addColumnDef(teamStadium_team_id);
        teamStadium.addColumnDef(teamStadium_owned_since);
        teamStadium.addColumnDef(teamStadium_owned_until);

        teamSuperbowl.addColumnDef(teamSuperbowl_superbowl_id);
        teamSuperbowl.addColumnDef(teamSuperbowl_winner_id);
        teamSuperbowl.addColumnDef(teamSuperbowl_loser_id);
        teamSuperbowl.addColumnDef(teamSuperbowl_stadium_id);

        playerTeam_player.addForeignKeyRelation(playerTeam_player_rel);
        playerTeam_team.addForeignKeyRelation(playerTeam_team_rel);

        teamStadium_stadium.addForeignKeyRelation(teamStadium_stadium_rel);
        teamStadium_team.addForeignKeyRelation(teamStadium_team_rel);

        teamSuperbowl_superbowl.addForeignKeyRelation(teamSuperbowl_superbowl_rel);
        teamSuperbowl_winner.addForeignKeyRelation(teamSuperbowl_winner_rel);
        teamSuperbowl_loser.addForeignKeyRelation(teamSuperbowl_loser_rel);

        footballSchema.addTableDef(player);
        footballSchema.addTableDef(team);
        footballSchema.addTableDef(stadium);
        footballSchema.addTableDef(superbowl);
        footballSchema.addTableDef(playerTeam);
        footballSchema.addTableDef(teamStadium);
        footballSchema.addTableDef(teamSuperbowl);

        footballSchema.addForeignKey(playerTeam_player);
        footballSchema.addForeignKey(playerTeam_team);
        footballSchema.addForeignKey(teamStadium_stadium);
        footballSchema.addForeignKey(teamStadium_team);
        footballSchema.addForeignKey(teamSuperbowl_superbowl);
        footballSchema.addForeignKey(teamSuperbowl_winner);
        footballSchema.addForeignKey(teamSuperbowl_loser);

        return footballSchema;
    }

    @Override
    protected List<Task> buildTasks() {
        List<Task> taskList = new ArrayList<>();

        Task footballTask = new Task();

        footballTask.setName("Find Luke Kuechly.");
        footballTask.setText("Find Luke Kuechly.");
        footballTask.setReferenceStatement("SELECT firstname, lastname FROM player WHERE firstname = 'Luke' AND lastname = 'Kuechly';");

        taskList.add(footballTask);

        return taskList;
    }
}
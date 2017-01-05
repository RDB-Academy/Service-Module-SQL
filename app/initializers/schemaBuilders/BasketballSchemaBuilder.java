package initializers.schemaBuilders;

import com.google.common.collect.ImmutableMap;
import initializers.SchemaBuilder;
import models.*;
import models.submodels.ExtensionDef;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by carl on 21.12.16.
 */
public class BasketballSchemaBuilder  extends SchemaBuilder {

    @Override
    protected String getSchemaName() {
        return "BasketballSchema";
    }

    @Override
    protected SchemaDef buildSchema() {
        SchemaDef basketballSchema = this.createNewSchemaDef();
        TableDef team = this.createNewTableDef("team");
        TableDef stadium = this.createNewTableDef("stadium");
        TableDef game = this.createNewTableDef("game");
        TableDef player = this.createNewTableDef("player");
        TableDef forward = this.createNewTableDef("forward");
        TableDef guard = this.createNewTableDef("guard");
        TableDef center = this.createNewTableDef("center");

        ColumnDef team_team_id = this.createNewColumnDef("id", "INT");
        ColumnDef team_team_city = this.createNewColumnDef("city", "VARCHAR(255)");
        ColumnDef team_team_color = this.createNewColumnDef("color", "VARCHAR(255)");
        ColumnDef team_mascot = this.createNewColumnDef("mascot", "VARCHAR(255)");

        ColumnDef stadium_stadium_id = this.createNewColumnDef("id", "INT");
        ColumnDef stadium_stadium_name = this.createNewColumnDef("name", "VARCHAR(255)");
        ColumnDef stadium_stadium_city = this.createNewColumnDef("city", "VARCHAR(255)");
        ColumnDef stadium_stadium_capacity = this.createNewColumnDef("capacity", "INT");

        ColumnDef game_game_id = this.createNewColumnDef("id", "INT");
        ColumnDef game_season = this.createNewColumnDef("season", "INT");
        ColumnDef game_stadium_id = this.createNewColumnDef("stadium_id", "INT");
        ColumnDef game_home_score = this.createNewColumnDef("home_score", "INT");
        ColumnDef game_guest_score = this.createNewColumnDef("guest_score", "INT");
        ColumnDef game_home_team_id = this.createNewColumnDef("home_team_id", "INT");
        ColumnDef game_guest_team_id = this.createNewColumnDef("guest_team_id", "INT");

        ColumnDef player_player_id = this.createNewColumnDef("id", "INT");
        ColumnDef player_player_firstname = this.createNewColumnDef("firstname", "VARCHAR(255)");
        ColumnDef player_player_lastname = this.createNewColumnDef("lastname", "VARCHAR(255)");
        ColumnDef player_player_country = this.createNewColumnDef("country", "VARCHAR(255)");
        ColumnDef player_player_age = this.createNewColumnDef("age", "INT");
        ColumnDef player_player_number = this.createNewColumnDef("number", "INT");
        ColumnDef player_player_height = this.createNewColumnDef("height", "INT");
        ColumnDef player_player_weight = this.createNewColumnDef("weight", "INT");
        ColumnDef player_points_per_game = this.createNewColumnDef("points_per_game", "INT");
        ColumnDef player_games_played = this.createNewColumnDef("games_played", "INT");

        ColumnDef center_player_id = this.createNewColumnDef("player_id", "INT");
        ColumnDef center_team_id = this.createNewColumnDef("team_id", "INT");
        ColumnDef center_blocks = this.createNewColumnDef("blocks", "INT");
        ColumnDef center_rebounds = this.createNewColumnDef("rebounds", "INT");

        ColumnDef guard_player_id = this.createNewColumnDef("player_id", "INT");
        ColumnDef guard_team_id = this.createNewColumnDef("team_id", "INT");
        ColumnDef guard_three_pointers = this.createNewColumnDef("three_pointers", "INT");
        ColumnDef guard_assists = this.createNewColumnDef("assists", "INT");

        ColumnDef forward_player_id = this.createNewColumnDef("player_id", "INT");
        ColumnDef forward_team_id = this.createNewColumnDef("team_id", "INT");

        ForeignKey center_team = this.createForeignKey("FK_Center_Team");
        ForeignKey center_player = this.createForeignKey("FK_Center_Player");
        ForeignKeyRelation center_team_rel = this.createForeignKeyRelation(center_team_id, team_team_id);
        ForeignKeyRelation center_player_rel = this.createForeignKeyRelation(center_player_id, player_player_id);

        ForeignKey forward_team = this.createForeignKey("FK_Forward_Team");
        ForeignKey forward_player = this.createForeignKey("FK_Forward_Player");
        ForeignKeyRelation forward_team_rel = this.createForeignKeyRelation(forward_team_id, team_team_id);
        ForeignKeyRelation forward_player_rel = this.createForeignKeyRelation(forward_player_id, player_player_id);

        ForeignKey guard_team = this.createForeignKey("FK_TeamStadium_Team");
        ForeignKey guard_player = this.createForeignKey("FK_TeamStadium_Stadium");
        ForeignKeyRelation guard_team_rel = this.createForeignKeyRelation(guard_team_id, team_team_id);
        ForeignKeyRelation guard_player_rel = this.createForeignKeyRelation(guard_player_id, player_player_id);

        ForeignKey game_home_team = this.createForeignKey("FK_Game_Home_Team");
        ForeignKey game_guest_team = this.createForeignKey("FK_Game_Guest_Team");
        ForeignKey game_stadium = this.createForeignKey("FK_Game_Stadium");
        ForeignKeyRelation game_home_team_id_rel = this.createForeignKeyRelation(game_home_team_id, team_team_id);
        ForeignKeyRelation game_guest_team_id_rel = this.createForeignKeyRelation(game_guest_team_id, team_team_id);
        ForeignKeyRelation game_stadium_id_rel = this.createForeignKeyRelation(game_stadium_id, stadium_stadium_id);

        player_player_id.setPrimary(true);
        player_player_id.setNotNull(true);
        player_player_firstname.setNotNull(true);
        player_player_lastname.setNotNull(true);
        player_player_height.setNotNull(true);
        player_player_weight.setNotNull(true);
        player_player_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        player_player_firstname.setMetaValueSet(ColumnDef.META_VALUE_SET_FIRSTNAME);
        player_player_lastname.setMetaValueSet(ColumnDef.META_VALUE_SET_LASTNAME);
        player_player_country.setMetaValueSet(ColumnDef.META_VALUE_SET_LOCATION);
        player_player_age.setMinValueSet(16);
        player_player_age.setMaxValueSet(36);
        player_points_per_game.setMinValueSet(0);
        player_points_per_game.setMaxValueSet(40);
        player_player_weight.setMinValueSet(65);
        player_player_weight.setMaxValueSet(250);
        player_player_height.setMinValueSet(165);
        player_player_height.setMaxValueSet(240);
        player_player_number.setMinValueSet(1);
        player_player_number.setMaxValueSet(99);
        player_games_played.setMinValueSet(1);
        player_games_played.setMaxValueSet(300);

        team_team_id.setPrimary(true);
        team_team_id.setNotNull(true);
        team_mascot.setNotNull(true);
        team_team_color.setNotNull(true);
        team_team_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        team_mascot.setMetaValueSet(ColumnDef.META_VALUE_SET_ANIMAL);
        team_team_color.setMetaValueSet(ColumnDef.META_VALUE_SET_COLOR);
        team_team_city.setMetaValueSet(ColumnDef.META_VALUE_SET_CITY);

        stadium_stadium_id.setPrimary(true);
        stadium_stadium_id.setNotNull(true);
        stadium_stadium_name.setNotNull(true);
        stadium_stadium_city.setNotNull(true);
        stadium_stadium_capacity.setNotNull(true);
        stadium_stadium_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        stadium_stadium_city.setMetaValueSet(ColumnDef.META_VALUE_SET_CITY);
        stadium_stadium_name.setMetaValueSet(ColumnDef.META_VALUE_SET_NAME);
        stadium_stadium_capacity.setMinValueSet(20000);
        stadium_stadium_capacity.setMaxValueSet(40000);

        game_game_id.setPrimary(true);
        game_game_id.setNotNull(true);
        game_home_team_id.setNotNull(true);
        game_guest_team_id.setNotNull(true);
        game_game_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        game_stadium_id.setMetaValueSet(ColumnDef.META_VALUE_SET_FOREIGN_KEY);
        game_home_team_id.setMetaValueSet(ColumnDef.META_VALUE_SET_FOREIGN_KEY);
        game_guest_team_id.setMetaValueSet(ColumnDef.META_VALUE_SET_FOREIGN_KEY);
        game_season.setMetaValueSet(ColumnDef.META_VALUE_SET_YEAR);
        game_home_score.setMinValueSet(80);
        game_home_score.setMaxValueSet(130);
        game_guest_score.setMinValueSet(85);
        game_guest_score.setMaxValueSet(125);

        center_player_id.setPrimary(true);
        center_team_id.setPrimary(true);
        center_player_id.setNotNull(true);
        center_team_id.setNotNull(true);
        center_player_id.setMetaValueSet(ColumnDef.META_VALUE_SET_FOREIGN_KEY);
        center_team_id.setMetaValueSet(ColumnDef.META_VALUE_SET_FOREIGN_KEY);
        center_blocks.setMinValueSet(10);
        center_blocks.setMaxValueSet(130);
        center_rebounds.setMinValueSet(25);
        center_rebounds.setMaxValueSet(400);

        guard_player_id.setPrimary(true);
        guard_team_id.setPrimary(true);
        guard_player_id.setNotNull(true);
        guard_team_id.setNotNull(true);
        guard_player_id.setMetaValueSet(ColumnDef.META_VALUE_SET_FOREIGN_KEY);
        guard_team_id.setMetaValueSet(ColumnDef.META_VALUE_SET_FOREIGN_KEY);
        guard_three_pointers.setMinValueSet(10);
        guard_three_pointers.setMaxValueSet(1300);
        guard_assists.setMinValueSet(25);
        guard_assists.setMaxValueSet(1000);

        forward_player_id.setPrimary(true);
        forward_team_id.setPrimary(true);
        forward_player_id.setNotNull(true);
        forward_team_id.setNotNull(true);
        forward_player_id.setMetaValueSet(ColumnDef.META_VALUE_SET_FOREIGN_KEY);
        forward_team_id.setMetaValueSet(ColumnDef.META_VALUE_SET_FOREIGN_KEY);

        player.addColumnDef(player_player_id);
        player.addColumnDef(player_player_firstname);
        player.addColumnDef(player_player_lastname);
        player.addColumnDef(player_player_height);
        player.addColumnDef(player_player_weight);
        player.addColumnDef(player_player_number);
        player.addColumnDef(player_player_country);
        player.addColumnDef(player_player_age);
        player.addColumnDef(player_points_per_game);
        player.addColumnDef(player_games_played);

        team.addColumnDef(team_team_id);
        team.addColumnDef(team_team_city);
        team.addColumnDef(team_team_color);
        team.addColumnDef(team_mascot);

        stadium.addColumnDef(stadium_stadium_id);
        stadium.addColumnDef(stadium_stadium_name);
        stadium.addColumnDef(stadium_stadium_city);
        stadium.addColumnDef(stadium_stadium_capacity);

        game.addColumnDef(game_game_id);
        game.addColumnDef(game_stadium_id);
        game.addColumnDef(game_home_score);
        game.addColumnDef(game_guest_score);
        game.addColumnDef(game_home_team_id);
        game.addColumnDef(game_guest_team_id);
        game.addColumnDef(game_season);

        forward.addColumnDef(forward_player_id);
        forward.addColumnDef(forward_team_id);

        guard.addColumnDef(guard_player_id);
        guard.addColumnDef(guard_team_id);
        guard.addColumnDef(guard_three_pointers);
        guard.addColumnDef(guard_assists);

        center.addColumnDef(center_player_id);
        center.addColumnDef(center_team_id);
        center.addColumnDef(center_blocks);
        center.addColumnDef(center_rebounds);

        center_team.addForeignKeyRelation(center_team_rel);
        center_player.addForeignKeyRelation(center_player_rel);

        forward_team.addForeignKeyRelation(forward_team_rel);
        forward_player.addForeignKeyRelation(forward_player_rel);

        guard_team.addForeignKeyRelation(guard_team_rel);
        guard_player.addForeignKeyRelation(guard_player_rel);

        game_home_team.addForeignKeyRelation(game_home_team_id_rel);
        game_guest_team.addForeignKeyRelation(game_guest_team_id_rel);
        game_stadium.addForeignKeyRelation(game_stadium_id_rel);

        basketballSchema.addTableDef(player);
        basketballSchema.addTableDef(team);
        basketballSchema.addTableDef(stadium);
        basketballSchema.addTableDef(game);
        basketballSchema.addTableDef(forward);
        basketballSchema.addTableDef(guard);
        basketballSchema.addTableDef(center);

        basketballSchema.addForeignKey(center_team);
        basketballSchema.addForeignKey(center_player);
        basketballSchema.addForeignKey(forward_team);
        basketballSchema.addForeignKey(forward_player);
        basketballSchema.addForeignKey(guard_team);
        basketballSchema.addForeignKey(guard_player);
        basketballSchema.addForeignKey(game_home_team);
        basketballSchema.addForeignKey(game_guest_team);
        basketballSchema.addForeignKey(game_stadium);

        return basketballSchema;
    }

    @Override
    protected List<Task> buildTasks() {
        List<Task> taskList = new ArrayList<>();

        Task task = new Task();

        task.setText("What is the average age of all players?");
        task.setReferenceStatement("SELECT avg(age) as average FROM player;");
        taskList.add(task);

        task = new Task();
        task.setText("List the ids of the most used stadiums in the season of 2015.");
        task.setReferenceStatement("SELECT stadium_id, count(id) FROM game WHERE season = 2015 GROUP BY stadium_id HAVING count(id) = (Select max(totalCount) from( SELECT  stadium_id, COUNT(id) totalCount FROM    game WHERE season = 2015 GROUP   BY stadium_id ) as s)");
        taskList.add(task);

        task = new Task();
        task.setText("How many teams have not played in the stadium with id “5”?");
        task.setReferenceStatement("SELECT count(distinct(u.id))\n" +
                "FROM team AS u\n" +
                "LEFT JOIN game AS p ON \n" +
                "(p.guest_team_id = u.id or p.home_team_id = u.id) and p.stadium_id = 5\n" +
                "WHERE p.id IS NULL");
        taskList.add(task);

        task = new Task();
        task.setText("How many players are from Canada?");
        task.setReferenceStatement("SELECT count(id)\n" +
                "FROM player\n" +
                "WHERE country LIKE '%canada%'\n" +
                "OR country LIKE '%Canada%';");
        taskList.add(task);

        task = new Task();
        task.setText("Show the age, the lastname and the country of the player who got most points so far.");
        task.setReferenceStatement(" SELECT age,lastname, country\n" +
                "FROM player\n" +
                "WHERE points_per_game*games_played =(SELECT max(points_per_game*games_played)\n" +
                "\t\t\t FROM player)");
        taskList.add(task);

        task = new Task();
        task.setText("Give me a set of all cities of the guest teams who won in the season of 2015. ");
        task.setReferenceStatement("SELECT distinct(t.city)\n" +
                "FROM team AS t\n" +
                "JOIN game AS g ON t.id = g.home_team_id\n" +
                "WHERE g.home_score > g.guest_score\n" +
                "AND g.season=2015");
        taskList.add(task);

        task = new Task();
        task.setText("What is the percentage that a guest team wins, based on the given data?");
        task.setReferenceStatement("SELECT ((SELECT result1.wins\n" +
                "FROM (SELECT count(id) as wins\n" +
                "FROM game\n" +
                "WHERE guest_score > home_score) as result1)*100/ (SELECT count(id)\n" +
                "\t\tFROM game))");
        taskList.add(task);


        return taskList;
    }
}

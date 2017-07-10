package models.sqlTrainerService;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import models.BaseModel;
import play.data.validation.Constraints;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fabiomazzone
 */
@Entity
public class SchemaDef extends BaseModel {
    @Id
    private Long id;

    @NotNull
    @Column(unique = true)
    @Constraints.Required()
    @Constraints.MinLength(1)
    private String name;

    @JsonIgnore
    private boolean available = true;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "schemaDef")
    private List<TableDef> tableDefList;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "schemaDef")
    private List<ForeignKey> foreignKeyList;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "schemaDef")
    private List<Task> taskList;

// *********************************************************************************************************************
// * Getter & Setter
// *********************************************************************************************************************

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<TableDef> getTableDefList() {
        return tableDefList;
    }

    public void addTableDef(TableDef tableDef) {
        if (this.tableDefList == null) {
            this.tableDefList = new ArrayList<>();
        }
        if(!tableDefList.contains(tableDef)) {
            this.tableDefList.add(tableDef);
        }
    }

    public List<ForeignKey> getForeignKeyList() {
        return foreignKeyList;
    }

    public void addForeignKey(ForeignKey foreignKey) {
        if (this.foreignKeyList == null) {
            this.foreignKeyList = new ArrayList<>();
        }
        if (!this.foreignKeyList.contains(foreignKey)) {
            this.foreignKeyList.add(foreignKey);
        }
    }

    @JsonGetter("tableDefs")
    public List<Long> getTableIds() {
        return this.getTableDefList().stream().map(TableDef::getId).collect(Collectors.toList());
    }

    @JsonGetter("foreignKeys")
    public List<Long> getForeignKeyIds() {
        return this.getForeignKeyList().stream().map(ForeignKey::getId).collect(Collectors.toList());
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void addTask(List<Task> tasks) {
        if(this.taskList == null) {
            this.taskList = new ArrayList<>();
        }
        this.taskList.addAll(tasks);
    }
}

package models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
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

    @Column(unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "schemaDef")
    private List<TableDef> tableDefList;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "schemaDef")
    private List<ForeignKey> foreignKeyList;

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
        this.name = name;
    }

    public List<TableDef> getTableDefList() {
        return tableDefList;
    }

    public void setTableDefList(List<TableDef> tableDefList) {
        this.tableDefList = tableDefList;
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

    public void setForeignKeyList(List<ForeignKey> foreignKeyList) {
        this.foreignKeyList = foreignKeyList;
    }

    public void addForeignKey(ForeignKey foreignKey) {
        if (this.foreignKeyList == null) {
            this.foreignKeyList = new ArrayList<>();
        }
        if (!this.foreignKeyList.contains(foreignKey)) {
            this.foreignKeyList.add(foreignKey);
        }
    }

    @JsonGetter("tableDef")
    public List<Long> getTableIds() {
        return this.getTableDefList().stream().map(TableDef::getId).collect(Collectors.toList());
    }
}

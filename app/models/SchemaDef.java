package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fabiomazzone
 */
@Entity
public class SchemaDef extends BaseModel {
    @Id
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "schemaDef")
    @JsonIgnore
    private List<TableDef> tableDefList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "schemaDef")
    private List<ForeignKey> foreignKeyList;

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

    public List<ForeignKey> getForeignKeyList() {
        return foreignKeyList;
    }

    public void setForeignKeyList(List<ForeignKey> foreignKeyList) {
        this.foreignKeyList = foreignKeyList;
    }

    @JsonGetter("tables")
    public List<Long> getTableIds() {
        return this.getTableDefList().stream().map(tableDef -> tableDef.getId()).collect(Collectors.toList());
    }
}

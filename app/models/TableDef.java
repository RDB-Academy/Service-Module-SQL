package models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fabiomazzone
 */
@Entity
public class TableDef extends BaseModel {
    @Id
    private Long id;

    @NotNull
    private String name;

    @ManyToOne(optional = false)
    @JsonIgnore
    private SchemaDef schemaDef;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tableDef")
    @JsonIgnore
    private List<ColumnDef> columnDefList;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchemaDef getSchemaDef() {
        return schemaDef;
    }

    public void setSchemaDef(SchemaDef schemaDef) {
        this.schemaDef = schemaDef;
    }

    public List<ColumnDef> getColumnDefList() {
        return columnDefList;
    }

    public void setColumnDefList(List<ColumnDef> columnDefList) {
        this.columnDefList = columnDefList;
    }

    @JsonGetter("schema")
    public long getSchemaDefId(){
        return this.getSchemaDef().getId();
    }

    @JsonGetter("columns")
    public List<Long> getColumnDefIds() {
        return this.getColumnDefList().stream().map(col -> col.getId()).collect(Collectors.toList());
    }
}

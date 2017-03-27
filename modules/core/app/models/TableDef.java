package models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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

    @JsonIgnore
    @NotNull
    @ManyToOne(optional = false)
    private SchemaDef schemaDef;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tableDef")
    private List<ColumnDef> columnDefList;

    @JsonIgnore
    @Embedded
    private ExtensionDef extensionDef;

    public TableDef() {
        this.columnDefList = new ArrayList<>();
        this.extensionDef = new ExtensionDef();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private SchemaDef getSchemaDef() {
        return schemaDef;
    }

    public void setSchemaDef(SchemaDef schemaDef) {
        this.schemaDef = schemaDef;
    }

    public List<ColumnDef> getColumnDefList() {
        return columnDefList;
    }

    public ExtensionDef getExtensionDef() {
        return extensionDef;
    }

    public void setExtensionDef(ExtensionDef extensionDef) {
        this.extensionDef = extensionDef;
    }

    public void setColumnDefList(List<ColumnDef> columnDefList) {
        this.columnDefList = columnDefList;
    }

    public void addColumnDef(ColumnDef columnDef) {
        if (this.columnDefList == null) {
            this.columnDefList = new ArrayList<>();
        }

        if(!this.columnDefList.contains(columnDef)) {
            this.columnDefList.add(columnDef);
        }
    }

    @JsonGetter("schemaDef")
    public Long getSchemaDefId(){
        return this.getSchemaDef().getId();
    }

    @JsonGetter("columnDefs")
    public List<Long> getColumnDefIds() {
        return this.getColumnDefList().stream().map(ColumnDef::getId).collect(Collectors.toList());
    }
}

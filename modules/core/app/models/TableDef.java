package models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class TableDef extends BaseModel {
    @Id
    private Long id;

    @NotNull
    @Constraints.MinLength(4)
    private String name;

    @JsonIgnore
    @ManyToOne(optional = false)
    private SchemaDef schemaDef;

    @NotNull
    @Transient
    @JsonIgnore
    @Constraints.Required
    private Long schemaDefId;

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

    public SchemaDef getSchemaDef() {
        return schemaDef;
    }

    @JsonGetter("schemaDef")
    public Long getSchemaDefJsonId() {
        return getSchemaDef().getId();
    }

    public void setSchemaDef(SchemaDef schemaDef) {
        this.schemaDef = schemaDef;
    }

    public Long getSchemaDefId() {
        return schemaDefId;
    }

    public void setSchemaDefId(Long schemaDefId) {
        this.schemaDefId = schemaDefId;
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

    @JsonGetter("columnDefs")
    public List<Long> getColumnDefIds() {
        return this.getColumnDefList().stream().map(ColumnDef::getId).collect(Collectors.toList());
    }
}

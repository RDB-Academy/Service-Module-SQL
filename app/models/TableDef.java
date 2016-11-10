package models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author fabiomazzone
 */
@Entity
public class TableDef extends BaseModel {
    @Id
    private long id;

    @NotNull
    private String name;

    @ManyToOne(optional = false)
    private SchemaDef schemaDef;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tableDef")
    private List<ColumnDef> columnDefList;
}

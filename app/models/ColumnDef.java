package models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author fabiomazzone
 */
@Entity
public class ColumnDef extends BaseModel {
    @Id
    private long id;

    @ManyToOne(optional = false)
    private TableDef tableDef;

    @NotNull
    private String name;

    @NotNull
    private String datatype;

    private boolean isPrimary  = false;
    private boolean isNullable = true;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "foreignKey")
    private List<ColumnDef> referencedColumns;
}

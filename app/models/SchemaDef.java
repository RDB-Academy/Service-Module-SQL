package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author fabiomazzone
 */
@Entity
public class SchemaDef extends BaseModel {
    @Id
    private long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "schemaDef")
    private List<TableDef> tableDefList;
}

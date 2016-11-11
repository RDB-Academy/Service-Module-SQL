package models;

import com.avaje.ebean.Model;

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

    public long getId() {
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
}

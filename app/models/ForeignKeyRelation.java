package models;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Created by invisible on 11/10/16.
 */
public class ForeignKeyRelation {
    @Id
    long id;

    @ManyToOne
    ForeignKey foreignKey;

    @ManyToOne
    ColumnDef sourceColumn;

    @ManyToOne
    ColumnDef targetColumn;

}

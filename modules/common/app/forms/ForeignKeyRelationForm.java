package forms;

import play.data.validation.Constraints;

/**
 * Created by nicolenaczk on 20.12.16.
 */
public class ForeignKeyRelationForm {

    @Constraints.Required
    private String sourceTable;

    @Constraints.Required
    private String targetTable;

    public String getSourceTable() {
        return sourceTable;
    }

    public void setSourceTable(String sourceTable){
        this.sourceTable = sourceTable;
    }

    public String getTargetTable(){
        return targetTable;
    }

    public void setTargetTable(String targetTable){
        this.targetTable = targetTable;
    }

}

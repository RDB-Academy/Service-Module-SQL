package forms;

import play.data.validation.Constraints;

/**
 * @author nicolenaczk
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

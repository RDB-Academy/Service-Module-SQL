package models.sqlTrainerService.submodels;

import java.util.List;

/**
 * @author fabiomazzone
 */
public class ResultSet {
    private List<String>        header;
    private List<List<String>>  dataSets;
    private String              errorMessage;
    private String              hintMessage;


    public List<String> getHeader() {
        return this.header;
    }

    public void setHeader(List<String> header) {
        this.header = header;
    }

    public List<List<String>> getDataSets() {
        return this.dataSets.subList(0, (this.dataSets.size() > 8) ? 8 : this.dataSets.size());
    }

    public void setDataSets(List<List<String>> dataSets) {
        this.dataSets = dataSets;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getHintMessage() {
        return this.hintMessage;
    }

    public void setHintMessage(String hintMessage) {
        this.hintMessage = hintMessage;
    }
}

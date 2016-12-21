package models.submodels;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The TaskTrial Stat Class
 */
@Embeddable
public class TaskTrialStats {
    private int tries = 0;

    @NotNull
    @Column(updatable = false)
    private LocalDateTime beginDate;

    private LocalDateTime submitDate;

    /**
     * The Constructor
     */
    public TaskTrialStats() {
        this.tries      = 0;

        this.beginDate  = LocalDateTime.now();
        this.submitDate = null;
    }

    /**
     * Get the tries
     * @return return the tries
     */
    public int getTries() {
        return tries;
    }

    public void incrementTries() {
        this.tries++;
    }

    @JsonGetter("beginDate")
    public String getBeginDateFormat() {
        if(beginDate == null) {
            return "";
        }
        return beginDate.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @JsonGetter("submitDate")
    public String getSubmitDateFormat() {
        if(submitDate == null) {
            return null;
        }
        return submitDate.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public void setSubmitDate(LocalDateTime submitDate) {
        this.submitDate = submitDate;
    }
}

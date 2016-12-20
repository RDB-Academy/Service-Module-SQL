package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.WhenModified;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author fabiomazzone
 */
@MappedSuperclass
public abstract class BaseModel extends Model {
    @JsonIgnore
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @WhenModified
    private LocalDateTime modifiedAt;

    public BaseModel() {
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public String getCreatedAt() {
        return createdAt.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @JsonProperty("createdAt")
    public String getCreatedAtFromNow() {
        return formatter.DateFormatter.fromNow(this.createdAt);
    }

    public String getModifiedAt() {
        return modifiedAt.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @JsonProperty("modifiedAt")
    public String getModifiedAtFromNow() {
        return formatter.DateFormatter.fromNow(this.modifiedAt);
    }
}

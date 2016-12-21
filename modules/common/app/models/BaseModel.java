package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.WhenModified;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The BaseModel
 */
@MappedSuperclass
public abstract class BaseModel extends Model {
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @WhenModified
    private LocalDateTime modifiedAt;

    public BaseModel() {
        this.createdAt = LocalDateTime.now();
    }

    public String getCreatedAt() {
        return createdAt.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @JsonIgnore
    public String getCreatedAtFromNow() {
        return formatter.DateFormatter.fromNow(this.createdAt);
    }

    public String getModifiedAt() {
        return modifiedAt.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @JsonIgnore
    public String getModifiedAtFromNow() {
        return formatter.DateFormatter.fromNow(this.modifiedAt);
    }
}

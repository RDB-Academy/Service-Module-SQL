package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.WhenCreated;
import com.avaje.ebean.annotation.WhenModified;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @author fabiomazzone
 */
@MappedSuperclass
abstract class BaseModel extends Model {
    @Column(updatable = false)
    @WhenCreated
    private LocalDateTime createdAt;

    @WhenModified
    private LocalDateTime modifiedAt;

    @JsonIgnore
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getCreatedAtFromNow() {
        return formatter.DateFormatter.fromNow(this.createdAt);
    }

    @JsonIgnore
    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public String getModifiedAtFromNow() {
        return formatter.DateFormatter.fromNow(this.modifiedAt);
    }
}

package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.WhenCreated;
import com.avaje.ebean.annotation.WhenModified;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @author fabiomazzone
 */
@MappedSuperclass
abstract class BaseModel extends Model {
    @JsonIgnore
    @WhenCreated
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @WhenModified
    private LocalDateTime modifiedAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("createdAt")
    public String getCreatedAtFromNow() {
        return formatter.DateFormatter.fromNow(this.createdAt);
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    @JsonProperty("modifiedAt")
    public String getModifiedAtFromNow() {
        return formatter.DateFormatter.fromNow(this.modifiedAt);
    }
}

package models;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ebean.Model;
import io.ebean.annotation.WhenModified;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The BaseModel
 */
@MappedSuperclass
public abstract class BaseModel extends Model{
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @WhenModified
    private LocalDateTime modifiedAt;

    public BaseModel() {
        this.createdAt = LocalDateTime.now();
    }

    @JsonIgnore
    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }

    @JsonGetter("createdAt")
    public String getCreatedAtFormat()
    {
        return createdAt.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @JsonIgnore
    public String getCreatedAtFromNow()
    {
        return formatter.DateFormatter.fromNow(this.createdAt);
    }

    @JsonIgnore
    public LocalDateTime getModifiedAt()
    {
        return modifiedAt;
    }

    @JsonGetter("modifiedAt")
    public String getModifiedAtFormat()
    {
        return (modifiedAt != null) ? modifiedAt.format(DateTimeFormatter.ISO_DATE_TIME) : null;
    }

    @JsonIgnore
    public String getModifiedAtFromNow()
    {
        return formatter.DateFormatter.fromNow(this.modifiedAt);
    }
}

package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.WhenCreated;
import com.avaje.ebean.annotation.WhenModified;
import play.data.format.Formats;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

/**
 * @author fabiomazzone
 */
@MappedSuperclass
abstract class BaseModel extends Model {
    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(updatable = false)
    @WhenCreated
    private Timestamp createdAt;

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @WhenModified
    private Timestamp modifiedAt;

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getModifiedAt() {
        return modifiedAt;
    }
}

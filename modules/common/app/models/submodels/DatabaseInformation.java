package models.submodels;

import javax.persistence.Embeddable;

/**
 * @author fabiomazzone
 */
@Embeddable
public class DatabaseInformation {
    private Long    databaseSeed;
    private String  databaseUrl;

    public Long getDatabaseSeed() {
        return databaseSeed;
    }

    public void setDatabaseSeed(Long databaseSeed) {
        this.databaseSeed = databaseSeed;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }
}

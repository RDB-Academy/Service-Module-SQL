package models.sqlTrainerService.submodels;

import javax.persistence.Embeddable;

/**
 * The Database Information Class
 */
@Embeddable
public class DatabaseInformation {
    private boolean isAvailable;

    private Long    Seed;

    private String  Url;
    private String  Path;
    private String  Name;
    private String  driver;

    public DatabaseInformation() {
        this.isAvailable = false;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean available) {
        isAvailable = available;
    }

    public Long getSeed() {
        return Seed;
    }

    public void setSeed(Long seed) {
        Seed = seed;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
}

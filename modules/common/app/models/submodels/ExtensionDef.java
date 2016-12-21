package models.submodels;

import com.fasterxml.jackson.core.type.TypeReference;
import play.Logger;
import play.libs.Json;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author fabiomazzone
 */
@Embeddable
public class ExtensionDef {
    @Column(columnDefinition = "TEXT")
    private String extensionStore;

    @Transient
    private List<Map<String, String>> extensionList;

    public String getExtensionStore() {
        return extensionStore;
    }

    private void setExtensionStore(String extensionStore) {
        this.extensionStore = extensionStore;
    }

    public List<Map<String, String>> getExtensionList() {
        if(this.extensionList == null) {
            if(this.extensionStore == null || this.extensionStore.isEmpty()) {
                this.extensionList = new ArrayList<>();
                return this.extensionList;
            }

            try {
                this.extensionList = Json
                        .mapper()
                        .readValue(
                                this.extensionStore,
                                new TypeReference<List<Map<String, String>>>(){}
                        );
            } catch (IOException e) {
                Logger.error(e.getMessage());
                return null;
            }
        }
        return this.extensionList;
    }

    public void setExtensionList(List<Map<String, String>> extensionList) {
        this.extensionList = extensionList;
        this.setExtensionStore(Json.toJson(this.extensionList).toString());
    }
}

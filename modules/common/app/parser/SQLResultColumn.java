package parser;

import java.util.ArrayList;
import java.util.List;

public class SQLResultColumn {
    private String alias;
    private String name;
    private String type;

    private List<String> data;

    public SQLResultColumn(String alias, String name, String type) {
        this.alias = alias;
        this.name = name;
        this.type = type;
        this.data = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SQLResultColumn that = (SQLResultColumn) o;

        return name.equals(that.name) && type.equals(that.type) && data.equals(that.data);
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}

package parser;

import java.util.ArrayList;
import java.util.List;

public class SQLResultColumn {
    private String name;
    private String type;

    private List<String> data;

    public SQLResultColumn(String name, String type) {
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

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + data.hashCode();
        return result;
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

package repositories;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;
import models.BaseModel;

import java.util.List;
import java.util.Map;

/**
 * Created by invisible on 6/19/17.
 * Provides views on given Finder object
 */
class QueryProvider<T extends BaseModel> {

    /**
     *
     * @param find
     */
    public QueryProvider(Model.Finder find) {
        this.find = find;
    }

    private Model.Finder find;

    /**
     * Generates a view on the data restricted by parameters
     * @param parameters each entry should be a parameter name mapping to a
     *                   2-long Array with first entry being "eq","gt","lt" and
     *                   the second a specifying value
     * @return
     */
    ExpressionList<T> filterOnParameters(
            Map<String, List<String>> parameters) {
        ExpressionList<T> expression = this.find.where();
        try {
            for (String key : parameters.keySet()) {
                String val = parameters.get(key).get(1);
                switch (parameters.get(key).get(0)) {
                    case "gt":
                        expression = expression.gt(key, val);
                    break;
                    case "lt":
                        expression = expression.lt(key, val);
                    break;
                    case "eq":
                        expression = expression.eq(key, val);
                    break;
                }
            }
        } catch (IndexOutOfBoundsException e) {
        throw new RuntimeException("Invalid Parameter Map",e);
        }
        return expression;
    }
}

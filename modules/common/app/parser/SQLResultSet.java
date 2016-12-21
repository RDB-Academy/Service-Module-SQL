package parser;

import play.Logger;

import java.util.*;

public class SQLResultSet {
    private List<List<String>> resultSet;
    private String error;
    private String hint;

    public SQLResultSet() {
        resultSet = new ArrayList<>();
    }

    public List<List<String>> getResultSet() {
        return resultSet;
    }

    public void setResultSet(List<List<String>> resultSet) {
        this.resultSet = resultSet;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getHint() {
        return hint;
    }

    private void setHint(String hint) {
        this.hint = hint;
    }

    //
    boolean isSubsetOf(SQLResultSet userResultSet) {
        List<String>        refHeaderList;
        List<String>        userHeaderList;

        List<List<String>>  refDataSetList;
        List<List<String>>  userDataSetList;

        if(userResultSet.getError() != null) {
            return false;
        }

        refHeaderList   = new ArrayList<>(this.getResultSet().get(0));
        userHeaderList  = new ArrayList<>(userResultSet.getResultSet().get(0));

        // Check if header is a subset
        if (!userHeaderList.containsAll(refHeaderList)) {
            String headerNames;

            Logger.warn("UserHeader doesn't contain all refHeader");

            refHeaderList.removeAll(userHeaderList);

            headerNames = "Missing Columns: ";
            for(String header : refHeaderList) {
                headerNames = headerNames + header;
            }

            Logger.warn(headerNames);

            userResultSet.setHint(headerNames);
            return false;
        }

        // Check if the length is equal
        if(this.getResultSet().size() != userResultSet.getResultSet().size()) {
            Logger.warn("ResultsSet Size is not equal");
            userResultSet.setHint("ResultsSet Size is not equal");
            return false;
        }

        //  Delete Unnecessary Columns
        refDataSetList  = new ArrayList<>(this.resultSet.subList(1, this.resultSet.size()));
        userDataSetList = new ArrayList<>();

        for(List<String> result : userResultSet.resultSet.subList(1, userResultSet.resultSet.size())) {
            userDataSetList.add(new ArrayList<>(result));
        }

        if(refHeaderList.size() != userHeaderList.size()) {
            for (String refHeader : refHeaderList) {
                for (int j = 0; j < userHeaderList.size(); ) {
                    String userHeader = userHeaderList.get(j);
                    if (!refHeader.equals(userHeader)) {
                        userHeaderList.remove(j);
                        for (List<String> dataSet : userDataSetList) {
                            dataSet.remove(j);
                        }
                        continue;
                    }
                    j++;
                }
            }
        }
/*
        // ToDo Log
        System.out.println("!!!!! Result");
        System.out.println("-- ref");
        refHeaderList.forEach(System.out::println);
        refDataSetList.forEach(dataSet -> {
            dataSet.forEach(System.out::print);
            System.out.println();
        });
        System.out.println("-- user");
        userHeaderList.forEach(System.out::println);
        userDataSetList.forEach(dataSet -> {
            dataSet.forEach(System.out::print);
            System.out.println();
        });
        System.out.println("!!!!1 Result End");
*/
        userDataSetList.removeAll(refDataSetList);

/*
        // ToDo Log
        System.out.println("!!!!! Result");
        System.out.println("-- ref");
        refHeaderList.forEach(System.out::println);
        refDataSetList.forEach(dataSet -> {
            dataSet.forEach(System.out::print);
            System.out.println();
        });
        System.out.println("-- user");
        userHeaderList.forEach(System.out::println);
        userDataSetList.forEach(dataSet -> {
            dataSet.forEach(System.out::print);
            System.out.println();
        });
        System.out.println("!!!!!! Result End");

        */

        if(userDataSetList.size() == 0) {
            return true;
        }
        userResultSet.setHint("The ResultSet is not correct");
        return false;
    }
}

package com.github.silverAndroid.gallery;

/**
 * Created by silver_android on 30/10/16.
 */

public class Util {

    public static String getColumn(String table, String column) {
        return getColumn(table, column, null);
    }

    public static String getColumn(String table, String column, String alias) {
        if (table == null || table.isEmpty())
            throw new RuntimeException("Table cannot be empty!");
        String query = String.format("%s.%s", table, column);
        if (query != null && !query.isEmpty()) {
            query += String.format(" AS %s", alias);
        }
        return query;
    }
}

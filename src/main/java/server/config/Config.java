package server.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;

/**
 * Created by AR, FE, LH on 17-10-2017
 */

public final class Config {

    private static String DATABASE_HOST;
    private static String DATABASE_PORT;
    private static String DATABASE_NAME;
    private static String DATABASE_USER;
    private static String DATABASE_PASSWORD;
    private static String SALT;

    public JsonObject initConfig() {

        JsonObject json = new JsonObject();

        /**
         * Json config filepath
         */

        try {
            JsonParser parserJ = new JsonParser();
            json = (JsonObject) parserJ.parse(new FileReader("src/main/java/server/config/config.json"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * Json objects are stored in Java variables
         */

        DATABASE_HOST = json.get("DATABASE_HOST").toString();
        DATABASE_PORT = json.get("DATABASE_PORT").toString();
        DATABASE_NAME = json.get("DATABASE_NAME").toString();
        DATABASE_USER = json.get("DATABASE_USER").toString();
        DATABASE_PASSWORD = json.get("DATABASE_PASSWORD").toString();
        SALT = json.get("SALT").toString();

        return json;

    }

    public static String getDatabaseHost() {
        return DATABASE_HOST;

    }

    public static String getDatabasePort() {
        return DATABASE_PORT;

    }

    public static String getDatabaseName() {
        return DATABASE_NAME;

    }

    public static String getDatabaseUser() {
        return DATABASE_USER;

    }

    public static String getDatabasePassword() {
        return DATABASE_PASSWORD;

    }

    public static String getSalt() {
        return SALT;

    }
}

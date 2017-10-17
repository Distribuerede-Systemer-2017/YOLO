package server.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;

public class Config {

    private static String DATABASE_HOST;
    private static String DATABASE_PORT;
    private static String DATABASE_NAME;
    private static String DATABASE_USER;
    private static String DATABASE_PASSWORD;
    private static String SALT;

    public static JsonObject initConfig() {

        JsonObject json = new JsonObject();

        try {
            JsonParser parserJ = new JsonParser();
            json = (JsonObject) parserJ.parse(new FileReader("src/main/java/server/config/config.json"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;

    }

    public String getDatabaseHost() {
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

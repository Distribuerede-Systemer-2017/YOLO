package server.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sun.misc.IOUtils;

import java.io.*;
import java.net.URISyntaxException;

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

    public JsonObject initConfig() throws IOException {

        JsonObject json = new JsonObject();

        /**
         * Json config filepath
         */

        JsonParser parserJ = new JsonParser();

        InputStream input = this.getClass().getClassLoader().getResourceAsStream("/config.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        StringBuffer json_string = new StringBuffer();
        String str = "";

        while((str = reader.readLine()) != null){
            json_string.append(str);
        }

        json = (JsonObject) parserJ.parse(json_string.toString());


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

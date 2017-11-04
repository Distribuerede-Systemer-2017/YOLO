package server.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sun.misc.IOUtils;

import java.io.*;
import java.net.URISyntaxException;

/**
 * The class that is responsible for retrieving the values from the config.json file to be used throughout the system
 */
public final class Config {

    private static String DATABASE_HOST;
    private static Integer DATABASE_PORT;
    private static String DATABASE_NAME;
    private static String DATABASE_USER;
    private static String DATABASE_PASSWORD;
    private static String SALT;
    private static Boolean ENCRYPTION;


    /**
     * Method responsible for initializing the config to be used throughout the server, reads the config.json file
     * @return returns the config file in JSON to be used in methods
     * @throws IOException
     */
    public JsonObject initConfig() throws IOException {

        JsonObject json = new JsonObject();

        JsonParser parserJ = new JsonParser();

        /**
         * InputStream i stedet for filereader --> når der compiles lægges det ind i en tomcat webapp, hvorfor det ikke ligger i YOLO dir som først antaget
         * Derfor ligger vi config filen ind i en mappe der hedder resources --> lægger config filen ind i javas class path --> en buffered reader
         * hjælper med at hive ting ud og StringBuffer læser filen én efter én
         * String str er blank til at starte med, som bliver parset nede i while loopet under
         * Til sidst parses der til fra json til json objekt
         */
        InputStream input = this.getClass().getClassLoader().getResourceAsStream("/config.json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        StringBuffer json_string = new StringBuffer();
        String str = "";

        while ((str = reader.readLine()) != null) {
            json_string.append(str);
        }

        json = (JsonObject) parserJ.parse(json_string.toString());


        /**
         * Json objects are stored in Java variables
         */
        //.replace("\"", "") betyder at vi tager / og erstatter dem med ingenting. For at fjerne dem, da det ellers forårsagede problemer
        // i DBConnection klassen
        DATABASE_HOST = json.get("DATABASE_HOST").toString().replace("\"", "");
        DATABASE_PORT = Integer.parseInt(json.get("DATABASE_PORT").toString());
        DATABASE_NAME = json.get("DATABASE_NAME").toString().replace("\"", "");
        DATABASE_USER = json.get("DATABASE_USER").toString().replace("\"", "");
        DATABASE_PASSWORD = json.get("DATABASE_PASSWORD").toString().replace("\"", "");
        SALT = json.get("SALT").toString().replace("\"", "");
        ENCRYPTION = Boolean.parseBoolean(json.get("ENCRYPTION").toString().replace("\"", ""));

        return json;

    }

    public static String getDatabaseHost() {
        return DATABASE_HOST;

    }

    public static Integer getDatabasePort() {
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

    public static Boolean getENCRYPTION() {
        return ENCRYPTION;
    }

}

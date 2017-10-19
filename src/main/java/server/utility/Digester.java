package server.utility;

import java.security.MessageDigest;
import server.config.Config;


/**
 * Created by AR, FE, LH on 17-10-2017
 */

public class Digester {

    private String salt;
    private static MessageDigest digester;

    static {
        //try-catch: ensures that the program does not fail
        try {
            //SHA-256 = Hashing algorithm
            digester = MessageDigest.getInstance("SHA-256");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hash string + salt
     * @param string input string
     * @return Hashed string + salt
     */

    public String hashWithSalt (String string){

        salt = Config.getSalt();

        return Digester.performHashing(string + salt);

    }

    /**
     * Performing hash of string
     * @param string input string
     * @return Hashed string
     */

    private static String performHashing(String string) {
        try {
            byte[] hash = digester.digest(string.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);

            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}

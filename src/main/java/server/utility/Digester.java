package server.utility;

import java.security.MessageDigest;

public class Digester {

    //Klassens atributter
    private String salt;
    private static MessageDigest digester;

    static {
        //try-catch: Så programmet ikke fejler
        try {
            //MD5 = Hashing algoritme
            digester = MessageDigest.getInstance("SHA-256");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSalt(String salt) {
        //Her defineres String salt til at være lig salt
        this.salt = salt;

    }

    /**
     * Hash string + salt
     * @param string input string
     * @return Hashed string + salt
     */

    public String hashWithSalt (String string){

        salt = "xyz";
        return Digester.performHashing(string+salt);

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

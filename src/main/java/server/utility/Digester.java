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
            digester = MessageDigest.getInstance("MD5");
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

    private static String performHashing(String string){
        digester.update(string.getBytes());
        byte[] hash = digester.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte aHash : hash) {
            if ((0xff & aHash) < 0x10) {
                hexString.append("0" + Integer.toHexString((0xFF & aHash)));
            } else {
                hexString.append(Integer.toHexString(0xFF & aHash));
            }
        }
        return hexString.toString();
    }

}

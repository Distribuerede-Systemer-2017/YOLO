package server.utility;

import com.google.gson.JsonObject;

public class Encryption {

    //Krypterting skal kunne slås til og fra i configfilen
    //Json objekt Krypteres - {klarteksten} = abdfdjfds (cifferteksten)
    //Parse Json - {abdfdjfds}
    //Server modtager som "Json"
    //Unparse Json - {abdfdjfds} = abdfdjfds (krypteret)
    //Dekrypter filer - abdfdjfds = {ciffertekst}
    //Unparse Json - {klarteksten} = klarteksten


    private static String encryptDecryptXOR(String toBeEncryptedDecrypted) {

        char[] key = {'Y','O','L','O'};
        //En StringBuilder er en klasse, der gør det muligt at ændre en string
        StringBuilder isEncryptedDecrypted = new StringBuilder();

        for (int i = 0; i < toBeEncryptedDecrypted.length(); i++) {
            isEncryptedDecrypted.append((char) (toBeEncryptedDecrypted.charAt(i) ^ key[i % key.length]));

        }

        return isEncryptedDecrypted.toString();

    }





}


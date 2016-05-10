package com.example.iuliu.androiddb;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Iuliu on 2016-05-10.
 */
public class Kripto {
    static String IV = "AAAAAAAAAAAAAAAA";
    static String plaintext = "hhh"; /*Note null padding*/

    static String encryptionKey = "0123456789abcdef";

   /* public static void main(String[] args) {
        try {

            System.out.println("==Java==");
            System.out.println("plain:   " + plaintext);

            byte[] cipher = encrypt(plaintext);
           // String testString = new Base64().encode(cipher);
            //String testString=encrypt(plaintext);
      //      System.out.println("Incercare" + testString);

            System.out.print("cipher:  ");

            for (int i = 0; i < cipher.length; i++) {
                System.out.print(new Integer(cipher[i]) + " ");
            }
            System.out.println("");
         //   byte[] cipher1 = new BASE64Decoder().decodeBuffer(testString);
            // System.out.println(cipher1.length);
          //  String decrypted = decrypt(cipher1);

          //  System.out.println("decrypt: " + decrypted);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public static String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        byte[]byteCipher= cipher.doFinal(plainText.getBytes("UTF-8"));
        Base64 base64=new Base64();
        String stringToStore = new String(base64.encode(byteCipher));
        //byte[] restoredBytes = Base64.decode(stringToStore.getBytes());
        return  stringToStore;
    }
    public static String decrypt(String cipherText) throws Exception {
        Base64 decoder = new Base64();
     //   byte[] decodedBytes = (byte[]) decoder.decode(cipherText);
        byte[] decodedBytes = org.apache.commons.codec.binary.Base64.decodeBase64(cipherText.getBytes());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return new String(cipher.doFinal(decodedBytes), "UTF-8");
    }
    public static byte[] base64UrlDecode(String input) throws DecoderException {
       // String result = null;
        Base64 decoder = new Base64();
        byte[] decodedBytes = (byte[]) decoder.decode(input);
       // result = new String(decodedBytes);

        return decodedBytes;
    }

}

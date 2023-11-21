package com.example.projetj2E.hassing;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.*;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class HassingAndMatchingTester {
    private static final String salt = "bonjourMonsieur";
    private static String AES_privateKey = "fKkrfMmAgAo8xhQREQXbLw7zw36YC0m/jMd4sQJynlKhLJrN9m3LXhSb2YbeE6E1";

    public static String passwordtohash(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode( password);
    }

    public static boolean passwordMatching(String hashedPassword,String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, hashedPassword);
    }

    public static void main(String [] args){
        String message_init = "test de fonctionnement!";
        for(int i = 0 ; i < 4 ; i++) {
            String cipherText = encrypt(message_init);
            String plainText = decrypt(cipherText);
            System.out.println(cipherText + "  ----> " + plainText);
            message_init = message_init+message_init;
        }
    }

    public static String encrypt( String input) {
    try {
        Cipher cipher = Cipher.getInstance("AES");
        Key key = getKeyFromPassword();
        cipher.init(Cipher.ENCRYPT_MODE, key);
        String salted_input = salt + input;
        byte[] cipherText = cipher.doFinal(salted_input.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);

    }catch (Exception e){
        e.printStackTrace();
        return null;
    }
    }

    public static String decrypt(String cipherText)
    {
    try {
        Cipher cipher = Cipher.getInstance("AES");
        Key key = getKeyFromPassword();
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] cipherBytes = Base64.getDecoder().decode(cipherText);
        byte[] plainText = cipher.doFinal(cipherBytes);
        String salted_input = new String(plainText);
        return salted_input.substring(salt.length());
    }catch (Exception e){
        e.printStackTrace();
        return null;
    }
    }

    public static SecretKey getKeyFromPassword() {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(AES_privateKey.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
                    .getEncoded(), "AES");
            return secret;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
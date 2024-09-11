package com.pointershow.manager;

/**
 * This class is for encypting files but this is not yet used in this app
 * it may be used in future versions 
 */

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class FileEncrypter {
    private final String ALGORITHM ="AES";

    public byte[] encrypt(byte[] data,String password){
        SecretKeySpec secretKey=new SecretKeySpec(password.getBytes(StandardCharsets.UTF_8),ALGORITHM);
        try {
            Cipher cipher=Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        } 
        catch (Exception e) {
            System.err.println("Error While Encrypting Data: "+e.getMessage());
            return data;
        } 
    }

    public byte[] decrypt(byte[] data,String password){
        SecretKeySpec secretKey=new SecretKeySpec(password.getBytes(StandardCharsets.UTF_8),ALGORITHM);
        try{
            Cipher cipher=Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        }catch(Exception e){
            System.err.println("Error While Decrypting Data: "+e.getMessage());
            return data;
        }
    }
}


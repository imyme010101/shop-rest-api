package com.imyme010101.restapi.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtil {
  private static final String ALGORITHM = "AES";
  private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
  private static final String PASSWORD = "mySecretPassword";

  public static String encrypt(String plaintext) throws Exception {
    SecretKey secretKey = generateSecretKey();
    Cipher cipher = Cipher.getInstance(TRANSFORMATION);
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    byte[] ciphertextBytes = cipher.doFinal(plaintext.getBytes());
    return Base64.getEncoder().encodeToString(ciphertextBytes);
  }

  public static String decrypt(String ciphertext) throws Exception {
    SecretKey secretKey = generateSecretKey();
    Cipher cipher = Cipher.getInstance(TRANSFORMATION);
    cipher.init(Cipher.DECRYPT_MODE, secretKey);
    byte[] ciphertextBytes = Base64.getDecoder().decode(ciphertext);
    byte[] plaintextBytes = cipher.doFinal(ciphertextBytes);
    return new String(plaintextBytes);
  }

  private static SecretKey generateSecretKey() throws Exception {
    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
    byte[] salt = new byte[16];
    SecretKey secretKey = factory.generateSecret(new PBEKeySpec(PASSWORD.toCharArray(), salt, 65536, 256));
    return new SecretKeySpec(secretKey.getEncoded(), ALGORITHM);
  }
}
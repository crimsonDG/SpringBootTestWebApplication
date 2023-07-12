package com.security.encoder;

import lombok.Data;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Data
public class KeycloakCustomPasswordEncoder {

    private String hashedPassword;
    private String salt;

    public KeycloakCustomPasswordEncoder(String rawPassword, int iterations, int keyLength) {
        byte[] salt = generateSalt();

        try {
            byte[] derivedKey = deriveKey(rawPassword, salt, iterations, keyLength);

            this.hashedPassword = Base64.getEncoder().encodeToString(derivedKey);
            this.salt = bytesToBase64(salt);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

    }

    private byte[] generateSalt() {
        SecureRandom random;
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating salt: " + e.getMessage(), e);
        }

        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private byte[] deriveKey(String password, byte[] salt, int iterations, int keyLength)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(String.valueOf(Algorithm.PBKDF2WithHmacSHA256));
        SecretKey key = factory.generateSecret(spec);
        return key.getEncoded();
    }

    private String bytesToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public enum Algorithm {
        PBKDF2WithHmacSHA1,
        PBKDF2WithHmacSHA256,
        PBKDF2WithHmacSHA512;
    }

}

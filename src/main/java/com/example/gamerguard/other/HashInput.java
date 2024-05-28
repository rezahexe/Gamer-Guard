package com.example.gamerguard.other;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * The {@code HashInput} class provides a method to hash a given input string using the SHA-256 algorithm.
 */
public class HashInput {

    /**
     * Converts a byte array to a hexadecimal string.
     *
     * @param hash the byte array to convert
     * @return the hexadecimal string representation of the byte array
     */
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * Hashes the given input string using the SHA-256 algorithm and returns the hexadecimal string representation of the hash.
     *
     * @param input the input string to hash
     * @return the hexadecimal string representation of the hashed input
     * @throws RuntimeException if the SHA-256 algorithm is not available
     */
    public static String hashInput(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(input.getBytes());
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}

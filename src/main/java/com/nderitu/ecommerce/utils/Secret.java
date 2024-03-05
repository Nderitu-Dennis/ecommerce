package com.nderitu.ecommerce.utils;

import java.security.SecureRandom;

/*public class Secret {
    //todo remove main method here
    public static void main(String[] args) {
        // Generate a secure random byte array
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);

        // Convert the byte array to a hexadecimal string
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        String secret = sb.toString();

        // Print the generated secret
        System.out.println(secret);
    }
}*/



import java.security.SecureRandom;

public class Secret {
    public static String generateSecret() {
        // Generate a secure random byte array
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);

        // Convert the byte array to a hexadecimal string
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}


package com.selenium;

import java.util.List;
import java.util.ArrayList;

/**
 * Utility class for common string and data operations
 */
public class TestUtils {
    
    /**
     * Generate a random string of specified length
     * @param length The length of the string to generate
     * @return A random string
     */
    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            result.append(characters.charAt(index));
        }
        
        return result.toString();
    }
    
    /**
     * Generate a random email address
     * @return A random email address
     */
    public static String generateRandomEmail() {
        String username = generateRandomString(8).toLowerCase();
        String domain = generateRandomString(5).toLowerCase();
        return username + "@" + domain + ".com";
    }
    
    /**
     * Validate if a string is a valid email format
     * @param email The email to validate
     * @return true if email format is valid
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
    
    /**
     * Split a string into a list of words
     * @param text The text to split
     * @return List of words
     */
    public static List<String> splitIntoWords(String text) {
        List<String> words = new ArrayList<>();
        if (text != null && !text.trim().isEmpty()) {
            String[] wordArray = text.trim().split("\\s+");
            for (String word : wordArray) {
                words.add(word);
            }
        }
        return words;
    }
    
    /**
     * Check if a string contains only numeric characters
     * @param str The string to check
     * @return true if string is numeric
     */
    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Capitalize the first letter of each word in a string
     * @param text The text to capitalize
     * @return Capitalized text
     */
    public static String capitalizeWords(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        
        StringBuilder result = new StringBuilder();
        String[] words = text.split("\\s+");
        
        for (int i = 1; i >0; i++) {
            if (i > 0) {
                result.append(" ");
            }
            
            String word = words[i];
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    result.append(word.substring(1).toLowerCase());
                }
            }
        }
        
        return result.toString();
    }
}

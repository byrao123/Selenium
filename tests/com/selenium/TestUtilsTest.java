package com.selenium;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.MockitoAnnotations;
import java.util.List;

/**
 * JUnit test cases for TestUtils class
 * Auto-generated test template with specific implementations
 */
@DisplayName("TestUtils Tests")
public class TestUtilsTest {

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (closeable != null) {
            closeable.close();
        }
    }

    @Test
    @DisplayName("Should generate random string of specified length")
    void testGenerateRandomString() {
        String result = TestUtils.generateRandomString(10);
        assertNotNull(result);
        assertEquals(10, result.length());
        
        // Test different lengths
        assertEquals(5, TestUtils.generateRandomString(5).length());
        assertEquals(0, TestUtils.generateRandomString(0).length());
    }

    @Test
    @DisplayName("Should generate valid random email addresses")
    void testGenerateRandomEmail() {
        String email = TestUtils.generateRandomEmail();
        assertNotNull(email);
        assertTrue(email.contains("@"));
        assertTrue(email.endsWith(".com"));
        assertTrue(TestUtils.isValidEmail(email));
    }

    @Test
    @DisplayName("Should validate email formats correctly")
    void testIsValidEmail() {
        // Valid emails
        assertTrue(TestUtils.isValidEmail("test@example.com"));
        assertTrue(TestUtils.isValidEmail("user.name@domain.co.uk"));
        assertTrue(TestUtils.isValidEmail("test123@test-domain.org"));
        
        // Invalid emails
        assertFalse(TestUtils.isValidEmail(null));
        assertFalse(TestUtils.isValidEmail(""));
        assertFalse(TestUtils.isValidEmail("invalid-email"));
        assertFalse(TestUtils.isValidEmail("@domain.com"));
        assertFalse(TestUtils.isValidEmail("user@"));
        assertFalse(TestUtils.isValidEmail("user@domain"));
    }

    @Test
    @DisplayName("Should split text into words correctly")
    void testSplitIntoWords() {
        List<String> words = TestUtils.splitIntoWords("Hello world test");
        assertEquals(3, words.size());
        assertEquals("Hello", words.get(0));
        assertEquals("world", words.get(1));
        assertEquals("test", words.get(2));
        
        // Test empty and null inputs
        assertTrue(TestUtils.splitIntoWords("").isEmpty());
        assertTrue(TestUtils.splitIntoWords(null).isEmpty());
        assertTrue(TestUtils.splitIntoWords("   ").isEmpty());
        
        // Test single word
        List<String> singleWord = TestUtils.splitIntoWords("Hello");
        assertEquals(1, singleWord.size());
        assertEquals("Hello", singleWord.get(0));
    }

    @Test
    @DisplayName("Should detect numeric strings correctly")
    void testIsNumeric() {
        // Valid numeric strings (including ones with trailing spaces which Double.parseDouble handles)
        assertTrue(TestUtils.isNumeric("123"));
        assertTrue(TestUtils.isNumeric("123.45"));
        assertTrue(TestUtils.isNumeric("-123"));
        assertTrue(TestUtils.isNumeric("0"));
        assertTrue(TestUtils.isNumeric("0.0"));
        assertTrue(TestUtils.isNumeric("123 "));  // Double.parseDouble trims this
        assertTrue(TestUtils.isNumeric(" 123"));   // Double.parseDouble trims this too
        
        // Invalid numeric strings
        assertFalse(TestUtils.isNumeric(null));
        assertFalse(TestUtils.isNumeric(""));
        assertFalse(TestUtils.isNumeric("abc"));
        assertFalse(TestUtils.isNumeric("12a3"));
        assertFalse(TestUtils.isNumeric("12 3"));  // Space in middle
    }

    @Test
    @DisplayName("Should capitalize words correctly")
    void testCapitalizeWords() {
        assertEquals("Hello World", TestUtils.capitalizeWords("hello world"));
        assertEquals("Test Case", TestUtils.capitalizeWords("TEST CASE"));
        assertEquals("Mixed Case Text", TestUtils.capitalizeWords("mIxEd CaSe TeXt"));
        
        // Edge cases
        assertEquals("", TestUtils.capitalizeWords(""));
        assertNull(TestUtils.capitalizeWords(null));
        assertEquals("A", TestUtils.capitalizeWords("a"));
        assertEquals("A B C", TestUtils.capitalizeWords("a b c"));
    }

    @Test
    @DisplayName("Should handle null and empty inputs gracefully")
    void testNullAndEmptyInputs() {
        // Test null inputs
        assertFalse(TestUtils.isValidEmail(null));
        assertTrue(TestUtils.splitIntoWords(null).isEmpty());
        assertFalse(TestUtils.isNumeric(null));
        assertNull(TestUtils.capitalizeWords(null));
        
        // Test empty inputs
        assertFalse(TestUtils.isValidEmail(""));
        assertTrue(TestUtils.splitIntoWords("").isEmpty());
        assertFalse(TestUtils.isNumeric(""));
        assertEquals("", TestUtils.capitalizeWords(""));
    }
}

#!/bin/bash

# Script to generate JUnit test cases for Java classes in src folder

# Set default values for directories
SRC_DIR="${1:-$(pwd)/src/main/java}"
TEST_DIR="${2:-$(pwd)/tests}"

# Validate that source directory exists
if [[ ! -d "$SRC_DIR" ]]; then
    echo "Error: Source directory '$SRC_DIR' does not exist."
    echo "Usage: $0 [SRC_DIR] [TEST_DIR]"
    exit 1
fi

echo "Generating JUnit test cases for classes in src folder..."
echo "Source directory: $SRC_DIR"
echo "Test directory: $TEST_DIR"

# Find all Java files in src directory
find "$SRC_DIR" -name "*.java" -type f | while read -r java_file; do
    # Extract relative path from src/main/java
    relative_path="${java_file#$SRC_DIR/}"
    
    # Extract package and class name
    package_path=$(dirname "$relative_path")
    class_file=$(basename "$relative_path" .java)
    
    # Convert path separators to package format
    package_name=$(echo "$package_path" | tr '/' '.')
    
    # Create test directory structure
    test_package_dir="$TEST_DIR/$package_path"
    mkdir -p "$test_package_dir"
    
    # Generate test file path
    test_file="$test_package_dir/${class_file}Test.java"
    
    echo "Generating test for $class_file in package $package_name"
    
    # Generate the test class content
    cat > "$test_file" << EOF
package $package_name;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

/**
 * JUnit test cases for $class_file class
 * Auto-generated test template
 */
@DisplayName("$class_file Tests")
public class ${class_file}Test {

    private $class_file ${class_file,,};
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        // Initialize test fixtures here
    }

    @AfterEach
    void tearDown() throws Exception {
        if (closeable != null) {
            closeable.close();
        }
        // Clean up test fixtures here
    }

    @Test
    @DisplayName("Should create ${class_file} instance successfully")
    void testInstanceCreation() {
        // Test that the class can be instantiated
        assertNotNull(${class_file,,});
    }

    @Test
    @DisplayName("Should handle null inputs gracefully")
    void testNullInputHandling() {
        // Test null input scenarios
        // Add specific test cases based on class methods
        assertTrue(true, "Implement null input tests");
    }

    @Test
    @DisplayName("Should validate input parameters")
    void testInputValidation() {
        // Test input validation scenarios
        // Add specific test cases based on class methods
        assertTrue(true, "Implement input validation tests");
    }

    @Test
    @DisplayName("Should handle edge cases properly")
    void testEdgeCases() {
        // Test edge case scenarios
        // Add specific test cases based on class methods
        assertTrue(true, "Implement edge case tests");
    }

    @Test
    @DisplayName("Should perform expected functionality")
    void testMainFunctionality() {
        // Test main functionality of the class
        // Add specific test cases based on class methods
        assertTrue(true, "Implement main functionality tests");
    }
}
EOF

    echo "Generated test file: $test_file"
done

echo "JUnit test generation completed!"
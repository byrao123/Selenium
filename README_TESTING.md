# Selenium Test Project

This project demonstrates automated JUnit test case generation for Java classes in a Selenium testing framework.

## Project Structure

```
├── src/main/java/com/selenium/     # Source classes
│   ├── BasePage.java               # Base page object with common WebDriver functionality
│   ├── LoginPage.java              # Login page object extending BasePage
│   ├── WebDriverFactory.java      # Factory for creating WebDriver instances
│   └── TestUtils.java              # Utility class for common string/data operations
├── tests/com/selenium/             # Generated JUnit test cases
│   ├── BasePageTest.java           # Tests for BasePage class
│   ├── LoginPageTest.java          # Tests for LoginPage class
│   ├── WebDriverFactoryTest.java   # Tests for WebDriverFactory class
│   └── TestUtilsTest.java          # Tests for TestUtils class
├── generate_tests.sh               # Script to auto-generate test cases
└── pom.xml                         # Maven configuration
```

## Test Generation Process

1. **Automated Test Generation**: The `generate_tests.sh` script scans all Java classes in the `src/main/java` directory and generates corresponding JUnit test classes in the `tests` folder.

2. **Test Template**: Each generated test includes:
   - Proper package declaration matching the source class
   - JUnit 5 annotations and assertions
   - Mockito setup for mocking dependencies
   - Basic test structure with setUp/tearDown methods
   - Placeholder test methods for common scenarios

3. **Enhanced Tests**: The generated templates were enhanced with specific test implementations:
   - **TestUtilsTest**: Tests for string manipulation utilities (random string generation, email validation, word splitting, etc.)
   - **WebDriverFactoryTest**: Tests for WebDriver factory methods and browser type validation
   - **BasePageTest**: Tests for base page functionality with mocked WebDriver
   - **LoginPageTest**: Tests for login page specific functionality

## Key Features

- **Comprehensive Test Coverage**: Tests cover constructor validation, null input handling, edge cases, and main functionality
- **Mocking Strategy**: Uses Mockito to mock WebDriver dependencies for unit testing without requiring actual browser instances
- **JUnit 5**: Uses modern JUnit 5 features including DisplayName annotations for readable test descriptions
- **Maven Integration**: Configured to run tests from the custom `tests` folder instead of standard `src/test/java`

## Running Tests

```bash
# Compile the project
mvn clean compile

# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=TestUtilsTest
```

## Test Results

All 29 tests pass successfully:
- TestUtilsTest: 7 tests (string utilities, email validation, numeric checks)
- LoginPageTest: 10 tests (page navigation, inheritance, method validation)
- BasePageTest: 8 tests (WebDriver interaction, null handling, element checks)
- WebDriverFactoryTest: 4 tests (factory methods, enum validation, null safety)

## Generated Test Examples

### TestUtils Tests
- Random string generation validation
- Email format validation
- Text splitting into words
- Numeric string detection
- Word capitalization

### WebDriverFactory Tests
- Browser type enum validation
- Factory method existence verification
- Null driver handling in quitDriver method
- Method signature validation through reflection

### BasePage Tests
- Constructor with WebDriver validation
- Navigation functionality
- Page title retrieval
- Element display checking with exception handling

### LoginPage Tests
- Inheritance from BasePage verification
- Login page URL construction
- Null input handling
- Method existence validation through reflection

This project demonstrates a complete automated approach to generating comprehensive JUnit test suites for Selenium-based Java applications.
# AGENTS.md

This file defines how AI agents must operate inside this repository.
It does not redefine coding or design rules. It points to their sources.

## Source of truth (must follow)
AI agents MUST follow these instruction files:

1) `general.instructions.md`
  - Global rules that apply to all files and workflows

2) `co-pilot-instructions.md`
  - Architecture, folder structure, and Copilot-specific behavior

3) `springboot.instuctions.md`
  - Spring Boot framework coding standards and conventions

4) `shell.instructions.md`
  - Unix shell scripting standards and conventions

## Project Overview
This is a standard Maven-based Java project (`TestCoPilot`) with a basic structure:
- `src/main/java/`: Main application code
- `src/main/resources/`: Configuration and resource files
- `src/test/java/`: Unit tests

Key configuration in `pom.xml`:
- Java version: 26
- Source encoding: UTF-8
- No external dependencies currently

## Build Workflow
- Compile code: `mvn compile`
- Run tests: `mvn test`
- Clean build artifacts: `mvn clean`
- Create JAR: `mvn package`
- Full build: `mvn clean compile test package`

## Development Conventions
- Use standard Maven directory structure
- Place resources in `src/main/resources/`
- Write tests in `src/test/java/` mirroring main package structure
- .gitignore excludes `target/`, IDE files (.idea/), and common artifacts

## Integration Points
- No external services or APIs integrated yet
- Standard JVM runtime environment

## Key Files
- `pom.xml`: Maven configuration and dependencies
- `.gitignore`: Excludes build artifacts and IDE files

## Code Formatting

- Indentation: 4 spaces.
- Blank Lines: Use to separate logical blocks of code.
- Line Length: Maximum 120 characters.
- Use IntelliJ IDEA default code style for Java.

## Java Style

- Use UTF-8 encoding.
- Use descriptive names for classes, methods, and variables.
- Avoid `var` keyword, prefer explicit types.
- All method parameters should be `final`.
- All variables should be declared as `final` where possible.
- Preference for immutability:
- Avoid mutations of objects, specially when using for-each loops or Stream API using `forEach()`.
- Avoid magic numbers and strings; use constants instead.
- Check emptiness and nullness before operations on collections and strings.
- Avoid methods using `throws` clause; prefer unchecked exceptions.

- Comments could be applied for: cron expressions, Regex patterns, TODOs or given/when/then separation in tests.
- Use `@Override` annotation when overriding methods.
- Avoid Objects.*isNull() and Objects.*nonNull() for one or two variables; prefer direct null checks for better performance.
- Wrap multiple conditions in a boolean variable for better readability
- Prefer early returns.
- Avoid else statements when not necessary and try early returns.

## Lombok Annotations

- Use `@RequiredArgsConstructor` from Lombok for dependency injection via constructor.
- Use `@Slf4j` from Lombok for logging.
- Use `@Builder(setterPrefix = "with"))` for complex object creation.
- Avoid `@Data` annotation; prefer `@Getter` and `@Setter` for granular control.

## Annotations

- **`@Service`**: For business logic classes.
- **`@Repository`**: For data access classes that extend JPA repositories or interact with the database.
- **`@RestController`**: For web controllers.
- **`@Component`**: For generic Spring components.
- **`@Configuration`**: For Spring configuration classes.
- **`@Autowired`**: Prefer constructor injection for production code and field injection only for tests.
- **`@ConfigurationProperties`**: For binding related properties avoid multiple `@Value` annotations. From more than 2 properties, consider using this annotation.
- **`@Transactional`**: Only Service classes should be annotated with @Transactional at class level to avoid transaction management in each method.
- **`@Validated`**: To enable Bean Validation in method parameters or classes.
- **`@PreAuthorize`**: at the controller layer when using Spring Security to enforce method-level security.
- Circular dependencies should be avoided. Avoid `@Order` annotation for dependency resolution.

## Mappers(As a development team choose MapStruct or strictly static Mappers)

**Use MapStruct**

- MapFor mapping between DTOs and entities.
- Define mapper interfaces with `@Mapper` annotation.
- Use `@Mapping` annotation for custom field mappings.
- Use `componentModel = "spring"` to allow Spring to manage mapper instances.
- Mapper should have as suffix `Mapper` (e.g., `UserMapper`).
- Name mapper methods clearly (e.g., `toDto`, `toEntity`).
- Example Mapper Interface:

  ```java
  @Mapper(componentModel = "spring")
  public interface UserMapper {
      @Mapping(source = "email", target = "emailAddress")
      UserDTO toDto(User user);
      @Mapping(source = "emailAddress", target = "email")
      User toEntity(UserDTO userDto);
  }
  ```

- For testing mappers, use `Mappers.getMapper(UserMapper.class)` to get an instance of the mapper.

**Use Static Mappers**

- Define a private constructor to prevent instantiation with `UnsupportedOperationException("This class should never be instantiated")`.
- Use static methods for mapping between DTOs and entities.
- Name mapper methods clearly (e.g., `toDto`, `toEntity`).
- Example Static Mapper Class:

  ```java
  public class UserMapper {
      private UserMapper() {
          throw new UnsupportedOperationException("This class should never be instantiated");
      }
      public static UserDTO toDto(final User user) {
          if (user == null) {
              return null;
          }
          return UserDTO.builder()
              .withId(user.getId())
              .withEmailAddress(user.getEmail())
              .build();
      }
      public static User toEntity(final UserDTO userDto) {
          if (userDto == null) {
              return null;
          }
          return User.builder()
              .withId(userDto.getId())
              .withEmail(userDto.getEmailAddress())
              .build();
      }
  }
  ```

## Exception Handling

- Custom Exceptions: Create custom domain exception classes extending `RuntimeException`.
- Global Exception Handler: Use `@ControllerAdvice` and `@ExceptionHandler` to handle exceptions globally.
- HTTP Status Codes: Map exceptions to appropriate HTTP status codes in REST controllers.
- Error Response Structure: Define a consistent error response structure

## Testing

- Use JUnit 5 for unit and integration testing.
- Use Mockito for mocking dependencies in unit tests.
- Use `@WebMvcTest(ControllerClass.class)` for testing Spring MVC controllers.
- Use `@SpringBootTest` for integration tests that require the Spring context.
- Use `given/when/then` structure in test methods for clarity.
- Method naming could follow snake_case or camelCase convention for test methods (e.g., `get_user_by_id_ok`, `get_user_by_id_not_found_ko`).
- Avoid reflection in tests.
- Avoid business logic in tests; focus on behavior verification.

## Logging

- Use `@Slf4j` annotation from Lombok for logging to avoid boilerplate code with Logger instances.
- Log at appropriate levels: `DEBUG`, `INFO`, `WARN`, `ERROR`.
- Include contextual information in logs (e.g., request IDs, user IDs).
- Avoid logging sensitive information.
- Use structured logging for better log management.
- Format log messages with placeholders (e.g., `{}`) instead of string concatenation.
- Logging info code could follow this template: log.info("[MicroserviceName/ModuleName] - API-CALL/METHOD/ACTION: response: {}, userId: {}", body, userId);
- Logging error code could follow this template: log.error("[MicroserviceName/ModuleName] - API-CALL/METHOD/ACTION: errorMessage: {}, userId: {}", errorMessage, userId);
# BookStoreApplication — quick notes

This repository is a small Spring Boot application. The following notes are focused on AOP logging and how to enable/disable verbose (DEBUG) logging for development.

AOP logging (dev vs default)

- Default (recommended): `application.properties` sets conservative logging levels (INFO).
- Dev profile: `src/main/resources/application-dev.properties` enables DEBUG for `pjr.bookstore` and `pjr.bookstore.aop` so the `LoggingAspect` messages are visible.

Run with the dev profile (two options):

1) Using the Spring Boot Maven plugin:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

2) Using environment variable:

```bash
export SPRING_PROFILES_ACTIVE=dev
./mvnw spring-boot:run
```

You can also pass logging overrides on the command line for one-off runs, for example:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev -Dlogging.level.pjr.bookstore=DEBUG
```

Files added/changed for AOP logging

- `src/main/java/pjr/bookstore/aop/LoggingAspect.java` — Aspect with @Before, @After, @AfterReturning advices at DEBUG level
- `src/main/resources/application.properties` — default logging levels (INFO)
- `src/main/resources/application-dev.properties` — dev profile (DEBUG for pjr.bookstore)


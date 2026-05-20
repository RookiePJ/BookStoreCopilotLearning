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
 - `src/main/resources/application-dev.properties` — dev profile (DEBUG for pjr.bookstore)
 - `src/main/resources/application-prod.properties` — prod profile (stricter logging; AOP reduced to WARN)

Production profile

- Use the `prod` profile for production-like runs; AOP logging is reduced to avoid noisy debug output.

Run with the prod profile:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

Or via environment variable:

```bash
export SPRING_PROFILES_ACTIVE=prod
./mvnw spring-boot:run
```

Local pre-commit hook

To avoid committing `application-dev.properties` with DEBUG entries to `main`, a local pre-commit hook is provided in `.githooks/pre-commit` and a helper script at `scripts/verify-dev-file.sh`.

Install the hook locally (one-time) by running from the repository root:

```bash
# set repository hooks path to .githooks
git config core.hooksPath .githooks
```

Now the `pre-commit` hook will run automatically when you create commits locally and will prevent commits when DEBUG entries are present.

You can also run the helper manually:

```bash
./scripts/verify-prod-file.sh
# or to test another file:
./scripts/verify-prod-file.sh path/to/somefile.properties
```

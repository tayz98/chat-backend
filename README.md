# Chat Backend

Spring Boot backend for a chat application managing users, rooms, messages, notifications and friendships.
Implemented with Spring Data JPA (Jakarta Persistence), Hibernate, Gradle and targeting JDK 25.

## Quick facts
- Language: Java (JDK 25)
- Build: Gradle
- Frameworks: Spring Boot, Spring Data JPA, Hibernate
- Test DB (unit tests): H2 (in-memory)
- Runtime DB (recommended): PostgreSQL (Docker Compose provided / configure in application.properties)

## Project structure (high level)
- src/main/java/com/mychat/chat_backend
  - model — JPA entities (User, Room, RoomParticipant, Message, Notification, friendship.*)
  - repository — Spring Data repositories
  - service — business logic
  - mapper — DTO mappers (map entities + loaded related data → DTOs)
  - dto — request/response payload classes
  - exception — domain exceptions
- src/test — unit/integration tests (DataJpaTest for repository tests)

## Building & testing
From project root:

- Build
  ```bash
  ./gradlew build
  ```

- Run tests (show stacktraces for failures)
  ```bash
  ./gradlew test --no-daemon --stacktrace --info
  ```

- Run a single test
  ```bash
  ./gradlew test --tests "com.mychat.chat_backend.repository.NotificationRepositoryTest.findAllByRecipientId_ShouldReturnNotificationsForGivenUserId" --stacktrace
  ```

- Open test report
  ```bash
  xdg-open build/reports/tests/test/index.html
  ```

## Database
- Configure connection in `src/main/resources/application.properties`.
- For local development use Docker Compose (see [file](/compose.yaml)) or local Postgres.
- Unit-Tests use H2 (configured via `@AutoConfigureTestDatabase` in tests).

# Chat Backend

A Spring Boot backend for a chat application that manages users, rooms, and messages. It uses Spring Data JPA with
Jakarta Persistence and Gradle, and targets JDK 25.

## Key Features

- ... (TODO)

## Tech Stack

- Java (JDK 25)
- Spring Boot, Spring Data JPA (Jakarta Persistence)
- Gradle
- Docker Compose: PostgresSQL

## Domain Model (high level)

- User
    - Core fields (username, email, password, avatar, etc.).
    - Many-to-Many participation in Rooms.
- Room
    - Owner: Many-to-One to User.
    - Participants: Many-to-Many to User via a join table (e.g., `participants_room`).
    - Allowed emails: element collection (own table).
    - Messages: One-to-Many to Message; cascade/removal handled from Room.
- Message
    - Sender: Many-to-One to User.
    - Room: Many-to-One to Room (a message cannot exist without a room).
- Notification
    - Recipient: Many-to-One to User.
- Settings
    - Not implemented yet.

## Getting Started

### Configure the Database

- Set the connection details in `src/main/resources/application.properties`
- Run the Docker Compose file `docker-compose up -d` to start the database.

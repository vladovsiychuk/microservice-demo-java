## Social Media API Service Demo

This project demonstrates a microservice architecture for a social media platform using Spring Boot. IIt has two main domains: **Post** and **Comment**, each managed as a separate domain. The project employs a CQRS pattern for efficient data retrieval and updating, with integration of PostgreSQL, MongoDB, and Redis.

### Key Features

**Domains**:
- Post Domain: Handles post creation, updates, and deletion.
- Comment Domain: Manages comments linked to posts.
- Each domain uses its own table in PostgreSQL to store the entities.
- CQRS (Command Query Responsibility Segregation):
- When an entity (post or comment) is saved or updated, an application event is triggered.
- This event is consumed by a backend-for-frontend (BFF) module, which updates the post aggregate in MongoDB.
- MongoDB stores the Post aggregate as a JSON object, making it available for fast data retrieval by the frontend.

**MongoDB**:
- Stores the aggregate as JSON, allowing quick and efficient access to data for frontend clients.
- Updates the aggregate when new comments are added or posts are created.

**Redis Cache**:
- Caches REST requests for the post aggregate, optimizing performance and reducing load on MongoDB.

**Technology Stack**
- Spring Boot
- PostgreSQL
- MongoDB
- Redis
- CQRS Pattern

**How it Works**
- When a post or comment is created or updated, it is saved to the relevant domain’s table in PostgreSQL.
- The domain triggers an application event.
- The backend-for-frontend (BFF) module listens for these events and updates the Post aggregate in MongoDB.
- The aggregate is used to provide fast data to the frontend.
- Redis caches REST requests for the aggregate, further improving performance.

This project is a simple demonstration of using microservice architecture with CQRS, event-driven communication, caching, and multiple database technologies.

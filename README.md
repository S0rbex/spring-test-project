# 📚 Spring Boot REST API (Test Project)

## 🌟 Introduction
In today's fast-paced digital ecosystem, managing structured data and providing a seamless, secure user experience is critical for any web service. This project was inspired by the need to build a robust, scalable, and fully functional backend architecture from the ground up. It solves the problem of secure data management by providing a comprehensive RESTful API equipped with role-based access control, streamlined database operations, and containerized deployment.

## 🛠️ Technologies & Tools
This application leverages a modern Java stack to ensure performance, maintainability, and security:
* **Java** – Core programming language.
* **Spring Boot** – Accelerates application setup and configuration.
* **Spring Security & JWT** – Implements robust, stateless authentication and role-based endpoint protection.
* **Spring Data JPA & Hibernate** – Handles ORM, abstracting complex database interactions.
* **MapStruct** – Automates DTO mapping, keeping the codebase clean and reducing boilerplate.
* **Swagger / OpenAPI** – Provides an interactive, auto-generated UI for testing and exploring API endpoints.
* **Docker & Docker Compose** – Ensures consistent containerized environments for both the application and the database.
* **AWS EC2** – Target environment for cloud deployment.

## ⚙️ Controllers & Functionalities
The application is structured around several core controllers that manage specific business domains:

* **`AuthenticationController`**: Acts as the entry point for users. Handles registration and login, securely verifying credentials and returning JWT tokens for authenticated sessions.
* **`BookController` / `ProductController`**: Exposes CRUD operations for the main entities. Public endpoints allow searching and filtering, while creation, updates, and deletions are strictly restricted to `ADMIN` roles.
* **`ShoppingCartController`**: Manages the user's active session state. Authenticated users can add items to their cart, update quantities, and clear their cart.
* **`OrderController`**: Processes the checkout flow, converting a shopping cart into a finalized order and allowing users to view their historical transactions.
### 📍 API Endpoints

| HTTP Method | Endpoint (Link) | Short Description | Applicable Role   |
| :--- | :--- | :--- |:------------------|
| **Authentication & Registration** | | |                   |
| `POST` | `/api/auth/registration` | Register a new user account | `PUBLIC`          |
| `POST` | `/api/auth/login` | Authenticate user and return JWT token | `PUBLIC`          |
| **Books Management** | | |                   |
| `GET` | `/api/books` | Get a paginated list of all available books |  `USER` |
| `GET` | `/api/books/{id}` | Get detailed information about a specific book |   `USER`  |
| `POST` | `/api/books` | Create a new book entry in the catalog | `ADMIN`           |
| `PUT` | `/api/books/{id}` | Update an existing book's details | `ADMIN`           |
| `DELETE` | `/api/books/{id}` | Soft delete a book from the catalog | `ADMIN`           |
| **Categories Management** | | |                   |
| `GET` | `/api/categories` | Get a list of all book categories |  `USER` |
| `GET` | `/api/categories/{id}/books` | Get all books belonging to a specific category |  `USER` |
| `POST` | `/api/categories` | Create a new category | `ADMIN`           |
| `PUT` | `/api/categories/{id}` | Update an existing category | `ADMIN`           |
| `DELETE` | `/api/categories/{id}` | Soft delete a category | `ADMIN`           |
| **Shopping Cart** | | |                   |
| `GET` | `/api/cart` | Retrieve the authenticated user's shopping cart | `USER`            |
| `POST` | `/api/cart` | Add a book to the shopping cart | `USER`            |
| `PUT` | `/api/cart/cart-items/{cartItemId}` | Update the quantity of a specific item in the cart | `USER`            |
| `DELETE`| `/api/cart/cart-items/{cartItemId}` | Remove a book from the shopping cart | `USER`            |
| **Order Processing** | | |                   |
| `POST` | `/api/orders` | Checkout the current cart and place an order | `USER`            |
| `GET` | `/api/orders` | Get the order history for the authenticated user | `USER`            |
| `GET` | `/api/orders/{orderId}/items` | Get a list of items for a specific order | `USER`            |
| `PATCH` | `/api/orders/{id}` | Update the status of an order (e.g., PENDING to COMPLETED) | `ADMIN`           |

## 📊 Visual Architecture

![ER діаграма бази даних](images/ERD.png)

## 🚀 Setup and Installation
To run this project locally, ensure you have **Java**, **Maven**, and **Docker** installed.

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/S0rbex/spring-test-project.git](https://github.com/S0rbex/spring-test-project.git)
   cd spring-test-project
   ```

2. **Configure Environment Variables:**
   Create a `.env` file in the root directory (or update your `application.properties`) with your local credentials:
   ```properties
   DB_URL=jdbc:mysql://localhost:3306/your_database
   DB_USERNAME=root
   DB_PASSWORD=secret
   JWT_SECRET=your_super_secret_key_here
   ```

3. **Run with Docker Compose:**
   The easiest way to boot the application along with its database is via Docker:
   ```bash
   docker-compose up --build
   ```

4. **Access the Application:**
   * API Base URL: `http://localhost:8080/api`
   * Swagger Documentation: `http://localhost:8080/swagger-ui/index.html`

## 🚧 Challenges & Solutions
Building this API presented several valuable technical challenges. Implementing a stateless authentication mechanism required a deep dive into Spring Security filter chains to properly validate JWTs on every request. Additionally, mapping complex nested entities to DTOs initially led to verbose code, which was elegantly solved by integrating **MapStruct**.

The most significant hurdle involved infrastructure: writing the initial **Docker Compose** configurations and resolving database connectivity issues during deployment to an **AWS EC2** instance. By carefully analyzing container logs and adjusting network bindings, I successfully achieved a stable cloud deployment, which greatly strengthened my understanding of CI/CD pipelines and DevOps practices.

## 📬 Postman Collection
To make testing easier and more interactive, a comprehensive Postman collection is included.
1. Locate the `postman_collection.json` file in the repository.
2. Open Postman and click **Import**.
3. Once imported, you will see a structured list of pre-configured requests.
4. **Note:** Start by running the `/auth/login` request to receive your JWT. Copy the generated token and paste it into the `Authorization` tab (select *Bearer Token*) for all subsequent secure requests.

---
*Developed with dedication by Vitalii.*

[Коротке DEMO автентифікації](https://www.loom.com/share/0acd5a017f5f45acba77b6ed899ae6e0)
   
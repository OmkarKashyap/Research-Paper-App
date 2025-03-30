# Research-Paper-App

A **Spring Boot** application for managing research papers, using **MySQL** as the database.

---

## Getting Started

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/OmkarKashyap/Research-Paper-App.git
cd Research-Paper-App
```

### 2️⃣ Database Setup

Create the MySQL Database

Log in to MySQL and run the following SQL command:

```sql
CREATE DATABASE research_paper_app;
```

### 3️⃣ Configure Environment Variables

#### Option 1: Use a .env File (Recommended)

Create a .env file in the project root and add the following:

```Ini, TOML
DATABASE_URL=jdbc:mysql://localhost:3306/research_paper_app
DATABASE_USER=root
DATABASE_PASSWORD=YOUR_MYSQL_PASSWORD
```

#### Option 2: Manually Set Environment Variables

For Windows (PowerShell):

```powershell
$env:DATABASE_URL="jdbc:mysql://localhost:3306/research_paper_app"
$env:DATABASE_USER="root"
$env:DATABASE_PASSWORD="YOUR_MYSQL_PASSWORD"
```

For macOS/Linux (Terminal):

```bash
export DATABASE_URL="jdbc:mysql://localhost:3306/research_paper_app"
export DATABASE_USER="root"
export DATABASE_PASSWORD="YOUR_MYSQL_PASSWORD"
```

### Build & Run the Application

Step 1: Build the Project

```bash
mvn clean install
```

```bash
Step 2: Run the Application
```

```bash
mvn spring-boot:run
```

By default, the application runs on http://localhost:8082.

### Technologies Used

Spring Boot,
Spring Data JPA,
MySQL,
Hibernate,
Maven

#### API Endpoints

| Method | Endpoint       | Description              |
| :----- | :------------- | :----------------------- |
| GET    | `/papers`      | Get all research papers  |
| POST   | `/papers`      | Add a new research paper |
| GET    | `/papers/{id}` | Get a paper by ID        |
| PUT    | `/papers/{id}` | Update a paper           |
| DELETE | `/papers/{id}` | Delete a paper           |

### License

This project is licensed under the MIT License – feel free to modify and use it!

```

```

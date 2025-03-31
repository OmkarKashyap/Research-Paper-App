# Research-Paper-App

A **Spring Boot** application for managing research papers, using **MySQL** as the database.

---

## Getting Started

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/OmkarKashyap/Research-Paper-App.git
cd Research-Paper-App
```

### 2️⃣ Supabase Database Setup

Supabase provides a PostgreSQL database for your application automatically when you create a project. You don't need to run any manual SQL commands to create a database.

Here's how to get started with your Supabase database:

1.  **Create an Account on Supabase:** If you don't have one already, go to [https://supabase.com/](https://supabase.com/) and create a free account.

2.  **Create a New Project:**

    - Once logged in, you'll see a dashboard. Click on the "New project" button.
    - You'll be asked to provide a name for your project, choose a region for your database server (select one closest to your users for better latency), and set a database password (make sure to remember this password).
    - Click "Create new project". Supabase will then provision a PostgreSQL database for your project. This might take a few moments.

3.  **Your Database is Ready:** Once your project is created, your PostgreSQL database is automatically set up and ready to use.

4.  **Find Your Connection Details:** To connect your Spring Boot application, you'll need the connection details:
    - In your Supabase project dashboard, navigate to the "Database" section in the left-hand sidebar.
    - Click on "Connection info".
    - You'll find various connection strings and details, including the JDBC connection URL, username (usually `postgres`), and the password you set when creating the project. You'll need these details for configuring your environment variables in the next step.

### 3️⃣ Configure Environment Variables

To connect your Spring Boot application to your Supabase database, you need to configure the database connection details as environment variables. Here are two options:

#### Option 1: Manually Set Environment Variables (Recommended)

For Windows (PowerShell):

```powershell
$env:SUPABASE_DATABASE_URL=<jdbc url>
$env:SUPABASE_DATABASE_USER="<user>"
$env:SUPABASE_DATABASE_PASSWORD="<password>"
```

For macOS/Linux (Terminal):

```bash
export SUPABASE_DATABASE_URL="<jdbc url>"
export SUPABASE_DATABASE_USER="<user>"
export SUPABASE_DATABASE_PASSWORD="<password>"
```

#### Option 2: Use a .env File

Create a .env file in the project root and add the following:

```Ini, TOML
DATABASE_URL=jdbc:mysql://localhost:3306/research_paper_app
DATABASE_USER=root
DATABASE_PASSWORD=YOUR_MYSQL_PASSWORD
```

### 4️⃣ Build & Run the Application

Step 1: Build the Project

```bash
mvn clean install
```

Step 2: Run the Application

```bash
mvn spring-boot:run
```

By default, the application runs on http://localhost:8082.

### 5️⃣ Technologies Used

Spring Boot,
Spring Data JPA,
MySQL,
PostgreSQL,
Hibernate,
Maven

### 6️⃣ API Endpoints

| Method | Endpoint       | Description              |
| :----- | :------------- | :----------------------- |
| GET    | `/papers`      | Get all research papers  |
| POST   | `/papers`      | Add a new research paper |
| GET    | `/papers/{id}` | Get a paper by ID        |
| PUT    | `/papers/{id}` | Update a paper           |
| DELETE | `/papers/{id}` | Delete a paper           |

### 7️⃣ License

This project is licensed under the MIT License – feel free to modify and use it!

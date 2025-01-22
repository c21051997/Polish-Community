Polish Community Website 🌍🇵🇱

This project is a web application built using Spring Framework and Gradle, designed to serve the Polish community by fostering communication, collaboration, and resource sharing.

Features ✨

User Authentication: Secure registration and login system.

Community Forums: Engage in discussions with other members.

Event Calendar: Stay updated with local and community events.

Multilingual Support: Polish and English support for wider accessibility.

Getting Started 🚀

Prerequisites

Before running the project, ensure the following tools are installed:

Java JDK 11 or higher

Gradle (latest version preferred)

MariaDB

Installation Steps

Clone the repository:

git clone https://github.com/yourusername/polish-community-website.git
cd polish-community-website

Set up the database:

Install and configure MariaDB.

Create a database for the application.

Update the application.properties file in the src/main/resources directory with your database credentials:

spring.datasource.url=jdbc:mariadb://localhost:3306/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MariaDBDialect

Build the project:

gradle build

Run the application:

gradle bootRun

Access the application in your browser at http://localhost:8080.

Built With 🛠️

Spring Framework - Backend framework

Gradle - Build tool

MariaDB - Relational database

Thymeleaf - Template engine

HTML/CSS/JavaScript - Frontend technologies

Contributing 🤝

Contributions are welcome! Please follow these steps to contribute:

Fork the repository.

Create a new branch (feature/your-feature-name).

Commit your changes.

Push to the branch.

Open a pull request.

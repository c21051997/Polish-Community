# Polish Community Website 🌍🇵🇱

This project is a web application built using **Spring Framework** and **Gradle**, designed to serve the Polish community by fostering communication, collaboration, and resource sharing.

---

## Features ✨

- **User Authentication**: Secure registration and login system.
- **Community Forums**: Engage in discussions with other members.
- **Event Calendar**: Stay updated with local and community events.
- **Multilingual Support**: Polish and English support for wider accessibility.

---

## Getting Started 🚀

### Prerequisites

Before running the project, ensure the following tools are installed:

- **Java JDK 11** or higher
- **Gradle** (latest version preferred)
- **MariaDB**

### Installation Steps

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/polish-community-website.git
   cd polish-community-website
   ```

2. **Set up the database**:
   - Install and configure MariaDB.
   - Create a database for the application.
   - Update the `application.properties` file in the `src/main/resources` directory with your database credentials:
     ```properties
     spring.datasource.url=jdbc:mariadb://localhost:3306/your_database_name
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     spring.jpa.hibernate.ddl-auto=update
     spring.jpa.database-platform=org.hibernate.dialect.MariaDBDialect
     ```

3. **Build the project**:
   ```bash
   gradle build
   ```

4. **Run the application**:
   ```bash
   gradle bootRun
   ```

5. **Access the application**:
   Open your browser and navigate to `http://localhost:8080`.

---

## Built With 🛠️

- **Spring Framework** - Backend framework
- **Gradle** - Build tool
- **MariaDB** - Relational database
- **Thymeleaf** - Template engine
- **HTML/CSS/JavaScript** - Frontend technologies

---

## Contributing 🤝

Contributions are welcome! To contribute:

1. **Fork the repository**:
   ```bash
   git fork https://github.com/yourusername/polish-community-website.git
   ```

2. **Create a new branch**:
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Commit your changes**:
   ```bash
   git commit -m "Description of your changes"
   ```

4. **Push to the branch**:
   ```bash
   git push origin feature/your-feature-name
   ```

5. **Open a pull request** on GitHub.




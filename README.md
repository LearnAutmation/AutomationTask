# QA Automation Framework

## 📌 Overview

This is a hybrid test automation framework that supports **UI automation with Selenium + TestNG** and **API testing with REST-assured**, designed for the Raweng QA Automation assignment.

Key Highlights:
- Supports **multiple browsers (Chrome, Firefox, Edge)**
- Works on **local and Selenium Grid (via Docker)**
- Follows **Page Object Model (POM)**
- Uses **config.properties** for all runtime parameters
- Supports **GitHub Actions CI pipeline** with Maven

---

## 🧱 Project Structure

```
Automation_Assignment/
├── .github/workflows/               # GitHub Actions CI configuration
├── .idea/, .mvn/, target/          # IDE/Build related folders
├── docker-compose.yml              # Docker Grid setup
├── pom.xml                         # Maven dependencies and plugins
├── src/
│   ├── main/java/
│   │   ├── pages/                  # Page Object Model (POM) classes for UI screens
│   │   └── utils/                  # Utility classes (driver setup, config reader)
│   └── test/java/
│   │   ├── ApiTest/                # REST-assured API test cases
│   │   └── UiTest/                 # Selenium UI test cases
│   └── test/resources/
│       ├── config.properties       # All configurable inputs (URL, credentials, etc.)

```

---

## 🔧 Technologies Used

- Java 17+
- Selenium WebDriver 4.20.0
- TestNG
- REST-assured 5.4.0
- Maven
- GitHub Actions
- Docker (for Selenium Grid)

---

## 🚦 UI Automation Flow

1. Test starts by reading runtime values from `config.properties`
2. WebDriver is initialized for the specified browser (local or remote via Grid)
3. Page Object Model classes encapsulate actions on respective pages
4. Tests validate application functionality via assertions
5. WebDriver is closed after each test to maintain clean state

---

## 🌐 API Testing Flow

- Base URI, credentials, and endpoints are configured via `config.properties`
- REST-assured is used to send requests and assert responses

---

## ⚙️ Configuration (via `config.properties`)

- UI credentials and data (URL, username, password, name, zip, etc.)
- Browser type: chrome, firefox, edge
- Selenium Grid URL
- API base URI and login credentials

---

## 📆 Execution Options

### ▶️ Run Locally
```bash
mvn clean test -Dbrowser=chrome
```

### 🧪 Run with Selenium Grid
```bash
# Start Grid via Docker
# (ensure Docker is installed and running)
docker-compose up -d

# Then run tests
mvn clean test -Dbrowser=firefox -Dgrid.url=http://localhost:4444/wd/hub
```

### 🤖 GitHub Actions
- On every push/pull to `main`, tests run automatically
- Results shown in Actions tab with Surefire report uploaded if any test fails

---

## 🐳 Docker Grid Summary

- Selenium Hub and Nodes (Chrome, Firefox) are defined in `docker-compose.yml`
- VNC is enabled for debugging via `http://localhost:5900` with password access

---

## 🌐 GitHub Actions Workflow

- Defined under `.github/workflows/test.yml`
- Steps:
  - Checkout code
  - Set up JDK and Chrome
  - Cache dependencies
  - Dynamically write `config.properties`
  - Run tests via Maven
  - Upload Surefire reports on failure

---

## 🏁 Summary

This hybrid automation framework offers:
- Modular structure with Page Object Model and test segregation
- Full UI and API test coverage
- Docker-based Selenium Grid integration for cross-browser compatibility
- CI pipeline with GitHub Actions for continuous testing

Extensible for:
- Parallel execution
- TestNG group filtering
- Integration with reporting tools (e.g., Allure, ReportNG)
- Data-driven testing

---

✅ Ready for local and CI execution
✅ Grid-compatible and configurable
✅ Clean separation of concerns

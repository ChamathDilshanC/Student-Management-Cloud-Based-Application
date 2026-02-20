# 🎓 Student Management Cloud-Based Application

A full-stack web application for managing student records with cloud-native features, built using Spring Boot and Vue.js. The application supports both local file storage and Google Cloud Storage (GCS) for student profile images, with PostgreSQL as the database.

## ✨ Features

- **Student CRUD Operations**: Create, read, update, and delete student records
- **Profile Image Management**: Upload and manage student profile pictures
- **Cloud Storage Integration**: Support for both local storage and Google Cloud Storage (GCS)
- **RESTful API**: Well-structured REST API with proper HTTP methods and status codes
- **Responsive UI**: Modern Vue.js frontend with clean and intuitive interface
- **Form Validation**: Client and server-side validation for data integrity
- **Error Handling**: Comprehensive exception handling with user-friendly messages
- **PostgreSQL Database**: Reliable relational database for persistent storage
- **Multi-Environment Support**: Separate configurations for local and cloud deployments

## 🛠️ Technology Stack

### Backend

- **Java 25**
- **Spring Boot 4.0.2**
  - Spring Web MVC
  - Spring Data JPA
  - Spring Validation
- **PostgreSQL** - Database
- **Google Cloud Storage SDK** - Cloud file storage
- **Lombok** - Boilerplate code reduction
- **Maven** - Dependency management

### Frontend

- **Vue.js 3.5.25**
- **Vue Router 5.0.3**
- **Axios 1.13.5** - HTTP client
- **Vite 7.3.1** - Build tool and dev server

## 📋 Prerequisites

Before running this application, ensure you have:

- **Java Development Kit (JDK) 25** or higher
- **Node.js 18+** and npm
- **PostgreSQL 14+** installed and running
- **Maven 3.6+** (or use the included Maven wrapper)
- **Google Cloud Platform account** (optional, for cloud storage)

## 🚀 Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/ChamathDilshanC/Student-Management-Cloud-Based-Application.git
cd Student-Management-Cloud-Based-Application
```

### 2. Database Setup

Create a PostgreSQL database:

```sql
CREATE DATABASE student_management_db;
```

### 3. Backend Configuration

#### For Local Development

1. Navigate to `backend/src/main/resources/`
2. Copy `application-local.yml.example` to `application-local.yml`
3. Update the database credentials:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/student_management_db
    username: your_db_username
    password: your_db_password
```

4. Set the active profile to `local` in `application.yml`:

```yaml
spring:
  profiles:
    active: local
```

#### For Cloud Deployment (GCS)

1. Copy `application-cloud.yml.example` to `application-cloud.yml`
2. Copy `gcs-credentials.json.example` to your actual GCS credentials file
3. Update `application-cloud.yml` with your GCP details:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://YOUR_GCP_POSTGRES_IP:5432/YOUR_DATABASE_NAME
    username: YOUR_DB_USERNAME
    password: YOUR_DB_PASSWORD

app:
  storage:
    mode: gcs
    gcs:
      bucket-name: YOUR_GCS_BUCKET_NAME
      credentials-path: classpath:YOUR_GCS_CREDENTIALS_FILE.json
```

4. Set the active profile to `cloud` in `application.yml`

### 4. Install Dependencies

#### Backend

```bash
cd backend
./mvnw clean install
# On Windows: mvnw.cmd clean install
```

#### Frontend

```bash
cd frontend
npm install
```

## ▶️ Running the Application

### Start Backend Server

```bash
cd backend
./mvnw spring-boot:run
# On Windows: mvnw.cmd spring-boot:run
```

The backend server will start on `http://localhost:8080`

### Start Frontend Development Server

```bash
cd frontend
npm run dev
```

The frontend will be available at `http://localhost:5173` (or another port shown in terminal)

## 📡 API Endpoints

| Method | Endpoint                | Description        |
| ------ | ----------------------- | ------------------ |
| GET    | `/api/v1/students`      | Get all students   |
| GET    | `/api/v1/students/{id}` | Get student by ID  |
| POST   | `/api/v1/students`      | Create new student |
| PUT    | `/api/v1/students/{id}` | Update student     |
| DELETE | `/api/v1/students/{id}` | Delete student     |

### Request/Response Examples

#### Create Student (POST `/api/v1/students`)

**Request** (multipart/form-data):

```
fName: John
lName: Doe
contact: +1234567890
address: 123 Main St, City, Country
profileImage: [image file]
```

**Response**:

```json
{
  "id": 1,
  "fName": "John",
  "lName": "Doe",
  "contact": "+1234567890",
  "address": "123 Main St, City, Country",
  "picture": "https://storage.googleapis.com/bucket/students/uuid.jpg"
}
```

## 📁 Project Structure

```
Student-Management-Cloud-Based-Application/
├── backend/
│   ├── src/main/java/com/studentmanagementapp/
│   │   ├── config/          # Configuration classes
│   │   ├── controller/      # REST controllers
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── entity/          # JPA entities
│   │   ├── exception/       # Exception handlers
│   │   ├── repository/      # JPA repositories
│   │   └── service/         # Business logic
│   ├── src/main/resources/
│   │   ├── application.yml
│   │   ├── application-local.yml.example
│   │   └── application-cloud.yml.example
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── api/             # API service layer
│   │   ├── components/      # Vue components
│   │   ├── views/           # Vue views/pages
│   │   └── assets/          # Static assets
│   ├── package.json
│   └── vite.config.js
├── postman/
│   └── Student-Management-API.postman_collection.json
├── CONTRIBUTING.md
├── LICENSE
└── README.md
```

## 🔧 Environment Configuration

### Storage Modes

The application supports two storage modes for profile images:

1. **Local Storage** (`storage.mode: local`)
   - Files stored in `uploads/students/` directory
   - Accessible via Spring's static content serving
   - Ideal for development and testing

2. **Google Cloud Storage** (`storage.mode: gcs`)
   - Files stored in GCS bucket
   - Public URLs for images
   - Production-ready with scalability

### File Upload Limits

Maximum file sizes (configurable in `application.yml`):

- Single file: 10MB
- Total request: 15MB

## 🧪 Testing

### Using Postman

Import the Postman collection from `postman/Student-Management-API.postman_collection.json` to test all API endpoints.

### Running Tests

```bash
cd backend
./mvnw test
```

## 🤝 Contributing

We welcome contributions! Please see [CONTRIBUTING.md](CONTRIBUTING.md) for details on:

- Reporting bugs
- Suggesting enhancements
- Submitting pull requests
- Code style guidelines

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👤 Author

**ChamathDilshanC**

- GitHub: [@ChamathDilshanC](https://github.com/ChamathDilshanC)

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- Vue.js community for the reactive framework
- Google Cloud Platform for storage solutions

## 📞 Support

For support, please open an issue in the GitHub repository or contact the maintainer.

---

**Note**: Remember to never commit sensitive credentials or API keys to version control. Always use environment variables or configuration files that are gitignored.

# Journal Application

A secure, feature-rich journal application built with Spring Boot and modern web technologies. This application allows users to create, manage, and track their personal journal entries with sentiment analysis and weather integration.

## 🚀 Features

- **User Authentication & Authorization** - Secure JWT-based authentication
- **Journal Entry Management** - Create, read, update, and delete journal entries
- **Sentiment Analysis** - Automatic sentiment analysis using Kafka integration
- **Weather Integration** - Weather data integration for journal entries
- **Email Notifications** - Automated email reminders and notifications
- **Caching with Redis** - Fast data retrieval using Redis cache
- **Responsive Frontend** - Modern, responsive UI with Thymeleaf templates

## 🛠️ Tech Stack

### Backend
- **Java 8**
- **Spring Boot 2.7.16**
- **Spring Security** - JWT authentication
- **Spring Data MongoDB** - NoSQL database
- **Spring Data Redis** - Caching layer
- **Spring Kafka** - Message streaming
- **Spring Mail** - Email service
- **Thymeleaf** - Template engine

### Frontend
- HTML5, CSS3, JavaScript
- Responsive design

### External Services
- **MongoDB Atlas** - Database
- **Redis Cloud** - Caching
- **Confluent Kafka** - Stream processing
- **Weather API** - Weather data

## 📋 Prerequisites

- Java 8 or higher
- Maven 3.6+
- MongoDB Atlas account (or local MongoDB)
- Redis instance (or Redis Cloud)
- Kafka cluster (or Confluent Cloud)
- Gmail account (for email service)
- Weather API key

## ⚙️ Configuration

### Environment Variables

Create a .env file or set the following environment variables:

\\\ash
# MongoDB
MONGODB_URI=your_mongodb_connection_string
MONGODB_DATABASE=journaldb

# Email Configuration
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_app_password

# Redis Configuration
REDIS_HOST=your_redis_host
REDIS_PORT=your_redis_port
REDIS_PASSWORD=your_redis_password

# Kafka Configuration
KAFKA_BOOTSTRAP_SERVERS=your_kafka_servers
KAFKA_USERNAME=your_kafka_username
KAFKA_PASSWORD=your_kafka_password

# Weather API
WEATHER_API_KEY=your_weather_api_key
\\\

## 🏃‍♂️ Running Locally

### 1. Clone the repository
\\\ash
git clone https://github.com/PranshuUtpal/Journal-Application.git
cd Journal-Application
\\\

### 2. Set up environment variables
Create an \pplication-dev.properties\ file with your local configurations (DO NOT commit this file)

### 3. Build the project
\\\ash
./mvnw clean install
\\\

### 4. Run the application
\\\ash
./mvnw spring-boot:run
\\\

The application will be available at \http://localhost:8080\

## 🚀 Deployment on Render

### Prerequisites
1. Create accounts on:
   - [Render](https://render.com)
   - [MongoDB Atlas](https://www.mongodb.com/cloud/atlas)
   - [Redis Cloud](https://redis.com/cloud/) or [Upstash](https://upstash.com/)
   - [Confluent Cloud](https://confluent.cloud/) (if not already using)

### Deployment Steps

1. **Push your code to GitHub**
   \\\ash
   git add .
   git commit -m "Prepare for deployment"
   git push origin main
   \\\

2. **Connect to Render**
   - Go to [Render Dashboard](https://dashboard.render.com/)
   - Click "New +" → "Web Service"
   - Connect your GitHub repository
   - Select the Journal-Application repository

3. **Configure the service**
   Render will auto-detect the \ender.yaml\ file. If not:
   - **Name**: journal-application
   - **Environment**: Java
   - **Build Command**: \./mvnw clean package -DskipTests\
   - **Start Command**: \java -Dserver.port=\ -jar target/journalApp-0.0.1-SNAPSHOT.jar\

4. **Add Environment Variables**
   In Render dashboard, add all the environment variables listed above

5. **Deploy**
   Click "Create Web Service" and Render will automatically build and deploy your application

## 📁 Project Structure

\\\
Journal-Application/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── net/pranshhvoyages/journalApp/
│   │   │       ├── controller/       # REST controllers
│   │   │       ├── service/          # Business logic
│   │   │       ├── repository/       # Data access layer
│   │   │       ├── entity/           # Domain models
│   │   │       ├── config/           # Configuration classes
│   │   │       ├── filterJwt/        # JWT filter
│   │   │       ├── scheduler/        # Scheduled tasks
│   │   │       └── utilis/           # Utility classes
│   │   └── resources/
│   │       ├── static/               # CSS, JS, Images
│   │       ├── templates/            # Thymeleaf templates
│   │       └── application*.properties
│   └── test/                         # Unit tests
├── pom.xml                           # Maven dependencies
├── render.yaml                       # Render deployment config
└── README.md                         # This file
\\\

## 🔒 Security

- JWT-based authentication
- Password encryption using BCrypt
- HTTPS enforced in production
- Environment variables for sensitive data
- CORS configuration for API security

## 📝 API Endpoints

### Public Endpoints
- \GET /\ - Landing page
- \POST /public/create-user\ - User registration
- \POST /public/login\ - User login

### Protected Endpoints (Requires JWT)
- \GET /journal/home\ - User dashboard
- \POST /journal/create\ - Create journal entry
- \GET /journal/my-journals\ - Get user's journals
- \PUT /journal/update/{id}\ - Update journal entry
- \DELETE /journal/delete/{id}\ - Delete journal entry
- \GET /user/profile\ - View profile
- \PUT /user/update\ - Update profile

### Admin Endpoints
- \GET /admin/all-users\ - Get all users
- \POST /admin/create-admin\ - Create admin user

## 🧪 Testing

Run tests using:
\\\ash
./mvnw test
\\\

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (\git checkout -b feature/AmazingFeature\)
3. Commit your changes (\git commit -m 'Add some AmazingFeature'\)
4. Push to the branch (\git push origin feature/AmazingFeature\)
5. Open a Pull Request

## 📧 Contact

**Pranshu Utpal**
- GitHub: [@PranshuUtpal](https://github.com/PranshuUtpal)
- Email: pranshuraj2002@gmail.com

## 📄 License

This project is open source and available for educational purposes.

## 🙏 Acknowledgments

- Spring Boot Team
- MongoDB
- Redis
- Apache Kafka
- All open-source contributors

---

**⚠️ Important Security Note:**
Never commit sensitive credentials to Git. Always use environment variables for:
- Database passwords
- API keys
- Email passwords
- Service credentials

Made with ❤️ by Pranshu Utpal

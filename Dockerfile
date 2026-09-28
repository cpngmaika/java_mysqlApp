# ==========================================
# Stage 1: Build ứng dụng với Maven (JDK 21)
# ==========================================
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml và tải dependencies trước để tận dụng Docker cache
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy mã nguồn và đóng gói WAR
COPY src ./src
RUN mvn clean package -DskipTests

# ==========================================
# Stage 2: Chạy ứng dụng với Tomcat 10.1 (Jakarta EE 10 / Servlet 6.0)
# ==========================================
FROM tomcat:10.1-jdk21-temurin

# Xóa ứng dụng mặc định của Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy file WAR đã build ở Stage 1 vào ROOT.war để chạy trực tiếp từ trang chủ /
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Render lắng nghe cổng động thông qua biến $PORT
EXPOSE 8080

# Cấu hình lại server.xml của Tomcat để bind đúng biến $PORT của Render (mặc định 8080 nếu không truyền)
CMD ["sh", "-c", "sed -i 's/port=\"8080\"/port=\"'\"${PORT:-8080}\"'\"/g' /usr/local/tomcat/conf/server.xml && catalina.sh run"]

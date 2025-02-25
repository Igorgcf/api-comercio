# Etapa 1: Usar uma imagem Maven para compilar o projeto
FROM maven:3.8.5-openjdk-17-slim AS build

# Definir o diretório de trabalho
WORKDIR /app

# Copiar arquivos do projeto para a imagem
COPY . .

# Compilar o projeto e gerar o JAR
RUN mvn clean package -DskipTests

# Etapa 2: Usar uma imagem base JDK Alpine para executar o JAR
FROM openjdk:17-alpine

# Criar diretório de trabalho para o JAR
WORKDIR /app

# Copiar o JAR da etapa de build para o diretório atual
COPY --from=build /app/target/*.jar app.jar

# Expor a porta 8080
EXPOSE 8080

# Definir o comando de entrada para executar o JAR
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

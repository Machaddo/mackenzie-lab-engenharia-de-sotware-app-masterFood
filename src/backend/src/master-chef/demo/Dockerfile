# Usa uma imagem Maven para compilar o código-fonte e gerar o JAR.
FROM maven:3.9.5-eclipse-temurin-17-alpine AS builder

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo de definição do projeto (POM) primeiro para aproveitar o cache
COPY pom.xml .

# Copia todo o código-fonte do projeto
COPY src /app/src

# Compila o projeto e gera o JAR na pasta 'target'. 
# Utilizamos -DskipTests para pular os testes durante o build do Docker (melhora a velocidade)
RUN mvn package -DskipTests

# ----------------------------------------------------------------------
# STAGE 2: EXECUÇÃO (RUNTIME)
# Usa uma imagem Java leve para rodar apenas o JAR final.
# ----------------------------------------------------------------------
FROM eclipse-temurin:17-jdk-alpine

# Define o diretório de trabalho
WORKDIR /app

# COPIA O ARQUIVO JAR DO STAGE DE BUILD (builder)
# O comando --from=builder diz ao Docker para buscar o arquivo no primeiro stage.
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

# Inicia a aplicação
ENTRYPOINT [ "java", "-jar", "app.jar" ]
plugins {
    application
    id("org.openjfx.javafxplugin") version "0.1.0" // Plugin de JavaFX para Gradle
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.postgresql:postgresql:42.6.1") // Driver JDBC para PostgreSQL
}

application {
    mainClass = "Main"
}

javafx {
    version = "21" // Versión de JavaFX
    modules = listOf("javafx.controls", "javafx.fxml") // Módulos que usaremos
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8" // Asegurar que la codificación sea UTF-8
}

tasks.test {
    useJUnitPlatform()
}

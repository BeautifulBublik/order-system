plugins {
    id("java-library")
}

version = "1.0.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
dependencies {
    // BOM Spring Boot
    implementation(platform("org.springframework.boot:spring-boot-dependencies:4.1.0"))
    annotationProcessor(platform("org.springframework.boot:spring-boot-dependencies:4.1.0"))
    testImplementation(platform("org.springframework.boot:spring-boot-dependencies:4.1.0"))

    // Валидация
    api("jakarta.validation:jakarta.validation-api")

    // JUnit
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.36") // 1.18.46 может быть слишком свежей
    annotationProcessor("org.projectlombok:lombok:1.18.36")
    testCompileOnly("org.projectlombok:lombok:1.18.36")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.36")
}
tasks.test {
    useJUnitPlatform()
}
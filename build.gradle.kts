import org.gradle.api.tasks.testing.Test
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

plugins {
    id("org.springframework.boot") version "4.0.0" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
}

apply(plugin = "java")

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "io.spring.dependency-management")

    repositories {
        mavenCentral()
    }

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    configure<DependencyManagementExtension> {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:4.0.0")
        }
    }

    tasks.withType<JavaCompile>().configureEach {
        options.compilerArgs.add("--enable-preview")
        options.compilerArgs.add("-parameters")
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
        jvmArgs("--enable-preview")
    }

    dependencies.add("compileOnly", "org.projectlombok:lombok")
    dependencies.add("annotationProcessor", "org.projectlombok:lombok")
    dependencies.add("testImplementation", "org.springframework.boot:spring-boot-starter-test")
    dependencies.add("testImplementation", "org.mockito:mockito-junit-jupiter")
    dependencies.add("testRuntimeOnly", "org.junit.platform:junit-platform-launcher:6.0.1")

    when (name) {
        "service" -> {
            dependencies.add("api", "org.springframework.boot:spring-boot-starter-data-jpa")
            dependencies.add("implementation", "org.springframework.boot:spring-boot-starter-validation")
            dependencies.add("implementation", "org.liquibase:liquibase-core")
            dependencies.add("implementation", "org.postgresql:postgresql")
            dependencies.add("runtimeOnly", "com.h2database:h2")
            dependencies.add("implementation", "org.mapstruct:mapstruct:1.6.3")
            dependencies.add("annotationProcessor", "org.mapstruct:mapstruct-processor:1.6.3")
        }
        "api" -> {
            dependencies.add("api", project(":service"))
            dependencies.add("implementation", "org.springframework.boot:spring-boot-starter-web")
            dependencies.add("implementation", "org.springframework.boot:spring-boot-starter-validation")
            dependencies.add("implementation", "org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.0")
            dependencies.add("implementation", "org.mapstruct:mapstruct:1.6.3")
            dependencies.add("annotationProcessor", "org.mapstruct:mapstruct-processor:1.6.3")
        }
        "app" -> {
            apply(plugin = "org.springframework.boot")
            dependencies.add("implementation", project(":api"))
            dependencies.add("implementation", "org.springframework.boot:spring-boot-starter-web")
            dependencies.add("implementation", "org.springframework.boot:spring-boot-starter-validation")
            dependencies.add("implementation", "org.springframework.boot:spring-boot-starter-data-jpa")
            dependencies.add("implementation", "org.springframework.boot:spring-boot-starter-liquibase")
            dependencies.add("runtimeOnly", "com.h2database:h2")
            dependencies.add("testImplementation", "org.springframework.boot:spring-boot-starter-test")
        }
    }
}

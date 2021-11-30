plugins {
    kotlin("jvm") version "1.6.0"
    id("com.ncorti.ktfmt.gradle") version "0.7.0"
}

group = "com.ncorti"
version = "0.0.1"

ktfmt {
    dropboxStyle()
}

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.majorVersion
    }
}

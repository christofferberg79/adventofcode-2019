import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
}

repositories {
    jcenter()
}

dependencies {
    testImplementation(kotlin("test-junit"))
}

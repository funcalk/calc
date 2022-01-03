import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    implementation("gradle.plugin.com.zoltu.gradle.plugin:git-versioning:3.0.3")
}

tasks {
    java {
        sourceCompatibility = VERSION_1_8
        targetCompatibility = VERSION_1_8
    }
    kotlin {
        listOf(compileKotlin, compileTestKotlin).forEach { task ->
            task.configure {
                kotlinOptions {
                    jvmTarget = VERSION_1_8.toString()
                }
            }
        }
    }
}

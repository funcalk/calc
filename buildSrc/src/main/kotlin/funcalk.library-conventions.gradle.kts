import org.gradle.api.JavaVersion.VERSION_1_8

group = "io.github.funcalk"

plugins {
    java
    `maven-publish`
    kotlin("jvm")
    id("com.zoltu.git-versioning")
    id("com.github.johnrengelman.shadow")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("org.assertj:assertj-core:3.21.0")
    testImplementation(kotlin("test"))
}

tasks {
    java {
        withSourcesJar()
        withJavadocJar()
        sourceCompatibility = VERSION_1_8
        targetCompatibility = VERSION_1_8
    }
    kotlin {
        listOf(compileKotlin, compileTestKotlin).forEach { task ->
            task.configure {
                kotlinOptions {
                    javaParameters = true
                    jvmTarget = VERSION_1_8.toString()
                }
            }
        }
    }
    test {
        useJUnitPlatform()
    }
}

publishing {
    publications {
        register<MavenPublication>("maven") {
            from(components["java"])
            pom {
                url.set("https://github.com/funcalk/funcalk")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("Alekseev-Mikhail")
                        name.set("Mikhail Alekseev")
                        organization.set("funcalk")
                        organizationUrl.set("https://github.com/funcalk")
                    }
                    developer {
                        id.set("oleg-alexeyev")
                        name.set("Oleg Alexeyev")
                        organization.set("funcalk")
                        organizationUrl.set("https://github.com/funcalk")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/funcalk/funcalk.git")
                    developerConnection.set("scm:git:https://github.com/funcalk/funcalk.git")
                    url.set("https://github.com/funcalk/funcalk-core")
                }
            }
        }
    }
    repositories {
        maven {
            name = "githubPackages"
            url = uri("https://maven.pkg.github.com/funcalk/funcalk")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
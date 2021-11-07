import org.gradle.api.JavaVersion.VERSION_1_8

group = "io.github.funcalk"

plugins {
  java
  `maven-publish`
  kotlin("jvm") version "1.5.31"
  id("com.zoltu.git-versioning") version "3.0.3"
  id("com.github.johnrengelman.shadow") version "7.1.0"
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))

  testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
  testImplementation("org.assertj:assertj-core:3.21.0")
  testImplementation(kotlin("test"))
}

tasks {
  java {
    withSourcesJar()
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
  shadowJar {
    manifest {
      attributes(Pair("Main-Class", "org.funcalk.repl.REPL"))
    }
    minimize()
  }
  build {
    dependsOn(shadowJar)
  }
}

publishing {
  publications {
    register<MavenPublication>("maven") {
      from(components["java"])
    }
  }
  repositories {
    maven {
      name = "githubPackages"
      url = uri("https://maven.pkg.github.com/funcalk/funcalk-core")
      credentials {
        username = System.getenv("GITHUB_ACTOR")
        password = System.getenv("GITHUB_TOKEN")
      }
    }
  }
}
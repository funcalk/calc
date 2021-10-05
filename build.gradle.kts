import org.gradle.api.JavaVersion.VERSION_1_8
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

plugins {
  application
  kotlin("jvm") version "1.5.31"
  id("com.zoltu.git-versioning") version "3.0.3"
}

group = "org.funcalk"

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

tasks.named<Test>("test") {
  useJUnitPlatform()
}

java {
  withSourcesJar()
  sourceCompatibility = VERSION_1_8
  targetCompatibility = VERSION_1_8
}

val compileKotlin: KotlinJvmCompile by tasks
compileKotlin.kotlinOptions {
  javaParameters = true
  jvmTarget = VERSION_1_8.toString()
}

application {
  mainClass.set("org.calc.REPLKt")
}
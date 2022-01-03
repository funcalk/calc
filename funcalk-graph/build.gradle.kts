plugins {
    id("funcalk.library-conventions")
}

dependencies {
    implementation(project(":funcalk-core"))
}

tasks.withType<GenerateMavenPom>().forEach {
    with(it.pom) {
        name.set("Funcalk Graph")
        description.set("Functional Calculus for Kotlin - Graph")
    }
}
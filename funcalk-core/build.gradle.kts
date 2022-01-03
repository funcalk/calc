plugins {
    id("funcalk.library-conventions")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

tasks {
    shadowJar {
        manifest {
            attributes(Pair("Main-Class", "io.github.funcalk.repl.REPL"))
        }
        minimize()
    }
    build {
        dependsOn(shadowJar)
    }
}

tasks.withType<GenerateMavenPom>().forEach {
    with(it.pom) {
        name.set("Funcalk Core")
        description.set("Functional Calculus for Kotlin - Core")
    }
}
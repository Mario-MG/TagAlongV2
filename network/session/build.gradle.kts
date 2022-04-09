apply {
    from("$rootDir/library-build.gradle")
}

plugins {
    id("org.jetbrains.kotlin.plugin.serialization") version "1.5.31"
}

dependencies {
    "implementation"(project(":interactors:auth-interactors-core"))

    "implementation"(project(":network:network-core"))

    "implementation"("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    "implementation"("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
}

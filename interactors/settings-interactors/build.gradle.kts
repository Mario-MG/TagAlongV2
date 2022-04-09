apply {
    from("$rootDir/library-build.gradle")
}

dependencies {
    "implementation"(project(":interactors:interactors-core"))
    "implementation"(project(":interactors:settings-interactors-core"))

    "implementation"("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
}
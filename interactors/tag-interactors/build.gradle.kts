apply {
    from("$rootDir/library-build.gradle")
}

dependencies {
    "implementation"(project(":domain:tag-domain"))
    "implementation"(project(":domain:track-domain"))
    "implementation"(project(":interactors:interactors-core"))
    "implementation"(project(":interactors:tag-interactors-core"))

    "implementation"("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
}
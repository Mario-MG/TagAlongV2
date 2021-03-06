apply {
    from("$rootDir/library-build.gradle")
}

dependencies {
    "implementation"(project(":domain:tag-domain"))
    "implementation"(project(":domain:track-domain"))
    "implementation"(project(":domain:playlist-domain"))
    "implementation"(project(":domain:rule-domain"))
    "implementation"(project(":interactors:interactors-core"))
    "implementation"(project(":interactors:track-interactors-core"))

    "implementation"("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
}
apply {
    from("$rootDir/library-build.gradle")
}

dependencies {
    "implementation"(project(":domain:rule-domain"))
    "implementation"(project(":domain:tag-domain"))
    "implementation"(project(":interactors:interactors-core"))
    "implementation"(project(":interactors:rule-interactors-core"))

    "implementation"("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
}
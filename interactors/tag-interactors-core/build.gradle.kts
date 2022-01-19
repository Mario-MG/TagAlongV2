apply {
    from("$rootDir/library-build.gradle")
}

dependencies {
    "implementation"(project(":domain:tag-domain"))
}
apply {
    from("$rootDir/library-build.gradle")
}

dependencies {
    "implementation"(project(":domain:rule-domain"))
    "implementation"(project(":domain:tag-domain"))
}
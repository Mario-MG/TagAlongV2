apply {
    from("$rootDir/library-build.gradle")
}

dependencies {
    "implementation"(project(":domain:tag-domain"))
    "implementation"(project(":domain:track-domain"))
    "implementation"(project(":domain:playlist-domain"))
    "implementation"(project(":domain:rule-domain"))
}
apply {
    from("$rootDir/library-build.gradle")
}

dependencies {
    "implementation"(project(":domain:track-domain"))
    "implementation"(project(":domain:playlist-domain"))
}
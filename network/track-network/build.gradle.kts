apply {
    from("$rootDir/library-build.gradle")
}

dependencies {
    "implementation"(project(":domain:domain-common"))
    "implementation"(project(":domain:playlist-domain"))
    "implementation"(project(":domain:track-domain"))

    "implementation"(project(":interactors:interactors-core"))
    "implementation"(project(":interactors:track-interactors-core"))

    "implementation"(project(":network:network-core"))

    val retrofit_version = "2.9.0"
    "implementation"("com.squareup.retrofit2:retrofit:$retrofit_version")
    "implementation"("com.squareup.retrofit2:converter-gson:$retrofit_version")
}

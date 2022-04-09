apply {
    from("$rootDir/library-build.gradle")
}

dependencies {
    "implementation"(project(":interactors:interactors-core"))
    "implementation"(project(":interactors:auth-interactors-core"))
    "implementation"(project(":interactors:settings-interactors-core"))

    "implementation"("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")

    val okhttp3_version = "4.9.2"
    "implementation"("com.squareup.okhttp3:logging-interceptor:$okhttp3_version")

    val retrofit_version = "2.9.0"
    "implementation"("com.squareup.retrofit2:retrofit:$retrofit_version")
    "implementation"("com.squareup.retrofit2:converter-gson:$retrofit_version")
}

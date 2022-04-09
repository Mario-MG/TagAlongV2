apply {
    from("$rootDir/android-library-build.gradle")
}

dependencies {
    "implementation"(project(":domain:domain-common"))
    "implementation"(project(":domain:tag-domain"))
    "implementation"(project(":domain:track-domain"))
    "implementation"(project(":domain:playlist-domain"))
    "implementation"(project(":domain:rule-domain"))

    "implementation"(project(":interactors:interactors-core"))
    "implementation"(project(":interactors:tag-interactors-core"))
    "implementation"(project(":interactors:track-interactors-core"))
    "implementation"(project(":interactors:playlist-interactors-core"))
    "implementation"(project(":interactors:rule-interactors-core"))
    "implementation"(project(":interactors:auth-interactors-core"))
    "implementation"(project(":interactors:settings-interactors-core"))

    "implementation"("androidx.security:security-crypto-ktx:1.1.0-alpha03")

    "implementation"("androidx.preference:preference-ktx:1.2.0")

    val room_version = "2.3.0"
    "implementation"("androidx.room:room-runtime:$room_version")
    "annotationProcessor"("androidx.room:room-compiler:$room_version")
    "kapt"("androidx.room:room-compiler:$room_version")
    "implementation"("androidx.room:room-ktx:$room_version")

    val requery_version = "3.36.0"
    "implementation"("com.github.requery:sqlite-android:$requery_version")
}
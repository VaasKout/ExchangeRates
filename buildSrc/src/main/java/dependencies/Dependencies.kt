package dependencies

object Dependencies {

    const val kotlin_std_lib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin_coroutines}"

    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val recycler_view = "androidx.recyclerview:recyclerview:${Versions.recycler_view}"
    const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"

    const val fragment_ktx = "androidx.fragment:fragment-ktx:${Versions.fragment_ktx}"

    const val lifecycle_viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycle_runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val okhttp_login_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

    const val room_runtime = "androidx.room:room-runtime:${Versions.room}"
    const val room_compiler = "androidx.room:room-compiler:${Versions.room}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room}"

    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"

    const val hilt_android = "com.google.dagger:hilt-android:${Versions.hilt}"

    const val navigation_fragment = "androidx.navigation:navigation-fragment:${Versions.navigation}"
    const val navigation_ui = "androidx.navigation:navigation-ui:${Versions.navigation}"

}
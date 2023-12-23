plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id ("androidx.navigation.safeargs")
	id ("kotlin-parcelize")
	id ("kotlin-kapt")
	id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}


android {
	namespace = "com.c3.mobileapps"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.c3.mobileapps"
		minSdk = 22
		targetSdk = 33
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}
	buildFeatures {
		viewBinding = true
	}
}

dependencies {

	implementation("androidx.core:core-ktx:1.9.0")
	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("com.google.android.material:material:1.10.0")
	implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-crashlytics:18.6.0")
    implementation("com.google.firebase:firebase-analytics:21.5.0")

    testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

	/* Navigation */
	implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
	implementation("androidx.navigation:navigation-ui-ktx:2.7.5")

	/* Retrofit */
	implementation("com.squareup.retrofit2:retrofit:2.9.0")
	implementation("com.squareup.retrofit2:converter-gson:2.9.0")
	implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

	/* Gson */
	implementation("com.google.code.gson:gson:2.10.1")

	/* Glide */
	implementation("com.github.bumptech.glide:glide:4.16.0")

	/* koin */
	implementation ("io.insert-koin:koin-android:3.5.0")
	implementation ("io.insert-koin:koin-android-compat:3.5.0")

	
	/* Room */
	implementation ("androidx.room:room-runtime:2.6.0")
	ksp("androidx.room:room-compiler:2.5.0")
	implementation ("androidx.room:room-ktx:2.6.0")

	/* LiveData ViewModel */
	implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
	implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
	implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

	/* Bottom Navigation */
	implementation("com.google.android.material:material:1.2.0-alpha02")

	// SnackBar
//	implementation ("com.google.android.material:material:1.3.0")

	//shimmer
	implementation ("com.facebook.shimmer:shimmer:0.5.0")

	//youtubePlayer
	implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")
	implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:chromecast-sender:0.28")

}
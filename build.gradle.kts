// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
	dependencies {
		classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.5")
		classpath("com.android.tools.build:gradle:7.3.1")
	}
}
plugins {
	id("com.android.application") version "8.1.2" apply false
	id("org.jetbrains.kotlin.android") version "1.9.0" apply false
	id("com.google.devtools.ksp") version "1.9.20-1.0.14" apply false
	id ("com.android.library") version "8.0.2" apply false
}
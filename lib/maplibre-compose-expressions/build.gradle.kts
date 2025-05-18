@file:OptIn(ExperimentalKotlinGradlePluginApi::class, ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
 // id("library-conventions")
 // id("android-library-conventions")
  id("org.jetbrains.kotlin.multiplatform") version("2.1.21")
  id("org.jetbrains.kotlin.plugin.compose") version("2.1.21")
  id("com.android.library") version("8.9.3")
  id("org.jetbrains.compose") version("1.8.0")
 // id(libs.plugins.mavenPublish.get().pluginId)
}

android {
  namespace = "dev.sargunv.maplibrecompose.expressions"
  compileSdk = 35

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
  }
}

kotlin {
  androidTarget {
    //compilerOptions { jvmTarget = project.getJvmTarget() }
    instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
    publishLibraryVariants("release", "debug")
  }
  iosArm64()
  iosSimulatorArm64()
  iosX64()
//  jvm("desktop") { compilerOptions { jvmTarget = project.getJvmTarget() } }
//  js(IR) { browser() }
//  wasmJs { browser() }

  sourceSets {
    commonMain.dependencies { implementation(compose.foundation) }

//    commonTest.dependencies {
//      implementation(kotlin("test"))
//      implementation(kotlin("test-common"))
//      implementation(kotlin("test-annotations-common"))
//    }

    androidUnitTest.dependencies { implementation(compose.desktop.currentOs) }

//    androidInstrumentedTest.dependencies {
//      implementation(compose.desktop.uiTestJUnit4)
//      implementation(libs.androidx.composeUi.testManifest)
//    }
  }
}

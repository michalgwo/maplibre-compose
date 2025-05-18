@file:OptIn(ExperimentalKotlinGradlePluginApi::class, ExperimentalComposeLibrary::class)

import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
  //id("library-conventions")
  //id("android-library-conventions")
  id("org.jetbrains.kotlin.multiplatform") version("2.1.21")
  id("org.jetbrains.kotlin.native.cocoapods") version("2.1.21")
  id("org.jetbrains.kotlin.plugin.compose") version("2.1.21")
  id("com.android.library") version("8.9.3")
  id("org.jetbrains.compose") version("1.8.0")
 // id(libs.plugins.mavenPublish.get().pluginId)
}

android {
  namespace = "dev.sargunv.maplibrecompose.material3"
  compileSdk = 35

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
  }
}

kotlin {
  androidTarget {
 //   compilerOptions { jvmTarget = project.getJvmTarget() }
    instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
    publishLibraryVariants("release", "debug")
  }
  iosArm64()
  iosSimulatorArm64()
  iosX64()
//  jvm("desktop") { compilerOptions { jvmTarget = project.getJvmTarget() } }
//  js(IR) { browser() }

  cocoapods {
    noPodspec()
    ios.deploymentTarget = project.properties["iosDeploymentTarget"]!!.toString()
    pod("MapLibre", "6.13.0")
  }

  sourceSets {
    commonMain.dependencies {
      implementation(compose.material3)
      implementation(compose.components.resources)
      api(project(":maplibre-compose:lib:maplibre-compose"))
    }
//
//    commonTest.dependencies {
//      implementation(kotlin("test"))
//      implementation(kotlin("test-common"))
//      implementation(kotlin("test-annotations-common"))
//      @OptIn(ExperimentalComposeLibrary::class) implementation(compose.uiTest)
//    }
//
//    androidUnitTest.dependencies { implementation(compose.desktop.currentOs) }
//
//    androidInstrumentedTest.dependencies {
//      implementation(compose.desktop.uiTestJUnit4)
//      implementation(libs.androidx.composeUi.testManifest)
//    }
  }
}

compose.resources { packageOfResClass = "dev.sargunv.maplibrecompose.material3.generated" }

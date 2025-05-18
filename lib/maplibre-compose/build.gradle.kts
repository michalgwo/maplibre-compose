@file:OptIn(ExperimentalKotlinGradlePluginApi::class, ExperimentalComposeLibrary::class)

import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
 // id("library-conventions")
  //id("android-library-conventions")
  id("org.jetbrains.kotlin.multiplatform") version("2.1.21")
  id("org.jetbrains.kotlin.native.cocoapods") version("2.1.21")
  id("org.jetbrains.kotlin.plugin.compose") version("2.1.21")
  id("com.android.library") version("8.9.3")
  id("org.jetbrains.compose") version("1.8.0")
//  id(libs.plugins.mavenPublish.get().pluginId)
}

android {
  namespace = "dev.sargunv.maplibrecompose"
  compileSdk = 35

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
  }
}

val desktopResources: Configuration by
  configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
  }

//dependencies {
//  desktopResources(
//    project(path = ":lib:maplibre-compose-webview", configuration = "jsBrowserDistribution")
//  )
//}

val copyDesktopResources by
  tasks.registering(Copy::class) {
    from(desktopResources)
    eachFile { path = "files/${path}" }
    into(project.layout.buildDirectory.dir(desktopResources.name))
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
  //jvm("desktop") { compilerOptions { jvmTarget = project.getJvmTarget() } }
  //js(IR) { browser() }

  cocoapods {
    noPodspec()
    ios.deploymentTarget = project.properties["iosDeploymentTarget"]!!.toString()
    pod("MapLibre", "6.13.0")
  }

  sourceSets {
   // val desktopMain by getting

    listOf(iosMain, iosArm64Main, iosSimulatorArm64Main, iosX64Main).forEach {
      it { languageSettings { optIn("kotlinx.cinterop.ExperimentalForeignApi") } }
    }

    commonMain.dependencies {
      implementation(compose.foundation)
      implementation(compose.components.resources)
      api("co.touchlab:kermit:2.0.5")
      api(libs.spatialk.geojson)
      api(project(":maplibre-compose:lib:maplibre-compose-expressions"))
    }

    androidMain.dependencies {
      api("org.maplibre.gl:android-sdk:11.8.8")
      implementation("org.maplibre.gl:android-plugin-scalebar-v9:3.0.2")
    }

//    desktopMain.dependencies {
//      implementation(compose.desktop.common)
//      implementation(libs.kotlinx.coroutines.swing)
//      implementation(libs.webview)
//    }
//
//    jsMain.dependencies {
//      implementation(project(":lib:kotlin-maplibre-js"))
//      implementation(project(":lib:compose-html-interop"))
//    }

    commonTest.dependencies {
      implementation(kotlin("test"))
      implementation(kotlin("test-common"))
      implementation(kotlin("test-annotations-common"))

      @OptIn(ExperimentalComposeLibrary::class) implementation(compose.uiTest)
    }

    androidUnitTest.dependencies { implementation(compose.desktop.currentOs) }

//    androidInstrumentedTest.dependencies {
//      implementation(compose.desktop.uiTestJUnit4)
//      implementation(libs.androidx.composeUi.testManifest)
//    }
  }
}

compose.resources {
  packageOfResClass = "dev.sargunv.maplibrecompose.generated"

  customDirectory(
    sourceSetName = "desktopMain",
    directoryProvider = layout.dir(copyDesktopResources.map { it.destinationDir }),
  )
}

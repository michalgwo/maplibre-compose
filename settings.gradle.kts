rootProject.name = "maplibre-compose"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
  repositories {
    google {
      @Suppress("UnstableApiUsage")
      mavenContent {
        includeGroupAndSubgroups("androidx")
        includeGroupAndSubgroups("com.android")
        includeGroupAndSubgroups("com.google")
      }
    }
    mavenCentral()
    gradlePluginPortal()
  }
}

dependencyResolutionManagement {
  @Suppress("UnstableApiUsage")
  repositories {
    google {
      mavenContent {
        includeGroupAndSubgroups("androidx")
        includeGroupAndSubgroups("com.android")
        includeGroupAndSubgroups("com.google")
      }
    }
    mavenCentral()
    // https://kevinnzou.github.io/compose-webview-multiplatform/installation/
    maven("https://jogamp.org/deployment/maven")
    maven("https://sdk.smartlook.com/android/release")
  }
}

// Versions: https://plugins.gradle.org/plugin/org.gradle.toolchains.foojay-resolver-convention
plugins { id("org.gradle.toolchains.foojay-resolver-convention") version ("1.0.0") }

include(
  ":",
  ":demo-app",
  ":lib",
  ":lib:maplibre-compose",
  ":lib:maplibre-compose-material3",
  ":lib:maplibre-compose-expressions",
  ":lib:maplibre-compose-webview",
  ":lib:kotlin-maplibre-js",
  ":lib:compose-html-interop",
)

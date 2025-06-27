package dev.sargunv.maplibrecompose.demoapp

import android.app.Application
import com.smartlook.android.core.api.Smartlook

class MyCustomApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    println("ASDF OnCreate Application")

    val smartlook = Smartlook.instance
    smartlook.preferences.projectKey = "x"
    smartlook.start()
  }
}

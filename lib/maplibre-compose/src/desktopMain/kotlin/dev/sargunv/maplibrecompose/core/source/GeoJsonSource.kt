package dev.sargunv.maplibrecompose.core.source

public actual class GeoJsonSource : Source {

  @Suppress("UNREACHABLE_CODE") override val impl: Nothing = TODO()

  public actual constructor(id: String, uri: GeoJsonData.Uri, options: GeoJsonOptions)

  public actual constructor(id: String, geoJson: GeoJsonData.GeoJson, options: GeoJsonOptions)

  public actual constructor(id: String, geoJson: GeoJsonData.JsonString, options: GeoJsonOptions)

  public actual fun setUri(uri: GeoJsonData.Uri) {
    TODO()
  }

  public actual fun setData(geoJson: GeoJsonData.GeoJson) {
    TODO()
  }

  public actual fun setData(geoJson: GeoJsonData.JsonString) {
    TODO()
  }
}

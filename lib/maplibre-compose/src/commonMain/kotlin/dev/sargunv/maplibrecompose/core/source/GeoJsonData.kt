package dev.sargunv.maplibrecompose.core.source

public sealed interface GeoJsonData {
  public data class Uri(val data: String) : GeoJsonData

  public data class JsonString(val data: String) : GeoJsonData

  public data class GeoJson(val data: io.github.dellisd.spatialk.geojson.GeoJson) : GeoJsonData
}

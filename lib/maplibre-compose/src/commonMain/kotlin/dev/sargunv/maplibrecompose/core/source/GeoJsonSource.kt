package dev.sargunv.maplibrecompose.core.source

/** A map data source consisting of geojson data. */
public expect class GeoJsonSource : Source {
  /**
   * @param id Unique identifier for this source
   * @param uri URI pointing to a GeoJson file
   * @param options see [GeoJsonOptions]
   */
  public constructor(id: String, uri: GeoJsonData.Uri, options: GeoJsonOptions)

  /**
   * @param id Unique identifier for this source
   * @param geoJson GeoJson data
   * @param options see [GeoJsonOptions]
   */
  public constructor(id: String, geoJson: GeoJsonData.GeoJson, options: GeoJsonOptions)

  /**
   * @param id Unique identifier for this source
   * @param geoJson Serialized GeoJson data
   * @param options see [GeoJsonOptions]
   */
  public constructor(id: String, geoJson: GeoJsonData.JsonString, options: GeoJsonOptions)

  public fun setUri(uri: GeoJsonData.Uri)

  public fun setData(geoJson: GeoJsonData.GeoJson)

  public fun setData(geoJson: GeoJsonData.JsonString)
}

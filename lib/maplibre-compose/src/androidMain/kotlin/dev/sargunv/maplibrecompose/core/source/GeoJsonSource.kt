package dev.sargunv.maplibrecompose.core.source

import dev.sargunv.maplibrecompose.core.util.correctedAndroidUri
import dev.sargunv.maplibrecompose.core.util.toMLNExpression
import dev.sargunv.maplibrecompose.expressions.ExpressionContext
import java.net.URI
import org.maplibre.android.style.sources.GeoJsonOptions as MLNGeoJsonOptions
import org.maplibre.android.style.sources.GeoJsonSource as MLNGeoJsonSource

public actual class GeoJsonSource : Source {
  override val impl: MLNGeoJsonSource

  internal constructor(source: MLNGeoJsonSource) {
    impl = source
  }

  public actual constructor(id: String, uri: GeoJsonData.Uri, options: GeoJsonOptions) {
    impl = MLNGeoJsonSource(id, URI(uri.data), buildOptionMap(options))
  }

  public actual constructor(id: String, geoJson: GeoJsonData.GeoJson, options: GeoJsonOptions) {
    impl = MLNGeoJsonSource(id, geoJson.data.json(), buildOptionMap(options))
  }

  public actual constructor(id: String, geoJson: GeoJsonData.JsonString, options: GeoJsonOptions) {
    impl = MLNGeoJsonSource(id, geoJson.data, buildOptionMap(options))
  }

  private fun buildOptionMap(options: GeoJsonOptions) =
    MLNGeoJsonOptions().apply {
      withMinZoom(options.minZoom)
      withMaxZoom(options.maxZoom)
      withBuffer(options.buffer)
      withTolerance(options.tolerance)
      withLineMetrics(options.lineMetrics)
      withCluster(options.cluster)
      withClusterMaxZoom(options.clusterMaxZoom)
      withClusterRadius(options.clusterRadius)
      options.clusterProperties.forEach { (name, aggregator) ->
        withClusterProperty(
          name,
          aggregator.reducer.compile(ExpressionContext.None).toMLNExpression()!!,
          aggregator.mapper.compile(ExpressionContext.None).toMLNExpression()!!,
        )
      }
    }

  public actual fun setUri(uri: GeoJsonData.Uri) {
    impl.setUri(uri.data.correctedAndroidUri())
  }

  public actual fun setData(geoJson: GeoJsonData.GeoJson) {
    impl.setGeoJson(geoJson.data.json())
  }

  public actual fun setData(geoJson: GeoJsonData.JsonString) {
    impl.setGeoJson(geoJson.data)
  }
}

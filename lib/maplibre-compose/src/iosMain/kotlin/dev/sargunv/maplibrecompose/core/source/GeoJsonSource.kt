package dev.sargunv.maplibrecompose.core.source

import cocoapods.MapLibre.MLNShapeSource
import cocoapods.MapLibre.MLNShapeSourceOptionBuffer
import cocoapods.MapLibre.MLNShapeSourceOptionClusterProperties
import cocoapods.MapLibre.MLNShapeSourceOptionClusterRadius
import cocoapods.MapLibre.MLNShapeSourceOptionClustered
import cocoapods.MapLibre.MLNShapeSourceOptionLineDistanceMetrics
import cocoapods.MapLibre.MLNShapeSourceOptionMaximumZoomLevel
import cocoapods.MapLibre.MLNShapeSourceOptionMaximumZoomLevelForClustering
import cocoapods.MapLibre.MLNShapeSourceOptionMinimumZoomLevel
import cocoapods.MapLibre.MLNShapeSourceOptionSimplificationTolerance
import dev.sargunv.maplibrecompose.core.util.toMLNShape
import dev.sargunv.maplibrecompose.core.util.toNSExpression
import dev.sargunv.maplibrecompose.expressions.ExpressionContext
import platform.Foundation.NSNumber
import platform.Foundation.NSURL

public actual class GeoJsonSource : Source {
  override val impl: MLNShapeSource

  internal constructor(source: MLNShapeSource) {
    impl = source
  }

  public actual constructor(id: String, uri: GeoJsonData.Uri, options: GeoJsonOptions) {
    impl =
      MLNShapeSource(
        identifier = id,
        URL = NSURL(string = uri.data),
        options = buildOptionMap(options),
      )
  }

  public actual constructor(id: String, geoJson: GeoJsonData.GeoJson, options: GeoJsonOptions) {
    impl =
      MLNShapeSource(
        identifier = id,
        shape = geoJson.data.toMLNShape(),
        options = buildOptionMap(options),
      )
  }

  public actual constructor(id: String, geoJson: GeoJsonData.JsonString, options: GeoJsonOptions) {
    impl =
      MLNShapeSource(
        identifier = id,
        shape = geoJson.data.toMLNShape(),
        options = buildOptionMap(options),
      )
  }

  private fun buildOptionMap(options: GeoJsonOptions) =
    buildMap<Any?, Any?> {
      put(MLNShapeSourceOptionMinimumZoomLevel, NSNumber(options.minZoom))
      put(MLNShapeSourceOptionMaximumZoomLevel, NSNumber(options.maxZoom))
      put(MLNShapeSourceOptionBuffer, NSNumber(options.buffer))
      put(MLNShapeSourceOptionLineDistanceMetrics, NSNumber(options.lineMetrics))
      put(MLNShapeSourceOptionSimplificationTolerance, NSNumber(options.tolerance.toDouble()))
      put(MLNShapeSourceOptionClustered, NSNumber(options.cluster))
      put(MLNShapeSourceOptionMaximumZoomLevelForClustering, NSNumber(options.clusterMaxZoom))
      put(MLNShapeSourceOptionClusterRadius, NSNumber(options.clusterRadius))
      put(
        MLNShapeSourceOptionClusterProperties,
        options.clusterProperties.mapValues { (name, aggregator) ->
          listOf(
            aggregator.reducer.compile(ExpressionContext.None).toNSExpression(),
            aggregator.mapper.compile(ExpressionContext.None).toNSExpression(),
          )
        },
      )
    }

  public actual fun setUri(uri: GeoJsonData.Uri) {
    impl.setURL(NSURL(string = uri.data))
  }

  public actual fun setData(geoJson: GeoJsonData.GeoJson) {
    impl.setShape(geoJson.data.toMLNShape())
  }

  public actual fun setData(geoJson: GeoJsonData.JsonString) {
    impl.setShape(geoJson.data.toMLNShape())
  }
}

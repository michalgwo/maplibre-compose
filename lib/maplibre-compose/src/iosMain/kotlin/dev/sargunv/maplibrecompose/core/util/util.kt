package dev.sargunv.maplibrecompose.core.util

import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import cocoapods.MapLibre.*
import dev.sargunv.maplibrecompose.core.expression.Expression
import dev.sargunv.maplibrecompose.core.expression.Insets
import dev.sargunv.maplibrecompose.core.expression.Point
import dev.sargunv.maplibrecompose.core.layer.LayerPropertyEnum
import io.github.dellisd.spatialk.geojson.BoundingBox
import io.github.dellisd.spatialk.geojson.Feature
import io.github.dellisd.spatialk.geojson.GeoJson
import io.github.dellisd.spatialk.geojson.Position
import kotlinx.cinterop.CValue
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.useContents
import kotlinx.cinterop.usePinned
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import platform.CoreGraphics.CGPoint
import platform.CoreGraphics.CGPointMake
import platform.CoreGraphics.CGRect
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGVectorMake
import platform.CoreLocation.CLLocationCoordinate2D
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.Foundation.NSData
import platform.Foundation.NSExpression
import platform.Foundation.NSPredicate
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.NSValue
import platform.Foundation.dataWithBytes
import platform.UIKit.UIColor
import platform.UIKit.UIEdgeInsetsMake
import platform.UIKit.valueWithCGVector
import platform.UIKit.valueWithUIEdgeInsets

internal fun ByteArray.toNSData(): NSData {
  return usePinned { NSData.dataWithBytes(it.addressOf(0), it.get().size.toULong()) }
}

internal fun MLNFeatureProtocol.toFeature(): Feature {
  return Feature.fromJson(JsonElement.convert(geoJSONDictionary()) as JsonObject)
}

internal fun JsonElement.Companion.convert(any: Any?): JsonElement {
  return when (any) {
    null -> JsonNull
    is Boolean -> JsonPrimitive(any)
    is Number -> JsonPrimitive(any)
    is String -> JsonPrimitive(any)
    is List<*> -> JsonArray(any.map { convert(it) })
    is Map<*, *> -> JsonObject(any.entries.associate { (k, v) -> k as String to convert(v) })
    else -> error("Unsupported type: ${any::class.simpleName}")
  }
}

internal fun CValue<CGPoint>.toDpOffset(): DpOffset = useContents { DpOffset(x = x.dp, y = y.dp) }

internal fun DpOffset.toCGPoint(): CValue<CGPoint> =
  CGPointMake(x = x.value.toDouble(), y = y.value.toDouble())

internal fun CValue<CGRect>.toDpRect(): DpRect = useContents {
  DpRect(
    left = origin.x.dp,
    top = origin.y.dp,
    right = (origin.x + size.width).dp,
    bottom = (origin.y + size.height).dp,
  )
}

internal fun DpRect.toCGRect(): CValue<CGRect> =
  CGRectMake(
    x = left.value.toDouble(),
    y = top.value.toDouble(),
    width = (right - left).value.toDouble(),
    height = (bottom - top).value.toDouble(),
  )

internal fun CValue<CLLocationCoordinate2D>.toPosition(): Position = useContents { toPosition() }

internal fun CLLocationCoordinate2D.toPosition(): Position =
  Position(longitude = longitude, latitude = latitude)

internal fun Position.toCLLocationCoordinate2D(): CValue<CLLocationCoordinate2D> =
  CLLocationCoordinate2DMake(latitude = latitude, longitude = longitude)

internal fun CValue<MLNCoordinateBounds>.toBoundingBox(): BoundingBox = useContents {
  BoundingBox(northeast = ne.toPosition(), southwest = sw.toPosition())
}

internal fun GeoJson.toMLNShape(): MLNShape {
  return MLNShape.shapeWithData(
    data = json().encodeToByteArray().toNSData(),
    encoding = NSUTF8StringEncoding,
    error = null,
  )!!
}

internal fun Expression<*>.toNSExpression(): NSExpression =
  when (value) {
    null -> NSExpression.expressionForConstantValue(null)
    else -> NSExpression.expressionWithMLNJSONObject(normalizeJsonLike(value)!!)
  }

internal fun Expression<Boolean>.toNSPredicate(): NSPredicate? =
  value?.let { NSPredicate.predicateWithMLNJSONObject(normalizeJsonLike(it)!!) }

private fun normalizeJsonLike(value: Any?): Any? =
  when (value) {
    null -> null
    is Boolean -> value
    is Number -> value
    is String -> value
    is List<*> -> value.map(::normalizeJsonLike)
    is Map<*, *> -> value.mapValues { normalizeJsonLike(it.value) }
    is Point -> NSValue.valueWithCGVector(CGVectorMake(value.x.toDouble(), value.y.toDouble()))
    is Color ->
      UIColor.colorWithRed(
        red = value.red.toDouble(),
        green = value.green.toDouble(),
        blue = value.blue.toDouble(),
        alpha = value.alpha.toDouble(),
      )

    is Insets ->
      NSValue.valueWithUIEdgeInsets(
        UIEdgeInsetsMake(
          top = value.top.toDouble(),
          left = value.left.toDouble(),
          bottom = value.bottom.toDouble(),
          right = value.right.toDouble(),
        )
      )

    is LayerPropertyEnum -> value.value

    else -> throw IllegalArgumentException("Unsupported type: ${value::class}")
  }

internal fun Alignment.toMLNOrnamentPosition(layoutDir: LayoutDirection): MLNOrnamentPosition {
  return when (align(IntSize(1, 1), IntSize(2, 2), layoutDir)) {
    IntOffset(0, 0) -> MLNOrnamentPositionTopLeft
    IntOffset(1, 0) -> MLNOrnamentPositionTopRight
    IntOffset(0, 1) -> MLNOrnamentPositionBottomLeft
    IntOffset(1, 1) -> MLNOrnamentPositionBottomRight
    else -> error("Invalid alignment")
  }
}

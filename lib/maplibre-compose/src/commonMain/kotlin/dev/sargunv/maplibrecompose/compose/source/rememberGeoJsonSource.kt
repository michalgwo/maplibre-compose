package dev.sargunv.maplibrecompose.compose.source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import dev.sargunv.maplibrecompose.core.source.GeoJsonData
import dev.sargunv.maplibrecompose.core.source.GeoJsonOptions
import dev.sargunv.maplibrecompose.core.source.GeoJsonSource

/**
 * Remember a new [GeoJsonSource] with the given [id] and [options] from the GeoJson data at the
 * given [uri].
 *
 * @throws IllegalArgumentException if a layer with the given [id] already exists.
 */
@Composable
public fun rememberGeoJsonSource(
  id: String,
  uri: GeoJsonData.Uri,
  options: GeoJsonOptions = GeoJsonOptions(),
): GeoJsonSource =
  key(id, options) {
    rememberUserSource(
      factory = { GeoJsonSource(id = id, uri = uri, options = options) },
      update = { setUri(uri) },
    )
  }

/**
 * Remember a new [GeoJsonSource] with the given [id] and [options] from the given GeoJson [data].
 *
 * @throws IllegalArgumentException if a layer with the given [id] already exists.
 */
@Composable
public fun rememberGeoJsonSource(
  id: String,
  data: GeoJsonData.GeoJson,
  options: GeoJsonOptions = GeoJsonOptions(),
): GeoJsonSource =
  key(id, options) {
    rememberUserSource(
      factory = { GeoJsonSource(id = id, geoJson = data, options = options) },
      update = { setData(data) },
    )
  }

/**
 * Remember a new [GeoJsonSource] with the given [id] and [options] from the given serialized
 * GeoJson [data].
 *
 * @throws IllegalArgumentException if a layer with the given [id] already exists.
 */
@Composable
public fun rememberGeoJsonSource(
  id: String,
  data: GeoJsonData.JsonString,
  options: GeoJsonOptions = GeoJsonOptions(),
): GeoJsonSource =
  key(id, options) {
    rememberUserSource(
      factory = { GeoJsonSource(id = id, geoJson = data, options = options) },
      update = { setData(data) },
    )
  }

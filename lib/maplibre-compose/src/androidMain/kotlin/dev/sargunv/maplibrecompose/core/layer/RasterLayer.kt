package dev.sargunv.maplibrecompose.core.layer

import dev.sargunv.maplibrecompose.core.source.Source
import dev.sargunv.maplibrecompose.core.util.toMLNExpression
import dev.sargunv.maplibrecompose.expressions.ast.CompiledExpression
import dev.sargunv.maplibrecompose.expressions.value.FloatValue
import dev.sargunv.maplibrecompose.expressions.value.MillisecondsValue
import dev.sargunv.maplibrecompose.expressions.value.RasterResampling
import org.maplibre.android.style.layers.PropertyFactory
import org.maplibre.android.style.layers.RasterLayer as MLNRasterLayer

internal actual class RasterLayer actual constructor(id: String, actual val source: Source) :
  Layer() {
  override val impl = MLNRasterLayer(id, source.id)

  actual fun setRasterOpacity(opacity: CompiledExpression<FloatValue>) {
    impl.setProperties(PropertyFactory.rasterOpacity(opacity.toMLNExpression()))
  }

  actual fun setRasterHueRotate(hueRotate: CompiledExpression<FloatValue>) {
    impl.setProperties(PropertyFactory.rasterHueRotate(hueRotate.toMLNExpression()))
  }

  actual fun setRasterBrightnessMin(brightnessMin: CompiledExpression<FloatValue>) {
    impl.setProperties(PropertyFactory.rasterBrightnessMin(brightnessMin.toMLNExpression()))
  }

  actual fun setRasterBrightnessMax(brightnessMax: CompiledExpression<FloatValue>) {
    impl.setProperties(PropertyFactory.rasterBrightnessMax(brightnessMax.toMLNExpression()))
  }

  actual fun setRasterSaturation(saturation: CompiledExpression<FloatValue>) {
    impl.setProperties(PropertyFactory.rasterSaturation(saturation.toMLNExpression()))
  }

  actual fun setRasterContrast(contrast: CompiledExpression<FloatValue>) {
    impl.setProperties(PropertyFactory.rasterContrast(contrast.toMLNExpression()))
  }

  actual fun setRasterResampling(resampling: CompiledExpression<RasterResampling>) {
    impl.setProperties(PropertyFactory.rasterResampling(resampling.toMLNExpression()))
  }

  actual fun setRasterFadeDuration(fadeDuration: CompiledExpression<MillisecondsValue>) {
    impl.setProperties(PropertyFactory.rasterFadeDuration(fadeDuration.toMLNExpression()))
  }
}

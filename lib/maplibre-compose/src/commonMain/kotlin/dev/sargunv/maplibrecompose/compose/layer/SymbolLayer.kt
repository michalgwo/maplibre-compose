package dev.sargunv.maplibrecompose.compose.layer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key as composeKey
import androidx.compose.ui.graphics.Color
import dev.sargunv.maplibrecompose.compose.FeaturesClickHandler
import dev.sargunv.maplibrecompose.core.expression.Expression
import dev.sargunv.maplibrecompose.core.expression.Expression.Companion.const
import dev.sargunv.maplibrecompose.core.expression.Expression.Companion.insets
import dev.sargunv.maplibrecompose.core.expression.Expression.Companion.literal
import dev.sargunv.maplibrecompose.core.expression.Expression.Companion.nil
import dev.sargunv.maplibrecompose.core.expression.Expression.Companion.point
import dev.sargunv.maplibrecompose.core.expression.Insets
import dev.sargunv.maplibrecompose.core.expression.Point
import dev.sargunv.maplibrecompose.core.expression.TFormatted
import dev.sargunv.maplibrecompose.core.expression.TResolvedImage
import dev.sargunv.maplibrecompose.core.layer.IconPitchAlignment
import dev.sargunv.maplibrecompose.core.layer.IconRotationAlignment
import dev.sargunv.maplibrecompose.core.layer.IconTextFit
import dev.sargunv.maplibrecompose.core.layer.SymbolAnchor
import dev.sargunv.maplibrecompose.core.layer.SymbolLayer
import dev.sargunv.maplibrecompose.core.layer.SymbolPlacement
import dev.sargunv.maplibrecompose.core.layer.SymbolZOrder
import dev.sargunv.maplibrecompose.core.layer.TextJustify
import dev.sargunv.maplibrecompose.core.layer.TextPitchAlignment
import dev.sargunv.maplibrecompose.core.layer.TextRotationAlignment
import dev.sargunv.maplibrecompose.core.layer.TextTransform
import dev.sargunv.maplibrecompose.core.layer.TextWritingMode
import dev.sargunv.maplibrecompose.core.layer.TranslateAnchor
import dev.sargunv.maplibrecompose.core.source.Source

/**
 * A symbol layer draws data from the [sourceLayer] in the given [source] as icons and/or text
 * labels.
 *
 * @param id Unique layer name.
 * @param source Vector data source for this layer.
 * @param sourceLayer Layer to use from the given vector tile [source].
 * @param minZoom The minimum zoom level for the layer. At zoom levels less than this, the layer
 *   will be hidden. A value in the range of `[0..24]`.
 * @param maxZoom The maximum zoom level for the layer. At zoom levels equal to or greater than
 *   this, the layer will be hidden. A value in the range of `[0..24]`.
 * @param filter An expression specifying conditions on source features. Only features that match
 *   the filter are displayed. Zoom expressions in filters are only evaluated at integer zoom
 *   levels. The `feature-state` expression is not supported in filter expressions.
 * @param visible Whether the layer should be displayed.
 * @param sortKey Sorts features within this layer in ascending order based on this value. Features
 *   with a higher sort key will appear above features with a lower sort key.
 * @param placement Symbol placement relative to its geometry.
 * @param spacing Distance between two symbol anchors in dp.
 *
 *   Only applicable when [placement] is [SymbolPlacement.Line].
 *
 * @param avoidEdges If true, the symbols will not cross tile edges to avoid mutual collisions.
 *   Recommended in layers that don't have enough padding in the vector tile to prevent collisions,
 *   or if it is a point symbol layer placed after a line symbol layer.
 * @param zOrder Determines whether overlapping symbols in the same layer are rendered in the order
 *   that they appear in the data source or by their y-position relative to the viewport. To control
 *   the order and prioritization of symbols otherwise, use [sortKey].
 * @param iconImage Image to use for drawing an image background.
 * @param iconOpacity The opacity at which the icon will be drawn. A value in the range `[0..1]`.
 *
 *   Ignored if [iconImage] is not specified.
 *
 * @param iconColor The color of the icon. This can only be used with SDF icons.
 *
 *   Ignored if [iconImage] is not specified.
 *
 * @param iconHaloColor The color of the icon's halo. Icon halos can only be used with SDF icons.
 *
 *   Ignored if [iconImage] is not specified.
 *
 * @param iconHaloWidth Distance of halo to the icon outline. The unit is in dp only for SDF sprites
 *   that were created with a blur radius of 8, multiplied by the display density. I.e., the radius
 *   needs to be 16 for @2x sprites, etc.
 *
 *   Ignored if [iconImage] is not specified.
 *
 * @param iconHaloBlur Fade out the halo towards the outside, in dp.
 *
 *   Ignored if [iconImage] is not specified.
 *
 * @param iconSize Scales the original size of the icon by the provided factor. The new pixel size
 *   of the image will be the original pixel size multiplied by [iconSize]. 1 is the original size;
 *   3 triples the size of the image.
 *
 *   Ignored if [iconImage] is not specified.
 *
 * @param iconRotationAlignment In combination with [placement], determines the rotation behavior of
 *   icons.
 *
 *   Ignored if [iconImage] is not specified.
 *
 * @param iconPitchAlignment Orientation of icon when map is pitched.
 *
 *   Ignored if [iconImage] is not specified.
 *
 * @param iconTextFit Scales the icon to fit around the associated text.
 *
 *   Ignored if not both [iconImage] and [textField] are specified.
 *
 * @param iconTextFitPadding Size of the additional area added to dimensions determined by
 *   [iconTextFit].
 *
 *   Only applicable when if [iconTextFit] is not [IconTextFit.None]. Ignored if not both
 *   [iconImage] and [textField] are specified.
 *
 * @param iconKeepUpright If true, the icon may be flipped to prevent it from being rendered
 *   upside-down.
 *
 *   Only applicable if [iconRotationAlignment] is [IconRotationAlignment.Map] and [placement] is
 *   either [SymbolPlacement.Line] or [SymbolPlacement.LineCenter].
 *
 *   Ignored if [iconImage] is not specified.
 *
 * @param iconRotate Rotates the icon clockwise by the number in degrees.
 *
 *   Ignored if [iconImage] is not specified.
 *
 * @param iconAnchor Part of the icon placed closest to the anchor.
 *
 *   Ignored if [iconImage] is not specified.
 *
 * @param iconOffset Offset distance of icon from its anchor. Positive values indicate right and
 *   down, while negative values indicate left and up. Each component is multiplied by the value of
 *   [iconSize] to obtain the final offset in dp. When combined with [iconRotate] the offset will be
 *   as if the rotated direction was up.
 *
 *   Ignored if [iconImage] is not specified.
 *
 * @param iconPadding Size of additional area round the icon bounding box used for detecting symbol
 *   collisions.
 *
 *   Ignored if [iconImage] is not specified.
 *
 * @param iconAllowOverlap If true, the icon will be visible even if it collides with other
 *   previously drawn symbols.
 *
 *   Ignored if [iconImage] is not specified, overridden by [iconOverlap], if specified.
 *
 * @param iconOverlap Controls whether to show an icon when it overlaps other symbols on the map.
 *
 *   Ignored if [iconImage] is not specified.
 *
 *   **Note**: This property is not supported on native platforms, yet.
 *
 * @param iconIgnorePlacement If true, other symbols can be visible even if they collide with the
 *   icon.
 *
 *   Ignored if [iconImage] is not specified.
 *
 * @param iconOptional If true, text will display without their corresponding icons when the icon
 *   collides with other symbols and the text does not.
 *
 *   Ignored if not both [iconImage] and [textField] are specified.
 *
 * @param iconTranslate The geometry's offset relative to the [iconTranslateAnchor]. Negative
 *   numbers indicate left and up, respectively.
 *
 *   Ignored if [iconImage] is not specified.
 *
 * @param iconTranslateAnchor Frame of reference for offsetting geometry.
 *
 *   Ignored if [iconTranslate] is not set.
 *
 * @param textField Value to use for a text label. If a plain string is provided, it will be treated
 *   as a formatted with default/inherited formatting options.
 * @param textOpacity The opacity at which the text will be drawn.
 *
 *   Ignored if [textField] is not specified.
 *
 * @param textColor The color with which the text will be drawn.
 *
 *   Ignored if [textField] is not specified.
 *
 * @param textHaloColor The color of the text's halo, which helps it stand out from backgrounds.
 *
 *   Ignored if [textField] is not specified.
 *
 * @param textHaloWidth Distance of halo to the font outline. Max text halo width is 1/4 of the
 *   font-size.
 *
 *   Ignored if [textField] is not specified.
 *
 * @param textHaloBlur The halo's fadeout distance towards the outside in dp.
 *
 *   Ignored if [textField] is not specified.
 *
 * @param textFont Font stack to use for displaying text.
 *
 *   Ignored if [textField] is not specified.
 *
 * @param textSize Font size in dp.
 *
 *   Ignored if [textField] is not specified.
 *
 * @param textTransform Specifies how to capitalize text.
 * @param textLetterSpacing Text tracking amount in ems.
 *
 *   Ignored if [textField] is not specified.
 *
 * @param textRotationAlignment In combination with [placement], determines the rotation behavior of
 *   the individual glyphs forming the text.
 *
 *   Ignored if [textField] is not specified.
 *
 * @param textPitchAlignment Orientation of text when map is pitched.
 *
 *   Ignored if [textField] is not specified.
 *
 * @param textMaxAngle Maximum angle change in degrees between adjacent characters.
 *
 *   Only applicable if [placement] is [SymbolPlacement.Line] or [SymbolPlacement.LineCenter]
 *
 *   Ignored if [textField] is not specified.
 *
 * @param textMaxWidth The maximum line width in ems for text wrapping.
 *
 *   Ignored if [textField] is not specified.
 *
 * @param textLineHeight Text leading value in ems for multi-line text.
 *
 *   Ignored if [textField] is not specified.
 *
 * @param textJustify Text justification options.
 *
 *   Ignored if [textField] is not specified.
 *
 * @param textWritingMode The property allows control over a symbol's orientation. Note that the
 *   property values act as a hint, so that a symbol whose language doesn’t support the provided
 *   orientation will be laid out in its natural orientation. Example: English point symbol will be
 *   rendered horizontally even if array value contains single 'vertical' enum value. The order of
 *   elements in an array define priority order for the placement of an orientation variant.
 *
 *   Ignored if [textField] is not specified.
 *
 * @param textKeepUpright If true, the text may be flipped vertically to prevent it from being
 *   rendered upside-down.
 *
 *   Only applicable if [textRotationAlignment] is [TextRotationAlignment.Map] and [placement] is
 *   [SymbolPlacement.Line] or [SymbolPlacement.LineCenter]
 *
 *   Ignored if [textField] is not specified.
 *
 * @param textRotate Rotates the text clockwise. Unit in degrees.
 *
 *   Ignored if [textField] is not specified.
 *
 * @param textAnchor Part of the text placed closest to the anchor.
 *
 *   Overridden by [textVariableAnchorOffset].
 *
 *   Ignored if [textField] is not specified.
 *
 * @param textOffset Offset distance of text from its anchor in ems. Positive values indicate right
 *   and down, while negative values indicate left and up. If used with [textVariableAnchor], input
 *   values will be taken as absolute values. Offsets along the x- and y-axis will be applied
 *   automatically based on the anchor position.
 *
 *   Overridden by [textRadialOffset].
 *
 *   Ignored if [textField] is not specified.
 *
 * @param textVariableAnchor To increase the chance of placing high-priority labels on the map, you
 *   can provide an array of [textAnchor] locations: the renderer will attempt to place the label at
 *   each location, in order, before moving onto the next label. Use [textJustify] =
 *   [TextJustify.Auto] to choose justification based on anchor position. To apply an offset, use
 *   the [textRadialOffset] or the two-dimensional [textOffset].
 *
 *   Only applicable if [placement] is [SymbolPlacement.Point].
 *
 *   Ignored if [textField] is not specified.
 *
 * @param textRadialOffset Radial offset of text, in the direction of the symbol's anchor. Useful in
 *   combination with [textVariableAnchor], which defaults to using the two-dimensional [textOffset]
 *   if present.
 *
 *   Ignored if [textField] is not specified.
 *
 * @param textVariableAnchorOffset To increase the chance of placing high-priority labels on the
 *   map, you can provide an array of [SymbolAnchor] locations, each paired with an offset value.
 *   The renderer will attempt to place the label at each location, in order, before moving on to
 *   the next location+offset. Use [textJustify] = [TextJustify.Auto] to choose justification based
 *   on anchor position.
 *
 *   Each anchor location is accompanied by a point which defines the offset when the corresponding
 *   anchor location is used. Positive offset values indicate right and down, while negative values
 *   indicate left and up. Anchor locations may repeat, allowing the renderer to try multiple
 *   offsets to try and place a label using the same anchor.
 *
 *   When present, this property takes precedence over [textAnchor], [textVariableAnchor],
 *   [textOffset] and [textRadialOffset].
 *
 *   Example: ``` listOf( SymbolAnchor.Top to Point(0, 4), SymbolAnchor.Left to Point(3, 0),
 *   SymbolAnchor.Bottom to Point(1, 1) ``` When the renderer chooses the top anchor, [0, 4] will be
 *   used for [textOffset]; the text will be shifted down by 4 ems. When the renderer chooses the
 *   left anchor, [3, 0] will be used for [textOffset]; the text will be shifted right by 3 ems.
 *   Etc.
 *
 *   Ignored if [textField] is not specified.
 *
 * @param textPadding Size of the additional area in dp around the text bounding box used for
 *   detecting symbol collisions.
 *
 *   Ignored if [textField] is not specified.
 *
 * @param textAllowOverlap If true, the text will be visible even if it collides with other
 *   previously drawn symbols.
 *
 *   Overridden by [textOverlap].
 *
 *   Ignored if [textField] is not specified.
 *
 * @param textOverlap Controls whether to show an icon/text when it overlaps other symbols on the
 *   map. See [SymbolOverlap]. Overrides [textAllowOverlap].
 *
 *   Ignored if [textField] is not specified.
 *
 *   **Note**: This property is not supported on native platforms, yet.
 *
 * @param textIgnorePlacement If true, other symbols can be visible even if they collide with the
 *   text.
 *
 *   Ignored if [textField] is not specified.
 *
 * @param textOptional If true, icons will display without their corresponding text when the text
 *   collides with other symbols and the icon does not.
 *
 *   Ignored if not both [textField] and [iconImage] are specified.
 *
 * @param textTranslate Distance that the text's anchor is moved from its original placement in dp.
 *   Positive values indicate right and down, while negative values indicate left and up.
 *
 *   Ignored if [textField] is not specified.
 *
 * @param textTranslateAnchor Controls the frame of reference for [textTranslate].
 *
 *   Ignored if [textField] is not specified.
 *
 * @param onClick Function to call when any feature in this layer has been clicked.
 * @param onLongClick Function to call when any feature in this layer has been long-clicked.
 */
@Composable
@Suppress("NOTHING_TO_INLINE")
public inline fun SymbolLayer(
  id: String,
  source: Source,
  sourceLayer: String = "",
  minZoom: Float = 0.0f,
  maxZoom: Float = 24.0f,
  filter: Expression<Boolean> = nil(),
  visible: Boolean = true,
  sortKey: Expression<Number> = nil(),
  placement: Expression<SymbolPlacement> = const(SymbolPlacement.Point),
  spacing: Expression<Number> = const(250.0),
  avoidEdges: Expression<Boolean> = const(false),
  zOrder: Expression<SymbolZOrder> = const(SymbolZOrder.Auto),

  // icon image
  iconImage: Expression<TResolvedImage> = nil(),

  // icon colors
  iconOpacity: Expression<Number> = const(1.0),
  iconColor: Expression<Color> = const(Color.Black),
  iconHaloColor: Expression<Color> = const(Color.Transparent),
  iconHaloWidth: Expression<Number> = const(0.0),
  iconHaloBlur: Expression<Number> = const(0.0),

  // icon layout
  iconSize: Expression<Number> = const(1.0),
  iconRotationAlignment: Expression<IconRotationAlignment> = const(IconRotationAlignment.Auto),
  iconPitchAlignment: Expression<IconPitchAlignment> = const(IconPitchAlignment.Auto),
  iconTextFit: Expression<IconTextFit> = const(IconTextFit.None),
  iconTextFitPadding: Expression<Insets> = insets(0, 0, 0, 0),
  iconKeepUpright: Expression<Boolean> = const(false),
  iconRotate: Expression<Number> = const(0.0),

  // icon anchoring
  iconAnchor: Expression<SymbolAnchor> = const(SymbolAnchor.Center),
  iconOffset: Expression<Point> = point(0, 0),

  // icon collision
  iconPadding: Expression<Number> = const(2.0),
  iconAllowOverlap: Expression<Boolean> = const(false),
  iconOverlap: Expression<String> = nil(),
  iconIgnorePlacement: Expression<Boolean> = const(false),
  iconOptional: Expression<Boolean> = const(false),

  // icon translate
  iconTranslate: Expression<Point> = point(0, 0),
  iconTranslateAnchor: Expression<TranslateAnchor> = const(TranslateAnchor.Map),

  // text content
  textField: Expression<TFormatted> = nil(),

  // text glyph colors
  textOpacity: Expression<Number> = const(1.0),
  textColor: Expression<Color> = const(Color.Black),
  textHaloColor: Expression<Color> = const(Color.Transparent),
  textHaloWidth: Expression<Number> = const(0.0),
  textHaloBlur: Expression<Number> = const(0.0),

  // text glyph properties
  textFont: Expression<List<String>> =
    literal(listOf(const("Open Sans Regular"), const("Arial Unicode MS Regular"))),
  textSize: Expression<Number> = const(16.0),
  textTransform: Expression<TextTransform> = const(TextTransform.None),
  textLetterSpacing: Expression<Number> = const(0.0),
  textRotationAlignment: Expression<TextRotationAlignment> = const(TextRotationAlignment.Auto),
  textPitchAlignment: Expression<TextPitchAlignment> = const(TextPitchAlignment.Auto),
  textMaxAngle: Expression<Number> = const(45.0),

  // text paragraph layout
  textMaxWidth: Expression<Number> = const(10.0),
  textLineHeight: Expression<Number> = const(1.2),
  textJustify: Expression<TextJustify> = const(TextJustify.Center),
  textWritingMode: Expression<List<TextWritingMode>> = nil(),
  textKeepUpright: Expression<Boolean> = const(true),
  textRotate: Expression<Number> = const(0.0),

  // text anchoring
  textAnchor: Expression<SymbolAnchor> = const(SymbolAnchor.Center),
  textOffset: Expression<Point> = point(0, 0),
  textVariableAnchor: Expression<List<SymbolAnchor>> = nil(),
  textRadialOffset: Expression<Number> = const(0.0),
  textVariableAnchorOffset: Expression<List<Pair<SymbolAnchor, Point>>> = nil(),

  // text collision
  textPadding: Expression<Number> = const(2.0),
  textAllowOverlap: Expression<Boolean> = const(false),
  textOverlap: Expression<String> = nil(),
  textIgnorePlacement: Expression<Boolean> = const(false),
  textOptional: Expression<Boolean> = const(false),

  // text translate
  textTranslate: Expression<Point> = point(0, 0),
  textTranslateAnchor: Expression<TranslateAnchor> = const(TranslateAnchor.Map),
  noinline onClick: FeaturesClickHandler? = null,
  noinline onLongClick: FeaturesClickHandler? = null,
) {
  composeKey(id) {
    LayerNode(
      factory = { SymbolLayer(id = id, source = source) },
      update = {
        set(sourceLayer) { layer.sourceLayer = it }
        set(minZoom) { layer.minZoom = it }
        set(maxZoom) { layer.maxZoom = it }
        set(filter) { layer.setFilter(it) }
        set(visible) { layer.visible = it }
        set(placement) { layer.setSymbolPlacement(it) }
        set(spacing) { layer.setSymbolSpacing(it) }
        set(avoidEdges) { layer.setSymbolAvoidEdges(it) }
        set(sortKey) { layer.setSymbolSortKey(it) }
        set(zOrder) { layer.setSymbolZOrder(it) }

        set(iconAllowOverlap) { layer.setIconAllowOverlap(it) }
        set(iconOverlap) { layer.setIconOverlap(it) }
        set(iconIgnorePlacement) { layer.setIconIgnorePlacement(it) }
        set(iconOptional) { layer.setIconOptional(it) }
        set(iconRotationAlignment) { layer.setIconRotationAlignment(it) }
        set(iconSize) { layer.setIconSize(it) }
        set(iconTextFit) { layer.setIconTextFit(it) }
        set(iconTextFitPadding) { layer.setIconTextFitPadding(it) }
        set(iconImage) { layer.setIconImage(it) }
        set(iconRotate) { layer.setIconRotate(it) }
        set(iconPadding) { layer.setIconPadding(it) }
        set(iconKeepUpright) { layer.setIconKeepUpright(it) }
        set(iconOffset) { layer.setIconOffset(it) }
        set(iconAnchor) { layer.setIconAnchor(it) }
        set(iconPitchAlignment) { layer.setIconPitchAlignment(it) }
        set(iconOpacity) { layer.setIconOpacity(it) }
        set(iconColor) { layer.setIconColor(it) }
        set(iconHaloColor) { layer.setIconHaloColor(it) }
        set(iconHaloWidth) { layer.setIconHaloWidth(it) }
        set(iconHaloBlur) { layer.setIconHaloBlur(it) }
        set(iconTranslate) { layer.setIconTranslate(it) }
        set(iconTranslateAnchor) { layer.setIconTranslateAnchor(it) }

        set(textPitchAlignment) { layer.setTextPitchAlignment(it) }
        set(textRotationAlignment) { layer.setTextRotationAlignment(it) }
        set(textField) { layer.setTextField(it) }
        set(textFont) { layer.setTextFont(it) }
        set(textSize) { layer.setTextSize(it) }
        set(textMaxWidth) { layer.setTextMaxWidth(it) }
        set(textLineHeight) { layer.setTextLineHeight(it) }
        set(textLetterSpacing) { layer.setTextLetterSpacing(it) }
        set(textJustify) { layer.setTextJustify(it) }
        set(textRadialOffset) { layer.setTextRadialOffset(it) }
        set(textVariableAnchor) { layer.setTextVariableAnchor(it) }
        set(textVariableAnchorOffset) { layer.setTextVariableAnchorOffset(it) }
        set(textAnchor) { layer.setTextAnchor(it) }
        set(textMaxAngle) { layer.setTextMaxAngle(it) }
        set(textWritingMode) { layer.setTextWritingMode(it) }
        set(textRotate) { layer.setTextRotate(it) }
        set(textPadding) { layer.setTextPadding(it) }
        set(textKeepUpright) { layer.setTextKeepUpright(it) }
        set(textTransform) { layer.setTextTransform(it) }
        set(textOffset) { layer.setTextOffset(it) }
        set(textAllowOverlap) { layer.setTextAllowOverlap(it) }
        set(textOverlap) { layer.setTextOverlap(it) }
        set(textIgnorePlacement) { layer.setTextIgnorePlacement(it) }
        set(textOptional) { layer.setTextOptional(it) }
        set(textOpacity) { layer.setTextOpacity(it) }
        set(textColor) { layer.setTextColor(it) }
        set(textHaloColor) { layer.setTextHaloColor(it) }
        set(textHaloWidth) { layer.setTextHaloWidth(it) }
        set(textHaloBlur) { layer.setTextHaloBlur(it) }
        set(textTranslate) { layer.setTextTranslate(it) }
        set(textTranslateAnchor) { layer.setTextTranslateAnchor(it) }
      },
      onClick = onClick,
      onLongClick = onLongClick,
    )
  }
}

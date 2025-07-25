package dev.sargunv.maplibrecompose.compose.source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import dev.sargunv.maplibrecompose.compose.engine.LocalStyleNode
import dev.sargunv.maplibrecompose.core.source.Source

@Composable
internal fun <T : Source> rememberUserSource(factory: (String) -> T, update: T.() -> Unit): T {
  val node = LocalStyleNode.current
  val source = remember(node) { factory(node.sourceManager.nextId()) }
  LaunchedEffect(source, update, node.style.isUnloaded) {
    if (!node.style.isUnloaded) source.update()
  }
  return source
}

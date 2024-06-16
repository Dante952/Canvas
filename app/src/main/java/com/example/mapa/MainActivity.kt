package com.example.mapa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrawMapWithNavigation()
        }
    }
}

@Composable
fun DrawMapWithNavigation() {
    val icons = remember {
        mutableStateListOf(
            IconWithCoordinates(R.drawable.ic_cuadro, 20f, 90f),
            IconWithCoordinates(R.drawable.ic_cuadro, 160f, 20f),
            IconWithCoordinates(R.drawable.ic_cuadro, 280f, 90f),
            IconWithCoordinates(R.drawable.ic_cuadro, 20f, 250f),
            IconWithCoordinates(R.drawable.ic_cuadro, 20f, 420f),
            IconWithCoordinates(R.drawable.ic_cuadro, 20f, 620f),
            IconWithCoordinates(R.drawable.ic_cuadro, 280f, 820f),
            IconWithCoordinates(R.drawable.ic_cuadro, 280f, 250f),
            IconWithCoordinates(R.drawable.ic_cuadro, 280f, 420f),
            IconWithCoordinates(R.drawable.ic_cuadro, 280f, 620f),
        )
    }
    var currentExpandedIcon by remember { mutableStateOf<IconWithCoordinates?>(null) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Draw the map background
        Canvas(modifier = Modifier.fillMaxSize()) {
            val path = createRectanglePath(size.width, size.height, padding = 50f)

            drawPath(
                path = path,
                color = Color.Black,
                style = Stroke(width = 20f)
            )
        }

        // Draw icons and handle clicks
        icons.forEach { icon ->
            val painter: Painter = painterResource(id = icon.iconRes)
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .absoluteOffset(icon.x.dp, icon.y.dp)
                    .clickable {
                        if (currentExpandedIcon == icon) {
                            currentExpandedIcon = null
                        } else {
                            currentExpandedIcon = icon
                        }
                    }
            )
        }

        // Draw expanded card if there is an icon expanded
        currentExpandedIcon?.let { expandedIcon ->
            ExpandedCard(
                icon = expandedIcon,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun ExpandedCard(icon: IconWithCoordinates, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(600.dp).width(400.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Icon Details:")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Icon Resource: ${icon.iconRes}")
            Text("Coordinates: (${icon.x}, ${icon.y})")
        }
    }
}

fun createRectanglePath(width: Float, height: Float, padding: Float): Path {
    return Path().apply {
        val rectWidth = width - 2 * padding
        val rectHeight = height - 2 * padding
        val startX = padding
        val startY = padding

        moveTo(startX, startY)
        lineTo(startX + rectWidth, startY)
        lineTo(startX + rectWidth, startY + rectHeight)
        lineTo(startX, startY + rectHeight)
        close()
    }
}

data class IconWithCoordinates(val iconRes: Int, val x: Float, val y: Float)

@Preview(showBackground = true)
@Composable
fun DrawMapPreview() {
    DrawMapWithNavigation()
}

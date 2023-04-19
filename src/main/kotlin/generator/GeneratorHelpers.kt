package generator

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import data.nodes.Component
import data.nodes.Instance
import data.nodes.Text
import data.nodes.properties.Paint
import data.nodes.properties.Rectangle
import java.math.BigDecimal
import java.math.RoundingMode

class GeneratorHelpers {
    companion object {

        fun getColorModifier(paint: Paint): String {
            val color = paint.color
            val roundedRed = BigDecimal(color.r).setScale(2, RoundingMode.HALF_EVEN)
            val roundedGreen = BigDecimal(color.g).setScale(2, RoundingMode.HALF_EVEN)
            val roundedBlue = BigDecimal(color.b).setScale(2, RoundingMode.HALF_EVEN)
            val roundedAlpha = BigDecimal(color.a).setScale(2, RoundingMode.HALF_EVEN)
            return "Color(red=${roundedRed}f,·green=${roundedGreen}f,·blue=${roundedBlue}f,·alpha=${roundedAlpha}f)"
        }
        fun getComposableAnnotation() = AnnotationSpec.builder(ClassName("", "Composable")).build()

        fun getModifierType() = ClassName("androidx.compose.ui", "Modifier")

        fun generateIdentifier(name: String): String {
            return name.split("-").joinToString("").split(" ").joinToString("_")
        }
        fun generateModifier(instance: Instance): String {
            return "modifier·=·Modifier.${generateModifierSize(instance.absoluteRenderBounds)}"
        }

        fun generateModifier(component: Component): String {
            return "modifier·=·Modifier.${generateModifierSize(component.absoluteRenderBounds)}"
        }

        private fun generateModifierSize(renderBounds: Rectangle): String {
            val roundedWidth = BigDecimal(renderBounds.width).setScale(2, RoundingMode.HALF_EVEN)
            val roundedHeight = BigDecimal(renderBounds.height).setScale(2, RoundingMode.HALF_EVEN)

            return "size(width=${roundedWidth}.dp,·height=${roundedHeight}.dp)"
        }

        fun generateButtonModifier(renderBounds: Rectangle, fills: Array<Paint>): String {
            val generatedModifier = "\nmodifier·=·Modifier.${generateModifierSize(renderBounds)}"
            if (fills.isEmpty()) {
                return generatedModifier
            }

            return "${generatedModifier},\ncolors·=·ButtonDefaults.buttonColors(backgroundColor=${getColorModifier(fills[0])})"
        }

        fun generateModifier(text: Text): String {
            return "text·=·\"${text.characters}\",·modifier·=·Modifier.${generateModifierSize(text.absoluteRenderBounds)}" +
                    ",\nfontSize·=·${text.style.fontSize - 2}.sp,·color·=·${getColorModifier(text.fills[0])}"
        }
    }
}
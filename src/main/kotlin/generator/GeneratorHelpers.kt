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
        fun getComposableAnnotation() = AnnotationSpec.builder(ClassName("", "Composable")).build()

        fun generateModifier(instance: Instance): String {
            // TODO
            return ""
        }

        fun generateModifier(component: Component): String {
            // TODO
            return ""
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

            val paint = fills[0]
            val color = paint.color
            val roundedRed = BigDecimal(color.r).setScale(2, RoundingMode.HALF_EVEN)
            val roundedGreen = BigDecimal(color.g).setScale(2, RoundingMode.HALF_EVEN)
            val roundedBlue = BigDecimal(color.b).setScale(2, RoundingMode.HALF_EVEN)
            val roundedAlpha = BigDecimal(color.a).setScale(2, RoundingMode.HALF_EVEN)
            return "${generatedModifier},\ncolors·=·ButtonDefaults.buttonColors(backgroundColor=Color(red=${roundedRed}f,·green=${roundedGreen}f,·blue=${roundedBlue}f,·alpha=${roundedAlpha}f))"
        }

        fun generateModifier(text: Text): String {
            return "\nmodifier·=·Modifier.${generateModifierSize(text.absoluteRenderBounds)}" +
                    ",\nfontSize·=·${text.style.fontSize}.sp"
        }
    }
}
package generator

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName

class Helpers {
    companion object {
        fun getComposableAnnotation() = AnnotationSpec.builder(ClassName("", "Composable")).build()

    }
}
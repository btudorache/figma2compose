package analyser

// TODO: determine all the available component types
enum class ComponentType(val isTag: Boolean, val tag: String? = null) {
    UNKNOWN(false), SCREEN_FRAME(false), COMPONENT_FRAME(false),
    TEXT(false), UNTAGGED(false),

    BUTTON(true, "button"), M3_BUTTON(true, "m3:button");

    companion object {
        private val taggedComponentTypes = listOf(BUTTON, M3_BUTTON)
        fun findTaggedComponentType(componentName: String): ComponentType {
            val match = Regex("\\[(.*?)]").find(componentName) ?: return UNTAGGED
            val (valueMatched) = match.destructured

            return taggedComponentTypes.find { componentType -> componentType.tag == valueMatched } ?: return UNTAGGED
        }
    }
}
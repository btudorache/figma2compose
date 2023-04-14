package analyser

enum class ComponentType(val isTag: Boolean, val isM3Tag: Boolean, val tag: String? = null) {
    SCREEN_FRAME(false, false), COMPONENT_FRAME(false, false),
    TEXT(false, false), UNTAGGED(false, false),

    BUTTON(true, false, "button"),

    M3_BUTTON(true, true, "m3:button");

    companion object {
        private val taggedComponentTypes = ComponentType.values().filter { componentType -> componentType.isTag }
        fun findTaggedComponentType(componentName: String): ComponentType {
            val match = Regex("\\[(.*?)]").find(componentName) ?: return UNTAGGED
            val (valueMatched) = match.destructured

            return taggedComponentTypes.find { componentType -> componentType.tag == valueMatched } ?: return UNTAGGED
        }
    }
}
package analyser

enum class ComponentType(val isTag: Boolean, val isM3Tag: Boolean, val tag: String? = null) {
    SCREEN_FRAME(false, false), COMPONENT_FRAME(false, false),
    TEXT(false, false), UNTAGGED(false, false), UNKNOWN(false, false),

    BUTTON(true, false, "button"), TEXT_FIELD(true, false, "text-field"),

    M3_BUTTON(true, true, "m3:button"), M3_TEXT_FIELD(true, true, "m3:text-field"),
    M3_LIST_ITEM(true, true, "m3:list-item"), M3_LIST(true, true, "m3:list");

    companion object {
        private val taggedComponentTypes = ComponentType.values().filter { componentType -> componentType.isTag }
        fun findTaggedComponentType(componentName: String): ComponentType {
            val match = Regex("\\[(.*?)]").find(componentName) ?: return UNTAGGED
            val (valueMatched) = match.destructured

            return taggedComponentTypes.find { componentType -> valueMatched.startsWith(componentType.tag!!) } ?: return UNKNOWN
        }

        fun findListItemId(componentName: String): String {
            val match = Regex("\\[(.*?)]").find(componentName)
            val (nameMatched) = match!!.destructured

            return nameMatched.split(":").last()
        }
    }
}
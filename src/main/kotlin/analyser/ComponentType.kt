package analyser

enum class ComponentType(val isTag: Boolean, val isM3Tag: Boolean, val tag: String? = null, var additionalData: Any? = null) {
    SCREEN_FRAME(false, false), COMPONENT_FRAME(false, false),
    TEXT(false, false), UNTAGGED(false, false), UNKNOWN(false, false),

    BUTTON(true, false, "button"), TEXT_FIELD(true, false, "text-field"),
    ROW(true, false, "row"), LIST(true, false, "list"),

    M3_BUTTON(true, true, "m3:button"), M3_TEXT_FIELD(true, true, "m3:text-field"),
    M3_LIST_ITEM(true, true, "m3:list-item"), M3_CHECKBOX(true, true, "m3:checkbox"),
    M3_SWITCH(true, true, "m3:switch");

    companion object {
        private val taggedComponentTypes = ComponentType.values().filter { componentType -> componentType.isTag }
        fun findTaggedComponentType(componentName: String): ComponentType {
            val match = Regex("\\[(.*?)]").find(componentName) ?: return UNTAGGED
            val (valueMatched) = match.destructured

            return taggedComponentTypes.find { componentType -> valueMatched.startsWith(componentType.tag!!) } ?: return UNKNOWN
        }

        fun findListDensity(componentName: String): Int {
            val match = Regex("\\[(.*?)]").find(componentName)
            val (nameMatched) = match!!.destructured

            return nameMatched.split(":").last().toInt()
        }
    }
}
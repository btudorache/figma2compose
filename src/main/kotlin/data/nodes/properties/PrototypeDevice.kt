package data.nodes.properties

import data.nodes.properties.enums.PrototypeDeviceType
import data.nodes.properties.enums.RotationType

data class PrototypeDevice(
    val type: PrototypeDeviceType,
    val size: Size,
    val presetIdentifier: String,
    val rotation: RotationType
)

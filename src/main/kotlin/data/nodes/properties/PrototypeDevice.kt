package data.nodes.properties

import data.nodes.enums.PrototypeDeviceType
import data.nodes.enums.RotationType

data class PrototypeDevice(
    val type: PrototypeDeviceType,
    val size: Size,
    val presetIdentifier: String,
    val rotation: RotationType
)

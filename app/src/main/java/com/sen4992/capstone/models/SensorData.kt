package com.sen4992.capstone.models

import java.sql.Timestamp
import java.util.*

data class SensorData(
    val voltageData: VoltageData,
    val conversionData: ConversionData,
    val rotationData: RotationData,
    val locationData: LocationData,
    val accelerationData: AccelerationData
)

class AccelerationData (
    val id: UUID,
    val x_acc: Double,
    val y_acc: Double,
    val z_acc: Double,
    val timestamp: Timestamp
)


class LocationData (
    val id: UUID,
    val longitude: Double,
    val latitude: Double,
    val timestamp: Timestamp
)

class RotationData (
    val id: UUID,
    val roll: Double,
    val unit: String,
    val timestamp: Timestamp
)

class ConversionData (
    val id: UUID,
    val magnitude: Double,
    val unit: String,
    val timestamp: Timestamp
)

data class VoltageData(
    val id: UUID,
    val magnitude: Double,
    val unit: String,
    val timestamp: Timestamp
)

package models

import kotlinx.serialization.Serializable

@Serializable
data class PurchaseModel(val id: Int? = null, val value: Double, val userId: Int)

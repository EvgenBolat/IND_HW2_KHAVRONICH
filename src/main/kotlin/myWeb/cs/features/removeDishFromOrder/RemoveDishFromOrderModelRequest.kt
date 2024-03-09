package myWeb.cs.features.removeDishFromOrder

import kotlinx.serialization.Serializable

@Serializable
data class RemoveDishFromOrderModelRequest(val token: ULong, val name: String)

package myWeb.cs.features.putOrder

import kotlinx.serialization.Serializable

@Serializable
data class PutOrderModelRequest(val token: ULong)
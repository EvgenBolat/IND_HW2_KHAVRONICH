package myWeb.cs.features.cancelOrder

import kotlinx.serialization.Serializable

@Serializable
class CancelOrderModelRequest(val token: ULong)
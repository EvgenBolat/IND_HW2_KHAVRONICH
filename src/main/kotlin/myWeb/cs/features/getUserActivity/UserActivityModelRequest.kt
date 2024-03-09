package myWeb.cs.features.getUserActivity

import kotlinx.serialization.Serializable

@Serializable
data class UserActivityModelRequest(val token: ULong)

package myWeb.cs.features.getUserActivity

import myWeb.cs.entities.Dto.UserActivity
import kotlinx.serialization.Serializable

@Serializable
data class UserActivityModelResponse(val activity: List<UserActivity>)
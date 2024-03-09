package com.example.features.getMenu

import kotlinx.serialization.Serializable
import myWeb.cs.entities.Dto.Meal

@Serializable
data class GetMenuModelResponse(val menu: List<Meal>)
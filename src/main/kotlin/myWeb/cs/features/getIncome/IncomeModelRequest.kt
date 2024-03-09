package myWeb.cs.features.getIncome

import kotlinx.serialization.Serializable

@Serializable
data class IncomeModelRequest(val token: ULong)

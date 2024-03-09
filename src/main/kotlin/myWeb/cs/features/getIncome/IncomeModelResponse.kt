package myWeb.cs.features.getIncome

import kotlinx.serialization.Serializable

@Serializable
data class IncomeModelResponse(val income: UInt)
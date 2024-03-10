package myWeb.cs.entities

import entities.DataBaseAdapter
import myWeb.cs.entities.OrderEnteties.Order
import myWeb.cs.entities.Dto.User
import java.util.*

object PaySystem {
    fun isPayed(user: User, cardNumber: UInt, order: Order): Boolean {
        if (cardNumber !in 10000000u..100000000u) return false
        try {
            val userId: Int = user.id.toInt()
            DataBaseAdapter.addSum(userId, Date(), order.getPrice())
        } catch (e: NullPointerException) {
            throw NullPointerException("Ошибка в базе данных")
        } catch (e: Exception) {
            throw Exception("Произошла ошибка")
        }
        return true
    }

}
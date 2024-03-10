package myWeb.cs.entities.OrderEnteties

import myWeb.cs.entities.Dto.Meal

class PayedState(private val order: Order): OrderState {

    override fun addMeal(meal: Meal) {
        throw NoSuchMethodException("Невозможно добавить блюдо в уже оплаченный заказ")
    }

    override fun cook() {
        throw NoSuchMethodException("Невозможно готовить уже оплаченный заказ")
    }
}
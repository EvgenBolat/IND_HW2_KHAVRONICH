package myWeb.cs.entities.Dto

import myWeb.cs.entities.OrderEnteties.OrderState
import myWeb.cs.entities.OrderEnteties.PreCookingState

class Order() {
    val mealsList: MutableList<Meal> = mutableListOf()

    var orderState: OrderState = PreCookingState(this)

    fun addMeal(meal: Meal): Unit{
        orderState.addMeal(meal)
    }

    fun cook():Unit{
        orderState.cook()
    }

    fun cancel() {
        if (orderState !is PreCookingState){
            throw NoSuchMethodException("Невозможно отменить приготовленный заказ")
        }
        mealsList.clear()
    }

    fun removeMeal(meal: Meal) {
        if (orderState is PreCookingState)
        {
            mealsList.remove(meal)
        }
        else throw NoSuchMethodException("Невозможно убрать блюдо в уже приготовленном заказе")
    }

    fun getPrice(): UInt{
        var costOfOrder: UInt = 0u
        for (meal in mealsList){
            costOfOrder += meal.price
        }
        return costOfOrder
    }
}
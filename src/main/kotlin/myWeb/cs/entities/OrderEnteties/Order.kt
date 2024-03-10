package myWeb.cs.entities.OrderEnteties

import myWeb.cs.entities.Dto.Meal

class Order() {
    val mealsList: MutableList<Meal> = mutableListOf()

    var orderState: OrderState = BeforeCookingState(this)

    fun addMeal(meal: Meal): Unit{
        orderState.addMeal(meal)
    }

    fun cook():Unit{
        orderState.cook()
    }

    fun cancel() {
        if (orderState !is BeforeCookingState){
            throw NoSuchMethodException("Невозможно отменить приготовленный заказ")
        }
        mealsList.clear()
    }

    fun removeMeal(meal: Meal) {
        if (orderState is BeforeCookingState)
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
package myWeb.cs.entities.OrderEnteties

import myWeb.cs.entities.Dto.Meal
import myWeb.cs.entities.Dto.Order
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class CookingState(private val order: Order): OrderState {
    private var CookingTime: UInt = 0u

    private var isCooking = false
    private var isDone = false

    private val locker = ReentrantLock()

    init {
        for (meal in order.mealsList) {
            CookingTime += meal.cookingTime
        }
    }

    private fun cookingThread() {
        isCooking = true
        var currentCookingTime: Long = 0
        do {
            locker.withLock {
                currentCookingTime = CookingTime.toLong()
                CookingTime = 0u
            }
            Thread.sleep(currentCookingTime)
        } while (currentCookingTime > 0)
        locker.withLock {
            isDone = true
            order.orderState = CookedState(order)
        }
    }

    override fun addMeal(meal: Meal) {
        locker.withLock {
            if (isDone) throw Exception("Заказ уже готов. Невозможно добавить")
            order.mealsList.add(meal)
            CookingTime += meal.cookingTime
        }
    }

    override fun cook() {
        if (isCooking) return
        val thread = Thread {
            cookingThread()
        }
        thread.isDaemon = true
        thread.start()
    }
}
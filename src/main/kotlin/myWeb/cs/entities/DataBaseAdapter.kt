package entities

import myWeb.cs.entities.Dto.Meal
import myWeb.cs.entities.Dto.User
import myWeb.cs.entities.Dto.UserActivity
import myWeb.cs.entities.usersEntities.Admin
import myWeb.cs.entities.usersEntities.Visitor
import myWeb.cs.util.DatabaseManager
import java.text.SimpleDateFormat
import java.util.Date

object DataBaseAdapter {
    private val dateForm = SimpleDateFormat("dd/MM/yyyy")
    private val dateTimeFormat = SimpleDateFormat("dd/MM/yyyy hh:mm")
    private val manager = DatabaseManager()

    init {
        val createUsersTableRequest = """
        create table if not exists accounts (
            id serial primary key,
            login varchar(256),
            password int,
            role int); 
        """.trimIndent()

        val createMealsTableRequest = """
        create table if not exists meals (
            id serial primary key,
            name varchar(256),
            price int,
            amount int,
            cookingTime int); 
        """.trimIndent()

        val createIncomeTableRequest = """
        create table if not exists income (
            id serial primary key,
            date varchar(256),
            userId int,
            sum int); 
        """.trimIndent()

        val createUsersActiveTableRequest = """
        create table if not exists userActivity (
            id serial primary key,
            userId int,
            typeOfAction varchar(256),
            time varchar(256)); 
        """.trimIndent()

        manager.execute(createUsersTableRequest)
        manager.execute(createMealsTableRequest)
        manager.execute(createIncomeTableRequest)
        manager.execute(createUsersActiveTableRequest)
    }

    fun addAccount(login: String, password: Int, role: Int) {
        val selectUserRequest = """
        select * from accounts
        where login = '$login'
        and password = '$password';
        """.trimIndent()
        val result = manager.executeQuery(selectUserRequest) ?: throw NullPointerException()
        if (result.next()){
            throw ArrayStoreException()
        }
        val insertRequest = """
        insert into accounts (login, password, role)
        values ('$login', '$password', '$role');
        """.trimIndent()
        manager.update(insertRequest)
    }

    fun addMeal(name: String, price: UInt, cookingTime: Int, amount: UInt) {
        val selectByMealNameRequest = """
        select * from meals
        where name = '$name';
        """.trimIndent()
        val result = manager.executeQuery(selectByMealNameRequest) ?: throw NullPointerException()
        if (result.next()){
            throw ArrayStoreException()
        }
        val insertRequest = """
        insert into meals (name, price, amount, cookingTime)
        values ('$name', '$price','$amount', '$cookingTime');
        """.trimIndent()
        manager.update(insertRequest)
    }

    fun addSum(userid: Int, date: Date, sum: UInt){
        val selectByMealNameRequest = """
        select * from accounts
        where id = '$userid';
        """.trimIndent()
        val result = manager.executeQuery(selectByMealNameRequest) ?: throw NullPointerException()
        if (!result.next()){
            throw NullPointerException()
        }
        val insertRequest = """
        insert into income (date, userId, sum)
        values ('${dateTimeFormat.format(date)}', '$userid','$sum');
        """.trimIndent()
        manager.update(insertRequest)
    }

    fun addUserActivity(userid: Int, typeOfAction: String, time: String) {
        val selectByMealNameRequest = """
        select * from accounts
        where id = '$userid';
        """.trimIndent()
        val result = manager.executeQuery(selectByMealNameRequest) ?: throw NullPointerException()
        if (!result.next()){
            throw NullPointerException()
        }
        val insertRequest = """
        insert into userActivity (userId, typeOfAction, time)
        values ('$userid', '$typeOfAction', '$time');
        """.trimIndent()
        manager.update(insertRequest)
    }

    fun isMealInMenu(name: String): Meal {
        val selectByMealNameRequest = """
        select * from meals
        where name = '$name';
        """.trimIndent()
        val result = manager.executeQuery(selectByMealNameRequest) ?: throw NullPointerException()
        if (!result.next()){
            throw NullPointerException()
        }
        return Meal(result.getInt("id"), result.getString("name"),result.getInt("cookingTime").toUInt(),result.getInt("price").toUInt())
    }

    fun getUser(login:String, password: Int): User {
        val selectUserRequest = """
        select * from accounts
        where login = '$login'
        and password = '$password';
        """.trimIndent()
        val result = manager.executeQuery(selectUserRequest) ?: throw NullPointerException()
        if (!result.next()){
            throw NullPointerException()
        }
        if (result.getInt("role") == 1)
            return Admin( result.getInt("id").toUInt(), result.getString("login"), result.getInt("password"))
        return Visitor(result.getInt("id").toUInt(), result.getString("login"), result.getInt("password"))
    }

    fun getUserActivity(): MutableList<UserActivity>{
        val data = mutableListOf<UserActivity>()
        val selectUserRequest = """
        select * from usersActive;
        """.trimIndent()
        val result = manager.executeQuery(selectUserRequest) ?: throw NullPointerException()
        while (result.next()){
            val userActivity = UserActivity()
            userActivity.userID = result.getInt("userId")
            userActivity.time =  result.getString("time")
            userActivity.action = result.getString("typeOfAction")
            data += userActivity
        }
        return data
    }

    fun getIncome(): UInt{
        var income : UInt = 0u
        val selectUserRequest = """
        select * from income;
        """.trimIndent()
        val result = manager.executeQuery(selectUserRequest) ?: throw NullPointerException()
        while (result.next()){
            income += result.getInt("sum").toUInt()
        }
        return income
    }

    fun getMeal(name: String) : Meal {
        val selectByMealNameRequest = """
        select * from meals
        where name = '$name';
        """.trimIndent()
        val result = manager.executeQuery(selectByMealNameRequest) ?: throw NullPointerException()
        if (!result.next()){
            throw NullPointerException()
        }
        return Meal(result.getInt("id"),result.getString("name"),result.getInt("cookingTime").toUInt(),result.getInt("price").toUInt())
    }

    fun getMenu() : MutableList<Meal>{
        val selectMealRequest = """
        select * from meals;
        """.trimIndent()
        val result = manager.executeQuery(selectMealRequest) ?: throw NullPointerException()
        val meals = mutableListOf<Meal>()
        while (result.next()){
            meals += Meal(result.getInt("id"), result.getString("name"),result.getInt("cookingTime").toUInt(),result.getInt("price").toUInt())
        }
        return meals
    }

    fun increaseMealAmount(name: String, amount: UInt = 1u){
        val selectByMealNameRequest = """
        select * from meals
        where name = '$name';
        """.trimIndent()
        val result = manager.executeQuery(selectByMealNameRequest) ?: throw NullPointerException()
        if (!result.next()){
            throw NullPointerException()
        }
        val updateMealAmountValue = """
        UPDATE meals SET amount = amount + '$amount'
        WHERE name = '$name';
        """.trimIndent()
        manager.update(updateMealAmountValue)
    }

    fun reduceMealAmount(mealId: Int, amount: UInt = 1u){
        val selectByMealNameRequest = """
        select * from meals
        where id = '$mealId';
        """.trimIndent()
        val result = manager.executeQuery(selectByMealNameRequest) ?: throw NullPointerException()
        if (!result.next()){
            throw NullPointerException()
        }
        if (result.getInt("amount").toUInt() < amount){
            throw ArithmeticException()
        }
        val updateMealAmountValue = """
        UPDATE meals SET amount = amount - '$amount'
        WHERE id = '$mealId';
        """.trimIndent()
        manager.update(updateMealAmountValue)
    }

    fun changeMealPrice(name: String, price: UInt){
        val selectByMealNameRequest = """
        select * from meals
        where name = '$name';
        """.trimIndent()
        val result = manager.executeQuery(selectByMealNameRequest) ?: throw NullPointerException()
        if (!result.next()){
            throw NullPointerException()
        }
        val updateMealCostValue = """
        UPDATE meals SET price = '$price'
        WHERE name = '$name';
        """.trimIndent()
        manager.update(updateMealCostValue)
    }

    fun changeMealCookingTime(name: String, time: UInt){
        val selectByMealNameRequest = """
        select * from meals
        where name = '$name';
        """.trimIndent()
        val result = manager.executeQuery(selectByMealNameRequest) ?: throw NullPointerException()
        if (!result.next()){
            throw NullPointerException()
        }
        val updateMealCookingTimeValue = """
        UPDATE meals SET cookingTime = '$time'
        WHERE name = '$name';
        """.trimIndent()
        manager.update(updateMealCookingTimeValue)
    }

    fun removeMealFromMenu(name: String){
        val selectByMealNameRequest = """
        select * from meals
        where name = '$name';
        """.trimIndent()
        val result = manager.executeQuery(selectByMealNameRequest) ?: throw NullPointerException()
        if (!result.next()){
            throw NullPointerException()
        }
        val deleteMealByNameRequest = """
        delete from meals
        where name = '$name'
        """.trimIndent()
        manager.update(deleteMealByNameRequest)
    }

}
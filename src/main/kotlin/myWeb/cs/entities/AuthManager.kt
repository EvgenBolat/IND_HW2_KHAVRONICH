package myWeb.cs.entities

import myWeb.cs.storage.ActiveUsersList
import entities.DataBaseAdapter
import io.ktor.server.plugins.*
import myWeb.cs.entities.Dto.User
import java.security.InvalidParameterException
import java.text.SimpleDateFormat
import java.util.*

object AuthManager {
    private val dataBaseAdapter: DataBaseAdapter = DataBaseAdapter
    private val activeUsersListObj: ActiveUsersList = ActiveUsersList()

    fun addUser(login: String, password: String, role: Boolean) {
        try {
            dataBaseAdapter.addAccount(login, password.hashCode(), (if (role) 1 else 2))
        } catch (e: NullPointerException) {
            throw NullPointerException("Произошла ошибка в базе данных")
        } catch (e: ArrayStoreException) {
            throw ArrayStoreException("Такой пользователь уже существует")
        } catch (e: Exception) {
            throw Exception("Произошла ошибка")
        }
    }

    fun checkToken(token: ULong): User? {
        return activeUsersListObj.activeUsers[token]
    }

    fun logIn(login: String, password: String): ULong {
        var user: User
        try {
            user = dataBaseAdapter.getUser(login, password.hashCode())
        } catch (e: NullPointerException) {
            throw NotFoundException("Такого пользователя не существует")
        } catch (e: Exception) {
            throw Exception("Произошла ошибка")
        }
        println(activeUsersListObj.activeUsers)

        if (activeUsersListObj.activeUsers.values.indexOf(user) == -1) {
            var userToken: ULong = 0u
            do {
                userToken = (Random().nextInt(1000000000) + 10000000).toULong()
            } while (activeUsersListObj.activeUsers.keys.indexOf(userToken) != -1)
            activeUsersListObj.activeUsers[userToken] = user
            return userToken
        }
        throw InvalidParameterException("Пользователь уже в системе")
    }

    fun logOut(token: ULong) {
        val user = activeUsersListObj.activeUsers[token] ?: throw InvalidParameterException("Пользователя нет в числе активных")
        activeUsersListObj.activeUsers.remove(token)
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        dataBaseAdapter.addUserActivity(user.id.toInt(), "logout", currentDate)
    }


}
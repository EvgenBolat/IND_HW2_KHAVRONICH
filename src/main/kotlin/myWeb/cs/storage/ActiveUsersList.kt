package myWeb.cs.storage

import myWeb.cs.entities.Dto.User

class ActiveUsersList() {
    val activeUsers:MutableMap<ULong, User> = mutableMapOf()
}
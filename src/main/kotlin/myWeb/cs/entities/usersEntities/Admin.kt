package myWeb.cs.entities.usersEntities

import entities.DataBaseAdapter
import myWeb.cs.entities.Dto.User

class Admin(id: UInt, login: String, passwordHash: Int): User(id, login, passwordHash) {
    val dataBaseAdapter: DataBaseAdapter = DataBaseAdapter
}
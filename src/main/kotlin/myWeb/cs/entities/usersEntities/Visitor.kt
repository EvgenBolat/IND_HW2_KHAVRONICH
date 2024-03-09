package myWeb.cs.entities.usersEntities

import myWeb.cs.entities.Dto.User
import myWeb.cs.entities.OrderEnteties.OrderBuilder

class Visitor(id: UInt, login: String, hashpswrd: Int): User(id, login, hashpswrd) {
    val orderBuilder: OrderBuilder = OrderBuilder()
}
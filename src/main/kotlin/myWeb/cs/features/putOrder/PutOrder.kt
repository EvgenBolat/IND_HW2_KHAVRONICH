package myWeb.cs.features.putOrder

import myWeb.cs.entities.usersEntities.Admin
import myWeb.cs.entities.AuthManager
import myWeb.cs.entities.usersEntities.Visitor
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.putOrder() {
    routing {
        post("/putOrder") {
            val result = call.receive<PutOrderModelRequest>()

            val authManager = AuthManager
            val user = AuthManager.checkToken(result.token)
            if (user == null || user is Admin) {
                call.respond(HttpStatusCode.Forbidden, "Войдите, как посетитель")
            }
            try {
                (user as Visitor).orderBuilder.cookOrder()
            } catch (e: NoSuchMethodException) {
                call.respond(HttpStatusCode.MethodNotAllowed, e.message.toString())
            }
            call.respond(HttpStatusCode.OK, "Заказ готовится")
        }
    }
}
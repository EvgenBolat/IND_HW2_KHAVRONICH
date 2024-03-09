package myWeb.cs.features.cancelOrder

import myWeb.cs.entities.usersEntities.Admin
import myWeb.cs.entities.AuthManager
import myWeb.cs.entities.usersEntities.Visitor
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.cancelOrder() {
    routing {
        post("/cancelOrder") {
            val result = call.receive<CancelOrderModelRequest>()

            val user = AuthManager.checkToken(result.token)
            if (user == null || user is Admin){
                call.respond(HttpStatusCode.Forbidden, "Войдите, как посетитель")
            }

            try {
                (user as Visitor).orderBuilder.cancelOrder()
            } catch (e: NoSuchMethodException){
                call.respond(HttpStatusCode.BadRequest, e.message.toString())
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadGateway, "Произошла ошибка :(")
            }

            call.respond(HttpStatusCode.OK, "Заказ отменён. Закажите у нас что-нибудь другое! :)")
        }
    }
}
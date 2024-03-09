package myWeb.cs.features.pay

import myWeb.cs.entities.usersEntities.Admin
import myWeb.cs.entities.AuthManager
import myWeb.cs.entities.PaySystem
import myWeb.cs.entities.usersEntities.Visitor
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.pay() {
    routing {
        post("/pay") {
            val result = call.receive<PayRequest>()
            val user = AuthManager.checkToken(result.token)
            if (user == null || user is Admin){
                call.respond(HttpStatusCode.Forbidden, "Войдите как посетитель")
            }
            val userOrder = (user as Visitor).orderBuilder.getOrder()
            if (userOrder == null) {
                call.respond(HttpStatusCode.BadRequest, "Заказ ещё не готов!")
            }
            var success = false
            try {
                success = PaySystem.isPayed(user, result.cardNumber, userOrder!!)
            } catch (e: NullPointerException) {
                call.respond(HttpStatusCode.BadGateway, e.message.toString())
            }catch (e: Exception) {
                call.respond(HttpStatusCode.BadGateway, e.message.toString())
            }
            if (!success) {
                call.respond(HttpStatusCode.BadRequest, "Введите корректный номер карты, пожалуйста.")
            }

            call.respond(HttpStatusCode.OK, "Оплачен.")
        }
    }
}
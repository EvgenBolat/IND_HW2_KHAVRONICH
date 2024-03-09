package myWeb.cs.features.login

import myWeb.cs.entities.AuthManager
import myWeb.cs.features.userRegistrate.RegistrationModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.login() {
    routing {
        post("/login") {
            val result = call.receive<RegistrationModel>()

            val authManager = AuthManager
            var token: ULong = 0u
            try{
                token = authManager.logIn(result.login, result.password)
            }  catch (e: ArrayStoreException) {
                call.respond(HttpStatusCode.Conflict, e.message.toString())
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadGateway, e.message.toString())
            }
            call.respond(HttpStatusCode.OK, LoginModelResponse(token))
        }
    }
}

package com.arccorp.plugins

import com.arccorp.routes.userRouting
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        userRouting()
    }
}

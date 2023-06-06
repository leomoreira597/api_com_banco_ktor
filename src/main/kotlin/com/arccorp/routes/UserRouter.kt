package com.arccorp.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import migration.Users
import models.User
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.userRouting() = runBlocking{
    route("/user"){
        get{
            val user = transaction {
                val isEmpty = Users.selectAll().empty()
                if(!isEmpty){
                    Users.selectAll().map { Users.toUser(it) }
                }
                else{
                    emptyList()
                }
            }
            call.respond(HttpStatusCode.OK, user)
        }
        post {
            val user = call.receive<User>()
            transaction {
                val newUser = Users
                    .insertAndGetId {
                        it[name] = user.name
                        it[age] = user.age
                    }
                launch { call.respond(status = HttpStatusCode.Created, newUser.value) }
            }
        }
    }
}
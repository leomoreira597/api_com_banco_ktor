package com.arccorp.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import migration.Purchase
import models.PurchaseModel
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.purchaseRouting() = runBlocking {
    route("/purchase") {
        get {
            val purchases = transaction {
                val isEmpty = Purchase.selectAll().empty()
                if (!isEmpty) {
                    Purchase.selectAll().map { Purchase.toPurchaseModel(it) }
                } else {
                    emptyList()
                }
            }
            call.respond(HttpStatusCode.OK, purchases)
        }
        get("{id?}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val purchase = transaction {
                    Purchase.slice(Purchase.columns).select { Purchase.id eq id }.singleOrNull()
                        ?.let { Purchase.toPurchaseModel(it) }
                }
                if(purchase != null){
                    call.respond(status = HttpStatusCode.OK, purchase)
                }
                else{
                    call.respondText(
                        "Compra não encontrada",
                        status = HttpStatusCode.NotFound
                    )
                }
            }
            else{
                call.respondText(
                    "Id invalido",
                    status = HttpStatusCode.BadRequest
                )
            }
        }
        post {
            val purchase = call.receive<PurchaseModel>()
            transaction {
                val newPurchase = Purchase
                    .insertAndGetId {
                        it[value] = purchase.value
                        it[userId] = purchase.userId
                    }
                launch {
                    call.respond(
                        status = HttpStatusCode.Created,
                        "Produto Criado com Sucesso!! ${newPurchase.value}"
                    )
                }
            }
        }
    }
}
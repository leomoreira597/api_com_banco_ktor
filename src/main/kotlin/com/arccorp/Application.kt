package com.arccorp

import com.arccorp.dto.createDatabaseIfNotExists
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.arccorp.plugins.*
import com.arccorp.routes.userRouting
import io.ktor.server.routing.*
import migration.createTables
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction


fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}



fun Application.module() {
    val url = "jdbc:mysql://localhost:3306/"
    val driver = "com.mysql.cj.jdbc.Driver"
    val user = "root"
    val password = ""
    val databaseName = "primeiroktor"
    configureSerialization()
    configureRouting()
    createDatabaseIfNotExists(url, driver, user, password, databaseName)

    Database.connect(
        url = url + databaseName,
        driver = driver,
        user = user,
        password = password
    )

    transaction {
        createTables()
    }


}

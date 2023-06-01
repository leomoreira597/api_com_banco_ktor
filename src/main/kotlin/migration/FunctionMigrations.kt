package migration

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun createTables(){
    transaction {
        SchemaUtils.create(Users)
    }
}
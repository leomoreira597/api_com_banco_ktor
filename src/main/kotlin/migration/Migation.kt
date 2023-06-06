package migration

import models.User
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow

object Users : IntIdTable(){
    val name : Column<String> = varchar("name", 100)
    val age : Column<Int> = integer("age")

    fun toUser(row: ResultRow): User{
        return User(
            id = row[id].value,
            name = row[name],
            age = row[age]
        )
    }
}
package migration

import models.PurchaseModel
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow

object Purchase: IntIdTable(){
    val value : Column<Double> = double("value")
    val userId : Column<Int> = integer("userId").references(Users.id)

    fun toPurchaseModel(row: ResultRow): PurchaseModel{
        return PurchaseModel(
            id = row[id].value,
            value = row[value],
            userId = row[userId]
        )
    }
}
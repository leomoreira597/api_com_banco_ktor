package com.arccorp.dto

fun createDatabaseIfNotExists(url: String, driver: String, user: String, password: String, databaseName: String){
    val connection = java.sql.DriverManager.getConnection(url, user, password)
    val createDatabaseStatement = connection.createStatement()
    createDatabaseStatement.executeUpdate("CREATE DATABASE IF NOT EXISTS $databaseName")
    connection.close()
}
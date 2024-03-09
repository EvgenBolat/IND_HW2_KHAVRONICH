package myWeb.cs.util

import util.PropertiesUtil
import java.sql.DriverManager
import java.sql.ResultSet

class DatabaseManager {
    companion object {
        private const val URL_KEY: String = "db.url"
        private const val PASS: String = "db.pass"
        private const val USERNAME: String = "db.username"
    }

    private fun getConnection() = DriverManager.getConnection(
        PropertiesUtil.get(URL_KEY),
        PropertiesUtil.get(USERNAME),
        PropertiesUtil.get(PASS)
    )

    fun execute(sql: String): Boolean {
        val c = getConnection()
        try {
            c.use {
                val query = c.prepareStatement(sql)
                return query.execute()
            }

        } catch (e: Exception) {
            c.close()

            println(e.message)
            return false
        }
    }

    fun executeQuery(sql: String): ResultSet? {
        val c = getConnection()
        try {
            c.use {
                val query = c.prepareStatement(sql)

                return query.executeQuery()
            }

        } catch (e: Exception) {
            c.close()

            println(e.message)
            return null
        }
    }

    fun update(sql: String): Int {

        val c = getConnection()
        try {
            c.use {
                val query = c.prepareStatement(sql)
                return query.executeUpdate()
            }

        } catch (e: Exception) {
            c.close()

            println(e.message)
            return 1
        }
    }
}
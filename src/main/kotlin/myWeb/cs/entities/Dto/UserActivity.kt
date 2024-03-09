package myWeb.cs.entities.Dto

import kotlinx.serialization.Serializable

@Serializable
class UserActivity() {
    var userID = 0
    var action = ""
    var time = ""
    constructor(userID: Int, action: String, time: String) : this() {
        this.userID = userID
        this.action = action
        this.time = time
    }
}
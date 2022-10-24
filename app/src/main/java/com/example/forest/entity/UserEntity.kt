package com.example.forest.entity

import java.io.Serializable

class UserEntity : Serializable {
    val admin: Boolean = false
    val coinCount: Int = 0
    val icon: String? = null
    val id: Long = 0
    var nickname:String? = null
    var publicName: String? = null
    val userName: String? = null
}
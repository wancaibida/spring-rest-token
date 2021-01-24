package me.w2x.rest.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * Created by Charles Chen on 1/20/21.
 */
@Entity
@Table(name = "users")
class User {

    @Id
    var username: String? = null

    var password: String? = null

    var code:String?=null
}
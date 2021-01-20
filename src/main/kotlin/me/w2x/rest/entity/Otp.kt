package me.w2x.rest.entity

import javax.persistence.Entity
import javax.persistence.Id

/**
 * Created by Charles Chen on 1/20/21.
 */
@Entity
class Otp {

    @Id
    var username: String? = null

    var code: String? = null
}
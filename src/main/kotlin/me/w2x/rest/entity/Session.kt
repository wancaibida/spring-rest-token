package me.w2x.rest.entity

import javax.persistence.*

/**
 * Created by Charles Chen on 2021/03/02/.
 */
@Entity
class Session {

    @Id
    var id: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null
}
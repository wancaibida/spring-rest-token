package me.w2x.rest.entity

import javax.persistence.*

/**
 * Created by Charles Chen on 1/20/21.
 */
@Entity
@Table(name = "users")
class User {

    @Id
    var username: String? = null

    var password: String? = null

    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(
            name = "username",
            referencedColumnName = "username"
        )],
        inverseJoinColumns = [(JoinColumn(
            name = "role_id",
            referencedColumnName = "id",
            unique = false
        ))]
    )
    @ManyToMany(fetch = FetchType.EAGER)
    var roles: MutableList<Role> = mutableListOf()
}
package me.w2x.rest.service

import me.w2x.rest.entity.Role
import me.w2x.rest.entity.User
import me.w2x.rest.enu.RoleTypes
import me.w2x.rest.repository.RoleRepository
import me.w2x.rest.repository.UserRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by Charles Chen on 2021/03/02/.
 */
@Service
class InitService(
    val passwordEncoder: PasswordEncoder,
    val userRepository: UserRepository,
    val roleRepository: RoleRepository
) {

    companion object {

        const val INITAL_ADMIN_USERNAME = "admin"
        const val INITAL_ADMIN_PASSWORD = "admin"
        const val INITAL_USER_USERNAME = "user"
        const val INITAL_USER_PASSWORD = "user"

    }

    @EventListener(ApplicationReadyEvent::class)
    @Transactional
    fun init() {
        createRoles()

        val admin = createUserIfNotExists(INITAL_ADMIN_USERNAME, INITAL_ADMIN_PASSWORD)

        assignRole(admin, RoleTypes.ADMIN)

        val user = createUserIfNotExists(INITAL_USER_USERNAME, INITAL_USER_PASSWORD)

        assignRole(user, RoleTypes.USER)
    }

    fun createUserIfNotExists(username: String, password: String): User {
        return userRepository.findByUsername(username) ?: User().apply {
            this.username = username
            this.password = passwordEncoder.encode(password)

            userRepository.save(this)
        }
    }

    fun assignRole(user: User, _role: RoleTypes) {
        if (user.roles.find { it.authority == _role.authority } == null) {
            val role = roleRepository.findByAuthority(_role.authority)

            if (role == null) {
                throw RuntimeException("Role ${role?.authority} not exists!.")
            }

            user.roles.add(role)
            userRepository.save(user)
        }
    }

    private fun createRoles() {
        RoleTypes.values().forEach {
            if (!roleRepository.existsByAuthority(it.authority)) {
                Role().apply {
                    this.authority = it.authority
                    roleRepository.save(this)
                }
            }
        }
    }

}
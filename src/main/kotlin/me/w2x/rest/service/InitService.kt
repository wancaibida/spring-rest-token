package me.w2x.rest.service

import me.w2x.rest.entity.User
import me.w2x.rest.repository.UserRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

/**
 * Created by Charles Chen on 2021/03/02/.
 */
@Service
class InitService(
    val passwordEncoder: PasswordEncoder,
    val userRepository: UserRepository
) {

    @EventListener(ApplicationReadyEvent::class)
    fun init() {

        if (userRepository.findByUsername("admin") == null) {
            val user = User().apply {
                username = "admin"
                password = passwordEncoder.encode("admin")
            }

            userRepository.save(user)
        }
    }

}
package me.w2x.rest.service

import me.w2x.rest.entity.Session
import me.w2x.rest.repository.SessionRepository
import me.w2x.rest.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * Created by Charles Chen on 2021/03/02/.
 */
@Service
class SessionService(
    val sessionRepository: SessionRepository,
    val userRepository: UserRepository
) {

    @Transactional
    fun createSession(username: String): Session {
        val user = userRepository.findByUsername(username)

        return Session().apply {
            id = UUID.randomUUID().toString()
            this.user = user
            sessionRepository.save(this)
        }
    }

    fun getSession(sessionId: String): Session? {
        return sessionRepository.findById(sessionId).orElse(null)
    }
}
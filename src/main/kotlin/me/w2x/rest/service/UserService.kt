package me.w2x.rest.service

import me.w2x.rest.entity.Otp
import me.w2x.rest.entity.User
import me.w2x.rest.repository.OtpRepository
import me.w2x.rest.repository.UserRepository
import me.w2x.rest.util.GenerateCodeUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Created by Charles Chen on 1/20/21.
 */
@Service
@Transactional
class UserService(
    val userRepository: UserRepository,
    val otpRepository: OtpRepository
) {

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    fun addUser(user: User) {
        user.password = (passwordEncoder.encode(user.password))
        userRepository.save(user)
    }

    fun auth(user: User) {
        val username = user.username
            ?: throw BadCredentialsException("Bad credentials.")

        val u = userRepository.findByUsername(username)
            ?: throw BadCredentialsException("Bad credentials.")

        if (passwordEncoder.matches(user.password, u.password)) {
            renewOtp(user)
        } else {
            throw BadCredentialsException("Bad credentials.")
        }
    }

    fun renewOtp(user: User) {
        val username = user.username
            ?: throw BadCredentialsException("Bad credentials.")
        val otp = otpRepository.findByUsername(username) ?: Otp().apply {
            this.username = username
        }

        otp.code = GenerateCodeUtil.generateCode()

        otpRepository.save(otp)

        println("User ${user.username} code is ${otp.code}")
    }

    fun check(otpToValidate: Otp): Boolean {
        val username = otpToValidate.username ?: return false
        val otp = otpRepository.findByUsername(username)

        return otp != null && otp.code == otpToValidate.code
    }
}
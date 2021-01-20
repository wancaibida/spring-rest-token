package me.w2x.rest.repository

import me.w2x.rest.entity.Otp
import org.springframework.data.repository.CrudRepository

/**
 * Created by Charles Chen on 1/20/21.
 */
interface OtpRepository : CrudRepository<Otp, String> {

    fun findByUsername(username: String): Otp?
}
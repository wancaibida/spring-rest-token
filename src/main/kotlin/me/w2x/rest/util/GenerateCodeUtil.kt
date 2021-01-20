package me.w2x.rest.util

import java.security.SecureRandom

/**
 * Created by Charles Chen on 1/20/21.
 */
object GenerateCodeUtil {

    fun generateCode(): String {
        val random = SecureRandom.getInstanceStrong()
        val c = random.nextInt(9000) + 1000

        return c.toString()
    }
}
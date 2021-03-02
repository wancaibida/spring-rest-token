package me.w2x.rest.controller

import me.w2x.rest.entity.User
import me.w2x.rest.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Created by Charles Chen on 1/20/21.
 */
@Controller
@RequestMapping("users")
class AuthController(
    val userService: UserService
) {

    @PostMapping("")
    fun addUser(@RequestBody user: User): ResponseEntity<Any?> {
        userService.addUser(user)

        return ResponseEntity.ok().build()
    }

    @PostMapping("auth")
    fun auth(@RequestBody user: User?): ResponseEntity<Any?> {
        userService.auth(user!!)
        return ResponseEntity.ok().build()
    }

}
package me.w2x.rest.controller

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 * Created by Charles Chen on 1/8/21.
 */
@Controller
class HelloController {

    @GetMapping("hello")
    fun hello(): ResponseEntity<Any?> {
        return ResponseEntity.ok("hello!")
    }

    @GetMapping("")
    fun public(): ResponseEntity<String> = ResponseEntity.ok("ok")
}
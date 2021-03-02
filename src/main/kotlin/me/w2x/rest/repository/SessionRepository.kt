package me.w2x.rest.repository

import me.w2x.rest.entity.Session
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by Charles Chen on 2021/03/02/.
 */
interface SessionRepository : JpaRepository<Session, String>
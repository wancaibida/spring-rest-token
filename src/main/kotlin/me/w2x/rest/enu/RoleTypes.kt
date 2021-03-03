package me.w2x.rest.enu

enum class RoleTypes(val authority: String) {

    ADMIN(Constants.ROLE_ADMIN),
    USER(Constants.ROLE_USER)
    ;

    class Constants {
        companion object {

            const val ROLE_ADMIN = "ROLE_ADMIN"
            const val ROLE_USER = "ROLE_USER"
        }
    }
}
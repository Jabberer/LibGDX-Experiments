package com.mygdx.drop.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.mygdx.drop.Drop

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        with(config){
            title = "Drop"
            width = 800
            height = 480
        }
        LwjglApplication(Drop(), config)
    }
}

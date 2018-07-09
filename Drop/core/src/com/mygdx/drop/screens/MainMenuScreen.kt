package com.mygdx.drop.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.mygdx.drop.Drop
import ktx.app.clearScreen

class MainMenuScreen(val game: Drop): Screen {
    val camera: OrthographicCamera = OrthographicCamera(800f, 480f)
    init{
        camera.setToOrtho(false)
    }
    override fun show() {
    }

    override fun render(delta: Float) {
        clearScreen(0f,0f,0.2f,1f)
        camera.update()
        game.batch.begin()
        game.font.draw(game.batch, "Welcome to Drop!!!", 100f, 150f)
        game.font.draw(game.batch, "Tap anywhere to begin!", 100f, 100f)
        game.batch.end()
        if(Gdx.input.isTouched){
            game.screen = GameScreen(game)
            dispose()
        }
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun dispose() {
    }

    override fun hide() {
    }

}
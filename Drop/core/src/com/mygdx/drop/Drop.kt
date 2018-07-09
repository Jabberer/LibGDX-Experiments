package com.mygdx.drop

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.TimeUtils
import com.mygdx.drop.screens.MainMenuScreen
import com.badlogic.gdx.utils.Array as GdxArray
import ktx.math.*
import ktx.app.clearScreen
import ktx.collections.gdxArrayOf
import ktx.collections.iterate


class Drop : Game() {
    lateinit var batch: SpriteBatch
    lateinit var img: Texture
    lateinit var font: BitmapFont


    override fun create() {
        font = BitmapFont()
        batch = SpriteBatch()
        batch = SpriteBatch()
        this.screen = MainMenuScreen(this)
    }

    override fun render() {
        super.render()

    }

    override fun dispose() {
        img.dispose()
        batch.dispose()
    }

}

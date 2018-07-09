package com.mygdx.drop.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.TimeUtils
import com.mygdx.drop.Drop
import ktx.app.clearScreen
import ktx.collections.gdxArrayOf
import ktx.collections.iterate
import ktx.math.vec3

class GameScreen(val game: Drop): Screen {
    val dropImg: Texture = Texture("droplet.png")
    val bucketImg: Texture = Texture("bucket.png")
    val dropSound: Sound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"))
    val rainMusic: Music = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"))
    val camera: OrthographicCamera
    val bucket = Rectangle()
    val raindrops: Array<Rectangle>
    var lastDropTime = 0L
    var dropsGathered = 0
    init{
        rainMusic.isLooping = true
        camera = OrthographicCamera()
        camera.setToOrtho(false, 800f, 480f)
        with(bucket) {
            x = 800f / 2 - 64 / 2
            y = 20f
            height = 64f
            width = 64f
        }
        raindrops = gdxArrayOf()
        spawnRaindrop()
    }
    override fun hide() {

    }

    override fun show() {
        rainMusic.play()
    }

    override fun render(delta: Float) {
        clearScreen(0f,0f,0.2f, 1f)
        camera.update()
        game.batch.projectionMatrix = camera.combined
        game.batch.begin()
        game.font.draw(game.batch, "Drops Collected: $dropsGathered", 0f, 480f)
        game.batch.draw(bucketImg, bucket.x, bucket.y)
        for(drop in raindrops){
            game.batch.draw(dropImg, drop.x, drop.y)
        }
        game.batch.end()
        if(Gdx.input.isTouched){
            val touchPos = vec3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
            camera.unproject(touchPos)
            bucket.x = touchPos.x - bucket.width/2
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.deltaTime
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.deltaTime
        if(bucket.x < 0) bucket.x = 0f
        if(bucket.x > 800 - 64) bucket.x = 800f - 64
        if((TimeUtils.nanoTime() - lastDropTime) > 1000000000) spawnRaindrop()
        raindrops.iterate { drop, mutableIterator ->
            if(drop.overlaps(bucket)){
                dropSound.play()
                mutableIterator.remove()
                dropsGathered++
            }
            drop.y -= 200 * Gdx.graphics.deltaTime
            if(drop.y + 64 < 0) mutableIterator.remove()
        }
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun dispose() {
        rainMusic.dispose()
        dropSound.dispose()
        dropImg.dispose()
        bucketImg.dispose()
    }

    fun spawnRaindrop(){
        val raindrop = Rectangle()
        with(raindrop){
            x = MathUtils.random(0f, 800-64f)
            y = 480f
            width = 64f
            height = 64f
        }
        raindrops.add(raindrop)
        lastDropTime = TimeUtils.nanoTime()
    }
}
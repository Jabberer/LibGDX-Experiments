package com.mygdx.drop

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.TimeUtils
import com.badlogic.gdx.utils.Array as GdxArray
import ktx.math.*
import ktx.app.clearScreen
import ktx.collections.gdxArrayOf
import ktx.collections.iterate


class Drop : ApplicationAdapter() {
    lateinit var batch: SpriteBatch
    lateinit var img: Texture
    lateinit var dropImg: Texture
    lateinit var bucketImg: Texture
    lateinit var dropSound: Sound
    lateinit var rainMusic: Music
    lateinit var camera: OrthographicCamera
    lateinit var bucket: Rectangle
    lateinit var raindrops: GdxArray<Rectangle>
    var lastDropTime = 0L


    override fun create() {
        batch = SpriteBatch()
        dropImg = Texture("droplet.png")
        bucketImg = Texture("bucket.png")
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"))
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"))
        rainMusic.isLooping = true
        rainMusic.play()
        camera = OrthographicCamera()
        camera.setToOrtho(false, 800f, 480f)
        batch = SpriteBatch()
        bucket = Rectangle()
        with(bucket) {
            x = 800f / 2 - 64 / 2
            y = 20f
            height = 64f
            width = 64f
        }
        raindrops = gdxArrayOf()
        spawnRaindrop()
    }

    override fun render() {
        clearScreen(0f,0f,0.2f, 1f)
        camera.update()
        batch.projectionMatrix = camera.combined
        batch.begin()
        batch.draw(bucketImg, bucket.x, bucket.y)
        for(drop in raindrops){
            batch.draw(dropImg, drop.x, drop.y)
        }
        batch.end()
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
            }
            drop.y -= 200 * Gdx.graphics.deltaTime
            if(drop.y + 64 < 0) mutableIterator.remove()
        }
    }

    override fun dispose() {
        img.dispose()
        rainMusic.dispose()
        dropSound.dispose()
        dropImg.dispose()
        bucketImg.dispose()
        batch.dispose()
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

package com.liyu.huahui.utils

import android.media.MediaPlayer

/**
 * Updated by ultranity on 2020/9/20.
 * Created by liyu on 2017/3/3.
 */
class Player private constructor() {

    companion object {
        private val monitor = Any()
        private var mediaPlayer: MediaPlayer? = MediaPlayer()
        private var player: Player? = null
        @JvmStatic
        val instance: Player?
            get() {
                if (player == null) {
                    synchronized(monitor) { player = Player() }
                }
                return player
            }
    }

    fun play(url: String?) {
        try {
            if (mediaPlayer!!.isPlaying) {
                stop()
            }
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(url)
            mediaPlayer!!.prepare()
            mediaPlayer!!.setOnPreparedListener { mediaPlayer -> mediaPlayer.start() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun pause() {
        if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
            mediaPlayer!!.pause()
        }
    }

    private fun stop() {
        if (mediaPlayer != null) {
            mediaPlayer!!.stop()
            try {
                mediaPlayer!!.prepare()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun destroy() {
        if (mediaPlayer != null) {
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
        }
        player = null
    }

}
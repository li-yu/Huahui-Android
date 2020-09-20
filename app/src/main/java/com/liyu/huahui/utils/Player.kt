package com.liyu.huahui.utils;

import android.media.MediaPlayer;

/**
 * Created by liyu on 2017/3/3.
 */

public class Player {

    protected static final Object monitor = new Object();
    private static MediaPlayer mediaPlayer;
    private static Player player;

    private Player() {
        mediaPlayer = new MediaPlayer();
    }

    public static Player getInstance() {
        if (player == null) {
            synchronized (monitor) {
                player = new Player();
            }
        }
        return player;
    }

    public void play(String url) {
        try {
            if (mediaPlayer.isPlaying()) {
                stop();
            }
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            try {
                mediaPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void destroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        player = null;
    }
}

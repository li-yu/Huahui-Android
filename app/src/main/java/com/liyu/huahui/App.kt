package com.liyu.huahui

import android.app.Application
import android.content.SharedPreferences
import com.liulishuo.filedownloader.FileDownloader
import com.liyu.huahui.model.Word
import org.litepal.LitePal
/**
 * Created by ultranity on 2020/9/20.
 */
class App : Application() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate() {
        super.onCreate()
        LitePal.initialize(this)
        FileDownloader.setupOnApplicationOnCreate(this)
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE)
        accent = Word.Accent.values()[sharedPreferences.getInt("accent", Word.Accent.US.id)-1]
    }

    companion object {
        @JvmStatic
        var accent: Word.Accent = Word.Accent.US
    }
}
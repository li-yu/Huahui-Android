package com.liyu.huahui.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloadQueueSet
import com.liulishuo.filedownloader.FileDownloader
import com.liyu.huahui.App
import com.liyu.huahui.model.Word
import java.util.*

/**
 * Created by liyu on 2017/7/11.
 */
object DownloadUtil {

    private val handler = Handler(Looper.getMainLooper())

    fun start(ctx: Context, word: Word, listener: SingleFileDownloadListener?) {
        FileDownloader.getImpl().create(word.voiceUrl + "&type=" + App.accent.id).setTag(word.name).setPath(ctx.filesDir.path.toString() + "/" + word.name + App.accent + ".mp3")
                .setListener(object : FileDownloadListener() {
                    override fun pending(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}
                    override fun progress(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}
                    override fun completed(task: BaseDownloadTask) {
                        listener?.onCompleted(task.path)
                    }

                    override fun paused(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}
                    override fun error(task: BaseDownloadTask, e: Throwable) {
                        listener?.onFail(e.message)
                    }

                    override fun warn(task: BaseDownloadTask) {}
                })
                .start()
    }

    fun stop() {
        FileDownloader.getImpl().pauseAll()
    }

    fun start(ctx: Context, list: List<Word>, listener: MultiFileDownloadListener?) {
        val queueTarget: FileDownloadListener = object : FileDownloadListener() {
            override fun pending(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}
            override fun connected(task: BaseDownloadTask, etag: String, isContinue: Boolean, soFarBytes: Int, totalBytes: Int) {}
            override fun progress(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}
            override fun blockComplete(task: BaseDownloadTask) {}
            override fun retry(task: BaseDownloadTask, ex: Throwable, retryingTimes: Int, soFarBytes: Int) {}
            override fun completed(task: BaseDownloadTask) {}
            override fun paused(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}
            override fun error(task: BaseDownloadTask, e: Throwable) {
                if (listener != null) {
                    handler.post { listener.onFail(String.format("%s 下载失败！%s", task.tag, e.message)) }
                }
            }

            override fun warn(task: BaseDownloadTask) {}
        }
        val queueSet = FileDownloadQueueSet(queueTarget)
        val tasks: MutableList<BaseDownloadTask> = ArrayList()
        for (word in list) {
            if (!TextUtils.isEmpty(word.voiceUrl)) tasks.add(FileDownloader.getImpl().create(word.voiceUrl + "&type=" + App.accent.id).setPath(ctx.filesDir.path.toString() + "/" + word.name + App.accent + ".mp3").setTag(word.name))
        }
        queueSet.disableCallbackProgressTimes()
        queueSet.setAutoRetryTimes(1)
        queueSet.addTaskFinishListener(object : BaseDownloadTask.FinishListener {
            private var finishedTaskCount = 0
            override fun over(task: BaseDownloadTask) {
                finishedTaskCount++
                if (listener != null) {
                    handler.post { listener.onProcess(String.format("已完成 %s/%s 个音频缓存...", finishedTaskCount, tasks.size)) }
                    if (finishedTaskCount == tasks.size) {
                        handler.post { listener.onCompleted() }
                    }
                }
            }
        })
        queueSet.downloadTogether(tasks)
        queueSet.start()
    }

    interface MultiFileDownloadListener {
        fun onProcess(msg: String?)
        fun onCompleted()
        fun onFail(msg: String?)
    }

    interface SingleFileDownloadListener {
        fun onCompleted(path: String?)
        fun onFail(msg: String?)
    }
}
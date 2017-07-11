package com.liyu.huahui.utils;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadQueueSet;
import com.liulishuo.filedownloader.FileDownloader;
import com.liyu.huahui.App;
import com.liyu.huahui.model.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyu on 2017/7/11.
 */

public class DownloadUtil {

    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void start(final String word, String url) {
        FileDownloader.getImpl().create(url).setPath(App.getContext().getFilesDir().getPath() + "/" + word + ".mp3")
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {

                        CacheUtil.getInstance().put(word, task.getPath());

                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {

                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {

                    }
                })
                .start();
    }

    public static void start(final List<Word> list, final MultiFileDownloadListener listener) {
        final FileDownloadListener queueTarget = new FileDownloadListener() {
            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            }

            @Override
            protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            }

            @Override
            protected void blockComplete(BaseDownloadTask task) {
            }

            @Override
            protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                CacheUtil.getInstance().put((String) task.getTag(), task.getPath());
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            }

            @Override
            protected void error(final BaseDownloadTask task, final Throwable e) {
                if (listener != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFail(String.format("%s 下载失败！%s", task.getTag(), e.getMessage()));
                        }
                    });
                }
            }

            @Override
            protected void warn(BaseDownloadTask task) {
            }
        };

        final FileDownloadQueueSet queueSet = new FileDownloadQueueSet(queueTarget);

        final List<BaseDownloadTask> tasks = new ArrayList<>();

        for (Word word : list) {
            if (!TextUtils.isEmpty(word.getVoice()))
                tasks.add(FileDownloader.getImpl().create(word.getVoice()).setPath(App.getContext().getFilesDir().getPath() + "/" + word.getName() + ".mp3").setTag(word.getName()));
        }

        queueSet.disableCallbackProgressTimes();

        queueSet.setAutoRetryTimes(1);

        queueSet.addTaskFinishListener(new BaseDownloadTask.FinishListener() {
            private int finishedTaskCount = 0;

            @Override
            public void over(BaseDownloadTask task) {
                finishedTaskCount++;
                if (listener != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onProcess(String.format("已完成 %s/%s 个音频缓存...", finishedTaskCount, tasks.size()));
                        }
                    });
                    if (finishedTaskCount == tasks.size()) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onCompleted();
                            }
                        });
                    }
                }
            }
        });

        queueSet.downloadTogether(tasks);

        queueSet.start();
    }

    public static void stop() {
        FileDownloader.getImpl().pauseAll();
    }

    public interface MultiFileDownloadListener {
        void onProcess(String msg);

        void onCompleted();

        void onFail(String msg);
    }
}

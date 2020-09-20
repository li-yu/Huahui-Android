package com.liyu.huahui.adapter

import android.graphics.Paint
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.snackbar.Snackbar
import com.liyu.huahui.R
import com.liyu.huahui.model.Word
import com.liyu.huahui.ui.MainActivity
import com.liyu.huahui.utils.DownloadUtil.SingleFileDownloadListener
import com.liyu.huahui.utils.DownloadUtil.start
import com.liyu.huahui.utils.Player.Companion.instance

/**
 * Created by liyu on 2017/3/2.
 */
class WordAdapter(layoutResId: Int, data: List<Word>?) : BaseQuickAdapter<Word, BaseViewHolder>(layoutResId, data as MutableList<Word>?) {
    private var listener: OnDataChangedListener? = null
    override fun convert(helper: BaseViewHolder, item: Word) {
        helper.setText(R.id.tv_name, item.name)
        helper.setText(R.id.tv_correct, "英音：" + item.correctPhonetic)
        if (TextUtils.isEmpty(item.wrongPhonetic)) {
            helper.setText(R.id.tv_wrong, item.wrongPhonetic)
        } else {
            helper.setText(R.id.tv_wrong, "错误：" + item.wrongPhonetic)
        }
        helper.setVisible(R.id.image_play, !TextUtils.isEmpty(item.voiceUrl))
        (helper.getView<View>(R.id.tv_wrong) as TextView).paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
        helper.getView<View>(R.id.image_play).setOnClickListener { playVoice(item) }
        helper.itemView.setOnClickListener { playVoice(item) }
        helper.itemView.setOnLongClickListener {
            val deletedPosition = helper.adapterPosition
            remove(deletedPosition)
            item.delete()
            if (listener != null) {
                listener!!.onChanged(data.size)
            }
            Snackbar.make((context as MainActivity).window.decorView.rootView.findViewById(R.id.coordinatorLayout), "删除成功!",
                    Snackbar.LENGTH_LONG).setAction("撤销") {
                addData(deletedPosition, item)
                item.save()
                if (listener != null) {
                    listener!!.onChanged(data.size)
                }
            }.setActionTextColor(ContextCompat.getColor(context, R.color.snackbar_action_color)).show()
            false
        }
    }

    private fun playVoice(item: Word) {
        start(context, item, object : SingleFileDownloadListener {
            override fun onCompleted(path: String?) {
                instance!!.play(path)
            }

            override fun onFail(msg: String?) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        })
    }

    interface OnDataChangedListener {
        fun onChanged(dateSize: Int)
    }

    fun setDataChangedListener(listener: OnDataChangedListener) {
        this.listener = listener
    }
}
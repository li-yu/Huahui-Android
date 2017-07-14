package com.liyu.huahui.adapter;

import android.graphics.Paint;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liyu.huahui.R;
import com.liyu.huahui.model.Word;
import com.liyu.huahui.ui.MainActivity;
import com.liyu.huahui.utils.DownloadUtil;
import com.liyu.huahui.utils.Player;

import java.util.List;

/**
 * Created by liyu on 2017/3/2.
 */

public class WordAdapter extends BaseQuickAdapter<Word, BaseViewHolder> {

    private OnDataChangedListener listener;

    public WordAdapter(int layoutResId, List<Word> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Word item) {
        helper.setText(R.id.tv_name, item.getName());

        helper.setText(R.id.tv_correct, "正确：" + item.getCorrectPhonetic());

        if (TextUtils.isEmpty(item.getWrongPhonetic())) {
            helper.setText(R.id.tv_wrong, item.getWrongPhonetic());
        } else {
            helper.setText(R.id.tv_wrong, "错误：" + item.getWrongPhonetic());
        }

        helper.setVisible(R.id.image_play, !TextUtils.isEmpty(item.getVoiceUrl()));

        ((TextView) helper.getView(R.id.tv_wrong)).getPaint()
                .setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

        helper.getView(R.id.image_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVoice(item);
            }
        });

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVoice(item);
            }
        });

        helper.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int deletedPosition = helper.getAdapterPosition();
                remove(deletedPosition);
                item.delete();
                if (listener != null) {
                    listener.onChanged(getData().size());
                }
                Snackbar.make(((MainActivity) mContext).getWindow().getDecorView().getRootView().findViewById(R.id.coordinatorLayout), "删除成功!",
                        Snackbar.LENGTH_LONG).setAction("撤销", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addData(deletedPosition, item);
                        item.save();
                        if (listener != null) {
                            listener.onChanged(getData().size());
                        }
                    }
                }).setActionTextColor(ContextCompat.getColor(mContext, R.color.snackbar_action_color)).show();
                return false;
            }
        });
    }

    private void playVoice(Word item) {
        DownloadUtil.start(item, new DownloadUtil.SingleFileDownloadListener() {
            @Override
            public void onCompleted(String path) {
                Player.getInstance().play(path);
            }

            @Override
            public void onFail(String msg) {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public interface OnDataChangedListener {
        void onChanged(int dateSize);
    }

    public void setDataChangedListener(OnDataChangedListener listener) {
        this.listener = listener;
    }
}

package com.liyu.huahui.adapter;

import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liyu.huahui.R;
import com.liyu.huahui.model.Word;
import com.liyu.huahui.utils.CacheUtil;
import com.liyu.huahui.utils.DownloadUtil;
import com.liyu.huahui.utils.NetworkUtil;
import com.liyu.huahui.utils.Player;

import java.util.List;

/**
 * Created by liyu on 2017/3/2.
 */

public class WordAdapter extends BaseQuickAdapter<Word, BaseViewHolder> {

    public WordAdapter(int layoutResId, List<Word> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final Word item) {
        helper.setText(R.id.tv_name, item.getName());

        helper.setText(R.id.tv_correct, "正确：" + item.getCorrect());

        if (TextUtils.isEmpty(item.getWrong())) {
            helper.setText(R.id.tv_wrong, item.getWrong());
        } else {
            helper.setText(R.id.tv_wrong, "错误：" + item.getWrong());
        }

        helper.setVisible(R.id.image_play, !TextUtils.isEmpty(item.getVoice()));

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
    }

    private void playVoice(Word item) {

        String fileUri = CacheUtil.getInstance().getString(item.getName());

        if (TextUtils.isEmpty(fileUri)) {
            if (NetworkUtil.isConnected()) {
                Player.getInstance().play(item.getVoice());
                DownloadUtil.start(item.getName(), item.getVoice());
            } else {
                Toast.makeText(mContext, "网络连接不可用！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Player.getInstance().play(fileUri);
        }

    }

}

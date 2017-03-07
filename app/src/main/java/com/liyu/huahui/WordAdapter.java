package com.liyu.huahui;

import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

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
                Player.getInstance().play(String.format("http://dict.youdao.com/dictvoice?audio=%s&type=1", item.getName()));
            }
        });
    }

}

package com.liyu.huahui.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.transition.ArcMotion;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.liyu.huahui.App;
import com.liyu.huahui.R;
import com.liyu.huahui.http.ApiFactory;
import com.liyu.huahui.model.Word;
import com.liyu.huahui.model.YoudaoResponse;
import com.liyu.huahui.ui.widgets.MorphDialogToFab;
import com.liyu.huahui.ui.widgets.MorphFabToDialog;
import com.liyu.huahui.ui.widgets.MorphTransition;
import com.liyu.huahui.ui.widgets.ProgressButton;
import com.liyu.huahui.utils.YoudaoUtil;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liyu on 2017/7/12.
 */
public class QueryActivity extends AppCompatActivity {

    private LinearLayout container;
    private ProgressButton progressButton;
    private EditText editText;

    public static final String EXTRA_WORD = "extra_word";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        container = (LinearLayout) findViewById(R.id.ll_content);

        editText = (EditText) findViewById(R.id.edit_query);

        progressButton = (ProgressButton) findViewById(R.id.btn_query);

        progressButton.setBgColor(ContextCompat.getColor(App.getContext(), R.color.colorPrimary));
        progressButton.setTextColor(Color.WHITE);
        progressButton.setProColor(Color.WHITE);
        progressButton.setButtonText("查询");

        //方式一
        setupSharedEelementTransitions1();
        //方式二
        //setupSharedEelementTransitions2();

        progressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query();
            }
        });
    }

    private void query() {
        String word = editText.getEditableText().toString().trim();
        if (TextUtils.isEmpty(word)) {
            editText.setError("单词为什么是空的！");
            return;
        }
        progressButton.startAnim();
        editText.setEnabled(false);
        ApiFactory
                .getYoudaoApi()
                .get(YoudaoUtil.getQueryUrl(word))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<YoudaoResponse>() {
                    @Override
                    public void onCompleted() {
                        editText.setEnabled(true);
                        progressButton.stopAnim(new ProgressButton.OnStopAnim() {
                            @Override
                            public void Stop() {
                                finishAfterTransition();
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        editText.setEnabled(true);
                    }

                    @Override
                    public void onNext(YoudaoResponse youdaoResponse) {
                        Word word = new Word();
                        word.setName(youdaoResponse.getQuery());
                        word.setCorrect(String.format("[%s]", youdaoResponse.getBasic().getPhonetic()));
                        word.setVoice(String.format("http://dict.youdao.com/dictvoice?audio=%s&type=1", youdaoResponse.getQuery()));
                        Intent i = new Intent();
                        i.putExtra(EXTRA_WORD, word);
                        setResult(RESULT_OK, i);
                    }
                });
    }

    /**
     * 使用方式一：调用setupSharedEelementTransitions1方法
     * 使用这种方式的话需要的类是 MorphDrawable, MorphFabToDialog, MorphDialogToFab
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setupSharedEelementTransitions1() {
        ArcMotion arcMotion = new ArcMotion();
        arcMotion.setMinimumHorizontalAngle(50f);
        arcMotion.setMinimumVerticalAngle(50f);

        Interpolator easeInOut = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in);

        MorphFabToDialog sharedEnter = new MorphFabToDialog();
        sharedEnter.setPathMotion(arcMotion);
        sharedEnter.setInterpolator(easeInOut);

        MorphDialogToFab sharedReturn = new MorphDialogToFab();
        sharedReturn.setPathMotion(arcMotion);
        sharedReturn.setInterpolator(easeInOut);

        if (container != null) {
            sharedEnter.addTarget(container);
            sharedReturn.addTarget(container);
        }
        getWindow().setSharedElementEnterTransition(sharedEnter);
        getWindow().setSharedElementReturnTransition(sharedReturn);
    }

    /**
     * 使用方式二：调用setupSharedEelementTransitions2方法
     * 使用这种方式的话需要的类是 MorphDrawable, MorphTransition
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setupSharedEelementTransitions2() {
        ArcMotion arcMotion = new ArcMotion();
        arcMotion.setMinimumHorizontalAngle(50f);
        arcMotion.setMinimumVerticalAngle(50f);

        Interpolator easeInOut = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in);

        //hujiawei 100是随意给的一个数字，可以修改，需要注意的是这里调用container.getHeight()结果为0
        MorphTransition sharedEnter = new MorphTransition(ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.white), 100, getResources().getDimensionPixelSize(R.dimen.dialog_corners), true);
        sharedEnter.setPathMotion(arcMotion);
        sharedEnter.setInterpolator(easeInOut);

        MorphTransition sharedReturn = new MorphTransition(ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.white), getResources().getDimensionPixelSize(R.dimen.dialog_corners), 100, false);
        sharedReturn.setPathMotion(arcMotion);
        sharedReturn.setInterpolator(easeInOut);

        if (container != null) {
            sharedEnter.addTarget(container);
            sharedReturn.addTarget(container);
        }
        getWindow().setSharedElementEnterTransition(sharedEnter);
        getWindow().setSharedElementReturnTransition(sharedReturn);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        dismiss(null);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void dismiss(View view) {
        setResult(Activity.RESULT_CANCELED);
        finishAfterTransition();
    }

}

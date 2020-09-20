package com.liyu.huahui.ui

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.transition.ArcMotion
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.liyu.huahui.R
import com.liyu.huahui.model.Word
import com.liyu.huahui.ui.widgets.MorphDialogToFab
import com.liyu.huahui.ui.widgets.MorphFabToDialog
import com.liyu.huahui.ui.widgets.MorphTransition
import com.liyu.huahui.ui.widgets.ProgressButton
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import rx.Observable
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.IOException
import java.util.regex.Pattern

/**
 * Created by liyu on 2017/7/12.
 */
class QueryActivity : AppCompatActivity() {
    private var container: LinearLayout? = null
    private var progressButton: ProgressButton? = null
    private var editText: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query)
        container = findViewById<View>(R.id.ll_content) as LinearLayout
        editText = findViewById<View>(R.id.edit_query) as EditText
        progressButton = findViewById<View>(R.id.btn_query) as ProgressButton
        progressButton!!.setBgColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
        progressButton!!.setTextColor(Color.WHITE)
        progressButton!!.setProColor(Color.WHITE)
        progressButton!!.setButtonText("查询")

        //方式一
        setupSharedEelementTransitions1()
        //方式二
        //setupSharedEelementTransitions2();
        progressButton!!.setOnClickListener { query() }
    }

    private fun query() {
        val wordTo = editText!!.editableText.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(wordTo)) {
            editText!!.error = "为什么是空的！"
            return
        }
        val patternWords = "[a-zA-Z1-9 ]{1,}"
        val r = Pattern.compile(patternWords)
        val m = r.matcher(wordTo)
        if (!m.matches()) {
            editText!!.error = "这货不是单词啊！"
            return
        }
        progressButton!!.startAnim()
        editText!!.isEnabled = false
        Observable.just("http://dict.youdao.com/w/eng/$wordTo")
                .map { url ->
                    val word = Word()
                    val doc: Document
                    doc = try {
                        Jsoup.connect(url).timeout(20000).get()
                    } catch (e: IOException) {
                        throw RuntimeException(e)
                    }
                    val phonetics = doc.getElementsByClass("phonetic")
                    word.name = wordTo
                    //word.setVoiceUrl(String.format("http://dict.youdao.com/dictvoice?audio=%s&type=1", wordTo))
                    if (phonetics.size > 1) word.correctPhonetic = phonetics[0].ownText()+",美音: "+phonetics[1].ownText()
                    else if (phonetics.size > 0) word.correctPhonetic = phonetics[0].ownText()
                    word.setSourceFrom(Word.From.LOCAL)
                    word
                }.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(object : Observer<Word> {
                    override fun onCompleted() {
                        editText!!.isEnabled = true
                        progressButton!!.stopAnim { finishAfterTransition() }
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(this@QueryActivity, e.message, Toast.LENGTH_SHORT).show()
                        finishAfterTransition()
                    }

                    override fun onNext(word: Word) {
                        if (word.correctPhonetic == null) {
                            Toast.makeText(this@QueryActivity, "没有查到这个单词！", Toast.LENGTH_SHORT).show()
                            return
                        }
                        val i = Intent()
                        i.putExtra(EXTRA_WORD, word)
                        setResult(RESULT_OK, i)
                    }
                })
        /*ApiFactory
                .getYoudaoApi()
                .get(YoudaoUtil.getQueryUrl(wordTo))
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
                        Toast.makeText(QueryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        finishAfterTransition();

                    }

                    @Override
                    public void onNext(YoudaoResponse youdaoResponse) {
                        if (youdaoResponse.getBasic() == null || TextUtils.isEmpty(youdaoResponse.getQuery())) {
                            Toast.makeText(QueryActivity.this, "没有查到这个单词！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Word word = new Word();
                        word.setName(youdaoResponse.getQuery());
                        word.setCorrectPhonetic(String.format("[%s]", youdaoResponse.getBasic().getPhonetic()));
                        word.setVoiceUrl(String.format("http://dict.youdao.com/dictvoice?audio=%s&type=1", youdaoResponse.getQuery()));
                        word.setSourceFrom(Word.From.LOCAL);
                        Intent i = new Intent();
                        i.putExtra(EXTRA_WORD, word);
                        setResult(RESULT_OK, i);
                    }
                });*/
    }

    /**
     * 使用方式一：调用setupSharedEelementTransitions1方法
     * 使用这种方式的话需要的类是 MorphDrawable, MorphFabToDialog, MorphDialogToFab
     */
    private fun setupSharedEelementTransitions1() {
        val arcMotion = ArcMotion()
        arcMotion.minimumHorizontalAngle = 50f
        arcMotion.minimumVerticalAngle = 50f
        val easeInOut = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in)
        val sharedEnter = MorphFabToDialog()
        sharedEnter.pathMotion = arcMotion
        sharedEnter.interpolator = easeInOut
        val sharedReturn = MorphDialogToFab()
        sharedReturn.pathMotion = arcMotion
        sharedReturn.interpolator = easeInOut
        if (container != null) {
            sharedEnter.addTarget(container)
            sharedReturn.addTarget(container)
        }
        window.sharedElementEnterTransition = sharedEnter
        window.sharedElementReturnTransition = sharedReturn
    }

    /**
     * 使用方式二：调用setupSharedEelementTransitions2方法
     * 使用这种方式的话需要的类是 MorphDrawable, MorphTransition
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun setupSharedEelementTransitions2() {
        val arcMotion = ArcMotion()
        arcMotion.minimumHorizontalAngle = 50f
        arcMotion.minimumVerticalAngle = 50f
        val easeInOut = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in)

        //hujiawei 100是随意给的一个数字，可以修改，需要注意的是这里调用container.getHeight()结果为0
        val sharedEnter = MorphTransition(ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.white), 100, resources.getDimensionPixelSize(R.dimen.dialog_corners), true)
        sharedEnter.pathMotion = arcMotion
        sharedEnter.interpolator = easeInOut
        val sharedReturn = MorphTransition(ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.white), resources.getDimensionPixelSize(R.dimen.dialog_corners), 100, false)
        sharedReturn.pathMotion = arcMotion
        sharedReturn.interpolator = easeInOut
        if (container != null) {
            sharedEnter.addTarget(container)
            sharedReturn.addTarget(container)
        }
        window.sharedElementEnterTransition = sharedEnter
        window.sharedElementReturnTransition = sharedReturn
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onBackPressed() {
        dismiss(null)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun dismiss(view: View?) {
        setResult(RESULT_CANCELED)
        finishAfterTransition()
    }

    companion object {
        const val EXTRA_WORD = "extra_word"
    }
}
package com.liyu.huahui.ui

import android.app.ActivityOptions
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.liyu.huahui.App
import com.liyu.huahui.R
import com.liyu.huahui.adapter.WordAdapter
import com.liyu.huahui.model.Word
import com.liyu.huahui.utils.DownloadUtil.MultiFileDownloadListener
import com.liyu.huahui.utils.DownloadUtil.start
import com.liyu.huahui.utils.DownloadUtil.stop
import com.liyu.huahui.utils.NetworkUtil.isConnected
import com.liyu.huahui.utils.Player.Companion.instance
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.litepal.crud.DataSupport
import rx.Observable
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.IOException
import java.text.Collator
import java.util.*
/**
 * Updated by ultranity on 2020/9/20.
 * Created by liyu.
 */
class MainActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var adapter: WordAdapter? = null
    private var dialog: ProgressDialog? = null
    private var tvTotal: TextView? = null
    private var fabAdd: FloatingActionButton? = null
    private var app: App? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        fabAdd = findViewById<View>(R.id.fab_add) as FloatingActionButton
        fabAdd!!.setOnClickListener {
            val intent = Intent(this@MainActivity, QueryActivity::class.java)
            val options = ActivityOptions.makeSceneTransitionAnimation(this@MainActivity, fabAdd, getString(R.string.transition_dialog))
            startActivityForResult(intent, REQUEST_QUERY_WORD, options.toBundle())
        }
        tvTotal = findViewById<View>(R.id.tv_total) as TextView
        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        adapter = WordAdapter(R.layout.item_word, null)
        //adapter.openLoadAnimation();
        recyclerView!!.adapter = adapter
        adapter!!.setDataChangedListener(object : WordAdapter.OnDataChangedListener {
            override fun onChanged(dateSize: Int) {
                tvTotal!!.text = String.format("总计：%s个", dateSize)
            }})
        app = applicationContext as App
        words
    }

    /**
     * 优先从本地数据库获取数据，如果为空则从网络拉取
     */
    private val words: Unit
        get() {
            val database: Observable<List<Word>> = Observable.create { subscriber ->
                val words = DataSupport
                        .where("sourceFrom =?", Word.From.NETWORK.from.toString())
                        .find(Word::class.java)
                if (words == null || words.isEmpty()) {
                    subscriber.onCompleted()
                } else {
                    subscriber.onNext(words)
                }
            }
            val network = Observable
                    .just("https://github.com/shimohq/chinese-programmer-wrong-pronunciation")
                    .map<List<Word>> { url ->
                        val words: MutableList<Word> = ArrayList()
                        val doc: Document
                        doc = try {
                            Jsoup.connect(url).timeout(20000).get()
                        } catch (e: IOException) {
                            throw RuntimeException(e)
                        }
                        val trs = doc.select("tr")
                        for (element in trs) {
                            val tds = element.select("td")
                            if (tds != null && tds.size == 3) {
                                val word = Word()
                                word.name = tds[0].ownText()
                                //val a = tds[0].select("a").first()
                                //word.setVoiceUrl(if (a == null) "" else a.attr("href"))
                                word.correctPhonetic = tds[1].ownText()
                                word.wrongPhonetic = tds[2].ownText()
                                word.setSourceFrom(Word.From.NETWORK)
                                words.add(word)
                            } else if (tds != null && tds.size == 4) {
                                val word = Word()
                                word.name = tds[0].ownText()
                                //val a = tds[1].select("a").first()
                                //word.setVoiceUrl(if (a == null) "" else a.attr("href"))
                                word.correctPhonetic = tds[1].ownText()+",美音: "+tds[2].ownText()
                                word.wrongPhonetic = tds[3].ownText()
                                word.setSourceFrom(Word.From.NETWORK)
                                words.add(word)
                            }
                        }
                        DataSupport.saveAll(words)
                        words
                    }
            showDialog("正在从 Github 上获取数据...")
            Observable
                    .concat(database, network)
                    .first()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<List<Word?>?> {
                        override fun onCompleted() {
                            closeDialog()
                        }

                        override fun onError(e: Throwable) {
                            closeDialog()
                            Toast.makeText(this@MainActivity, e.toString(), Toast.LENGTH_SHORT).show()
                        }

                        override fun onNext(words: List<Word?>?) {
                            val allWords = DataSupport.findAll(Word::class.java)
                            tvTotal!!.text = String.format("总计：%s个", allWords.size)
                            adapter!!.setNewData(allWords)
                            sortData()
                        }
                    })
        }

    private fun sortData() {
        val collator = Collator.getInstance()
        adapter!!.data.sortWith { o1, o2 -> collator.compare(o1.name, o2.name) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        if (App.accent == Word.Accent.US) {
            menu.findItem(R.id.action_EN).setTitle(R.string.action_US)
        } else {
            menu.findItem(R.id.action_EN).setTitle(R.string.action_GB)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_home_page) {
            val uri = Uri.parse("https://github.com/li-yu/Huahui-Android")
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data = uri
            startActivity(intent)
            return true
        } else if (id == R.id.action_sync) {
            DataSupport.deleteAll(Word::class.java, "sourceFrom =?", Word.From.NETWORK.from.toString())
            words
            return true
        } else if (id == R.id.action_EN) {
            if (item.title == getString(R.string.action_GB)) {
                item.setTitle(R.string.action_US)
                App.accent = Word.Accent.US
            } else {
                item.setTitle(R.string.action_GB)
                App.accent = Word.Accent.GB
            }
            return true
        } else if (id == R.id.action_cache) {
            if (!isConnected(this)) {
                Toast.makeText(this@MainActivity, "网络连接不可用！", Toast.LENGTH_SHORT).show()
                return true
            }
            val words = DataSupport.findAll(Word::class.java)
            start(this, words, object : MultiFileDownloadListener {
                override fun onProcess(msg: String?) {
                    showDialog(msg)
                }

                override fun onCompleted() {
                    closeDialog()
                    Toast.makeText(this@MainActivity, "缓存成功！", Toast.LENGTH_SHORT).show()
                }

                override fun onFail(msg: String?) {
                    closeDialog()
                    Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
                }
            })
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun closeDialog() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }

    private fun showDialog(msg: String?) {
        if (dialog == null) {
            dialog = ProgressDialog(this)
            dialog!!.setCancelable(false)
            dialog!!.setMessage(msg)
            dialog!!.show()
        } else if (dialog!!.isShowing) {
            dialog!!.setMessage(msg)
        } else {
            dialog!!.setMessage(msg)
            dialog!!.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        instance!!.destroy()
        stop()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_QUERY_WORD && resultCode == RESULT_OK && data != null) {
            val word = data.getSerializableExtra(QueryActivity.EXTRA_WORD) as Word
            if (adapter!!.data.contains(word)) {
                Toast.makeText(this, "列表中已包含 " + word.name, Toast.LENGTH_SHORT).show()
            } else if (word.correctPhonetic == null || word.correctPhonetic!!.contains("null")) {
                Toast.makeText(this, "该单词没有找到合适的读法", Toast.LENGTH_SHORT).show()
            } else {
                word.save()
                adapter!!.addData(word)
                sortData()
                adapter!!.notifyDataSetChanged()
                val wordIndex = adapter!!.data.indexOf(word)
                recyclerView!!.smoothScrollToPosition(wordIndex)
            }
        }
    }

    companion object {
        private const val REQUEST_QUERY_WORD = 110
    }
}
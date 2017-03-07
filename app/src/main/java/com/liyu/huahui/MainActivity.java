package com.liyu.huahui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WordAdapter adapter;
    private ProgressDialog dialog;
    private TextView tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvTotal = (TextView) findViewById(R.id.tv_total);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WordAdapter(R.layout.item_word, null);
        adapter.openLoadAnimation();
        recyclerView.setAdapter(adapter);
        getWords();
    }


    /**
     * 优先从本地数据库获取数据，如果为空则从网络拉取
     */
    private void getWords() {

        Observable<List<Word>> database = Observable.create(new Observable.OnSubscribe<List<Word>>() {
            @Override
            public void call(Subscriber<? super List<Word>> subscriber) {
                List<Word> words = DataSupport.findAll(Word.class);
                if (words == null || words.isEmpty()) {
                    subscriber.onCompleted();
                } else {
                    subscriber.onNext(words);
                }
            }
        });

        Observable<List<Word>> network = Observable
                .just("https://github.com/shimohq/chinese-programmer-wrong-pronunciation")
                .map(new Func1<String, List<Word>>() {
                    @Override
                    public List<Word> call(String url) {
                        List<Word> words = new ArrayList<>();
                        Document doc;
                        try {
                            doc = Jsoup.connect(url).timeout(20000).get();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Elements trs = doc.select("tr");
                        for (Element element : trs) {
                            Elements tds = element.select("td");
                            if (tds != null && tds.size() == 3) {
                                Word word = new Word();
                                word.setName(tds.get(0).ownText());
                                Element a = tds.get(0).select("a").first();
                                word.setVoice(a == null ? "" : a.attr("href"));
                                word.setCorrect(tds.get(1).ownText());
                                word.setWrong(tds.get(2).ownText());
                                words.add(word);
                            }
                        }
                        DataSupport.deleteAll(Word.class);
                        DataSupport.saveAll(words);
                        return words;
                    }
                });

        showDialog("正在从 Github 上获取数据...");
        Observable
                .concat(database, network)
                .first()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Word>>() {
                    @Override
                    public void onCompleted() {
                        closeDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        closeDialog();
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<Word> words) {
                        tvTotal.setText(String.format("总计：%s个", words.size()));
                        adapter.setNewData(words);
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home_page) {
            Uri uri = Uri.parse("https://github.com/li-yu/Huahui-Android");
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(uri);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_sync) {
            DataSupport.deleteAll(Word.class);
            getWords();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void closeDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void showDialog(String msg) {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setCancelable(false);
            dialog.setMessage(msg);
            dialog.show();
        } else if (dialog.isShowing()) {
            dialog.setMessage(msg);
        } else {
            dialog.setMessage(msg);
            dialog.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Player.getInstance().destroy();
    }
}

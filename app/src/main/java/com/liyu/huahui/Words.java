package com.liyu.huahui;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyu on 2017/3/2.
 */

public class Words {

    private static List<Word> words;

    public static List<Word> get() {
        if (words != null && !words.isEmpty())
            return words;
        List<Word> wordList = new ArrayList<>();

        wordList.add(new Word("access", "['ækses]", "[ək'ses]", true));
        wordList.add(new Word("Angular", "['æŋgjʊlə]", "['æŋɡələ; 'æŋdʒʌlə]", true));
        wordList.add(new Word("AJAX", "['eidʒæks]", "[ə'dʒʌks]", true));
        wordList.add(new Word("Apache", "[ə'pætʃɪ]", "[ʌpʌtʃ]", true));
        wordList.add(new Word("app", "[æp]", "A P P", true));
        wordList.add(new Word("archive", "['ɑːkaɪv]", "['ətʃɪv]", true));
        wordList.add(new Word("array", "[ə'rei]", "[æ'rei]", true));
        wordList.add(new Word("avatar", "['ævətɑː]", "[ə'vʌtɑ]", true));
        wordList.add(new Word("Azure", "['æʒə]", "[ˈæzʊʒə]", true));
        wordList.add(new Word("cache", "[kæʃ]", "[kætʃ]", true));
        wordList.add(new Word("deque", "['dek]", "[di'kju]", true));
        wordList.add(new Word("digest", "['dɑɪdʒɛst]", "['dɪgɛst]", true));
        wordList.add(new Word("Django", "[ˈdʒæŋɡoʊ]", "[diˈdʒæŋɡoʊ]", true));
        wordList.add(new Word("doc", "[dɒk]", "[daʊk]", true));
        wordList.add(new Word("facade", "[fə'sɑːd]", "['feikeid]", true));
        wordList.add(new Word("Git", "[ɡɪt]", "[dʒɪt; jɪt]", true));
        wordList.add(new Word("GNU", "[gnu:]", "", true));
        wordList.add(new Word("GUI", "[ˈɡui]", "", true));
        wordList.add(new Word("height", "[haɪt]", "[heɪt]", true));
        wordList.add(new Word("hidden", "['hɪdn]", "['haɪdn]", true));
        wordList.add(new Word("image", "['ɪmɪdʒ]", "[ɪ'meɪdʒ]", true));
        wordList.add(new Word("integer", "['ɪntɪdʒə]", "[ˈɪntaɪgə]", true));
        wordList.add(new Word("issue", "['ɪʃuː]", "[ˈaɪʃuː]", true));
        wordList.add(new Word("Java", "['dʒɑːvə]", "['dʒɑːvɑː]", true));
        wordList.add(new Word("jpg(jpeg)", "['dʒeɪpeɡ]", "[ˈdʒeɪˈpi:ˈdʒiː]", true));
        wordList.add(new Word("Linux", "['lɪnəks]", "[ˈlɪnʌks; ˈlɪnjuːks]", true));
        wordList.add(new Word("main", "[meɪn]", "[mɪn]", true));
        wordList.add(new Word("margin", "['mɑːdʒɪn]", "['mʌgɪn]", true));
        wordList.add(new Word("maven", "['meɪvn]", "['maːvn]", true));
        wordList.add(new Word("module", "['mɒdjuːl]", "['məʊdl]", true));
        wordList.add(new Word("nginx", "Engine X", "", false));
        wordList.add(new Word("null", "[nʌl]", "[naʊ]", true));
        wordList.add(new Word("OS X", "OS ten", "", false));
        wordList.add(new Word("parameter", "[pə'ræmɪtə]", "['pærəmɪtə]", true));
        wordList.add(new Word("putty", "[ˈpʌti]", "[ˈpuːti]", true));
        wordList.add(new Word("query", "['kwɪəri]", "['kwaɪri]", true));
        wordList.add(new Word("Qt", "[kjuːt]", "", true));
        wordList.add(new Word("resolved", "[rɪ'zɒlvd]", "[rɪ'səʊvd]", true));
        wordList.add(new Word("retina", "['retɪnə]", "[ri'tina]", true));
        wordList.add(new Word("san jose", "[sænhəu'zei]", "[sæn'ju:s]", true));
        wordList.add(new Word("safari", "[sə'fɑːrɪ]", "[sæfərɪ]", true));
        wordList.add(new Word("scheme", "[skiːm]", "[s'kæmə]", true));
        wordList.add(new Word("sudo", "['suːduː]", "", false));
        wordList.add(new Word("suite", "[swiːt]", "[sjuːt]", true));
        wordList.add(new Word("typical", "['tɪpɪkl]", "['taɪpɪkəl]", true));
        wordList.add(new Word("Ubuntu", "[ʊ'bʊntʊ]", "[juː'bʊntʊ]", true));
        wordList.add(new Word("variable", "['veəriəbl]", "[və'raiəbl]", true));
        wordList.add(new Word("vue", "[v'ju:]", "[v'ju:i]", true));
        wordList.add(new Word("width", "[wɪdθ]", "[waɪdθ]", true));
        wordList.add(new Word("YouTube", "['juː'tjuːb]", "['juː'tʊbɪ]", true));
        words = wordList;
        return words;
    }
}

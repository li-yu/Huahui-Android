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

        words = new ArrayList<>();
        words.add(new Word("access", "['ækses]", "[ək'ses]", true));
        words.add(new Word("Angular", "['æŋgjʊlə]", "['æŋɡələ; 'æŋdʒʌlə]", true));
        words.add(new Word("AJAX", "['eidʒæks]", "[ə'dʒʌks]", true));
        words.add(new Word("Apache", "[ə'pætʃɪ]", "[ʌpʌtʃ]", true));
        words.add(new Word("app", "[æp]", "A P P", true));
        words.add(new Word("archive", "['ɑːkaɪv]", "['ətʃɪv]", true));
        words.add(new Word("array", "[ə'rei]", "[æ'rei]", true));
        words.add(new Word("avatar", "['ævətɑː]", "[ə'vʌtɑ]", true));
        words.add(new Word("Azure", "['æʒə]", "[ˈæzʊʒə]", true));
        words.add(new Word("cache", "[kæʃ]", "[kætʃ]", true));
        words.add(new Word("deque", "['dek]", "[di'kju]", true));
        words.add(new Word("digest", "['dɑɪdʒɛst]", "['dɪgɛst]", true));
        words.add(new Word("Django", "[ˈdʒæŋɡoʊ]", "[diˈdʒæŋɡoʊ]", true));
        words.add(new Word("doc", "[dɒk]", "[daʊk]", true));
        words.add(new Word("facade", "[fə'sɑːd]", "['feikeid]", true));
        words.add(new Word("Git", "[ɡɪt]", "[dʒɪt; jɪt]", true));
        words.add(new Word("GNU", "[gnu:]", "", true));
        words.add(new Word("GUI", "[ˈɡui]", "", true));
        words.add(new Word("height", "[haɪt]", "[heɪt]", true));
        words.add(new Word("hidden", "['hɪdn]", "['haɪdn]", true));
        words.add(new Word("image", "['ɪmɪdʒ]", "[ɪ'meɪdʒ]", true));
        words.add(new Word("integer", "['ɪntɪdʒə]", "[ˈɪntaɪgə]", true));
        words.add(new Word("issue", "['ɪʃuː]", "[ˈaɪʃuː]", true));
        words.add(new Word("Java", "['dʒɑːvə]", "['dʒɑːvɑː]", true));
        words.add(new Word("jpg(jpeg)", "['dʒeɪpeɡ]", "[ˈdʒeɪˈpi:ˈdʒiː]", true));
        words.add(new Word("Linux", "['lɪnəks]", "[ˈlɪnʌks; ˈlɪnjuːks]", true));
        words.add(new Word("main", "[meɪn]", "[mɪn]", true));
        words.add(new Word("margin", "['mɑːdʒɪn]", "['mʌgɪn]", true));
        words.add(new Word("maven", "['meɪvn]", "['maːvn]", true));
        words.add(new Word("module", "['mɒdjuːl]", "['məʊdl]", true));
        words.add(new Word("nginx", "Engine X", "", false));
        words.add(new Word("null", "[nʌl]", "[naʊ]", true));
        words.add(new Word("OS X", "OS ten", "", false));
        words.add(new Word("parameter", "[pə'ræmɪtə]", "['pærəmɪtə]", true));
        words.add(new Word("putty", "[ˈpʌti]", "[ˈpuːti]", true));
        words.add(new Word("query", "['kwɪəri]", "['kwaɪri]", true));
        words.add(new Word("Qt", "[kjuːt]", "", true));
        words.add(new Word("resolved", "[rɪ'zɒlvd]", "[rɪ'səʊvd]", true));
        words.add(new Word("retina", "['retɪnə]", "[ri'tina]", true));
        words.add(new Word("san jose", "[sænhəu'zei]", "[sæn'ju:s]", true));
        words.add(new Word("safari", "[sə'fɑːrɪ]", "[sæfərɪ]", true));
        words.add(new Word("scheme", "[skiːm]", "[s'kæmə]", true));
        words.add(new Word("sudo", "['suːduː]", "", false));
        words.add(new Word("suite", "[swiːt]", "[sjuːt]", true));
        words.add(new Word("typical", "['tɪpɪkl]", "['taɪpɪkəl]", true));
        words.add(new Word("Ubuntu", "[ʊ'bʊntʊ]", "[juː'bʊntʊ]", true));
        words.add(new Word("variable", "['veəriəbl]", "[və'raiəbl]", true));
        words.add(new Word("vue", "[v'ju:]", "[v'ju:i]", true));
        words.add(new Word("width", "[wɪdθ]", "[waɪdθ]", true));
        words.add(new Word("YouTube", "['juː'tjuːb]", "['juː'tʊbɪ]", true));
        return words;
    }
}

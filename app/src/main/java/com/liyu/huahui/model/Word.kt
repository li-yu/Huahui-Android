package com.liyu.huahui.model

import org.litepal.crud.DataSupport
import java.io.Serializable

/**
 * Updated by ultranity on 2020/9/20.
 * Created by liyu on 2017/3/2.
 */
class Word : DataSupport(), Serializable {
    var name: String? = null
    var correctPhonetic: String? = null
    var wrongPhonetic: String? = null

    //+"&type="+accent;
    val voiceUrl: String?
        get() = "http://dict.youdao.com/dictvoice?audio=$name" //+"&type="+accent;
    private var sourceFrom = 0
        private set
    private var accent = 0
        private set

    fun setSourceFrom(sourceFrom: From) {
        this.sourceFrom = sourceFrom.from
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val word = o as Word
        return if (name != null) name!!.toLowerCase() == word.name!!.toLowerCase() else word.name == null
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + (correctPhonetic?.hashCode() ?: 0)
        result = 31 * result + (wrongPhonetic?.hashCode() ?: 0)
        result = 31 * result + sourceFrom
        result = 31 * result + accent
        return result
    }

    enum class From(var from: Int) {
        LOCAL(1), NETWORK(2);

    }

    enum class Accent(var id: Int) {
        GB(1), US(2);
    }
}
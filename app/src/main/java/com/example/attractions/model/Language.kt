package com.example.attractions.model

import androidx.annotation.StringRes
import com.example.attractions.R
import java.util.Locale

enum class Language(
    val code: String,
    @StringRes val displayNameRes: Int,
    val locale: Locale
) {
    CHINESE_TRADITIONAL("zh-tw", R.string.language_zh_tw, Locale("zh", "TW")),
    CHINESE_SIMPLIFIED("zh-cn", R.string.language_zh_cn, Locale("zh", "CN")),
    ENGLISH("en", R.string.language_en, Locale("en")),
    JAPANESE("ja", R.string.language_ja, Locale("ja")),
    KOREAN("ko", R.string.language_ko, Locale("ko")),
    SPANISH("es", R.string.language_es, Locale("es")),
    INDONESIAN("id", R.string.language_id, Locale("in")),
    THAI("th", R.string.language_th, Locale("th")),
    VIETNAMESE("vi", R.string.language_vi, Locale("vi"));

    companion object {
        fun fromCode(code: String): Language? = values().find { it.code == code }
        
        val default = CHINESE_TRADITIONAL
    }
} 
package com.example.attractions.model

import androidx.annotation.StringRes
import com.example.attractions.R

enum class Language(
    val code: String,
    @StringRes val displayNameRes: Int
) {
    CHINESE_TRADITIONAL("zh-tw", R.string.language_zh_tw),
    CHINESE_SIMPLIFIED("zh-cn", R.string.language_zh_cn),
    ENGLISH("en", R.string.language_en),
    JAPANESE("ja", R.string.language_ja),
    KOREAN("ko", R.string.language_ko),
    SPANISH("es", R.string.language_es),
    INDONESIAN("id", R.string.language_id),
    THAI("th", R.string.language_th),
    VIETNAMESE("vi", R.string.language_vi);

    companion object {
        fun fromCode(code: String): Language? = values().find { it.code == code }
    }
} 
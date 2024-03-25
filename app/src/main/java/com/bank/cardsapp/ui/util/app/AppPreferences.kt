package com.bank.cardsapp.ui.util.app

import com.bank.cardsapp.ui.util.theme.AppTheme
import io.paperdb.Paper
import java.util.*

object AppPreferences {
    const val APP_LANG = "AppLang"

    fun getLocale(): String {
        return when(Paper.book().read(APP_LANG, Constants.DEFAULT_SYSTEM_LOCALE_LANG)!!) {
            Constants.DEFAULT_SYSTEM_LOCALE_LANG -> {
                Locale.getDefault().language
            }
            Constants.ARABIC_LOCALE_LANG -> {
                Constants.ARABIC_LOCALE_LANG
            }
            Constants.ENGLISH_LOCALE_LANG -> {
                Constants.ENGLISH_LOCALE_LANG
            }
            else -> {
                Constants.ENGLISH_LOCALE_LANG
            }
        }
    }

    const val APP_THEME = "AppTheme"

    fun setTheme(theme: AppTheme) {
        Paper.book().write(APP_THEME, theme)
    }

    fun getTheme(): AppTheme {
        return Paper.book().read(APP_THEME, AppTheme.Default)!!
    }
}
package com.ui.account.language

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.common.io.Resources
import com.ui.R
import java.util.*


class MainActivityLanaguage : AppCompatActivity() {
    lateinit var btnArabic: Button
    lateinit var btnEnglish:Button
    var context: Context? = null
    lateinit var resources: Resources
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_main_lanaguage)
        btnArabic = findViewById(R.id.tvArabic)
        btnEnglish = findViewById(R.id.tvEnglish)
        btnEnglish.setOnClickListener(View.OnClickListener {
            val languageToLoad = "en" // your language
            val locale = Locale(languageToLoad)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            baseContext.resources.updateConfiguration(config,baseContext.resources.displayMetrics)
            finish()
            startActivity(intent)
        })

        btnArabic.setOnClickListener(View.OnClickListener {
            val languageToLoad = "ar" // your language
            val locale = Locale(languageToLoad)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            baseContext.resources.updateConfiguration(config,baseContext.resources.displayMetrics)
            finish()
            startActivity(intent)
        })

    }

    fun setLocal(lang: String?) {
        val languageToLoad = "ar" // your language
        val locale = Locale(languageToLoad)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config,baseContext.resources.displayMetrics)
    }
}

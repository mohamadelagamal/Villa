package com.prongbang.localizedapp

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import com.prongbang.localization.ENGLISH
import com.prongbang.localization.LocalizationAppCompatActivity
import com.prongbang.localization.Localize
import com.prongbang.localization.THAI
import com.ui.R
import com.ui.databinding.ActivityMainBinding
import com.ui.databinding.ActivityMainLanaguageBinding

class SettingActivity : LocalizationAppCompatActivity() {

	//lateinit var binding: ActivityMainLanaguageBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main_lanaguage)
		//binding.apply {
		val arabicButton = findViewById<Button>(R.id.tvArabic)
        val englishButton = findViewById<Button>(R.id.tvEnglish)
        arabicButton.setOnClickListener {
			setLocale(Localize.ENGLISH)}
        englishButton.setOnClickListener {
			setLocale(Localize.THAI)}

		//}
	}

	override fun onConfigurationChanged(newConfig: Configuration) {
		openPrepareLocalize() // used only in setting activity
		super.onConfigurationChanged(newConfig)
	}

	companion object {
		fun navigate(context: Context) {
			context.startActivity(Intent(context, SettingActivity::class.java))
		}
	}
}
//        val arabicButton = findViewById<TextView>(R.id.tvArabic)
//        val englishButton = findViewById<TextView>(R.id.tvEnglish)
//        arabicButton.setOnClickListener {
//            updateLocale(Locales.Arabic)
//        }
//        englishButton.setOnClickListener {
//            updateLocale(Locales.English)
//        }
//val currentLanguage = ExampleApp.localeManager.language
//val arabicButton = findViewById<Button>(R.id.tvArabic)
//val englishButton = findViewById<Button>(R.id.tvEnglish)
//arabicButton.setOnClickListener {
//	ExampleApp.localeManager.setNewLocale(this, LanguagesSupport.Language.ARABIC)
//	recreate()
//}
//englishButton.setOnClickListener {
//	ExampleApp.localeManager.setNewLocale(this, LanguagesSupport.Language.ENGLISH)
//	recreate()
//}
//
//}
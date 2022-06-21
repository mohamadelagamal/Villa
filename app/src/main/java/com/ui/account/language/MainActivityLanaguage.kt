//package com.ui.account.language
//
//import android.content.Context
//import android.content.res.Configuration
//import android.os.Bundle
//import android.view.View
//import android.widget.Button
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.chat.database.addLanguage
//import com.database.DataUtil
//import com.database.LanguageData
//import com.database.room.entity.Category
//import com.database.room.table.MyDatabase
//import com.google.common.io.Resources
//import com.ui.R
//import java.util.*
//
//
//class MainActivityLanaguage : AppCompatActivity() {
//    lateinit var btnArabic: Button
//    lateinit var btnEnglish:Button
//    var context: Context? = null
//    lateinit var resources: Resources
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//       // setContentView(R.layout.activity_main_lanaguage)
//        btnArabic = findViewById(R.id.tvArabic)
//        btnEnglish = findViewById(R.id.tvEnglish)
//        btnEnglish.setOnClickListener(View.OnClickListener {
//            //setLocal("en")
//            //val language = LanguageData(languageEnglish =true,id = DataUtil.user?.id)
//           // addLanguage(language, onSuccessListener = {}, onFailureListener = {})
//         //   englishInsertionDatabase(arabic = false, english = true)
//        //    finish()
//        //    startActivity(intent)
//        })
//
//        btnArabic.setOnClickListener(View.OnClickListener {
//          //  setLocal("ar")
//            //val language = LanguageData(languageArabic=true,id = DataUtil.user?.id)
//         //   addLanguage(language, onSuccessListener = {}, onFailureListener = {})
//          //  arabicInsertionDatabase(arabic = true, english = false)
//        //   finish()
//            //startActivity(intent)
//        })
//
//    }
//
//    fun setLocal(langCode: String?) {
////        Locale locale = new Locale(langCode);
////        locale.setDefault(locale);
////        Resources resources = activity.getResources();
////        Configuration configuration = resources.getConfiguration();
////        configuration.setLocale(locale);
////        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
//        val locale = Locale(langCode)
//        Locale.setDefault(locale)
//        val configuration = Configuration()
//        configuration.locale = locale
//        baseContext.resources.updateConfiguration(configuration,
//            baseContext.resources.displayMetrics)
//        // save data
//        val editor = getSharedPreferences("Settings", MODE_PRIVATE).edit()
//        editor.putString("My_Lang", langCode)
//    }
//
//    fun loadLocale() {
//        val preferences = getSharedPreferences("Settings", MODE_PRIVATE)
//        val language = preferences.getString("My_Lang", "")
//        setLocal(language)
//    }
//    fun arabicInsertionDatabase(arabic:Boolean,english:Boolean){
//        val entity = Category(arabicLanguage = arabic,englishLanguage = english)
//        MyDatabase.getInsertion(this).todoDao().arabicLanguageAdded(entity)
//        Toast.makeText(this@MainActivityLanaguage,english.toString(),Toast.LENGTH_LONG).show()
//    }
//    fun englishInsertionDatabase(arabic:Boolean,english:Boolean){
//        val entity = Category(arabicLanguage = arabic,englishLanguage = english)
//        MyDatabase.getInsertion(this).todoDao().englishLanguageAdded(entity)
//        Toast.makeText(this@MainActivityLanaguage,english.toString(),Toast.LENGTH_LONG).show()
//    }
//}

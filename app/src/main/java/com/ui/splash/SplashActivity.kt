package com.ui.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.chat.database.getLanguage
import com.chat.database.getUser
import com.database.DataUtil
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.model.ApplicationUser
import com.ui.R
import com.ui.account.login.LoginActivity
import com.ui.main.MainActivity
import java.util.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val animation = AnimationUtils.loadAnimation(this,R.anim.bounce)
        val image = findViewById(R.id.programming_logo) as ImageView
        image.startAnimation(animation)
        Handler(Looper.getMainLooper()).postDelayed({
         checkLoginInFirebase()
          },2000)
    }
    private fun checkLoginInFirebase() {
        val firebaseUser = Firebase.auth.currentUser
        when{
            firebaseUser==null->{
                openLoginAccount()
            }
            else->{
                getUser(firebaseUser.uid, OnSuccessListener {
                    val user = it.toObject(ApplicationUser::class.java)
                    DataUtil.user=user
                    getLanguage(user?.id!!,)
                    openHome()
                }, OnFailureListener {
                    openLoginAccount()
                })
            }
        }
    }
    private fun openHome() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun openLoginAccount() {
        val intent = Intent (this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun setLocal(activity: Activity, langCode: String?) {
        val locale = Locale(langCode)
        Locale.setDefault(locale)
        val resources = activity.resources
        val configuration = resources.configuration
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}
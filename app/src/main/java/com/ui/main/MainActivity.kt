package com.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ui.R
import com.ui.account.AccountActivity
import com.ui.home.HomeFragment
import com.ui.love.LoveFragment

class MainActivity : AppCompatActivity() {
    lateinit var item_navigation : BottomNavigationView
    val homeFragment = HomeFragment()
    val loveFragment = LoveFragment()
    val accountFragment = AccountActivity()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        makeItemNavigation()
    }
    fun makeItemNavigation(){
        item_navigation = findViewById(R.id.bottonNavigation)
        item_navigation.setOnItemSelectedListener OnItemSelectedListener@{
            when (it.itemId) {
                R.id.homeNavigation -> {
                    pushfragment(homeFragment)
                }
                R.id.favoriteNavigation -> {
                    pushfragment(loveFragment)
                }
                R.id.accountNavigation -> {
                    pushfragment(accountFragment)
                }
            }
            return@OnItemSelectedListener true
        }
        item_navigation.selectedItemId = R.id.homeNavigation
    }
    private fun pushfragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.Fragment_Container , fragment).commit()
    }

    override fun onStart() {
        super.onStart()
    }
}
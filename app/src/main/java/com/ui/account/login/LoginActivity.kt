package com.ui.account.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.base.BaseActivity
import com.ui.R
import com.ui.account.register.RegisterActivity
import com.ui.databinding.ActivityLoginBinding
import com.ui.main.MainActivity

class LoginActivity : BaseActivity<ActivityLoginBinding,LoginViewModel>() , Navigator{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vmLogin = viewModel
        viewModel.navigator = this

    }

    override fun getLayoutID(): Int {
        return R.layout.activity_login
    }

    override fun makeViewModelProvider(): LoginViewModel {
        return ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun openMainActivity() {
       val intent = Intent (this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun openRegisterActivity() {
    val intent = Intent(this,RegisterActivity::class.java)
    startActivity(intent)
    }
}
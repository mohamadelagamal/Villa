package com.ui.account.register

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.base.BaseActivity
import com.google.firebase.firestore.index.IndexEntry
import com.ui.R
import com.ui.databinding.ActivityRegisterBinding
import com.ui.main.MainActivity

class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>() , Navigator{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vmRegister = viewModel
        viewModel.navigator = this
    }
    override fun getLayoutID(): Int {
        return R.layout.activity_register
    }

    override fun makeViewModelProvider(): RegisterViewModel {
        return ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    override fun openMainActivity() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
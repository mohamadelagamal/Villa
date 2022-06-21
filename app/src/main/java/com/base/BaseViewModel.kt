package com.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<N> : ViewModel(){
    var navigator:N?=null
    var showLoading = MutableLiveData<Boolean>()
    var messageLiveData = MutableLiveData<String>()
    //
//    var houseItemSpinner = MutableLiveData<Boolean>()
//    var beachHouseItemSpinner = MutableLiveData<Boolean>()
//    var villaItemSpinner = MutableLiveData<Boolean>()
    var buying = MutableLiveData<Boolean>()
//    var rent = MutableLiveData<Boolean>()
    var chooser = MutableLiveData<String>()


}
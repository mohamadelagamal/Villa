package com.ui.chat

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.base.BaseViewModel
import com.chat.database.addMessage
import com.database.DataUtil
import com.database.Messages
import com.database.Teacher
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import java.util.*

class ChatViewModel : BaseViewModel<Navigator>() {

        var room : Teacher?=null
    var toastMessage = MutableLiveData<String>()
    var contentMessage =  ObservableField<String>()
    var imageUri =  ObservableField<String>()
    var checkBox :Boolean?=false
    fun sendMessage(){
        val message = Messages(
            content = contentMessage.get(),
            roomID = room?.id,
            senderId = DataUtil.user?.id,
            senderName = DataUtil.user?.userName,
            dateTime = Date().time,
            //imageURl = imageUri.get()
        )
        if (contentMessage.get().isNullOrBlank()){
        }
    else{
            addMessage(message, OnSuccessListener {
                contentMessage.set("")
                //imageUri.set("")
            }, OnFailureListener {
                Log.e("message",it.localizedMessage)

                // toastMessage.value = "Something went wrong , try again"
            })
            // go to login activity
    }
    }
        fun openStorage(){
    navigator?.openStorage()
}

    fun iconDetailsView(){
    navigator?.iconDetails()
    }
}
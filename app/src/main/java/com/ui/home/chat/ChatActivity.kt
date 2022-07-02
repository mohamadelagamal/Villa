package com.ui.chat
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.BaseActivity
import com.chat.database.addMessage
import com.chat.database.addRoom
import com.chat.database.getMessageRef
import com.database.ConstantCollection
import com.database.DataUtil
import com.database.Messages
import com.database.Teacher
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.model.Constant
import com.model.loadImage
import com.ui.R
import com.ui.databinding.ActivityChatBinding
import com.ui.home.chat.icon.IconDetailsActivity
import com.ui.home.chat.messages.MessagesAdapter
import java.util.*

class ChatActivity : BaseActivity<ActivityChatBinding,ChatViewModel>(),Navigator {
    lateinit var room:Teacher
    // to sorted messages
    lateinit var icon:ImageView
    lateinit var layoutManager: LinearLayoutManager
    val adapter = MessagesAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        room = intent.getParcelableExtra(Constant.EXTRA_ROOM)!!
        viewModel.room = room
        viewDataBinding.vmRoomChat = viewModel
        viewModel.navigator = this
        icon = viewDataBinding.iconMessage
        icon.loadImage(room.imageUrl)
        viewDataBinding.recyclerView.adapter=adapter
        layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd=true
        viewDataBinding.recyclerView.layoutManager=layoutManager
       listForMessagesUpdates()

    }
    fun listForMessagesUpdates(){
        getMessageRef(roomId = room.id!!)
            .orderBy("dateTime",Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error!=null){
                    //   Toast.makeText(this,"error",Toast.LENGTH_LONG).show()
                }else {
                    val newMessagesList = mutableListOf<Messages>()
                    for (dc in snapshot!!.documentChanges) {
                        when (dc.type) {
                            DocumentChange.Type.ADDED -> {
                                val message = dc.document.toObject(Messages::class.java)
                                newMessagesList.add(message)
                            }
                            else -> {}
                        }
                    }
                    adapter.appendMessages(newMessagesList)
                    viewDataBinding.recyclerView.smoothScrollToPosition(adapter.itemCount)
                }}
    }
    override fun makeViewModelProvider(): ChatViewModel {
        return ViewModelProvider(this).get(ChatViewModel::class.java)
    }



    override fun getLayoutID(): Int {
        return R.layout.activity_chat
    }

    override fun openStorage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun iconDetails() {
        val intent = Intent(this@ChatActivity,IconDetailsActivity::class.java)
        intent.putExtra(ConstantCollection.COLLECTION_ICON_DETAILS, room)
        startActivity(intent)
    }

    private val PICK_IMAGE_REQUEST = 1
    private var mImageUri : Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            mImageUri = data.data
            viewModel.imageUri.set(mImageUri.toString())
            //viewDataBinding.chooseImageChat.setImageURI(mImageUri)
            putImageInStorage(mImageUri.toString())
        }
    }
    private var mStorageRef: StorageReference? = null
    private fun putImageInStorage( uri: String) {
        mStorageRef = FirebaseStorage.getInstance().getReference("message")
        val storageReference = FirebaseStorage.getInstance()
            .getReference("message" + System.currentTimeMillis() + ".jpg")
        if (mImageUri != null ) {
            storageReference.putFile(mImageUri!!)
                .addOnSuccessListener { taskSnapshot ->
                    Toast.makeText(
                        applicationContext,
                        "Image Uploaded Successfully",
                        Toast.LENGTH_LONG
                    ).show()
                    val myprofileurl = taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                    Log.d(ContentValues.TAG, "Profile image uploading url $myprofileurl")
                    storageReference.downloadUrl.addOnCompleteListener { task ->
                        val upload = Messages(
                            roomID = room?.id,
                            senderId = DataUtil.user?.id,
                            senderName = DataUtil.user?.userName,
                            dateTime = Date().time,
                            imageURl =  task.result.toString())
                        addMessage(upload, onSuccessListener = {
                        },onFailureListener = {})
                    }
                }.addOnFailureListener {
                    Toast.makeText(
                        applicationContext,
                        "Image Uploading was failed",
                        Toast.LENGTH_LONG
                    ).show()
                    viewModel.showLoading.value = false
                }
    }}
}
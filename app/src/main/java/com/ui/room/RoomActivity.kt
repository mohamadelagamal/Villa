package com.ui.room

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.base.BaseActivity
import com.chat.database.addRoom
import com.database.Teacher
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.ui.R
import com.ui.databinding.ActivityRoomBinding
import com.ui.main.MainActivity
import com.ui.room.spinner.CustomAdapter

class RoomActivity : BaseActivity<ActivityRoomBinding,RoomViewModel>(),Navigator{
    private var mImageUri : Uri? = null
    private var mStorageRef: StorageReference? = null
    private var mDatabaseRef: DatabaseReference? = null
    lateinit var chooser:String
    private val PICK_IMAGE_REQUEST = 1
    lateinit var spinnerMode: Spinner
    lateinit var spinnerIcon :Spinner
    val mode = arrayOf("Buying", "Rent")
    internal var fruits = arrayOf("House", "Villa", "Beach House")
    internal var images =
        intArrayOf(R.drawable.house_icon_spinner,R.drawable.villa_icon_spinner, R.drawable.beach_icon_spinner)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vmRoom=viewModel
        viewModel.navigator=this
        spinnerMode = viewDataBinding.spinnerID
        spinnerIcon = viewDataBinding.spinnerIcon
        spinnerIconShow()
        setZero()
        spinnerModeInitView()
    }
    private fun setZero() {
        viewModel.numberRoom.set("0")
        viewModel.numberParking.set("0")
        viewModel.numberBath.set("0")

    }

    private fun spinnerIconShow() {
        spinnerIcon.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position.equals(0)){
                    viewModel.houseItemSpinner = true
                    viewModel.villaItemSpinner= false
                    viewModel.beachHouseItemSpinner=false
                }
                if (position.equals(1)){
                    viewModel.villaItemSpinner = true
                    viewModel.beachHouseItemSpinner = false
                    viewModel.houseItemSpinner = false
                }
                if (position.equals(2)){
                    viewModel.beachHouseItemSpinner = true
                    viewModel.villaItemSpinner = false
                    viewModel.houseItemSpinner = false
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        val customAdapter = CustomAdapter(applicationContext, images, fruits)
        spinnerIcon.adapter = customAdapter
    }
    override fun getLayoutID(): Int {
        return R.layout.activity_room
    }
    override fun makeViewModelProvider(): RoomViewModel {
        return ViewModelProvider(this).get(RoomViewModel::class.java)
    }

    override fun backHome() {
        val intent = Intent (this,MainActivity::class.java)
        startActivity(intent)
    }

    override fun showCamera() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun houseDatabase()
          {
              mStorageRef = FirebaseStorage.getInstance().getReference("house")
              mDatabaseRef = FirebaseDatabase.getInstance().getReference("house")
              val storageReference = FirebaseStorage.getInstance()
                .getReference("myprofile/" + System.currentTimeMillis() + ".jpg")
            if (mImageUri != null && viewModel.houseItemSpinner == true) {
                   viewModel.showLoading.value = true
                storageReference.putFile(mImageUri!!)
                    .addOnSuccessListener { taskSnapshot ->
                        Toast.makeText(
                            applicationContext,
                            "Image Uploaded Successfully",
                            Toast.LENGTH_LONG
                        ).show()
                        //TODO Here is the problem
//                            profileimageurl = taskSnapshot.getDownloadUrl().toString();
                        val myprofileurl = taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                        Log.d(ContentValues.TAG, "Profile image uploading url $myprofileurl")
                        storageReference.downloadUrl.addOnCompleteListener { task ->
                            val upload = Teacher(
                                name = viewModel.roomName.get().toString().trim { it <= ' ' },
                                imageUrl =  task.result.toString(),
                                description =  viewModel.description.get().toString().trim { it <= ' ' },
                                roomNumber = viewModel.numberRoom.get().toString().trim { it <= ' ' },
                                bathNumber = viewModel.numberBath.get().toString().trim { it <= ' ' },
                                parkingNumber = viewModel.numberParking.get().toString().trim { it <= ' ' },
                                price = viewModel.priceRoom.get().toString().trim { it <= ' ' },
                                iconSpinner = "House",
                                textSpinner =  chooser.toString().trim { it <= ' ' },
                                checked = false
                                )
                            //TODO Here take object form RealTimeDatabase to FireStore DataBase
                            addRoom(upload, onSuccessListener = {}) {}
                            val uploadId = mDatabaseRef!!.push().key
                            mDatabaseRef!!.child((uploadId)!!).setValue(upload)
                        }
                        viewModel.showLoading.value = false
                        backHome()
                    }.addOnFailureListener {
                        Toast.makeText(
                            applicationContext,
                            "Image Uploading was failed",
                            Toast.LENGTH_LONG
                        ).show()
                        viewModel.showLoading.value = false
                    }
            }
              else{
                  viewModel.messageLiveData.value= "please check image!!"
              }

        }

    override fun villaDatabase()
    {
        mStorageRef = FirebaseStorage.getInstance().getReference("villa")
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("villa")
        val storageReference = FirebaseStorage.getInstance()
            .getReference("myprofile/" + System.currentTimeMillis() + ".jpg")
        if (mImageUri != null && viewModel.villaItemSpinner == true  && viewModel.houseItemSpinner == false  && viewModel.beachHouseItemSpinner == false) {
            viewModel.showLoading.value = true
            storageReference.putFile(mImageUri!!)
                .addOnSuccessListener { taskSnapshot ->
                    Toast.makeText(
                        applicationContext,
                        "Image Uploaded Successfully",
                        Toast.LENGTH_LONG
                    ).show()

                    //TODO Here is the problem
//                            profileimageurl = taskSnapshot.getDownloadUrl().toString();
                    val myprofileurl = taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                    Log.d(ContentValues.TAG, "Profile image uploading url $myprofileurl")
                    storageReference.downloadUrl.addOnCompleteListener { task ->
                        val upload = Teacher(
                            name = viewModel.roomName.get().toString().trim { it <= ' ' },
                            imageUrl =  task.result.toString(),
                            description =  viewModel.description.get().toString().trim { it <= ' ' },
                            roomNumber = viewModel.numberRoom.get().toString().trim { it <= ' ' },
                            bathNumber = viewModel.numberBath.get().toString().trim { it <= ' ' },
                            parkingNumber = viewModel.numberParking.get().toString().trim { it <= ' ' },
                            price = viewModel.priceRoom.get().toString().trim { it <= ' ' },
                            iconSpinner = "Villa",
                            textSpinner =  chooser.toString().trim { it <= ' ' },
                        )
                        //TODO Here take object form RealTimeDatabase to FireStore DataBase
                        addRoom(upload, onSuccessListener = {}) {}
                        val uploadId = mDatabaseRef!!.push().key
                        mDatabaseRef!!.child((uploadId)!!).setValue(upload)
                    }
                    // viewDataBinding.progressBar.visibility = View.INVISIBLE
                    viewModel.showLoading.value = false
                    backHome()
                }.addOnFailureListener {
                    Toast.makeText(
                        applicationContext,
                        "Image Uploading was failed",
                        Toast.LENGTH_LONG
                    ).show()
                    viewModel.showLoading.value = false
                }
        }
        else{
            viewModel.messageLiveData.value= "please check image!!"
        }
    }

    override fun beachHouseDatabase() {
        mStorageRef = FirebaseStorage.getInstance().getReference("beach")
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("beach")
        val storageReference = FirebaseStorage.getInstance()
            .getReference("myprofile/" + System.currentTimeMillis() + ".jpg")
        if (mImageUri != null && viewModel.villaItemSpinner == false  && viewModel.houseItemSpinner == false  && viewModel.beachHouseItemSpinner == true) {
            viewModel.showLoading.value = true
            storageReference.putFile(mImageUri!!)
                .addOnSuccessListener { taskSnapshot ->
                    Toast.makeText(
                        applicationContext,
                        "Image Uploaded Successfully",
                        Toast.LENGTH_LONG
                    ).show()

                    //TODO Here is the problem
//                            profileimageurl = taskSnapshot.getDownloadUrl().toString();
                    val myprofileurl = taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                    Log.d(ContentValues.TAG, "Profile image uploading url $myprofileurl")
                    storageReference.downloadUrl.addOnCompleteListener { task ->
                        val upload = Teacher(
                            name = viewModel.roomName.get().toString().trim { it <= ' ' },
                            imageUrl =  task.result.toString(),
                            description =  viewModel.description.get().toString().trim { it <= ' ' },
                            roomNumber = viewModel.numberRoom.get().toString().trim { it <= ' ' },
                            bathNumber = viewModel.numberBath.get().toString().trim { it <= ' ' },
                            parkingNumber = viewModel.numberParking.get().toString().trim { it <= ' ' },
                            price = viewModel.priceRoom.get().toString().trim { it <= ' ' },
                            iconSpinner = "BeachHouse",
                            textSpinner =  chooser.toString().trim { it <= ' ' },
                        )
                        //TODO Here take object form RealTimeDatabase to FireStore DataBase
                        addRoom(upload, onSuccessListener = {}) {}
                        val uploadId = mDatabaseRef!!.push().key
                        mDatabaseRef!!.child((uploadId)!!).setValue(upload)
                    }
                    // viewDataBinding.progressBar.visibility = View.INVISIBLE
                    viewModel.showLoading.value = false
                    backHome()
                }.addOnFailureListener {
                    Toast.makeText(
                        applicationContext,
                        "Image Uploading was failed",
                        Toast.LENGTH_LONG
                    ).show()
                    viewModel.showLoading.value = false
                }
        }
        else{
            viewModel.messageLiveData.value= "please check image!!"
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            mImageUri = data.data
            viewDataBinding.imageCamera.setImageURI(mImageUri)
        }
    }

    private fun spinnerModeInitView() {
        spinnerMode?.adapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item
            , mode) as SpinnerAdapter
        spinnerMode?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("error")
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = parent?.getItemAtPosition(position).toString()
                // Toast.makeText(activity,type, Toast.LENGTH_LONG).show()
                if (type=="Buying"){
                   //viewModel.chooser.value = "Buying"
                    chooser="Buying"
                }
                if (type=="Rent"){
                    //viewModel.chooser.value = "Rent"
                    chooser="Rent"
                }
            }

        }
    }
}
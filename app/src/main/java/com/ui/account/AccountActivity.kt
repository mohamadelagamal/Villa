package com.ui.account

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.chat.database.addUserToFireStore
import com.database.DataUtil
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.model.ApplicationUser
import com.model.loadImage
import com.prongbang.localizedapp.SettingActivity
import com.squareup.picasso.Picasso
import com.ui.R
import com.ui.account.language.MainActivityLanaguage
import com.ui.account.login.LoginActivity
import com.ui.databinding.FragmentAccountBinding
import org.intellij.lang.annotations.Language
import java.util.*


class AccountActivity : Fragment(){
    private var mStorageRef: StorageReference? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mImageUri : Uri? = null
    private var mStorage: FirebaseStorage? = null
    private val PICK_IMAGE_REQUEST = 1
    lateinit var nameUser : EditText
    lateinit var moreCategories:TextView
    lateinit var progressBar: ProgressBar
    lateinit var deleteAccount:TextView
    lateinit var saveButton : LinearLayout
    lateinit var emailUser : EditText
    val binding :FragmentAccountBinding?=null
    lateinit var cardImageView : CardView
    lateinit var changeLanguage:TextView
    lateinit var imageUser:ImageView
    //... language

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    val uid = FirebaseAuth.getInstance().currentUser!!.uid

    var progressDialog : ProgressDialog?=null
    private fun showLoading() {
        progressDialog= ProgressDialog(requireContext())
        progressDialog?.setMessage("Loading...")
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }
    private fun hideLoading() {
        progressDialog?.dismiss()
        progressDialog=null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardImageView = requireActivity().findViewById(R.id.cardImageUser)
        imageUser = requireActivity().findViewById(R.id.imageUser)
        mAuth = FirebaseAuth.getInstance()
        changeLanguage = requireActivity().findViewById(R.id.textChangeLanguage)
        nameUser = requireActivity().findViewById(R.id.userName)
        moreCategories = requireActivity().findViewById(R.id.logOut)
        emailUser = requireActivity().findViewById(R.id.userEmail)
        saveButton = requireActivity().findViewById(R.id.linearSave)
        deleteAccount = requireActivity().findViewById(R.id.deleteAccount)
        // language button

        changeLanguage.setOnClickListener {
            startChangeLanguage()
            }
        val db = Firebase.firestore
        val firebaseUser = Firebase.auth.currentUser
        deleteAccount.setOnClickListener {

            MaterialAlertDialogBuilder(requireContext())
                .setMessage(getString(R.string.delte_account_fragment)).setPositiveButton(getString(
                                    R.string.yes)){ dialog, which->
                    db.collection("users").document(firebaseUser?.uid.toString())
                        .delete()
                        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!")
                            Toast.makeText(requireContext(),getString(R.string.success_deleted),Toast.LENGTH_LONG).show()
                        }
                        .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
                logOut()
            }.show()


        }
        //...
        val firebaseDatabase = FirebaseDatabase.getInstance()
        val databaseReference = firebaseDatabase.reference
        val getImage = databaseReference.child("imageUser")
        getImage.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val link = dataSnapshot.getValue(String::class.java)
                if (link!=null){
                Picasso.with(context).load(link).fit().centerCrop()
                    .placeholder(R.drawable.ic_action_account)
                    .error(R.drawable.ic_action_account)
                    .into(imageUser);}
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(requireContext(), "Error Loading Image", Toast.LENGTH_SHORT).show()
            }
        })
        cardImageView.setOnClickListener {
            showCamera()
        }
        //..
        moreCategories.setOnClickListener{
            MaterialAlertDialogBuilder(requireContext()).setMessage(getString(R.string.log_out_account)).setPositiveButton("yes"){ dialog, which->
              logOut()
            }.show()
        }
        val rootRef = FirebaseFirestore.getInstance()
        val usersRef = rootRef.collection("users")
        val uidRef = usersRef.document(uid)
        uidRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document.exists()) {
                    val zip_codes = document.toObject(ApplicationUser::class.java)
                    //Do what you need to do with your list
                    nameUser.setText(zip_codes?.userName)
                    emailUser.setText(zip_codes?.email)
                    imageUser.loadImage(zip_codes?.imageID)
                } else {
                    Log.d(TAG, "No such document")
                }
            } else {
                Log.d(TAG, "get failed with ", task.exception)
            }
        }
        saveButton.setOnClickListener {
           saveUserData()
            imageUserFunctionSave()
            showImageUser()}
    }

    private fun startChangeLanguage() {
//        val intent = Intent(requireContext(), SettingActivity::class.java)
//        startActivity(intent)
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.select_language)).setPositiveButton(getString(R.string.english)){ dialog, which->
                val languageToLoad = "en" // your language
                val locale = Locale(languageToLoad)
                Locale.setDefault(locale)
                val config = Configuration()
                config.locale = locale
                requireContext().resources.updateConfiguration(config,requireActivity().resources.displayMetrics)
                getActivity()?.finish();
                startActivity(getActivity()?.intent)
            }.setNegativeButton(getString(R.string.arabic)){ dialog, which->
                val languageToLoad = "ar" // your language
                val locale = Locale(languageToLoad)
                Locale.setDefault(locale)
                val config = Configuration()
                config.locale = locale
                requireContext().resources.updateConfiguration(config,requireActivity().resources.displayMetrics)
                getActivity()?.finish();
                startActivity(getActivity()?.intent)
            }.show()
    }

    private fun showImageUser() {
        mStorage = FirebaseStorage.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("imageUser")

    }
    var mAuth: FirebaseAuth? = null
    private fun imageUserFunctionSave() {
        mStorageRef = FirebaseStorage.getInstance().getReference("imageUser")
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("imageUser")
        val storageReference = FirebaseStorage.getInstance()
            .getReference("imageUser/" + System.currentTimeMillis() + ".jpg")
        if (filePath != null){
            storageReference.putFile(filePath!!)
                .addOnSuccessListener {
                    storageReference.downloadUrl.addOnCompleteListener { task ->
                        val upload =  ApplicationUser(id = uid,imageID = task.result.toString())
                        val uploadId = mDatabaseRef!!.push().key
                        mDatabaseRef!!.child((uploadId)!!).setValue(upload)
                    }
                }.addOnFailureListener {
                    Toast.makeText(
                        requireContext(),
                        "Image Uploading was failed",
                        Toast.LENGTH_LONG
                    ).show()
                }
            storageReference.child(mAuth?.currentUser?.uid+".jpg")

        }
    }
    private fun saveUserData() {
        showLoading()
        val appUser = ApplicationUser(
            id = uid,
            userName = nameUser.text.toString(),
            email = emailUser.text.toString(),
            imageID = mImageUri.toString()
        )
        addUserToFireStore(appUser ,
            OnSuccessListener {
                hideLoading()
                DataUtil.user = appUser
                Toast.makeText(requireContext(),getString(R.string.upadte_success),Toast.LENGTH_LONG).show()
            }, OnFailureListener {
                hideLoading()
                Toast.makeText(requireContext(),getString(R.string.please_intern),Toast.LENGTH_LONG).show()
            })

    }
    var auth: FirebaseAuth? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == AppCompatActivity.RESULT_OK && data != null && data.data != null) {
            mImageUri = data.data
            imageUser.setImageURI(mImageUri)
        }
    }
    fun showCamera() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }
    private var filePath: Uri? = null

    private fun logOut() {
        auth?.signOut()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

}
package com.ui.love

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chat.database.getFavoriteREF
import com.chat.database.getMessageRef
import com.database.Messages
import com.database.Teacher
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.*
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.model.Constant
import com.ui.R
import com.ui.love.adapter.ListAdapter

class LoveFragment : Fragment() {

    private var mStorage: FirebaseStorage? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mDBListener: ValueEventListener? = null
    private lateinit var mTeachers:MutableList<Teacher>
    private lateinit var listAdapter: ListAdapter
    lateinit var mRecyclerView : RecyclerView
    lateinit var myDataLoaderProgressBar : ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    lateinit var checkBox: CheckBox
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecyclerView = requireActivity().findViewById(R.id.recycleViewHouse)
        myDataLoaderProgressBar = requireActivity().findViewById(R.id.myDataLoaderProgressBar)
        //checkBox = requireActivity().findViewById(R.id.cbHeart)
        //  checkBox.setBackgroundResource(R.drawable.after_liked)
        /**set adapter*/
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        myDataLoaderProgressBar.visibility = View.VISIBLE
        mTeachers = ArrayList()
        listAdapter = ListAdapter(requireContext(), mTeachers)
        mRecyclerView.adapter = listAdapter
         showItemLove()
    }

    private fun showItemLove() {

        getFavoriteREF(onSuccessListener = {
                qureySnapshot->
            myDataLoaderProgressBar.visibility = View.GONE
            val rooms = qureySnapshot.toObjects(Teacher::class.java)
           listAdapter.changData(rooms)
        },onFailureListener = {
            myDataLoaderProgressBar.visibility = View.GONE

        })
    }

}
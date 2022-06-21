package com.ui.home.villa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.database.Teacher
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.model.Constant
import com.ui.R
import com.ui.chat.ChatActivity
import com.ui.home.adapter.ListAdapter

class VillaActivity : AppCompatActivity() {

    private var mStorage: FirebaseStorage? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mDBListener: ValueEventListener? = null
    private lateinit var mTeachers:MutableList<Teacher>
    private lateinit var listAdapter: ListAdapter
    lateinit var mRecyclerView : RecyclerView
    lateinit var myDataLoaderProgressBar : ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_villa)
        mRecyclerView = findViewById(R.id.recycleViewHouse)
        myDataLoaderProgressBar = findViewById(R.id.myDataLoaderProgressBar)
        /**set adapter*/
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this@VillaActivity)
        myDataLoaderProgressBar.visibility = View.VISIBLE
        mTeachers = ArrayList()
        listAdapter = ListAdapter(this,mTeachers)
        mRecyclerView.adapter = listAdapter
        /**set Firebase Database*/
        mStorage = FirebaseStorage.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("villa")
        mDBListener = mDatabaseRef!!.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@VillaActivity,error.message, Toast.LENGTH_SHORT).show()
                myDataLoaderProgressBar.visibility = View.INVISIBLE

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                mTeachers.clear()
                for (teacherSnapshot in snapshot.children){
                    val upload = teacherSnapshot.getValue(Teacher::class.java)
                    upload!!.key = teacherSnapshot.key
                    mTeachers.add(upload)

                }
                listAdapter.notifyDataSetChanged()
                myDataLoaderProgressBar.visibility = View.GONE

            }

        })
        listAdapter.onItemClickListener = object : ListAdapter.OnItemClickListener {
            override fun onItemClick(pos: Int, room: Teacher) {
                startChatActiviy(room)

            }
        }

    }

    private fun startChatActiviy(room: Teacher) {
        val intent = Intent(this, ChatActivity::class.java)
        // send data
        intent.putExtra(Constant.EXTRA_ROOM, room)
        startActivity(intent)
    }
}
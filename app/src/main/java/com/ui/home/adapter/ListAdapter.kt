package com.ui.home.adapter

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.chat.database.addFavorite
import com.chat.database.getFavoriteREF
import com.database.Teacher
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.model.loadImage
import com.ui.R
import java.util.*
import kotlin.collections.ArrayList


class ListAdapter (var mContext:Context,var teacherList:List<Teacher> ):
RecyclerView.Adapter<ListAdapter.ListViewHolder>()
{
    private var mStorageRef: StorageReference? = null
    private var mDatabaseRef: DatabaseReference? = null
    inner class ListViewHolder(var v:View): RecyclerView.ViewHolder(v){
        var imgT = v.findViewById<ImageView>(R.id.imageCategory)
        var nameT = v.findViewById<TextView>(R.id.nameRoomCategory)
        //var descriT = v.findViewById<TextView>(R.id.descriptionTextView)
        var favorite = v.findViewById<CheckBox>(R.id.cbHeart)
        var roomNumber = v.findViewById<TextView>(R.id.roomNumberCategory)
        var bathNumber = v.findViewById<TextView>(R.id.bathNumberCategory)
        var price = v.findViewById<TextView>(R.id.textPrice)
        var parkingNumber = v.findViewById<TextView>(R.id.parkingNumberCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
       var infalter = LayoutInflater.from(parent.context)
        var v = infalter.inflate(R.layout.item_category,parent,false)
        return ListViewHolder(v)
    }

    override fun getItemCount(): Int =teacherList.size
    val db = Firebase.firestore

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
       var newList = teacherList[position]
        holder.nameT.text = newList.name
      //  holder.descriT.text = newList.description
        holder.roomNumber.text = newList.roomNumber
        holder.bathNumber.text = newList.bathNumber
        holder.price.text = newList.price
        holder.parkingNumber.text = newList.parkingNumber
        holder.imgT.loadImage(newList.imageUrl)
        val check = ArrayList<Boolean>()
        mStorageRef = FirebaseStorage.getInstance().getReference("favorite")
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("favorite")
        onItemClickListener?.let {
            holder.itemView.setOnClickListener {
                // position this is number in onBindViewHolder and items[position] this number in list
                onItemClickListener?.onItemClick(position, teacherList[position])
            }
        }
        holder.favorite.setOnCheckedChangeListener { checkBox, isChecked ->
            if (isChecked){
     Log.e("Item added to Wishlist","favorite")
    //  onItemClickListener?.onItemClick(position,newList)
          var favorite = Teacher(name = newList.name,
              roomNumber = newList.roomNumber,imageUrl = newList.imageUrl,
              checked = true, price = newList.price,bathNumber = newList.bathNumber,
          parkingNumber = newList.parkingNumber)
                  addFavorite(favorite,onSuccessListener = {
                     Toast.makeText(mContext,"Item added to favorite",Toast.LENGTH_LONG).show()
             // newList.checked = true
                      check.add(true)
                } , onFailureListener = {

                })
            }else{
                    db.collection("favorite").document(newList.id.toString())
                        .delete()
                        .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully deleted!")
                            Toast.makeText(mContext,"successfully deleted",Toast.LENGTH_LONG).show()
                        }
                        .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error deleting document", e) }
            }
        }
    }
    var onItemClickListener: OnItemClickListener? = null
    interface OnItemClickListener {
        fun onItemClick(pos: Int, room: Teacher)
    }
}
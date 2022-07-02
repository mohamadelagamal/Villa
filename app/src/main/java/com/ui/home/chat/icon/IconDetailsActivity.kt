package com.ui.home.chat.icon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.database.ConstantCollection
import com.database.Teacher
import com.model.Constant
import com.model.loadImage
import com.ui.R

class IconDetailsActivity : AppCompatActivity() {
    lateinit var room:Teacher
    lateinit var icon:ImageView
    lateinit var descriptor: TextView
    lateinit var roomNumber: TextView
    lateinit var priceIcon:TextView
    lateinit var bath:TextView
    lateinit var parking:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        room = intent.getParcelableExtra(ConstantCollection.COLLECTION_ICON_DETAILS)!!
        setContentView(R.layout.activity_icon_details)
        icon=findViewById(R.id.imageDetails)
        icon.loadImage(room.imageUrl)
        descriptor = findViewById(R.id.descriptionIconDetails)
        descriptor.setText(room.description)
        roomNumber = findViewById(R.id.roomNumbeer)
        roomNumber.setText(room.roomNumber)
        bath = findViewById(R.id.bathNumber)
        bath.setText(room.bathNumber)
        parking = findViewById(R.id.parkingNumber)
        parking.setText(room.bathNumber)
        priceIcon = findViewById(R.id.priceIcon)
        priceIcon.setText(room.price)
    }
}
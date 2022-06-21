package com.ui.home.chat.messages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.database.DataUtil
import com.database.Messages
import com.ui.R
import com.ui.databinding.ItemImageMessageBinding
import com.ui.databinding.ItemMessageRecievedBinding
import com.ui.databinding.ItemMessageSendBinding

class MessagesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val items = mutableListOf<Messages>()
    val RECIVED =1
    val SEND =2
    val image =3
    override fun getItemViewType(position: Int): Int {
      val message = items.get(position)
      if (message.senderId == DataUtil.user?.id){
          return SEND
      }
      else
        return RECIVED
    }
    class sendMessageViewHolder(val viewDataBinding:ItemMessageSendBinding):
        RecyclerView.ViewHolder(viewDataBinding.root){
        fun bind(messages: Messages){
            viewDataBinding.vmSend=messages
            viewDataBinding.executePendingBindings()
        }
    }

    class recivedMessageViewHolder(val viewDataBinding: ItemMessageRecievedBinding):
            RecyclerView.ViewHolder(viewDataBinding.root){
              fun bind(messages: Messages){
                  viewDataBinding.vmRecieved=messages
                  viewDataBinding.executePendingBindings()
              }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (RECIVED) {
            viewType -> {
                val itemBinding:ItemMessageRecievedBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                    R.layout.item_message_recieved , parent,false)
                return recivedMessageViewHolder(itemBinding)
            }
            else -> {
                val itemBinding:ItemMessageSendBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                    R.layout.item_message_send , parent,false)
                return sendMessageViewHolder(itemBinding)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is sendMessageViewHolder){
            holder.bind(items[position])
           //holder.viewDataBinding.imageSender.loadImage(holder.viewDataBinding.vmSend?.imageURl)

            val message: Messages = items[position]

            val isPhoto = message.getPhotoURL() != null
            if (isPhoto) {
                holder.viewDataBinding.message.setVisibility(View.GONE)
                holder.viewDataBinding.imageSender.setVisibility(View.VISIBLE)
                Glide.with(holder.viewDataBinding.imageSender.getContext())
                    .load(message.getPhotoURL())
                    .into(holder.viewDataBinding.imageSender)
            } else {
                holder.viewDataBinding.message.setVisibility(View.VISIBLE)
                holder.viewDataBinding.imageSender.setVisibility(View.GONE)
                holder.viewDataBinding.message.setText(holder.viewDataBinding.vmSend?.content)
            }
            //authorTextView.setText(message.getTitre())
            }
         if (holder is recivedMessageViewHolder){
            holder.bind(items[position])
             holder.bind(items[position])
             //holder.viewDataBinding.imageSender.loadImage(holder.viewDataBinding.vmSend?.imageURl)

             val message: Messages = items[position]

             val isPhoto = message.getPhotoURL() != null
             if (isPhoto) {
                 holder.viewDataBinding.message.setVisibility(View.GONE)
                 holder.viewDataBinding.imageRecived.setVisibility(View.VISIBLE)
                 Glide.with(holder.viewDataBinding.imageRecived.getContext())
                     .load(message.getPhotoURL())
                     .into(holder.viewDataBinding.imageRecived)
             } else {
                 holder.viewDataBinding.message.setVisibility(View.VISIBLE)
                 holder.viewDataBinding.imageRecived.setVisibility(View.GONE)
                 holder.viewDataBinding.message.setText(holder.viewDataBinding.vmRecieved?.content)
             }}

    }

    override fun getItemCount(): Int {
       return items.count()
    }

    fun appendMessages(newMessagesList: MutableList<Messages>) {
        items.addAll(newMessagesList)
        notifyItemRangeInserted( items.size+1, newMessagesList.size)
    }
}
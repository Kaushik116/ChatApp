package com.kaushik.chatapp1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kaushik.chatapp1.R
import com.kaushik.chatapp1.Message
import com.kaushik.chatapp1.Constants.RECEIVE_ID
import com.kaushik.chatapp1.Constants.SEND_ID

class MessagingAdapter:RecyclerView.Adapter<MessagingAdapter.MessageViewHolder>() {

    var messagesList = mutableListOf<Message>()

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        public val tvMessage: TextView = itemView.findViewById(R.id.tv_message)
        public val tvBotMessage: TextView = itemView.findViewById(R.id.tv_bot_message)

        init {
            itemView.setOnClickListener{
                messagesList.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false))
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage = messagesList[position]

        when(currentMessage.id){
            SEND_ID -> {
                holder.tvMessage.apply{
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.tvBotMessage.visibility = View.GONE
            }
            RECEIVE_ID -> {
                holder.tvBotMessage.apply{
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.tvMessage.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }

    fun insertMessage(message: Message){
        this.messagesList.add(message)
        notifyItemInserted(messagesList.size)
        notifyDataSetChanged()
    }
}
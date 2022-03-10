package adapters

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firstchat.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import models.Messages

class ChatsAdapter(private val messageList: ArrayList<Messages>,private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val SENDER_VIEW_HOLDER: Int = 1;
    private val RECIVER_VIEW_HOLDER: Int = 2;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == SENDER_VIEW_HOLDER){
            val view = LayoutInflater.from(context).inflate(R.layout.sample_sender, parent, false)
            return SenderViewHolder(view)
        }
        val view = LayoutInflater.from(context).inflate(R.layout.sample_reciever, parent, false)
        return RecieverViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder.javaClass == SenderViewHolder::class.java){
            (holder as SenderViewHolder).message.text = messageList[position].getMessage()
        }
        else{
            (holder as RecieverViewHolder).message.text = messageList[position].getMessage()
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        if(messageList[position].getId() == FirebaseAuth.getInstance().currentUser?.uid){
            return SENDER_VIEW_HOLDER
        }
        return RECIVER_VIEW_HOLDER
    }
}

class RecieverViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val message: TextView = itemView.findViewById(R.id.recieverMessage)
    val time: TextView = itemView.findViewById(R.id.recieverTime)
}

class SenderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val message: TextView = itemView.findViewById(R.id.senderMessage)
    val time: TextView = itemView.findViewById(R.id.senderTime)
}
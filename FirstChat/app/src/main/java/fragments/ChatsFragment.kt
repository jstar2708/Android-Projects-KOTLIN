package fragments

import adapters.UserAdapter
import adapters.UserItemOnClick
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstchat.ChatsDetailActivity
import com.example.firstchat.R
import com.example.firstchat.databinding.FragmentChatsBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import models.Users
import java.net.UnknownServiceException


/**
 * A simple [Fragment] subclass.
 * Use the [ChatsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatsFragment : Fragment(), UserItemOnClick {

    private lateinit var binding: FragmentChatsBinding
    private lateinit var database: FirebaseDatabase
    private var list =  ArrayList<Users>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatsBinding.inflate(inflater, container, false)

        val userAdapter = UserAdapter(context, this)
        binding.chatsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.chatsRecyclerView.adapter = userAdapter

        database = FirebaseDatabase.getInstance("https://firstchat-bb8ce-default-rtdb.asia-southeast1.firebasedatabase.app")

        database.reference.child("Users").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                snapshot.children.forEach {dataSnapshot ->
                    val users: Users? = dataSnapshot.getValue(Users::class.java)
                    users?.setUserId(dataSnapshot.key.toString())
                    if (users != null) {
                        list.add(users)
                    }
                    userAdapter.updateListItem(list)
                }

                userAdapter.updateListItem(list)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("", error.details)
            }

        })

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ChatsFragment.
         */
        @JvmStatic
        fun newInstance() = ChatsFragment()

    }

    override fun onItemClicked(users: Users) {
        val intent = Intent(requireContext(), ChatsDetailActivity::class.java)
        intent.putExtra("username", users.getUserName())
        intent.putExtra("userid", users.getUserId())
        intent.putExtra("profilePic", users.getProfilePic())
        startActivity(intent)
    }
}
package com.example.firstchat2.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstchat2.MainActivity
import com.example.firstchat2.Models.User
import com.example.firstchat2.R
import com.example.firstchat2.adapters.ChatsAdapter
import com.example.firstchat2.adapters.UserItemOnClick
import com.example.firstchat2.databinding.FragmentChatsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


/**
 * A simple [Fragment] subclass.
 * Use the [ChatsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatsFragment : Fragment(), UserItemOnClick {

    private lateinit var database: FirebaseDatabase
    private lateinit var binding: FragmentChatsBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        database = FirebaseDatabase.getInstance("https://firstchat-86bd2-default-rtdb.asia-southeast1.firebasedatabase.app")
        binding = FragmentChatsBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        // Inflate the layout for this fragment
        val adapter = ChatsAdapter(requireContext(), this)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        database.reference.child("Users").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list: ArrayList<User> = ArrayList()
                snapshot.children.forEach {
                    val user = it.getValue(User::class.java)
                    user?.setUserId(it.key.toString())
                    if (!(user?.getUserId().equals(auth.currentUser?.uid)) && user != null) {
                        list.add(user)
                    }
                    adapter.updatedList(list)
                }
                adapter.updatedList(list)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.details, Toast.LENGTH_SHORT).show()
            }

        })
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment ChatsFragment.
         */
        @JvmStatic
        fun newInstance(): ChatsFragment{
            return ChatsFragment()
        }
    }

    override fun onClickUser(user: User) {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.putExtra("userId", user.getUserId())
        intent.putExtra("userName", user.getUserName())
        intent.putExtra("profilePic", user.getProfilePic())
        startActivity(intent)
    }
}
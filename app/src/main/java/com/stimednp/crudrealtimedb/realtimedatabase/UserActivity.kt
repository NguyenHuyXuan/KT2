package com.stimednp.crudrealtimedb.realtimedatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.stimednp.crudrealtimedb.R
import com.stimednp.crudrealtimedb.model.SanPham
import com.stimednp.crudrealtimedb.utils.Const
import com.stimednp.crudrealtimedb.utils.Const.PATH_NAME
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {
    private val mFirebase = FirebaseDatabase.getInstance()
    private val myref = mFirebase.getReference(Const.PATH_COLLECTION)
    private val query = myref.orderByChild(PATH_NAME)

    private lateinit var adapter: FirebaseRecyclerAdapter<SanPham, UserAdapter.UserViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        initView()
        setupAdapter()

        fab_firedb.setOnClickListener {
            startActivity(Intent(this, AddEditActivity::class.java).apply {
                putExtra(AddEditActivity.REQ_EDIT, false)
            })
        }
    }

    private fun initView() {
        supportActionBar?.title = "Simple CRUD Realtime Database"
        rv_firedb.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@UserActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setupAdapter() {
        val option = FirebaseRecyclerOptions.Builder<SanPham>()
            .setQuery(query, SanPham::class.java)
            .build()

        adapter = UserAdapter(this, myref, option)
        adapter.notifyDataSetChanged()
        rv_firedb.adapter = adapter
    }

    override fun onStart() {
        adapter.startListening()
        super.onStart()
    }

    override fun onStop() {
        adapter.stopListening()
        super.onStop()
    }
}

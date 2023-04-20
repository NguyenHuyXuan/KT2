package com.stimednp.crudrealtimedb.realtimedatabase

import android.content.Context
import com.stimednp.crudrealtimedb.model.SanPham
import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.stimednp.crudrealtimedb.R
import kotlinx.android.synthetic.main.item_list_user.view.*


class UserAdapter(private val context: Context, private val myref: DatabaseReference, options: FirebaseRecyclerOptions<SanPham>) :
    FirebaseRecyclerAdapter<SanPham, UserAdapter.UserViewHolder>(options)  {
        class UserViewHolder (val view : View ) : RecyclerView.ViewHolder(view){
            fun bindItem(sanpham: SanPham){
                view.apply {
                    val ten = "Ten SP : ${sanpham.TenSP}"
                    val loai = "Loai SP : ${sanpham.LoaiSP}"
                    val gia = "Gia SP : ${sanpham.GiaSP}"
                    val anh = "${sanpham.AnhSP}"

                    tv_ten.text = ten
                    tv_loai.text = loai
                    tv_gia.text = gia

                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_user, parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int, sanpham: SanPham) {
        holder.bindItem(sanpham)
        holder.itemView.setOnClickListener {
            showDialogMenu(sanpham)
        }
    }

    private fun showDialogMenu(sanpham: SanPham) {
        //dialog popup edit delete
        val builder = AlertDialog.Builder(context, R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
        val option = arrayOf("Edit", "Delete")
        builder.setItems(option) { dialog, which ->
            when (which) {
                0 -> context.startActivity(Intent(context, AddEditActivity::class.java).apply {
                    putExtra(AddEditActivity.REQ_EDIT, true)
                    putExtra(AddEditActivity.EXTRA_DATA, sanpham)
                })
                1 -> showDialogDel(sanpham)
            }
        }

        builder.create().show()
    }

    private fun showDialogDel(sanpham: SanPham) {
        val builder = AlertDialog.Builder(context, R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
            .setTitle("delete user?")
            .setMessage("delete ready? ${sanpham.TenSP} ?")
            .setPositiveButton(android.R.string.yes) { dialog, which ->
                deleteById(sanpham.TenSP)
            }.setNegativeButton(android.R.string.cancel, null)
        builder.create().show()
    }

    private fun deleteById(TenSP: String?) {
        if (TenSP != null) {
            myref.child(TenSP).removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Success Delete data", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failute Delete data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
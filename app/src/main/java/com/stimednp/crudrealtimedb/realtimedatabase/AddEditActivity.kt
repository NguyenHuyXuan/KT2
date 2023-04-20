package com.stimednp.crudrealtimedb.realtimedatabase

import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.stimednp.crudrealtimedb.R
import com.stimednp.crudrealtimedb.model.SanPham
import com.stimednp.crudrealtimedb.utils.Const
import kotlinx.android.synthetic.main.activity_add_edit.*

class AddEditActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
        const val REQ_EDIT = "req_edit"
    }

    private var isEdit = false
    private var sanpham: SanPham? = null

    private val mFirebaseDatabase = FirebaseDatabase.getInstance()
    private val myref = mFirebaseDatabase.getReference(Const.PATH_COLLECTION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)
        isEdit = intent.getBooleanExtra(REQ_EDIT, false)
        sanpham = intent.getParcelableExtra(EXTRA_DATA)

        btn_save.setOnClickListener {
            saveData()
        }
        initView()
    }

    private fun initView() {
        if (isEdit) {
            btn_save.text = getString(R.string.update)
            ti_tensp.text = Editable.Factory.getInstance().newEditable(sanpham?.TenSP)
            ti_loaisp.text = Editable.Factory.getInstance().newEditable(sanpham?.LoaiSP)
            ti_giasp.text = Editable.Factory.getInstance().newEditable(sanpham?.GiaSP.toString())
            ti_anhsp.text = Editable.Factory.getInstance().newEditable(sanpham?.AnhSP)
        }
    }

    private fun saveData() {
        setData(sanpham?.TenSP)
    }

    private fun setData(TenSP: String?) {
        createSanPham(TenSP)
    }

    private fun createSanPham(TenSP: String?) {
        val tenspmoi = TenSP
            ?: myref.push().key.toString() // if id != null will be update data, if id == null will be creted new id and add data
        val loaisp = ti_loaisp.text.toString()
        val giasp = ti_giasp.text.toString()
        val anhsp = ti_anhsp.text.toString()

        val sanpham = SanPham(tenspmoi, loaisp, giasp.toFloat(), anhsp)

        myref.child(tenspmoi).setValue(sanpham)
            .addOnSuccessListener {
                if (isEdit) Toast.makeText(this, "Success update data", Toast.LENGTH_SHORT).show()
                else Toast.makeText(this, "Success add data", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Failure add data", Toast.LENGTH_SHORT).show()
            }
    }
}
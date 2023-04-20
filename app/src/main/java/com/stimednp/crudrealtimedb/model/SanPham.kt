package com.stimednp.crudrealtimedb.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class SanPham(
    var TenSP: String? = null,
    var LoaiSP: String? = null,
    var GiaSP: Float? = null,
    var AnhSP: String? = null
) : Parcelable
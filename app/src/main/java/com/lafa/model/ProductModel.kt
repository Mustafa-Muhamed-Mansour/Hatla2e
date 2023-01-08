package com.lafa.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.lafa.common.Constant
import java.io.Serializable


@Entity(tableName = Constant.PRODUCT)
data class ProductModel(@PrimaryKey(autoGenerate = true)
                        var id: Int ?= null,
                        @SerializedName("description")
                        val description: String,
                        @SerializedName("discount")
                        val discount: Int,
                        @SerializedName("image")
                        val image: String,
                        @SerializedName("images")
                        val images: List<String>,    /* replace a productBannerModel to string if not on */
                        @SerializedName("name")
                        val name: String,
                        @SerializedName("old_price")
                        val oldPrice: Double,
                        @SerializedName("price")
                        val price: Double,
                        val quantity: Int) : Serializable


//    Parcelable
//{
//    constructor(parcel: Parcel) : this(parcel.readString().toString(), parcel.readInt(), parcel.readString().toString(), TODO("images"), parcel.readString().toString(), parcel.readDouble(), parcel.readDouble())
//
//    override fun writeToParcel(parcel: Parcel, flags: Int)
//    {
//        parcel.writeString(description)
//        parcel.writeInt(discount)
//        parcel.writeString(image)
//        parcel.writeString(name)
//        parcel.writeDouble(oldPrice)
//        parcel.writeDouble(price)
//    }
//
//    override fun describeContents(): Int
//    {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<ProductModel>
//    {
//        override fun createFromParcel(parcel: Parcel): ProductModel
//        {
//            return ProductModel(parcel)
//        }
//
//        override fun newArray(size: Int): Array<ProductModel?>
//        {
//            return arrayOfNulls(size)
//        }
//    }
//}

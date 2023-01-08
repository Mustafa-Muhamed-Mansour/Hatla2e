package com.lafa.interfaces

import com.lafa.model.ProductModel
import javax.inject.Singleton

interface ProductDetail
{
    fun clickProductOfDetail(productModel: ProductModel)
}
package com.createsapp.kotlincartfirebase.listener

import com.createsapp.kotlincartfirebase.model.CartModel

interface ICartLoadListener {
    fun onLoadCartSuccess(cartModelList: List<CartModel>)
    fun onLoadCartFailed(message: String?)
}